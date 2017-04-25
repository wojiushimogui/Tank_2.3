package client;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Explode;
import model.Missile;
import model.Tank;
import model.Tank.Direction;
/*
 * 此版本的改进：处理当我方坦克死亡之后，按下键盘A产生新的我方坦克。
 *
 * */
public class TankClient extends Frame{
	
	public final static int GAME_WIDTH=600;
	public final static int GAME_HEIGHT=600;
	
	
	private Tank tk=new Tank(50,50,true,Direction.STOP,this);
	//主坦克的get、set方法
	public Tank getTk() {
		return tk;
	}

	public void setTk(Tank tk) {
		this.tk = tk;
	}

	private Random r = new Random();
	//存放多辆敌方坦克
	private List<Tank> enemyTanks = new ArrayList<Tank> ();
	//private Tank enemy = new Tank(100,100,false,this);
	
	/*
	 * 为每个被击中的坦克添加一个爆炸对象
	 * */
	private List<Explode> explodes = new ArrayList<Explode>(); 
	
	public List<Explode> getExplodes() {
		return explodes;
	}

	private List<Missile> missiles = new ArrayList<Missile> ();
	
	public List<Missile> getMissiles() {
		return missiles;
	}

	private Image offScreenImage = null;
	
	public static void main(String[] args) {
		new TankClient().launchFrame();
	}
	
	@Override
	public void update(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics goffScreen = offScreenImage.getGraphics();// 重新定义一个画虚拟桌布的画笔//
		Color c = goffScreen.getColor();
		goffScreen.setColor(Color.darkGray);
		goffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		goffScreen.setColor(c);
		paint(goffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	@Override
	public void paint(Graphics g) {
		
		/*
		 * 画出我们自己的坦克，首先判断自己的坦克是否是活的，如果是，则画出来
		 * 否则，则提示 Game  Over ,并休眠100000毫秒
		 * */
		if(tk.isLive()){
			tk.draw(g);	
		}
		else{
			g.drawString("Game Over,按键A可以复活！！！",GAME_WIDTH/2 , GAME_HEIGHT/2);
		}	
		
		/*
		 * 将敌方坦克也画出来,如果没有了敌方坦克，则产生一定数量的地方坦克
		 * */
		if(enemyTanks.size()==0){
			this.produceTank();
		}
		for(int i=0;i<enemyTanks.size();i++){
			Tank enemy = enemyTanks.get(i);
			if(!enemy.isLive()){
				enemyTanks.remove(enemy);
			}
			else{
				enemy.draw(g);
			}
			
		}
		
		//炸弹对象
		for(int i=0;i<explodes.size();i++){
			Explode e = explodes.get(i);
			if(!e.isLive()){
				explodes.remove(e);
			}
			else{
				e.draw(g);
			}
			
		}
		
		//画子弹
		for(int i=0;i<missiles.size();i++){
			Missile ms = missiles.get(i);			
			//判断子弹是否还存活在，如果不是存活的，则移除
			if(!ms.isLive()){
				missiles.remove(ms);
			}
			else{
				ms.hitTanks(enemyTanks);
				ms.draw(g);
				//将自己的坦克也加入到子弹可以攻击的范围
				ms.hitTank(tk);
			}
			
		}
	}
	/*
	 * 函数功能：产生敌方坦克
	 * */
	public void produceTank(){
		
		int totalNum =r.nextInt(4)+3 ;
		
		for(int i=0;i<totalNum;i++){
			//位置也随机
			int x = (r.nextInt(10)+1)*40;
			int y = (r.nextInt(10)+1)*30;
			
			Direction[] dirs =Direction.values();
			int rn = r.nextInt(dirs.length);
			Direction dir = dirs[rn];
			Tank enemy = new Tank(x,y,false,dir,this);
			enemyTanks.add(enemy);
		}
	}
	public void launchFrame(){
		//初始化多辆敌方坦克
		this.produceTank();
		
		this.setTitle("坦克大战");
		this.setLocation(300, 400);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setBackground(Color.GRAY);
		//为关闭窗口添加响应
		this.addWindowListener(new WindowAdapter(){

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
		//设置是否允许用户改变窗口的大小，这里限制下，不允许
		this.setResizable(false);
		this.setVisible(true);
		
		new Thread(new MyRepaint()).start();
		this.addKeyListener(new KeyMonitor());
		
	}
	
	private class MyRepaint implements Runnable{

		@Override
		public void run() {
			while(true){
				//每50ms重画一次
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private class KeyMonitor extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			tk.keyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			tk.keyReleased(e);
		}	
		
	}

}
