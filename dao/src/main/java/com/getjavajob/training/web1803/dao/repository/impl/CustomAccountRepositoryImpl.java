package com.getjavajob.training.web1803.dao.repository.impl;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.dao.repository.CustomAccountRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class CustomAccountRepositoryImpl implements CustomAccountRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Account> findBySpecificationAndPage(Specification<Account> specification, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> query = criteriaBuilder.createQuery(Account.class);
        Root<Account> root = query.from(Account.class);
        int page = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        List<Account> result = entityManager.createQuery(query.select(root)
                .where(specification.toPredicate(root, query, criteriaBuilder)))
                .setFirstResult(page == 1 ? 0 : page * pageSize - pageSize)
                .setMaxResults(pageSize)
                .getResultList();
        return new PageImpl<>(result, pageable, 0);
    }

    @Override
    public long countFindBySpecification(Specification<Account> specification) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Account> root = query.from(Account.class);

        return entityManager.createQuery(query.select(criteriaBuilder.count(root))
                .where(specification.toPredicate(root, query, criteriaBuilder)))
                .getSingleResult();
    }
}