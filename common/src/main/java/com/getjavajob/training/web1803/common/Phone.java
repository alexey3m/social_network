package com.getjavajob.training.web1803.common;

import com.getjavajob.training.web1803.common.enums.PhoneType;

import java.util.Objects;

public class Phone {
    private String number;
    private PhoneType phoneType;

    public Phone(String number, PhoneType phoneType) {
        this.number = number;
        this.phoneType = phoneType;
    }

    public Phone() {
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
        return Objects.equals(number, phone.number) &&
                phoneType == phone.phoneType;
    }

    @Override
    public int hashCode() {

        return Objects.hash(number, phoneType);
    }

    @Override
    public String toString() {
        return "Phone{" +
                "number='" + number + '\'' +
                ", phoneType=" + phoneType +
                '}';
    }
}
