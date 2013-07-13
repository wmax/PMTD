package pmtd.util;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.FileSystemLocation;
import org.newdawn.slick.util.ResourceLoader;
import org.newdawn.slick.util.ResourceLocation;

public class SpriteCache {

	private static SpriteCache spriteCache;
	private HashMap<String, Image>	sprites = new HashMap<String, Image>();
	
	public static SpriteCache instanceOf() {
		if(spriteCache == null)
			spriteCache = new SpriteCache();
		
		return spriteCache;
	}
	
	public Image getSprite(String nameWithPostfix) throws SlickException {
		if( !sprites.containsKey(nameWithPostfix) ) {
			InputStream is = ResourceLoader.getResourceAsStream(nameWithPostfix);
			Image im = new Image(is, nameWithPostfix, false);
			sprites.put(nameWithPostfix, im);	
		}

		return sprites.get(nameWithPostfix);
	}
	
	public void removeSprite(String nameWithPostfix) {
		if( sprites.containsKey(nameWithPostfix) )
			sprites.remove(nameWithPostfix);
	}
	
	public void addResourceLocation(String pathToResource) {
		ResourceLocation rl = new FileSystemLocation( new File(pathToResource) );
		ResourceLoader.addResourceLocation( rl );
	}	
}