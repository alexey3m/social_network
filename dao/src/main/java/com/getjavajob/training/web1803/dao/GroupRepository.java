package com.getjavajob.training.web1803.dao;

import com.getjavajob.training.web1803.common.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface GroupRepository extends JpaRepository<Group, Integer>, JpaSpecificationExecutor<Group> {

    boolean existsByName(String name);

    Group findByName(String name);

    Page<Group> findByNameIgnoreCaseContaining(String name, Pageable pageable);

    long countByNameIgnoreCaseContaining(String name);
}