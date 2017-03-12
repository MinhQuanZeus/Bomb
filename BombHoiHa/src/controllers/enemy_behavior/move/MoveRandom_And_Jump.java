package controllers.enemy_behavior.move;

import controllers.EnemyController;
import controllers.GameController;
import gui.GameFrame;
import models.EnemyModel;
import models.GameModel;
import models.ItemMapModel;
import models.PlayerModel;
import utils.Utils;
import views.EnemyView;

import java.awt.*;
import java.util.List;

/**
 * Created by l on 3/11/2017.
 */
public class MoveRandom_And_Jump extends EnemyMoveBehavior {
    private String lastMove = "";
    private boolean jump = false;
    private int xOfModelJumpTo = -1;
    private int yOfModelJumpTo = -1;

    // xác định vừa mới  vào lần đầu hàm move
    private boolean beginState = true;

    // dành cho việc co ảnh khi nhảy qua vật và quay về kích thước đầu khi đến  ô cần
    private int modelHeight;

    @Override
    public void move(EnemyModel model, EnemyView view, PlayerModel playerModel, List<GameController> gameControllers, EnemyController.EnemyType type, EnemyController enemyController) {
        super.move(model, view, playerModel, gameControllers, type, enemyController);
        if (beginState) {
            beginState = false;
            modelHeight = model.getHeight();
        }

        if (jump) {
            model.moveCorrectly(xOfModelJumpTo, yOfModelJumpTo);
            setImage();
            if (model.getX() == xOfModelJumpTo && model.getY() == yOfModelJumpTo) {
                model.setHeight(modelHeight);
                jump = false;
                xOfModelJumpTo = -1;
                yOfModelJumpTo = -1;
            }
        } else {
            int x1 = model.getX();
            int y1 = model.getY();

            if (lastMove.equals("")) {
                switch (Utils.getRandom(4)) {
                    case (0): {
                        x1 += model.getSpeed();
                        break;
                    }
                    case (1): {
                        x1 -= model.getSpeed();
                        break;
                    }
                    case (2): {
                        y1 += model.getSpeed();
                        break;
                    }
                    case (3): {
                        y1 -= model.getSpeed();
                        break;
                    }
                }
            } else {
                switch (lastMove) {
                    case ("len"): {
                        y1 -= model.getSpeed();
                        break;
                    }
                    case ("xuong"): {
                        y1 += model.getSpeed();
                        break;
                    }
                    case ("trai"): {
                        x1 -= model.getSpeed();
                        break;
                    }
                    case ("phai"): {
                        x1 += model.getSpeed();
                        break;
                    }
                }
            }

            Rectangle r = new Rectangle(x1 + 5, y1 + ItemMapModel.SIZE_TILED - ItemMapModel.SIZE_TILED / 2, ItemMapModel.SIZE_TILED - 10, ItemMapModel.SIZE_TILED / 2);

            if (x1 < 0 || x1 > GameFrame.WIDTH - ItemMapModel.SIZE_TILED || y1 < 0 + ItemMapModel.SIZE_TILED || y1 > GameFrame.HEIGHT - ItemMapModel.SIZE_TILED * 2) {
                lastMove = "";
                x1 = model.getX();
                y1 = model.getY();
            } else {
                for (int i = 0; i < gameControllers.size(); i++) {
                    if (gameControllers.get(i).getModel().getRect().intersects(r)) {
                        setImage();
                        jump = true;
                        GameModel g = gameControllers.get(i).getModel();

                        switch (lastMove) {
                            case ("len"): {
                                xOfModelJumpTo = x1;
                                yOfModelJumpTo = y1 - g.getHeight() - model.getHeight();
                                break;
                            }
                            case ("xuong"): {
                                xOfModelJumpTo = x1;
                                yOfModelJumpTo = y1 + g.getHeight() + model.getHeight() - model.getHeight()/2;
                                break;
                            }
                            case ("trai"): {
                                xOfModelJumpTo =x1 - g.getWidth() - (ItemMapModel.SIZE_TILED - 10);
                                yOfModelJumpTo = y1;
                                break;
                            }
                            case ("phai"): {
                                xOfModelJumpTo = x1 + model.getWidth() + g.getWidth() - 10;
                                yOfModelJumpTo = y1;
                                break;
                            }
                        }
                        if (xOfModelJumpTo < 0  || xOfModelJumpTo > GameFrame.WIDTH - ItemMapModel.SIZE_TILED || yOfModelJumpTo < 0 + ItemMapModel.SIZE_TILED || yOfModelJumpTo > GameFrame.HEIGHT - ItemMapModel.SIZE_TILED) {
                            lastMove = "";
                            x1 = model.getX();
                            y1 = model.getY();
                            jump = false;
                        }else{
                            for (int j = 0; j < gameControllers.size(); j++) {
                                if (gameControllers.get(j).getModel().getRect().intersects(new Rectangle(xOfModelJumpTo + 5, yOfModelJumpTo + model.getHeight() - ItemMapModel.SIZE_TILED / 2, ItemMapModel.SIZE_TILED - 10, ItemMapModel.SIZE_TILED / 2))) {
                                    jump = false;

                                }
                            }
                            lastMove = "";
                            return;
                        }
                    }
                }
            }

            if (x1 != model.getX()) {
                if (model.getX() > x1) {
                    lastMove = "trai";
                    moveDirection = "trai";
                    model.moveLeft();
                } else {
                    lastMove = "phai";
                    moveDirection = "phai";
                    model.moveRight();
                }
            } else if (y1 != model.getY()) {
                if (model.getY() > y1) {
                    lastMove = "len";
                    moveDirection = "len";
                    model.moveUp();
                } else {
                    lastMove = "xuong";
                    moveDirection = "xuong";
                    model.moveDown();
                }
            }


            setImage();
        }
    }

    @Override
    public void setImage() {
        if (jump) {
            super.setImage();
            if (model.getHeight() < EnemyModel.HEIGHT - 20) {
                model.setHeight(model.getHeight() + 1);
            } else {
                model.setHeight(model.getHeight() - 1);
            }
        } else {
            super.setImage();
        }
    }
}
