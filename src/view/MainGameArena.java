package view;

import models.Character;
import models.Match;
import models.*;
import models.skills.BuffSkill;
import view.components.MatrixButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The {@code MainGameArena} class represents the main game panel where player interactions
 * and game board elements take place. It extends {@link JPanel} and manages a matrix of buttons
 * that represent the cells of the game board.
 * <p>
 * This class handles user actions such as moving characters, using abilities, attacking,
 * and passing turns. It also manages the visual aspects of the game arena, including
 * setting colors based on the arena's element and updating button states.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>{@code
 * MainGameWindow mainWindow = new MainGameWindow();
 * Match match = new Match();
 * MatrixButton[][] matrixButtons = initializeMatrixButtons();
 * MainGameArena gameArena = new MainGameArena(mainWindow, match, matrixButtons);
 * mainWindow.add(gameArena);
 * mainWindow.setVisible(true);
 * }</pre>
 * </p>
 *
 * @author Joseph
 * @version 1.0
 * @since 2024-04-27
 */
public class MainGameArena extends JPanel{
    /**
     * The main game window that contains this panel.
     */
    private MainGameWindow window;

    /**
     * The {@link Match} object that manages the current state of the game.
     */
    private Match match;

    /**
     * A 2D array of {@link MatrixButton} representing the game board.
     */
    private MatrixButton[][] matrix;

    /**
     * Constructs a new {@code MainGameArena} with the specified main window, match, and matrix buttons.
     *
     * @param mainWindow    The main game window.
     * @param match         The {@link Match} object managing the game state.
     * @param matrixButton  A 2D array of {@link MatrixButton} representing the game board.
     */
    public MainGameArena(MainGameWindow mainWindow, Match match, MatrixButton[][] matrixButton) {
        this.window = mainWindow;
        this.match = match;
        this.matrix = matrixButton;
        System.out.println("Current arena is: " + match.getArena());
        match.startMatch();
        match.getTeam1().setTurn(true);
        /* Components creation */
        JPanel gridMatrixButtonsPanel = new JPanel(new GridLayout(10, 10, 5, 5));
        JPanel firstPlayerPanel = new JPanel();
        JPanel secondPlayerPanel = new JPanel();
        JLabel title = new JLabel("Let's Play", SwingConstants.CENTER);
        window.setTitle("Game Execution");

        GridBagConstraints gbc = new GridBagConstraints();

        /* Components settings */
        this.match.getTeam1().setTowers(1);
        this.match.getTeam2().setTowers(2);
        setArenaColor(gridMatrixButtonsPanel);
        setArenaColor(firstPlayerPanel);
        setArenaColor(secondPlayerPanel);
        setArenaColor(this);
        setLayout(new GridBagLayout());
        gridMatrixButtonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setMatrixButtons(gridMatrixButtonsPanel);
        enablePlayerButtons();

        /* Add components */
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(title, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        add(firstPlayerPanel, gbc);

        gbc.gridy = 1;
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        add(gridMatrixButtonsPanel, gbc);

        gbc.gridy = 1;
        gbc.gridx = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        add(secondPlayerPanel, gbc);
    }

    /**
     * Displays a popup dialog with options for a character button.
     *
     * @param button The {@link MatrixButton} that was clicked.
     */
    private void showPopup(MatrixButton button) {
        // Create custom buttons that will appear in the dialog
        Object[] options = {"Move", "Ability","Pass"};
        // Show the popup window with custom buttons
        int selection = JOptionPane.showOptionDialog(
                null,            // Parent component
                "What would you like to do?",            // Message
                "Action Selection",           // Dialog title
                JOptionPane.DEFAULT_OPTION,     // Dialog type
                JOptionPane.INFORMATION_MESSAGE, // Message type
                null,                           // Custom icon (null for default)
                options,                         // Custom buttons
                options[0]);                    // Default option

        // Check which button was selected
        if (selection != -1) {  // If an option is selected (not closed with 'x')
            System.out.println("You selected: " + options[selection]);
            if (selection == 0){
                characterMoveSelectionCeld(button);
            }
            else if(selection == 1){
                characterAbilitySelection(button);
            }
            else {
                passTurn(button);
            }
        }
    }

    /**
     * Displays a popup dialog with options for a tower button.
     *
     * @param button The {@link MatrixButton} representing the tower that was clicked.
     */
    private void showTowerPopup(MatrixButton button) {
        Object[] options = {"Attack","Pass"};
        int selection = JOptionPane.showOptionDialog(
                null,            // Parent component
                "What would you like to do?",            // Message
                "Action Selection",           // Dialog title
                JOptionPane.DEFAULT_OPTION,     // Dialog type
                JOptionPane.INFORMATION_MESSAGE, // Message type
                null,                           // Custom icon (null for default)
                options,                         // Custom buttons
                options[0]);                    // Default option

        if (selection != -1) {
            System.out.println("You selected: " + options[selection]);
            if(selection == 0){
                characterAbilitySelection(button);
            }
            else{
                passTurn(button);
            }
        }
    }

    /**
     * Configures the matrix buttons within the grid panel.
     *
     * @param gridMatrixButtonsPanel The panel containing the grid of buttons.
     */
    private void setMatrixButtons(JPanel gridMatrixButtonsPanel){
        for(MatrixButton[] buttonRow: matrix){
            for(MatrixButton button: buttonRow) {
                ActionListenerCleaner(button);
                if (!button.getImagepath().isEmpty()){
                    button.setEnabled(true);
                    if (button.getTower() == null){
                        button.addActionListener(e -> showPopup(button));
                    }
                    else{
                        button.addActionListener(e -> showTowerPopup(button));
                    }
                }
                gridMatrixButtonsPanel.add(button);
                button.setPreferredSize(new Dimension(74, 74));
            }
        }
    }

    /**
     * Handles the selection of movement for a character button.
     *
     * @param btn The {@link MatrixButton} representing the character to move.
     */
    private void characterMoveSelectionCeld(MatrixButton btn){
        int[] positionAvailable = getPositions(btn);
        for(MatrixButton[] buttonRow: matrix){
            for(MatrixButton button: buttonRow){
                if((Arrays.stream(positionAvailable).anyMatch(i -> i == button.getIdentifier())) &&
                        (button.getCharacter() == null) &&
                        (button.getTower() == null)){
                    button.setEnabled(true);
                    button.setBackground(new Color(153, 255, 153));
                    button.addActionListener(e -> moveCharacter(button, btn, positionAvailable));
                }
                else if(button.getIdentifier() == btn.getIdentifier()){
                    System.out.println("Restore applied to button #" + button.getIdentifier());
                    ActionListenerCleaner(button);
                    button.addActionListener(e -> restorePreviousState(btn, positionAvailable));
                }
                else{
                    button.setEnabled(false);
                }
            }
        }
    }

    /**
     * Restores the previous state of a button after a movement action.
     *
     * @param btn     The {@link MatrixButton} to restore.
     * @param btnArray An array of identifiers representing previously available positions.
     */
    private void restorePreviousState(MatrixButton btn, int[] btnArray) {
        System.out.println("Character Action applied to button #" + btn.getIdentifier());
        ActionListenerCleaner(btn);
        if (btn.getTower() == null){
            btn.addActionListener(e -> showPopup(btn));
        }
        else{
            btn.addActionListener(e -> showTowerPopup(btn));
        }
        // Calculate positions around the current button
        for(MatrixButton[] buttonRow: matrix){
            for(MatrixButton button: buttonRow){
                if(Arrays.stream(btnArray).anyMatch(i -> i == button.getIdentifier())){
                    button.setBackground(Color.LIGHT_GRAY);
                    ActionListenerCleaner(button);
                    button.setEnabled(false);
                }
                else if(!button.getImagepath().isEmpty()){
                    button.setEnabled(true);
                }
            }
        }
        verifyMovements();
    }

    /**
     * Removes all {@link ActionListener}s associated with a button.
     *
     * @param button The {@link MatrixButton} from which to remove listeners.
     */
    private void ActionListenerCleaner(MatrixButton button){
        ActionListener[] listeners = button.getActionListeners();
        for (ActionListener listener : listeners) {
            button.removeActionListener(listener);
        }
    }

    /**
     * Moves a character from the origin button to the destination button.
     *
     * @param destinationButton The {@link MatrixButton} where the character will move to.
     * @param originButton      The {@link MatrixButton} where the character is currently located.
     * @param btnArr            An array of identifiers representing available positions for movement.
     */
    private void moveCharacter(MatrixButton destinationButton, MatrixButton originButton, int[] btnArr){
        System.out.println("Moving from button #" + originButton.getIdentifier() + " to button #" + destinationButton.getIdentifier());
        increaseMovements();
        swapButtonsAtributes(destinationButton, originButton);
        for(MatrixButton[] buttonRow: matrix){
            for(MatrixButton button: buttonRow){
                if(Objects.equals(button.getBackground(), new Color(153, 255, 153))){
                    button.setEnabled(false);
                    ActionListenerCleaner(button);
                    button.setBackground(Color.LIGHT_GRAY);
                    System.out.println("Button #" + button.getIdentifier() + " has been disabled");
                }
                else if ((!button.getImagepath().isEmpty()) && (button.getIdentifier() != destinationButton.getIdentifier())){
                    button.setEnabled(true);
                    System.out.println("Button #" + button.getIdentifier() + " has been enabled");
                }
            }
        }
        verifyMovements();
    }

    /**
     * Swaps the attributes between two buttons, representing the movement of a character.
     *
     * @param destinationButton The {@link MatrixButton} of the destination.
     * @param originButton      The {@link MatrixButton} of the origin.
     */
    public void swapButtonsAtributes(MatrixButton destinationButton, MatrixButton originButton) {
        /* Destination Button Configuration */
        destinationButton.setImagepath(originButton.getImagepath());
        destinationButton.setBackground(Color.LIGHT_GRAY);
        destinationButton.setFilter(new Color(0, 0, 0,100));
        destinationButton.setIcon(originButton.getIcon());
        if(originButton.getCharacter() != null) {
            destinationButton.setCharacter(originButton.getCharacter());
        }
        if(originButton.getTower() != null){
            destinationButton.setTower(originButton.getTower());
        }
        ActionListenerCleaner(destinationButton);
        destinationButton.addActionListener(e -> showPopup(destinationButton));
        destinationButton.setEnabled(false);

        /* Origin Button Configuration */
        originButton.setImagepath("");
        originButton.removeFilter();
        originButton.setBackground(Color.LIGHT_GRAY);
        originButton.setIcon(null);
        if(originButton.getCharacter() != null) {
            originButton.setCharacter(null);
        }
        if(originButton.getTower() != null){
            originButton.setTower(null);
        }
        ActionListenerCleaner(originButton);
        originButton.setEnabled(false);

        /* Revalidate and Repaint */
        originButton.revalidate();
        originButton.repaint();
        destinationButton.revalidate();
        destinationButton.repaint();
    }

    /**
     * Handles the selection of abilities for a character or tower button.
     *
     * @param button The {@link MatrixButton} that was clicked.
     */
    public void characterAbilitySelection(MatrixButton button){
        if(button.getCharacter() != null){
            ASkill[] arrayAbilitys = button.getCharacter().getSkills();
            String[] skillNames = new String[arrayAbilitys.length];
            for (int i = 0; i < arrayAbilitys.length; i++) {
                skillNames[i] = arrayAbilitys[i].getName();
            }
            JComboBox<String> skillSelection = new JComboBox<>(skillNames);
            int option = JOptionPane.showConfirmDialog(null, skillSelection, "Select an ability", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                int selectedSkillIndex = skillSelection.getSelectedIndex();
                ASkill selectedSkill = arrayAbilitys[selectedSkillIndex];
                System.out.println("Selected ability: " + selectedSkill.getName());
                characterAbility(button, selectedSkill);
            }
        }
        else if(button.getTower() != null){
            int[] positions = getPositionsToAttack(button);
            for (int i : positions){
                System.out.println("Enemy detected at button #: " + i);
            }
            if (positions.length != 0){
                towerAttack(button, positions);
            }
            else{
                JOptionPane.showMessageDialog(window, "You must be next to an enemy to attack.", "No enemies in range", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    /**
     * Executes a selected ability for a character or tower.
     *
     * @param button The {@link MatrixButton} executing the ability.
     * @param skill  The {@link ASkill} selected to execute.
     */
    public void characterAbility(MatrixButton button, ASkill skill){
        String type = skill.toJson().get("type").toString();
        switch (type){
            case "attack":
                System.out.println("Attack type: " + skill.toJson().get("type"));
                int[] positions = getPositionsToAttack(button);
                for (int i : positions){
                    System.out.println("Enemy detected at button #: " + i);
                }
                if (positions.length != 0) {
                    characterAttack(button, positions, skill);
                }
                else{
                    JOptionPane.showMessageDialog(window, "You must be next to an enemy to attack.", "No enemies in range", JOptionPane.WARNING_MESSAGE);
                }
                break;
            case "buff":
                System.out.println("Buff type: " + skill.toJson().get("type"));
                int[] positionsBuff = getPositionsToBuffOrHeal(button);
                for (int i : positionsBuff){
                    System.out.println("Ally detected at button #: " + i);
                }
                characterBuffOrHeal(button, positionsBuff, skill);
                break;
            case "heal":
                System.out.println("Heal type: " + skill.toJson().get("type"));
                int[] positionsHeal = getPositionsToBuffOrHeal(button);
                for (int i : positionsHeal){
                    System.out.println("Ally detected at button #: " + i);
                }
                characterBuffOrHeal(button, positionsHeal, skill);
                break;
        }
    }

    /**
     * Handles the buffing or healing of allies.
     *
     * @param button      The {@link MatrixButton} performing the buff or heal.
     * @param alliesBuff  An array of ally identifiers available for buffing or healing.
     * @param skill       The {@link ASkill} used for buffing or healing.
     */
    public void characterBuffOrHeal(MatrixButton button, int[] alliesBuff, ASkill skill){
        if (skill instanceof BuffSkill){
            JOptionPane.showMessageDialog(window, "Select an ally to buff");
        } else {
            JOptionPane.showMessageDialog(window, "Select an ally to heal");
        }
        for (MatrixButton[] row : matrix) {
            for (MatrixButton tempButton : row) {
                if (Arrays.stream(alliesBuff).anyMatch(i -> i == tempButton.getIdentifier())){
                    tempButton.setEnabled(true);
                    ActionListenerCleaner(tempButton);
                    tempButton.addActionListener(e -> restoreAndBuffOrHeal(tempButton, button, alliesBuff, skill));
                }
            }
        }
    }

    /**
     * Retrieves the adjacent positions with allies available for buffing or healing from a given button.
     *
     * @param btn The {@link MatrixButton} from which to find adjacent ally positions.
     * @return An array of integers representing the identifiers of positions with allies.
     */
    public int[] getPositionsToBuffOrHeal(MatrixButton btn){
        Team actualTeam;
        if (match.getTeam1().getTurn()){
            actualTeam = match.getTeam1();
        }
        else{
            actualTeam = match.getTeam2();
        }
        ArrayList<Byte> arrayPositions = new ArrayList<>();
        for (MatrixButton[] row : matrix){
            for(MatrixButton btnMtrx : row){
                if (verifyTeam(btnMtrx, actualTeam)){
                    arrayPositions.add(btnMtrx.getIdentifier());
                }
            }
        }
        // Convert the ArrayList<Byte> to an int[] array using streams
        return arrayPositions.stream()
                .mapToInt(Byte::intValue)  // Map each Byte to its int value
                .toArray();  // Collect the result into an int[] array
    }

    /**
     * Restores the state after a buff or heal action has been executed.
     *
     * @param btnTarget    The {@link MatrixButton} representing the target of the buff or heal.
     * @param btnHealer    The {@link MatrixButton} representing the healer.
     * @param allies       An array of ally identifiers involved in the buff or heal.
     * @param skill        The {@link ASkill} used during the buff or heal.
     */
    public void restoreAndBuffOrHeal(MatrixButton btnTarget, MatrixButton btnHealer, int[] allies, ASkill skill){
        if (skill instanceof BuffSkill){
            if(btnTarget.getCharacter() != null){
                skill.use(btnHealer.getCharacter(), btnTarget.getCharacter());
                btnTarget.updateEntityInfo();
                JOptionPane.showMessageDialog(window, "Ally Buffed");
            }
            else {
                JOptionPane.showMessageDialog(window, "Tower cannot be buffed");
            }
            System.out.println(btnTarget.getName() + " buffed");
            passTurn(btnHealer);
        } else {
            if(btnTarget.getCharacter() != null){
                skill.use(btnHealer.getCharacter(), btnTarget.getCharacter());
                btnTarget.updateEntityInfo();
                JOptionPane.showMessageDialog(window, "Ally Healed");
            }
            else {
                btnHealer.getCharacter().useSkill(skill, btnTarget.getTower());
                btnTarget.updateEntityInfo();
                JOptionPane.showMessageDialog(window, "Ally Healed");
            }
            System.out.println(btnTarget.getName() + " healed");
            passTurn(btnHealer);
        }
        for (MatrixButton[] row : matrix) {
            for (MatrixButton tempButton : row) {
                if (Arrays.stream(allies).anyMatch(i -> i == tempButton.getIdentifier())){
                    if(tempButton.getCharacter()!=null){
                        ActionListenerCleaner(tempButton);
                        tempButton.addActionListener(e -> showPopup(tempButton));
                    }else{
                        ActionListenerCleaner(tempButton);
                        tempButton.addActionListener(e -> showTowerPopup(tempButton));
                    }
                }
            }
        }
    }

    /**
     * Handles a character's attack towards selected enemies using a specific skill.
     *
     * @param button       The {@link MatrixButton} of the character performing the attack.
     * @param enemiesIndex An array of enemy identifiers available for attack.
     * @param skill        The {@link ASkill} used for the attack.
     */
    public void characterAttack(MatrixButton button, int[] enemiesIndex, ASkill skill){
        JOptionPane.showMessageDialog(window, "Select the enemy you want to attack");
        for (MatrixButton[] row : matrix) {
            for (MatrixButton tempButton : row) {
                if (Arrays.stream(enemiesIndex).anyMatch(i -> i == tempButton.getIdentifier())){
                    tempButton.setEnabled(true);
                    ActionListenerCleaner(tempButton);
                    tempButton.addActionListener(e -> restoreAndGetAttackForCharacter(tempButton, button, enemiesIndex, skill));
                }
            }
        }
    }

    /**
     * Restores the state after a character attack has been executed.
     *
     * @param btnTarget    The {@link MatrixButton} representing the target of the attack.
     * @param btnAttacker  The {@link MatrixButton} representing the attacker.
     * @param enemies      An array of enemy identifiers involved in the attack.
     * @param skill        The {@link ASkill} used during the attack.
     */
    public void restoreAndGetAttackForCharacter(MatrixButton btnTarget, MatrixButton btnAttacker, int[] enemies, ASkill skill){
        if(btnTarget.getCharacter() != null){
            System.out.println("Current health of: " + btnTarget.getIdentifier() + " = " + btnTarget.getCharacter().getHealth());
            btnTarget.addActionListener(e -> showPopup(btnTarget));
            skill.use(btnAttacker.getCharacter(), btnTarget.getCharacter());
            btnTarget.updateEntityInfo();
            JOptionPane.showMessageDialog(window, "Enemy damaged");
        }
        else{
            System.out.println("Current health of: " + btnTarget.getIdentifier() + " = " + btnTarget.getTower().getHealth());
            btnTarget.addActionListener(e -> showTowerPopup(btnTarget));
            skill.use(btnAttacker.getCharacter(), btnTarget.getTower());
            btnTarget.updateEntityInfo();
            System.out.println("Health after attack: " + btnTarget.getIdentifier() + " = " + btnTarget.getTower().getHealth());
            JOptionPane.showMessageDialog(window, "Enemy damaged");
        }
        for (MatrixButton[] row : matrix) {
            for (MatrixButton tempButton : row) {
                if (Arrays.stream(enemies).anyMatch(i -> i == tempButton.getIdentifier())){
                    if(tempButton.getCharacter()!=null){
                        ActionListenerCleaner(tempButton);
                        tempButton.addActionListener(e -> showPopup(tempButton));
                    }else{
                        ActionListenerCleaner(tempButton);
                        tempButton.addActionListener(e -> showTowerPopup(tempButton));
                    }
                }
            }
        }
        System.out.println(btnAttacker.getName() + " has attacked");
        passTurn(btnAttacker);
    }

    /**
     * Handles a tower's attack towards selected enemies.
     *
     * @param button  The {@link MatrixButton} of the tower performing the attack.
     * @param enemies An array of enemy identifiers available for attack.
     */
    public void towerAttack(MatrixButton button, int[] enemies){
        JOptionPane.showMessageDialog(window, "Select the enemy you want to attack");
        for (MatrixButton[] row : matrix) {
            for (MatrixButton tempButton : row) {
                if (Arrays.stream(enemies).anyMatch(i -> i == tempButton.getIdentifier())){
                    tempButton.setEnabled(true);
                    ActionListenerCleaner(tempButton);
                    tempButton.addActionListener(e -> restoreAndGetAttackForTower(tempButton, button, enemies));
                }
            }
        }
    }

    /**
     * Restores the state after a tower attack has been executed.
     *
     * @param btnTarget    The {@link MatrixButton} representing the target of the attack.
     * @param btnAttacker  The {@link MatrixButton} representing the attacking tower.
     * @param enemies      An array of enemy identifiers involved in the attack.
     */
    public void restoreAndGetAttackForTower(MatrixButton btnTarget, MatrixButton btnAttacker, int[] enemies){
        if(btnTarget.getTower() != null){
            System.out.println("Current health of: " + btnTarget.getIdentifier() + " = " + btnTarget.getTower().getHealth());
            btnTarget.addActionListener(e -> showTowerPopup(btnTarget));
            btnAttacker.getTower().attack(btnTarget.getTower(), btnAttacker.getEntityDamage());
            btnTarget.updateEntityInfo();
            System.out.println("Health after attack: " + btnTarget.getIdentifier() + " = " + btnTarget.getTower().getHealth());
            JOptionPane.showMessageDialog(window, "Enemy damaged");
        }
        else{
            System.out.println("Current health of: " + btnTarget.getIdentifier() + " = " + btnTarget.getCharacter().getHealth());
            btnTarget.addActionListener(e -> showPopup(btnTarget));
            btnAttacker.getTower().attack(btnTarget.getCharacter(), btnAttacker.getEntityDamage());
            btnTarget.updateEntityInfo();
            System.out.println("Health after attack: " + btnTarget.getIdentifier() + " = " + btnTarget.getCharacter().getHealth());
            JOptionPane.showMessageDialog(window, "Enemy damaged");
        }
        for (MatrixButton[] row : matrix) {
            for (MatrixButton tempButton : row) {
                if (Arrays.stream(enemies).anyMatch(i -> i == tempButton.getIdentifier())){
                    if(tempButton.getCharacter()!=null){
                        ActionListenerCleaner(tempButton);
                        tempButton.addActionListener(e -> showPopup(tempButton));
                    }else{
                        ActionListenerCleaner(tempButton);
                        tempButton.addActionListener(e -> showTowerPopup(tempButton));
                    }
                }
            }
        }
        System.out.println("The tower has attacked");
        passTurn(btnAttacker);
    }

    /**
     * Verifies if a button belongs to a specific team.
     *
     * @param btn   The {@link MatrixButton} to verify.
     * @param team  The {@link Team} to which the button should belong.
     * @return {@code true} if the button belongs to the team, {@code false} otherwise.
     */
    public boolean verifyTeam(MatrixButton btn, Team team){
        ArrayList<Character> characters = team.getCharacters();
        int towersTeamNumber = team.getTowersTeamNumber();
        if(btn.getCharacter() != null){
            return characters.stream().anyMatch(i -> i == btn.getCharacter());
        }
        else if(btn.getTower() != null){
            return towersTeamNumber == btn.getTower().getTeam();
        }
        return false;
    }

    /**
     * Retrieves the adjacent positions available for movement from a given button.
     *
     * @param btn The {@link MatrixButton} from which to find adjacent positions.
     * @return An array of integers representing the identifiers of available positions.
     */
    public int[] getPositions(MatrixButton btn){
        int[] arrayBannedLastPositions = {9,19,29,39,49,59,69,79,89,99};
        int[] arrayBannedNextPositions = {0,10,20,30,40,50,60,70,80,90};
        ArrayList<Byte> arrayPositions = new ArrayList<>();
        for (MatrixButton[] row : matrix){
            for(MatrixButton btnMtrx : row){
                if ((btnMtrx.getIdentifier() == (btn.getIdentifier() - 10))){
                    arrayPositions.add(btnMtrx.getIdentifier());
                }
                if ((btnMtrx.getIdentifier() == (btn.getIdentifier() + 10))){
                    arrayPositions.add(btnMtrx.getIdentifier());
                }
                if ((btnMtrx.getIdentifier() == (btn.getIdentifier() - 1)) &&
                        (Arrays.stream(arrayBannedLastPositions).noneMatch(i -> i == btnMtrx.getIdentifier()))){
                    arrayPositions.add(btnMtrx.getIdentifier());
                }
                if ((btnMtrx.getIdentifier() == (btn.getIdentifier() + 1)) &&
                        (Arrays.stream(arrayBannedNextPositions).noneMatch(i -> i == btnMtrx.getIdentifier()))){
                    arrayPositions.add(btnMtrx.getIdentifier());
                }
            }
        }
        // Convert the ArrayList<Byte> to an int[] array using streams
        return arrayPositions.stream()
                .mapToInt(Byte::intValue)  // Map each Byte to its int value
                .toArray();  // Collect the result into an int[] array
    }

    /**
     * Retrieves the adjacent positions with enemies available for an ability from a given button.
     *
     * @param btn The {@link MatrixButton} from which to find adjacent enemy positions.
     * @return An array of integers representing the identifiers of positions with enemies.
     */
    public int[] getPositionsToAttack(MatrixButton btn){
        Team actualEnemyTeam;
        if (match.getTeam1().getTurn()){
            actualEnemyTeam = match.getTeam2();
        }
        else{
            actualEnemyTeam = match.getTeam1();
        }
        int[] arrayBannedLastPositions = {9,19,29,39,49,59,69,79,89,99};
        int[] arrayBannedNextPositions = {0,10,20,30,40,50,60,70,80,90};
        ArrayList<Byte> arrayPositions = new ArrayList<>();
        for (MatrixButton[] row : matrix){
            for(MatrixButton btnMtrx : row){
                if ((btnMtrx.getIdentifier() == (btn.getIdentifier() - 10)) && (verifyTeam(btnMtrx, actualEnemyTeam))){
                    arrayPositions.add(btnMtrx.getIdentifier());
                }
                if ((btnMtrx.getIdentifier() == (btn.getIdentifier() + 10)) && (verifyTeam(btnMtrx, actualEnemyTeam))){
                    arrayPositions.add(btnMtrx.getIdentifier());
                }
                if ((btnMtrx.getIdentifier() == (btn.getIdentifier() - 1)) &&
                        (Arrays.stream(arrayBannedLastPositions).noneMatch(i -> i == btnMtrx.getIdentifier())) &&
                        (verifyTeam(btnMtrx, actualEnemyTeam))){
                    arrayPositions.add(btnMtrx.getIdentifier());
                }
                if ((btnMtrx.getIdentifier() == (btn.getIdentifier() + 1)) &&
                        (Arrays.stream(arrayBannedNextPositions).noneMatch(i -> i == btnMtrx.getIdentifier())) &&
                        (verifyTeam(btnMtrx, actualEnemyTeam))){
                    arrayPositions.add(btnMtrx.getIdentifier());
                }
            }
        }
        // Convert the ArrayList<Byte> to an int[] array using streams
        return arrayPositions.stream()
                .mapToInt(Byte::intValue)  // Map each Byte to its int value
                .toArray();  // Collect the result into an int[] array
    }

    /**
     * Enables the buttons of players that can attack based on the target team.
     *
     * @param targetTeam The {@link Team} that can be attacked.
     */
    public void setPlayerButtonsToAttack(Team targetTeam) {
        for (MatrixButton[] row : matrix) {
            for (MatrixButton button : row) {
                if ((button.getCharacter() != null || button.getTower() != null) && verifyTeam(button, targetTeam)) {
                    button.setEnabled(true);
                }
            }
        }
    }

    /**
     * Enables the buttons of the current player's team.
     */
    public void enablePlayerButtons(){
        Team currentTeam = match.getTeam1().getTurn() ? match.getTeam1() : match.getTeam2();
        Team oppositeTeam = match.getTeam1().getTurn() ? match.getTeam2() : match.getTeam1();

        for (MatrixButton[] row : matrix) {
            for (MatrixButton button : row) {
                if ((button.getCharacter() != null || button.getTower() != null) && verifyTeam(button, currentTeam) && !Objects.equals(button.getFilter(), new Color(0, 0, 0, 100))) {
                    button.setEnabled(true);
                    System.out.println("Button " + button.getIdentifier() + " enabled");
                } else if (verifyTeam(button, oppositeTeam)) {
                    button.setEnabled(false);
                } else {
                    button.setEnabled(false);
                }
            }
        }
    }

    /**
     * Increments the movement count for the current team.
     */
    public void increaseMovements(){
        if (match.getTeam1().getTurn()){
            match.getTeam1().setMoves();
        }
        else{
            match.getTeam2().setMoves();
        }
    }

    /**
     * Verifies if the current team has reached the movement limit and switches turns if necessary.
     */
    public void verifyMovements(){
        if ((match.getTeam1().getTurn()) && (match.getTeam1().getMoves() >= 6)){
            match.getTeam1().resetMoves();
            match.getTeam1().setTurn(false);
            match.getTeam2().setTurn(true);
        }
        else if ((match.getTeam2().getTurn()) && (match.getTeam2().getMoves() >= 6)){
            match.getTeam2().resetMoves();
            match.getTeam2().setTurn(false);
            match.getTeam1().setTurn(true);
            restoreButtonsFilters();
        }
        enablePlayerButtons();
    }

    /**
     * Restores the filters of the buttons after a turn has been passed.
     */
    public void restoreButtonsFilters(){
        for(MatrixButton[] rows : matrix){
            for(MatrixButton btn : rows){
                if ((Objects.equals(btn.getFilter(), new Color(0, 0, 0,100))) && (verifyTeam(btn, match.getTeam1()))){
                    btn.setFilter(new Color(255,0,0,100));
                }
                else if ((Objects.equals(btn.getFilter(), new Color(0, 0, 0,100))) && (verifyTeam(btn, match.getTeam2()))){
                    btn.setFilter(new Color(0,0,255,100));
                }
            }
        }
    }

    /**
     * Passes the turn of the current team, increments movements, and verifies the game state.
     *
     * @param btn The {@link MatrixButton} that performs the pass turn action.
     */
    public void passTurn(MatrixButton btn){
        increaseMovements();
        btn.setFilter(new Color(0, 0, 0,100));
        btn.setEnabled(false);
        verifyMovements();
        System.out.println("Remaining moves (1): " + match.getTeam1().getMoves() + " (2): " + match.getTeam2().getMoves());
    }

    /**
     * Sets the background color of a panel based on the arena's element.
     *
     * @param pnl The {@link JPanel} to set the background color for.
     */
    public void setArenaColor(JPanel pnl){
        switch (match.getArena().getElement()){
            case FIRE:
                pnl.setBackground(new Color(255, 153, 51));
                break;
            case WATER:
                pnl.setBackground(new Color(2, 25, 153));
                break;
            case GROUND:
                pnl.setBackground(new Color(153, 102, 51));
                break;
            case AIR:
                pnl.setBackground(new Color(121, 134, 134));
                break;
        }
    }
}
