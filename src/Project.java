import javax.swing.JFrame;

class ThreadforImage extends Thread{
	
	Planet_3D p3d;
	int cnt=0;
	ThreadforImage(){
		super();
		//p3d=new Planet_3D("earth");
	}
	
	public void run(){
		while(true){
			p3d.onSaveImage();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}

public class Project {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*JFrame frame = new JFrame();
		ThreadforImage thread=new ThreadforImage();
		thread.start();
		frame.getContentPane().add(thread.p3d);
		frame.setSize( 550, 550 );
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible( true );*/
		
		Main main=new Main();
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
