package view;

import models.Team;
import utils.IMG;
import view.components.MatrixButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        JPanel gridMatrixButtonsPanel = new JPanel(new GridLayout(10, 10, 5, 5));
        gridMatrixButtonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(gridMatrixButtonsPanel);
        for(MatrixButton[] buttonRow: matrixButton){
            for(MatrixButton button: buttonRow){
                ActionListener[] listeners = button.getActionListeners();
                for (ActionListener listener : listeners) {
                    button.removeActionListener(listener);
                }

                gridMatrixButtonsPanel.add(button);
                button.setPreferredSize(new Dimension(74, 74));
                //ConfForEachButton(gridMatrixButtonsPanel,button);
            }
        }

    }
//    private void ConfForEachButton (JPanel matrix,MatrixButton btn){
//        btn.setPreferredSize(new Dimension(74, 74));
//        if (!btn.getImagepath().isEmpty()){
//            Image image = IMG.toImage(Objects.requireNonNull(getClass().getResource(btn.getImagepath())));
//            // Escalar la imagen al tamaño del botón
//            Image scaledImage = image.getScaledInstance(btn.getWidth()-20,btn.getHeight(), Image.SCALE_SMOOTH);
//            btn.setEnabled(true);
//            btn.setText("");
//            btn.setIcon(new ImageIcon(scaledImage));
//            if (btn.getIdentifier()<50){
//                btn.setFilter(new Color(255,0,0,100));
//                btn.setEntity(team1,btn.getImagepath());
//            }
//            else{
//                btn.setFilter(new Color(0,0,255,100));
//                btn.setEntity(team2,btn.getImagepath());
//            }
//            btn.revalidate();
//            btn.repaint();
//        }
//        matrix.add(btn);
//    }
}