package gui;

import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by KhoaBeo on 3/28/2017.
 */
public class ChooseMapPanel extends JPanel {

    private JLabel thumb0;
    private JLabel thumb1;
    private JLabel thumb2;
    private JLabel thumb3;
    private JLabel thumb4;
    private JLabel btnBackToMenu;

    public ChooseMapPanel() {
        setLayout(null);
        initComp();
    }

    private void initComp() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (mouseEvent.getSource().equals(btnBackToMenu)) {
                    GameFrame.mainPanel.showPanel(MainPanel.TAG_MENU);
                }

                if (mouseEvent.getSource().equals(thumb0)) {
                    GameFrame.mainPanel.showGamePanel(true, 0);
                }

                if (mouseEvent.getSource().equals(thumb1)) {
                    GameFrame.mainPanel.showGamePanel(true, 1);
                }

                if (mouseEvent.getSource().equals(thumb2)) {
                    GameFrame.mainPanel.showGamePanel(true, 2);
                }

                if (mouseEvent.getSource().equals(thumb3)) {
                    GameFrame.mainPanel.showGamePanel(true, 3);
                }

                if (mouseEvent.getSource().equals(thumb4)) {
                    GameFrame.mainPanel.showGamePanel(true, 4);
                }
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                if (mouseEvent.getSource().equals(btnBackToMenu)) {
                    ImageIcon imageIcon = new ImageIcon("resources/System/back-0.png");
                    btnBackToMenu.setIcon(imageIcon);
                }
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                if (mouseEvent.getSource().equals(btnBackToMenu)) {
                    ImageIcon imageIcon = new ImageIcon("resources/System/back-1.png");
                    btnBackToMenu.setIcon(imageIcon);
                }
            }
        };

        ImageIcon icon = new ImageIcon("resources/System/thumb-0.png");
        thumb0 = new JLabel(icon);
        thumb0.setBounds(10, 200, 100, 100);
        add(thumb0);
        thumb0.addMouseListener(mouseAdapter);

        icon = new ImageIcon("resources/System/thumb-1.png");
        thumb1 = new JLabel(icon);
        thumb1.setBounds(thumb0.getX() + thumb0.getWidth() + 10, thumb0.getY(), 100, 100);
        add(thumb1);
        thumb1.addMouseListener(mouseAdapter);

        icon = new ImageIcon("resources/System/thumb-2.png");
        thumb2 = new JLabel(icon);
        thumb2.setBounds(thumb1.getX() + thumb0.getWidth() + 10, thumb0.getY(), 100, 100);
        add(thumb2);
        thumb2.addMouseListener(mouseAdapter);

        icon = new ImageIcon("resources/System/thumb-3.png");
        thumb3 = new JLabel(icon);
        thumb3.setBounds(thumb2.getX() + thumb0.getWidth() + 10, thumb0.getY(), 100, 100);
        add(thumb3);
        thumb3.addMouseListener(mouseAdapter);

        icon = new ImageIcon("resources/System/thumb-4.png");
        thumb4 = new JLabel(icon);
        thumb4.setBounds(thumb3.getX() + thumb0.getWidth() + 10, thumb0.getY(), 100, 100);
        add(thumb4);
        thumb4.addMouseListener(mouseAdapter);

        ImageIcon imageIcon = new ImageIcon("resources/System/back-0.png");
        btnBackToMenu = new JLabel(imageIcon);
        btnBackToMenu.setBounds((GameFrame.WIDTH - imageIcon.getIconWidth()) / 2, 507, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        add(btnBackToMenu);
        btnBackToMenu.addMouseListener(mouseAdapter);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        graphics.drawImage(Utils.loadImageFromRes("System/map-pick"), 0, 0, null);
    }
}
