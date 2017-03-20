package gui;

import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by KhoaBeo on 3/14/2017.
 */
public class EndGamePanel extends JPanel {

    private JLabel btnBackToMenu;
    private Image backGround;

    public EndGamePanel(boolean win) {
        setLayout(null);
        initComp();
        if (win) {
            backGround = Utils.loadImageFromRes("System/game-won");
        } else {
            backGround = Utils.loadImageFromRes("System/game-over2");
        }
    }

    private void initComp() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                Utils.playSound("select.wav",false);
                if (mouseEvent.getSource().equals(btnBackToMenu)) {
                    GameFrame.mainPanel.showPanel(MainPanel.TAG_MENU);
                }
            }
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if(e.getSource().equals(btnBackToMenu)){
                    ImageIcon imageIcon = new ImageIcon("resources/System/back-1.png");
                    btnBackToMenu.setIcon(imageIcon);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if(e.getSource().equals(btnBackToMenu)){
                    ImageIcon imageIcon = new ImageIcon("resources/System/back-0.png");
                    btnBackToMenu.setIcon(imageIcon);
                }
            }
        };

        ImageIcon imageIcon = new ImageIcon("resources/System/back-0.png");
        btnBackToMenu = new JLabel(imageIcon);
        btnBackToMenu.setBounds((GameFrame.WIDTH - imageIcon.getIconWidth()) / 2 , 500, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        btnBackToMenu.setFocusable(false);
        add(btnBackToMenu);
        btnBackToMenu.addMouseListener(mouseAdapter);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        graphics.drawImage(backGround, 0, 0, GameFrame.WIDTH, GameFrame.HEIGHT, null);
    }
}
