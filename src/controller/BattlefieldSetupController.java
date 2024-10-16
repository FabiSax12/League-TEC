//package controller;
//
//import models.Character;
//import models.Team;
//import models.Element;
//import view.BattlefieldSetup;
//
//import javax.swing.*;
//
//public class BattlefieldSetupController {
//    private BattlefieldSetup view;
//    private Team currentTeam;
//    private Team[] teams;
//    private int turnIndex = 0; // Índice del turno
//    private boolean placingTowers = true; // Se empieza colocando torretas
//
//    public BattlefieldSetupController(Team team1, Team team2, Element element) {
//        // Inicialización de los equipos y sus torretas/personajes
//        teams = new Team[]{team1, team2};
//        currentTeam = teams[turnIndex];
//
//        // Crear la vista y pasar el controlador
//        this.view = new BattlefieldSetup(this, team1, team2, element);
//        view.updateCharactersOptions(currentTeam);
//    }
//
//    // Manejar la acción de un botón del campo de batalla
//    public void handleGridButtonAction(int row, int col, Character selectedCharacter) {
//        // Validar la colocación según el jugador
//        if ((turnIndex == 0 && row >= 10 / 2) || (turnIndex == 1 && row < 10 / 2)) {
//            JOptionPane.showMessageDialog(view, "No puedes colocar entidades en el campo del oponente.");
//            return;
//        }
//
//        JButton button = view.getGridButton(row, col);
//
//        // Manejo de colocación de torretas o personajes seleccionados
//        if (placingTowers && !currentTeam.getTowers().isEmpty()) {
//            button.setText("T"); // Representación de una torreta
//            button.setEnabled(false);
//            currentTeam.getTowers().remove(0); // Remover la torreta colocada
//            if (currentTeam.getTowers().isEmpty()) {
//                placingTowers = false; // Cambiar a colocación de personajes cuando las torretas estén puestas
//                view.updatePlaceButtonText("Colocar Personaje");
//            }
//        } else if (!placingTowers && selectedCharacter != null) {
//            button.setText(selectedCharacter.getName().substring(0, 1)); // Representación del personaje
//            button.setEnabled(false);
//            view.disableCharacterOption(selectedCharacter);
//        }
//    }
//
//    // Manejar la acción del botón de colocación
//    public void handlePlaceButtonAction() {
//        // Habilitar solo las celdas correspondientes al jugador actual
//        view.enableBattlefieldButtons(turnIndex);
//
//        // Cambio de turno si es necesario
//        if (!placingTowers) {
//            turnIndex = (turnIndex + 1) % 2; // Alternar entre 0 y 1
//            currentTeam = teams[turnIndex];
//            placingTowers = true; // Volver a la colocación de torretas
//            view.updateTurnLabel("Turno de: " + currentTeam.getName());
//            view.updatePlaceButtonText("Colocar Torreta");
//            view.updateCharactersOptions(currentTeam); // Actualizar opciones de personajes
//        }
//    }
//}
