package pmtd.systems;

import org.newdawn.slick.geom.Vector2f;

import pmtd.TDGame;
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
	TDGame theGame;
    @Mapper ComponentMapper<Position> pm;
    @Mapper ComponentMapper<Target> tm;
    @Mapper ComponentMapper<Damage> dm;

    public ImpactSystem(TDGame tdGame) {
        super(Aspect.getAspectForAll(Position.class, Target.class, Damage.class));
        theGame = tdGame;
    }

    @Override
    protected void process(Entity e) {
        Position pos = pm.getSafe(e);
        Target target = tm.getSafe(e);
        Damage dmg = dm.getSafe(e);
        
        if(target.getPosition().distance(new Vector2f(pos.x, pos.y)) < 5) {   
//        	e.deleteFromWorld();
//        	return;
        	Health enemyHealth = target.entity.getComponent(Health.class);
        	
        	if(enemyHealth == null) {
        		e.deleteFromWorld();
        		return;
        	}
        	
        	enemyHealth.health -= dmg.amount;
        	e.deleteFromWorld();
        	
        	if(enemyHealth.health <= 0) {
        		theGame.money += 20;
        		theGame.score++;
        		target.entity.deleteFromWorld();
        	}
        }
    }
}