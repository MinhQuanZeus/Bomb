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
    public static HashMap<String,Image> enemy_Duck_Image_Hashmap;
    public static HashMap<String,Image> explosionImageMap;
    public static HashMap<String,Image> enemy_SlimJellyHead_ImageMap;

    public static void init(){
        enemy_Duck_Image_Hashmap = new HashMap<>();
        explosionImageMap = new HashMap<>();
        enemy_SlimJellyHead_ImageMap = new HashMap<>();

        int c =0;
        for(int i = 0;i < 11;i++){
            if(i >= 0 && i < 3){
                enemy_Duck_Image_Hashmap.put("xuong" + c, Utils.loadImageFromRes("Enemies/" + i));
                System.out.println(Utils.loadImageFromRes("Enemies/" + i));
            }else if(i >= 3 && i <6){
                enemy_Duck_Image_Hashmap.put("len" + c,Utils.loadImageFromRes("Enemies/" + i));
            }else if(i >= 6 && i < 8){
                if(i == 7){
                    enemy_Duck_Image_Hashmap.put("phai" + c,Utils.loadImageFromRes("Enemies/" + i));
                    c++;
                    enemy_Duck_Image_Hashmap.put("phai" + c,Utils.loadImageFromRes("Enemies/" + i));
                }else{
                    enemy_Duck_Image_Hashmap.put("phai" + c,Utils.loadImageFromRes("Enemies/" + i));
                }
            }else{
                enemy_Duck_Image_Hashmap.put("trai" + c,Utils.loadImageFromRes("Enemies/" + i));
            }
            c++;
            if(c == 3){
                c = 0;
            }
        }

        c = 0;
        BufferedImage explosionImage = (BufferedImage) Utils.loadImageFromRes("Enemies/explosion");
        for(int i = 0;i <= 2;i++){
            for(int j = 0;j <= 2;j++){
                explosionImageMap.put("explosion"+c,explosionImage.getSubimage(170*j,i*170,170,170));
                c += 1;
            }
        }

        enemy_SlimJellyHead_ImageMap.put("xuong0",Utils.loadImageFromRes("Enemies/110"));
        enemy_SlimJellyHead_ImageMap.put("xuong1",Utils.loadImageFromRes("Enemies/113"));
        enemy_SlimJellyHead_ImageMap.put("xuong2",Utils.loadImageFromRes("Enemies/114"));
        enemy_SlimJellyHead_ImageMap.put("phai0",Utils.loadImageFromRes("Enemies/111"));
        enemy_SlimJellyHead_ImageMap.put("phai1",Utils.loadImageFromRes("Enemies/112"));
        enemy_SlimJellyHead_ImageMap.put("phai2",Utils.loadImageFromRes("Enemies/111"));
        enemy_SlimJellyHead_ImageMap.put("len0",Utils.loadImageFromRes("Enemies/115"));
        enemy_SlimJellyHead_ImageMap.put("len1",Utils.loadImageFromRes("Enemies/116"));
        enemy_SlimJellyHead_ImageMap.put("len2",Utils.loadImageFromRes("Enemies/115"));
        enemy_SlimJellyHead_ImageMap.put("trai0",Utils.loadImageFromRes("Enemies/110"));
        enemy_SlimJellyHead_ImageMap.put("trai1",Utils.loadImageFromRes("Enemies/113"));
        enemy_SlimJellyHead_ImageMap.put("trai2",Utils.loadImageFromRes("Enemies/114"));
    }

    public static HashMap<String,Image> imageHashMapFactory(EnemyController.EnemyType type){
        switch (type){
            case DUCK:{
                return enemy_Duck_Image_Hashmap;
            }
            case SLIM_JELLY_HEAD:{
                return enemy_SlimJellyHead_ImageMap;
            }
        }

        return null;
    }
}
