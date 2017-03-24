package views;

import models.GameModel;

import java.awt.*;

/**
 * Created by QuanT on 3/22/2017.
 */
public class ShurikenView extends GameView {
    private Animation animation;


    public ShurikenView(String url) {
        super(url + "-0");
        animation = new Animation(100,7, url);
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
