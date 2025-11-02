package pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl.DataModel.UserDataModelSQL;
import pt.psoft.g1.psoftg1.usermanagement.services.SearchUsersQuery;
import pt.psoft.g1.psoftg1.shared.services.Page;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
class UserRepoCustomImpl implements UserRepoCustom {

    private final EntityManager em;

    @Override
    public List<UserDataModelSQL> searchUsers(Page page, SearchUsersQuery query) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserDataModelSQL> cq = cb.createQuery(UserDataModelSQL.class);
        Root<UserDataModelSQL> root = cq.from(UserDataModelSQL.class);
        cq.select(root);

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(query.getUsername())) {
            predicates.add(cb.like(root.get("username"), "%" + query.getUsername() + "%"));
        }

        if (StringUtils.hasText(query.getFullName())) {
            predicates.add(cb.like(root.get("name").get("name"), "%" + query.getFullName() + "%"));
        }

        if (!predicates.isEmpty()) {
            cq.where(cb.or(predicates.toArray(new Predicate[0])));
        }

        cq.orderBy(cb.asc(root.get("username")));

        TypedQuery<UserDataModelSQL> typedQuery = em.createQuery(cq);
        typedQuery.setFirstResult((page.getNumber() - 1) * page.getLimit());
        typedQuery.setMaxResults(page.getLimit());

        return typedQuery.getResultList();
    }
}
