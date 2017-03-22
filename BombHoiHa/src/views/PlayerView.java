package views;

import gui.GameFrame;
import models.GameModel;
import models.PlayerModel;
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

    public PlayerView() {
        super(MOVE_DOWN + "-0");
        animation = new Animation(150, 4, MOVE_DOWN);
    }

    @Override
    public void draw(Graphics graphics, GameModel model) {
        if (((PlayerModel) model).isImmunity()) {
            if (System.currentTimeMillis() % 2 == 0) {
                ((Graphics2D) graphics).setComposite(AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER, 0.2f));
            }
        }
        super.draw(graphics, model);
        ((Graphics2D) graphics).setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 1f));
        graphics.drawImage(Utils.loadImageFromRes("Bomberman/life"), 0, 0, null);
        graphics.setFont(new Font("Courier New", Font.BOLD, 20));
        graphics.setColor(Color.white);
        graphics.drawString("Score:" + ((PlayerModel) model).getScore(), GameFrame.WIDTH - 200, 22);
        graphics.drawString(((PlayerModel) model).getLife() + "", 25, 30);
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
            animation.reload();
        }

        if (animation.getImage() != null) {
            image = animation.getImage();
        } else {
            if (((PlayerModel) model).getLife() == 0) {
                model.setAlive(false);
                GameFrame.mainPanel.showEndPanel(false);
            } else {
                ((PlayerModel) model).setExplode(false);
                ((PlayerModel) model).reduceLife();
                animation.setUrl(MOVE_DOWN);
                ((PlayerModel) model).setImmunity(true);
            }
        }
    }

    public void setImageHold() {
        image = Utils.loadImageFromRes(animation.getUrl() + "-1");
    }
}
