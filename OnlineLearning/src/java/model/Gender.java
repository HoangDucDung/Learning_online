package model;

public enum Gender {
    MALE(true, "Male"),
    FEMALE(false, "Female");
    
    private final boolean flag;
    private final String name;
    
    private Gender(boolean f, String n) {
        flag = f;
        name = n;
    }

    @Override
    public String toString() {
        return name;
    }
    
    public boolean asBoolean() {
        return flag;
    }
    
    public static Gender of(boolean isMale) {
        return isMale ? MALE : FEMALE;
    }
    
    
}
