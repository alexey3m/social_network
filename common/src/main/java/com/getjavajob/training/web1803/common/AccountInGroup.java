package com.getjavajob.training.web1803.common;

import com.getjavajob.training.web1803.common.enums.GroupRole;
import com.getjavajob.training.web1803.common.enums.GroupStatus;
import lombok.Value;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import static javax.persistence.EnumType.STRING;

@Value
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
}
