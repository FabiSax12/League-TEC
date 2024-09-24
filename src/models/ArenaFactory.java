package models;

public abstract class ArenaFactory {
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
