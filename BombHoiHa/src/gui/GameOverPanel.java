package gui;

import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by KhoaBeo on 3/14/2017.
 */
public class GameOverPanel extends JPanel {

    private JLabel btnBackToMenu;

    public GameOverPanel() {
        setLayout(null);
        initComp();
    }

    private void initComp() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                if (mouseEvent.getSource().equals(btnBackToMenu)) {
                    GameFrame.mainPanel.showPanel(MainPanel.TAG_MENU);
                }
            }
        };

        ImageIcon imageIcon = new ImageIcon("resources/System/button_back-to-game-menu.png");
        btnBackToMenu = new JLabel(imageIcon);
        btnBackToMenu.setBounds((GameFrame.WIDTH - imageIcon.getIconWidth()) / 2 , 500, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        btnBackToMenu.setFocusable(false);
        add(btnBackToMenu);
        btnBackToMenu.addMouseListener(mouseAdapter);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        graphics.drawImage(Utils.loadImageFromRes("System/game-over2"), 0, 0, GameFrame.WIDTH, GameFrame.HEIGHT, null);
    }
}
