package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JScrollPane;

//import main.PanAndZoom.PanningHandler;

public class JPanelMapaZoom2 extends JPanelMapa
{
	private static int prevN = 0;
	private Dimension preferredSize = new Dimension(400,400);    
	private Rectangle2D[] rects = new Rectangle2D[50];
	
	
	Point2D XFormedPoint;
	AffineTransform at;
	private JPanelMapaZoom2 yomismo;
	
	public JPanelMapaZoom2()
	{
		super();
	/*	for (int i=0; i<rects.length; i++) {
	        rects[i] = new Rectangle2D.Double(
	                Math.random()*.8, Math.random()*.8, 
	                Math.random()*.2, Math.random()*.2);
	    }*/
		
        addMouseWheelListener(new MouseAdapter() 
        {
        	@Override
	        public void mouseWheelMoved(MouseWheelEvent e) {
	            updatePreferredSize(e.getWheelRotation(), e.getPoint());
	        }

         });
        
        PanningHandler panner = new PanningHandler();
        addMouseListener(panner);
        addMouseMotionListener(panner);
        init();
	}
        
	public void init()
	{
		yomismo=this;

	}
	private void updatePreferredSize(int n, Point p) {

	    if(n == 0)              // ideally getWheelRotation() should never return 0. 
	        n = -1 * prevN;     // but sometimes it returns 0 during changing of zoom 
	                            // direction. so if we get 0 just reverse the direction.

	    double d = (double) n * 1.08;
	    d = (n > 0) ? 1 / d : -d;

	    int w = (int) (getWidth() * d);
	    int h = (int) (getHeight() * d);
	    preferredSize.setSize(w, h);

	    int offX = (int)(p.x * d) - p.x;
	    int offY = (int)(p.y * d) - p.y;
	    setLocation(this.getLocation().x-offX,this.getLocation().y-offY); 
	    //in the original code, zoomPanel is being shifted. here we are shifting containerPanel
	  //  System.out.println(p.x+" "+p.y+" "+preferredSize.height+" "+preferredSize.width);
	    getParent().doLayout();             // do the layout for containerPanel
	    getParent().getParent().doLayout(); // do the layout for jf (JFrame)

	    prevN = n;
	}

	@Override
	public Dimension getPreferredSize() {
	    return preferredSize;
	}

	private Rectangle2D r = new Rectangle2D.Float();
	public void paint(Graphics g) 
	{
	    super.paint(g);
	    Graphics2D ourGraphics = (Graphics2D) g;
	    AffineTransform saveTransform = ourGraphics.getTransform();
	    
	    at = new AffineTransform(saveTransform);
	    
	    		//.getVerticalScrollBar().setValue(a.getVerticalScrollBar().getValue()+(int)at.getTranslateY());
	//    g.setColor(Color.red);
	 //   int w = getWidth();
	  //  int h = getHeight();
	   /* for (Rectangle2D rect : rects) 
	    {
	        r.setRect(rect.getX() * w, rect.getY() * h, 
	                rect.getWidth() * w, rect.getHeight() * h);
	        ((Graphics2D)g).draw(r);
	    }*/       
	}

	private	class PanningHandler implements MouseListener, MouseMotionListener 
	{
		double referenceX;
		double referenceY;
		double translateX;
		double translateY;
//	saves the initial transform at the beginning of the pan interaction
		AffineTransform initialTransform;

//	capture the starting point 
		public void mousePressed(MouseEvent e) 
		{

// first transform the mouse point to the pan and zoom
// coordinates
			/*try 
			{
				XFormedPoint = at.inverseTransform(e.getPoint(), null);
			}
			catch (NoninvertibleTransformException te) 
			{
				System.out.println(te);
			}*/

	// save the transformed starting point and the initial
	// transform
			referenceX = e.getX();//XFormedPoint.getX();
			referenceY =e.getY();// XFormedPoint.getY();
		//	System.out.println("rX "+referenceX+"  rY "+referenceY);
			//initialTransform = at;
		}

		public void mouseDragged(MouseEvent e) 
		{
		
			// first transform the mouse point to the pan and zoom
			// coordinates. We must take care to transform by the
			// initial tranform, not the updated transform, so that
			// both the initial reference point and all subsequent
			// reference points are measured against the same origin.
			/*try 
			{
				XFormedPoint = initialTransform.inverseTransform(e.getPoint(), null);
			}
			catch (NoninvertibleTransformException te) 
			{
				System.out.println(te);
			}*/

// the size of the pan translations 
// are defined by the current mouse location subtracted
// from the reference location
			double deltaX =-1*( e.getX() - referenceX);
			double deltaY =-1*(e.getY() - referenceY);
			
			// make the reference point be the new mouse point. 
	//		referenceX = XFormedPoint.getX();
	//		referenceY = XFormedPoint.getY();
			System.out.println(e.getX()+" "+e.getY());
		//	translateX += deltaX;
		//	translateY += deltaY;
			
			// schedule a repaint.
			deltaX=deltaX/2;
			deltaY=deltaY/2;
		/*	if(Math.abs(deltaX)<Math.abs(deltaY))deltaX=0;
			else if(Math.abs(deltaY)<Math.abs(deltaX))deltaY=0;
			else deltaY=0;*/
	//		System.out.println("deltaX"+deltaX+"   deltaY "+deltaY);
			
			JScrollPane a=((JScrollPane)(yomismo.getParent().getParent()));
		//	System.out.println(a.getVerticalScrollBar().getValue()+" "+(int)translateY);
		    int y=a.getVerticalScrollBar().getValue()+(int)deltaY;
		    int x=a.getHorizontalScrollBar().getValue()+(int)deltaX;
		 //   System.out.println(y);
	if(Math.abs(deltaY)>2){
		a.getVerticalScrollBar().setValue(y);
		//referenceX=e.getX();
		referenceY=e.getY();
			
	}
	if(Math.abs(deltaX)>2){
		a.getHorizontalScrollBar().setValue(x);
		referenceX=e.getX();
		//referenceY=e.getY();
			
	}
	    	//repaint();
		}

		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseMoved(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}

}
