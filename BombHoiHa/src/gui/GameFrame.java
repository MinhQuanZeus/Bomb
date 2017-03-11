package gui;

import javax.swing.*;

/**
 * Created by KhoaBeo on 3/9/2017.
 */
public class GameFrame extends JFrame {

    public static final int WIDTH = 700;
    public static final int HEIGHT = 729;
    public static MainPanel mainPanel;

    public GameFrame() {
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainPanel = new MainPanel();
        add(mainPanel);
        setVisible(true);
    }
}
