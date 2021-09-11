import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;


public class BrickBreaker extends JFrame implements ActionListener,KeyListener{

	JPanel leftpanel,rightpanel,scorepanel;
	JTextArea scoretext;
	int score,life;

	Planet_3D p3d;
	ImageIcon planet3d;

	MyPanel gamepanel;

	LifePanel lifepanel;


	JLabel stagelabel, planetlabel, masslabel, radiuslabel;

	String[] planetarray={"","Earth","Moon","Mars","Jupiter","Saturn","Uranus","Neptune","Pluto","Venus","Mercury","Sun"};
	String[] massarray={"","5.972 ¡¿ 10^24","7.36 ¡¿ 10^22","6.39 ¡¿ 10^23","1.898 ¡¿ 10^27","5.683 ¡¿ 10^26","8.681 ¡¿ 10^25","1.024 ¡¿ 10^26","1.3 ¡¿ 10^22","4.867 ¡¿ 10^24","3.285 ¡¿ 10^23","1.989 ¡¿ 10^30"};
	String[] radiusarray={"","6371","1737","3390","69911","58232","25362","24622","1186","6052","2440","695700"};


	//Timer t1,t2;
	//int period1=10,period2=20000;

	InsteadofTimer1 thread1;
	InsteadofTimer2 thread2;

	int stage;

	int width=1700,height=1000;


	BrickBreaker(int stage,int score,int life){

		this.stage=stage;
		this.score=score;
		this.life=life;
		
		
		setTitle("Gardians of Galaxy");
		setSize(width,height);

		leftpanel=new JPanel();
		leftpanel.setLayout(new BorderLayout(30,30));
		leftpanel.setSize(150,1000);
		leftpanel.setBackground(Color.BLACK);

		scorepanel=new JPanel();

		TitledBorder title=new TitledBorder("Á¡¼ö");
		title.setTitleColor(Color.WHITE);
		scorepanel.setBorder(title);
		scorepanel.setBackground(Color.BLACK);

		scoretext=new JTextArea(1,8);
		scoretext.setPreferredSize(new Dimension(1,8));
		scoretext.setForeground(Color.WHITE);
		String scorestring=new String(String.format("    %010d",score));
		scoretext.setText(scorestring);
		scoretext.setEditable(false);
		scoretext.setBackground(Color.BLACK);

		scorepanel.add(scoretext);


		lifepanel=new LifePanel();
		lifepanel.setBackground(Color.BLACK);

		leftpanel.add(scorepanel,BorderLayout.NORTH);
		leftpanel.add(lifepanel,BorderLayout.CENTER);

		add(leftpanel,BorderLayout.WEST);


		gamepanel=new MyPanel();
		gamepanel.setSize(gamepanel.width, gamepanel.height);

		
		p3d=new Planet_3D(stage,planetarray[stage]);
		p3d.onSaveImage(); 

		planet3d=new ImageIcon("Planet/planet"+stage+".jpg");

		add(gamepanel,BorderLayout.CENTER);


		rightpanel=new JPanel();
		rightpanel.setBounds(1650, 0, 150, 1000);
		rightpanel.setLayout(new BoxLayout(rightpanel,BoxLayout.Y_AXIS));
		rightpanel.setBackground(Color.BLACK);

		stagelabel=new JLabel("Stage:");
		stagelabel.setForeground(Color.WHITE);
		JTextArea jta1=new JTextArea(String.valueOf(stage));
		jta1.setForeground(Color.WHITE);
		jta1.setBackground(Color.BLACK);
		rightpanel.add(stagelabel);
		stagelabel.setLabelFor(jta1);
		rightpanel.add(jta1);

		planetlabel=new JLabel("Planet:");
		planetlabel.setForeground(Color.WHITE);
		JTextArea jta2=new JTextArea(planetarray[stage]);
		jta2.setForeground(Color.WHITE);
		jta2.setBackground(Color.BLACK);
		rightpanel.add(planetlabel);
		stagelabel.setLabelFor(jta2);
		rightpanel.add(jta2);

		masslabel=new JLabel("Mass of Planet:");
		masslabel.setForeground(Color.WHITE);
		JTextArea jta3=new JTextArea(massarray[stage]+"(kg)");
		jta3.setForeground(Color.WHITE);
		jta3.setBackground(Color.BLACK);
		rightpanel.add(masslabel);
		stagelabel.setLabelFor(jta3);
		rightpanel.add(jta3);

		radiuslabel=new JLabel("Radius of Planet:");
		radiuslabel.setForeground(Color.WHITE);
		JTextArea jta4=new JTextArea(radiusarray[stage]+"(km)");
		jta4.setForeground(Color.WHITE);
		jta4.setBackground(Color.BLACK);
		rightpanel.add(radiuslabel);
		stagelabel.setLabelFor(jta4);
		rightpanel.add(jta4);

		add(rightpanel,BorderLayout.EAST);


		addKeyListener(this);
		this.setFocusable(true);

		setVisible(true);

		//t1=new Timer(period1,this);
		//t2=new Timer(period2,this);
		//t1.start();
		//t2.start();
		
		thread1=new InsteadofTimer1();
		thread2=new InsteadofTimer2();
		thread1.start();
		thread2.start();
		
		
	}

	class LifePanel extends JPanel{

		LifePanel(){
			TitledBorder title=new TitledBorder("°ø");
			title.setTitleColor(Color.WHITE);
			setBorder(title);
			setBackground(Color.GRAY);
		}

		@Override
		protected void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponent(g);

			g.setColor(Color.WHITE);
			for(int i=0;i<life/5;++i){
				for(int j=0;j<5;++j){
					g.fillOval(j*20+10,i*20+20,10,10);
				}
			}
			for(int i=0;i<life%5;++i){
				g.fillOval(i*20+10,life/5*20+20,10,10);
			}

		}

	}

	public double AngletoRadian(double angle){
		return angle/180*Math.PI;
	}

	double Anormalize(double angle){
		return (double)(((int)angle%360+360)%360);
	}

	boolean Dchk(double s,double m,double e){
		return s<=m&&m<=e;
	}

	boolean Achk(double s,double m,double e){
		s=Anormalize(s);
		m=Anormalize(m);
		e=Anormalize(e);
		if(s<=m&&m<=e)return true;
		if(s>e&&(m<=e||(s<=m&&m<360)))return true;
		return false;
	}

	double Dis(double r1,double theta1,double r2,double theta2){
		return Math.sqrt(r1*r1+r2*r2-2*r1*r2*Math.cos(AngletoRadian(theta1-theta2)));
	}

	class MyPanel extends JPanel{
		Vector<Brick> brick;
		Vector<Ball> ball;
		Planet planet;
		Board board;
		int width=1500,height=1000,originx,originy;

		MyPanel(){

			originx=width/2;
			originy=height/2;

			int[] bs={0,300,200,230,350,300,300,300,200,300,250,300};
			int[] be={0,350,250,300,450,400,400,350,300,400,300,450};
			int[] bi={0, 20, 20, 30, 30, 20, 20, 10, 30, 20, 10, 15};
			int[] bj={0, 40, 40, 20, 30, 40, 30, 30, 40, 20, 20, 30};
			int[] bh={0, 10,  8, 10, 10, 10, 10, 10,  8, 10,  8, 10};
			int[] bw={0, 20, 20, 10, 30, 30, 20, 20, 20, 15, 15, 10};
			int[] al={0,  1,  1,  2,  3,  3,  3,  3,  3,  3,  4,  5};
			


			brick=new Vector<Brick>();
			for(int i=bs[stage];i<be[stage];i+=bi[stage]){
				for(int j=0;j<360;j+=bj[stage]){
					brick.add(new Brick(originx,originy,i,j,bh[stage],bw[stage],(int)(1+al[stage]*Math.random())));
				}
			}

			int[]    pr={0,   150,   115,   130,   190,   175,   160,   160,   110,   150,   125,   205};
			double[] pm={0,5000.0,4000.0,4500.0,7000.0,6000.0,5500.0,5500.0,3500.0,5000.0,4500.0,8000.0};

			ball=new Vector<Ball>();
			ball.add(new Ball(originx,originy,pr[stage]+17,-90));

			planet=new Planet(originx,originy,pr[stage],pm[stage]);

			board=new Board(originx,originy,pr[stage],-105,10,30);

		}

		@Override
		protected void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponent(g);

			g.drawImage(planet3d.getImage(),0,0,null);

			for(int i=0;i<brick.size();++i){
				Brick b=brick.get(i);
				g.setColor(new Color(b.r,b.g,b.b));
				g.fillPolygon(b.x, b.y, b.t);
			}

			for(int i=0;i<ball.size();++i){
				Ball b=ball.get(i);
				g.setColor(new Color(b.r,b.g,b.b));
				g.fillOval(b.x-b.rad, b.y-b.rad, b.rad*2, b.rad*2);
			}

			g.setColor(new Color(board.r,board.g,board.b));
			g.fillPolygon(board.x,board.y,board.t);

		}

		void collipsionchk(){
			for(int i=0;i<ball.size();++i){
				Ball a=ball.get(i);
				if(a.runchk==false)continue;
				if(a.dis<planet.R){
					ball.remove(i);
					continue;
				}
				Brick broken = null;
				boolean chk=false;
				for(int j=0;j<brick.size();++j){
					Brick b=brick.get(j);

					if((Dchk(a.dis,b.dis,a.dis+a.rad)&&Achk(b.theta,a.theta,b.theta+b.width))
							||(Dchk(a.dis-a.rad,b.dis+b.height,a.dis)&&Achk(b.theta,a.theta,b.theta+b.width)))
					{
						broken=b;
						chk=true;
						a.v=-a.v;
						break;
					}

					if((Dchk(b.dis,a.dis,b.dis+b.height)&&Achk(a.theta,b.theta,a.theta+a.rad/a.dis/Math.PI*180))
							||(Dchk(b.dis,a.dis,b.dis+b.height)&&Achk(a.theta-+a.rad/a.dis/Math.PI*180,b.theta+b.width,a.theta)))
					{
						broken=b;
						chk=true;
						a.w=-a.w;
						break;
					}

					if(Dis(a.dis,a.theta,b.dis,b.theta)<a.rad
							||Dis(a.dis,a.theta,b.dis+b.height,b.theta)<a.rad
							||Dis(a.dis,a.theta,b.dis,b.theta+b.width)<a.rad
							||Dis(a.dis,a.theta,b.dis+b.height,b.theta+b.width)<a.rad)
					{
						broken=b;
						chk=true;
						a.v=-a.v;
						a.w=-a.w;
						break;
					}

				}

				if(chk){
					score+=50;
					broken.collision();
					if(broken.life==0){
						
						if(Math.random()<0.1){
							Ball newball=new Ball(originx,originy,broken.dis,(broken.theta+broken.width/2));
							newball.runchk=true;
							newball.w=a.w;
							newball.v=a.v;
							ball.add(newball);
						}
						else if(Math.random()<0.1){
							board.itemeffect("extension");
						}
						else if(Math.random()<0.1){
							board.itemeffect("sticky");
						}

						
						brick.remove(broken);
						score+=100;
					}
					String scorestring=new String(String.format("    %010d",score));
					scoretext.setText(scorestring);

					
				}

				if(Dchk(a.dis-a.rad,board.dis+board.height,a.dis)
						&&Achk(Anormalize(board.theta),Anormalize(a.theta),Anormalize(board.theta+board.width)))
				{
					if(board.chk>0){
						a.update((int) board.w);
						a.v=0;
						a.dis=board.dis+board.height+a.rad+1;
						a.runchk=false;
					}
					else{
						a.w+=board.w/10;
						a.v=-a.v;
					}
				}

			}
			if(ball.size()==0){
				--life;
				if(life>=0){
					Ball a=new Ball(originx, originy, board.dis+board.height+7, board.theta+board.width/2);
					a.update((int) board.w);
					ball.add(a);
					repaint();
				}
				lifepanel.repaint();
			}
		}

	}

	void LOSE(){
		//t1.stop();
		//t2.stop();
		MyDialog lose=new MyDialog(this,false);
		File f=new File("Planet/planet"+stage+".jpg");
		f.delete();
		dispose();
	}

	void WIN(){
		//t1.stop();
		//t2.stop();
		MyDialog win=new MyDialog(this,true);
		File f=new File("Planet/planet"+stage+".jpg");
		f.delete();
		dispose();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode=e.getKeyCode();

		if(keyCode==KeyEvent.VK_RIGHT){
			gamepanel.board.update(2);
			for(int i=0;i<gamepanel.ball.size();++i){
				Ball b=gamepanel.ball.get(i);
				if(b.runchk==false)b.update(2);
			}
		}

		if(keyCode==KeyEvent.VK_LEFT){
			gamepanel.board.update(-2);
			for(int i=0;i<gamepanel.ball.size();++i){
				Ball b=gamepanel.ball.get(i);
				if(b.runchk==false)b.update(-2);
			}
		}

		if(keyCode==KeyEvent.VK_SPACE){
			for(int i=0;i<gamepanel.ball.size();++i){
				Ball b=gamepanel.ball.get(i);
				if(b.runchk==false){
					b.shoot(gamepanel.board.w/10,5.0);
					b.runchk=true;
				}
			}
		}

	}


	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}


	class InsteadofTimer1 extends Thread{

		InsteadofTimer1(){

		}

		public void run(){
			boolean reach;
			while(true){
				reach=false;
				for(int i=gamepanel.brick.size()-1;i>=0;--i){
					Brick b=gamepanel.brick.get(i);
					if(b.dis<=gamepanel.planet.R){
						gamepanel.brick.remove(b);
						reach=true;
					}
					else b.update(10);
				}
				if(reach)break;
				
				gamepanel.repaint();
				
				try {
					Thread.sleep(20000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(reach)LOSE();
		}
	}

	class InsteadofTimer2 extends Thread{

		InsteadofTimer2(){

		}

		public void run(){
			while(true){
				gamepanel.collipsionchk();
				if(life<0)break;
				double friction=gamepanel.planet.gravity(gamepanel.board.dis)*gamepanel.planet.m;
				gamepanel.board.run(friction);
				for(int i=0;i<gamepanel.ball.size();++i){
					Ball b=gamepanel.ball.get(i);
					if(b.runchk==true){
						double gravityforce=gamepanel.planet.gravity(b.dis)*0.15;
						b.run(0,gravityforce);
					}
					else{
						b.run(friction,0);
					}
				}
				if(gamepanel.brick.size()==0)break;
				
				gamepanel.repaint();
				
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(life<0)LOSE();
			if(gamepanel.brick.size()==0)WIN();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		/*
		if(t2==e.getSource()){
			boolean reach=false;
			for(int i=gamepanel.brick.size()-1;i>=0;--i){
				Brick b=gamepanel.brick.get(i);
				if(b.dis<=gamepanel.planet.R){
					gamepanel.brick.remove(b);
					reach=true;
				}
				else b.update(10);
			}
			if(reach)LOSE();
		}

		if(t1==e.getSource()){
			gamepanel.collipsionchk();
			double friction=gamepanel.planet.gravity(gamepanel.board.dis)*gamepanel.planet.m;
			gamepanel.board.run(friction);
			for(int i=0;i<gamepanel.ball.size();++i){
				Ball b=gamepanel.ball.get(i);
				if(b.runchk==true){
					double gravityforce=gamepanel.planet.gravity(b.dis)*0.15;
					b.run(0,gravityforce);
				}
				else{
					b.theta=gamepanel.board.theta+gamepanel.board.width/2;
					b.run(0,0);
				}
			}
			if(gamepanel.brick.size()==0){
				WIN();
			}
		}
		
		gamepanel.repaint();
		*/
		
	}

}