package com.ershov.socialnet.dao;

import com.ershov.socialnet.common.Relationship;
import com.ershov.socialnet.common.enums.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public class RelationshipDAO {
    private static final Logger logger = LoggerFactory.getLogger(RelationshipDAO.class);

    private EntityManager entityManager;

    @Autowired
    public RelationshipDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }



    public List<Integer> getFriendsIdList(int id) {
        logger.info("In getFriendsIdList method");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Relationship> criteriaQuery = criteriaBuilder.createQuery(Relationship.class);
        Root<Relationship> from = criteriaQuery.from(Relationship.class);
        CriteriaQuery<Relationship> firstSelect = criteriaQuery.select(from).where(
                criteriaBuilder.and(
                        criteriaBuilder.or(
                                criteriaBuilder.equal(from.get("userOneId"), id),
                                criteriaBuilder.equal(from.get("userTwoId"), id)),
                        criteriaBuilder.equal(from.get("status"), Status.ACCEPTED)));
        List<Relationship> relationshipList = entityManager.createQuery(firstSelect).getResultList();
        Set<Integer> result = new HashSet<>();
        for (Relationship relationship : relationshipList) {
            int userOneId = relationship.getUserOneId();
            if (userOneId != id) {
                result.add(userOneId);
            }
            int userTwoId = relationship.getUserTwoId();
            if (userTwoId != id) {
                result.add(userTwoId);
            }
        }
        return new ArrayList<>(result);
    }

    public List<Integer> getPendingRequestToId(int id) {
        logger.info("In getPendingRequestToId method");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Relationship> criteriaQuery = criteriaBuilder.createQuery(Relationship.class);
        Root<Relationship> from = criteriaQuery.from(Relationship.class);
        CriteriaQuery<Relationship> firstSelect = criteriaQuery.select(from).where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(from.get("status"), Status.PENDING),
                        criteriaBuilder.notEqual(from.get("actionUserId"), id),
                        criteriaBuilder.or(
                                criteriaBuilder.equal(from.get("userOneId"), id),
                                criteriaBuilder.equal(from.get("userTwoId"), id))));
        List<Relationship> relationshipList = entityManager.createQuery(firstSelect).getResultList();
        Set<Integer> result = new HashSet<>();
        for (Relationship relationship : relationshipList) {
            int userOneId = relationship.getUserOneId();
            if (userOneId != id) {
                result.add(userOneId);
            }
            int userTwoId = relationship.getUserTwoId();
            if (userTwoId != id) {
                result.add(userTwoId);
            }
        }
        return new ArrayList<>(result);
    }

    public List<Integer> getFriendRequestsFromId(int id) {
        logger.info("In getFriendRequestsFromId method");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Relationship> criteriaQuery = criteriaBuilder.createQuery(Relationship.class);
        Root<Relationship> from = criteriaQuery.from(Relationship.class);
        CriteriaQuery<Relationship> firstSelect = criteriaQuery.select(from).where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(from.get("userTwoId"), id),
                        criteriaBuilder.equal(from.get("status"), Status.PENDING),
                        criteriaBuilder.equal(from.get("actionUserId"), id)));
        List<Relationship> firstResult = entityManager.createQuery(firstSelect).getResultList();
        CriteriaQuery<Relationship> secondSelect = criteriaQuery.select(from).where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(from.get("userOneId"), id),
                        criteriaBuilder.equal(from.get("status"), Status.PENDING),
                        criteriaBuilder.equal(from.get("actionUserId"), id)));
        List<Relationship> secondResult = entityManager.createQuery(secondSelect).getResultList();
        Set<Integer> result = new HashSet<>();
        for (Relationship relationship : firstResult) {
            int userOneId = relationship.getUserOneId();
            if (userOneId != id) {
                result.add(userOneId);
            }
        }
        for (Relationship relationship : secondResult) {
            int userTwoId = relationship.getUserTwoId();
            if (userTwoId != id) {
                result.add(userTwoId);
            }
        }
        return new ArrayList<>(result);
    }
}