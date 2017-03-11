package models;

/**
 * Created by QuanT on 3/9/2017.
 */
public class PlayerModel extends GameModel {

    public static final int WIDTH = 50;
    public static final int HEIGHT = 70;

    private int maxBomb;
    private int countBomb;
    private int explosionSize;

    public PlayerModel(int x, int y) {
        super(x, y, WIDTH, HEIGHT);
        maxBomb = 1;
        explosionSize = 1;
    }

    public void increaseCountBomb() {
        countBomb++;
    }

    public void reduceCountBomb() {
        countBomb--;
    }

    public boolean checkMaxBomb() {
        if (countBomb < maxBomb) {
            return true;
        }
        return false;
    }

    public int getExplosionSize() {
        return explosionSize;
    }
}
