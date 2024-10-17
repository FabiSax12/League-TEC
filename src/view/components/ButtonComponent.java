package view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Custom JButton component with predefined styles and color themes.
 *
 * <p>This class allows for the creation of buttons with a specific font and customizable background colors.
 * It also includes custom mouse UI behavior, such as changing the cursor on hover.</p>
 */
public class ButtonComponent extends JButton {

    /**
     * Constructs a button with the specified text and default style.
     *
     * <p>The button is styled with a default background color and custom font.</p>
     *
     * @param text the text to display on the button.
     */
    public ButtonComponent(String text) {
        setText(text);
        setFont(new Font("Consolas", Font.PLAIN, 16));
        setFocusPainted(false);
        setBackground(new Color(220, 220, 220));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mouseUI();
    }

    /**
     * Constructs a button with the specified text and color theme.
     *
     * <p>The button can be styled with predefined color schemes such as blue, green, or red.</p>
     *
     * @param text  the text to display on the button.
     * @param color the custom color theme to apply to the button.
     */
    public ButtonComponent(String text, CustomColors color) {
        setText(text);
        setFont(new Font("Consolas", Font.PLAIN, 16));
        setFocusPainted(false);

        switch (color) {
            case DEFAULT:
                setBackground(new Color(220, 220, 220));
                break;
            case BLUE:
                setBackground(new Color(0, 111, 238));
                setForeground(Color.WHITE);
                break;
            case GREEN:
                setBackground(new Color(23, 201, 100));
                setForeground(Color.BLACK);
                break;
            case RED:
                setBackground(new Color(243, 18, 96));
                setForeground(Color.WHITE);
                break;
        }

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mouseUI();
    }

    /**
     * Sets up the mouse UI interactions, such as changing the cursor to a hand when hovering over the button.
     */
    private void mouseUI() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });
    }
}