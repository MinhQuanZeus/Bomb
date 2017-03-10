package controllers.enemy_behavior.move;

import controllers.EnemyController;
import models.EnemyModel;
import models.GameModel;
import models.PlayerModel;
import utils.Utils;
import views.EnemyView;

import java.awt.*;
import java.util.Vector;

/**
 * Created by l on 3/10/2017.
 */
public class MoveRandomStupid extends EnemyMoveBehavior {
    // hướng đi trước
    private String lastMove = "";

    @Override
    public void move(EnemyModel model, EnemyView view, PlayerModel playerModel, Vector<GameModel> gameModels, EnemyController.EnemyType type,EnemyController enemyController) {
        super.move(model,view,playerModel,gameModels,type,enemyController);
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
