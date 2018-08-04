package com.getjavajob.training.web1803.dao.repository;

import com.getjavajob.training.web1803.common.AccountInGroup;
import com.getjavajob.training.web1803.common.Group;
import com.getjavajob.training.web1803.common.enums.GroupStatus;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class GroupSpecification {
    public static Specification<Group> searchByMemberId(final int memberId) {
        return (Specification<Group>) (root, query, criteriaBuilder) -> {
            Join<Group, AccountInGroup> accountInGroupJoin = root.join("accounts", JoinType.INNER);
            return criteriaBuilder.and(
                    criteriaBuilder.equal(accountInGroupJoin.get("userMemberId"), memberId),
                    criteriaBuilder.equal(accountInGroupJoin.get("status"), GroupStatus.ACCEPTED));
        };
    }
}