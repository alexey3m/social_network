package com.ershov.socialnet.common;

import com.ershov.socialnet.common.enums.Status;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import static javax.persistence.EnumType.STRING;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
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
    private Status status;
    @Column(name = "action_user_id", nullable = false)
    private Integer actionUserId;

    public Relationship(Integer userOneId, Integer userTwoId, Status status, Integer actionUserId) {
        this.userOneId = userOneId;
        this.userTwoId = userTwoId;
        this.status = status;
        this.actionUserId = actionUserId;
    }
}
