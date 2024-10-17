package models;

import java.util.ArrayList;

/**
 * Represents an arena where battles take place, providing an elemental boost
 * to characters that match the arena's element.
 */
public class Arena {
    private final Element element;

    /**
     * Constructs an arena with a specified elemental type.
     *
     * @param type the element of the arena.
     */
    public Arena(Element type) {
        this.element = type;
    }

    /**
     * Applies elemental boosts to characters in both teams if their element matches the arena's element.
     *
     * <p>If a character's element matches the arena's element, the character's
     * {@code applyElementalBoost()} method is called to apply the boost.</p>
     *
     * @param team1 the first team of characters.
     * @param team2 the second team of characters.
     */
    public void applyBoosts(ArrayList<Character> team1, ArrayList<Character> team2) {
        for (Character character : team1) {
            if (character.getElement() == this.element) character.applyElementalBoost();
        }
        for (Character character : team2) {
            if (character.getElement() == this.element) character.applyElementalBoost();
        }
    }

    /**
     * Returns the element of the arena.
     *
     * @return the elemental type of the arena.
     */
    public Element getElement() {
        return element;
    }
}