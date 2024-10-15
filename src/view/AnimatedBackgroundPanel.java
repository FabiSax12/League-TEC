package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimatedBackgroundPanel extends JPanel{
    private Image backgroundImage;
    private int backgroundX = 0;
    private Timer timer;

    // Constructor para cargar la imagen y configurar el temporizador
    public AnimatedBackgroundPanel(String imagePath) {
        backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibujar la imagen en movimiento
        if (backgroundImage != null) {
            // Mover la imagen horizontalmente
            g.drawImage(backgroundImage, backgroundX, 0, getWidth(), getHeight(), this);
            //g.drawImage(backgroundImage, backgroundX - getWidth(), 0, getWidth(), getHeight(), this);
        }
    }
}