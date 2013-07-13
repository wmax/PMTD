package pmtd.entities.creeps;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class PathFollowingCreep extends Creep {
	
	private static ArrayList<Vector2f> waypoints;
	private int nextWaypoint = 0;

	public PathFollowingCreep(int health, boolean centered)
			throws SlickException {
		super(
				cache.getSprite("simpleCreepTop.png"),
				(int) waypoints.get(0).x,
				(int) waypoints.get(0).y,
				centered
		);
		this.health = health;
	}

	@Override
	protected void selectTarget() {
		Vector2f vect = new Vector2f( rect.getCenter() );
		if( vect.distance(waypoints.get(nextWaypoint)) > 1
			|| waypoints.size() == nextWaypoint +1 )
			return;
		
		nextWaypoint++;
		path.clear();
		moveTarget = waypoints.get(nextWaypoint);
		calcPath();
	}

	public static void setWaypoints(ArrayList<Vector2f> waypoints) {
		PathFollowingCreep.waypoints = waypoints;
	}
}