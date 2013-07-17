package pmtd.systems;

import org.newdawn.slick.geom.Vector2f;

import pmtd.components.Direction;
import pmtd.components.Position;
import pmtd.components.Target;
import pmtd.components.Velocity;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class RotationSystem extends EntityProcessingSystem {

    @Mapper ComponentMapper<Position> pm;
    @Mapper ComponentMapper<Direction> dm;
    @Mapper ComponentMapper<Target> tm;

    public RotationSystem() {
        super(Aspect.getAspectForAll(Position.class, Direction.class, Target.class));
    }

    @Override
    protected void process(Entity e) {
        Position pos = pm.get(e);
        Direction dir = dm.get(e);
        Target target = tm.get(e);

        Vector2f turnToTarget = target.getTarget();
        
		Vector2f vn = new Vector2f(pos.x, pos.y);
		vn.sub(turnToTarget).normalise().negateLocal();
		
		dir.dir = (float) (vn.getTheta() + 90);
    }
}