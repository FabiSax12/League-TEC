package utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;


public abstract class IMG {
    public static Image toImage(String imgPath){
        ImageIcon characterIcon = new ImageIcon(imgPath);
        Image originalImage = characterIcon.getImage();
        return getScaledImage(originalImage, 200, 200);
    }
    public static Image toImage(URL imgPath){
        ImageIcon characterIcon = new ImageIcon(imgPath);
        Image originalImage = characterIcon.getImage();
        return getScaledImage(originalImage, 200, 200);
    }

    private static Image getScaledImage(Image srcImg, int w, int h) {
        BufferedImage scaledImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = scaledImage.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawImage(srcImg, 0, 0, w, h, null);
        g2d.dispose();

        return scaledImage;
    }
}
