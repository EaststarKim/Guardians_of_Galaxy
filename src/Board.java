
public class Board {

	double dis,theta,m;
	double w;
	int originx,originy;
	int height,width;
	int[] x,y;
	int t;
	int r,g,b;
	
	int chk;
	
	Board(int originx,int originy,double dis,double theta,int height,int width){
		this.dis=dis;
		this.theta=theta;
		this.originx=originx;
		this.originy=originy;
		this.height=height;
		this.width=width;
		w=0;
		x=new int[100];
		y=new int[100];
		t=0;
		for(int i=0;i<=width;i+=2){
			x[t]=originx+(int) (dis*Math.cos((theta+i)/180*Math.PI));
			y[t++]=originy+(int) (dis*Math.sin((theta+i)/180*Math.PI));
		}
		for(int i=2;i<=height;i+=2){
			x[t]=originx+(int) ((dis+i)*Math.cos((theta+width)/180*Math.PI));
			y[t++]=originy+(int) ((dis+i)*Math.sin((theta+width)/180*Math.PI));
		}
		for(int i=width-2;i>=0;i-=2){
			x[t]=originx+(int) ((dis+height)*Math.cos((theta+i)/180*Math.PI));
			y[t++]=originy+(int) ((dis+height)*Math.sin((theta+i)/180*Math.PI));
		}
		for(int i=height-2;i>=2;i-=2){
			x[t]=originx+(int) ((dis+i)*Math.cos((theta)/180*Math.PI));
			y[t++]=originy+(int) ((dis+i)*Math.sin((theta)/180*Math.PI));
		}
		r=0;g=b=255;
		//r=(int) (this.dis);
		//g=(int) (this.dis);
		//b=(int) (this.dis);
		
		chk=0;
		
	}
	
	void update(int move){
		w+=move;
		if(w<-10)w=-10;
		if(w>10)w=10;
	}
	
	void run(double f){
		theta+=w;
		if(Math.abs(w)<f)w=0;
		else if(w>0)w-=f;
		else w+=f;
		t=0;
		for(int i=0;i<=width;i+=2){
			x[t]=originx+(int) (dis*Math.cos((theta+i)/180*Math.PI));
			y[t++]=originy+(int) (dis*Math.sin((theta+i)/180*Math.PI));
		}
		for(int i=2;i<=height;i+=2){
			x[t]=originx+(int) ((dis+i)*Math.cos((theta+width)/180*Math.PI));
			y[t++]=originy+(int) ((dis+i)*Math.sin((theta+width)/180*Math.PI));
		}
		for(int i=width-2;i>=0;i-=2){
			x[t]=originx+(int) ((dis+height)*Math.cos((theta+i)/180*Math.PI));
			y[t++]=originy+(int) ((dis+height)*Math.sin((theta+i)/180*Math.PI));
		}
		for(int i=height-2;i>=2;i-=2){
			x[t]=originx+(int) ((dis+i)*Math.cos((theta)/180*Math.PI));
			y[t++]=originy+(int) ((dis+i)*Math.sin((theta)/180*Math.PI));
		}
		if(chk>0){r=0;g=255;b=100;}
		else{r=0;g=b=255;}
	}
	
	void itemeffect(String item){
		if(item=="extension"){
			if(width>50)return;
		}
		
		ItemThread thread=new ItemThread(item);
		thread.start();
	}
	
	class ItemThread extends Thread{
		
		String item;
		
		ItemThread(String item){
			this.item=item;
		}
		
		public void run(){
			if(item=="extension")width+=10;
			if(item=="sticky")++chk;
			try {
				sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(item=="extension")width-=10;
			if(item=="sticky")--chk;
		}
	}
	
}
