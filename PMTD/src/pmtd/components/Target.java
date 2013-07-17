package pmtd.components;

import org.newdawn.slick.geom.Vector2f;

import com.artemis.Component;
import com.artemis.Entity;

public class Target extends Component {
	public Entity target;
	public int lastX, lastY;

	public Target(Entity t) {
		target = t;
		Position pos = t.getComponent(Position.class);
		lastX = (int) pos.x;
		lastY = (int) pos.y;
	}
	
	public Target(int x, int y) {
		lastX = x;
		lastY = y;
	}
	
	public Vector2f getTarget() {
		if(target == null)
			return new Vector2f(lastX, lastY);
		
		Position t = target.getComponent(Position.class);
		if(t == null)
			return new Vector2f(lastX, lastY);

		return new Vector2f(t.x, t.y);
	}
}
