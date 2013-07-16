package pmtd.components;

import com.artemis.Component;

public class Position extends Component {
	public float x, y, dir;

	public Position(int x, int y, float direction) {
		this.x = x;
		this.y = y;
		dir = direction;
	}
}