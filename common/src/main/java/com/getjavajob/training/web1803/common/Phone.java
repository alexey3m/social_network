package com.getjavajob.training.web1803.common;

import com.getjavajob.training.web1803.common.enums.PhoneType;
import lombok.Value;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Objects;

import static javax.persistence.EnumType.STRING;

@Value
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Phone implements Serializable {

    @Id
    @Column(name = "phone_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Integer phoneId;
    @Column(name = "phone_number", nullable = false)
    private String number;
    @Column(name = "phone_type", nullable = false)
    @Enumerated(STRING)
    private PhoneType phoneType;

}
