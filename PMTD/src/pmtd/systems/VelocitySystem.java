package pmtd.systems;

import org.newdawn.slick.geom.Vector2f;

import pmtd.components.Position;
import pmtd.components.Target;
import pmtd.components.Velocity;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

/**
 * Calculate the velocity for every moving entity depending on the target's position.
 * @author wmax
 *
 */
public class VelocitySystem extends EntityProcessingSystem {

    @Mapper ComponentMapper<Position> pm;
    @Mapper ComponentMapper<Velocity> vm;
    @Mapper ComponentMapper<Target> tm;

    float lastX, lastY;
    public VelocitySystem() {
        super(Aspect.getAspectForAll(Position.class, Velocity.class, Target.class));
    }

    @Override
    protected void process(Entity e) {
        Position pos = pm.get(e);
        Target target = tm.getSafe(e);
        
        Vector2f moveTarget = target.getPosition();
        
        if(Math.abs(lastX - moveTarget.x) > 100 || Math.abs(lastY - moveTarget.y) > 100)
//        	if(e.getId() == 2)
//        	System.err.println(e.getId() + " " + moveTarget.toString());

        // if the target's positions hasn't changed do not proceed
        if(pos.x == moveTarget.x && pos.y == moveTarget.y)
        	return;
        
        Velocity velocity = vm.get(e);
       
		Vector2f start = new Vector2f(pos.x, pos.y);
		Vector2f vn = new Vector2f(start);
		moveTarget.sub(vn).normalise();
		
		velocity.vecX = moveTarget.x * velocity.speed;
		velocity.vecY = moveTarget.y * velocity.speed;
		lastX = moveTarget.x;
		lastY = moveTarget.y;
    }
}