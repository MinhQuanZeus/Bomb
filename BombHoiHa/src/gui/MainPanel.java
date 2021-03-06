package gui;

import manager.MapManager;
import models.EnemyModel;
import utils.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    public static final String TAG_CHOOSE_MAP = "tag_choose_map";


    public static GamePanel gamePanel;
    private CardLayout cardLayout;
    private MenuPanel menuPanel;
    private EndGamePanel endGamePanel;
    private InstructionPanel instructionPanel;
    private ChooseMapPanel chooseMapPanel;

    private static SoundPlayer bgm;
    private static boolean mute = false;


    public MainPanel() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        menuPanel = new MenuPanel();
        add(menuPanel, TAG_MENU);
        instructionPanel = new InstructionPanel();
        add(instructionPanel, TAG_INSTRUCTION);
        chooseMapPanel = new ChooseMapPanel();
        add(chooseMapPanel, TAG_CHOOSE_MAP);
        setBGM(TAG_MENU);
        cardLayout.show(this, TAG_MENU);
    }

    public void showPanel(String tag) {
        if (gamePanel != null)
            gamePanel.setRunning(false);
        if (tag.equals(TAG_MENU)){
            menuPanel = new MenuPanel();
            add(menuPanel,TAG_MENU);
        }
        setBGM(tag);
        cardLayout.show(this, tag);
    }

    public void showGamePanel(boolean versus, int stage) {
        if (gamePanel != null) {
            gamePanel.setRunning(false);
        }
        EnemyModel.enemyCount = 0;
        gamePanel = new GamePanel(versus, stage);
        add(gamePanel, TAG_GAME);
        setBGM(TAG_GAME);
        cardLayout.show(this, TAG_GAME);
        gamePanel.requestFocusInWindow();
    }

    public void showStoryIntroPanel() {
        StoryIntroPanel storyIntroPanel = new StoryIntroPanel();
        add(storyIntroPanel, TAG_STORY_INTRO);
        setBGM(TAG_STORY_INTRO);
        cardLayout.show(this, TAG_STORY_INTRO);
    }

    public void showStoryEndPanel() {
        gamePanel.setRunning(false);
        StoryEndPanel storyEndPanel = new StoryEndPanel();
        add(storyEndPanel, TAG_STORY_END);
        setBGM(TAG_STORY_END);
        cardLayout.show(this, TAG_STORY_END);
    }

    public void showEndPanel(String tag, Integer score) {
        gamePanel.setRunning(false);
        endGamePanel = new EndGamePanel(tag, score);
        if (tag.equals(EndGamePanel.LOSE)) {
            add(endGamePanel, TAG_END_GAME_LOSE);
            setBGM(TAG_END_GAME_LOSE);
            cardLayout.show(this, TAG_END_GAME_LOSE);
        } else {
            add(endGamePanel, TAG_END_GAME_WIN);
            setBGM(TAG_END_GAME_WIN);
            cardLayout.show(this, TAG_END_GAME_WIN);
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
                case TAG_STORY_INTRO: {
                    bgm = new SoundPlayer(new File("resources/Sounds/story-intro.wav"));
                }break;
                case TAG_STORY_END: {
                    bgm = new SoundPlayer(new File("resources/Sounds/story-end.wav"));
                }break;
                case TAG_CHOOSE_MAP: {
                    bgm = new SoundPlayer(new File("resources/Sounds/choose-map.wav"));
                }break;
            }
            if (!mute)
                bgm.play();
    }

    public static void setMute(boolean mute) {
        MainPanel.mute = mute;
        if (mute) bgm.stop();
        if (!mute) bgm.play();
    }

    public static boolean isMute() {
        return mute;
    }

    public static void stopBGM(){
        bgm.stop();
    }
}
