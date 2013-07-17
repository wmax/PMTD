package pmtd;

import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Path;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

import pmtd.components.Cooldown;
import pmtd.components.Direction;
import pmtd.components.Health;
import pmtd.components.Position;
import pmtd.components.Range;
import pmtd.components.Sprite;
import pmtd.components.Target;
import pmtd.components.Velocity;
import pmtd.components.Waypoints;
import pmtd.entities.creeps.Creep;
import pmtd.entities.towers.Tower;
import pmtd.systems.AttackingSystem;
import pmtd.systems.ImpactSystem;
import pmtd.systems.MovementSystem;
import pmtd.systems.RangeRenderingSystem;
import pmtd.systems.RenderingSystem;
import pmtd.systems.RotationSystem;
import pmtd.systems.VelocitySystem;
import pmtd.systems.WaypointFollowingSystem;
import pmtd.util.SpriteCache;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;

public class TDGame extends BasicGame {
	
	// integrating artemis
	private World world = new World();
	private EntitySystem renderingSystem;
	private EntitySystem rangeRenderingSystem;
	
	private TiledMap map;
	
	private ArrayList<Creep> creeps = new ArrayList<Creep>();

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
		
		//integrating artemis
		world.setManager(new GroupManager());
        world.setManager(new TagManager());
        world.setSystem(new MovementSystem());
        world.setSystem(new WaypointFollowingSystem());
        world.setSystem(new AttackingSystem(world));
        world.setSystem(new VelocitySystem());
        world.setSystem(new RotationSystem());
        world.setSystem(new ImpactSystem());
        renderingSystem = world.setSystem(new RenderingSystem(gc), true);
        rangeRenderingSystem = world.setSystem(new RangeRenderingSystem(gc), true);

        world.initialize();
        
		gc.setVSync(true);
		
		SpriteCache.instanceOf().addResourceLocation("./resources/sprites/");
		map = new TiledMap("/resources/TestMap1.tmx");

		waypoints.add(new Vector2f(0, 96));
		waypoints.add(new Vector2f(544, 96));
		waypoints.add(new Vector2f(544, 288));
		waypoints.add(new Vector2f(288, 288));
		waypoints.add(new Vector2f(288, 352));
		waypoints.add(new Vector2f(96, 352));
		
		// the last point's position is outside the map to
		// produce the illusion that creeps acutally leave the map
		waypoints.add(new Vector2f(96, 650));

		Tower.setCreeps(creeps);
	}

	@Override
	public void update(GameContainer gc, int timeDelta) throws SlickException {
		//integrating artemis
        world.setDelta(timeDelta);
        world.process();

		handleInput(gc);
		
		if( spawningInitiated )
			spawnCreep(timeDelta);
				
		if( creeps.size() > 0)
			manageCreeps();
	}

	private void manageCreeps() {		
		Vector2f end = waypoints.get(waypoints.size() - 1);

		for( int i = 0; i < creeps.size(); i++) {
			Creep c = creeps.get(i);
			Vector2f pos = new Vector2f(c.getRect().getCenter());
			
			if( c.getHealth() <= 0 ) {
				money += c.getBounty();
				score++;
			} else if( pos.distance(end) < 1 )
				lifes--;
		
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
				
		if(i.isKeyPressed(Input.KEY_R)) {
			// the tower consists of two independent entities, which are 
			// connected by their position
			
			// the bottom
			Entity e = world.createEntity();
			Position pos = new Position(i.getMouseX(), i.getMouseY());
			e.addComponent(pos);
			e.addComponent(new Sprite("sBase.png"));
			e.addComponent(new Direction(90));
			e.addToWorld();
			
			// the top
			e = world.createEntity();
			e.addComponent(pos);
			e.addComponent(new Direction(0));
			e.addComponent(new Sprite("sTop.png"));			
			e.addComponent(new Range(100));
			e.addComponent(new Cooldown(1000));
			e.addToWorld();
		}
	}

	private void spawnCreep(int timeDelta) throws SlickException {		
		lastCreepSpawned += timeDelta;

		if( lastCreepSpawned > 600 && level > creepsSpawned ) {
			
			Entity e = world.createEntity();
			e.addComponent(new Position((int)waypoints.get(0).x, (int)waypoints.get(0).y));
			e.addComponent(new Sprite("simpleCreepTop.png"));
			e.addComponent(new Health(20 + level * 5));
			e.addComponent(new Velocity(1));
			e.addComponent(new Direction(0));
			e.addComponent(new Waypoints(waypoints, 0));
			e.addComponent(new Target((int)waypoints.get(0).x, (int)waypoints.get(0).y));
			e.addToWorld();
			world.getManager(GroupManager.class).add(e, "Creeps");
			
			creepsSpawned++;
			lastCreepSpawned = 0;
		} else if( level == creepsSpawned && creeps.size() == 0) {
			spawningInitiated = false;
			creepsSpawned = 0;
			level++;
		}
	}

	@Override
	public void render(GameContainer gc, Graphics pen) throws SlickException {
		
		map.render(0, 0);
	//	renderWay(gc, pen);
		
		for( Creep c : creeps )	
			c.render(pen);
				
		renderGui(gc, pen);

		renderingSystem.process();
		rangeRenderingSystem.process();
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