package models;

/**
 * Created by QuanT on 3/9/2017.
 */
public class EnemyModel extends GameModel {
    public static final int WIDTH = 50;
    public static final int HEIGHT = 70;

    public EnemyModel(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public EnemyModel(int x, int y) {
        super(x, y, WIDTH, HEIGHT);
    }
}
