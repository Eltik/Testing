package endran.bridge;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class Game {
    Team team1;
    Team team2;
    Helpers helper = new Helpers();
    public Game(Team team1, Team team2) {
        this.team1 = team1;
        this.team2 = team2;
    }

    public void startGame(WorldMap map, Location loc) {
    	if (map.mapExists()) {
    		map.createMap(loc);
    		helper.log("Loaded map.");
    		Location[] locs = map.getLocations();
    		
        	World world = loc.getWorld();
        	double x = loc.getBlockX() + locs[0].getBlockX();
        	double y = loc.getBlockY() + locs[0].getBlockY();
        	double z = loc.getBlockZ() + locs[0].getBlockZ();
        	
        	Location newLoc = new Location(world, x, y, z);
        	
        	double x2 = loc.getBlockX() + locs[1].getBlockX();
        	double y2 = loc.getBlockY() + locs[1].getBlockY();
        	double z2 = loc.getBlockZ() + locs[1].getBlockZ();
        	
        	Location newLoc2 = new Location(world, x2, y2, z2);
        	
            team1.teleport(newLoc);
            team2.teleport(newLoc2);
            helper.log("Teleported players.");
            team1.giveKit();
            team2.giveKit();
            Bukkit.getLogger().info("Game started!");	
    	}
    }
}