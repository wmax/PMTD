package pmtd.systems;

import org.newdawn.slick.geom.Vector2f;

import pmtd.components.Position;
import pmtd.components.Velocity;
import pmtd.components.Waypoints;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class WaypointFollowingSystem extends EntityProcessingSystem {

	@Mapper ComponentMapper<Waypoints> wm;
    @Mapper ComponentMapper<Velocity> vm;
    @Mapper ComponentMapper<Position> pm;

	public WaypointFollowingSystem() {
		super(Aspect.getAspectForAll(Position.class, Velocity.class, Waypoints.class));
	}

	@Override
	protected void process(Entity entity) {
		Waypoints wps = wm.get(entity);
		Velocity vel = vm.get(entity);
		Position pos = pm.get(entity);
		
		if(wps.current < wps.waypoints.size()) {
			Vector2f moveTarget = wps.waypoints.get(wps.current);
		
			Vector2f start = new Vector2f(pos.x, pos.y);
			Vector2f vn = new Vector2f(start);
			vn.sub(moveTarget).normalise().negateLocal();
			
			pos.dir = (float) (vn.getTheta() + 90);
			vel.vecX = vn.x;
			vel.vecY = vn.y;
			
			if(start.distance(moveTarget) <= 1)
				wps.current += 1;
		}
	}
}