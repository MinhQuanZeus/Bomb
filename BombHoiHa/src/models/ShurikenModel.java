package models;

/**
 * Created by QuanT on 3/23/2017.
 */
public class ShurikenModel extends GameModel {
    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;

    public ShurikenModel(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public ShurikenModel(int x, int y) {
        super(x, y, WIDTH, HEIGHT);
    }
}
