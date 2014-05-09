package pmtd.systems;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import pmtd.components.Direction;
import pmtd.components.Position;
import pmtd.components.Sprite;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class BackGroundRenderingSystem extends EntityProcessingSystem {

	@Mapper ComponentMapper<Position> pm;
//	@Mapper ComponentMapper<Direction> dm;
    @Mapper ComponentMapper<Sprite> sm;
    
    private GameContainer con;

	public BackGroundRenderingSystem(GameContainer container) {
		super(Aspect.getAspectForAll(Position.class, Sprite.class).exclude(Direction.class));
		con = container;
	}

	@Override
	protected void process(Entity entity) {
		Graphics g = con.getGraphics();
		Sprite sp = sm.getSafe(entity);
		Position pos = pm.get(entity);
		
		if(pos == null)
			return;
	//	Direction dir = dm.get(entity);
		
	//	sp.image.setRotation(dir.dir);
		g.drawImage(sp.image, pos.x - 32, pos.y - 32);
	}
}