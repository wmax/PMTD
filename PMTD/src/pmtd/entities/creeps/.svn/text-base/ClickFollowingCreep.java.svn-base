package pmtd.entities.creeps;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class ClickFollowingCreep extends Creep {
	
	private static Vector2f lastClickedPosition;

	public ClickFollowingCreep(int x, int y, boolean centered)
			throws SlickException {
		super(cache.getSprite("simpleCreepTop.png"), x, y, centered);
	}

	@Override
	protected void selectTarget() {
		if( lastClickedPosition == null)
			return;
		
		if( moveTarget == null || !moveTarget.equals(lastClickedPosition)) {
			moveTarget = lastClickedPosition;
			path.clear();
			calcPath();
		}
	}

	@Override
	public void update(GameContainer gc, int timeDelta) {
		if( gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON))
			lastClickedPosition = new Vector2f(gc.getInput().getMouseX(), gc.getInput().getMouseY());		
		super.update(gc, timeDelta);
	}
}
