package view.components;

import models.Team;
import models.Tower;
import models.Character;

import javax.swing.*;
import java.awt.*;

public class MatrixButton extends JButton {
    private byte identifier = 0;
    private String characterImagePath="";
    private Color filter=null;
    private Character character = null;
    private Tower tower = null;
    private ImageIcon icon=null;


    public MatrixButton() {
        setFocusPainted(false);
        setBackground(new Color(220, 220, 220));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibujar la imagen escalada
        if (icon != null) {
            Image scaledImage = icon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT);
            g.drawImage(scaledImage, 0, 0, getWidth(), getHeight(), this);
        }

        // Dibujar el filtro si está aplicado
        if (filter != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(filter);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.dispose();
        }
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
        repaint();
    }

    public void setFilter(Color colorTransparente) {
        this.filter = colorTransparente;
        repaint();
    }

    public void setIdentifier(byte identifier){this.identifier=identifier;}

    public void removeFilter() {
        this.filter = null;
        repaint();
    }

    public void setImagepath(String path){
        if ((path.isEmpty())||(path==null)){setIcon(null);this.characterImagePath="";}
        else{
            this.characterImagePath=path;
            ImageIcon newIcon = new ImageIcon(path);
            setIcon(newIcon);
        }
    }

    public void setCharacter(Character character){
        if (character == null) {
            this.character = null;
            setToolTipText(null);
        } else {
            this.character = character;
            setToolTipText(generaTooltipCharacter(character));
            System.out.println("character agregado...");
        }
    }

    public void setTower(Tower tower){
        if (tower == null) {
            this.tower = null;
            setToolTipText(null);
        } else {
            this.tower = tower;
            setToolTipText(generaTooltipTower(tower));
        }
    }

    public void setEntity(Team team,String pathImageExpected) {
        for (Character character:team.getCharacters()){
            if (character.getSpritePath().equals(pathImageExpected)){
                this.character = character;
                setToolTipText(generaTooltipCharacter(character));
                return;
            }
        }
        for (Tower tower:team.getTowers()){
            if (tower.getSpritePath().equals(pathImageExpected)){
                this.tower = tower;
                setToolTipText(generaTooltipTower(tower));
                return;
            }
        }

    }

    public ImageIcon getIcon(){return icon;}

    public byte getIdentifier() {return identifier;}

    public String getImagepath(){return characterImagePath;}

    public Character getCharacter() {return character;}

    public Tower getTower() {return tower;}

    private String generaTooltipCharacter(Character character) {
        return "<html>"
                + "<b><i>Nombre:</i></b> " + character.getName() + "<br>"
                + "<b><i>Puntos de vida:</i></b> " + character.getHealth() + "<br>"
                + "<b><i>Maná:</i></b> " + character.getMana() + "<br>"
                + "<b><i>Daño:</i></b> " + character.getDamage() + "<br>"
                + "<b><i>Elemento:</i></b> " + character.getElement() + "<br>"
                + "<b><i>Movimientos:</i></b> " + character.getMovements() + "<br>"
                + "</html>";
    }

    private String generaTooltipTower(Tower tower) {
        return "<html>"
                + "<b><i>Nombre:</i></b> Torre<br>"
                + "<b><i>Puntos de vida:</i></b> " + tower.getHealth() + "<br>"
                + "<b><i>Daño:</i></b> " + tower.getDamage() + "<br>"
                + "</html>";
    }

    public void updateEntityInfo(){
        if (character!=null){setToolTipText(generaTooltipCharacter(character));}
        else{setToolTipText(generaTooltipTower(tower));}

    }


    public Color getFilter(){return filter;}

    public int getEntityDamage(){
        if (character!=null){return character.getDamage();}
        else{return tower.getDamage();}
    }
}


