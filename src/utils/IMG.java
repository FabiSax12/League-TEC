package utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

/**
 * Utility class for handling image scaling and conversion.
 *
 * <p>This class provides methods to load images from file paths or URLs and scale them to a specific size.</p>
 */
public abstract class IMG {

    /**
     * Converts an image from a file path to a scaled image.
     *
     * <p>This method loads an image from the specified file path and returns a version of it scaled to 200x200 pixels.</p>
     *
     * @param imgPath the file path to the image.
     * @return the scaled image.
     */
    public static Image toImage(String imgPath){
        File imageFile = new File(System.getProperty("user.dir") + "\\src" + imgPath);

        if (imageFile.exists()) {
            ImageIcon characterIcon = new ImageIcon(imageFile.getAbsolutePath());
            return characterIcon.getImage();
        } else {
            System.out.println("Imagen no encontrada: " + imgPath);
            return null;
        }
    }

    /**
     * Converts an image from a file path to a scaled image.
     *
     * <p>This method loads an image from the specified file path and returns a version of it scaled to 200x200 pixels.</p>
     *
     * @param imgPath the file path to the image.
     * @return the scaled image.
     */
    public static Image toImageAndScale(String imgPath, int width, int height){
        File imageFile = new File(System.getProperty("user.dir") + "\\src" + imgPath);

        if (imageFile.exists()) {
            ImageIcon characterIcon = new ImageIcon(imageFile.getAbsolutePath());
            Image originalImage = characterIcon.getImage();
            return getScaledImage(originalImage, width, height);
        } else {
            System.out.println("Imagen no encontrada: " + imgPath);
            return null;
        }
    }

    /**
     * Converts an image from a URL to a scaled image.
     *
     * <p>This method loads an image from the specified URL and returns a version of it scaled to 200x200 pixels.</p>
     *
     * @param imgPath the URL to the image.
     * @return the scaled image.
     */
    public static Image toImage(URL imgPath){
        ImageIcon characterIcon = new ImageIcon(imgPath);
        Image originalImage = characterIcon.getImage();
        return getScaledImage(originalImage, 200, 200);
    }

    /**
     * Scales an image to the specified width and height.
     *
     * <p>Uses rendering hints for better quality scaling, such as bilinear interpolation and anti-aliasing.</p>
     *
     * @param srcImg the source image to be scaled.
     * @param w the width to scale the image to.
     * @param h the height to scale the image to.
     * @return the scaled image.
     */
    public static Image getScaledImage(Image srcImg, int w, int h) {
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
