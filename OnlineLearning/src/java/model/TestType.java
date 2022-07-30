package model;


public class TestType {
    private int testTypeID;
    private String testTypeName;
    private int order;
    private boolean status;
    private String type;

    public TestType() {
    }

    public TestType(int testTypeID, String testTypeName, int order, boolean status, String type) {
        this.testTypeID = testTypeID;
        this.testTypeName = testTypeName;
        this.order = order;
        this.status = status;
        this.type = type;
    }

    public int getTestTypeID() {
        return testTypeID;
    }

    public void setTestTypeID(int testTypeID) {
        this.testTypeID = testTypeID;
    }

    public String getTestTypeName() {
        return testTypeName;
    }

    public void setTestTypeName(String testTypeName) {
        this.testTypeName = testTypeName;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
    
}
