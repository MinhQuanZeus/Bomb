package views;

import models.GameModel;

import java.awt.*;

/**
 * Created by KhoaBeo on 3/11/2017.
 */
public class BombView extends GameView {

    private Animation animation;

    public BombView(String url) {
        super(url + "-0");
        animation = new Animation(170, url);
    }

    public void setImage() {
        if (animation.getImage() != null) {
            image = animation.getImage();
        } else {
            animation.reload();
        }
    }

    @Override
    public void draw(Graphics graphics, GameModel model) {
        super.draw(graphics, model);
        setImage();
    }
}
