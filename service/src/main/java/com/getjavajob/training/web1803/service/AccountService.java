package com.getjavajob.training.web1803.service;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.common.enums.Role;
import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;
import com.getjavajob.training.web1803.dao.repository.AccountRepository;
import com.getjavajob.training.web1803.dao.repository.specifications.AccountSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    private static final int SEARCH_RESULT_PER_PAGE = 5;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void create(Account account) throws DaoNameException {
//        String birthday = account.getBirthday();
//        String icq = account.getIcq();
//        String skype = account.getSkype();
//        account.setBirthday(birthday.length() == 0 ? null : birthday);
//        account.setIcq(icq.length() == 0 ? null : icq);
//        account.setSkype(skype.length() == 0 ? null : skype);

        if (accountRepository.findByEmail(account.getEmail()).isPresent()) {
            throw new DaoNameException();
        }
        accountRepository.save(account);
    }

    public Account get(int id) {
        return accountRepository.getOne(id);
    }

    public byte[] getPhoto(int id) {
        byte[] photo = accountRepository.getOne(id).getPhoto();
        if (photo != null) {
            return photo.length == 0 ? null : photo;
        } else {
            return null;
        }
    }

    public Account getByEmail(String email) throws DaoNameException {
        return accountRepository.findByEmail(email).orElseThrow(DaoNameException::new);
    }

    public List<Account> searchByStringPages(String search, int page) {
        Page<Account> accounts = accountRepository.findBySpecificationAndPage(
                AccountSpecification.searchByAllPartsName(search),
                PageRequest.of(page, SEARCH_RESULT_PER_PAGE));
        return accounts.stream().collect(Collectors.toList());
    }

    public long searchByStringCount(String search) {
        return accountRepository.countFindBySpecification(AccountSpecification.searchByAllPartsName(search));
    }

    public Role getRole(int id) {
        return accountRepository.getOne(id).getRole();
    }

    @Transactional
    public void update(Account account) {
        Account currentAccount = accountRepository.getOne(account.getId());
        String birthday = account.getBirthday();
        String icq = account.getIcq();
        String skype = account.getSkype();
        account.setBirthday(birthday.length() == 0 ? null : birthday);
        account.setIcq(icq.length() == 0 ? null : icq);
        account.setSkype(skype.length() == 0 ? null : skype);
        account.setRole(currentAccount.getRole());
        account.setRegDate(currentAccount.getRegDate());

        accountRepository.save(account);
    }

    @Transactional
    public void updateRole(int id, Role role) {
        Account account = accountRepository.getOne(id);
        account.setRole(role);

        accountRepository.save(account);
    }

    @Transactional
    public void remove(int id) {
        accountRepository.delete(accountRepository.getOne(id));
    }
}