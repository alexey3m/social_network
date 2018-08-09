package com.getjavajob.training.web1803.common;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "soc_group")
public class Group implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "group_id")
    private Integer id;
    private String name;
    private byte[] photo;
    @Column(name = "create_date")
    private String createDate;
    private String info;
    @Column(name = "user_creator_id")
    private Integer userCreatorId;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "group_id", nullable = false)
    private List<AccountInGroup> accounts = new ArrayList<>();

    public Group(int id, String name, byte[] photo, String createDate, String info, int userCreatorId, List<AccountInGroup> accounts) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.createDate = createDate;
        this.info = info;
        this.userCreatorId = userCreatorId;
        this.accounts = accounts;
    }

    public Group() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getUserCreatorId() {
        return userCreatorId;
    }

    public void setUserCreatorId(Integer userCreatorId) {
        this.userCreatorId = userCreatorId;
    }

    public List<AccountInGroup> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountInGroup> accounts) {
        this.accounts = accounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return id.equals(group.id) &&
                userCreatorId.equals(group.userCreatorId) &&
                Objects.equals(name, group.name) &&
                Arrays.equals(photo, group.photo) &&
                Objects.equals(createDate, group.createDate) &&
                Objects.equals(info, group.info) &&
                Objects.equals(accounts, group.accounts);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(id, name, createDate, info, userCreatorId, accounts);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
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
                ", accounts=" + accounts +
                '}';
    }
}