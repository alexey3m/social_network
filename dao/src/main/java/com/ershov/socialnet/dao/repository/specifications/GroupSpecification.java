package com.ershov.socialnet.dao.repository.specifications;

import com.ershov.socialnet.common.AccountInGroup;
import com.ershov.socialnet.common.Group;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

import static com.ershov.socialnet.common.enums.GroupStatus.ACCEPTED;
import static javax.persistence.criteria.JoinType.INNER;

public class GroupSpecification {
    public static Specification<Group> searchByMemberId(final int memberId) {
        return (root, query, criteriaBuilder) -> {
            Join<Group, AccountInGroup> accountInGroupJoin = root.join("accounts", INNER);
            return criteriaBuilder.and(
                    criteriaBuilder.equal(accountInGroupJoin.get("userMemberId"), memberId),
                    criteriaBuilder.equal(accountInGroupJoin.get("status"), ACCEPTED));
        };
    }
}