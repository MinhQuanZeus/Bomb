package gui;

import manager.GameManager;
import models.PlayerModel;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Hoang on 3/25/2017.
 */
public class StoryEndPanel extends JPanel {
    private JLabel btnNext;

    private int part = 0;

    public StoryEndPanel() {
        setLayout(null);
        initComp();
    }

    private void initComp() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                System.out.println(part);
                super.mouseClicked(mouseEvent);
                Utils.playSound("select.wav", false);
                if (part == 6) {
                    GameFrame.mainPanel.showEndPanel(EndGamePanel.WIN, ((PlayerModel) GameManager.playerController.getModel()).getScore());
                    GamePanel.setFlag(true);
                    part = 0;
                }
                if (mouseEvent.getSource().equals(btnNext)) {
                    part++;
                    repaint();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if (e.getSource().equals(btnNext)) {
                    ImageIcon imageIcon = new ImageIcon("resources/System/next-1.png");
                    btnNext.setIcon(imageIcon);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if (e.getSource().equals(btnNext)) {
                    ImageIcon imageIcon = new ImageIcon("resources/System/next-0.png");
                    btnNext.setIcon(imageIcon);
                }
            }

        };

        ImageIcon imageIcon = new ImageIcon("resources/System/next-0.png");
        btnNext = new JLabel(imageIcon);
        btnNext.setBounds(400, 470, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        btnNext.setFocusable(false);
        add(btnNext);
        btnNext.addMouseListener(mouseAdapter);

    }

    @Override
    protected void paintComponent(Graphics graphics) {
        graphics.drawImage(Utils.loadImageFromRes("System/end-" + part), 0, 0, GameFrame.WIDTH, GameFrame.HEIGHT, null);
    }
}
