//need Java 3D

//draw textured sphere - createSphere,basicSphere()
//source : http://stackoverflow.com/questions/22517973/light-and-textures-in-java3d

//save Canvas3D as image(jpg file) - OnSaveImage(partially edited)
//and other java 3d setting
//source : Java 3D Programming (p.292-306) - From SwingTest.Java



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsConfigTemplate;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageOutputStream;
import javax.media.j3d.Alpha;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.GraphicsConfigTemplate3D;
import javax.media.j3d.Group;
import javax.media.j3d.ImageComponent;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Locale;
import javax.media.j3d.Material;
import javax.media.j3d.PhysicalBody;
import javax.media.j3d.PhysicalEnvironment;
import javax.media.j3d.PointLight;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.media.j3d.ViewPlatform;
import javax.media.j3d.VirtualUniverse;
import javax.swing.JPanel;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;

public class Planet_3D extends JPanel {

	private BranchGroup    sceneBranchGroup = null;

	private RotationInterpolator  rotator = null;

	private static Canvas3D     offScreenCanvas3D = null;

	private static ImageComponent2D   imageComponent = null;

	private static final int  offScreenWidth = 1500;
	private static final int  offScreenHeight = 1000;
	
	int stage;
	String planetname;

	public Planet_3D(int stage,String planetname)
	{
		this.stage=stage;
		setLayout( new BorderLayout() );
		this.planetname=planetname;
		init();
	}

	protected void init()
	{
		VirtualUniverse universe = createVirtualUniverse();

		Locale locale = createLocale( universe );

		BranchGroup sceneBranchGroup = createSceneBranchGroup();

		Background background = createBackground();

		if( background != null )
			sceneBranchGroup.addChild( background );

		ViewPlatform vp = createViewPlatform();

		BranchGroup viewBranchGroup =
				createViewBranchGroup( getViewTransformGroupArray(), vp );

		locale.addBranchGraph( sceneBranchGroup );

		addViewBranchGroup( locale, viewBranchGroup );

		createView( vp );
	}

	protected void addCanvas3D( Canvas3D c3d )
	{
		add( "Center", c3d );
	}

	protected View createView( ViewPlatform vp )
	{
		View view = new View();

		PhysicalBody pb = createPhysicalBody();
		PhysicalEnvironment pe = createPhysicalEnvironment();
		view.setPhysicalEnvironment( pe );
		view.setPhysicalBody( pb );

		if( vp != null )
			view.attachViewPlatform( vp );

		view.setBackClipDistance( getBackClipDistance() );
		view.setFrontClipDistance( getFrontClipDistance() );

		Canvas3D c3d = createCanvas3D( false );

		view.addCanvas3D( c3d );

		view.addCanvas3D( createOffscreenCanvas3D() );

		addCanvas3D( c3d );

		return view;
	}


	protected Background createBackground()
	{

		Background back = new Background(new Color3f( 0.0f, 0.0f, 0.0f ) );

		back.setApplicationBounds( createApplicationBounds() );
		return back;
	}


	protected Bounds createApplicationBounds()
	{
		return new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
	}


	protected Canvas3D createCanvas3D( boolean offscreen )
	{

		GraphicsConfigTemplate3D gc3D = new GraphicsConfigTemplate3D();
		gc3D.setSceneAntialiasing( GraphicsConfigTemplate.PREFERRED );

		GraphicsDevice gd[] = GraphicsEnvironment.
				getLocalGraphicsEnvironment().
				getScreenDevices();

		Canvas3D c3d = new Canvas3D( gd[0].getBestConfiguration( gc3D ), offscreen );
		c3d.setSize( 1500, 1000 );

		return c3d;
	}


	protected double getScale()
	{
		return 3;
	}

	public TransformGroup[] getViewTransformGroupArray()
	{
		TransformGroup[] tgArray = new TransformGroup[1];
		tgArray[0] = new TransformGroup();

		Transform3D t3d = new Transform3D();
		t3d.setScale( getScale() );
		
		double[] scale={0,70.0,90.0,80.0,55.0,60.0,65.0,65.0,95.0,70.0,85.0,50.0};
		
		t3d.setTranslation( new Vector3d( 0.0, 0.0, -scale[stage] ) );
		t3d.invert();
		tgArray[0].setTransform( t3d );

		return tgArray;
	}

	protected void addViewBranchGroup( Locale locale, BranchGroup bg )
	{
		locale.addBranchGraph( bg );
	}

	protected Locale createLocale( VirtualUniverse u )
	{
		return new Locale( u );
	}

	protected PhysicalBody createPhysicalBody()
	{
		return new PhysicalBody();
	}

	protected PhysicalEnvironment createPhysicalEnvironment()
	{
		return new PhysicalEnvironment();
	}

	protected float getViewPlatformActivationRadius()
	{
		return 100;
	}

	protected ViewPlatform createViewPlatform()
	{
		ViewPlatform vp = new ViewPlatform();
		vp.setViewAttachPolicy( View.RELATIVE_TO_FIELD_OF_VIEW );
		vp.setActivationRadius( getViewPlatformActivationRadius() );

		return vp;
	}

	protected double getBackClipDistance()
	{
		return 100.0;
	}

	protected double getFrontClipDistance()
	{
		return 1.0;
	}

	protected BranchGroup createViewBranchGroup(
			TransformGroup[] tgArray, ViewPlatform vp )
	{
		BranchGroup vpBranchGroup = new BranchGroup();

		if( tgArray != null && tgArray.length > 0 )
		{
			Group parentGroup = vpBranchGroup;
			TransformGroup curTg = null;

			for( int n = 0; n < tgArray.length; n++ )
			{
				curTg = tgArray[n];
				parentGroup.addChild( curTg );
				parentGroup = curTg;
			}

			tgArray[tgArray.length-1].addChild( vp );
		}
		else
			vpBranchGroup.addChild( vp );

		return vpBranchGroup;
	}

	protected VirtualUniverse createVirtualUniverse()
	{
		return new VirtualUniverse();
	}

	protected Canvas3D createOffscreenCanvas3D()
	{

		offScreenCanvas3D = createCanvas3D( true );

		offScreenCanvas3D.getScreen3D().setSize( offScreenWidth,
				offScreenHeight );

		offScreenCanvas3D.getScreen3D().
		setPhysicalScreenHeight( 0.0254/90 * offScreenHeight );
		offScreenCanvas3D.getScreen3D().
		setPhysicalScreenWidth( 0.0254/90 * offScreenWidth );

		RenderedImage renderedImage =
				new BufferedImage( offScreenWidth, offScreenHeight,
						BufferedImage.TYPE_3BYTE_BGR );

		imageComponent =
				new ImageComponent2D( ImageComponent.FORMAT_RGB8,
						renderedImage );

		imageComponent.setCapability( ImageComponent2D.ALLOW_IMAGE_READ );

		offScreenCanvas3D.setOffScreenBuffer( imageComponent );

		return offScreenCanvas3D;
	}


	protected BranchGroup createSceneBranchGroup()
	{

		BranchGroup objRoot = new BranchGroup();

		TransformGroup objTrans = new TransformGroup();
		objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

		BoundingSphere bounds = new BoundingSphere(
				new Point3d(0.0,0.0,0.0), 100.0);

		Transform3D yAxis = new Transform3D();
		
		Alpha rotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE,
				0, 0,
				4000, 0, 0,
				0, 0, 0);

		rotator = new RotationInterpolator( rotationAlpha,
				objTrans, yAxis, 0.0f,
				(float) Math.PI*2.0f );
		
		rotator.setSchedulingBounds( bounds );

		objTrans.addChild(rotator);

		sceneBranchGroup = new BranchGroup();

		sceneBranchGroup.setCapability( Group.ALLOW_CHILDREN_EXTEND );
		sceneBranchGroup.setCapability( Group.ALLOW_CHILDREN_READ );
		sceneBranchGroup.setCapability( Group.ALLOW_CHILDREN_WRITE );

		sceneBranchGroup.addChild( createSphere() );

		Color3f lColor1 = new Color3f( 0.7f,0.7f,0.7f );
		Vector3f lDir1  = new Vector3f( -1.0f,-1.0f,-1.0f );
		Color3f alColor = new Color3f( 0.2f,0.2f,0.2f );

		AmbientLight aLgt = new AmbientLight( alColor );
		aLgt.setInfluencingBounds( bounds );

		DirectionalLight lgt1 = new DirectionalLight( lColor1, lDir1 );
		lgt1.setInfluencingBounds( bounds );

		objRoot.addChild(aLgt);
		objRoot.addChild(lgt1);

		objTrans.addChild( sceneBranchGroup );
		objRoot.addChild( objTrans );

		return objRoot;
	}


	protected BranchGroup createSphere()
	{
		BranchGroup group = new BranchGroup();

		group.addChild(basicSphere(0,0,0));


		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 10000000.0);

		PointLight light = new PointLight();
		light.setColor(new Color3f(Color.WHITE));
		light.setPosition(0.0f,0.0f,0.0f);
		light.setInfluencingBounds(bounds);
		group.addChild(light);

		return group;
	}

	private TransformGroup basicSphere(double x, double y, double z) {
		try {
			int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;

			Texture tex = new TextureLoader(
					ImageIO.read(new FileInputStream("Planet/"+planetname+"map.jpg"))
					).getTexture();
			tex.setBoundaryModeS(Texture.WRAP);
			tex.setBoundaryModeT(Texture.WRAP);

			TextureAttributes texAttr = new TextureAttributes();

			Appearance ap = new Appearance();
			ap.setTexture(tex);
			ap.setTextureAttributes(texAttr);

			Material material = new Material();
			material.setSpecularColor(new Color3f(Color.WHITE));
			material.setDiffuseColor(new Color3f(Color.WHITE));
			ap.setMaterial(material);

			Sphere sphere = new Sphere(2.0f, primflags, 100, ap);

			Transform3D transform = new Transform3D();
			transform.setTranslation(new Vector3d(0.0f,0.0f,0.0f));

			TransformGroup transformGroup = new TransformGroup();
			transformGroup.setTransform(transform);

			transformGroup.addChild(sphere);

			return transformGroup;
		} catch(Exception e) { e.printStackTrace(); }
		return null;
	}

	void onSaveImage()
	{
		offScreenCanvas3D.renderOffScreenBuffer();
		offScreenCanvas3D.waitForOffScreenRendering();
		
		try
		{
			FileOutputStream fileOut = new FileOutputStream( "Planet/planet"+stage+".jpg" );
			
			BufferedImage challenge=imageComponent.getImage();

			ImageWriter imageWriter = (ImageWriter)ImageIO.getImageWritersBySuffix("jpeg").next();
			ImageOutputStream ios = ImageIO.createImageOutputStream(fileOut);
			imageWriter.setOutput(ios);
			IIOMetadata imageMetaData = imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(challenge), null);
			imageWriter.write(imageMetaData, new IIOImage(challenge, null, null), null);

		}
		catch( Exception e )
		{
		}

	}

}
