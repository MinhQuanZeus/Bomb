package controllers.enemy_controller;

import controllers.EnemyController;
import controllers.GameController;
import controllers.enemy_behavior.EnemyMoveBehavior;
import controllers.enemy_behavior.RandomStupidMoveBehavior;
import models.EnemyModel;
import models.GameModel;
import models.PlayerModel;
import views.AutoLoadPic;
import views.EnemyView;
import views.GameView;

import java.util.Vector;

/**
 * Created by l on 3/10/2017.
 */
public class Enemy_Duck_Controller extends EnemyController{
    public static final int ENEMY_DUCK_DELAY_DRAW = 500;
    protected long lastTime = System.currentTimeMillis();

    public Enemy_Duck_Controller(int x, int y, int speed, PlayerModel playerModel, Vector<GameModel>gameModels){
        super(new EnemyModel(x,y,speed),new EnemyView(AutoLoadPic.enemyDuckImages.get("xuong0")),playerModel,gameModels);
        enemyMoveBehavior = new RandomStupidMoveBehavior();
    }

    @Override
    public void run()
    {
        int x1 = model.getX();
        int y1 = model.getY();
        enemyMoveBehavior.move((EnemyModel) model,(EnemyView) view,null,gameModels);

        String move = "";

        if(x1 == model.getX() && y1 == model.getY()){
            move = "";
            setImage(move);
            return;
        }

        if(x1 == model.getX()){
            if(y1 < model.getY()){
                move = "xuong";
            }else if(y1 > model.getY()){
                move = "len";
            }
        }else if(y1 == model.getY()){
            if(x1 < model.getX()){
                move = "phai";
            }else if(x1 > model.getX()){
                move = "trai";
            }
        }

        setImage(move);
    }

    public void setImage(String move) {
        if (move.equals("")) {
            view.setImage(AutoLoadPic.enemyDuckImages.get("xuong0"));
        }
        view.setImage(AutoLoadPic.enemyDuckImages.get(move + drawCount));
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime >  ENEMY_DUCK_DELAY_DRAW) {
            lastTime = currentTime;
            drawCount++;
            if(drawCount >= 3){
                drawCount = 0;
            }
        }
    }
}
