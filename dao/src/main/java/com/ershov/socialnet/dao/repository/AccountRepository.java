package com.ershov.socialnet.dao.repository;

import com.ershov.socialnet.common.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer>, CustomAccountRepository {

    Optional<Account> findByEmail(String email);
}
