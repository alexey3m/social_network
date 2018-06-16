package com.getjavajob.training.web1803.service;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.common.Status;
import com.getjavajob.training.web1803.dao.AccountDAO;
import com.getjavajob.training.web1803.dao.RelationshipDAO;
import com.getjavajob.training.web1803.dao.exceptions.DaoException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RelationshipService {
    private RelationshipDAO relationshipDAO;

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
    }

    public boolean addQueryFriend(int idFrom, int idTo) {
        try {
            return idFrom < idTo ? relationshipDAO.createQueryFriend(idFrom, idTo, idFrom) : relationshipDAO.createQueryFriend(idTo, idFrom, idFrom);
        } catch (DaoException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean acceptFriend(int idFrom, int idTo) {
        try {
            return idFrom < idTo ? relationshipDAO.updateQueryFriend(idFrom, idTo, 1, idFrom) : relationshipDAO.updateQueryFriend(idTo, idFrom, 1, idFrom);
        } catch (DaoException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean declineFriend(int idFrom, int idTo) {
        try {
            return idFrom < idTo ? relationshipDAO.updateQueryFriend(idFrom, idTo, 2, idFrom) : relationshipDAO.updateQueryFriend(idTo, idFrom, 2, idFrom);
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

    public Status getStatus(int idFrom, int idTo) {
        try {
            return idFrom < idTo ? relationshipDAO.getStatus(idFrom, idTo) : relationshipDAO.getStatus(idTo, idFrom);
        } catch (DaoException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Status getPendingRequestToMe(int idFrom, int idTo) {
        try {
            return idFrom < idTo ? relationshipDAO.getPendingRequestToMe(idFrom, idTo, idFrom) : relationshipDAO.getPendingRequestToMe(idTo, idFrom, idFrom);
        } catch (DaoException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Account> getAcceptedFriendsList(int id) {
        List<Integer> friendsId;
        List<Account> friends = new ArrayList<>();
        try {
            friendsId = relationshipDAO.getFriendsIdList(id);
            AccountDAO accountDAO = new AccountDAO();
            for (int idFriend : friendsId) {
                friends.add(accountDAO.get(idFriend));
            }
        } catch (DaoException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
        return friends;
    }

    public List<Account> getPendingRequestsToId(int id) {
        List<Integer> friendsId;
        List<Account> friends = new ArrayList<>();
        try {
            friendsId = relationshipDAO.getPendingRequestToId(id);
            AccountDAO accountDAO = new AccountDAO();
            for (int idFriend : friendsId) {
                friends.add(accountDAO.get(idFriend));
            }
        } catch (DaoException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
        return friends;
    }

    public List<Account> getFriendRequestsFromId(int id) {
        List<Integer> friendsId;
        List<Account> friends = new ArrayList<>();
        try {
            friendsId = relationshipDAO.getFriendRequestsFromId(id);
            AccountDAO accountDAO = new AccountDAO();
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