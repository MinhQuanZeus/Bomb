package controllers.enemy_behavior.move.path_finding;

import java.util.Vector;

public class Cell {
	public int x,y;
	public Vector<Cell> next;
	public double g;
	public double h;
	public double f;
	public Cell comeFrom;

	public Cell(int x, int y){
		this.x = x;
		this.y = y;
		next = new Vector<>();
		g = 0;
		h = 0;
		f = 0;
		comeFrom = null;
	}
	
	public int getHang() {
		return x;
	}

	public void setHang(int x) {
		this.x = x;
	}

	public int getCot() {
		return y;
	}

	public void setCot(int y) {
		this.y = y;
	}

	public void add(Cell newcell){
		next.add(newcell);
		
	}
	
	public void removeOneNeiboCell(Cell oldcell){
		next.remove(oldcell);
	}
	public void remove(){
		for(int i = 0;i < next.size();i++){
			next.get(i).removeOneNeiboCell(this);
		}
		next.clear();
	}
	public Vector<Cell> getNext(){
		return next;
	}

	public double distance(int x1,int y1){
		return Math.sqrt((y1-y)*(y1-y) + (x1-x)*(x1-x));
	}

	public void setComeFrom(Cell comeFrom) {
		this.comeFrom = comeFrom;
	}

	public void setG(double g) {
		this.g = g;
	}

	public void setH(double h) {
		this.h = h;
	}

	public void setF(double f) {
		this.f = f;
	}

	public double getG() {
		return g;
	}

	public double getH() {
		return h;
	}

	public double getF() {
		return f;
	}

	public Cell getComeFrom() {
		return comeFrom;
	}
}
