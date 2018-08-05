package com.getjavajob.training.web1803.common;

import com.getjavajob.training.web1803.common.enums.PhoneType;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Objects;

import static javax.persistence.EnumType.STRING;

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

    public Phone(Integer phoneId, String number, PhoneType phoneType) {
        this.phoneId = phoneId;
        this.number = number;
        this.phoneType = phoneType;
    }

    public Phone() {
    }

    public Integer getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Integer phoneId) {
        this.phoneId = phoneId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public PhoneType getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(PhoneType phoneType) {
        this.phoneType = phoneType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return phoneId == phone.phoneId && Objects.equals(number, phone.number) &&
                phoneType == phone.phoneType;
    }

    @Override
    public int hashCode() {

        return Objects.hash(phoneId, number, phoneType);
    }

    @Override
    public String toString() {
        return "Phone{" +
                "phoneId=" + phoneId +
                ", number='" + number + '\'' +
                ", phoneType=" + phoneType +
                '}';
    }
}
