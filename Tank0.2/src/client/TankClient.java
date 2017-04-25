package client;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class TankClient extends Frame{

	public static void main(String[] args) {
		new TankClient().launchFrame();
	}
	
	@Override
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(50, 50, 30, 30);
		g.setColor(c);
	}

	public void launchFrame(){
		
		this.setTitle("坦克大战");
		this.setLocation(300, 400);
		this.setSize(600, 400);
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
	}

}
