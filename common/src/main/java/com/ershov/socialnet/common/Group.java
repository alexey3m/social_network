package com.ershov.socialnet.common;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
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
    private List<AccountInGroup> accounts;

}