package com.getjavajob.training.web1803.common;

import com.getjavajob.training.web1803.common.enums.PhoneType;
import lombok.AllArgsConstructor;
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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

import static javax.persistence.EnumType.STRING;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
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
