package pmtd.components;

import org.newdawn.slick.geom.Vector2f;

import com.artemis.Component;
import com.artemis.Entity;

public class Target extends Component {
	public Entity entity;
	public int lastX, lastY;
	public boolean noEntity = false;

	public Target(Entity t) {
		entity = t;
		Position pos = t.getComponent(Position.class);
		lastX = (int) pos.x;
		lastY = (int) pos.y;
	}
	
	public Target(int x, int y) {
		lastX = x;
		lastY = y;
	}
	
	public Vector2f getPosition() {
		if(noEntity)
			return new Vector2f(lastX, lastY);

		if(entity == null) {
			noEntity = true;
			return new Vector2f(lastX, lastY);
		}
		
		Position t = entity.getComponent(Position.class);
		if(t == null)
			return new Vector2f(lastX, lastY);

		return new Vector2f(t.x, t.y);
	}
}
