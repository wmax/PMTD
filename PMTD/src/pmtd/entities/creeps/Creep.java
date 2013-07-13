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
	
	/**
	 * Defines the default behaviour of a creep.
	 * The creep's duty is to select a target and move towards it.
	 */
	public void update(GameContainer gc, int timeDelta) {
		selectTarget();
		move();
	}
	
	@Override
	public void render(Graphics pen) {
		image.setRotation((float) direction);
		super.render(pen);
	}

	/**
	 * Because selecting a target can be very different depending on
	 * the creep's breed, this behaviour should be implemented in the subclass of the creep.
	 */
	protected abstract void selectTarget();
	
	public int getBounty() {
		return bounty;
	}
	
	public void setBounty(int bounty) {
		this.bounty = bounty;
	}
	
	public int getHealth() {
		return health;
	}
}