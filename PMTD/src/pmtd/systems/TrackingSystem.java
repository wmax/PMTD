package pmtd.systems;

import org.newdawn.slick.SlickException;

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
		
		if(t.target == null)
			return;

		Position tPos = t.target.getComponent(Position.class);
		
		if(tPos == null)
			return;
		
		t.lastX = (int) tPos.x;
		t.lastY = (int) tPos.y;
	}
}
