package models;

import java.util.ArrayList;

public class Character extends Entity {
    private final String name;
    private int level;
    private int mana;
    private int damage;
    private int defense;
    private int movements;
    private boolean dead;
    private final Element element;
    private ArrayList<ASkill> skills;

    public Character(String name, float health, int mana, int attack, Element element) {
        this.name = name;
        this.health = health;
        this.mana = mana;
        this.damage = attack;
        this.element = element;
        this.level = 1;
        this.defense = 100;
        this.movements = 1;
        this.skills = new ArrayList<>();
    }

    public void move() {

    }

    public void useSkill(ASkill skill) {

    }

    public void applyElementalBoost() {

    }

    public void addSkill(ASkill skill) {
        this.skills.add(skill);
    }

    public void removeSkill(ASkill skill) {}

    public void levelUp() {}

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

    public int getDamage() {
        return damage;
    }

    public int getDefense() {
        return defense;
    }

    public int getMovements() {
        return movements;
    }

    public Element getElement() {
        return element;
    }

    public boolean isDead() {return dead;}

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

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setDead(boolean dead) {this.dead = dead;}

    @Override
    public String toString() {
        return this.getName();
    }
}
