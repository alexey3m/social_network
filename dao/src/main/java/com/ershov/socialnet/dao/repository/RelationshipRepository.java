package com.ershov.socialnet.dao.repository;

import com.ershov.socialnet.common.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelationshipRepository extends JpaRepository<Relationship, Integer> {

    Relationship findByUserOneIdAndUserTwoId(int userOneId, int UserTwoId);

    Relationship findByUserOneIdAndUserTwoIdAndActionUserId(int userOneId, int UserTwoId, int actionUserid);
}
