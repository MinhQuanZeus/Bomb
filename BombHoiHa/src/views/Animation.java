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
    private int size;
    private Image image;
    private String url;

    public Animation(int delay, int size, String url) {
        this.delay = delay;
        this.url = url;
        this.size = size;
    }

    public Image getImage() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime > delay) {
            if (index >= size) {
                return null;
            }
            image = Utils.loadImageFromRes(url + "-" + index++);
            lastTime = currentTime;
        }
        return image;
    }

    public void reload() {
        index = 0;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
