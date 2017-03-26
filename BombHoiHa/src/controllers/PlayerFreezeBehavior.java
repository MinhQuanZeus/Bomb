package controllers;

/**
 * Created by QuanT on 3/26/2017.
 */
public class PlayerFreezeBehavior {
    private int count;
    private int frezzePeriod;

    public PlayerFreezeBehavior(int frezzePeriod) {
        this.frezzePeriod = frezzePeriod;
        this.count = 0;
    }

    public void run(PlayerController playerController) {
        switch (playerController.getPlayerStage()) {
            case NORMAL:
                break;
            case FREEZE:
                count++;
                if(count > frezzePeriod) {
                    count = 0;
                    playerController.setPlayStage(Stage.NORMAL);
                }
                break;
        }
    }
}
