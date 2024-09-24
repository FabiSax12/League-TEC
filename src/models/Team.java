package models;

import java.util.ArrayList;

public class Team {
    private ArrayList<ACharacter> characters;
    private ArrayList<Tower> towers;

    public Team() {
        this.characters = new ArrayList<>();
        this.towers = new ArrayList<>();
    }

    public void addCharacter(ACharacter character) {
        this.characters.add(character);
    }

    public void addTower(Tower tower) {
        this.towers.add(tower);
    }

    public ArrayList<ACharacter> getCharacters() {
        return this.characters;
    }

    public ArrayList<Tower> getTowers() {
        return this.towers;
    }
}
