package controllers.enemy_behavior.move;

import controllers.EnemyController;
import controllers.enemy_behavior.move.path_finding.AStar;
import controllers.enemy_behavior.move.path_finding.Cell;
import manager.MapManager;
import models.EnemyModel;
import models.ItemMapModel;
import models.PlayerModel;
import views.EnemyView;

import java.util.Stack;

/**
 * Created by l on 3/16/2017.
 */
public class MoveFindPlayer extends EnemyMoveBehavior {

    // đang đi thì trên đường bị đặt bom =>> tìm đường khác => mustFindOtherWay = true
    private boolean mustFindOtherWay = false;

    //class chay bfs
    private AStar aStar;
    // đường đi tới đích thứ tự: từ điểm kết thúc đến điểm bắt đầu
    private Stack<Cell> way;

    // ô đang đến
    private Cell nextCell;
    // ô trước đóđang đi qua nhưng chưa ra ngoài hẳn
    private Cell lastCell;

    private boolean beginState = true;

    @Override
    public void move(EnemyModel model, EnemyView view, PlayerModel playerModel, EnemyController.EnemyType type, EnemyController enemyController) {
        super.move(model, view, playerModel, type, enemyController);

        int hangPlayer = (playerModel.getY() + playerModel.getHeight() - ItemMapModel.SIZE_TILED + 5)/ ItemMapModel.SIZE_TILED;
        int cotPlayer = (playerModel.getX() + 5)/ItemMapModel.SIZE_TILED;

        int hangEnemy = (model.getY() + model.getHeight() - ItemMapModel.SIZE_TILED + 5) / ItemMapModel.SIZE_TILED;
        int cotEnemy = (model.getX() + 5) / ItemMapModel.SIZE_TILED;

        if(beginState){
            beginState = false;

            aStar = new AStar(MapManager.map);
            way = new Stack<>();
            nextCell = new Cell(hangEnemy,cotEnemy);
            lastCell = new Cell(hangEnemy,cotEnemy);
        }else{
            if(way.size() == 0){
                way = aStar.doAStar(hangEnemy,cotEnemy,hangPlayer,cotPlayer);
                nextCell = way.peek();
                lastCell = nextCell;

            }else{
                model.moveCorrectly(nextCell.getCot() * ItemMapModel.SIZE_TILED - ( model.getWidth() - ItemMapModel.SIZE_TILED),nextCell.getHang() * ItemMapModel.SIZE_TILED - (model.getHeight() - ItemMapModel.SIZE_TILED ));

                // 9 là bom trên bản đồ, tự nhiên xuất hiện bom

                if(MapManager.map[nextCell.getHang()][nextCell.getCot()] == 9){
                    Cell c = lastCell;
                    lastCell = nextCell;
                    nextCell = c;
                    mustFindOtherWay = true;
                }

                if((model.getX() == (nextCell.getCot() * ItemMapModel.SIZE_TILED - ( model.getWidth() - ItemMapModel.SIZE_TILED))) && (model.getY() == (nextCell.getHang() * ItemMapModel.SIZE_TILED - (model.getHeight() - ItemMapModel.SIZE_TILED)))){
                    way.pop();
                    if(mustFindOtherWay){
                        mustFindOtherWay = false;
                        way = aStar.doAStar(nextCell.getHang(), nextCell.getCot(), hangPlayer, cotPlayer);
                        nextCell = way.peek();
                        lastCell = nextCell;


                    }else {
                        // cho vị trí con chim trùng với vị trí nextCell luôn

                        if (way.empty()) { // hoàn thành việc đi vì đã đến cell đích, stack rỗng
                            enemyController.setEnemyMoveBehavior(new Stop());
                        } else {
                            lastCell = nextCell;
                            nextCell = way.peek();
                        }
                    }
                }
            }
        }

        if(way.size() != 0){
            if(lastCell.getCot() == nextCell.getCot()){
                if(lastCell.getHang() < nextCell.getHang()){
                    moveDirection = "xuong";

                }else{
                    moveDirection = "len";
                }
            }
            else if(lastCell.getHang() == nextCell.getHang()){
                if(lastCell.getCot() < nextCell.getCot()){
                    moveDirection = "phai";
                }else{
                    moveDirection = "trai";
                }
            }
        }

        setImage();
    }

}
