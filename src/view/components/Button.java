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
}
