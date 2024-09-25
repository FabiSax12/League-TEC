package models;

public abstract class AAbility {
    String name;

    public AAbility(String name){
        this.name = name;
    }

    public abstract void use();
    public abstract void getName();
    public abstract int getManaCost();
}
