package view;

import models.Team;
import models.Tower;
import models.Character;
import view.components.MatrixButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Objects;


public class MainGameArena extends JPanel{
    private MainGameWindow window;
    private Team team1;
    private Team team2;
    private MatrixButton[][] matrix;

    public MainGameArena(MainGameWindow mainWindow, Team team1, Team team2,MatrixButton[][] matrixButton) {
        this.window=mainWindow;
        this.team1=team1;
        this.team2=team2;
        this.matrix=matrixButton;
        /*Creación de componentes*/
        JPanel gridMatrixButtonsPanel = new JPanel(new GridLayout(10, 10, 5, 5));
        JPanel firstPlayerPanel = new JPanel();
        JPanel secondPlayerPanel = new JPanel();
        JLabel title = new JLabel("A Jugar",SwingConstants.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();

        /*Configuración de componentes*/
        setLayout(new GridBagLayout());
        gridMatrixButtonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setMatrixButtons(gridMatrixButtonsPanel);
        gridMatrixButtonsPanel.setBackground(new Color(2, 25, 153));
        firstPlayerPanel.setBackground(new Color(255, 204, 102));
        secondPlayerPanel.setBackground(new Color(51, 204, 51));
        title.setBackground(new Color(0, 153, 204));
        setBackground(new Color(123, 113, 98));
        //gridMatrixButtonsPanel.set

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
    private void setMatrixButtons(JPanel gridMatrixButtonsPanel){
        for(MatrixButton[] buttonRow: matrix){
            for(MatrixButton button: buttonRow) {
                ActionListenerCleaner(button);
                if (!button.getImagepath().isEmpty()){
                    button.setEnabled(true);
                    button.addActionListener(e->showPopup(button));
                }
                gridMatrixButtonsPanel.add(button);
                button.setPreferredSize(new Dimension(74, 74));
            }
        }
    }
    private void characterMoveSelectionCeld(MatrixButton btn){
        // Calcular las posiciones alrededor del botón actual
        int[] positionAvailable = {btn.getIdentifier() - 10,btn.getIdentifier() + 10,btn.getIdentifier() - 1,btn.getIdentifier() + 1,btn.getIdentifier()};
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
    // Método para restaurar el estado anterior de los botones
    private void restorePreviousState(MatrixButton btn,int[] btnArray) {// Calcular las posiciones alrededor del botón actual
        for(MatrixButton[] buttonRow: matrix){
            for(MatrixButton button: buttonRow){
                if(Arrays.stream(btnArray).anyMatch(i -> i == button.getIdentifier())){
                    if(button.getIdentifier()!=btn.getIdentifier()){
                        button.setEnabled(false);
                        button.setBackground(Color.LIGHT_GRAY);
                    }else{
                        System.out.println("Character Action aplicado a btn #"+button.getIdentifier());
                        ActionListenerCleaner(button);
                        button.addActionListener(e -> showPopup(button));
                    }
                }else if(!button.getImagepath().isEmpty()){button.setEnabled(true);}
            }
        }
    }
    private void ActionListenerCleaner(MatrixButton button){
        ActionListener[] listeners = button.getActionListeners();
        for (ActionListener listener : listeners) {
            button.removeActionListener(listener);
        }
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
            //elif(selection==1){characterAttack(button);}
            //elif(selection==2){changePlayerTurn();}
        }
    }
    private void moveCharacter(MatrixButton destinationButton,MatrixButton originButton,int[] btnArr){
        System.out.println("Moviendo del botón #"+originButton.getIdentifier()+" al botón #"+destinationButton.getIdentifier());
        swapButtonsAtributes(destinationButton, originButton);
        for(MatrixButton[] buttonRow: matrix){
            for(MatrixButton button: buttonRow){
                if(Objects.equals(button.getBackground(), new Color(153, 255, 153))){
                    button.setEnabled(false);
                    ActionListenerCleaner(button);
                    button.setBackground(Color.LIGHT_GRAY);
                    System.out.println("Se ha desabilitado el botón #"+button.getIdentifier());
                }
                else if (!button.getImagepath().isEmpty()){button.setEnabled(true);
                    System.out.println("Se ha habilitado el botón #"+button.getIdentifier());}
            }
        }
    }
    public void swapButtonsAtributes(MatrixButton destinationButton, MatrixButton originButton) {
        /* Configuración de Botón de destino */
        destinationButton.setImagepath(originButton.getImagepath());
        destinationButton.setBackground(Color.LIGHT_GRAY);
        destinationButton.setFilter(originButton.getFilter());
        destinationButton.setIcon(originButton.getIcon());
        if(originButton.getCharacter() != null) {
            destinationButton.setCharacter(originButton.getCharacter());
        }
        if(originButton.getTower() != null){
            destinationButton.setTower(originButton.getTower());
        }
        ActionListenerCleaner(destinationButton);
        destinationButton.addActionListener(e->showPopup(destinationButton));
        destinationButton.setEnabled(true);

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
}