package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import io.micrometer.common.util.StringUtils;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.services.SearchBooksQuery;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

@Repository
@RequiredArgsConstructor
public class BookRepoCustomImpl implements BookRepoCustom {

    private final EntityManager em;

    @Override
    public List<Book> searchBooks(pt.psoft.g1.psoftg1.shared.services.Page page, SearchBooksQuery query) {

        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        final Root<Book> root = cq.from(Book.class);

        Join<Book, Genre> genreJoin = root.join("genre");
        Join<Book, Author> authorJoin = root.join("authors");

        cq.select(root);
        List<Predicate> where = new ArrayList<>();

        if (StringUtils.isNotBlank(query.getTitle())) {
            where.add(cb.like(root.get("title"), "%" + query.getTitle() + "%"));
        }

        if (StringUtils.isNotBlank(query.getGenre())) {
            where.add(cb.like(genreJoin.get("name"), "%" + query.getGenre() + "%"));
        }

        if (StringUtils.isNotBlank(query.getAuthorName())) {
            where.add(cb.like(authorJoin.get("name"), "%" + query.getAuthorName() + "%"));
        }

        if (!where.isEmpty()) {
            cq.where(cb.or(where.toArray(new Predicate[0])));
        }

        cq.orderBy(cb.asc(root.get("title")));

        TypedQuery<Book> q = em.createQuery(cq);
        q.setFirstResult((page.getNumber() - 1) * page.getLimit());
        q.setMaxResults(page.getLimit());

        return q.getResultList();
    }
}
