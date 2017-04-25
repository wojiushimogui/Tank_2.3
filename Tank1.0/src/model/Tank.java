package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import client.TankClient;

	public class Tank {
		//坦克所在的位置坐标
		private int x;
		private int y;
		
		//坦克的高度和宽度
		private static final int WIDTH = 30;
		private static final int HEIGHT = 30;
		
		//定义两个常量，表示运动的速度
		private static final int XSPEED = 5;
		private static final int YSPEED = 5;
		
		//定义四个布尔类型变量来记录按键的情况,默认状态下为false，表示没有键按下
		private boolean b_L,b_U,b_R,b_D;
		
		//定义一个枚举类型来表示运行的方向	
		public enum Direction{
			L,LU,U,RU,R,RD,D,LD,STOP
		}
		//定义一个变量来表示坦克要运行的方向，初始状态为STOP
		private Direction dir = Direction.STOP;
		
		//炮筒方向
		private Direction ptDir = Direction.D;
		
		private TankClient tc;

		public Tank(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public Tank(int x, int y, TankClient tc) {
			this(x,y);
			this.tc = tc;
		}

		public void draw(Graphics g){
			
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.fillOval(x, y, 30, 30);
			g.setColor(c);
			//画一个炮筒
			drawGunBarrel(g);
			
			move();//根据键盘按键的结果改变坦克所在的位置
		}
		
		private void drawGunBarrel(Graphics g) {
			int centerX = this.x + this.WIDTH/2;
			int centerY = this.y + this.HEIGHT/2;
			
			if(ptDir==Direction.L){//L,LU,U,RU,R,RD,D,LD,STOP
				g.drawLine(centerX, centerY, x, y + HEIGHT/2);
			}
			else if(dir==Direction.LU){
				g.drawLine(centerX, centerY, x, y );
			}
			else if(dir==Direction.U){
				g.drawLine(centerX, centerY, x+ WIDTH/2, y );
			}
			else if(dir==Direction.RU){
				g.drawLine(centerX, centerY, x + WIDTH, y );
			}
			else if(dir==Direction.R){
				g.drawLine(centerX, centerY, x+ WIDTH, y + HEIGHT/2);
			}
			else if(dir==Direction.RD){
				g.drawLine(centerX, centerY, x+ WIDTH, y + HEIGHT);
			}
			else if(dir==Direction.D){
				g.drawLine(centerX, centerY, x+ WIDTH/2, y + HEIGHT);
			}
			else if(dir==Direction.LD){
				g.drawLine(centerX, centerY, x, y + HEIGHT);
			}
			
		}

		//记录键盘的按键情况
		public void keyPressed(KeyEvent e){
			int key=e.getKeyCode();
			//System.out.println(key);
			switch(key){
			case 17:
				tc.setMs(fire());
				break;
			case KeyEvent.VK_LEFT:
				b_L=true;
				break;
			case KeyEvent.VK_UP:
				b_U=true;
				break;
			case KeyEvent.VK_RIGHT:
				b_R=true;
				break;
			case KeyEvent.VK_DOWN:
				b_D=true;
				break;
			}
			//根据上面的按键情况，确定坦克即将要运行的方向
			moveDirection();
		}
		
		//键盘按键松下时，也要进行记录
		public void keyReleased(KeyEvent e) {
			int key=e.getKeyCode();
			switch(key){
			
			case KeyEvent.VK_LEFT:
				b_L=false;
				break;
			case KeyEvent.VK_UP:
				b_U=false;
				break;
			case KeyEvent.VK_RIGHT:
				b_R=false;
				break;
			case KeyEvent.VK_DOWN:
				b_D=false;
				break;
			}
		}
		
		//根据键盘的按键情况来确定坦克的运行方向
		private void moveDirection() {//L,LU,U,RU,R,RD,D,LD,STOP
			if(b_L&&!b_U&&!b_R&&!b_D){
				dir = Direction.L;
			}
			else if(b_L&&b_U&&!b_R&&!b_D){
				dir = Direction.LU;
			}
			else if(!b_L&&b_U&&!b_R&&!b_D){
				dir = Direction.U;
			}
			else if(!b_L&&b_U&&b_R&&!b_D){
				dir = Direction.RU;
			}
			else if(!b_L&&!b_U&&b_R&&!b_D){
				dir = Direction.R;
			}
			else if(!b_L&&!b_U&&b_R&&b_D){
				dir = Direction.RD;
			}
			else if(!b_L&&!b_U&&!b_R&&b_D){
				dir = Direction.D;
			}
			else if(b_L&&!b_U&&!b_R&&b_D){
				dir = Direction.LD;
			}
			else{//其它所有情况，都是不动
				dir = Direction.STOP;
			}
			//将坦克方向赋值给炮筒方向
			if(dir!=Direction.STOP){
				ptDir = dir;
			}
			
		}
	
		//上面有运行方向，但是还缺少具体的运行细节，例如：假设是按下了右键，则应该横坐标x+=XSPEED;
		private void move(){
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
			else if(dir==Direction.STOP){
				//... nothing
			}
			
		}
		
		public Missile fire(){
			//计算子弹的位置,并利用炮筒的方向来new一个子弹对象
			int x = this.x +(this.WIDTH)/2 - (Missile.WIDTH)/2;
			int y = this.y + (this.HEIGHT)/2 -(Missile.HEIGHT)/2;
			Missile ms = new Missile(x,y,this.ptDir);
			return ms;
		}
	
	}
