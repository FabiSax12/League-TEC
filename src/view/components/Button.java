package view.components;

import javax.swing.*;
import java.awt.*;

public class Button extends JButton{
    public Button(String text) {
        setText(text);
        setFont(new Font("Arial", Font.PLAIN, 16));
        setFocusPainted(false);
        setBackground(new Color(220, 220, 220));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    public Button(String text, CustomColors color) {
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
    }
}
