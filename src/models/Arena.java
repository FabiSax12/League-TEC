package models;

import java.util.ArrayList;

public class Arena {
    private final Element element;

    public Arena(Element type) {
        this.element = type;
    }

    public void applyBoosts(ArrayList<Character> team1, ArrayList<Character> team2) {
        for (Character character : team1) {
            if (character.getElement() == this.element) character.applyElementalBoost();
        }
        for (Character character : team2) {
            if (character.getElement() == this.element) character.applyElementalBoost();
        }
    }

    public Element getElement() {
        return element;
    }
}
