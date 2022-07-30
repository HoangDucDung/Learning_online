package model;

public class DimensionDetail {

    private int numberCorrect;
    private String dimensionName;
    private int dimensionID;
    private int number;
    private int typeID;

    public DimensionDetail() {
    }

    public DimensionDetail(int numberCorrect, String dimensionName, int dimensionID, int number, int typeID) {
        this.numberCorrect = numberCorrect;
        this.dimensionName = dimensionName;
        this.dimensionID = dimensionID;
        this.number = number;
        this.typeID = typeID;
    }

    public int getNumberCorrect() {
        return numberCorrect;
    }

    public void setNumberCorrect(int numberCorrect) {
        this.numberCorrect = numberCorrect;
    }

    public String getDimensionName() {
        return dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public int getDimensionID() {
        return dimensionID;
    }

    public void setDimensionID(int dimensionID) {
        this.dimensionID = dimensionID;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    @Override
    public String toString() {
        return "DimensionDetail{" + "numberCorrect=" + numberCorrect + ", dimensionName=" + dimensionName + ", dimensionID=" + dimensionID + ", number=" + number + ", typeID=" + typeID + '}';
    }

}
