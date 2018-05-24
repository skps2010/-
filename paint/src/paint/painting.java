package paint;
import java.awt.*;    
import java.awt.event.*;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;




public class painting extends JFrame{
Image icon, dart,back ;
JPanel pm = new JPanel(null);
int where;
int speed;
int play=0;
Point[] p= new Point[60];
int timer;
int arrow;
int go;
int attact;
int high=0;
float t;

public static void main(String[] args) {
	painting p = new painting(); 
	p.set();
	p.gameset();
	p.ready();
}

public void set(){
setResizable(false);
setSize(640, 640);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
getContentPane().add(pm);
setVisible(true);
setLocationRelativeTo(null);
setFocusable(true);
repaint();
addKeyListener(new KeyListener() {
	public void keyPressed(KeyEvent e) {
		requestFocus();
		int key = e.getKeyCode();
		if(key==KeyEvent.VK_LEFT)
			go=-1;
		if(key==KeyEvent.VK_RIGHT)
			go=1;
		if(key==KeyEvent.VK_SPACE)
			play=1;
			
	}
	public void keyReleased(KeyEvent k) {
		go=0;
	}

	public void keyTyped(KeyEvent k) {
	}
	
});
try {
    icon = ImageIO.read(new File("icon.png"));
    dart = ImageIO.read(new File("1.png"));
    back = ImageIO.read(new File("back.jpg"));
}
catch (Exception ex) {
    System.out.println("No example.jpg!!");
}

}

public void gameset()
{
	where=330;
	speed=0;
	timer=0;
	arrow=0;
	go=0;
	attact=20;
	for(int i=0;i<60;i++)
	{
		p[i] = new Point(0, 700);
	}
}

public void ready(){
	repaint();
	while(play==0)
	{
		try {

			Thread.sleep(120);

		} catch (InterruptedException e) {
		}
	}
	run();
}

public void gameover(){
	if(timer/20>high)high=timer/20;
	repaint();
	while(play==2)
	{
		try {

			Thread.sleep(120);

		} catch (InterruptedException e) {
		}
	}
	gameset();
	run();
}

public void paint(Graphics paint) {
	Image offscreen = createImage(640, 640);
	Graphics g = offscreen.getGraphics();
	g.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
	
	if(play==1){
	g.drawImage(back, 0, 0, 640, 640,null);
	if(go==0)speed*=0.5;
	if(go==1)speed+=5;
	if(go==-1)speed-=5;
	if(speed>30)speed=30;
	if(speed<-30)speed=-30;
	where+=speed;
	if(where<0)where=0;
	if(where>590)where=590;
	g.drawImage(icon, where, 550, null);
	for(int i=0;i<60;i++)
	{
		p[i].setLocation(p[i].getX(),p[i].getY()+10);
		if(p[i].getY()<=640)g.drawImage(dart, (int)p[i].getX(), (int)p[i].getY(), null);
		if(where+50>p[i].getX() && where-30<p[i].getX() && p[i].getY()+50>550 && p[i].getY()<600)play=2;		
	}
	}
	if(play==2)
	{	
		g.drawImage(back, 0, 0, 640, 640, null);
		g.drawString("你撐了"+timer/20+"秒", 200, 200);
		g.drawString("按空白鍵重新", 175, 400);
		g.drawString("最高分："+high+"秒", 175, 600);
	}
	if(play==0)
	{	
		g.drawImage(back, 0, 0, 640, 640, null);
		g.drawString("飛機閃飛彈遊戲", 150, 200);
		g.drawString("按空白鍵開始", 175, 400);
		g.drawString("作者：盧昭華", 175, 600);
	}
	paint.drawImage(offscreen, 0, 0, null);
	
	
	
}



public void run() {
	while(play==1) {
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
		}
		timer++;
		if(timer%attact==0)
		{
			t=(float)timer/1000;
			if(t>2)t=2;
			for(int i=0;i<Math.random()*(1+t);i++)
			{
			p[arrow].setLocation(Math.random()*610,0);
			arrow++;
			if(arrow==59)arrow=0;
			}
		}
		if(timer%100==0&&attact>4)attact--;
		repaint();
		setTitle("存活時間:"+timer/20+"秒");
	}
	gameover();
}
}
	
