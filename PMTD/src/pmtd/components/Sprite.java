package pmtd.components;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import pmtd.util.SpriteCache;

import com.artemis.Component;

public class Sprite extends Component {
	public Image image;

	public Sprite(String imageName) throws SlickException {
		image = SpriteCache.instanceOf().getSprite(imageName);
	}
}
