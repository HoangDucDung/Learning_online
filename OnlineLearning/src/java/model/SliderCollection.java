
package model;

import java.sql.Timestamp;

public class SliderCollection {

    private int sliderCollectionID;
    private String name;
    private Timestamp createdTime;
    private Timestamp updatedTime;
    private int order;
    private Account ownerID;
    private boolean status;

    public SliderCollection() {
    }

    public SliderCollection(int sliderCollectionID, String name, Timestamp createdTime, Timestamp updatedTime, int order, Account ownerID, boolean status) {
        this.sliderCollectionID = sliderCollectionID;
        this.name = name;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        this.order = order;
        this.ownerID = ownerID;
        this.status = status;
    }

    public int getSliderCollectionID() {
        return sliderCollectionID;
    }

    public void setSliderCollectionID(int sliderCollectionID) {
        this.sliderCollectionID = sliderCollectionID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Account getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(Account ownerID) {
        this.ownerID = ownerID;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
