package pmtd.util;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class GameMath {
		
	public static double calcRectDist( Rectangle a, Rectangle b) {
		if(a == null || b == null)
			return 0;
		
		return calcDist(a.getX(), a.getY(), b.getX(), b.getY());
	}
	
	public static double calcDist( float x1, float y1, float x2, float y2 ) {
		float dx = Math.abs( x1 - x2 );
		float dy = Math.abs( y1 - y2 );
		
		return Math.hypot(dx, dy);
	}
	
	public static double calcRectToPointDist(Rectangle r, Vector2f p) {
		if( p == null || r == null)
			return 0;
		
		return calcDist(r.getX(), r.getY(), (float) p.getX(), (float) p.getY());
	}
}
