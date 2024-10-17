package models;

import java.util.ArrayList;

/**
 * Represents a team in the game, consisting of a player, a list of characters, and a list of towers.
 * Each team has a name, moves counter, and a flag to indicate whose turn it is.
 */
public class Team {
    private final String name;
    private Player player;
    private final ArrayList<Character> characters;
    private final ArrayList<Tower> towers;
    private boolean turn = false;
    private byte moves = 0;

    /**
     * Constructs a new Team for the given player.
     * The team's name is initialized as the player's name, and the team starts with empty lists of characters and towers.
     *
     * @param player the player associated with the team.
     */
    public Team(Player player) {
        this.name = player.getName();
        this.player = player;
        this.characters = new ArrayList<>();
        this.towers = new ArrayList<>();
    }

    /**
     * Adds a character to the team's list of characters.
     *
     * @param character the character to add.
     */
    public void addCharacter(Character character) {
        this.characters.add(character);
    }

    /**
     * Adds a tower to the team's list of towers.
     */
    public void addTower() {
        this.towers.add(new Tower());
    }

    /**
     * Returns the list of characters in the team.
     *
     * @return an ArrayList of characters.
     */
    public ArrayList<Character> getCharacters() {
        return this.characters;
    }

    /**
     * Sets whether it is this team's turn or not.
     *
     * @param turn {@code true} if it is this team's turn, {@code false} otherwise.
     */
    public void setTurn(boolean turn){
        this.turn=turn;
        for (Character indexCharacter:characters){
            indexCharacter.regenerateMana();
        }
    }

    /**
     * Returns the list of towers in the team.
     *
     * @return an ArrayList of towers.
     */
    public ArrayList<Tower> getTowers() {
        return this.towers;
    }

    /**
     * Returns the number of moves the team has made in the current turn.
     *
     * @return the number of moves as a byte.
     */
    public byte getMoves() {
        return moves;
    }

    /**
     * Increments the number of moves the team has made by 1.
     */
    public void setMoves() {
        this.moves += 1;
    }

    /**
     * Returns the name of the team, which is the player's name.
     *
     * @return the team's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns whether it is currently this team's turn.
     *
     * @return {@code true} if it is this team's turn, {@code false} otherwise.
     */
    public boolean getTurn(){return turn;}

    /**
     * Returns the player associated with the team.
     *
     * @return the player of the team.
     */
    public Player getPlayer(){return player;}

    /**
     * Resets the number of moves the team has made to 0.
     */
    public void resetMoves(){this.moves=0;}

    /**
     * Set towers to a team
     *
     * @param teamNumber The number of the team (1, 2)
     */
    public void setTowers(int teamNumber){
        for(Tower tower:this.getTowers()){
            tower.setTeam(teamNumber);
        }
    }

    /**
     * Gets the team number (1, 2) of a towers
     *
     * @return The team number of the tower
     */
    public int getTowersTeamNumber(){
        return towers.getFirst().getTeam();
    }
}
