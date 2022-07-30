
package model;

import java.math.BigDecimal;

public class CoursePricePackage {
    private int priceId;
    private String name;
    private int accessDuration;
    private boolean status;
    private BigDecimal listPrice;
    private BigDecimal salePrice;
    private Course courseId;

    public CoursePricePackage() {
    }

    public CoursePricePackage(int priceId, String name, int accessDuration, boolean status, BigDecimal listPrice, BigDecimal salePrice, Course courseId) {
        this.priceId = priceId;
        this.name = name;
        this.accessDuration = accessDuration;
        this.status = status;
        this.listPrice = listPrice;
        this.salePrice = salePrice;
        this.courseId = courseId;
    }

    public int getPriceId() {
        return priceId;
    }

    public void setPriceId(int priceId) {
        this.priceId = priceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAccessDuration() {
        return accessDuration;
    }

    public void setAccessDuration(int accessDuration) {
        this.accessDuration = accessDuration;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public Course getCourseId() {
        return courseId;
    }

    public void setCourseId(Course courseId) {
        this.courseId = courseId;
    }
    
}
