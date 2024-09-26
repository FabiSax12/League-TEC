package models;

public abstract class Entity {
    protected float health;
    protected int damage;

    public void takeDamage(int damage) {
        this.health -= damage;
    }

    public void attack(Entity entity, int damage) {
        entity.takeDamage(damage);
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
