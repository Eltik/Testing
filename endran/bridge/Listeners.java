package endran.bridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class Listeners implements Listener {
	private App app;
	public Listeners(App app) {
		this.app = app;
	}
	
	@EventHandler
    public void setMap(PlayerInteractEvent event) {
        
    }
}
