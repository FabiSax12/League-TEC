package models;

public class Tower extends Entity {
    public Tower(){
        this.health = 10000;
        this.damage = 25;
    }

    public void attack(Character target) {

    }
    @Override
    public String toString() {
        return "Torre";
    }
}
