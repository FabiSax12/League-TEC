package view.components;

import models.Team;
import models.Tower;
import models.Character;

import javax.swing.*;
import java.awt.*;

public class MatrixButton extends JButton {
    private byte identifier = 0;
    private String characterImagePath;
    private byte color=0;  // 0 = rojo, 1 = azul, 2 = verde, etc.
    private Character character = null;
    private Tower tower = null;
    private ImageIcon icon;

    public MatrixButton() {
        setFocusPainted(false);
        setBackground(new Color(220, 220, 220));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.characterImagePath="";
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (icon != null) {
            // Escalar el GIF animado para adaptarse al tamaño del botón
            Image scaledImage = icon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT);

            // Dibujar la imagen escalada
            g.drawImage(scaledImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
    public void setIcon(ImageIcon icon) {
        this.icon = icon;
        repaint();  // Asegurarse de que el botón se redibuje
    }
    public void setFilter(Color colorTransparente) {
        setContentAreaFilled(false); // Evita que el fondo se pinte automáticamente
        setOpaque(false);            // Asegura la transparencia

        // Sobrescribimos el método paintComponent del botón
        setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                super.paint(g, c);  // Pinta el botón normalmente

                // Obtenemos el objeto Graphics2D para permitir transparencia
                Graphics2D g2d = (Graphics2D) g.create();

                // Aplicar el color transparente
                g2d.setColor(colorTransparente);

                // Dibujamos un rectángulo del tamaño del botón con el color transparente
                g2d.fillRect(0, 0, c.getWidth(), c.getHeight());

                g2d.dispose();  // Liberar recursos
            }
        });
    }
    public void setIdentifier(byte identifier){this.identifier=identifier;}
    public void setImagepath(String path){
        if (path==null){setIcon(null);this.characterImagePath="";}
        else{this.characterImagePath=path;}
    }
    public void setColor(byte color){
        switch (color){
            case 0:
                setBackground(Color.LIGHT_GRAY);
                break;
            case 1:
                setBackground(Color.BLUE);
                break;
            case 2:
                setBackground(Color.RED);
                break;
            case 3:
                setBackground(Color.WHITE);
                break;
        }
    }
    public void setCharacter(Character character){
        if (character == null) {
            this.character = null;
            setToolTipText(null);
        }else{
            this.character = character;
            String tooltipText ="<html>"
                    + "<b><i>Nombre:</i></b> " + character.getName() + "<br>"
                    + "<b><i>Puntos de vida:</i></b> " + character.getHealth() + "<br>"
                    + "<b><i>Maná:</i></b> " + character.getMana() + "<br>"
                    + "<b><i>Daño:</i></b> " + character.getDamage() + "<br>"
                    + "<b><i>Elemento:</i></b> " + character.getElement() + "<br>"
                    + "<b><i>Movimientos:</i></b> " + character.getMovements() + "<br>"
                    + "</html>";
            setToolTipText(tooltipText);
            System.out.println("character agregado...");
        }
    }
    public void setTower(Tower tower){
        if (tower == null) {
            this.tower = null;
            setToolTipText(null);
        } else {
            this.tower = tower;
            String tooltipText = "<html>"
                    + "<b><i>Nombre:</i></b> Torre<br>"
                    + "<b><i>Puntos de vida:</i></b> " + tower.getHealth() + "<br>"
                    + "<b><i>Daño:</i></b> " + tower.getDamage() + "<br>"
                    + "</html>";
            setToolTipText(tooltipText);
        }
    }
    public void setEntity(Team team,String pathImageExpected) {
        for (Character character:team.getCharacters()){
            if (character.getSpritePath().equals(pathImageExpected)){
                this.character = character;
                String tooltipText ="<html>"
                        + "<b><i>Nombre:</i></b> " + character.getName() + "<br>"
                        + "<b><i>Puntos de vida:</i></b> " + character.getHealth() + "<br>"
                        + "<b><i>Maná:</i></b> " + character.getMana() + "<br>"
                        + "<b><i>Daño:</i></b> " + character.getDamage() + "<br>"
                        + "<b><i>Elemento:</i></b> " + character.getElement() + "<br>"
                        + "<b><i>Movimientos:</i></b> " + character.getMovements() + "<br>"
                        + "</html>";
                setToolTipText(tooltipText);
                return;
            }
        }
        for (Tower tower:team.getTowers()){
            if (tower.getSpritePath().equals(pathImageExpected)){
                this.tower = tower;
                String tooltipText = "<html>"
                        + "<b><i>Nombre:</i></b> Torre<br>"
                        + "<b><i>Puntos de vida:</i></b> " + tower.getHealth() + "<br>"
                        + "<b><i>Daño:</i></b> " + tower.getDamage() + "<br>"
                        + "</html>";
                setToolTipText(tooltipText);
                return;
            }
        }

    }
    public ImageIcon getIcon(){return icon;}
    public byte getIdentifier() {return identifier;}
    public String getImagepath(){return characterImagePath;}
    public byte getColor(){return color;}
    public Character getCharacter() {return character;}
    public Tower getTower() {return tower;}
}


