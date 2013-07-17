package pmtd.systems;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

import pmtd.components.Direction;
import pmtd.components.Position;
import pmtd.components.Range;
import pmtd.components.Sprite;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class RangeRenderingSystem extends EntityProcessingSystem {

	@Mapper ComponentMapper<Position> pm;
    @Mapper ComponentMapper<Range> rm;

    private GameContainer con;

	public RangeRenderingSystem(GameContainer container) {
		super(Aspect.getAspectForAll(Position.class, Range.class));
		con = container;
	}

	@Override
	protected void process(Entity entity) {
		Graphics g = con.getGraphics();
		Position pos = pm.get(entity);
		Range range = rm.get(entity);
		
		System.err.println("Drawing dat range");
		g.setColor(Color.gray);
		g.setLineWidth(1);
		g.draw( new Circle(pos.x, pos.y, range.range) );
	}
}