package com.getjavajob.training.web1803.common;

import lombok.Value;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Value
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

}