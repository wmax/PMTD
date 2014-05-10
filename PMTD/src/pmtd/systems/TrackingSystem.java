package pmtd.systems;

import org.newdawn.slick.SlickException;

import pmtd.components.Id;
import pmtd.components.Position;
import pmtd.components.Target;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class TrackingSystem extends EntityProcessingSystem{
	
	@Mapper ComponentMapper<Target> tm;
	
	public TrackingSystem(World world) throws SlickException {
		super(Aspect.getAspectForAll(Target.class));
	}

	@Override
	protected void process(Entity e) {
		Target t = tm.getSafe(e);
		
		if(t.entity == null)
			return;

		Position tPos = t.entity.getComponent(Position.class);
		Position ePos = e.getComponent(Position.class);
		
		if(tPos == null) {
			System.err.println("Bullet " + e.getComponent(Id.class).id + "- null");
			return;
		}
		System.err.println("Bullet " + e.getComponent(Id.class).id + "- " + tPos.x + " " + tPos.y + "\tpos: " + ePos.x + " " + ePos.y);
		t.lastX = (int) tPos.x;
		t.lastY = (int) tPos.y;
	}
}
