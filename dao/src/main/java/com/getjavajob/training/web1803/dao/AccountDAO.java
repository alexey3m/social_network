package com.getjavajob.training.web1803.dao;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.common.enums.Role;
import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
@Transactional
public class AccountDAO {
    private static final Logger logger = LoggerFactory.getLogger(AccountDAO.class);
    private static final int SEARCH_RESULT_PER_PAGE = 5;

    private EntityManager entityManager;

    @Autowired
    public AccountDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public AccountDAO() {
    }

    @Transactional
    public boolean create(Account account) throws DaoNameException {
        logger.info("In create method");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQueryCheckEmail = criteriaBuilder.createQuery(Account.class);
        Root<Account> from = criteriaQueryCheckEmail.from(Account.class);
        CriteriaQuery<Account> selectEmail = criteriaQueryCheckEmail.select(from).where(
                criteriaBuilder.equal(from.get("email"), account.getEmail()));
        boolean emailNotExist = entityManager.createQuery(selectEmail).getResultList().isEmpty();
        if (emailNotExist) {
            entityManager.persist(account);
            logger.info("new account created.");
            return true;
        } else {
            logger.warn("Email exists. Thrown exception - " + DaoNameException.class);
            throw new DaoNameException("Email \"" + account.getEmail() + "\" is already used.");
        }
    }

    public Account get(int id) {
        logger.info("In get method");
        return entityManager.find(Account.class, id);
    }

    public byte[] getPhoto(int id) {
        logger.info("In getPhoto method");
        return entityManager.find(Account.class, id).getPhoto();
    }

    public int loginAndGetId(String email, String password) throws DaoNameException {
        logger.info("In loginAndGetId method");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQueryLogin = criteriaBuilder.createQuery(Account.class);
        Root<Account> from = criteriaQueryLogin.from(Account.class);
        CriteriaQuery<Account> selectOnEmail = criteriaQueryLogin.select(from).where(criteriaBuilder.and(
                criteriaBuilder.equal(from.get("email"), email),
                criteriaBuilder.equal(from.get("password"), password)));
        Account account;
        try {
            account = entityManager.createQuery(selectOnEmail).getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Login fails. Thrown exception - " + DaoNameException.class);
            throw new DaoNameException("Email: \"" + email + "\" and password: " + password + " not found in database.");
        }
        return account.getId();
    }

    public Account getByEmail(String email) throws DaoNameException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQueryLogin = criteriaBuilder.createQuery(Account.class);
        Root<Account> from = criteriaQueryLogin.from(Account.class);
        CriteriaQuery<Account> selectOnEmail = criteriaQueryLogin.select(from).where(
                criteriaBuilder.equal(from.get("email"), email));
        try {
            return entityManager.createQuery(selectOnEmail).getSingleResult();
        } catch (NoResultException e) {
            logger.warn("Login fails. Thrown exception - ", e);
            throw new DaoNameException("Email: \"" + email + "\" not found in database.");
        }
    }

    public Role getRole(int accountId) {
        logger.info("In getRole method");
        return entityManager.find(Account.class, accountId).getRole();
    }

    public List<Account> searchByString(String search, int page) {
        logger.info("In searchByString method. Search string: " + search);
        String lowerSearch = search.toLowerCase();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
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
        CriteriaQuery<Account> select = criteriaQuerySearch.select(from).where(whereClause);
        TypedQuery<Account> searchQuery = entityManager.createQuery(select);
        searchQuery.setFirstResult(page == 1 ? 0 : page * SEARCH_RESULT_PER_PAGE - SEARCH_RESULT_PER_PAGE);
        searchQuery.setMaxResults(SEARCH_RESULT_PER_PAGE);
        return searchQuery.getResultList();
    }

    public long searchByStringCount(String search) {
        logger.info("In searchByStringCount method. Search string: " + search);
        String lowerSearch = search.toLowerCase();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Account> from = criteriaQuery.from(Account.class);
        criteriaQuery.select(criteriaBuilder.count(from));
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
        criteriaQuery.where(whereClause);
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Transactional
    public boolean update(Account account) {
        logger.info("In update method");
        entityManager.merge(account);
        return true;
    }

    @Transactional
    public boolean updateRole(int accountId, Role newRole) {
        logger.info("In updateRole method");
        Account account = entityManager.find(Account.class, accountId);
        account.setRole(newRole);
        entityManager.merge(account);
        return true;
    }

    @Transactional
    public boolean remove(int id) {
        logger.info("In remove method");
        Account account = entityManager.find(Account.class, id);
        entityManager.remove(account);
        return true;
    }
}