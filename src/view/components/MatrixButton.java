package view.components;

import javax.swing.*;
import java.awt.*;

public class MatrixButton extends JButton {
    public int identifier = 0;
    public MatrixButton() {
        setText("  ");
        setFont(new Font("Arial", Font.PLAIN, 16));
        setFocusPainted(false);
        setBackground(new Color(220, 220, 220));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
    public int getIdentifier() {return identifier;}
}
