package pmtd.entities.towers;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import pmtd.entities.bullets.SimpleBullet;
import pmtd.entities.creeps.Creep;

public class BasicTower extends Tower {
	private static int prize = 50;

	public BasicTower(int x, int y, boolean centered) throws SlickException {
		super(
				cache.getSprite("sBase.png"),
				cache.getSprite("sTop.png"),
				new Sound("/resources/sounds/gunshot1.ogg"),
				x, y
		);
		maxTargetCount = 1;
	}
	
	@Override
	protected void spawnBullet(Creep c) throws SlickException {
		myBullet = new SimpleBullet(
				(int) rect.getCenterX(),
				(int) rect.getCenterY(), c, power
		);
		bullets.add(myBullet);
	}
	
	public static int getPrize() { return prize; }
	public static void setPrize(int prize) { BasicTower.prize = prize; }
}
