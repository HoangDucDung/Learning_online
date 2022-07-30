package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Account {

    private int accountID;
    private String firstName;
    private String lastName;
    private String email;
    private String profilePictureUrl;
    private Role role;
    private BigDecimal balance;
    private Timestamp createdTime;
    private Timestamp modifiedTime;
    private String phone;
    private String address;
    private Gender gender;
    private String password;
    private Boolean status;

    public Account() {
    }

    public Account(int accountId, String firstName, String lastName, String email, String profilePictureUrl, Role role, BigDecimal balance, Timestamp createdTime, Timestamp modifiedTime, String phone, String address, Gender gender, String password) {
        this.accountID = accountId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.profilePictureUrl = profilePictureUrl;
        this.role = role;
        this.balance = balance;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.password = password;
    }

    public Account(int accountID, String firstName, String lastName, String email, String profilePictureUrl, Role role, BigDecimal balance, Timestamp createdTime, Timestamp modifiedTime, String phone, String address, Gender gender, String password, Boolean status) {
        this.accountID = accountID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.profilePictureUrl = profilePictureUrl;
        this.role = role;
        this.balance = balance;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.password = password;
        this.status = status;
    }

    
    
    @Deprecated
    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Timestamp modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return this.accountID;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    
    
    @Override
    public String toString() {
        return "Account{" + "accountID=" + accountID + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", profilePictureUrl=" + profilePictureUrl + ", role=" + role + ", balance=" + balance + ", createdTime=" + createdTime + ", modifiedTime=" + modifiedTime + ", phone=" + phone + ", address=" + address + ", gender=" + gender + ", password=" + password + '}';
    }
    
}
