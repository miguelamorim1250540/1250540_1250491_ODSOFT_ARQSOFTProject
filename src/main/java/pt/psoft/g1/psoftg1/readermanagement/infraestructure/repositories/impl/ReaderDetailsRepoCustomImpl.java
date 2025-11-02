package pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.services.SearchReadersQuery;
import pt.psoft.g1.psoftg1.shared.services.Page;
import pt.psoft.g1.psoftg1.usermanagement.model.User;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReaderDetailsRepoCustomImpl implements ReaderDetailsRepoCustom {

    private final EntityManager em;

    @Override
    public List<ReaderDetails> searchReaderDetails(final Page page, final SearchReadersQuery query) {

        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<ReaderDetails> cq = cb.createQuery(ReaderDetails.class);
        final Root<ReaderDetails> readerDetailsRoot = cq.from(ReaderDetails.class);
        Join<ReaderDetails, User> userJoin = readerDetailsRoot.join("reader");

        cq.select(readerDetailsRoot);

        final List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(query.getName())) {
            predicates.add(cb.like(userJoin.get("name"), "%" + query.getName() + "%"));
            cq.orderBy(cb.asc(userJoin.get("name")));
        }

        if (StringUtils.hasText(query.getEmail())) {
            predicates.add(cb.equal(userJoin.get("username"), query.getEmail()));
            cq.orderBy(cb.asc(userJoin.get("username")));
        }

        if (StringUtils.hasText(query.getPhoneNumber())) {
            predicates.add(cb.equal(readerDetailsRoot.get("phoneNumber").get("phoneNumber"), query.getPhoneNumber()));
            cq.orderBy(cb.asc(readerDetailsRoot.get("phoneNumber").get("phoneNumber")));
        }

        if (!predicates.isEmpty()) {
            cq.where(cb.or(predicates.toArray(new Predicate[0])));
        }

        final TypedQuery<ReaderDetails> queryResult = em.createQuery(cq);
        queryResult.setFirstResult((page.getNumber() - 1) * page.getLimit());
        queryResult.setMaxResults(page.getLimit());

        return queryResult.getResultList();
    }
}
