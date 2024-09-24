package models;

public class Character extends ACharacter {

    public Character(String name, int mana, Element element) {
        this.name = name;
        this.mana = mana;
        this.element = element;

        this.level = 1;
        this.hitPoints = 0;
        this.attackMin = 0;
        this.defenseMin = 0;
        this.attackMax = 100;
        this.defenseMax = 100;
        this.movements = 2;
    }

    @Override
    public void move() {

    }

    @Override
    public void attack() {

    }

    @Override
    public void defend() {

    }

    @Override
    public void useAbility() {

    }

    @Override
    public void addAbility() {

    }

    @Override
    public void applyElementalBoost() {

    }
}
