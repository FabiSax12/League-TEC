package view.components;

import models.Team;
import models.Tower;
import models.Character;

import javax.swing.*;
import java.awt.*;

/**
 * A custom JButton component used to display and manage matrix-style game elements such as characters and towers.
 *
 * <p>This button supports displaying scaled images, applying transparent filters, and managing tooltips
 * for characters and towers. It also allows setting identifiers and managing entities (characters or towers).</p>
 */
public class MatrixButton extends JButton {
    private byte identifier = 0;
    private String characterImagePath="";
    private Color filter=null;
    private Character character = null;
    private Tower tower = null;
    private ImageIcon icon=null;

    /**
     * Constructs a MatrixButton with default styles.
     */
    public MatrixButton() {
        setFocusPainted(false);
        setBackground(new Color(220, 220, 220));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    /**
     * Paints the button component, drawing the image and applying a filter if one is set.
     *
     * @param g the Graphics object used for painting.
     */
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

    /**
     * Sets an ImageIcon to be displayed on the button.
     *
     * @param icon the ImageIcon to display.
     */
    public void setIcon(ImageIcon icon) {
        this.icon = icon;
        repaint();
    }

    /**
     * Applies a transparent color filter to the button.
     *
     * @param colorTransparente the transparent color filter to apply.
     */
    public void setFilter(Color colorTransparente) {
        this.filter = colorTransparente;
        repaint();
    }

    /**
     * Sets the identifier for the button.
     *
     * @param identifier the identifier to set.
     */
    public void setIdentifier(byte identifier){this.identifier=identifier;}

    /**
     * Removes the currently applied filter.
     */
    public void removeFilter() {
        this.filter = null;
        repaint();
    }

    /**
     * Sets the image path for the character and updates the icon.
     *
     * @param path the path of the image.
     */
    public void setImagepath(String path){
        if ((path.isEmpty())||(path==null)){setIcon(null);this.characterImagePath="";}
        else{
            this.characterImagePath=path;
            ImageIcon newIcon = new ImageIcon(path);
            setIcon(newIcon);
        }
    }

    /**
     * Associates a character with the button and generates a tooltip for the character.
     *
     * @param character the character to associate with the button.
     */
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

    /**
     * Associates a tower with the button and generates a tooltip for the tower.
     *
     * @param tower the tower to associate with the button.
     */
    public void setTower(Tower tower){
        if (tower == null) {
            this.tower = null;
            setToolTipText(null);
        } else {
            this.tower = tower;
            setToolTipText(generaTooltipTower(tower));
        }
    }

    /**
     * Sets the entity (character or tower) based on the team and image path.
     *
     * @param team the team containing characters and towers.
     * @param pathImageExpected the expected image path for the entity.
     */
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

    /**
     * Generates a tooltip for the given character.
     *
     * @param character the character to generate the tooltip for.
     * @return the tooltip as a String.
     */
    private String generaTooltipCharacter(Character character) {
        return "<html>"
                + "<b><i>Nombre:</i></b> " + character.getName() + "<br>"
                + "<b><i>Puntos de vida:</i></b> " + character.getHealth() + "<br>"
                + "<b><i>Maná:</i></b> " + character.getMana() + "<br>"
                + "<b><i>Daño:</i></b> " + character.getDamage() + "<br>"
                + "<b><i>Elemento:</i></b> " + character.getElement() + "<br>"
                + "<b><i>Defensa:</i></b> " + character.getDefense() + "<br>"
                + "<b><i>Movimientos:</i></b> " + character.getMovements() + "<br>"
                + "</html>";
    }

    /**
     * Generates a tooltip for the given tower.
     *
     * @param tower the tower to generate the tooltip for.
     * @return the tooltip as a String.
     */
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


