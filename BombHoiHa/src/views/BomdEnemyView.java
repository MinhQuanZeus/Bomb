package views;

import models.BomdEnemyModel;
import models.GameModel;
import models.ItemMapModel;

import java.awt.*;

/**
 * Created by l on 3/25/2017.
 */
public class BomdEnemyView extends  EnemyView {

    private Image smokeImage;
    private Image shadowImage;

    public BomdEnemyView(Image image) {
        super(image);
    }

    public void setSmokeImage(Image smokeImage) {
        this.smokeImage = smokeImage;
    }

    public void setShadowImage(Image shadowImage) {
        this.shadowImage = shadowImage;
    }

    public BomdEnemyView(String url) {
        super(url);
    }


    @Override
    public void draw(Graphics graphics, GameModel model) {
        GameModel shadowModel = ((BomdEnemyModel)model).getShadowModel();
        graphics.drawImage(shadowImage, shadowModel.getX(), shadowModel.getY(), shadowModel.getWidth(), shadowModel.getHeight(), null);
        graphics.drawImage(image, model.getX(), model.getY(), model.getWidth(), model.getHeight(), null);
        if(smokeImage != null){
            graphics.drawImage(smokeImage, model.getX(), ((BomdEnemyModel)model).getyMoveTo() + ((BomdEnemyModel)model).getHeight() - ItemMapModel.SIZE_TILED/4, ItemMapModel.SIZE_TILED, ItemMapModel.SIZE_TILED/4, null);
        }
    }
}
