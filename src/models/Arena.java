package models;

import java.util.ArrayList;

public class Arena {
    private final Element type;

    public Arena(Element type) {
        this.type = type;
    }

    public void applyBoosts(ArrayList<ACharacter> team1, ArrayList<ACharacter> team2) {
        for (ACharacter character : team1) {
            if (character.getElement() == this.type) character.applyElementalBoost();
        }
        for (ACharacter character : team2) {
            if (character.getElement() == this.type) character.applyElementalBoost();
        }
    }
}
