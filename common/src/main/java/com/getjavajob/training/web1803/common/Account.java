package com.getjavajob.training.web1803.common;

import com.getjavajob.training.web1803.common.enums.Role;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.EnumType.STRING;

@Entity
@XmlRootElement(name = "account")
@XmlAccessorType(XmlAccessType.FIELD)
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", nullable = false)
    @XmlAttribute(name = "id")
    private int id;
    @Column(name = "email", nullable = false)
    @XmlElement(name = "email")
    private String email;
    @Column(name = "password", nullable = false)
    @XmlElement(name = "password")
    private String password;
    @Column(name = "first_name")
    @XmlElement(name = "firstName")
    private String firstName;
    @Column(name = "last_name")
    @XmlElement(name = "lastName")
    private String lastName;
    @Column(name = "middle_name")
    @XmlElement(name = "middleName")
    private String middleName;
    @XmlElement(name = "birthday")
    private String birthday;
    @XmlTransient
    private byte[] photo;
    @XmlElement(name = "skype")
    private String skype;
    @XmlElement(name = "icq")
    private int icq;
    @Column(name = "reg_date")
    @XmlElement(name = "regDate")
    private String regDate;
    @Enumerated(STRING)
    @XmlElement(name = "role")
    private Role role;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "account_id", nullable = false)
    @XmlElement(name = "phones")
    private List<Phone> phones = new ArrayList<>();

    public Account(int id, String email, String password, String firstName, String lastName, String middleName, String birthday,
                   byte[] photo, String skype, int icq, String regDate, Role role, List<Phone> phones) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthday = birthday;
        this.photo = photo;
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

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
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
                ", photo=" + photo +
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
                Objects.equals(skype, account.skype) &&
                Objects.equals(regDate, account.regDate) &&
                Objects.equals(phones, account.phones);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, email, password, firstName, lastName, middleName, birthday, skype, icq, regDate, role, phones);
    }
}