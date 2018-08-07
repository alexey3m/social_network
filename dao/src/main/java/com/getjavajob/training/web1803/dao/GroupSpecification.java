package com.getjavajob.training.web1803.dao;

import com.getjavajob.training.web1803.common.AccountInGroup;
import com.getjavajob.training.web1803.common.Group;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

import static com.getjavajob.training.web1803.common.enums.GroupStatus.ACCEPTED;
import static javax.persistence.criteria.JoinType.INNER;

public class GroupSpecification {
    public static Specification<Group> searchByMemberId(final int memberId) {
        return (Specification<Group>) (root, query, criteriaBuilder) -> {
            Join<Group, AccountInGroup> accountInGroupJoin = root.join("accounts", INNER);
            return criteriaBuilder.and(
                    criteriaBuilder.equal(accountInGroupJoin.get("userMemberId"), memberId),
                    criteriaBuilder.equal(accountInGroupJoin.get("status"), ACCEPTED));
        };
    }
}