package client;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class TankClient extends JFrame{

	public static void main(String[] args) {
		new TankClient().launchFrame();
	}
	
	public void launchFrame(){
		
		this.setTitle("坦克大战");
		this.setLocation(300, 400);
		this.setSize(300, 400);
		this.setBackground(Color.RED);
		//this.getContentPane().setBackground(Color.RED);
		this.getContentPane().setVisible(false);
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
