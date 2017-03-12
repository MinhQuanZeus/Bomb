package views;

import models.GameModel;
import utils.Utils;

import java.awt.*;

/**
 * Created by QuanT on 3/12/2017.
 */
public class ItemView extends GameView {

    private Animation animation;


    public ItemView(String url) {
        super(url + "-0");
        animation = new Animation(100, url);
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
