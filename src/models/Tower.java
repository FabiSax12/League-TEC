package models;

public class Tower extends Entity {
    public Tower(){
        this.health = 2000;
        this.damage = 25;
    }

    public void attack(Character target) {
        target.setHealth(target.getHealth() - damage);
    }
}
