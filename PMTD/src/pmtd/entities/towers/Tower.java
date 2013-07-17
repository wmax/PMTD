package pmtd.entities.towers;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Circle;

import pmtd.entities.VisibleEntity;
import pmtd.entities.creeps.Creep;
import pmtd.util.GameMath;

public abstract class Tower extends VisibleEntity {
	protected static ArrayList<Creep> creeps;

	protected Image canon;
	protected Sound shotSound;

	protected int maxTargetCount = 1;
	protected int power	= 10;
	protected int shotCooldown = 500;
	protected float range =	95;

	protected ArrayList<Creep> targets = new ArrayList<Creep>();
	protected int lastShotMade = 0;

	/**
	 * Constructs a tower with a base and a top.
	 * The top is the movable part which could point into the target's
	 * direction for example.
	 */
	public Tower(Image base, Image top, Sound shotSound, int x, int y) throws SlickException {
		super(base, x, y, true);
		canon = top;
		this.shotSound = shotSound;
	}

	/**
	 * A tower's duty is to pick some targets and shoot them.
	 */
	public void update(GameContainer gc, int timeDelta) throws SlickException {
		lastShotMade += timeDelta;
		checkTargets();
		aim();
		fire();
	}
	
	/**
	 * Remove targets from the current selection which are too far away.
	 */
	protected void checkTargets() {
		if(targets.size() == 0)
			return;
		
		for(int i = 0; i < targets.size(); i++)
			if( GameMath.calcRectDist(rect, targets.get(i).getRect()) > range )
				targets.remove(targets.get(i));
	}
	
	/**
	 * Select some targets which are in range of the tower.
	 */
	protected void aim() {
		if( targets.size() == 1)
			calcDirection(rect, targets.get(0).getRect());
		
		if( targets.size() >= maxTargetCount)
			return;
		
		for( Creep c : creeps )
			if( targets.contains(c) ) {	
				// just do nothing because this creep is already selected
			} else if( GameMath.calcRectDist(rect, c.getRect()) <= range )
				if( targets.size() < maxTargetCount )
					targets.add(c);
	}

	/**
	 * Fire at all the previously selected targets if the cooldown is ready.
	 */
	protected void fire() throws SlickException {
		if( targets.isEmpty() || lastShotMade <= shotCooldown)
			return;
		
		for( int i = 0; i < targets.size(); i++ ) {
			Creep c = targets.get(i);
			shoot(c);
		}
		lastShotMade = 0;
	}
	
	protected void shoot(Creep c) throws SlickException {
		if( c.getHealth() <= 0)
			targets.remove(c);
		else {
			spawnBullet(c);
			shotSound.play();
		}
	}
	
	public void render(Graphics pen) {
		// let the ancestor draw the base
		super.render(pen);
		
		// draw the canon ( top )
		canon.setRotation((float) direction);
		canon.draw(rect.getX(), rect.getY());
		
		// draw a circle which identifies the shot range
		pen.setColor(Color.gray);
		pen.setLineWidth(1);
		pen.draw( new Circle(rect.getCenterX(), rect.getCenterY(), range) );
	}
	
	protected abstract void spawnBullet(Creep c) throws SlickException;
	
	public static void setCreeps(ArrayList<Creep> creeps) {
		Tower.creeps = creeps;
	}
}