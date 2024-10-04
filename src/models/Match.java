package models;

import controller.BattlefieldSetupController;

public class Match {
    private final Team team1;
    private final Team team2;
    private final Arena arena;

    public Match(Arena arena, Team team1, Team team2) {
        this.arena = arena;
        this.team1 = team1;
        this.team2 = team2;

        for (int i = 0; i < 3; i++) {
            this.team1.addTower();
            this.team2.addTower();
        }
    }

    public void startMatch(){
        arena.applyBoosts(team1.getCharacters(), team2.getCharacters());
        System.out.println("Match started");
        new BattlefieldSetupController(team1, team2, arena.getElement());
    }

    public boolean isGameOver(){
        return team1.getTowers().isEmpty() || team2.getTowers().isEmpty();
    }

    public Team getTeam1() {
        return team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public Arena getArena() {
        return arena;
    }
}
