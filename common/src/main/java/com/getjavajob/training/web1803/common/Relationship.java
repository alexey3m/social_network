package com.getjavajob.training.web1803.common;

import com.getjavajob.training.web1803.common.enums.Status;

import javax.persistence.*;

import java.util.Objects;

import static javax.persistence.EnumType.STRING;

@Entity
public class Relationship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "user_one_id", nullable = false)
    private Integer userOneId;
    @Column(name = "user_two_id", nullable = false)
    private Integer userTwoId;
    @Enumerated(STRING)
    @Column(name = "status", nullable = false)
    private Status type;
    @Column(name = "action_user_id", nullable = false)
    private Integer actionUserId;

    public Relationship(Integer userOneId, Integer userTwoId, Status type, Integer actionUserId) {
        this.userOneId = userOneId;
        this.userTwoId = userTwoId;
        this.type = type;
        this.actionUserId = actionUserId;
    }

    public Relationship() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserOneId() {
        return userOneId;
    }

    public void setUserOneId(Integer userOneId) {
        this.userOneId = userOneId;
    }

    public Integer getUserTwoId() {
        return userTwoId;
    }

    public void setUserTwoId(Integer userTwoId) {
        this.userTwoId = userTwoId;
    }

    public Status getType() {
        return type;
    }

    public void setType(Status type) {
        this.type = type;
    }

    public Integer getActionUserId() {
        return actionUserId;
    }

    public void setActionUserId(Integer actionUserId) {
        this.actionUserId = actionUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relationship that = (Relationship) o;
        return id.equals(that.id) &&
                userOneId.equals(that.userOneId) &&
                userTwoId.equals(that.userTwoId) &&
                actionUserId.equals(that.actionUserId) &&
                type == that.type;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userOneId, userTwoId, type, actionUserId);
    }

    @Override
    public String toString() {
        return "Relationship{" +
                "id=" + id +
                ", userOneId=" + userOneId +
                ", userTwoId=" + userTwoId +
                ", type=" + type +
                ", actionUserId=" + actionUserId +
                '}';
    }
}
