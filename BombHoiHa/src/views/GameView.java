package views;

import models.GameModel;
import utils.Utils;

import java.awt.*;

/**
 * Created by QuanT on 3/9/2017.
 */
public class GameView {
    protected Image image;

    public GameView(Image image) {
        this.image = image;
    }

    public GameView(String url) {
        this.image = Utils.loadImageFromRes(url);
    }

    public void draw(Graphics graphics, GameModel model) {
        graphics.drawImage(image, model.getX(), model.getY(), model.getWidth(), model.getHeight(), null);
    }
}
