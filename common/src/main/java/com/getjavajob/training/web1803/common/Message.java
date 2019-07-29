package com.getjavajob.training.web1803.common;

import com.getjavajob.training.web1803.common.enums.MessageType;
import lombok.Value;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.EnumType.STRING;

@Value
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id", nullable = false)
    private Integer id;
    @Column(name = "assign_id", nullable = false)
    private Integer assignId;
    @Enumerated(STRING)
    @Column(name = "type", nullable = false)
    private MessageType type;
    private byte[] photo;
    private String text;
    @Column(name = "date_create", nullable = false)
    private String dateCreate;
    @Column(name = "user_creator_id", nullable = false)
    private Integer userCreatorId;

}