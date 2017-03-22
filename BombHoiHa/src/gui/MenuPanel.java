package gui;

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

    public MenuPanel() {
        setLayout(null);
        initComp();
    }

    private void initComp() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                Utils.playSound("select.wav",false);
                if (mouseEvent.getSource().equals(btnExit)) {
                    System.exit(0);
                }
                if (mouseEvent.getSource().equals(btnStart)) {
                    GameFrame.mainPanel.showGamePanel();
                }
                if (mouseEvent.getSource().equals(btnInstruction)) {
                    GameFrame.mainPanel.showPanel(MainPanel.TAG_INSTRUCTION);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if(e.getSource().equals(btnStart)){
                    ImageIcon imageIcon = new ImageIcon("resources/System/start-1.png");
                    btnStart.setIcon(imageIcon);
                }
                if(e.getSource().equals(btnExit)){
                    ImageIcon imageIcon = new ImageIcon("resources/System/exit-1.png");
                    btnExit.setIcon(imageIcon);
                }
                if(e.getSource().equals(btnInstruction)){
                    ImageIcon imageIcon = new ImageIcon("resources/System/instruction-1.png");
                    btnInstruction.setIcon(imageIcon);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if(e.getSource().equals(btnStart)){
                    ImageIcon imageIcon = new ImageIcon("resources/System/start-0.png");
                    btnStart.setIcon(imageIcon);
                }
                if(e.getSource().equals(btnExit)){
                    ImageIcon imageIcon = new ImageIcon("resources/System/exit-0.png");
                    btnExit.setIcon(imageIcon);
                }
                if(e.getSource().equals(btnInstruction)){
                    ImageIcon imageIcon = new ImageIcon("resources/System/instruction-0.png");
                    btnInstruction.setIcon(imageIcon);
                }
            }

        };

        ImageIcon imageIcon = new ImageIcon("resources/System/start-0.png");
        btnStart = new JLabel(imageIcon);
        btnStart.setBounds(350, 400, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        btnStart.setFocusable(false);
        add(btnStart);
        btnStart.addMouseListener(mouseAdapter);

        imageIcon = new ImageIcon("resources/System/instruction-0.png");
        btnInstruction = new JLabel(imageIcon);
        btnInstruction.setBounds(btnStart.getX(), btnStart.getY() + btnStart.getHeight() + 10, btnStart.getWidth(), btnStart.getHeight());
        btnInstruction.setFocusable(false);
        add(btnInstruction);
        btnInstruction.addMouseListener(mouseAdapter);

        imageIcon = new ImageIcon("resources/System/exit-0.png");
        btnExit = new JLabel(imageIcon);
        btnExit.setBounds(btnStart.getX(), btnStart.getY() + btnStart.getHeight() + 60, btnStart.getWidth(), btnStart.getHeight());
        btnExit.setFocusable(false);
        add(btnExit);
        btnExit.addMouseListener(mouseAdapter);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        graphics.drawImage(Utils.loadImageFromRes("System/game-menu"), 0, 0, GameFrame.WIDTH, GameFrame.HEIGHT, null);
    }
}
