import controller.CharacterSelectionController;
import database.DB;
import models.*;
import view.MainGameWindow;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        DB.loadData();
        new MainGameWindow();
    }
}

//import view.AnimatedBackgroundPanel;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;


//public class Main{
//    public static void main(String[] args) {
//        // Crear el JFrame
//        JFrame frame = new JFrame("Fondo animado");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(800, 600);
//
//        // Crear el JPanel con el fondo animado
//        AnimatedBackgroundPanel animatedPanel = new AnimatedBackgroundPanel("/assets/sandAvatar.gif");
//
//        // Añadir el panel al frame
//        frame.add(animatedPanel);
//
//        // Hacer visible el frame
//        frame.setVisible(true);
//    }
//}
