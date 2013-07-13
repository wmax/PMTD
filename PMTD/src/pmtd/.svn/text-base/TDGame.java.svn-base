package pmtd;

import java.util.ArrayList;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Path;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

import pmtd.entities.bullets.SimpleBullet;
import pmtd.entities.creeps.Creep;
import pmtd.entities.creeps.PathFollowingCreep;
import pmtd.entities.towers.BasicTower;
import pmtd.entities.towers.Tower;
import pmtd.gui.Gui;
import pmtd.util.SpriteCache;

/**
 * Ein Grundgerüst für unsere Towerdefense.
 * Einfach diese Klasse ausführen und schon zeigt sich ein 
 * schwarzes Fenster mit einen kleinen Kreis.
 * 
 * Mit Escape kann man das "Spiel" schließen.
 * Mit Pfeil-Hoch kann man den Kreis in Bewegung setzen.
 * Mit Pfeil-Runter kann man den kreis anhalten.
 * Dieser bewegt sich Horizontal über den Bildschirm hin und her.
 * 
 * @author max
 *
 */
public class TDGame extends BasicGame {
	private TiledMap map;
	private Gui gui;
	
	private ArrayList<Tower> towers = new ArrayList<Tower>();
	private ArrayList<Creep> creeps = new ArrayList<Creep>();
	private ArrayList<SimpleBullet> bullets = new ArrayList<SimpleBullet>();

	private int lifes = 10;
	private int money = 100;
	private int score = 0;
	private int level = 1;
	
	private boolean spawningInitiated = false;
	private int creepsSpawned = 0;
	private int lastCreepSpawned = 0;
	
	protected ArrayList<Vector2f> waypoints = new ArrayList<Vector2f>();
	protected Path pathShape;
			
	public TDGame(String title) throws SlickException {
		super(title);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		gc.setVSync(true);
		
		SpriteCache.instanceOf().addResourceLocation("./resources/sprites/");
		map = new TiledMap("/resources/TestMap1.tmx");

		waypoints.add(new Vector2f(0, 96));
		waypoints.add(new Vector2f(544, 96));
		waypoints.add(new Vector2f(544, 288));
		waypoints.add(new Vector2f(288, 288));
		waypoints.add(new Vector2f(288, 352));
		waypoints.add(new Vector2f(96, 352));
		waypoints.add(new Vector2f(96, 640));

		Tower.setCreeps(creeps);
		Tower.setBullets(bullets);
		PathFollowingCreep.setWaypoints(waypoints);
		
		gui = new Gui(new Rectangle(0, 0, map.getWidth()*64, map.getHeight()*64));
		
		for( Vector2f v : waypoints)
			if( pathShape == null )	pathShape = new Path(v.x, v.y);
			else					pathShape.lineTo(v.x, v.y);
	}

	@Override
	public void update(GameContainer gc, int timeDelta) throws SlickException {
		handleInput(gc);
		if( spawningInitiated ) spawnCreep(timeDelta);
		manageBullets();
		if( creeps.size() > 0) manageCreeps();
		
		for( Creep c : creeps )
			c.update(gc, timeDelta);
		
		for( SimpleBullet b : bullets )
			b.update(gc, timeDelta);
		
		for( Tower t : towers )
			t.update(gc, timeDelta);
	}
	
	private void manageBullets() {
		for(int i = 0; i < bullets.size(); i++) {
			SimpleBullet b = bullets.get(i);
			if( b.getTargetHit() )
				bullets.remove(b);
		}
	}

	private void manageCreeps() {		
		Vector2f end = waypoints.get(waypoints.size() - 1);

		for( int i = 0; i < creeps.size(); i++) {
			Creep c = creeps.get(i);
			Vector2f pos = new Vector2f(c.getRect().getCenter());
			
			if( c.getHealth() <= 0 ) {
				money += c.getBounty();
				score++;
			} else if( pos.distance(end) < 1 ) lifes--;
		
			if( c.getHealth() <= 0 || pos.distance(end) < 1)
				creeps.remove(c);
		}
	}

	public void handleInput(GameContainer gc) throws SlickException {
		Input i = gc.getInput();

		if(i.isKeyPressed(Input.KEY_ESCAPE))
			gc.exit();
		
		if(i.isKeyPressed(Input.KEY_C) && !spawningInitiated)
			spawningInitiated = true;
		
		if(i.isKeyPressed(Input.KEY_T))
			buyTower(i.getMouseX(), i.getMouseY());
	}

	private void spawnCreep(int timeDelta) throws SlickException {		
		lastCreepSpawned += timeDelta;

		if( lastCreepSpawned > 600 && level > creepsSpawned ) {
			creeps.add( new PathFollowingCreep(20 + level * 5, true) );
			creepsSpawned++;
			lastCreepSpawned = 0;
		} else if( level == creepsSpawned && creeps.size() == 0) {
			spawningInitiated = false;
			creepsSpawned = 0;
			level++;
		}
	}

	private void buyTower(int x, int y) throws SlickException {
		if( money < BasicTower.getPrize() ) return;
		towers.add( new BasicTower(x, y, true) );
		money -= BasicTower.getPrize();
	}

	@Override
	public void render(GameContainer gc, Graphics pen) throws SlickException {
		map.render(0, 0);
	//	renderWay(gc, pen);

		for( SimpleBullet b : bullets )
			b.render(pen);
		
		for( Creep c : creeps )	
			c.render(pen);
		
		for( Tower t : towers )	
			t.render(pen);
		
		renderGui(gc, pen);
		gui.render(gc, pen);
	}
	
	private void renderGui(GameContainer gc, Graphics pen) {
		pen.setColor(Color.white);
		pen.drawString(	"Lifes: " + Integer.toString(lifes), map.getWidth()*64 - 100, 0);
		pen.drawString(	"Money: " + Integer.toString(money), map.getWidth()*64 - 100, 20);
		pen.drawString( "Score: " + Integer.toString(score), map.getWidth()*64 - 100, 40);
		pen.drawString( "Level: " + Integer.toString(level), map.getWidth()*64 - 100, 60);
	}

	@SuppressWarnings("unused")
	private void renderWay(GameContainer gc, Graphics pen) {
		pen.setLineWidth(100);
		pen.setColor(Color.darkGray);
		pen.draw(pathShape);
	}

	public static void main ( String[] args ) throws SlickException {
		TDGame game = new TDGame("A Test");
		AppGameContainer container = new AppGameContainer(game, 640, 640, false);
		container.start();
	}
}