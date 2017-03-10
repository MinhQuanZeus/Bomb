package views;

import utils.Utils;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by l on 3/10/2017.
 */
public class AutoLoadPic {
    public static HashMap<String,Image> enemyDuckImages;

    public static void init(){
        enemyDuckImages = new HashMap<>();

        int c =0;
        for(int i = 0;i < 11;i++){
            if(i >= 0 && i < 3){
                enemyDuckImages.put("xuong" + c, Utils.loadImageFromRes(i + ".png"));
            }else if(i >= 3 && i <6){
                enemyDuckImages.put("len" + c,Utils.loadImageFromRes(i + ".png"));
            }else if(i >= 6 && i < 8){
                if(i == 7){
                    enemyDuckImages.put("phai" + c,Utils.loadImageFromRes(i + ".png"));
                    c++;
                    enemyDuckImages.put("phai" + c,Utils.loadImageFromRes(i + ".png"));
                }else{
                    enemyDuckImages.put("phai" + c,Utils.loadImageFromRes(i + ".png"));
                }
            }else{
                enemyDuckImages.put("trai" + c,Utils.loadImageFromRes(i + ".png"));
            }
            c++;
            if(c == 3){
                c = 0;
            }
        }
    }
}
