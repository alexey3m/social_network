package com.getjavajob.training.web1803.dao;

import com.getjavajob.training.web1803.common.AccountInGroup;
import com.getjavajob.training.web1803.common.Group;
import com.getjavajob.training.web1803.common.enums.GroupRole;
import com.getjavajob.training.web1803.common.enums.GroupStatus;
import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.List;

@Repository
@Transactional
public class GroupDAO {
    private static final String SELECT_ALL_GROUPS = "SELECT * FROM soc_group";
    private static final String SELECT_GROUP_BY_NAME = SELECT_ALL_GROUPS + " WHERE name = ?";
    private static final String INSERT_GROUP = "INSERT INTO soc_group (name, photo, create_date, " +
            "info, user_creator_id) VALUES(?, ?, ?, ?, ?)";
    private static final String INSERT_ACCOUNT_IN_GROUP1 = "INSERT INTO account_in_group (group_id, user_member_id, role, status) " +
            "VALUES (?, ?, ?, ?)";
    private static final String INSERT_ACCOUNT_IN_GROUP = "INSERT INTO account_in_group (group_id, user_member_id, role, status) " +
            "VALUES (?, ?, ?, ?)";
    private static final String SELECT_GROUPS_ID_BY_NAME = "SELECT group_id FROM soc_group WHERE name = ?";
    private static final String SELECT_GROUPS_ID_BY_USER_MEMBER_ID = "SELECT * FROM soc_group WHERE group_id IN " +
            "(SELECT group_id FROM account_in_group WHERE user_member_id = ? AND status = 2)";
    private static final String SELECT_ROLE_MEMBER = "SELECT role FROM account_in_group WHERE group_id = ? AND user_member_id = ?";
    private static final String SELECT_STATUS_MEMBER = "SELECT status FROM account_in_group WHERE group_id = ? AND user_member_id = ?";
    private static final String COLUMN_GROUP_ID = "group_id";
    private static final String SELECT_GROUP_BY_ID = SELECT_ALL_GROUPS + " WHERE group_id = ?";
    private static final String SELECT_USER_MEMBER_ID_FROM_ACCOUNT_IN_GROUP = "SELECT * FROM account_in_group WHERE group_id = ?";
    private static final String UPDATE_GROUP = "UPDATE soc_group SET photo = ?, info = ? WHERE group_id = ?";
    private static final String UPDATE_STATUS_MEMBER = "UPDATE account_in_group SET status = ? WHERE group_id = ? AND user_member_id = ?";
    private static final String UPDATE_ROLE_MEMBER = "UPDATE account_in_group SET role = ? WHERE group_id = ? AND user_member_id = ?";
    private static final String REMOVE_ACCOUNT_IN_GROUP = "DELETE FROM account_in_group WHERE user_member_id = ? AND group_id = ?";
    private static final String SELECT_LAST_INSERT_ID = "SELECT LAST_INSERT_ID() AS id";
    private static final String REMOVE_GROUP = "DELETE FROM soc_group WHERE group_id = ?";
    private static final String SEARCH_GROUPS_BY_STRING = "SELECT * FROM soc_group WHERE LOWER(name) LIKE ?";
    private static final String SELECT_PHOTO_BY_ID = "SELECT photo FROM soc_group WHERE group_id = ?";


    private SessionFactory sessionFactory;

    @Autowired
    public GroupDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public GroupDAO() {
    }

    @Transactional
    public boolean create(Group group) throws DaoNameException {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Group> criteriaQueryCheckEmail = criteriaBuilder.createQuery(Group.class);
        Root<Group> from = criteriaQueryCheckEmail.from(Group.class);
        CriteriaQuery<Group> selectName = criteriaQueryCheckEmail.select(from).where(criteriaBuilder.equal(from.get("name"), group.getName()));
        boolean nameNotExist = session.createQuery(selectName).getResultList().isEmpty();
        if (nameNotExist) {
            session.persist(group);
            return true;
        } else {
            throw new DaoNameException("Group name \"" + group.getName() + "\" is already used.");
        }
    }

    public Group get(int groupId) {
        return sessionFactory.getCurrentSession().get(Group.class, groupId);
    }

    public List<Group> getAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Group> criteriaQuery = criteriaBuilder.createQuery(Group.class);
        Root<Group> from = criteriaQuery.from(Group.class);
        CriteriaQuery<Group> selectName = criteriaQuery.select(from);
        return session.createQuery(selectName).getResultList();
    }

    public List<Group> getAllByUserId(int userId) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Group> criteriaQuery = criteriaBuilder.createQuery(Group.class);
        Root<Group> from = criteriaQuery.from(Group.class);
        Join<Group, AccountInGroup> accountInGroupJoin = from.join("accounts", JoinType.INNER);
        criteriaQuery.select(from).distinct(true).where(criteriaBuilder.and(
                        criteriaBuilder.equal(accountInGroupJoin.get("userMemberId"), userId),
                        criteriaBuilder.equal(accountInGroupJoin.get("status"), GroupStatus.ACCEPTED)));
        List<Group> result = session.createQuery(criteriaQuery).getResultList();
        System.out.println("List<Group>: " + result);
        return result;
    }

    public List<Group> searchByString(String search) {
        String lowerSearch = search.toLowerCase();
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Group> criteriaQuerySearch = criteriaBuilder.createQuery(Group.class);
        Root<Group> from = criteriaQuerySearch.from(Group.class);
        CriteriaQuery<Group> selectEmail = criteriaQuerySearch.select(from).where(
                criteriaBuilder.like(from.get("name"), "%" + lowerSearch + "%"));
        return session.createQuery(selectEmail).getResultList();
    }

    public int getId(String name) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Group> criteriaQuery = criteriaBuilder.createQuery(Group.class);
        Root<Group> from = criteriaQuery.from(Group.class);
        CriteriaQuery<Group> select = criteriaQuery.select(from).where(criteriaBuilder.equal(from.get("name"), name));
        return session.createQuery(select).getSingleResult().getId();
    }

    public byte[] getPhoto(int id) {
        Session session = sessionFactory.getCurrentSession();
        Group group = session.get(Group.class, id);
        return group.getPhoto();
    }

    public GroupRole getRoleMemberInGroup(int groupId, int memberId) {
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
        Session session = sessionFactory.getCurrentSession();
        Group currentGroup = session.get(Group.class, group.getId());
        group.setAccounts(currentGroup.getAccounts());
        session.merge(group);
        return true;
    }

    @Transactional
    public boolean addPendingMemberToGroup(int idGroup, int idNewMember) {
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
        Session session = sessionFactory.getCurrentSession();
        Group currentGroup = session.get(Group.class, idGroup);
        List<AccountInGroup> accounts = currentGroup.getAccounts();
//        List<AccountInGroup> accountsResult = new ArrayList<>();
//        for (AccountInGroup accountInGroup : accounts) {
//            if (accountInGroup.getUserMemberId() != idMemberToDelete) {
//                accountsResult.add(accountInGroup);
//            }
//        }
//        currentGroup.setAccounts(accountsResult);

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
        Session session = sessionFactory.getCurrentSession();
        Group group = session.get(Group.class, idGroup);
        session.remove(group);
        return true;
    }
}