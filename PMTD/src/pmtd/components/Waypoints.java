package pmtd.components;

import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

import com.artemis.Component;

public class Waypoints extends Component {
	public ArrayList<Vector2f> waypoints;
	public int current;
	
	public Waypoints(ArrayList<Vector2f> waypoints, int current) {
		this.waypoints = waypoints;
		this.current = current;
	}
}
