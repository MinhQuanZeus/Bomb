package gui;

import utils.Utils;

import javax.swing.*;

/**
 * Created by KhoaBeo on 3/9/2017.
 */
public class GameFrame extends JFrame {

    public static final int WIDTH = 565;
    public static final int HEIGHT = 585;
    public static MainPanel mainPanel;

    public GameFrame() {
        this.setIconImage(Utils.loadImageFromRes("Logo/Logo"));
        setTitle("Bomb Hối Hả");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainPanel = new MainPanel();
        add(mainPanel);
        setVisible(true);
    }
}
