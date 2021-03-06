package pmtd.systems;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import pmtd.components.Cooldown;
import pmtd.components.Damage;
import pmtd.components.Direction;
import pmtd.components.Id;
import pmtd.components.Position;
import pmtd.components.Range;
import pmtd.components.Sprite;
import pmtd.components.Target;
import pmtd.components.Velocity;
import pmtd.util.GameMath;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.ImmutableBag;

public class AttackingSystem extends EntityProcessingSystem{
	private World world;
	int id = 0;
	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Direction> dm;
	@Mapper ComponentMapper<Cooldown> cm;
	@Mapper ComponentMapper<Range> rm;
	
	public AttackingSystem(World world) {
		super(Aspect.getAspectForAll(Position.class, Direction.class, Cooldown.class, Range.class));
		this.world = world;
	}

	@Override
	protected void process(Entity e) {
		Position pos = pm.get(e);
		Direction dir = dm.get(e);
		Range range = rm.get(e);
		Cooldown cd = cm.get(e);
		
		ImmutableBag<Entity> creeps = world.getManager(GroupManager.class).getEntities("Creeps");
		System.err.println("creep count: " + creeps.size());
		
		// check every creep
		for(int i = 0; i < creeps.size(); i++) {
			Entity creep = creeps.get(i);
			Position creepPos = creep.getComponent(Position.class);
			
			// if its in range to this attacker
			if(GameMath.calcDist(pos.x, pos.y, creepPos.x, creepPos.y) <= range.range) {
				// aim into targets direction
				Vector2f vn = new Vector2f(pos.x, pos.y);
				vn.sub(new Vector2f(creepPos.x, creepPos.y));
				vn.normalise().negateLocal();
				dir.dir = (float) (vn.getTheta() + 90);

				spawnSimpleBullet(creep, pos, cd);
				id++;
				break;
			}
		}
	}

	private void spawnSimpleBullet(Entity creep, Position pos, Cooldown cd) {
		cd.lastShotMade += world.getDelta();
		if(cd.lastShotMade < cd.cooldown)
			return;
		
		Position cp = creep.getComponent(Position.class);
		
		Entity bullet = world.createEntity();
		bullet.addComponent(new Damage(30));
		bullet.addComponent(new Position((int)pos.x, (int)pos.y));
		bullet.addComponent(new Target(creep));
		bullet.addComponent(new Id(this.id));

		bullet.addComponent(new Velocity(8f));
		
		try {
			bullet.addComponent(new Sprite("Bullet.png"));
		} catch (SlickException e1) {
			e1.printStackTrace();
		}
		
		bullet.addToWorld();		
		cd.lastShotMade -= cd.cooldown;
	}
}
