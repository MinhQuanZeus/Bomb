package controllers;

import models.GameModel;
import models.GameVector;
import views.GameView;

import java.awt.*;

/**
 * Created by QuanT on 3/9/2017.
 */
public class GameController {
    protected GameModel model;
    protected GameView view;
    protected GameVector vector;

    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
        this.vector = new GameVector();
    }

    public GameModel getModel() {
        return model;
    }

    public void draw(Graphics g) {
        view.draw(g, model);
    }

    public void run() {

    }
}
