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
    public static final String DINO = "Dino";
    public static final String FISH = "Fish";
    private Animation animation;
    private String urlImage;
    private String urlPlayer;
    private Image driver;

    public PlayerView(String url) {
        super(url + MOVE_DOWN + "-0");
        this.urlImage = url;
        this.urlPlayer = url;
        animation = new Animation(150, 4, urlImage + MOVE_DOWN);
    }

    @Override
    public void draw(Graphics graphics, GameModel gameModel) {
        PlayerModel model = (PlayerModel) gameModel;
        model.setWidth(image.getWidth(null) * 23 / 10);
        model.setHeight(image.getHeight(null) * 23 / 10);
        if (model.isImmunity() && System.currentTimeMillis() % 2 == 0) {
            ((Graphics2D) graphics).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
            graphics.drawImage(image, model.getX(), model.getY(),
                    model.getWidth(), model.getHeight(),
                    null);
            ((Graphics2D) graphics).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        } else {
            graphics.drawImage(image, model.getX(), model.getY(),
                    model.getWidth(), model.getHeight(),
                    null);
        }

        if (model.isDriver()) {
            switch (model.getShotDirection()) {
                case RIGHT:
                    driver = Utils.loadImageFromRes(urlPlayer + "/driverright");
                    break;
                case LEFT:
                    driver = Utils.loadImageFromRes(urlPlayer + "/driverleft");
                    break;
                case DOWN:
                    driver = Utils.loadImageFromRes(urlPlayer + "/driverdown");
                    break;
                case UP:
                    driver = Utils.loadImageFromRes(urlPlayer + "/driverup");
                    break;
            }
            graphics.drawImage(driver,
                    model.getX() + (model.getWidth() - driver.getWidth(null) * 2) / 2,
                    model.getY() - 15,
                    driver.getWidth(null) * 2, driver.getHeight(null) * 2,
                    null);
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
            if (!((PlayerModel) model).isDriver()) {
                if (((PlayerModel) model).getLife() == 0) {
                    model.setAlive(false);
                    if (!GameManager.versus) {
                        GameFrame.mainPanel.showEndPanel(EndGamePanel.LOSE, ((PlayerModel) GameManager.playerController.getModel()).getScore());
                    } else {
                        if (animation.getUrl().contains("BombermanTwo")) {
                            GameFrame.mainPanel.showEndPanel(EndGamePanel.BOMBERMAN, null);
                        } else {
                            GameFrame.mainPanel.showEndPanel(EndGamePanel.HAMMERBOMBER, null);
                        }
                    }
                } else {
                    ((PlayerModel) model).setExplode(false);
                    ((PlayerModel) model).reduceLife();
                    animation.setUrl(urlImage + MOVE_DOWN);
                    ((PlayerModel) model).setImmunity(true);
                }
            } else {
                ((PlayerModel) model).setExplode(false);
                ((PlayerModel) model).setDriver(false);
                urlImage = urlPlayer;
                animation.setUrl(urlImage + MOVE_DOWN);
                ((PlayerModel) model).setImmunity(true);
                if (((PlayerModel) model).getPet().equals(DINO)) {
                    ((PlayerModel) model).expandExplosionSize(-5);
                } else {
                    ((PlayerModel) model).speedDown();
                }
            }
        }
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public void setImageHold() {
        image = Utils.loadImageFromRes(animation.getUrl() + "-1");
    }
}
