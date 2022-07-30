package model;

import java.math.BigDecimal;
import java.sql.Date;

public class TransactionHistory {

    private int id;
    private Account accountID;
    private Course courseID;
    private BigDecimal amount;
    private Date transactionTime;
    private CoursePricePackage coursePackageID;
    private Account saleID;
    private Account updatedBySaleID;
    private String note;
    private Date validFrom;
    private Date validTo;

    public TransactionHistory() {
    }
    
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
    public Account getSaleID() {
        return saleID;
    }

    public void setSaleID(Account saleID) {
        this.saleID = saleID;
    }

    public Account getUpdatedBySaleID() {
        return updatedBySaleID;
    }

    public void setUpdatedBySaleID(Account updatedBySaleID) {
        this.updatedBySaleID = updatedBySaleID;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccountID() {
        return accountID;
    }

    public void setAccountID(Account accountID) {
        this.accountID = accountID;
    }

    public Course getCourseID() {
        return courseID;
    }

    public void setCourseID(Course courseID) {
        this.courseID = courseID;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public CoursePricePackage getCoursePackageID() {
        return coursePackageID;
    }

    public void setCoursePackageID(CoursePricePackage coursePackageID) {
        this.coursePackageID = coursePackageID;
    }

}
