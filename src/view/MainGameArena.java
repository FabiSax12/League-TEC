package view;

import models.Team;
import view.components.MatrixButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


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