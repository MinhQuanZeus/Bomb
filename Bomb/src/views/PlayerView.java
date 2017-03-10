package views;

import models.GameModel;
import utils.Utils;

import java.awt.*;

/**
 * Created by KhoaBeo on 3/9/2017.
 */
public class PlayerView extends GameView {

    public static final String MOVE_UP = "Bomberman/moveup";
    public static final String MOVE_DOWN = "Bomberman/movedown";
    public static final String MOVE_LEFT = "Bomberman/moveleft";
    public static final String MOVE_RIGHT = "Bomberman/moveright";

    private Animation animation;

    public PlayerView(String url) {
        super(url + "-0");
        animation = new Animation(170, MOVE_DOWN);
    }

    public void setImage(String url) {
        if (!animation.getUrl().equals(url)) {
            animation.setUrl(url);
            animation.reload();
        }

        if (animation.getImage() != null) {
            image = animation.getImage();
        } else {
            animation.reload();
        }
    }

    public void setImageHold() {
        image = Utils.loadImageFromRes(animation.getUrl() + "-1");
    }
}
