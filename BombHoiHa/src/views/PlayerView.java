package views;

import gui.EndGamePanel;
import gui.GameFrame;
import manager.GameManager;
import models.GameModel;
import models.PlayerModel;
import utils.Utils;

import java.awt.*;

/**
 * Created by KhoaBeo on 3/9/2017.
 */
public class PlayerView extends GameView {

    public static final String MOVE_UP = "/moveup";
    public static final String MOVE_DOWN = "/movedown";
    public static final String MOVE_LEFT = "/moveleft";
    public static final String MOVE_RIGHT = "/moveright";

    private Animation animation;
    private String urlImage;

    public PlayerView(String url) {
        super(url + MOVE_DOWN + "-0");
        this.urlImage = url;
        animation = new Animation(150, 4, urlImage + MOVE_DOWN);
    }

    @Override
    public void draw(Graphics graphics, GameModel model) {
        if (((PlayerModel) model).isImmunity() && System.currentTimeMillis() % 2 == 0) {
            ((Graphics2D) graphics).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
            super.draw(graphics, model);
            ((Graphics2D) graphics).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        } else {
            super.draw(graphics, model);
        }
        if (!GameManager.versus) {
            graphics.drawImage(Utils.loadImageFromRes("Bomberman/life"), 0, 0, null);
            graphics.drawImage(Utils.loadImageFromRes("Bomberman/clock"), 40, 0, null);
            graphics.setFont(new Font("Courier New", Font.BOLD, 20));
            graphics.setColor(Color.white);
            graphics.drawString("Score:" + ((PlayerModel) model).getScore(), GameFrame.WIDTH - 200, 22);
            graphics.drawString(((PlayerModel) model).getLife() + "", 9, 20);
        }
    }

    public void setImage(String url) {
        if (!animation.getUrl().equals(urlImage + url)) {
            animation.setUrl(urlImage + url);
            animation.reload();
        }

        if (animation.getImage() != null) {
            image = animation.getImage();
        } else {
            animation.reload();
        }
    }

    public void explode(GameModel model) {
        if (!animation.getUrl().equals(urlImage + "/explosion")) {
            animation.setUrl(urlImage + "/explosion");
            animation.reload();
        }

        if (animation.getImage() != null) {
            image = animation.getImage();
        } else {
            if (((PlayerModel) model).getLife() == 0) {
                model.setAlive(false);
                if (!GameManager.versus) {
                    GameFrame.mainPanel.showEndPanel(EndGamePanel.LOSE);
                } else {
                    if (animation.getUrl().contains("BombermanTwo")) {
                        GameFrame.mainPanel.showEndPanel(EndGamePanel.BOMBERMAN);
                    } else {
                        GameFrame.mainPanel.showEndPanel(EndGamePanel.HAMMERBOMBER);
                    }
                }
            } else {
                ((PlayerModel) model).setExplode(false);
                ((PlayerModel) model).reduceLife();
                animation.setUrl(urlImage + MOVE_DOWN);
                ((PlayerModel) model).setImmunity(true);
            }
        }
    }


    public void setImageHold() {
        image = Utils.loadImageFromRes(animation.getUrl() + "-1");
    }
}
