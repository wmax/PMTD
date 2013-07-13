package pmtd.entities;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import pmtd.util.SpriteCache;

/**
 * Represents all visible objects.
 * Objects like towers and creeps should extend this class.
 */
public abstract class VisibleEntity {
	protected static SpriteCache cache = SpriteCache.instanceOf();
	protected ArrayList<Vector2f> path = new ArrayList<Vector2f>();
	protected Vector2f moveTarget;

	protected Image image;
	protected Rectangle rect;
	protected double direction = 0;

	/**
	 * Spawns an entity on a certain position with a certain look.
	 *
	 */
	public VisibleEntity(Image theImage, int x, int y, boolean centered ) throws SlickException {
		image = theImage;
		if( centered ) {
			x -= image.getWidth() / 2;
			y -= image.getHeight() / 2;
		}
		
		rect = new Rectangle(x, y, image.getWidth(), image.getHeight());
	}
		
	public Rectangle getRect() {
		return rect;
	}
	
	protected void calcDirection(Rectangle a, Rectangle b) {
		Vector2f vn = new Vector2f(a.getCenterX(), a.getCenterY());
		vn.sub(new Vector2f(b.getCenterX(), b.getCenterY()));
		vn.normalise().negateLocal();
		direction = (float) (vn.getTheta() + 90);
	}

	protected void calcPath() {
		Vector2f start = new Vector2f(rect.getCenterX(), rect.getCenterY());
		Vector2f vn = new Vector2f(start);
		vn.sub(moveTarget).normalise().negateLocal();
		
		while( start.distance(moveTarget) > 1)
			path.add( new Vector2f(start.add(vn)) );
		
		direction = vn.getTheta() + 90;
	}
	
	protected void move() {		
		if( path == null || path.isEmpty() )
			return;
		
		rect.setCenterX(path.get(0).x);
		rect.setCenterY(path.get(0).y);
		path.remove(0);
	}
	
	public void render(Graphics pen) {
		pen.setColor(Color.white);
		image.draw(rect.getX(), rect.getY());
	}
	
	/**
	 * Subclasses should implement the entity's logic here.
	 * This method should be called in the game loop for every entity then. 
	 */
	public abstract void update(GameContainer gc, int timeDelta) throws SlickException;
}
