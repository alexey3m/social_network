package com.getjavajob.training.web1803.dao.repository;

import com.getjavajob.training.web1803.common.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}
