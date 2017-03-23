package views;

import java.awt.*;

/**
 * Created by QuanT on 3/12/2017.
 */
public class BulletView extends GameView {
    public BulletView(Image image) {
        super(image);
    }

    public BulletView(String url) {
        super(url);
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
