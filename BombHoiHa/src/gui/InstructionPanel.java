package gui;

import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Hoang on 3/22/2017.
 */
public class InstructionPanel extends JPanel {
    private JLabel btnBackToMenu;
    private JLabel btnNext;
    private int part = 0;

    public InstructionPanel() {
        setLayout(null);
        initComp();
    }

    private void initComp() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                Utils.playSound("select.wav", false);
                if (mouseEvent.getSource().equals(btnBackToMenu)) {
                    GameFrame.mainPanel.showPanel(MainPanel.TAG_MENU);
                }
                if (mouseEvent.getSource().equals(btnNext)) {
                    if (part == 0) {
                        part = 1;
                        ImageIcon imageIcon = new ImageIcon("resources/System/prev-0.png");
                        btnNext.setIcon(imageIcon);
                        btnNext.setBounds(btnBackToMenu.getX() - 148, 509, imageIcon.getIconWidth(), imageIcon.getIconHeight());
                        repaint();
                    } else {
                        part = 0;
                        ImageIcon imageIcon = new ImageIcon("resources/System/next-0.png");
                        btnNext.setIcon(imageIcon);
                        btnNext.setBounds(btnBackToMenu.getX() + 363, 509, imageIcon.getIconWidth(), imageIcon.getIconHeight());
                        repaint();
                    }
                    repaint();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if (e.getSource().equals(btnBackToMenu)) {
                    ImageIcon imageIcon = new ImageIcon("resources/System/back-1.png");
                    btnBackToMenu.setIcon(imageIcon);
                }
                if (e.getSource().equals(btnNext)) {
                    if (part == 0) {
                        ImageIcon imageIcon = new ImageIcon("resources/System/next-1.png");
                        btnNext.setIcon(imageIcon);
                        repaint();
                    } else {
                        ImageIcon imageIcon = new ImageIcon("resources/System/prev-1.png");
                        btnNext.setIcon(imageIcon);
                        repaint();
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if (e.getSource().equals(btnBackToMenu)) {
                    ImageIcon imageIcon = new ImageIcon("resources/System/back-0.png");
                    btnBackToMenu.setIcon(imageIcon);
                }
                if (e.getSource().equals(btnNext)) {
                    if (part==0) {
                        ImageIcon imageIcon = new ImageIcon("resources/System/next-0.png");
                        btnNext.setIcon(imageIcon);
                    }
                    else{
                        ImageIcon imageIcon = new ImageIcon("resources/System/prev-0.png");
                        btnNext.setIcon(imageIcon);
                    }
                }

            }
        };

        ImageIcon imageIcon = new ImageIcon("resources/System/back-0.png");
        btnBackToMenu = new JLabel(imageIcon);
        btnBackToMenu.setBounds((GameFrame.WIDTH - imageIcon.getIconWidth()) / 2, 507, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        btnBackToMenu.setFocusable(false);
        add(btnBackToMenu);
        btnBackToMenu.addMouseListener(mouseAdapter);

        imageIcon = new ImageIcon("resources/System/next-0.png");
        btnNext = new JLabel(imageIcon);
        btnNext.setBounds(btnBackToMenu.getX() + 363, 509, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        btnNext.setFocusable(false);
        add(btnNext);
        btnNext.addMouseListener(mouseAdapter);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        graphics.drawImage(Utils.loadImageFromRes("System/game-ins-" + part), 0, 0, GameFrame.WIDTH, GameFrame.HEIGHT, null);
    }
}
