package controllers.boss_behavior.attack;

import controllers.BomdEnemyController;
import gui.GameFrame;
import manager.MapManager;
import models.BomdEnemyModel;
import models.BossEnemyModel;
import models.ItemMapModel;
import models.PlayerModel;
import utils.Utils;
import views.AutoLoadPic;
import views.BomdEnemyView;
import views.BossEnemyView;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by l on 3/25/2017.
 */
public class AttackThrowBomd extends BossAttackBehavior {

    boolean a = true;

    private ArrayList<BomPosition> bomPositionList;
    private long lastTimeThrowBom = System.currentTimeMillis();
    private long delayTimeThrowBom = 3000;
    private int explosionSize = 2;
    private int bomQuantity = 4;

    @Override
    public void attack(BossEnemyModel bossEnemyModel, BossEnemyView bossEnemyView, PlayerModel playerModel) {
        super.attack(bossEnemyModel, bossEnemyView, playerModel);
        setImage();
        generateBomQuantityAndExplosionSize();

        if (BomdEnemyController.BOMD_COUNT == 0) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastTimeThrowBom >= delayTimeThrowBom) {
                lastTimeThrowBom = currentTime;
                bomPositionList = new ArrayList<>();

                boolean flag;
                int hang;
                int cot;
                for (int i = 0; i < bomQuantity; i++) {
                    flag = true;
                    do {

                        hang = Utils.getRandom(14);
                        cot = Utils.getRandom(14);
                    } while (MapManager.map[hang][cot] != 0);

                    BomPosition bomPosition = new BomPosition(hang,cot);

                    for(int j = 0;j < bomPositionList.size();j++){
                        if(bomPositionList.get(j).getHang() == hang && bomPositionList.get(j).getCot() == cot){
                            i-=1;
                            flag = false;
                            break;
                        }
                    }

                    if(true){
                        Rectangle r = new Rectangle(ItemMapModel.SIZE_TILED * cot,ItemMapModel.SIZE_TILED * hang,ItemMapModel.SIZE_TILED ,ItemMapModel.SIZE_TILED );
                        if(r.intersects(bossEnemyModel.getRectOfBossAndShadow())){
                            i -= 1;
                            flag = false;
                        }
                    }
                    if(flag){
                        bomPositionList.add(bomPosition);
                    }
                }

                for(int i = 0; i < bomPositionList.size();i++){

                    new BomdEnemyController(ItemMapModel.SIZE_TILED * bomPositionList.get(i).getCot(),0,ItemMapModel.SIZE_TILED * bomPositionList.get(i).getHang(),new BomdEnemyView(AutoLoadPic.enemy_weapons_ImageMap.get("bbomd0")),explosionSize);
                }
            }
        }
    }

    private void generateBomQuantityAndExplosionSize() {
        switch (bossEnemyModel.getHp()){
            case(4):{
                explosionSize = 3;
                bomQuantity = 5;
                break;
            }
            case(3):{
                explosionSize = 4;
                bomQuantity = 3;
                break;
            }
            case(2):{
                explosionSize = 5;
                bomQuantity = 3;
                break;
            }
            case(1):{
                explosionSize = 8;
                bomQuantity = 4;
                break;
            }
        }
    }


    class BomPosition {
        int hang;
        int cot;

        public BomPosition(int hang, int cot) {
            this.hang = hang;
            this.cot = cot;
        }

        public int getHang() {
            return hang;
        }

        public int getCot() {
            return cot;
        }

        public void setHang(int hang) {
            this.hang = hang;
        }

        public void setCot(int cot) {
            this.cot = cot;
        }
    }
}
