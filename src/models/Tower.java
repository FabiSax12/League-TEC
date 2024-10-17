package models;

public class Tower extends Entity {
    private int team;
    public Tower(){
        this.health = 10000;
        this.damage = 75;
        this.spritePath = "/assets/tower.jpg";
        this.team=0;
    }

    public void attack(Character target) {

    }
    @Override
    public String toString() {
        return "Torre";
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }
}
