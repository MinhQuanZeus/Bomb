package gui;

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

    public MenuPanel() {
        setLayout(null);
        initComp();
    }

    private void initComp() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                if (mouseEvent.getSource().equals(btnExit)) {
                    System.exit(0);
                }
                if (mouseEvent.getSource().equals(btnStart)) {
                    GameFrame.mainPanel.showPanel(MainPanel.TAG_GAME);
                }
            }
        };

        ImageIcon imageIcon = new ImageIcon("resources/System/button_start.png");
        btnStart = new JLabel(imageIcon);
        btnStart.setBounds(350, 400, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        btnStart.setFocusable(false);
        add(btnStart);
        btnStart.addMouseListener(mouseAdapter);

        imageIcon = new ImageIcon("resources/System/button_exit.png");
        btnExit = new JLabel(imageIcon);
        btnExit.setBounds(btnStart.getX(), btnStart.getY() + btnStart.getHeight() + 10, btnStart.getWidth(), btnStart.getHeight());
        btnExit.setFocusable(false);
        add(btnExit);
        btnExit.addMouseListener(mouseAdapter);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        graphics.drawImage(Utils.loadImageFromRes("System/menu-buttonless"), 0, 0, GameFrame.WIDTH, GameFrame.HEIGHT, null);
    }
}
