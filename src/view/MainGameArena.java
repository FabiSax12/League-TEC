package view;

import models.Character;
import models.Match;
import models.*;
import view.components.MatrixButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Objects;


public class MainGameArena extends JPanel{
    private MainGameWindow window;
    private Match match;
    private MatrixButton[][] matrix;

    public MainGameArena(MainGameWindow mainWindow, Match match, MatrixButton[][] matrixButton) {
        this.window=mainWindow;
        this.match= match;
        this.matrix=matrixButton;
        System.out.println("La arena actual es:"+match.getArena());
        match.startMatch();
        match.getTeam1().setTurn(true);
        /*Components creation*/
        JPanel gridMatrixButtonsPanel = new JPanel(new GridLayout(10, 10, 5, 5));
        JPanel firstPlayerPanel = new JPanel();
        JPanel secondPlayerPanel = new JPanel();
        JLabel title = new JLabel("A Jugar",SwingConstants.CENTER);
        window.setTitle("Ejecución de juego");

        GridBagConstraints gbc = new GridBagConstraints();

        /*Components settings*/
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

        /*Agregar componentes*/
        gbc.gridy=0;
        gbc.gridx=0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth=GridBagConstraints.REMAINDER;
        add(title,gbc);

        gbc.gridy=1;
        gbc.gridx=0;
        gbc.gridwidth=1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridheight=GridBagConstraints.REMAINDER;
        gbc.anchor=GridBagConstraints.CENTER;
        gbc.fill=GridBagConstraints.BOTH;
        add(firstPlayerPanel,gbc);

        gbc.gridy=1;
        gbc.gridx=1;
        gbc.gridwidth=1;
        gbc.gridheight=GridBagConstraints.REMAINDER;
        add(gridMatrixButtonsPanel,gbc);

        gbc.gridy=1;
        gbc.gridx=2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridwidth=GridBagConstraints.REMAINDER;;
        gbc.gridheight=GridBagConstraints.REMAINDER;
        gbc.anchor=GridBagConstraints.CENTER;
        gbc.fill=GridBagConstraints.BOTH;
        add(secondPlayerPanel,gbc);
    }

    private void showPopup(MatrixButton button) {
        // Crear los botones personalizados que aparecerán en el diálogo
        Object[] options = {"Mover", "Atacar","Pasar"};
        // Mostrar la ventana emergente con botones personalizados
        int selection = JOptionPane.showOptionDialog(
                null,            // Componente padre
                "Que desea hacer?",            // Mensaje
                "Selección de acción",           // Título del cuadro de diálogo
                JOptionPane.DEFAULT_OPTION,     // Tipo de cuadro de diálogo
                JOptionPane.INFORMATION_MESSAGE, // Tipo de mensaje
                null,                           // Icono personalizado (null para sin icono)
                options,                         // Botones personalizados
                options[0]);                    // Opción por defecto
        // Comprobar qué botón fue seleccionado
        if (selection != -1) {  // Si se selecciona una opción (no se cierra con 'x')
            System.out.println("Seleccionaste: " + options[selection]);
            if (selection==0){characterMoveSelectionCeld(button);}
            else if(selection==1){characterAbilitySelection(button);}
            else {passTurn(button);}
        }
    }

    private void showTowerPopup(MatrixButton button) {
        Object[] options = {"Atacar","Pasar"};
        int selection = JOptionPane.showOptionDialog(
                null,            // Componente padre
                "Que desea hacer?",            // Mensaje
                "Selección de acción",           // Título del cuadro de diálogo
                JOptionPane.DEFAULT_OPTION,     // Tipo de cuadro de diálogo
                JOptionPane.INFORMATION_MESSAGE, // Tipo de mensaje
                null,                           // Icono personalizado (null para sin icono)
                options,                         // Botones personalizados
                options[0]);                    // Opción por defecto

        if (selection != -1) {
            System.out.println("Seleccionaste: " + options[selection]);
            if(selection==0){characterAbilitySelection(button);}
            else{passTurn(button);}
        }
    }

    private void setMatrixButtons(JPanel gridMatrixButtonsPanel){
        for(MatrixButton[] buttonRow: matrix){
            for(MatrixButton button: buttonRow) {
                ActionListenerCleaner(button);
                if (!button.getImagepath().isEmpty()){
                    button.setEnabled(true);
                    if (button.getTower()==null){button.addActionListener(e->showPopup(button));}
                    else{button.addActionListener(e->showTowerPopup(button));}
                }
                gridMatrixButtonsPanel.add(button);
                button.setPreferredSize(new Dimension(74, 74));
            }
        }
    }

    private void characterMoveSelectionCeld(MatrixButton btn){
        int[] positionAvailable = getPositions(btn);
        for(MatrixButton[] buttonRow: matrix){
            for(MatrixButton button: buttonRow){
                if((Arrays.stream(positionAvailable).anyMatch(i -> i == button.getIdentifier()))&&(button.getCharacter()==null)&&(button.getTower()==null)){
                    button.setEnabled(true);
                    button.setBackground(new Color(153, 255, 153));
                    button.addActionListener(e->moveCharacter(button,btn,positionAvailable));
                }
                else if(button.getIdentifier()==btn.getIdentifier()){
                        System.out.println("Restore aplicado a btn #"+button.getIdentifier());
                        ActionListenerCleaner(button);
                        button.addActionListener(e -> restorePreviousState(btn,positionAvailable));
                }
                else{button.setEnabled(false);}
            }
        }
    }

    private void restorePreviousState(MatrixButton btn,int[] btnArray) {
        System.out.println("Character Action aplicado a btn #"+btn.getIdentifier());
        ActionListenerCleaner(btn);
        if (btn.getTower()==null){btn.addActionListener(e->showPopup(btn));}
        else{btn.addActionListener(e->showTowerPopup(btn));}//////////////////////////////////////////////////////////////// Calcular las posiciones alrededor del botón actual
        for(MatrixButton[] buttonRow: matrix){
            for(MatrixButton button: buttonRow){
                if(Arrays.stream(btnArray).anyMatch(i -> i == button.getIdentifier())){
                    button.setBackground(Color.LIGHT_GRAY);
                    ActionListenerCleaner(button);
                    button.setEnabled(false);
                }else if(!button.getImagepath().isEmpty()){button.setEnabled(true);}
            }
        }
        verifyMovements();
    }

    private void ActionListenerCleaner(MatrixButton button){
        ActionListener[] listeners = button.getActionListeners();
        for (ActionListener listener : listeners) {
            button.removeActionListener(listener);
        }
    }

    private void moveCharacter(MatrixButton destinationButton,MatrixButton originButton,int[] btnArr){
        System.out.println("Moviendo del botón #"+originButton.getIdentifier()+" al botón #"+destinationButton.getIdentifier());
        increaseMovements();
        swapButtonsAtributes(destinationButton, originButton);
        for(MatrixButton[] buttonRow: matrix){
            for(MatrixButton button: buttonRow){
                if(Objects.equals(button.getBackground(), new Color(153, 255, 153))){
                    button.setEnabled(false);
                    ActionListenerCleaner(button);
                    button.setBackground(Color.LIGHT_GRAY);
                    System.out.println("Se ha desabilitado el botón #"+button.getIdentifier());
                }
                else if ((!button.getImagepath().isEmpty())&&(button.getIdentifier()!=destinationButton.getIdentifier())){
                    button.setEnabled(true);
                    System.out.println("Se ha habilitado el botón #"+button.getIdentifier());}
            }
        }
        verifyMovements();
    }

    public void swapButtonsAtributes(MatrixButton destinationButton, MatrixButton originButton) {
        /* Configuración de Botón de destino */
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
        destinationButton.addActionListener(e->showPopup(destinationButton));
        destinationButton.setEnabled(false);

        /* Configuración de Botón de Origen */
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

        /* Revalidar y repintar */
        originButton.revalidate();
        originButton.repaint();
        destinationButton.revalidate();
        destinationButton.repaint();
    }

    public void characterAbilitySelection(MatrixButton button){
        if(button.getCharacter()!=null){
            int[] positions=getPositionsToAbility(button);
            ASkill[] arrayAbilitys=button.getCharacter().getSkills();
            for (int i:positions){System.out.println("Enemigo detectado en botón #: "+i);}
            if (positions.length != 0) {
                String[] skillNames = new String[arrayAbilitys.length];
                for (int i = 0; i < arrayAbilitys.length; i++) {
                    skillNames[i] = arrayAbilitys[i].getName(); // Asumiendo que ASkill tiene un método getName()
                }

                JComboBox<String> skillSelection = new JComboBox<>(skillNames);
                int option = JOptionPane.showConfirmDialog(
                        null,
                        skillSelection,
                        "Selecciona una habilidad",
                        JOptionPane.OK_CANCEL_OPTION
                );

                if (option == JOptionPane.OK_OPTION) {
                    int selectedSkillIndex = skillSelection.getSelectedIndex();
                    ASkill selectedSkill = arrayAbilitys[selectedSkillIndex];
                    System.out.println("Habilidad seleccionada: " + selectedSkill.getName());

                    characterAbility(button, selectedSkill);
                }
            }
            else{
                JOptionPane.showMessageDialog(
                    window,
                    "Debes estar al lado de un enemigo para atacar.",
                    "No hay enemigos al alcance",
                    JOptionPane.WARNING_MESSAGE
            );}
        }else if(button.getTower()!=null){
            int[] positions=getPositionsToAbility(button);
            for (int i:positions){System.out.println("Enemigo detectado en botón #: "+i);}
            if (positions.length!=0){
                towerAttack(button,positions);
            }
            else{
                JOptionPane.showMessageDialog(
                        window,
                        "Debes estar al lado de un enemigo para atacar.",
                        "No hay enemigos al alcance",
                        JOptionPane.WARNING_MESSAGE
                );}
        }


    }

    public void characterAbility(MatrixButton button,ASkill skill){
        System.out.println("El personaje ha atacado");
        passTurn(button);
        System.out.println("Tipo de ataque: "+skill.toJson().get("type"));
        String type = skill.toJson().get("type").toString();
        switch (type){
            case "attack":
                break;
            case "buff":
                break;
            case "heal":
        }

    }

    public void characterAttack(){

    }

    public void towerAttack(MatrixButton button,int[] enemies){
        for (MatrixButton[] row : matrix) {
            for (MatrixButton tempButton : row) {
                if (Arrays.stream(enemies).anyMatch(i->i==tempButton.getIdentifier())){
                    tempButton.setEnabled(true);
                    ActionListenerCleaner(tempButton);
                    tempButton.addActionListener(e->restoreAndGetAttack(tempButton,button,enemies));
                }
            }
        }
    }

    void restoreAndGetAttack(MatrixButton btnTarget, MatrixButton btnAttacker,int[] enemies){
        if(btnAttacker.getTower()!=null){
            if(btnTarget.getTower()!=null){
                System.out.println("Vida actual de: "+ btnTarget.getIdentifier()+" = "+ btnTarget.getTower().getHealth());
                btnTarget.addActionListener(e->showTowerPopup(btnTarget));
                btnAttacker.getTower().attack(btnTarget.getTower(), btnAttacker.getEntityDamage());
                btnTarget.updateEntityInfo();
                System.out.println("Vida luego del ataque: "+ btnTarget.getIdentifier()+" = "+ btnTarget.getTower().getHealth());
                JOptionPane.showConfirmDialog(window,"Enemigo dañado");
            }else{
                System.out.println("Vida actual de: "+ btnTarget.getIdentifier()+" = "+ btnTarget.getCharacter().getHealth());
                btnTarget.addActionListener(e->showTowerPopup(btnTarget));
                btnAttacker.getTower().attack(btnTarget.getCharacter(), btnAttacker.getEntityDamage());
                btnTarget.updateEntityInfo();
                System.out.println("Vida luego del ataque: "+ btnTarget.getIdentifier()+" = "+ btnTarget.getCharacter().getHealth());
                JOptionPane.showConfirmDialog(window,"Enemigo dañado");
            }

        }
        else{

        }
        for (MatrixButton[] row : matrix) {
            for (MatrixButton tempButton : row) {
                if (Arrays.stream(enemies).anyMatch(i->i==tempButton.getIdentifier())){
                    ActionListenerCleaner(tempButton);
                    tempButton.setEnabled(false);
                }
            }

        }passTurn(btnAttacker);
    }

    public boolean verifyTeam(MatrixButton btn,Team team){
        ArrayList<Character> characters=team.getCharacters();
        int towersTeamNumber=team.getTowersTeamNumber();
        if(btn.getCharacter()!=null){
            return characters.stream().anyMatch(i->i == btn.getCharacter());
        }else if(btn.getTower()!=null){
            return towersTeamNumber==btn.getTower().getTeam();
        }return false;
    }

    public int[] getPositions(MatrixButton btn){
        int[] arrayBannedLastPositions={9,19,29,39,49,59,69,79,89,99};
        int[] arrayBannedNextPositions={0,10,20,30,40,50,60,70,80,90};
        ArrayList<Byte> arrayPositions=new ArrayList<>();
        for (MatrixButton[] row:matrix){
            for(MatrixButton btnMtrx:row){
                if ((btnMtrx.getIdentifier()==(btn.getIdentifier())-10)){
                    arrayPositions.add(btnMtrx.getIdentifier());
                }
                if ((btnMtrx.getIdentifier()==(btn.getIdentifier())+10)){
                    arrayPositions.add(btnMtrx.getIdentifier());
                }
                if ((btnMtrx.getIdentifier()==(btn.getIdentifier())-1) && (Arrays.stream(arrayBannedLastPositions).noneMatch(i -> i == btnMtrx.getIdentifier()))){
                    arrayPositions.add(btnMtrx.getIdentifier());
                }
                if ((btnMtrx.getIdentifier()==(btn.getIdentifier())+1) && (Arrays.stream(arrayBannedNextPositions).noneMatch(i -> i == btnMtrx.getIdentifier()))){
                    arrayPositions.add(btnMtrx.getIdentifier());
                }
            }
        }
        // Convertimos el ArrayList<Byte> a un array de int[] de manera funcional
        return arrayPositions.stream()
                .mapToInt(Byte::intValue)  // Mapeamos cada Byte a su valor int
                .toArray();  // Recolectamos el resultado en un array de int[]
    }

    public int[] getPositionsToAbility(MatrixButton btn){
        Team actualEnemyTeam;
        if (match.getTeam1().getTurn()){
            actualEnemyTeam=match.getTeam2();
        }else{
            actualEnemyTeam=match.getTeam1();
        }
        int[] arrayBannedLastPositions={9,19,29,39,49,59,69,79,89,99};
        int[] arrayBannedNextPositions={0,10,20,30,40,50,60,70,80,90};
        ArrayList<Byte> arrayPositions=new ArrayList<>();
        for (MatrixButton[] row:matrix){
            for(MatrixButton btnMtrx:row){
                if ((btnMtrx.getIdentifier()==(btn.getIdentifier()-10)) && (verifyTeam(btnMtrx,actualEnemyTeam))){
                    arrayPositions.add(btnMtrx.getIdentifier());
                }
                if ((btnMtrx.getIdentifier()==(btn.getIdentifier()+10)) && (verifyTeam(btnMtrx,actualEnemyTeam))){
                    arrayPositions.add(btnMtrx.getIdentifier());
                }
                if ((btnMtrx.getIdentifier()==(btn.getIdentifier())-1) && (Arrays.stream(arrayBannedLastPositions).noneMatch(i -> i == btnMtrx.getIdentifier())) && (verifyTeam(btnMtrx,actualEnemyTeam))){
                    arrayPositions.add(btnMtrx.getIdentifier());
                }
                if ((btnMtrx.getIdentifier()==(btn.getIdentifier())+1) && (Arrays.stream(arrayBannedNextPositions).noneMatch(i -> i == btnMtrx.getIdentifier())) && (verifyTeam(btnMtrx,actualEnemyTeam))){
                    arrayPositions.add(btnMtrx.getIdentifier());
                }
            }
        }
        // Convertimos el ArrayList<Byte> a un array de int[] de manera funcional
        return arrayPositions.stream()
                .mapToInt(Byte::intValue)  // Mapeamos cada Byte a su valor int
                .toArray();  // Recolectamos el resultado en un array de int[]
    }

    public void setPlayerButtonsToAttack(Team targetTeam) {
        for (MatrixButton[] row : matrix) {
            for (MatrixButton button : row) {
                if ((button.getCharacter() != null || button.getTower() != null) && verifyTeam(button, targetTeam)) {
                    button.setEnabled(true);
                }
            }
        }
    }

    public void enablePlayerButtons(){
        Team currentTeam = match.getTeam1().getTurn() ? match.getTeam1() : match.getTeam2();
        Team oppositeTeam = match.getTeam1().getTurn() ? match.getTeam2() : match.getTeam1();

        for (MatrixButton[] row : matrix) {
            for (MatrixButton button : row) {
                if ((button.getCharacter() != null || button.getTower() != null) &&
                        verifyTeam(button, currentTeam) &&
                        !Objects.equals(button.getFilter(), new Color(0, 0, 0, 100))) {
                    button.setEnabled(true);
                } else if (verifyTeam(button, oppositeTeam)) {
                    button.setEnabled(false);
                } else {
                    button.setEnabled(false);
                }
            }
        }
    }

    public void increaseMovements(){
        if (match.getTeam1().getTurn()){
            match.getTeam1().setMoves();
        }else{
            match.getTeam2().setMoves();
        }
    }

    public void verifyMovements(){
        if ((match.getTeam1().getTurn())&&(match.getTeam1().getMoves()>=6)){
            match.getTeam1().resetMoves();
            match.getTeam1().setTurn(false);
            match.getTeam2().setTurn(true);
        }else if ((match.getTeam2().getTurn())&&(match.getTeam2().getMoves()>=6)){
            match.getTeam2().resetMoves();
            match.getTeam2().setTurn(false);
            match.getTeam1().setTurn(true);
            restoreButtonsFilters();
        }
        enablePlayerButtons();
    }

    public void restoreButtonsFilters(){
        for(MatrixButton[] rows:matrix){
            for(MatrixButton btn:rows){
                if ((Objects.equals(btn.getFilter(), new Color(0, 0, 0,100)))&&(verifyTeam(btn,match.getTeam1()))){
                    btn.setFilter(new Color(255,0,0,100));
                }else if ((Objects.equals(btn.getFilter(), new Color(0, 0, 0,100)))&&(verifyTeam(btn,match.getTeam2()))){
                    btn.setFilter(new Color(0,0,255,100));
                }
            }
        }
    }

    public void passTurn(MatrixButton btn){
        increaseMovements();
        btn.setFilter(new Color(0, 0, 0,100));
        btn.setEnabled(false);
        verifyMovements();
    }

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