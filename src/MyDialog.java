import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class MyDialog extends JDialog implements ActionListener{

	JLabel message;

	JButton tryagain,playagain,next;
	
	boolean info;
	
	int stage,score,life;
	
	MyDialog(BrickBreaker frame,boolean outcome){

		super(frame,"Info",true);
		
		this.stage=frame.stage;
		this.score=frame.score;
		this.life=frame.life;

		setSize(400,200);
		setLocation(700,400);

		setLayout(new BorderLayout(20,20));

		info=outcome;
		

		if(outcome==false){

			message=new JLabel("실패!",JLabel.CENTER);
			add(message,BorderLayout.NORTH);
			
			message=new JLabel("점수 :"+score,JLabel.CENTER);
			add(message,BorderLayout.CENTER);

			tryagain=new JButton("재도전");
			tryagain.addActionListener(this);

			add(tryagain,BorderLayout.SOUTH);

		}
		else{

			message=new JLabel("성공!",JLabel.CENTER);
			add(message,BorderLayout.NORTH);

			playagain=new JButton("한번 더");
			playagain.addActionListener(this);

			next=new JButton("다음 스테이지로");
			next.addActionListener(this);

			add(playagain,BorderLayout.WEST);
			add(next,BorderLayout.EAST);
			
		}

		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if(e.getSource()==tryagain){
			BrickBreaker newgame=new BrickBreaker(stage,0,5);
			newgame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}

		if(e.getSource()==playagain){
			BrickBreaker newgame=new BrickBreaker(stage,score,life+2);
			newgame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}

		if(e.getSource()==next){
			BrickBreaker newgame=new BrickBreaker(stage+1,score,life+4);
			newgame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		
		dispose();
		
	}

}
