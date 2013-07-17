package pmtd.components;

import com.artemis.Component;

public class Cooldown extends Component {
	public int cooldown, lastShotMade = 0;
	
	public Cooldown(int amount) {
		cooldown = amount;
	}
}
