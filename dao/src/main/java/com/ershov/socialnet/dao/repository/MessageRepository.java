package com.ershov.socialnet.dao.repository;

import com.ershov.socialnet.common.Message;
import com.ershov.socialnet.common.enums.MessageType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> getAllByTypeAndAssignId(MessageType type, int assignId);

    List<Message> getAllByTypeAndUserCreatorId(MessageType type, int userCreatorId);

    List<Message> getAllByTypeAndUserCreatorIdAndAssignId(MessageType type, int userCreatorId, int assignId);
}
