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

import model.Explode;
import model.Missile;
import model.Tank;
/*
 * 此版本为当有敌对坦克被击中后产生爆炸的效果
 * */
public class TankClient extends Frame{
	
	public final static int GAME_WIDTH=600;
	public final static int GAME_HEIGHT=600;
	
	
	private Tank tk=new Tank(50,50,true,this);
	
	
	//用来保存多辆敌方坦克
	private List<Tank> enemyTanks = new ArrayList<Tank>();
	public List<Tank> getEnemyTanks() {
		return enemyTanks;
	}

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
		//直接调用坦克类的draw方法
		tk.draw(g);	
		//将敌方坦克显示出来
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
				ms.draw(g);
				//每个子弹都要与每个敌方的坦克进行碰撞检测
				for(int j=0;j<enemyTanks.size();j++){
					Tank enemy = enemyTanks.get(j);
					if(enemy.isLive()){
						ms.hitTank(enemy);
					}
				}
				
				
			}
			
		}
	}

	public void launchFrame(){
		
		//初始化10个坦克在界面上
		for(int i=0;i<10;i++){
			Tank enemy = new Tank((i+1)*40,50,false,this);
			enemyTanks.add(enemy);
		}
		
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
