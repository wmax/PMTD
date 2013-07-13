package pmtd.entities.bullets;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import pmtd.entities.VisibleEntity;
import pmtd.entities.creeps.Creep;

public class SimpleBullet extends VisibleEntity {

	Creep target;
	protected int damage;
	protected boolean targetHit;
	
	public SimpleBullet(int x, int y, Creep target, int damage)
			throws SlickException {
		super(cache.getSprite("Bullet.png"), x, y, true);
		this.target = target;
		this.damage = damage;
		targetHit = false;
	}

	@Override
	public void update(GameContainer gc, int timeDelta) {
		Vector2f pos = new Vector2f( rect.getCenter() );
		moveTarget = new Vector2f( target.getRect().getCenter() );

		if( pos.distance(moveTarget) < 2) {
			target.getHit(damage);
			targetHit = true;
		} else {
			path.clear();
			calcPath();
			move();	
			move();
		}	
	}
	
	public boolean getTargetHit() {
		return targetHit;
	}
}
