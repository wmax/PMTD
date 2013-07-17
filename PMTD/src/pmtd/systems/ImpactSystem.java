package pmtd.systems;

import org.newdawn.slick.geom.Vector2f;

import pmtd.components.Damage;
import pmtd.components.Health;
import pmtd.components.Position;
import pmtd.components.Target;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class ImpactSystem extends EntityProcessingSystem {

    @Mapper ComponentMapper<Position> pm;
    @Mapper ComponentMapper<Target> tm;
    @Mapper ComponentMapper<Damage> dm;

    public ImpactSystem() {
        super(Aspect.getAspectForAll(Position.class, Target.class, Damage.class));
    }

    @Override
    protected void process(Entity e) {
        Position pos = pm.getSafe(e);
        Target target = tm.getSafe(e);
        Damage dmg = dm.getSafe(e);
        
        if(target.getTarget().distance(new Vector2f(pos.x, pos.y)) < 2) {        	
        	Health enemyHealth = target.target.getComponent(Health.class);
        	
        	if(enemyHealth == null) {
        		e.deleteFromWorld();
        		return;
        	}
        	
        	enemyHealth.health -= dmg.amount;
        	e.deleteFromWorld();
        	
        	if(enemyHealth.health <= 0) {
        		target.target.deleteFromWorld();
        	}
        }
    }
}