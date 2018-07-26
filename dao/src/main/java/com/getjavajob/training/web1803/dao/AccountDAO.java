package com.getjavajob.training.web1803.dao;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.common.enums.Role;
import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class AccountDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public AccountDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public AccountDAO() {
    }

    public boolean create(Account account) throws DaoNameException {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQueryCheckEmail = criteriaBuilder.createQuery(Account.class);
        Root<Account> from = criteriaQueryCheckEmail.from(Account.class);
        CriteriaQuery<Account> selectEmail = criteriaQueryCheckEmail.select(from).where(criteriaBuilder.equal(from.get("email"), account.getEmail()));
        boolean emailNotExist = session.createQuery(selectEmail).getResultList().isEmpty();
        if (emailNotExist) {
            session.persist(account);
            return true;
        } else {
            throw new DaoNameException("Email \"" + account.getEmail() + "\" is already used.");
        }
    }

    public Account get(int id) {
        return sessionFactory.getCurrentSession().get(Account.class, id);
    }

    public byte[] getPhoto(int id) {
        return sessionFactory.getCurrentSession().get(Account.class, id).getPhoto();
    }

    public int loginAndGetId(String email, String password) throws DaoNameException {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQueryLogin = criteriaBuilder.createQuery(Account.class);
        Root<Account> from = criteriaQueryLogin.from(Account.class);
        CriteriaQuery<Account> selectOnEmail = criteriaQueryLogin.select(from).where(criteriaBuilder.and(
                criteriaBuilder.equal(from.get("email"), email),
                criteriaBuilder.equal(from.get("password"), password)));
        Account account;
        try {
            account = session.createQuery(selectOnEmail).getSingleResult();
        } catch (NoResultException e) {
            throw new DaoNameException("Email: \"" + email + "\" and password: " + password + " not found in database.");
        }
        return account.getId();
    }

    public Role getRole(int accountId) {
        return sessionFactory.getCurrentSession().get(Account.class, accountId).getRole();
    }

    public List<Account> searchByString(String search) {
        String lowerSearch = search.toLowerCase();
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQuerySearch = criteriaBuilder.createQuery(Account.class);
        Root<Account> from = criteriaQuerySearch.from(Account.class);
        Expression<String> exp1 = criteriaBuilder.concat(from.get("firstName"), " ");
        exp1 = criteriaBuilder.concat(exp1, from.get("lastName"));
        exp1 = criteriaBuilder.concat(exp1, " ");
        exp1 = criteriaBuilder.concat(exp1, from.get("middleName"));
        Expression<String> exp2 = criteriaBuilder.concat(from.get("firstName"), " ");
        exp2 = criteriaBuilder.concat(exp2, from.get("middleName"));
        exp2 = criteriaBuilder.concat(exp2, " ");
        exp2 = criteriaBuilder.concat(exp2, from.get("lastName"));
        Predicate whereClause = criteriaBuilder.or(
                criteriaBuilder.like(exp1, "%" + lowerSearch + "%"),
                criteriaBuilder.like(exp2, "%" + lowerSearch + "%"));
        CriteriaQuery<Account> selectEmail = criteriaQuerySearch.select(from).where(whereClause);
        return session.createQuery(selectEmail).getResultList();
    }

    @Transactional
    public boolean update(Account account) {
        sessionFactory.getCurrentSession().merge(account);
        return true;
    }

    @Transactional
    public boolean updateRole(int accountId, Role newRole) {
        Session session = sessionFactory.getCurrentSession();
        Account account = session.find(Account.class, accountId);
        account.setRole(newRole);
        session.merge(account);
        return true;
    }

    @Transactional
    public boolean remove(int id) {
        Session session = sessionFactory.getCurrentSession();
        Account account = session.find(Account.class, id);
        session.remove(account);
        return true;
    }
}