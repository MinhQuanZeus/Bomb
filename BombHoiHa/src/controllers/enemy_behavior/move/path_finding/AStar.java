package controllers.enemy_behavior.move.path_finding;


import java.util.Stack;
import java.util.Vector;

/**
 * Created by l on 3/9/2017.
 */
public class AStar {
    public int[][] m;
    public Cell[][] mCell;
    public int[] dinh, back;

    public AStar(int[][] m) {
        this.m = m;

    }

    public void init(){
        mCell = new Cell[14][14];
        for(int i = 0 ;i < 14;i++){
            for(int j = 0;j < 14;j++){
                Cell c = new Cell(i, j);
                mCell[i][j] = c;
            }
        }

        for(int i = 0;i < 14;i++){
            for(int j = 0 ;j < 14;j++){
                if(m[i][j] != 1 && m[i][j] != 2 && m[i][j] != 9){
                    if(i+1 < 14){
                        if(m[i+1][j] != 1 && m[i+1][j] != 2 && m[i+1][j] != 9){
                            mCell[i][j].add(mCell[i+1][j]);
                            mCell[i+1][j].add(mCell[i][j]);
                        }
                    }
                    if(j+1 < 14){
                        if(m[i][j+1] != 1 && m[i][j+1] != 2 && m[i][j+1] != 9){
                            mCell[i][j].add(mCell[i][j+1]);
                            mCell[i][j+1].add(mCell[i][j]);
                        }
                    }
                }
            }
        }
    }

    public Stack<Cell> doAStar(int startx,int starty,int endx,int endy){

        init();
        // Trong trường hợp ko tìm đc đường đi , dùng bestCell lưu điểm cuối có thể đến
        Cell bestCellCanMoveTo = null;

        if(startx == endx && starty == endy){
            Stack<Cell> a = new Stack<>();
            a.add(new Cell(startx,starty));
            return a;
        }

        Vector<Cell> open = new Vector<>();
        Vector<Cell> close = new Vector<>();

        mCell[startx][starty].setG(0);
        mCell[startx][starty].setH(mCell[startx][starty].distance(endx,endy));
        mCell[startx][starty].setF(mCell[startx][starty].getH() + mCell[startx][starty].getG());

        open.add(mCell[startx][starty]);
        while(open.size() != 0){
            Cell current = open.get(0);

            for(int i = 0 ;i < open.size();i++){
                Cell c = open.get(i);
                if(c.getF() < current.getF()){
                    current = c;
                }
            }

            bestCellCanMoveTo = current;
            open.remove(current);
            close.add(current);
            if(current.getHang() == endx && current.getCot() == endy){
                open.clear();
                close.clear();

                return find_Path(startx,starty,endx,endy);
            }else{
                for(int i = 0 ;i < current.getNext().size();i++){
                    Cell c = current.getNext().get(i);
                    if(close.contains(c)){
                        continue;
                    }

                    double check_Current_G = current.getG() + current.distance(c.getHang(),c.getCot());
                    if(!open.contains(c) || check_Current_G < c.getG()){
                        c.setComeFrom(current);
                        c.setG(check_Current_G);
                        c.setH(c.distance(endx,endy));
                        c.setF(c.getG() + c.getH());
                        if(!open.contains(c)){
                            open.add(c);
                        }
                    }
                }
            }
        }
        return find_Path(startx,starty,bestCellCanMoveTo.getHang(),bestCellCanMoveTo.getCot());
    }

    public Stack<Cell> find_Path(int startx, int starty, int endx, int endy) {
        Stack<Cell> way = new Stack<>();
        way.clear();
        Cell c = mCell[endx][endy];
        while(c != null){
            way.push(c);
            Cell c1 = c.getComeFrom();
            c.setComeFrom(null);
            c = c1;
        }

        return way;
    }


}
