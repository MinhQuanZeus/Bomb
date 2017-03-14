package views;

import models.GameModel;
import utils.Utils;

/**
 * Created by KhoaBeo on 3/9/2017.
 */
public class PlayerView extends GameView {

    public static final String MOVE_UP = "Bomberman/moveup";
    public static final String MOVE_DOWN = "Bomberman/movedown";
    public static final String MOVE_LEFT = "Bomberman/moveleft";
    public static final String MOVE_RIGHT = "Bomberman/moveright";

    private Animation animation;

    public PlayerView() {
        super(MOVE_DOWN + "-0");
        animation = new Animation(150, 4, MOVE_DOWN);
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

    public void explode(GameModel model) {
        if (!animation.getUrl().equals("Bomberman/explosion")) {
            animation.setUrl("Bomberman/explosion");
            animation.setSize(2);
            animation.reload();
            animation.setDelay(500);
        }

        if (animation.getImage() != null) {
            image = animation.getImage();
        } else {
            model.setAlive(false);
        }
    }

    public void setImageHold() {
        image = Utils.loadImageFromRes(animation.getUrl() + "-1");
    }
}
