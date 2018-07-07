package com.getjavajob.training.web1803.service;

import com.getjavajob.training.web1803.common.Account;
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
        return idFrom < idTo ? relationshipDAO.createQueryFriend(idFrom, idTo, idFrom) :
                relationshipDAO.createQueryFriend(idTo, idFrom, idFrom);
    }

    public boolean acceptFriend(int idFrom, int idTo) {
        return idFrom < idTo ? relationshipDAO.updateQueryFriend(idFrom, idTo, 1, idFrom) :
                relationshipDAO.updateQueryFriend(idTo, idFrom, 1, idFrom);
    }

    public boolean declineFriend(int idFrom, int idTo) {
        return idFrom < idTo ? relationshipDAO.updateQueryFriend(idFrom, idTo, 2, idFrom) :
                relationshipDAO.updateQueryFriend(idTo, idFrom, 2, idFrom);
    }

    public boolean removeFriend(int idFrom, int idTo) {
        return idFrom < idTo ? relationshipDAO.removeFriend(idFrom, idTo) : relationshipDAO.removeFriend(idTo, idFrom);
    }

    public Status getStatus(int idFrom, int idTo) {
        return idFrom < idTo ? relationshipDAO.getStatus(idFrom, idTo) : relationshipDAO.getStatus(idTo, idFrom);
    }

    public Status getPendingRequestToMe(int idFrom, int idTo) {
        return idFrom < idTo ? relationshipDAO.getPendingRequestToMe(idFrom, idTo, idFrom) :
                relationshipDAO.getPendingRequestToMe(idTo, idFrom, idFrom);
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