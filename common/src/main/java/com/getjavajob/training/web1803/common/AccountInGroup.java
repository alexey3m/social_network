package com.getjavajob.training.web1803.common;

import com.getjavajob.training.web1803.common.enums.GroupRole;
import com.getjavajob.training.web1803.common.enums.GroupStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import static javax.persistence.EnumType.STRING;

@Entity
@Table(name = "account_in_group")
public class AccountInGroup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "user_member_id", nullable = false)
    private Integer userMemberId;
    @Enumerated(STRING)
    @Column(name = "role", nullable = false)
    private GroupRole role;
    @Enumerated(STRING)
    @Column(name = "status", nullable = false)
    private GroupStatus status;

    public AccountInGroup(Integer userMemberId, GroupRole role, GroupStatus status) {
        this.userMemberId = userMemberId;
        this.role = role;
        this.status = status;
    }

    public AccountInGroup(Integer id, Integer userMemberId, GroupRole role, GroupStatus status) {
        this.id = id;
        this.userMemberId = userMemberId;
        this.role = role;
        this.status = status;
    }

    public AccountInGroup() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserMemberId() {
        return userMemberId;
    }

    public void setUserMemberId(Integer userMemberId) {
        this.userMemberId = userMemberId;
    }

    public GroupRole getRole() {
        return role;
    }

    public void setRole(GroupRole role) {
        this.role = role;
    }

    public GroupStatus getStatus() {
        return status;
    }

    public void setStatus(GroupStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountInGroup that = (AccountInGroup) o;
        return id.equals(that.id) &&
                userMemberId.equals(that.userMemberId) &&
                role == that.role &&
                status == that.status;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userMemberId, role, status);
    }

    @Override
    public String toString() {
        return "AccountInGroup{" +
                "id=" + id +
                ", userMemberId=" + userMemberId +
                ", role=" + role +
                ", status=" + status +
                '}';
    }
}
