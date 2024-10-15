package models;

import java.util.ArrayList;

public class Team {
    private final String name;
    private Player player;
    private final ArrayList<Character> characters;
    private final ArrayList<Tower> towers;
    private boolean turn = false;

    public Team(Player player) {
        this.name = player.getName();
        this.player = player;
        this.characters = new ArrayList<>();
        this.towers = new ArrayList<>();
    }

    public void addCharacter(Character character) {
        this.characters.add(character);
    }

    public void addTower() {
        this.towers.add(new Tower());
    }

    public ArrayList<Character> getCharacters() {
        return this.characters;
    }

    public void setTurn(boolean turn){this.turn=turn;}

    public ArrayList<Tower> getTowers() {
        return this.towers;
    }

    public String getName() {
        return name;
    }

    public boolean getTurn(){return turn;}

    public Player getPlayer(){return player;}
}
