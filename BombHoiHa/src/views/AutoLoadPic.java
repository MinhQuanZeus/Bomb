package views;

import controllers.EnemyController;
import utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * Created by l on 3/10/2017.
 */
public class AutoLoadPic {
    public static HashMap<String, Image> enemy_Duck_Image_ImageMap;
    public static HashMap<String, Image> explosionImageMap;
    public static HashMap<String, Image> enemy_SlimJellyHead_ImageMap;
    public static HashMap<String, Image> enemy_fireHead_ImageMap;
    public static HashMap<String, Image> enemy_weapons_ImageMap;

    public static void init() {
        enemy_Duck_Image_ImageMap = new HashMap<>();
        explosionImageMap = new HashMap<>();
        enemy_SlimJellyHead_ImageMap = new HashMap<>();
        enemy_fireHead_ImageMap = new HashMap<>();
        enemy_weapons_ImageMap = new HashMap<>();

        int c = 0;
        for (int i = 0; i < 11; i++) {
            if (i >= 0 && i < 3) {
                enemy_Duck_Image_ImageMap.put("xuong" + c, Utils.loadImageFromRes("Enemies/" + i));
                System.out.println(Utils.loadImageFromRes("Enemies/" + i));
            } else if (i >= 3 && i < 6) {
                enemy_Duck_Image_ImageMap.put("len" + c, Utils.loadImageFromRes("Enemies/" + i));
            } else if (i >= 6 && i < 8) {
                if (i == 7) {
                    enemy_Duck_Image_ImageMap.put("phai" + c, Utils.loadImageFromRes("Enemies/" + i));
                    c++;
                    enemy_Duck_Image_ImageMap.put("phai" + c, Utils.loadImageFromRes("Enemies/" + i));
                } else {
                    enemy_Duck_Image_ImageMap.put("phai" + c, Utils.loadImageFromRes("Enemies/" + i));
                }
            } else {
                enemy_Duck_Image_ImageMap.put("trai" + c, Utils.loadImageFromRes("Enemies/" + i));
            }
            c++;
            if (c == 3) {
                c = 0;
            }
        }

        c = 0;
        BufferedImage explosionImage = (BufferedImage) Utils.loadImageFromRes("Enemies/explosion");
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                explosionImageMap.put("explosion" + c, explosionImage.getSubimage(170 * j, i * 170, 170, 170));
                c += 1;
            }
        }

        enemy_SlimJellyHead_ImageMap.put("xuong0", Utils.loadImageFromRes("Enemies/110"));
        enemy_SlimJellyHead_ImageMap.put("xuong1", Utils.loadImageFromRes("Enemies/113"));
        enemy_SlimJellyHead_ImageMap.put("xuong2", Utils.loadImageFromRes("Enemies/114"));
        enemy_SlimJellyHead_ImageMap.put("phai0", Utils.loadImageFromRes("Enemies/111"));
        enemy_SlimJellyHead_ImageMap.put("phai1", Utils.loadImageFromRes("Enemies/112"));
        enemy_SlimJellyHead_ImageMap.put("phai2", Utils.loadImageFromRes("Enemies/111"));
        enemy_SlimJellyHead_ImageMap.put("len0", Utils.loadImageFromRes("Enemies/115"));
        enemy_SlimJellyHead_ImageMap.put("len1", Utils.loadImageFromRes("Enemies/116"));
        enemy_SlimJellyHead_ImageMap.put("len2", Utils.loadImageFromRes("Enemies/115"));
        enemy_SlimJellyHead_ImageMap.put("trai0", Utils.loadImageFromRes("Enemies/110"));
        enemy_SlimJellyHead_ImageMap.put("trai1", Utils.loadImageFromRes("Enemies/113"));
        enemy_SlimJellyHead_ImageMap.put("trai2", Utils.loadImageFromRes("Enemies/114"));

        enemy_fireHead_ImageMap.put("xuong0", Utils.loadImageFromRes("Enemies/98"));
        enemy_fireHead_ImageMap.put("xuong1", Utils.loadImageFromRes("Enemies/99"));
        enemy_fireHead_ImageMap.put("phai0", Utils.loadImageFromRes("Enemies/98"));
        enemy_fireHead_ImageMap.put("phai1", Utils.loadImageFromRes("Enemies/99"));
        enemy_fireHead_ImageMap.put("len0", Utils.loadImageFromRes("Enemies/98"));
        enemy_fireHead_ImageMap.put("len1", Utils.loadImageFromRes("Enemies/99"));
        enemy_fireHead_ImageMap.put("trai0", Utils.loadImageFromRes("Enemies/98"));
        enemy_fireHead_ImageMap.put("trai1", Utils.loadImageFromRes("Enemies/99"));
        enemy_fireHead_ImageMap.put("tancong0", Utils.loadImageFromRes("Enemies/94"));
        enemy_fireHead_ImageMap.put("tancong1", Utils.loadImageFromRes("Enemies/95"));
        enemy_fireHead_ImageMap.put("tancong2", Utils.loadImageFromRes("Enemies/96"));

        enemy_weapons_ImageMap.put("roundBullet", Utils.loadImageFromRes("Enemies/round"));
    }

    public static HashMap<String, Image> imageEnemyHashMapFactory(EnemyController.EnemyType type) {
        switch (type) {
            case DUCK: {
                return enemy_Duck_Image_ImageMap;
            }
            case SLIM_JELLY_HEAD: {
                return enemy_SlimJellyHead_ImageMap;
            }
            case FIRE_HEAD: {
                return enemy_fireHead_ImageMap;
            }
        }

        return null;
    }
}
