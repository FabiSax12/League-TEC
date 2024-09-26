package models;

import java.util.ArrayList;

public class Team {
    private final String name;
    private final ArrayList<Character> characters;
    private final ArrayList<Tower> towers;

    public Team(String name) {
        this.name = name;
        this.characters = new ArrayList<>();
        this.towers = new ArrayList<>();
    }

    public void addCharacter(Character character) {

    }

    public void addTower(Tower tower) {

    }

    public ArrayList<Character> getCharacters() {
        return this.characters;
    }

    public ArrayList<Tower> getTowers() {
        return this.towers;
    }

    public String getName() {
        return name;
    }
}
