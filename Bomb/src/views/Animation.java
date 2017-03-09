package views;

import utils.Utils;

import java.awt.*;

/**
 * Created by KhoaBeo on 3/9/2017.
 */
public class Animation {

    private long lastTime;
    private int index;
    private int delay;
    private Image image;
    private String url;

    public Animation(int delay, String url) {
        this.delay = delay;
        this.url = url;
    }

    public Image getImage() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime > delay) {
            image = Utils.loadImageFromRes(url + "-" + index++);
            lastTime = currentTime;
        }
        return image;
    }

    public void reload() {
        if (image == null) {
            index = 0;
        }
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
