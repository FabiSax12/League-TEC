package models;

import java.util.ArrayList;

public class Match {
    private Team team1;
    private Team team2;
    private Arena arena;

    public Match(Arena arena, Team team1, Team team2) {
        this.arena = arena;
        this.team1 = team1;
        this.team2 = team2;

        for (int i = 0; i < 4; i++) {
            this.team1.addTower(new Tower());
            this.team2.addTower(new Tower());
        }
    }

    public void startMatch(){
        arena.applyBoosts(team1.getCharacters(), team2.getCharacters());
    }

    public boolean isGameOver(){
        return team1.getTowers().isEmpty() || team2.getTowers().isEmpty();
    }
}
