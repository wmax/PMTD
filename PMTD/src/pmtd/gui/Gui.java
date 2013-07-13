package pmtd.gui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

/**
 * This class is not finished yet.
 * It's purpose is to factor out the gui code from TDGame.
 * @author max
 *
 */
public class Gui {
	private ArrayList<Image> images = new ArrayList<Image>();
	
	private Rectangle screenArea;
	private Rectangle buildArea;
	private Rectangle statArea;
	
	private Rectangle spriteBounds = new Rectangle(0, 0, 64, 64);
	private int spacing = 5;
	
	public Gui(Rectangle screenArea) {
		this.screenArea = screenArea;
		
		buildArea = new Rectangle(
				0,
				screenArea.getHeight() - spriteBounds.getHeight() - spacing,
				screenArea.getWidth(),
				spriteBounds.getHeight() + spacing
		);
		
		statArea = new Rectangle(
				screenArea.getWidth() - 100 - spacing, 
				0, 
				screenArea.getWidth(), 
				100
		);
	}
	
	public void update(GameContainer gc, int timeDelta) {
		handleInput(gc.getInput());
	}
	
	private void handleInput(Input input) {
	}

	public void render(GameContainer gc, Graphics pen) {
		pen.setColor(Color.gray);
		pen.setBackground(Color.gray);
		pen.fill(buildArea);
	}
}
