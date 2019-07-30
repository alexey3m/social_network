package com.getjavajob.training.web1803.dao.repository;

import com.getjavajob.training.web1803.common.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface CustomAccountRepository {

    Page<Account> findBySpecificationAndPage(Specification<Account> specification, Pageable pageable);

    long countFindBySpecification(Specification<Account> specification);
}
