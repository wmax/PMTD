package pmtd.systems;

import pmtd.components.Position;
import pmtd.components.Velocity;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class MovementSystem extends EntityProcessingSystem {

    @Mapper ComponentMapper<Position> pm;
    @Mapper ComponentMapper<Velocity> vm;

    public MovementSystem() {
        super(Aspect.getAspectForAll(Position.class, Velocity.class));
    }

    @Override
    protected void process(Entity e) {
        Position pos = pm.get(e);
        Velocity velocity = vm.get(e);
       
        if(velocity == null)
        	return;
        
        pos.x += velocity.vecX;
        pos.y += velocity.vecY;
    }
}