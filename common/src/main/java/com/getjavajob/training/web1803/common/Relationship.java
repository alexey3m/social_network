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
    private int id;
    @Column(name = "user_one_id", nullable = false)
    private int userOneId;
    @Column(name = "user_two_id", nullable = false)
    private int userTwoId;
    @Enumerated(STRING)
    @Column(name = "status", nullable = false)
    private Status type;
    @Column(name = "action_user_id", nullable = false)
    private int actionUserId;

    public Relationship(int userOneId, int userTwoId, Status type, int actionUserId) {
        this.userOneId = userOneId;
        this.userTwoId = userTwoId;
        this.type = type;
        this.actionUserId = actionUserId;
    }

    public Relationship() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserOneId() {
        return userOneId;
    }

    public void setUserOneId(int userOneId) {
        this.userOneId = userOneId;
    }

    public int getUserTwoId() {
        return userTwoId;
    }

    public void setUserTwoId(int userTwoId) {
        this.userTwoId = userTwoId;
    }

    public Status getType() {
        return type;
    }

    public void setType(Status type) {
        this.type = type;
    }

    public int getActionUserId() {
        return actionUserId;
    }

    public void setActionUserId(int actionUserId) {
        this.actionUserId = actionUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relationship that = (Relationship) o;
        return id == that.id &&
                userOneId == that.userOneId &&
                userTwoId == that.userTwoId &&
                actionUserId == that.actionUserId &&
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
