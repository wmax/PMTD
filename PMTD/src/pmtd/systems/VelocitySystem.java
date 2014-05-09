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
 * Calculate velocity the velocity for every moving entity depending on the target's position.
 * @author wmax
 *
 */
public class VelocitySystem extends EntityProcessingSystem {

    @Mapper ComponentMapper<Position> pm;
    @Mapper ComponentMapper<Velocity> vm;
    @Mapper ComponentMapper<Target> tm;

    public VelocitySystem() {
        super(Aspect.getAspectForAll(Position.class, Velocity.class, Target.class));
    }

    @Override
    protected void process(Entity e) {
        Position pos = pm.get(e);
        Target target = tm.getSafe(e);
        
        Vector2f moveTarget = target.getTarget();

        // if the target's positions hasn't changed do not proceed
        if(pos.x == moveTarget.x && pos.y == moveTarget.y)
        	return;
        
        Velocity vel = vm.get(e);
       
		Vector2f start = new Vector2f(pos.x, pos.y);
		Vector2f vn = new Vector2f(start);
		vn.sub(moveTarget).normalise().negateLocal();
		
		vel.vecX = vn.x * vel.speed;
		vel.vecY = vn.y * vel.speed;
    }
}