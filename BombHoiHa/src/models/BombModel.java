package models;

/**
 * Created by KhoaBeo on 3/25/2017.
 */
public class BombModel extends GameModel {

    private boolean isSlide;

    public BombModel(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public boolean isSlide() {
        return isSlide;
    }

    public void setSlide(boolean slide) {
        isSlide = slide;
    }
}
