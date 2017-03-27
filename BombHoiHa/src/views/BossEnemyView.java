package views;

import models.BossEnemyModel;
import models.GameModel;

import java.awt.*;

/**
 * Created by l on 3/24/2017.
 */
public class BossEnemyView extends GameView {
    private Image imageShadow;

    public BossEnemyView(Image image) {
        super(image);
    }

    public void setImageShadow(Image imageShadow) {
        this.imageShadow = imageShadow;
    }

    public void setImage(Image image){
        this.image = image;
    }

    public BossEnemyView(String url) {
        super(url);
    }

    @Override
    public void draw(Graphics graphics, GameModel model) {

        if (((BossEnemyModel) model).isImmunity()
                && System.currentTimeMillis() % 2 == 0) {
            if(((BossEnemyModel)model).isDestroy() == true){
                graphics.drawImage(imageShadow, model.getX() + BossEnemyModel.BOSS_WIDTH/2 -((BossEnemyModel)model).getWidth()/3/2, model.getY() + BossEnemyModel.BOSS_HEIGHT + BossEnemyModel.BOSS_SHADOW_DISTANCE,((BossEnemyModel)model).getWidth()/3, ((BossEnemyModel)model).getHeight()/5, null);
                graphics.drawImage(image, model.getX(), model.getY(), model.getWidth(), model.getHeight(), null);
            }else{
                ((Graphics2D) graphics).setComposite(AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER, 0.2f));
                graphics.drawImage(imageShadow, model.getX() + BossEnemyModel.BOSS_WIDTH/2 -((BossEnemyModel)model).getWidth()/3/2, model.getY() + BossEnemyModel.BOSS_HEIGHT + BossEnemyModel.BOSS_SHADOW_DISTANCE,((BossEnemyModel)model).getWidth()/3, ((BossEnemyModel)model).getHeight()/5, null);
                graphics.drawImage(image, model.getX(), model.getY(), model.getWidth(), model.getHeight(), null);
                ((Graphics2D) graphics).setComposite(AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER, 1f));
            }
        } else {
            graphics.drawImage(imageShadow, model.getX() + BossEnemyModel.BOSS_WIDTH/2 -((BossEnemyModel)model).getWidth()/3/2, model.getY() + BossEnemyModel.BOSS_HEIGHT + BossEnemyModel.BOSS_SHADOW_DISTANCE,((BossEnemyModel)model).getWidth()/3, ((BossEnemyModel)model).getHeight()/5, null);
            graphics.drawImage(image, model.getX(), model.getY(), model.getWidth(), model.getHeight(), null);
        }
    }
}
