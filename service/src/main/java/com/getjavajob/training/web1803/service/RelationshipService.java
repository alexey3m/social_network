package com.getjavajob.training.web1803.service;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.common.Relationship;
import com.getjavajob.training.web1803.common.enums.Status;
import com.getjavajob.training.web1803.dao.AccountDAO;
import com.getjavajob.training.web1803.dao.RelationshipDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RelationshipService {
    private RelationshipDAO relationshipDAO;
    private AccountDAO accountDAO;

    @Autowired
    public RelationshipService(RelationshipDAO relationshipDAO, AccountDAO accountDAO) {
        this.relationshipDAO = relationshipDAO;
        this.accountDAO = accountDAO;
    }

    public boolean addQueryFriend(int idFrom, int idTo) {

        return idFrom < idTo ? relationshipDAO.createQueryFriend(new Relationship(idFrom, idTo, Status.PENDING,idFrom)) :
                relationshipDAO.createQueryFriend(new Relationship(idTo, idFrom, Status.PENDING,idFrom));
    }

    public boolean acceptFriend(int idFrom, int idTo) {
        return idFrom < idTo ? relationshipDAO.updateQueryFriend(new Relationship(idFrom, idTo, Status.ACCEPTED,idFrom)) :
                relationshipDAO.updateQueryFriend(new Relationship(idTo, idFrom, Status.ACCEPTED,idFrom));
    }

    public boolean declineFriend(int idFrom, int idTo) {
        return idFrom < idTo ? relationshipDAO.updateQueryFriend(new Relationship(idFrom, idTo, Status.DECLINE,idFrom)) :
                relationshipDAO.updateQueryFriend(new Relationship(idTo, idFrom, Status.DECLINE,idFrom));
    }

    public boolean removeFriend(int idFrom, int idTo) {
        return idFrom < idTo ? relationshipDAO.removeFriend(new Relationship(idFrom, idTo, null,0)) :
                relationshipDAO.removeFriend(new Relationship(idTo, idFrom, null,0));
    }

    public Status getStatus(int idFrom, int idTo) {
        return idFrom < idTo ? relationshipDAO.getStatus(new Relationship(idFrom, idTo, null,0)) :
                relationshipDAO.getStatus(new Relationship(idTo, idFrom, null,0));
    }

    public Status getPendingRequestToMe(int idFrom, int idTo) {
        return idFrom < idTo ? relationshipDAO.getPendingRequestToMe(new Relationship(idFrom, idTo, null,idFrom)) :
                relationshipDAO.getPendingRequestToMe(new Relationship(idTo, idFrom, null,idFrom));
    }

    public List<Account> getAcceptedFriendsList(int id) {
        List<Integer> friendsId = relationshipDAO.getFriendsIdList(id);
        List<Account> friends = new ArrayList<>();
        for (int idFriend : friendsId) {
            friends.add(accountDAO.get(idFriend));
        }
        return friends;
    }

    public List<Account> getPendingRequestsToId(int id) {
        List<Integer> friendsId = relationshipDAO.getPendingRequestToId(id);
        List<Account> friends = new ArrayList<>();
        for (int idFriend : friendsId) {
            friends.add(accountDAO.get(idFriend));
        }
        return friends;
    }

    public List<Account> getFriendRequestsFromId(int id) {
        List<Integer> friendsId = relationshipDAO.getFriendRequestsFromId(id);
        List<Account> friends = new ArrayList<>();
        for (int idFriend : friendsId) {
            friends.add(accountDAO.get(idFriend));
        }
        return friends;
    }
}