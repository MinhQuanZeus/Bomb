package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by KhoaBeo on 3/9/2017.
 */
public class MainPanel extends JPanel {

    public static final String TAG_GAME = "tag_game";

    private CardLayout cardLayout;
    private GamePanel gamePanel;

    public MainPanel() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        gamePanel = new GamePanel();
        add(gamePanel, TAG_GAME);
    }
}
