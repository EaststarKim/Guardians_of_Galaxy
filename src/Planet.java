
public class Planet {

	int R;
	double G=5,M,m=0.5;
	int originx,originy;
	int r,g,b;
	
	Planet(int originx,int originy,int R,double M){
		this.originx=originx;
		this.originy=originy;
		this.R=R;
		this.M=M;
		this.r=0;
		this.g=255;
		this.b=0;
	}
	
	double gravity(double r){
		return G*M/r/r;
	}
	
}
