package com.getjavajob.training.web1803.common;

import com.getjavajob.training.web1803.common.enums.Status;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
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
@AllArgsConstructor
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

}
