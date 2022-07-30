package model;

public class Setting {

    private int id;
    private String name;
    private int order;
    private Boolean status;
    private String type;
    private int settingID;

    public Setting() {
    }

    public Setting(int Id, String Name, int Order, Boolean Status, String type) {
        this.id = Id;
        this.name = Name;
        this.order = Order;
        this.status = Status;
        this.type = type;
    }

    public Setting(int Id, String Name, int Order, Boolean Status, String type, int SettingID) {
        this.id = Id;
        this.name = Name;
        this.order = Order;
        this.status = Status;
        this.type = type;
        this.settingID = SettingID;
    }

    public int getId() {
        return id;
    }

    public void setId(int Id) {
        this.id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int Order) {
        this.order = Order;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean Status) {
        this.status = Status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSettingID() {
        return settingID;
    }

    public void setSettingID(int SettingID) {
        this.settingID = SettingID;
    }

    @Override
    public String toString() {
        return "Setting{" + "Id=" + id + ", Name=" + name + ", Order=" + order + ", Status=" + status + ", type=" + type + '}';
    }

}
