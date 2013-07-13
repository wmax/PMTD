package pmtd.entities.creeps;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pmtd.entities.VisibleEntity;

public abstract class Creep extends VisibleEntity {
	
	protected int health = 1000;
	protected int bounty = 5;

	public Creep(Image theImage, int x, int y, boolean centered) throws SlickException {
		super(theImage, x, y, centered);
	}
	
	public int getHit(int damage) {
		health -= damage;
		return health;
	}
	
	public void update(GameContainer gc, int timeDelta) {
		selectTarget();
		move();
	}
	
	@Override
	public void render(Graphics pen) {
		image.setRotation((float) direction);
		super.render(pen);
	}

	protected abstract void selectTarget();
	
	public int getBounty() { return bounty; }
	public void setBounty(int bounty) { this.bounty = bounty; }
	public int getHealth() { return health; }
}