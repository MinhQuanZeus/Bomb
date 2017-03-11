package controllers.enemy_behavior.attack;

import controllers.ControllerManager;
import controllers.EnemyController;
import controllers.enemy_behavior.move.MoveRandomStupid;
import models.EnemyModel;
import models.GameModel;
import models.PlayerModel;
import utils.Utils;
import views.EnemyView;

import java.awt.*;
import java.util.Vector;

/**
 * Created by l on 3/11/2017.
 */
public class AttackShootABullet extends EnemyAttackBehavior {
    protected String moveDirection = "";
    /*
    giả là đã có đạn
    TO DO: thay băng đạn thật
    */
    private Vector<String> bullets;
    private int delayShoot = 2000;
    private long lastTimeShoot = System.currentTimeMillis();
    // số phát bắn
    private int shootNumber = 2;
    // số phát đã bắn
    private int shootedNumber = 0;

    @Override
    public void attack(EnemyModel model, EnemyView view, PlayerModel playerModel, Vector<GameModel> gameModels, EnemyController.EnemyType type, EnemyController enemyController, ControllerManager controllerManager) {
        super.attack(model, view, playerModel, gameModels, type, enemyController,controllerManager);

        int widthBullet = 20;
        int heightBullet = 20;
        int xBullet = 0;
        int yBullet = 0;

        long currentTime = System.currentTimeMillis();
        if(currentTime - lastTimeShoot >= delayShoot){
            moveDirection = enemyController.getEnemyMoveBehavior().getMoveDirection();
            if(moveDirection.equals("")){
                moveDirection = "xuong";
            }

            boolean shoot = true;
            switch (moveDirection){
                case("len"):{
                    xBullet = model.getX() + model.getX()/2 - widthBullet/2;
                    yBullet = model.getY() - heightBullet;
                }
                case("xuong"):{
                    xBullet = model.getX() + model.getX()/2 - widthBullet/2;
                    yBullet = model.getY() + heightBullet;
                }
                case("trai"):{
                    xBullet = model.getX() - widthBullet;
                    yBullet = model.getY() + model.getHeight()/2 - heightBullet/2;
                }
                case("phai"):{
                    xBullet = model.getX() + widthBullet;
                    yBullet = model.getY() + model.getHeight()/2 - heightBullet/2;
                }
            }

            Rectangle r = new Rectangle(xBullet,yBullet,widthBullet,heightBullet);
            for(int i = 0;i < gameModels.size();i++){
                if(gameModels.get(i).getRect().intersects(r)){
                    shoot = false;
                    break;
                }
            }

            if(shoot){
                lastTimeShoot = currentTime;
                shootedNumber++;
                System.out.println(lastTimeShoot + " shoot!!!!!");
            }else{
                switch (Utils.getRandom(4)){
                    case (0):{
                        moveDirection = "xuong";
                        break;
                    }
                    case (1):{
                        moveDirection = "len";
                        break;
                    }
                    case (2):{
                        moveDirection = "trai";
                        break;
                    }
                    case (3):{
                        moveDirection = "phai";
                        break;
                    }
                }
            }

            if(shootNumber == shootedNumber){
                enemyController.setEnemyAttackBehavior(new AttackNothing());
                enemyController.setEnemyMoveBehavior(new MoveRandomStupid());
            }
        }

        switch (type){
            case FIRE_HEAD:{
                setImage();
                break;
            }
        }
    }

    public void setShootNumber(int shootNumber) {
        this.shootNumber = shootNumber;
    }

    public void setDelayShoot(int delayShoot) {
        this.delayShoot = delayShoot;
    }
}
