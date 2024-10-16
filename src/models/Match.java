package models;

/**
 * Represents a match between two teams in a specified arena.
 *
 * <p>The match involves two teams, each with their own set of characters and towers.
 * The match starts by applying elemental boosts based on the arena, and it continues
 * until one team loses all its towers.</p>
 */
public class Match {
    private final Team team1;
    private final Team team2;
    private final Arena arena;

    /**
     * Constructs a new Match with the specified arena and teams.
     *
     * <p>Each team is initially given three towers as part of the setup.</p>
     *
     * @param arena the arena where the match takes place.
     * @param team1 the first team participating in the match.
     * @param team2 the second team participating in the match.
     */
    public Match(Arena arena, Team team1, Team team2) {
        this.arena = arena;
        this.team1 = team1;
        this.team2 = team2;

        for (int i = 0; i < 3; i++) {
            this.team1.addTower();
            this.team2.addTower();
        }
    }

    /**
     * Starts the match by applying elemental boosts to both teams' characters based on the arena.
     *
     * <p>This method can also be expanded to initialize the battlefield and other match-specific logic.</p>
     */
    public void startMatch() {
        arena.applyBoosts(team1.getCharacters(), team2.getCharacters());
        System.out.println("Match started");
        // new BattlefieldSetupController(team1, team2, arena.getElement());
    }

    /**
     * Checks if the match is over.
     *
     * <p>The match is over if either team has no remaining towers.</p>
     *
     * @return {@code true} if the match is over (i.e., one team has no towers left), {@code false} otherwise.
     */
    public boolean isGameOver() {
        return team1.getTowers().isEmpty() || team2.getTowers().isEmpty();
    }

    /**
     * Returns the first team in the match.
     *
     * @return the first team.
     */
    public Team getTeam1() {
        return team1;
    }

    /**
     * Returns the second team in the match.
     *
     * @return the second team.
     */
    public Team getTeam2() {
        return team2;
    }

    /**
     * Returns the arena where the match is taking place.
     *
     * @return the arena of the match.
     */
    public Arena getArena() {
        return arena;
    }
}
