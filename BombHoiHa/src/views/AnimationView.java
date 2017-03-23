package views;

import models.GameModel;

import java.awt.*;

/**
 * Created by KhoaBeo on 3/23/2017.
 */
public class AnimationView extends GameView {
    private Animation animation;

    public AnimationView (String url, int animationSize) {
        super(url + "-0");
        animation = new Animation(170, animationSize, url);
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
