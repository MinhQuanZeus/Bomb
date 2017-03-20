package controllers.enemy_behavior.move;

import controllers.EnemyController;
import models.EnemyModel;
import models.PlayerModel;
import views.AutoLoadPic;
import views.EnemyView;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by l on 3/10/2017.
 */
public class EnemyMoveBehavior {
    protected EnemyController enemyController;
    protected EnemyModel model;
    protected EnemyView view;
    protected EnemyController.EnemyType type;

    protected int drawCount = 0;
    protected int howManyPicOnAMove = 3;
    protected String moveDirection = "";

    protected long lastTime = System.currentTimeMillis();
    protected long delay = 200;

    protected boolean done;

    // dùng cho việc chuyển sang đứng yên và bắn sau thời gian nhất định
    protected long delayToChangeMove = 0;
    protected long timeStartThisMove = System.currentTimeMillis();

    public boolean isDone() {
        return done;
    }

    public void setHowManyPicOnAMove(int howManyPicOnAMove) {
        this.howManyPicOnAMove = howManyPicOnAMove;
    }

    public void move(EnemyModel model, EnemyView view, PlayerModel playerModel, EnemyController.EnemyType type, EnemyController enemyController) {
        this.view = view;
        this.type = type;
        this.enemyController = enemyController;
        this.model = model;
        switch (type) {
            case FIRE_HEAD: {
                howManyPicOnAMove = 2;
                break;
            }
        }
    }

    public void setImage() {
        HashMap<String, Image> map = new HashMap<>();

        map = AutoLoadPic.imageEnemyHashMapFactory(type);

        if (moveDirection.equals("")) {
            view.setImage(map.get("xuong0"));
        }
        view.setImage(map.get(moveDirection + drawCount));
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime > delay) {
            lastTime = currentTime;
            drawCount++;
            if (drawCount >= howManyPicOnAMove) {
                drawCount = 0;
            }
        }
    }

    public String getMoveDirection() {
        return moveDirection;
    }
}
