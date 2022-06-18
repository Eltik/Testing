package endran.bridge;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin{
    public void onEnable() {
    	Helpers helper = new Helpers();
    	helper.log("Loaded Bridge.");
        Bukkit.getServer().getPluginManager().registerEvents(new Listeners(this), this);
        new Commands(this);
    }
}
