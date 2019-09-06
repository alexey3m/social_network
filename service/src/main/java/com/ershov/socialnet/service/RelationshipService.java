package com.ershov.socialnet.service;

import com.ershov.socialnet.common.Account;
import com.ershov.socialnet.common.Relationship;
import com.ershov.socialnet.common.enums.Status;
import com.ershov.socialnet.dao.RelationshipDAO;
import com.ershov.socialnet.dao.repository.AccountRepository;
import com.ershov.socialnet.dao.repository.RelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RelationshipService {
    private RelationshipDAO relationshipDAO;

    private AccountRepository accountRepository;
    private RelationshipRepository relationshipRepository;

    @Autowired
    public RelationshipService(AccountRepository accountRepository, RelationshipRepository relationshipRepository) {
        this.accountRepository = accountRepository;
        this.relationshipRepository = relationshipRepository;
    }

    @Transactional
    public boolean addQueryFriend(int idFrom, int idTo) {
        relationshipRepository.saveAndFlush(
                idFrom < idTo ?
                        new Relationship(idFrom, idTo, Status.PENDING, idFrom) :
                        new Relationship(idTo, idFrom, Status.PENDING, idFrom));
        return true;
    }

    @Transactional
    public boolean acceptFriend(int idFrom, int idTo) {
        Relationship relationship = idFrom < idTo ?
                relationshipRepository.findByUserOneIdAndUserTwoId(idFrom, idTo) :
                relationshipRepository.findByUserOneIdAndUserTwoId(idTo, idFrom);
        relationship.setStatus(Status.ACCEPTED);
        relationshipRepository.saveAndFlush(relationship);
        return true;
    }

    @Transactional
    public boolean declineFriend(int idFrom, int idTo) {
        Relationship relationship = idFrom < idTo ?
                relationshipRepository.findByUserOneIdAndUserTwoId(idFrom, idTo) :
                relationshipRepository.findByUserOneIdAndUserTwoId(idTo, idFrom);
        relationship.setStatus(Status.DECLINE);
        relationshipRepository.saveAndFlush(relationship);
        return true;
    }

    @Transactional
    public boolean removeFriend(int idFrom, int idTo) {
        Relationship relationship = idFrom < idTo ?
                relationshipRepository.findByUserOneIdAndUserTwoId(idFrom, idTo) :
                relationshipRepository.findByUserOneIdAndUserTwoId(idTo, idFrom);
        relationshipRepository.delete(relationship);
        return true;
    }

    public Status getStatus(int idFrom, int idTo) {
        Relationship relationship = idFrom < idTo ?
                relationshipRepository.findByUserOneIdAndUserTwoId(idFrom, idTo) :
                relationshipRepository.findByUserOneIdAndUserTwoId(idTo, idFrom);
        return relationship.getStatus();
    }

    public Status getPendingRequestToMe(int idFrom, int idTo) {
        Relationship relationship = idFrom < idTo ?
                relationshipRepository.findByUserOneIdAndUserTwoIdAndActionUserId(idFrom, idTo, idFrom) :
                relationshipRepository.findByUserOneIdAndUserTwoIdAndActionUserId(idTo, idFrom, idFrom);
        return relationship.getStatus();
    }

    public List<Account> getAcceptedFriendsList(int id) {
        List<Integer> friendsId = relationshipDAO.getFriendsIdList(id);
        List<Account> friends = new ArrayList<>();
        for (int idFriend : friendsId) {
            friends.add(accountRepository.getOne(idFriend));
        }
        return friends;
    }

    public List<Account> getPendingRequestsToId(int id) {
        List<Integer> friendsId = relationshipDAO.getPendingRequestToId(id);
        List<Account> friends = new ArrayList<>();
        for (int idFriend : friendsId) {
            friends.add(accountRepository.getOne(idFriend));
        }
        return friends;
    }

    public List<Account> getFriendRequestsFromId(int id) {
        List<Integer> friendsId = relationshipDAO.getFriendRequestsFromId(id);
        List<Account> friends = new ArrayList<>();
        for (int idFriend : friendsId) {
            friends.add(accountRepository.getOne(idFriend));
        }
        return friends;
    }
}