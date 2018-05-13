public class Account {
    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String middleName;
    private String birthday;
    private String phonePers;
    private String phoneWork;
    private String addressPers;
    private String addressWork;
    private String email;
    private int icq;
    private String skype;
    private String extra;

    public Account(int id, String username, String password, String firstName, String lastName, String middleName, String birthday,
                   String phonePers, String phoneWork, String addressPers, String addressWork, String email, int icq, String skype, String extra) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthday = birthday;
        this.phonePers = phonePers;
        this.phoneWork = phoneWork;
        this.addressPers = addressPers;
        this.addressWork = addressWork;
        this.email = email;
        this.icq = icq;
        this.skype = skype;
        this.extra = extra;
    }

    public Account() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPhonePers() {
        return phonePers;
    }

    public void setPhonePers(String phonePers) {
        this.phonePers = phonePers;
    }

    public String getPhoneWork() {
        return phoneWork;
    }

    public void setPhoneWork(String phoneWork) {
        this.phoneWork = phoneWork;
    }

    public String getAddressPers() {
        return addressPers;
    }

    public void setAddressPers(String addressPers) {
        this.addressPers = addressPers;
    }

    public String getAddressWork() {
        return addressWork;
    }

    public void setAddressWork(String addressWork) {
        this.addressWork = addressWork;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIcq() {
        return icq;
    }

    public void setIcq(int icq) {
        this.icq = icq;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", birthday='" + birthday + '\'' +
                ", phonePers='" + phonePers + '\'' +
                ", phoneWork='" + phoneWork + '\'' +
                ", addressPers='" + addressPers + '\'' +
                ", addressWork='" + addressWork + '\'' +
                ", email='" + email + '\'' +
                ", icq=" + icq +
                ", skype='" + skype + '\'' +
                ", extra='" + extra + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (id != account.id) return false;
        if (icq != account.icq) return false;
        if (!username.equals(account.username)) return false;
        if (firstName != null ? !firstName.equals(account.firstName) : account.firstName != null) return false;
        if (lastName != null ? !lastName.equals(account.lastName) : account.lastName != null) return false;
        if (middleName != null ? !middleName.equals(account.middleName) : account.middleName != null) return false;
        if (birthday != null ? !birthday.equals(account.birthday) : account.birthday != null) return false;
        if (phonePers != null ? !phonePers.equals(account.phonePers) : account.phonePers != null) return false;
        if (phoneWork != null ? !phoneWork.equals(account.phoneWork) : account.phoneWork != null) return false;
        if (addressPers != null ? !addressPers.equals(account.addressPers) : account.addressPers != null) return false;
        if (addressWork != null ? !addressWork.equals(account.addressWork) : account.addressWork != null) return false;
        if (email != null ? !email.equals(account.email) : account.email != null) return false;
        if (skype != null ? !skype.equals(account.skype) : account.skype != null) return false;
        return extra != null ? extra.equals(account.extra) : account.extra == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + username.hashCode();
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (phonePers != null ? phonePers.hashCode() : 0);
        result = 31 * result + (phoneWork != null ? phoneWork.hashCode() : 0);
        result = 31 * result + (addressPers != null ? addressPers.hashCode() : 0);
        result = 31 * result + (addressWork != null ? addressWork.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + icq;
        result = 31 * result + (skype != null ? skype.hashCode() : 0);
        result = 31 * result + (extra != null ? extra.hashCode() : 0);
        return result;
    }
}