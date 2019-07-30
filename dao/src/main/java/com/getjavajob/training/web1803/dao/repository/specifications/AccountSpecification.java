package com.getjavajob.training.web1803.dao.repository.specifications;

import com.getjavajob.training.web1803.common.Account;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;

public class AccountSpecification {

    private AccountSpecification() {
        // no op
    }

    public static Specification<Account> searchByAllPartsName(String search) {
        return (root, query, criteriaBuilder) -> {
            String lowerSearch = search.toLowerCase();
            Expression<String> exp1 = criteriaBuilder.concat(root.get("firstName"), " ");
            exp1 = criteriaBuilder.concat(exp1, root.get("lastName"));
            exp1 = criteriaBuilder.concat(exp1, " ");
            exp1 = criteriaBuilder.concat(exp1, root.get("middleName"));
            Expression<String> exp2 = criteriaBuilder.concat(root.get("firstName"), " ");
            exp2 = criteriaBuilder.concat(exp2, root.get("middleName"));
            exp2 = criteriaBuilder.concat(exp2, " ");
            exp2 = criteriaBuilder.concat(exp2, root.get("lastName"));

            return criteriaBuilder.or(
                    criteriaBuilder.like(exp1, "%" + lowerSearch + "%"),
                    criteriaBuilder.like(exp2, "%" + lowerSearch + "%"));
        };
    }

}
