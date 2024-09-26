import controller.CharacterSelectionController;
import models.*;

public class Main {
    public static void main(String[] args) {
        // Creación de la arena
        Arena arena = ArenaFactory.create();

        // Equipos
        Team team1 = new Team("Jugador 1");
        Team team2 = new Team("Jugador 2");

        // Creación del juego
        Match game = new Match(arena, team1, team2);
        new CharacterSelectionController(team1, team2, game);
    }
}
