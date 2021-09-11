
public class Brick {
	
	double dis,theta;
	int originx,originy;
	int height,width;
	int[] x,y;
	int t;
	int r,g,b;
	int life;
	
	Brick(int originx,int originy,double dis,double theta,int height,int width,int life){
		this.dis=dis;
		this.theta=theta;
		this.originx=originx;
		this.originy=originy;
		this.height=height;
		this.width=width;
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
		
		r=10*(25-life*4);
		//g=(int) ((Math.random())*this.dis);
		//b=(int) ((Math.random())*this.dis);
		this.life=life;
	}
	
	void update(int fall){
		dis-=fall;
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
		
	}
	
	void collision(){
		--life;
		r=10*(26-life*4);
	}
	
}
