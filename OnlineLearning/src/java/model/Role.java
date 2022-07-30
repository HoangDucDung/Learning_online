
package model;

public class Role {
    
    public static String ADMIN = "ADMIN";
    public static String EXPERT = "EXPERT";
    public static String CUSTOMER = "CUSTOMER";
    public static String SALE = "SALE";
    public static String MARKETING = "MARKETING";
    
    private int id;
    private String name;

    public Role() {
    }

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
