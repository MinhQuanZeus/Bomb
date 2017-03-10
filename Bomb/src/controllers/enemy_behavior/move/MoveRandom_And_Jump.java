package controllers.enemy_behavior.move;

import controllers.EnemyController;
import models.EnemyModel;
import models.GameModel;
import models.PlayerModel;
import utils.Utils;
import views.AutoLoadPic;
import views.EnemyView;

import java.awt.*;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by l on 3/11/2017.
 */
public class MoveRandom_And_Jump extends EnemyMoveBehavior{
    private String lastMove = "";
    private boolean jump = false;
    private int xOfModelJumpTo = -1;
    private int yOfModelJumpTo = -1;

    // xác định vừa mới  vào lần đầu hàm move
    private boolean beginState = true;

    // dành cho việc co ảnh khi nhảy qua vật và quay về kích thước đầu khi đến  ô cần
    private int modelHeight;

    @Override
    public void move(EnemyModel model, EnemyView view, PlayerModel playerModel, Vector<GameModel> gameModels, EnemyController.EnemyType type, EnemyController enemyController) {
        super.move(model, view, playerModel, gameModels, type, enemyController);
        if(beginState){
            beginState = false;
            modelHeight = model.getHeight();
        }

        if(jump){
            model.moveCorrectly(xOfModelJumpTo,yOfModelJumpTo);
            setImage();
            if(model.getX() == xOfModelJumpTo && model.getY() == yOfModelJumpTo){
                model.setHeight(modelHeight);
                jump = false;
                xOfModelJumpTo = -1;
                yOfModelJumpTo = -1;
            }
        }else{
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
                    jump = true;
                    GameModel g = gameModels.get(i);

                    switch (lastMove){
                        case("len"):{
                            xOfModelJumpTo = g.getX();
                            yOfModelJumpTo = g.getY() - g.getHeight();
                            break;
                        }
                        case("xuong"):{
                            xOfModelJumpTo = g.getX();
                            yOfModelJumpTo = g.getY() + g.getHeight();
                            break;
                        }
                        case("trai"):{
                            xOfModelJumpTo = g.getX() - g.getWidth();
                            yOfModelJumpTo = g.getY();
                            break;
                        }
                        case("phai"):{
                            xOfModelJumpTo = g.getX() + g.getWidth();
                            yOfModelJumpTo = g.getY();
                            break;
                        }
                    }
                    for(int j = 0;j < gameModels.size();j++){
                        if(gameModels.get(j).getRect().intersects(new Rectangle(xOfModelJumpTo,yOfModelJumpTo,model.getWidth(),model.getHeight()))){
                            jump = false;
                        }
                    }
                    lastMove = "";
                    return;
                }
            }

            if (x1 != model.getX()) {

                if (model.getX() > x1) {
                    lastMove = "trai";
                    drawMove = "trai";
                    model.moveLeft();
                } else {
                    lastMove = "phai";
                    drawMove = "phai";
                    model.moveRight();
                }
            } else {
                if (model.getY() > y1) {
                    lastMove = "len";
                    drawMove = "len";
                    model.moveUp();
                } else {
                    lastMove = "xuong";
                    drawMove = "xuong";
                    model.moveDown();
                }
            }


            setImage();
        }
    }

    @Override
    public void setImage() {
        if(jump){
            super.setImage();
            if(model.getHeight() < EnemyModel.HEIGHT - 10){
                model.setHeight(model.getHeight()+1);
            }else{
                model.setHeight(model.getHeight()-1);
            }
        }else{
            super.setImage();
        }
    }
}
