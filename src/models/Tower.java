package models;

/**
 * Represents a tower in the game. Towers have high health and can deal damage, typically serving as defensive structures.
 */
public class Tower extends Entity {

    /**
     * Constructs a new Tower with default health, damage, and sprite path.
     *
     * <p>The tower starts with 10,000 health, 25 damage, and its sprite image is set to {@code "/assets/tower.jpg"}.</p>
     */
    public Tower() {
        this.health = 10000;
        this.damage = 25;
        this.spritePath = "/assets/tower.jpg";
    }

    /**
     * Attacks a target character.
     *
     * <p>This method is currently empty but can be implemented to deal damage to a character target.</p>
     *
     * @param target the character being attacked by the tower.
     */
    public void attack(Character target) {
        // Attack logic can be implemented here.
    }

    /**
     * Returns a string representation of the tower, which is simply "Torre".
     *
     * @return the string "Torre".
     */
    @Override
    public String toString() {
        return "Torre";
    }
}
