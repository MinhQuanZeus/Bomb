package models;

/**
 * Created by l on 3/25/2017.
 */
public class BomdEnemyModel extends GameModel {
    public static int BOMD_ENEMY_SPEED = 5;

    private int yMoveTo;

    private GameModel shadowModel;

    private boolean isStop;

    public BomdEnemyModel(int x, int y,int yMoveTo) {
        super(x, y, ItemMapModel.SIZE_TILED, ItemMapModel.SIZE_TILED);

        this.speed = BOMD_ENEMY_SPEED;
        this.yMoveTo = yMoveTo;

        shadowModel = new GameModel(x + 20,yMoveTo + 30,ItemMapModel.SIZE_TILED -40,ItemMapModel.SIZE_TILED - 30);

        isStop = false;
    }

    public void fallDown(){
        y += speed;

        if(Math.abs(y - yMoveTo) <= speed){
            y = yMoveTo;
            isStop = true;
        }
    }

    public boolean isStop() {
        return isStop;
    }


    public int getyMoveTo() {
        return yMoveTo;
    }

    public void calculateShadow(){
        int xShadow = shadowModel.getX();
        int widthShadow = shadowModel.getWidth();

        shadowModel.setX(xShadow - 1);
        shadowModel.setWidth(widthShadow + 2);

        if(shadowModel.getX() < this.x + 10){
            shadowModel.setX(x + 10);
            shadowModel.setWidth(ItemMapModel.SIZE_TILED - 20);
        }
    }

    public GameModel getShadowModel() {
        return shadowModel;
    }
}
