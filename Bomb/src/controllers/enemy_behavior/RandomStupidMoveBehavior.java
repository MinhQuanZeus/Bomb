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
    public String lastMove = "";

    @Override
    public void move(EnemyModel model, EnemyView view, PlayerModel playerModel, Vector<GameModel> gameModels) {
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

        if (x1 != model.getX()) {
            Rectangle r = new Rectangle(x1, y1, EnemyModel.WIDTH, EnemyModel.HEIGHT);
            Iterator i = gameModels.iterator();
            while (i.hasNext()) {
                GameModel g = (GameModel) i.next();
                if (g.getRect().intersects(r)) {
                    lastMove = "";
                    return;
                }
            }

            if (model.getX() > x1) {
                lastMove = "trai";
                model.moveDown();
            } else {
                lastMove = "phai";
                model.moveUp();
            }
        } else {
            Rectangle r = new Rectangle(x1, y1, EnemyModel.WIDTH, EnemyModel.HEIGHT);
            Iterator i = gameModels.iterator();
            while (i.hasNext()) {
                GameModel g = (GameModel) i.next();
                if (g.getRect().intersects(r)) {
                    lastMove = "";
                    return;
                }
            }

            if (model.getY() > x1) {
                lastMove = "len";
                model.moveDown();
            } else {
                lastMove = "xuong";
                model.moveUp();
            }
        }
    }


}
