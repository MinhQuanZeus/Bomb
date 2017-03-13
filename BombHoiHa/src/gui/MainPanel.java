package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by KhoaBeo on 3/9/2017.
 */
public class MainPanel extends JPanel {

    public static final String TAG_GAME = "tag_game";
    public static final String TAG_MENU = "tag_menu";
    public static final String TAG_GAME_OVER = "tag_game_over";

    private CardLayout cardLayout;
    private GamePanel gamePanel;
    private MenuPanel menuPanel;
    private GameOverPanel gameOverPanel;

    public MainPanel() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        menuPanel = new MenuPanel();
        add(menuPanel, TAG_MENU);
    }

    public void showPanel(String tag) {
        if (tag.equals(TAG_GAME)) {
            gamePanel = new GamePanel();
            add(gamePanel, TAG_GAME);
            cardLayout.show(this, tag);
            gamePanel.requestFocusInWindow();
        } else if (tag.equals(TAG_GAME_OVER)) {
            gameOverPanel = new GameOverPanel();
            add(gameOverPanel, TAG_GAME_OVER);
            cardLayout.show(this, tag);
        } else {
            cardLayout.show(this, tag);
        }
    }
}
