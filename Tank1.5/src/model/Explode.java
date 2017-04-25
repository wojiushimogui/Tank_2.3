package model;

import java.awt.Color;
import java.awt.Graphics;

import client.TankClient;

public class Explode {
	//爆炸所在的位置
	private int x;
	private int y;
	
	//爆炸图片的直径
	private int[] diameter={6,20,40,60,20,7};
	
	//标识爆炸对象是否存活的属性
	private boolean live =true;
	
	//标识爆炸显示到第几步了
	private int step = 0;
	
	private TankClient tc;
	
	public Explode(int x ,int y , TankClient tc){
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	
	/*
	 * 让爆炸显示出来
	 * */
	public void draw(Graphics g){
		if(!live) return;
		//判断显示到第几步了，如果全部显示完了，则此对象已死，返回
		if(step>=diameter.length){
			live = false;
			step = 0;
			return;
		}
		Color c = g.getColor();
		g.setColor(Color.YELLOW);
		g.fillOval(x, y, diameter[step], diameter[step]);
		g.setColor(c);
		step++;
	}
	
	
}
