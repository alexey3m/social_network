package com.getjavajob.training.web1803.common;

import java.util.Map;
import java.util.Objects;

public class Account {
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String middleName;
    private String birthday;
    private byte[] photo;
    private String photoFileName;
    private String skype;
    private int icq;
    private String regDate;
    private Role role;
    private Map<String, PhoneType> phones;

    public Account(int id, String email, String password, String firstName, String lastName, String middleName, String birthday,
                   byte[] photo, String photoFileName, String skype, int icq, String regDate, Role role, Map<String, PhoneType> phones) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthday = birthday;
        this.photo = photo;
        this.photoFileName = photoFileName;
        this.skype = skype;
        this.icq = icq;
        this.regDate = regDate;
        this.role = role;
        this.phones = phones;
    }

    public Account() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoFileName() {
        return photoFileName;
    }

    public void setPhotoFileName(String photoFileName) {
        this.photoFileName = photoFileName;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public int getIcq() {
        return icq;
    }

    public void setIcq(int icq) {
        this.icq = icq;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Map<String, PhoneType> getPhones() {
        return phones;
    }

    public void setPhones(Map<String, PhoneType> phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", birthday='" + birthday + '\'' +
                ", photoFileName='" + photoFileName + '\'' +
                ", skype='" + skype + '\'' +
                ", icq=" + icq +
                ", regDate='" + regDate + '\'' +
                ", role=" + role +
                ", phones=" + phones +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id &&
                icq == account.icq &&
                role == account.role &&
                Objects.equals(email, account.email) &&
                Objects.equals(password, account.password) &&
                Objects.equals(firstName, account.firstName) &&
                Objects.equals(lastName, account.lastName) &&
                Objects.equals(middleName, account.middleName) &&
                Objects.equals(birthday, account.birthday) &&
                Objects.equals(photoFileName, account.photoFileName) &&
                Objects.equals(skype, account.skype) &&
                Objects.equals(regDate, account.regDate) &&
                Objects.equals(phones, account.phones);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, email, password, firstName, lastName, middleName, birthday, photoFileName, skype, icq, regDate, role, phones);
    }
}