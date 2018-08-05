package com.getjavajob.training.web1803.common;

import com.getjavajob.training.web1803.common.enums.MessageType;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.EnumType.STRING;

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

    public Message(Integer id, Integer assignId, MessageType type, byte[] photo, String text, String dateCreate, Integer userCreatorId) {
        this.id = id;
        this.assignId = assignId;
        this.type = type;
        this.photo = photo;
        this.text = text;
        this.dateCreate = dateCreate;
        this.userCreatorId = userCreatorId;
    }

    public Message() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAssignId() {
        return assignId;
    }

    public void setAssignId(Integer assignId) {
        this.assignId = assignId;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String createDate) {
        this.dateCreate = createDate;
    }

    public Integer getUserCreatorId() {
        return userCreatorId;
    }

    public void setUserCreatorId(Integer userCreatorId) {
        this.userCreatorId = userCreatorId;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", assignId=" + assignId +
                ", type=" + type +
                ", text='" + text + '\'' +
                ", dateCreate='" + dateCreate + '\'' +
                ", userCreatorId=" + userCreatorId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id == message.id &&
                assignId == message.assignId &&
                userCreatorId == message.userCreatorId &&
                type == message.type &&
                Objects.equals(text, message.text) &&
                Objects.equals(dateCreate, message.dateCreate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, assignId, type, text, dateCreate, userCreatorId);
    }
}