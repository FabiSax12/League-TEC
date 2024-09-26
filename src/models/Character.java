package models;

import java.util.ArrayList;

public class Character extends Entity {
    protected String name;
    protected int level;
    protected int mana;
    protected int attackMax;
    protected int defenseMax;
    protected int movements;
    protected Element element;
    protected ArrayList<ASkill> abilities;

    public Character(String name, float health, int mana, int attack, Element element) {
        this.name = name;
        this.health = health;
        this.mana = mana;
        this.attackMax = attack;
        this.element = element;
        this.level = 1;
        this.defenseMax = 100;
        this.movements = 1;
        this.abilities = new ArrayList<>();
    }

    public void move() {

    }

    public void useAbility() {

    }

    public void applyElementalBoost() {

    }

    public void addAbility(ASkill ability) {
        this.abilities.add(ability);
    }

    // Getters
    public String getName() {
        return name;
    }

    public float getHealth() {
        return health;
    }

    public int getLevel() {
        return level;
    }

    public int getMana() {
        return mana;
    }

    public int getAttackMax() {
        return attackMax;
    }

    public int getDefenseMax() {
        return defenseMax;
    }

    public int getMovements() {
        return movements;
    }

    public Element getElement() {
        return element;
    }

    // Setters
    public void setHealth(float health) {
        this.health = health;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setMovements(int movements) {
        this.movements = movements;
    }

    public void setAttackMax(int attackMax) {
        this.attackMax = attackMax;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
