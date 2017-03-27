package gui;

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
                        GameFrame.mainPanel.showGamePanel(false);
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
            }

        };

        ImageIcon imageIcon = new ImageIcon("resources/System/next-0.png");
        btnNext = new JLabel(imageIcon);
        btnNext.setBounds(400, 460, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        btnNext.setFocusable(false);
        add(btnNext);
        btnNext.addMouseListener(mouseAdapter);

        imageIcon = new ImageIcon("resources/System/prev-0.png");
        btnPrev = new JLabel(imageIcon);
        btnPrev.setBounds(50, 460, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        btnPrev.setFocusable(false);
        add(btnPrev);
        btnPrev.addMouseListener(mouseAdapter);

    }

    @Override
    protected void paintComponent(Graphics graphics) {
        graphics.drawImage(Utils.loadImageFromRes("System/intro-" + part), 0, 0, GameFrame.WIDTH, GameFrame.HEIGHT, null);
    }
}
