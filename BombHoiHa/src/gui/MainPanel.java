package gui;

import manager.MapManager;
import models.EnemyModel;
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
    public static final String TAG_END_GAME = "tag_end_game";
    public static final String TAG_INSTRUCTION = "tag_instruction";

    private CardLayout cardLayout;
    private GamePanel gamePanel;
    private MenuPanel menuPanel;
    private EndGamePanel endGamePanel;
    private InstructionPanel instructionPanel;

    private static SoundPlayer bgm;


    public MainPanel() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        menuPanel = new MenuPanel();
        add(menuPanel, TAG_MENU);
        instructionPanel = new InstructionPanel();
        add(instructionPanel, TAG_INSTRUCTION);
        setBGM(TAG_MENU);
        cardLayout.show(this, TAG_MENU);
    }

    public void showPanel(String tag) {
        gamePanel.setRunning(false);
        cardLayout.show(this, tag);
        setBGM(tag);
    }

    public void showGamePanel() {
        if (gamePanel != null) {
            gamePanel.setRunning(false);
        }
        EnemyModel.enemyCount = 0;
        gamePanel = new GamePanel();
        add(gamePanel, TAG_GAME);
        setBGM(TAG_GAME);
        cardLayout.show(this, TAG_GAME);
        gamePanel.requestFocusInWindow();
    }

    public void showEndPanel(boolean win) {
        gamePanel.setRunning(false);
        endGamePanel = new EndGamePanel(win);
        add(endGamePanel, TAG_END_GAME);
        setBGM(TAG_END_GAME);
        cardLayout.show(this, TAG_END_GAME);
    }

    public static void setBGM(String tag) {
        if (bgm != null)
            bgm.stop();
        switch (tag) {
            case TAG_MENU: {
                bgm = new SoundPlayer(new File("resources/Sounds/game-menu.wav"));
            }
            break;
            case TAG_GAME: {
                bgm = new SoundPlayer(new File("resources/Sounds/game-stage-" + MapManager.mapLevel + ".wav"));
            }
            break;
            case TAG_END_GAME: {
                bgm = new SoundPlayer(new File("resources/Sounds/game-over.wav"));
            }
            break;
            case TAG_INSTRUCTION: {
                bgm = new SoundPlayer(new File("resources/Sounds/game-instruction.wav"));
            }
            break;
        }
        bgm.play();
    }
}
