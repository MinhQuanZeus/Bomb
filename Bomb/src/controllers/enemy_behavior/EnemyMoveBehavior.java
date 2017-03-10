package controllers.enemy_behavior;

import controllers.EnemyController;
import models.EnemyModel;
import models.GameModel;
import models.PlayerModel;
import views.AutoLoadPic;
import views.EnemyView;

import java.awt.*;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by l on 3/10/2017.
 */
public class EnemyMoveBehavior {

    protected EnemyView view;
    protected EnemyController.EnemyType type;
    protected int drawCount = 0;
    protected int howManyPicOnAMove = 3;
    protected String drawMove = "";

    protected long lastTime = System.currentTimeMillis();
    protected long delay = 170;

    protected boolean done;

    public boolean isDone() {
        return done;
    }

    public void setHowManyPicOnAMove(int howManyPicOnAMove) {
        this.howManyPicOnAMove = howManyPicOnAMove;
    }

    public void move(EnemyModel model, EnemyView view, PlayerModel playerModel, Vector<GameModel> gameModels, EnemyController.EnemyType type){
        this.view = view;
        this.type = type;

    }

    public void setImage() {
        HashMap<String,Image> map = new HashMap<>();

        map = AutoLoadPic.imageHashMapFactory(EnemyController.EnemyType.DUCK);

        if (drawMove.equals("")) {
            view.setImage(map.get("xuong0"));
        }
        view.setImage(map.get(drawMove + drawCount));
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime >  delay) {
            lastTime = currentTime;
            drawCount++;
            if(drawCount >= howManyPicOnAMove){
                drawCount = 0;
            }
        }
    }
}
