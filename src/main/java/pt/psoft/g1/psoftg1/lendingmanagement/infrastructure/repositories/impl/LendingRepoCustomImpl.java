package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.shared.services.Page;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LendingRepoCustomImpl implements LendingRepoCustom {

    private final EntityManager em;

    @Override
    public List<Lending> getOverdue(Page page) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Lending> cq = cb.createQuery(Lending.class);
        Root<Lending> root = cq.from(Lending.class);
        cq.select(root);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.isNull(root.get("returnedDate")));
        predicates.add(cb.lessThan(root.get("limitDate"), LocalDate.now()));

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(root.get("limitDate")));

        TypedQuery<Lending> query = em.createQuery(cq);
        query.setFirstResult((page.getNumber() - 1) * page.getLimit());
        query.setMaxResults(page.getLimit());

        return query.getResultList();
    }

    @Override
    public List<Lending> searchLendings(Page page, String readerNumber, String isbn, Boolean returned, LocalDate startDate, LocalDate endDate) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Lending> cq = cb.createQuery(Lending.class);
        Root<Lending> lendingRoot = cq.from(Lending.class);

        Join<Lending, Book> bookJoin = lendingRoot.join("book", JoinType.LEFT);
        Join<Lending, ReaderDetails> readerJoin = lendingRoot.join("readerDetails", JoinType.LEFT);

        cq.select(lendingRoot);

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(readerNumber)) {
            predicates.add(cb.like(readerJoin.get("readerNumber"), "%" + readerNumber + "%"));
        }
        if (StringUtils.hasText(isbn)) {
            predicates.add(cb.like(bookJoin.get("isbn"), "%" + isbn + "%"));
        }
        if (returned != null) {
            predicates.add(returned
                    ? cb.isNotNull(lendingRoot.get("returnedDate"))
                    : cb.isNull(lendingRoot.get("returnedDate")));
        }
        if (startDate != null) {
            predicates.add(cb.greaterThanOrEqualTo(lendingRoot.get("startDate"), startDate));
        }
        if (endDate != null) {
            predicates.add(cb.lessThanOrEqualTo(lendingRoot.get("startDate"), endDate));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(lendingRoot.get("lendingNumber")));

        TypedQuery<Lending> query = em.createQuery(cq);
        query.setFirstResult((page.getNumber() - 1) * page.getLimit());
        query.setMaxResults(page.getLimit());

        return query.getResultList();
    }
}
