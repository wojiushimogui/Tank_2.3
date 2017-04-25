package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import client.TankClient;
import model.Tank.Direction;

public class Missile {

	//定义两个常量，表示运动的速度
	private static final int XSPEED = 10;
	private static final int YSPEED = 10;
	
	//子弹所在的位置
	private int x;
	private int y;
	
	//坦克的高度和宽度
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	
	//子弹的运行方向
	private Direction dir;
	
	private boolean live = true;
	
	public Missile(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	public void draw(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.YELLOW);
		g.fillOval(x, y, 5, 5);
		g.setColor(c);
		move();
	}

	private void move() {
		if(dir==Direction.L){//L,LU,U,RU,R,RD,D,LD,STOP
			x -= XSPEED;
		}
		else if(dir==Direction.LU){
			x -= XSPEED;
			y -= YSPEED;
		}
		else if(dir==Direction.U){
			y -= YSPEED;
		}
		else if(dir==Direction.RU){
			x += XSPEED;
			y -= YSPEED;
		}
		else if(dir==Direction.R){
			x += XSPEED;
		}
		else if(dir==Direction.RD){
			x += XSPEED;
			y += YSPEED;
		}
		else if(dir==Direction.D){
			y += YSPEED;
		}
		else if(dir==Direction.LD){
			x -= XSPEED;
			y += YSPEED;
		}
		
		//根据子弹所在的位置x,y来判断子弹是否还存活在
		if(x<0||x>TankClient.GAME_WIDTH||y<0||y>TankClient.GAME_HEIGHT){
			live = false;
		}
	}
	public boolean isLive() {	
		return live;
	}
}
