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

    public static final String BOMBERMAN = "bomberman";
    public static final String HAMMERBOMBER = "hammerbomber";
    public static final String WIN = "win";
    public static final String LOSE = "lose";

    private JLabel btnBackToMenu;
    private Image backGround;
    private Integer score;
    private JLabel lbScore;

    public EndGamePanel(String tag, Integer score) {
        setLayout(null);
        switch (tag) {
            case WIN:
                backGround = Utils.loadImageFromRes("System/game-won");
                break;
            case LOSE:
                backGround = Utils.loadImageFromRes("System/game-over2");
                break;
            case BOMBERMAN:
                backGround = Utils.loadImageFromRes("System/bomberman-won");
                break;
            case HAMMERBOMBER:
                backGround = Utils.loadImageFromRes("System/hammer-won");
                break;
        }
        this.score = score;
        initComp();
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
            @Override
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

        if (score != null) {
            lbScore = new JLabel("Your Score: " + score);
            lbScore.setFont(new Font("Bookman Old Style", Font.BOLD, 30));
            lbScore.setHorizontalAlignment(SwingConstants.CENTER);
            lbScore.setSize(500, 50);
            lbScore.setForeground(Color.BLACK);
            lbScore.setLocation(
                    (GameFrame.WIDTH - lbScore.getWidth()) / 2,
                    0
                    );
            add(lbScore);
        }

        ImageIcon imageIcon = new ImageIcon("resources/System/back-0.png");
        btnBackToMenu = new JLabel(imageIcon);
        btnBackToMenu.setBounds((GameFrame.WIDTH - imageIcon.getIconWidth()) / 2 , 500, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        btnBackToMenu.setFocusable(false);
        add(btnBackToMenu);
        btnBackToMenu.addMouseListener(mouseAdapter);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(backGround, 0, 0, GameFrame.WIDTH, GameFrame.HEIGHT, null);
    }
}
