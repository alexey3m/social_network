package com.getjavajob.training.web1803.common;

import com.getjavajob.training.web1803.common.enums.Status;
import lombok.Value;

import javax.persistence.*;

import java.util.Objects;

import static javax.persistence.EnumType.STRING;

@Value
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
