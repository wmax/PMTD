package pmtd.systems;

import org.newdawn.slick.geom.Vector2f;

import pmtd.TDGame;
import pmtd.components.Position;
import pmtd.components.Target;
import pmtd.components.Waypoints;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class WaypointFollowingSystem extends EntityProcessingSystem {
	TDGame tdGame;

	@Mapper ComponentMapper<Waypoints> wm;
    @Mapper ComponentMapper<Position> pm;
    @Mapper ComponentMapper<Target> tm;

	public WaypointFollowingSystem(TDGame tdGame) {
		super(Aspect.getAspectForAll(Position.class, Waypoints.class));
		this.tdGame = tdGame;
	}

	@Override
	protected void process(Entity entity) {
		Waypoints wps = wm.get(entity);
		Position pos = pm.get(entity);
		Target target = tm.get(entity);
		
		if(wps.current < wps.waypoints.size()) {
			Vector2f moveTarget = wps.waypoints.get(wps.current);
			Vector2f start = new Vector2f(pos.x, pos.y);

			if(target.lastX != moveTarget.x || target.lastY != moveTarget.y) {
				System.err.println("Adding target to creep");
				target.lastX = (int)moveTarget.x;
				target.lastY = (int)moveTarget.y;
			}
			
			if(start.distance(moveTarget) <= 1)
				wps.current += 1;
		} else {
			this.tdGame.lifes--;
			entity.deleteFromWorld();
		}
	}
}