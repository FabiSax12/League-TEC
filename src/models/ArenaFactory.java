package models;

/**
 * Factory class for creating Arena objects with random elemental types.
 */
public abstract class ArenaFactory {

    /**
     * Creates an arena with a randomly selected elemental type.
     *
     * <p>The method generates a random number between 0 and 3 to determine the element of the arena.
     * Depending on the random value, it returns an Arena with one of the following elements:
     * AIR, FIRE, GROUND, or WATER.</p>
     *
     * @return an {@code Arena} object with a randomly assigned element, or {@code null} if no element is assigned.
     */
    public static Arena create() {
        double random = Math.round(Math.random() * 3);

        if (random == 0) {
            return new Arena(Element.AIR);
        }

        if (random == 1) {
            return new Arena(Element.FIRE);
        }

        if (random == 2) {
            return new Arena(Element.GROUND);
        }

        if (random == 3) {
            return new Arena(Element.WATER);
        }

        return null;
    }
}