//need Java 3D

//Text 3D
//source : http://www.java3d.org/samples.html

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Font3D;
import javax.media.j3d.FontExtrusion;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Text3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.universe.SimpleUniverse;

class MainPanel extends JPanel{

	public void setUp() {
		
		setLayout(new GridLayout(1, 1, 2, 2));

		GraphicsConfiguration config = SimpleUniverse
				.getPreferredConfiguration();
		Canvas3D canvas3D = new Canvas3D(config);
		canvas3D.setSize(800, 500);
		SimpleUniverse universe = new SimpleUniverse(canvas3D);
		BranchGroup group = new BranchGroup();
		addObjects(group);
		addLights(group);
		universe.getViewingPlatform().setNominalViewingTransform();
		universe.addBranchGraph(group);
		add(canvas3D);
		
	}

	public void addLights(BranchGroup group) {
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
				1000.0);

		Color3f light1Color = new Color3f(1.0f, 1.0f, 1.0f);
		Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
		DirectionalLight light1 = new DirectionalLight(light1Color,
				light1Direction);
		light1.setInfluencingBounds(bounds);
		group.addChild(light1);

		Color3f ambientColor = new Color3f(.1f, .1f, .1f);
		AmbientLight ambientLightNode = new AmbientLight(ambientColor);
		ambientLightNode.setInfluencingBounds(bounds);
		group.addChild(ambientLightNode);
	}

	private void addObjects(BranchGroup group) {
		Font3D f3d = new Font3D(new Font("TestFont", Font.PLAIN, 2),
				new FontExtrusion());
		Text3D text = new Text3D(f3d, new String("Guardians of Galaxy"), new Point3f(-4.5f, -1.0f, -4.5f));
		 
		text.setString("Guardians of Galaxy");
		Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
		Color3f color = new Color3f(0.2f, 0.2f, 0.6f);
		Appearance a = new Appearance();
		Material m = new Material(color, color, color, white, 80.0f);
		m.setLightingEnable(true);
		a.setMaterial(m);

		Shape3D sh = new Shape3D();
		sh.setGeometry(text);
		sh.setAppearance(a);
		TransformGroup tg = new TransformGroup();
		Transform3D t3d = new Transform3D();
		Transform3D tDown = new Transform3D();
		Transform3D rot = new Transform3D();
		Vector3f v3f = new Vector3f(-4.0f, -2.0f, -15.0f);
		t3d.setTranslation(v3f);
		rot.rotX(Math.PI / 5);
		t3d.mul(rot);
		v3f = new Vector3f(-0.7f, -1.0f, -1.0f);
		tDown.setTranslation(v3f);
		t3d.mul(tDown);
		tg.setTransform(t3d);
		tg.addChild(sh);
		group.addChild(tg);
		
	}
}

public class Main extends JFrame implements ActionListener{
	
	MainPanel titlepanel;
	
	JPanel panel;
	JButton start;
	JLabel label;
	
	Main(){
		JFrame main=new JFrame("Guardians of Galaxy");
		
		titlepanel=new MainPanel();
		titlepanel.setUp();
		main.add(titlepanel,BorderLayout.NORTH);
		
		panel=new JPanel();
		panel.setBackground(Color.BLACK);
		
		start=new JButton();
		start.addActionListener(this);
		
		label=new JLabel("  START  ");
		label.setFont(new Font("Times New Roman",Font.BOLD,50));
		
		start.add(label);
		
		panel.add(start,BorderLayout.CENTER);
		
		main.add(panel,BorderLayout.CENTER);
		
		main.setSize(1700, 1000);
		main.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==start){
			dispose();
			BrickBreaker game=new BrickBreaker(1,0,5);
			game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	}
	
}