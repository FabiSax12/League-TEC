package models;

/**
 * Represents a generic entity in the game, with attributes such as health, damage, defense,
 * and a sprite. Both characters and other entities in the game can inherit from this class.
 *
 * <p>This class provides basic methods for attacking, taking damage, and handling health.</p>
 */
public abstract class Entity {
    protected String spritePath;
    protected float maxHealth;
    protected float health;
    protected int damage;
    protected int defense;

    /**
     * Attacks a target entity, applying damage to it.
     *
     * @param target the entity being attacked.
     * @param damage the amount of damage dealt to the target.
     */
    public void attack(Entity target, int damage) {
        target.takeDamage(damage);
    }

    /**
     * Reduces the entity's health based on the incoming damage and its defense value.
     *
     * @param damage the amount of damage received.
     */
    public void takeDamage(int damage) {
        this.health -= damage - defense;
    }

    /**
     * Gets the current health of the entity.
     *
     * @return the current health.
     */
    public float getHealth() {
        return health;
    }

    /**
     * Gets the path to the sprite image representing the entity.
     *
     * @return the sprite image path.
     */
    public String getSpritePath() {
        return spritePath;
    }

    /**
     * Sets the entity's current health.
     *
     * @param health the new health value.
     */
    public void setHealth(float health) {
        this.health = health;
    }

    /**
     * Gets the maximum health of the entity.
     *
     * @return the maximum health.
     */
    public float getMaxHealth() {
        return maxHealth;
    }

    /**
     * Sets the maximum health of the entity.
     *
     * @param maxHealth the new maximum health value.
     */
    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }

    /**
     * Gets the current damage value of the entity.
     *
     * @return the damage value.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Sets the damage value of the entity.
     *
     * @param damage the new damage value.
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Gets the current defense value of the entity.
     *
     * @return the defense value.
     */
    public int getDefense() {
        return defense;
    }

    /**
     * Sets the defense value of the entity.
     *
     * @param defense the new defense value.
     */
    public void setDefense(int defense) {
        this.defense = defense;
    }
}