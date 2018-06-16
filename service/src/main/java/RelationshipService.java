package com.getjavajob.training.web1803.service;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.dao.AccountDAO;
import com.getjavajob.training.web1803.dao.RelationshipDAO;
import com.getjavajob.training.web1803.dao.exceptions.DaoException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RelationshipService {
    private RelationshipDAO relationshipDAO;
    private AccountDAO accountDAO;

    public RelationshipService() {
        try {
            relationshipDAO = new RelationshipDAO();
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    //Constructor for tests
    public RelationshipService(RelationshipDAO relationshipDAO, AccountDAO accountDAO) {
        this.relationshipDAO = relationshipDAO;
        this.accountDAO = accountDAO;
    }

    public boolean addFriend(int idFrom, int idTo) {
        try {
            return idFrom < idTo ? relationshipDAO.addFriend(idFrom, idTo) : relationshipDAO.addFriend(idTo, idFrom);
        } catch (DaoException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeFriend(int idFrom, int idTo) {
        try {
            return idFrom < idTo ? relationshipDAO.removeFriend(idFrom, idTo) : relationshipDAO.removeFriend(idTo, idFrom);
        } catch (DaoException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Account> getFriendsList(int id) {
        List<Integer> friendsId;
        List<Account> friends = new ArrayList<>();
        try {
            friendsId = relationshipDAO.getFriendsIdList(id);
            if (accountDAO == null) {
                accountDAO = new AccountDAO();
            }
            for (int idFriend : friendsId) {
                friends.add(accountDAO.get(idFriend));
            }
        } catch (DaoException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
        return friends;
    }
}