package models;

public abstract class Entity {
    protected String spritePath;
    protected float maxHealth;
    protected float health;
    protected int damage;
    protected int defense;

    public void attack(Entity target, int damage) {
        target.takeDamage(damage);
    }

    public void takeDamage(int damage) {
        int totalDamage=damage - defense;
        if (totalDamage > 0) {
            this.health -= damage - defense;
        }
    }

    public float getHealth() {
        return health;
    }

    public String getSpritePath() {
        return spritePath;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }
}
