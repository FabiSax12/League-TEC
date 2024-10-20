package view.components;

import models.Entity;
import models.Team;
import models.Tower;
import models.Character;
import utils.IMG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * A custom JButton component used to display and manage matrix-style game elements such as characters and towers.
 *
 * <p>This button supports displaying scaled images, applying transparent filters, and managing tooltips
 * for characters and towers. It also allows setting identifiers and managing entities (characters or towers).</p>
 */
public class MatrixButton extends JButton {
    private byte identifier;
    private Color filter;
    private Entity entity;
    private ImageIcon icon;

    /**
     * Constructs a MatrixButton with default styles.
     */
    public MatrixButton() {
        setFocusPainted(false);
        setBackground(new Color(220, 220, 220));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.filter = null;
        this.identifier = 0;
        this.icon = null;
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

    public ImageIcon getIcon(){return icon;}

    public byte getIdentifier() {return identifier;}

    public Color getFilter(){
        return filter;
    }

    public Entity getEntity(){
        return entity;
    }

    public void setEntity(Entity entity, Team t1, Team t2){
        if (entity == null) {
            removeEntity();
        } else {
            this.entity = entity;
            setToolTipText(Tooltip.create(entity));
            Image image = IMG.toImageAndScale(entity.getSpritePath(), this.getWidth(),this.getHeight());
            setIcon(new ImageIcon(image));
            resetFilter(t1, t2);
        }

        revalidate();
        repaint();
    }

    public void removeEntity() {
        this.entity = null;
        setToolTipText(null);
        setIcon(null);
        this.removeFilter();
        revalidate();
        repaint();
    }

    public void resetFilter(Team t1, Team t2) {
        if (this.entity == null) removeFilter();

        else if (t1.getEntities().contains(this.entity)) {
            this.setFilter(new Color(255,0,0,100));
        } else if (t2.getEntities().contains(this.entity)) {
            this.setFilter(new Color(0,0,255,100));
        }
    }

    public void resetBackground() {
        setBackground(new Color(220, 220, 220));
    }

    public void removeActionListeners() {
        for (ActionListener listener : getActionListeners()) {
            removeActionListener(listener);
        }
    }

    public void refresh() {
        setToolTipText(Tooltip.create(this.entity));
        revalidate();
        repaint();
    }
}

abstract class Tooltip {
    public static String create(Entity entity) {
        if (entity instanceof Character) {
            return "<html>"
                    + "<b><i>Nombre:</i></b> " + ((Character) entity).getName() + "<br>"
                    + "<b><i>Puntos de vida:</i></b> " + entity.getHealth() + "<br>"
                    + "<b><i>Maná:</i></b> " + ((Character) entity).getMana() + "<br>"
                    + "<b><i>Daño:</i></b> " + entity.getDamage() + "<br>"
                    + "<b><i>Elemento:</i></b> " + ((Character) entity).getElement() + "<br>"
                    + "<b><i>Defensa:</i></b> " + entity.getDefense() + "<br>"
                    + "<b><i>Movimientos:</i></b> " + ((Character) entity).getMovements() + "<br>"
                    + "</html>";
        } else if (entity instanceof Tower) {
            return "<html>"
                    + "<b><i>Nombre:</i></b> Torre<br>"
                    + "<b><i>Puntos de vida:</i></b> " + entity.getHealth() + "<br>"
                    + "<b><i>Daño:</i></b> " + entity.getDamage() + "<br>"
                    + "</html>";
        }

        return null;
    }
}

