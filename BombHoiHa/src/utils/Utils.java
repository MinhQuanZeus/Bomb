package utils;

import controllers.ItemType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by QuanT on 3/9/2017.
 */
public class Utils {
    public static Image loadImageFromRes(String url) {
        try {
            Image image = ImageIO.read(new File("resources/" + url + ".png"));
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static int getRandom(int max) {
        Random random = new Random();
        int a;
        a = random.nextInt(max);
        return a;
    }


}
