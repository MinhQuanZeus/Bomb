package controllers.enemy_behavior;

import models.EnemyModel;
import models.GameModel;
import models.PlayerModel;
import utils.Utils;
import views.AutoLoadPic;
import views.EnemyView;

import java.awt.*;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by l on 3/10/2017.
 */
public class RandomStupidMoveBehavior extends EnemyMoveBehavior {
    private String lastMove = "";
    private int drawCount = 1;
    private long lastTime;
    private long delay = 1000;

    private EnemyView view;

    @Override
    public void move(EnemyModel model, EnemyView view, PlayerModel playerModel, Vector<GameModel> gameModels) {
        this.view = view;
        lastTime = System.currentTimeMillis();

        int x1 = model.getX();
        int y1 = model.getY();

        if (lastMove.equals("")) {
            switch (Utils.getRandom(4)) {
                case (0): {
                    x1 += model.getSpeed();
                    break;
                }
                case (1): {
                    x1 -= model.getSpeed();
                    break;
                }
                case (2): {
                    y1 += model.getSpeed();
                    break;
                }
                case (3): {
                    y1 -= model.getSpeed();
                    break;
                }
            }
        } else {
            switch (lastMove) {
                case ("len"): {
                    y1 -= model.getSpeed();
                    break;
                }
                case ("xuong"): {
                    y1 += model.getSpeed();
                    break;
                }
                case ("trai"): {
                    x1 -= model.getSpeed();
                    break;
                }
                case ("phai"): {
                    x1 += model.getSpeed();
                    break;
                }
            }
        }

        Rectangle r = new Rectangle(x1, y1, EnemyModel.WIDTH, EnemyModel.HEIGHT);

        for(int i = 0;i < gameModels.size();i++){
            if (gameModels.get(i).getRect().intersects(r)) {
                setImage();
                lastMove = "";
                return;
            }
        }

        if (x1 != model.getX()) {

            if (model.getX() > x1) {
                lastMove = "trai";
                model.moveLeft();
            } else {
                lastMove = "phai";
                model.moveRight();
            }
        } else {
            if (model.getY() > y1) {
                lastMove = "len";
                model.moveUp();
            } else {
                lastMove = "xuong";
                model.moveDown();
            }
        }

        setImage();
    }


    public void setImage() {
        if (lastMove.equals("")) {
            view.setImage(AutoLoadPic.enemyDuckImages.get("xuong" + drawCount));
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastTime >  delay) {
                lastTime = currentTime;
                drawCount++;
                if(drawCount >= 3){
                    drawCount = 0;
                }
            }
        }
        view.setImage(AutoLoadPic.enemyDuckImages.get(lastMove + drawCount));
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime >  delay) {
            lastTime = currentTime;
            drawCount++;
            if(drawCount >= 3){
                drawCount = 0;
            }
        }
    }
}
