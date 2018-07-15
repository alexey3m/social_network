package com.getjavajob.training.web1803.common;

import java.util.List;
import java.util.Objects;

public class Group {
    private int id;
    private String name;
    private byte[] photo;
    private String createDate;
    private String info;
    private int userCreatorId;
    private List<Integer> acceptedMembersId;
    private List<Integer> pendingMembersId;
    private List<Integer> adminsId;

    public Group(int id, String name, byte[] photo, String createDate, String info, int userCreatorId,
                 List<Integer> acceptedMembersId, List<Integer> pendingMembersId, List<Integer> adminsId) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.createDate = createDate;
        this.info = info;
        this.userCreatorId = userCreatorId;
        this.acceptedMembersId = acceptedMembersId;
        this.pendingMembersId = pendingMembersId;
        this.adminsId = adminsId;
    }

    public Group() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getUserCreatorId() {
        return userCreatorId;
    }

    public void setUserCreatorId(int userCreatorId) {
        this.userCreatorId = userCreatorId;
    }

    public List<Integer> getAcceptedMembersId() {
        return acceptedMembersId;
    }

    public void setAcceptedMembersId(List<Integer> acceptedMembersId) {
        this.acceptedMembersId = acceptedMembersId;
    }

    public List<Integer> getAdminsId() {
        return adminsId;
    }

    public void setAdminsId(List<Integer> adminsId) {
        this.adminsId = adminsId;
    }

    public List<Integer> getPendingMembersId() {
        return pendingMembersId;
    }

    public void setPendingMembersId(List<Integer> pendingMembersId) {
        this.pendingMembersId = pendingMembersId;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", photo=" + photo +
                ", createDate='" + createDate + '\'' +
                ", info='" + info + '\'' +
                ", userCreatorId=" + userCreatorId +
                ", acceptedMembersId=" + acceptedMembersId +
                ", pendingMembersId=" + pendingMembersId +
                ", adminsId=" + adminsId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return id == group.id &&
                userCreatorId == group.userCreatorId &&
                Objects.equals(name, group.name) &&
                Objects.equals(createDate, group.createDate) &&
                Objects.equals(info, group.info) &&
                Objects.equals(acceptedMembersId, group.acceptedMembersId) &&
                Objects.equals(pendingMembersId, group.pendingMembersId) &&
                Objects.equals(adminsId, group.adminsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, createDate, info, userCreatorId, acceptedMembersId, pendingMembersId, adminsId);
    }
}