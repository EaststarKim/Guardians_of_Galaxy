
public class Ball {

	int rad;
	double m;
	int originx,originy,x,y;
	double dis,theta;
	double w,v;
	int r,g,b;
	boolean runchk;
	
	Ball(int originx,int originy,double dis,double theta){
		this.originx=originx;
		this.originy=originy;
		this.dis=dis;
		this.theta=theta;
		w=v=0;
		x=originx+(int)(dis*Math.cos(theta/180*Math.PI));
		y=originy+(int)(dis*Math.sin(theta/180*Math.PI));
		rad=6;
		r=g=b=255;
		runchk=false;
	}
	
	void shoot(double d,double e){
		this.w=d;
		this.v=e;
	}
	
	void update(int move){
		w+=move;
		if(w<-10)w=-10;
		if(w>10)w=10;
	}
	
	void run(double f,double g){
		theta+=w;
		dis+=v;
		if(Math.abs(w)<f)w=0;
		else if(w>0)w-=f;
		else w+=f;
		if(runchk){
			
			if(w>1)w=1;
			if(w<-1)w=-1;
		}
		v-=g;
		
		x=originx+(int)(dis*Math.cos(theta/180*Math.PI));
		y=originy+(int)(dis*Math.sin(theta/180*Math.PI));
		
	}
}
