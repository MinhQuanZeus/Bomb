package gui;

import utils.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by KhoaBeo on 3/9/2017.
 */
public class MainPanel extends JPanel {

    public static final String TAG_GAME = "tag_game";
    public static final String TAG_MENU = "tag_menu";
    public static final String TAG_GAME_OVER = "tag_end_game";

    private CardLayout cardLayout;
    private GamePanel gamePanel;
    private MenuPanel menuPanel;
    private EndGamePanel endGamePanel;

    private SoundPlayer bgm;

    public MainPanel() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        menuPanel = new MenuPanel();
        add(menuPanel, TAG_MENU);
        setBGM(TAG_MENU);
    }

    public void showPanel(String tag) {
        if (tag.equals(TAG_GAME)) {
            gamePanel = new GamePanel();
            add(gamePanel, TAG_GAME);
            cardLayout.show(this, tag);
            gamePanel.requestFocusInWindow();
        } else {
            cardLayout.show(this, tag);
        }
        setBGM(tag);
    }

    public void setBGM(String tag){
        if (bgm != null)
            bgm.stop();
        switch (tag){
            case "tag_menu":{
                bgm = new SoundPlayer(new File("resources/Sounds/intro.wav"));
            }break;
            case "tag_game":{
                bgm = new SoundPlayer(new File("resources/Sounds/game.wav"));
            }break;
            case "tag_end_game":{
                bgm = new SoundPlayer(new File("resources/Sounds/end.wav"));
            }break;
        }
        bgm.play();
    }

    public void showPanel(boolean win) {
        endGamePanel = new EndGamePanel(win);
        add(endGamePanel, TAG_GAME_OVER);
        setBGM(TAG_GAME_OVER);
        cardLayout.show(this, TAG_GAME_OVER);
    }
}
