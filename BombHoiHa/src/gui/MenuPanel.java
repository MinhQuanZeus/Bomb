package gui;

import manager.GameManager;
import utils.SoundPlayer;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by KhoaBeo on 3/13/2017.
 */
public class MenuPanel extends JPanel {

    private JLabel btnStart;
    private JLabel btnExit;
    private JLabel btnInstruction;
    private JLabel btnVersus;
    private JLabel gifLabel;
    private JLabel btnMute;

    public MenuPanel() {
        setLayout(null);
        initComp();
    }

    private void initComp() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                Utils.playSound("select.wav", false);
                if (mouseEvent.getSource().equals(btnExit)) {
                    System.exit(0);
                }
                if (mouseEvent.getSource().equals(btnStart)) {
                    GameFrame.mainPanel.showStoryIntroPanel();
                }
                if (mouseEvent.getSource().equals(btnVersus)) {
                    GameFrame.mainPanel.showPanel(MainPanel.TAG_CHOOSE_MAP);
                }
                if (mouseEvent.getSource().equals(btnInstruction)) {
                    GameFrame.mainPanel.showPanel(MainPanel.TAG_INSTRUCTION);
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
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if (e.getSource().equals(btnStart)) {
                    ImageIcon imageIcon = new ImageIcon("resources/System/start-1.png");
                    btnStart.setIcon(imageIcon);
                }
                if (e.getSource().equals(btnExit)) {
                    ImageIcon imageIcon = new ImageIcon("resources/System/exit-1.png");
                    btnExit.setIcon(imageIcon);
                }
                if (e.getSource().equals(btnInstruction)) {
                    ImageIcon imageIcon = new ImageIcon("resources/System/instruction-1.png");
                    btnInstruction.setIcon(imageIcon);
                }
                if (e.getSource().equals(btnVersus)) {
                    ImageIcon imageIcon = new ImageIcon("resources/System/versus-1.png");
                    btnVersus.setIcon(imageIcon);
                }
                if (e.getSource().equals(btnMute)) {
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
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if (e.getSource().equals(btnStart)) {
                    ImageIcon imageIcon = new ImageIcon("resources/System/start-0.png");
                    btnStart.setIcon(imageIcon);
                }
                if (e.getSource().equals(btnExit)) {
                    ImageIcon imageIcon = new ImageIcon("resources/System/exit-0.png");
                    btnExit.setIcon(imageIcon);
                }
                if (e.getSource().equals(btnInstruction)) {
                    ImageIcon imageIcon = new ImageIcon("resources/System/instruction-0.png");
                    btnInstruction.setIcon(imageIcon);
                }
                if (e.getSource().equals(btnVersus)) {
                    ImageIcon imageIcon = new ImageIcon("resources/System/versus-0.png");
                    btnVersus.setIcon(imageIcon);
                }
                if (e.getSource().equals(btnMute)) {
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

        ImageIcon imageIcon = new ImageIcon("resources/System/start-0.png");
        btnStart = new JLabel(imageIcon);
        btnStart.setBounds(350, 350, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        btnStart.setFocusable(false);
        add(btnStart);
        btnStart.addMouseListener(mouseAdapter);

        imageIcon = new ImageIcon("resources/System/versus-0.png");
        btnVersus = new JLabel(imageIcon);
        btnVersus.setBounds(btnStart.getX(), btnStart.getY() + btnStart.getHeight() + 10, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        btnVersus.setFocusable(false);
        add(btnVersus);
        btnVersus.addMouseListener(mouseAdapter);

        imageIcon = new ImageIcon("resources/System/instruction-0.png");
        btnInstruction = new JLabel(imageIcon);
        btnInstruction.setBounds(btnStart.getX(), btnVersus.getY() + btnVersus.getHeight() + 10, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        btnInstruction.setFocusable(false);
        add(btnInstruction);
        btnInstruction.addMouseListener(mouseAdapter);

        imageIcon = new ImageIcon("resources/System/exit-0.png");
        btnExit = new JLabel(imageIcon);
        btnExit.setBounds(btnStart.getX(), btnInstruction.getY() + btnInstruction.getHeight() + 10, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        btnExit.setFocusable(false);
        add(btnExit);
        btnExit.addMouseListener(mouseAdapter);

        imageIcon = new ImageIcon("resources/System/dancing.gif");
        gifLabel = new JLabel(imageIcon);
        gifLabel.setBounds(90, btnStart.getY() - 55, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        gifLabel.setFocusable(false);
        add(gifLabel);

        imageIcon = new ImageIcon("resources/System/mute-0.png");
        btnMute = new JLabel(imageIcon);
        btnMute.setBounds(450,2,imageIcon.getIconWidth(),imageIcon.getIconHeight());
        btnMute.setFocusable(false);
        add(btnMute);
        btnMute.addMouseListener(mouseAdapter);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        graphics.drawImage(Utils.loadImageFromRes("System/game-menu"), 0, 0, GameFrame.WIDTH, GameFrame.HEIGHT, null);
    }
}
