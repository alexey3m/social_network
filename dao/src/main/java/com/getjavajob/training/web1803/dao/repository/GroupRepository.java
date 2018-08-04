package com.getjavajob.training.web1803.dao.repository;

import com.getjavajob.training.web1803.common.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GroupRepository extends CrudRepository<Group, Integer>, PagingAndSortingRepository<Group, Integer>,
        JpaSpecificationExecutor<Group> {

    boolean existsByName(String name);

    Group findByName(String name);

    Page<Group> findByNameIgnoreCaseContaining(String name, Pageable pageable);

    long countByNameIgnoreCaseContaining(String name);
}