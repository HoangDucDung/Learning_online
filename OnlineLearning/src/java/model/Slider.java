package model;

public class Slider {

    private int sliderID;
    private String title;
    private String subTitle;
    private String description;
    private String navigationLink;
    private String imageUrl;
    private int order;
    private boolean status;

    public Slider() {
    }

    public Slider(int sliderID, String title, String subTitle, String description, String navigationLink, String imageUrl, int order, boolean status) {
        this.sliderID = sliderID;
        this.title = title;
        this.subTitle = subTitle;
        this.description = description;
        this.navigationLink = navigationLink;
        this.imageUrl = imageUrl;
        this.order = order;
        this.status = status;
    }

    public int getSliderID() {
        return sliderID;
    }

    public void setSliderID(int sliderID) {
        this.sliderID = sliderID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNavigationLink() {
        return navigationLink;
    }

    public void setNavigationLink(String navigationLink) {
        this.navigationLink = navigationLink;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
