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
    public static final String TAG_END_GAME_WIN = "tag_end_game_win";
    public static final String TAG_END_GAME_LOSE = "tag_end_game_lose";
    public static final String TAG_INSTRUCTION = "tag_instruction";
    public static final String TAG_STORY_INTRO = "tag_story_intro";
    public static final String TAG_STORY_END = "tag_story_end";


    public static GamePanel gamePanel;
    private CardLayout cardLayout;
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
        if (gamePanel != null)
            gamePanel.setRunning(false);
        cardLayout.show(this, tag);
        setBGM(tag);
    }

    public void showGamePanel(boolean versus) {
        if (gamePanel != null) {
            gamePanel.setRunning(false);
        }
        EnemyModel.enemyCount = 0;
        gamePanel = new GamePanel(versus);
        add(gamePanel, TAG_GAME);
        setBGM(TAG_GAME);
        cardLayout.show(this, TAG_GAME);
        gamePanel.requestFocusInWindow();
    }

    public void showStoryIntroPanel(){
        StoryIntroPanel storyIntroPanel = new StoryIntroPanel();
        add(storyIntroPanel, TAG_STORY_INTRO);
        cardLayout.show(this,TAG_STORY_INTRO);
    }

    public void showStoryEndPanel(){
        StoryEndPanel storyEndPanel = new StoryEndPanel();
        add(storyEndPanel, TAG_STORY_END);
        cardLayout.show(this,TAG_STORY_END);
    }

    public void showEndPanel(boolean win) {
        gamePanel.setRunning(false);
        endGamePanel = new EndGamePanel(win);
        if (win) {
            add(endGamePanel, TAG_END_GAME_WIN);
            setBGM(TAG_END_GAME_WIN);
            cardLayout.show(this, TAG_END_GAME_WIN);
        }
        else{
            add(endGamePanel, TAG_END_GAME_LOSE);
            setBGM(TAG_END_GAME_LOSE);
            cardLayout.show(this, TAG_END_GAME_LOSE);
        }
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
            case TAG_END_GAME_LOSE: {
                bgm = new SoundPlayer(new File("resources/Sounds/game-over.wav"));
            }
            break;
            case TAG_END_GAME_WIN: {
                bgm = new SoundPlayer(new File("resources/Sounds/game-won.wav"));
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
