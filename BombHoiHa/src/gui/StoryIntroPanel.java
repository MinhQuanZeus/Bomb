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
public class StoryIntroPanel extends JPanel {
    private JLabel btnNext;
    private JLabel btnPrev;
    private JLabel btnSkip;

    private int part = 0;

    public StoryIntroPanel() {
        setLayout(null);
        initComp();
    }

    private void initComp() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                Utils.playSound("select.wav", false);
                if (mouseEvent.getSource().equals(btnNext)) {
                    if (part == 5) {
                        GameFrame.mainPanel.showGamePanel(false, 1);
                        part = 0;
                    }
                    if (part < 5)
                        part++;
                    repaint();
                }
                if (mouseEvent.getSource().equals(btnPrev)) {
                    if (part > 0)
                        part--;
                    repaint();
                }
                if (mouseEvent.getSource().equals(btnSkip)){
                    GameFrame.mainPanel.showGamePanel(false, 1);
                    part = 0;
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if (e.getSource().equals(btnNext)) {
                    ImageIcon imageIcon = new ImageIcon("resources/System/next-1.png");
                    btnNext.setIcon(imageIcon);
                }
                if (e.getSource().equals(btnPrev)) {
                    ImageIcon imageIcon = new ImageIcon("resources/System/prev-1.png");
                    btnPrev.setIcon(imageIcon);
                }
                if (e.getSource().equals(btnSkip)) {
                    ImageIcon imageIcon = new ImageIcon("resources/System/skip-1.png");
                    btnSkip.setIcon(imageIcon);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if (e.getSource().equals(btnNext)) {
                    ImageIcon imageIcon = new ImageIcon("resources/System/next-0.png");
                    btnNext.setIcon(imageIcon);
                }
                if (e.getSource().equals(btnPrev)) {
                    ImageIcon imageIcon = new ImageIcon("resources/System/prev-0.png");
                    btnPrev.setIcon(imageIcon);
                }
                if (e.getSource().equals(btnSkip)) {
                    ImageIcon imageIcon = new ImageIcon("resources/System/skip-0.png");
                    btnSkip.setIcon(imageIcon);
                }
            }

        };

        ImageIcon imageIcon = new ImageIcon("resources/System/next-0.png");
        btnNext = new JLabel(imageIcon);
        btnNext.setBounds(422, 35, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        btnNext.setFocusable(false);
        add(btnNext);
        btnNext.addMouseListener(mouseAdapter);

        imageIcon = new ImageIcon("resources/System/prev-0.png");
        btnPrev = new JLabel(imageIcon);
        btnPrev.setBounds(30, 35, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        btnPrev.setFocusable(false);
        add(btnPrev);
        btnPrev.addMouseListener(mouseAdapter);

        imageIcon = new ImageIcon("resources/System/skip-0.png");
        btnSkip = new JLabel(imageIcon);
        btnSkip.setBounds(228, 15, 108, 40);
        btnSkip.setFocusable(false);
        add(btnSkip);
        btnSkip.addMouseListener(mouseAdapter);

    }

    @Override
    protected void paintComponent(Graphics graphics) {
        graphics.drawImage(Utils.loadImageFromRes("System/intro-" + part), 0, 0, GameFrame.WIDTH, GameFrame.HEIGHT, null);
    }
}
