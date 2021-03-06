package gui;

import manager.GameManager;
import manager.MapManager;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by KhoaBeo on 3/24/2017.
 */
public class PausedPanel extends JPanel {

    private GamePanel gamePanel;
    private JLabel label;
    private JLabel btnResume;
    private JLabel btnQuit;
    private JLabel btnMute;

    public PausedPanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        setSize(300, 300);
        setLocation((GameFrame.WIDTH - this.getWidth()) / 2, (GameFrame.HEIGHT - this.getHeight()) / 2);
        setLayout(null);
        initComp();
        setVisible(false);
        setOpaque(false);
    }

    private void initComp() {

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                Utils.playSound("select.wav",false);
                if (mouseEvent.getSource().equals(btnQuit)) {
                    GameFrame.mainPanel.showPanel(MainPanel.TAG_MENU);
                    GamePanel.paused = false;
                }
                if (mouseEvent.getSource().equals(btnResume)) {
                    setVisible(false);
                    gamePanel.resume();
                }
                if (mouseEvent.getSource().equals(btnMute)) {
                    if (MainPanel.isMute()) {
                        MainPanel.setMute(false);
                        ImageIcon imageIcon = new ImageIcon("resources/System/mute-1.png");
                        btnMute.setIcon(imageIcon);
                    }
                    else{
                        MainPanel.setMute(true);
                        ImageIcon imageIcon = new ImageIcon("resources/System/unmute-1.png");
                        btnMute.setIcon(imageIcon);
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                super.mouseEntered(mouseEvent);
                if (mouseEvent.getSource().equals(btnQuit)) {
                    btnQuit.setIcon(new ImageIcon("resources/System/quit-1.png"));
                }
                if (mouseEvent.getSource().equals(btnResume)) {
                    btnResume.setIcon(new ImageIcon("resources/System/resume-1.png"));
                }
                if (mouseEvent.getSource().equals(btnMute)) {
                    if (MainPanel.isMute()){
                        ImageIcon imageIcon = new ImageIcon("resources/System/unmute-1.png");
                        btnMute.setIcon(imageIcon);
                    }
                    else{
                        ImageIcon imageIcon = new ImageIcon("resources/System/mute-1.png");
                        btnMute.setIcon(imageIcon);
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                super.mouseExited(mouseEvent);
                if (mouseEvent.getSource().equals(btnQuit)) {
                    btnQuit.setIcon(new ImageIcon("resources/System/quit-0.png"));
                }
                if (mouseEvent.getSource().equals(btnResume)) {
                    btnResume.setIcon(new ImageIcon("resources/System/resume-0.png"));
                }
                if (mouseEvent.getSource().equals(btnMute)) {
                    if (MainPanel.isMute()){
                        ImageIcon imageIcon = new ImageIcon("resources/System/unmute-0.png");
                        btnMute.setIcon(imageIcon);
                    }
                    else{
                        ImageIcon imageIcon = new ImageIcon("resources/System/mute-0.png");
                        btnMute.setIcon(imageIcon);
                    }
                }
            }
        };

        ImageIcon imageIcon = new ImageIcon("resources/System/paused.png");
        label = new JLabel(imageIcon);
        label.setBounds((this.getWidth() - imageIcon.getIconWidth()) / 2, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        add(label);

        imageIcon = new ImageIcon("resources/System/resume-0.png");
        btnResume = new JLabel(imageIcon);
        btnResume.setBounds((this.getWidth() - imageIcon.getIconWidth()) / 2, label.getY() + label.getHeight() + 10, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        btnResume.setFocusable(false);
        add(btnResume);
        btnResume.addMouseListener(mouseAdapter);

        imageIcon = new ImageIcon("resources/System/quit-0.png");
        btnQuit = new JLabel(imageIcon);
        btnQuit.setBounds((this.getWidth() - imageIcon.getIconWidth()) / 2, btnResume.getY() + btnResume.getHeight() + 10, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        btnQuit.setFocusable(false);
        add(btnQuit);
        btnQuit.addMouseListener(mouseAdapter);

        if (MainPanel.isMute())
            imageIcon = new ImageIcon("resources/System/unmute-0.png");
        else imageIcon = new ImageIcon("resources/System/mute-0.png");
        btnMute = new JLabel(imageIcon);
        btnMute.setBounds((this.getWidth() - imageIcon.getIconWidth()) / 2,btnResume.getY() + btnResume.getHeight() + 65,imageIcon.getIconWidth(),imageIcon.getIconHeight());
        btnMute.setFocusable(false);
        add(btnMute);
        btnMute.addMouseListener(mouseAdapter);
    }
}
