package pmtd.systems;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import pmtd.components.Position;
import pmtd.components.Sprite;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class RenderingSystem extends EntityProcessingSystem {

	@Mapper ComponentMapper<Position> pm;
    @Mapper ComponentMapper<Sprite> sm;
    
    private GameContainer con;

	public RenderingSystem(GameContainer container) {
		super(Aspect.getAspectForAll(Position.class, Sprite.class));
		con = container;
	}

	@Override
	protected void process(Entity entity) {
		Graphics g = con.getGraphics();
		Sprite sp = sm.getSafe(entity);
		Position pos = pm.get(entity);
		
		sp.image.setRotation(pos.dir);
		g.drawImage(sp.image, pos.x - 32, pos.y - 32);
	}
}