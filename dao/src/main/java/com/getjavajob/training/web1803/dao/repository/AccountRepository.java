package com.getjavajob.training.web1803.dao.repository;

import com.getjavajob.training.web1803.common.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer>, CustomAccountRepository {

    Optional<Account> findByEmail(String email);
}
