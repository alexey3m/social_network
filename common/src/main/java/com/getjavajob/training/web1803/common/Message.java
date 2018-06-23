package com.getjavajob.training.web1803.common;

import java.util.Objects;

public class Message {
    private int id;
    private int assignId;
    private MessageType type;
    private byte[] photo;
    private String photoFileName;
    private String text;
    private String createDate;
    private int userCreatorId;

    public Message(int id, int assignId, MessageType type, byte[] photo, String photoFileName, String text,
                   String createDate, int userCreatorId) {
        this.id = id;
        this.assignId = assignId;
        this.type = type;
        this.photo = photo;
        this.photoFileName = photoFileName;
        this.text = text;
        this.createDate = createDate;
        this.userCreatorId = userCreatorId;
    }

    public Message() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAssignId() {
        return assignId;
    }

    public void setAssignId(int assignId) {
        this.assignId = assignId;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoFileName() {
        return photoFileName;
    }

    public void setPhotoFileName(String photoFileName) {
        this.photoFileName = photoFileName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getUserCreatorId() {
        return userCreatorId;
    }

    public void setUserCreatorId(int userCreatorId) {
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
                ", photoFileName='" + photoFileName + '\'' +
                ", text='" + text + '\'' +
                ", createDate='" + createDate + '\'' +
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
                Objects.equals(photoFileName, message.photoFileName) &&
                Objects.equals(text, message.text) &&
                Objects.equals(createDate, message.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, assignId, type, photoFileName, text, createDate, userCreatorId);
    }
}