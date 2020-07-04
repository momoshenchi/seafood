package control;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ReadImage {

    private static final String IMG_PATH = "D:\\Program Files (x86)\\java\\workspace\\bighomework\\seafood\\cn.zucc\\image\\";
    public  static ImageIcon read(String name)
    {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(IMG_PATH +name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon icon = new ImageIcon(img);
        return  icon;
    }
}
