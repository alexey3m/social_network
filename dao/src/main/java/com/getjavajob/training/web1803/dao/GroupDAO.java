package com.getjavajob.training.web1803.dao;

import com.getjavajob.training.web1803.common.AccountInGroup;
import com.getjavajob.training.web1803.common.Group;
import com.getjavajob.training.web1803.common.enums.GroupRole;
import com.getjavajob.training.web1803.common.enums.GroupStatus;
import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
@Transactional
public class GroupDAO {
    private static final Logger logger = LoggerFactory.getLogger(GroupDAO.class);
    private static final int SEARCH_RESULT_PER_PAGE = 5;

    private SessionFactory sessionFactory;

    @Autowired
    public GroupDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public GroupDAO() {
    }

    @Transactional
    public boolean create(Group group) throws DaoNameException {
        logger.info("In create method");
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Group> criteriaQueryCheckEmail = criteriaBuilder.createQuery(Group.class);
        Root<Group> from = criteriaQueryCheckEmail.from(Group.class);
        CriteriaQuery<Group> selectName = criteriaQueryCheckEmail.select(from).where(criteriaBuilder.equal(
                from.get("name"), group.getName()));
        boolean nameNotExist = session.createQuery(selectName).getResultList().isEmpty();
        if (nameNotExist) {
            session.persist(group);
            logger.info("New group created.");
            return true;
        } else {
            logger.warn("Name exists. Thrown exception - " + DaoNameException.class);
            throw new DaoNameException("Group name \"" + group.getName() + "\" is already used.");
        }
    }

    public Group get(int groupId) {
        logger.info("In get method");
        return sessionFactory.getCurrentSession().get(Group.class, groupId);
    }

    public List<Group> getAll() {
        logger.info("In getAll method");
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Group> criteriaQuery = criteriaBuilder.createQuery(Group.class);
        Root<Group> from = criteriaQuery.from(Group.class);
        CriteriaQuery<Group> selectName = criteriaQuery.select(from);
        return session.createQuery(selectName).getResultList();
    }

    public List<Group> getAllByUserId(int userId) {
        logger.info("In getAllByUserId method");
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Group> criteriaQuery = criteriaBuilder.createQuery(Group.class);
        Root<Group> from = criteriaQuery.from(Group.class);
        Join<Group, AccountInGroup> accountInGroupJoin = from.join("accounts", JoinType.INNER);
        criteriaQuery.select(from).distinct(true).where(criteriaBuilder.and(
                criteriaBuilder.equal(accountInGroupJoin.get("userMemberId"), userId),
                criteriaBuilder.equal(accountInGroupJoin.get("status"), GroupStatus.ACCEPTED)));
        return session.createQuery(criteriaQuery).getResultList();
    }

    public List<Group> searchByString(String search, int page) {
        logger.info("In searchByString method. Search string: " + search);
        String lowerSearch = search.toLowerCase();
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Group> criteriaQuerySearch = criteriaBuilder.createQuery(Group.class);
        Root<Group> from = criteriaQuerySearch.from(Group.class);
        CriteriaQuery<Group> select = criteriaQuerySearch.select(from).where(
                criteriaBuilder.like(from.get("name"), "%" + lowerSearch + "%"));
        TypedQuery<Group> searchQuery = session.createQuery(select);
        searchQuery.setFirstResult(page == 1 ? 0 : page * SEARCH_RESULT_PER_PAGE - SEARCH_RESULT_PER_PAGE);
        searchQuery.setMaxResults(SEARCH_RESULT_PER_PAGE);
        return searchQuery.getResultList();
    }

    public long searchByStringCount(String search) {
        logger.info("In searchByStringCount method. Search string: " + search);
        String lowerSearch = search.toLowerCase();
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Group> from = criteriaQuery.from(Group.class);
        criteriaQuery.select(criteriaBuilder.count(from));
        criteriaQuery.where(criteriaBuilder.like(from.get("name"), "%" + lowerSearch + "%"));
        return session.createQuery(criteriaQuery).getSingleResult();
    }


    public int getId(String name) {
        logger.info("In getId method");
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Group> criteriaQuery = criteriaBuilder.createQuery(Group.class);
        Root<Group> from = criteriaQuery.from(Group.class);
        CriteriaQuery<Group> select = criteriaQuery.select(from).where(criteriaBuilder.equal(from.get("name"), name));
        return session.createQuery(select).getSingleResult().getId();
    }

    public byte[] getPhoto(int id) {
        logger.info("In getPhoto method");
        Session session = sessionFactory.getCurrentSession();
        Group group = session.get(Group.class, id);
        return group.getPhoto();
    }

    public GroupRole getRoleMemberInGroup(int groupId, int memberId) {
        logger.info("In getRoleMemberInGroup method");
        Session session = sessionFactory.getCurrentSession();
        Group group = session.get(Group.class, groupId);
        for (AccountInGroup accountInGroup : group.getAccounts()) {
            if (accountInGroup.getUserMemberId() == memberId) {
                return accountInGroup.getRole();
            }
        }
        return GroupRole.UNKNOWN;
    }

    public GroupStatus getStatusMemberInGroup(int groupId, int memberId) {
        logger.info("In getStatusMemberInGroup method");
        Session session = sessionFactory.getCurrentSession();
        Group group = session.get(Group.class, groupId);
        for (AccountInGroup accountInGroup : group.getAccounts()) {
            if (accountInGroup.getUserMemberId() == memberId) {
                return accountInGroup.getStatus();
            }
        }
        return GroupStatus.UNKNOWN;
    }

    @Transactional
    public boolean update(Group group) {
        logger.info("In update method");
        Session session = sessionFactory.getCurrentSession();
        Group currentGroup = session.get(Group.class, group.getId());
        group.setAccounts(currentGroup.getAccounts());
        session.merge(group);
        return true;
    }

    @Transactional
    public boolean addPendingMemberToGroup(int idGroup, int idNewMember) {
        logger.info("In addPendingMemberToGroup method");
        Session session = sessionFactory.getCurrentSession();
        Group currentGroup = session.get(Group.class, idGroup);
        List<AccountInGroup> accounts = currentGroup.getAccounts();
        accounts.add(new AccountInGroup(idNewMember, GroupRole.USER, GroupStatus.PENDING));
        currentGroup.setAccounts(accounts);
        session.merge(currentGroup);
        return true;
    }

    @Transactional
    public boolean setStatusMemberInGroup(int idGroup, int member, GroupStatus status) {
        logger.info("In setStatusMemberInGroup method");
        Session session = sessionFactory.getCurrentSession();
        Group currentGroup = session.get(Group.class, idGroup);
        List<AccountInGroup> accounts = currentGroup.getAccounts();
        for (AccountInGroup accountInGroup : accounts) {
            if (accountInGroup.getUserMemberId() == member) {
                accountInGroup.setStatus(status);
                break;
            }
        }
        currentGroup.setAccounts(accounts);
        session.merge(currentGroup);
        return true;
    }

    @Transactional
    public boolean setRoleMemberInGroup(int idGroup, int member, GroupRole role) {
        logger.info("In setRoleMemberInGroup method");
        Session session = sessionFactory.getCurrentSession();
        Group currentGroup = session.get(Group.class, idGroup);
        List<AccountInGroup> accounts = currentGroup.getAccounts();
        for (AccountInGroup accountInGroup : accounts) {
            if (accountInGroup.getUserMemberId() == member) {
                accountInGroup.setRole(role);
                break;
            }
        }
        currentGroup.setAccounts(accounts);
        session.merge(currentGroup);
        return true;
    }

    @Transactional
    public boolean removeMemberFromGroup(int idGroup, int idMemberToDelete) {
        logger.info("In removeMemberFromGroup method");
        Session session = sessionFactory.getCurrentSession();
        Group currentGroup = session.get(Group.class, idGroup);
        List<AccountInGroup> accounts = currentGroup.getAccounts();
        for (AccountInGroup accountInGroup : accounts) {
            if (accountInGroup.getUserMemberId() == idMemberToDelete) {
                accounts.remove(accountInGroup);
                break;
            }
        }
        currentGroup.setAccounts(accounts);
        session.merge(currentGroup);
        return true;
    }

    @Transactional
    public boolean remove(int idGroup) {
        logger.info("In remove method");
        Session session = sessionFactory.getCurrentSession();
        Group group = session.get(Group.class, idGroup);
        session.remove(group);
        return true;
    }
}