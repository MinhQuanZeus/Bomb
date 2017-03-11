package views;

import models.GameModel;

import java.awt.*;

/**
 * Created by KhoaBeo on 3/11/2017.
 */
public class ExplosionView extends GameView {

    private Animation animation;

    public ExplosionView(String url) {
        super(url + "-0");
        animation = new Animation(100, url);
    }

    @Override
    public void draw(Graphics graphics, GameModel model) {
        super.draw(graphics, model);
        setImage(model);
    }

    public void setImage(GameModel model) {
        if (animation.getImage() != null) {
            image = animation.getImage();
        } else {
            model.setAlive(false);
        }
    }
}
