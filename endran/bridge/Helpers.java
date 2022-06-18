package endran.bridge;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class Helpers {
	
	public Helpers() {
		// Constructor
	}
	
	public boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
	
    public ArrayList<Block> getBlocks(Block start, int radius) {
    	ArrayList<Block> blocks = new ArrayList<Block>();
    	for (double x = start.getLocation().getX() - radius; x <= start.getLocation().getX() + radius; x++) {
    		for (double y = start.getLocation().getY() - radius; y <= start.getLocation().getY() + radius; y++) {
    			for (double z = start.getLocation().getZ() - radius; z <= start.getLocation().getZ() + radius; z++) {
    				Location blockLocation = new Location(start.getWorld(), x, y, z);
    				Location loc = blockLocation.subtract(start.getLocation());
    				Block block = new Block(loc, blockLocation.getBlock());
    				blocks.add(block);
    			}
		    }
    	}
        return blocks;
    }
    
    public ArrayList<Block> getMap(String mapName) {
    	File file = new File(mapName + ".txt");
    	if (!file.exists() || !file.canRead()) {
    		return null;
    	}
    	ArrayList<Block> list = new ArrayList<>();
    	try {
    		Scanner reader = new Scanner(file);
    		while (reader.hasNextLine()) {
    			String data = reader.nextLine();
    			String[] split = data.split(", ");
    			double x = 0;
    			double y = 0;
    			double z = 0;
    			String type = "";
    			String worldName = "";
    			for (String a : split) {
    				if (a.contains("x:")) {
    					String[] splitX = a.split("x: ");
    					x = Double.parseDouble(splitX[1]);
    				}
    				if (a.contains("y:")) {
    					String[] splitY = a.split("y: ");
    					y = Double.parseDouble(splitY[1]);
    				}
    				if (a.contains("z:")) {
    					String[] splitZ = a.split("z: ");
    					z = Double.parseDouble(splitZ[1]);
    				}
    				if (a.contains("type:")) {
    					String[] splitType = a.split("type: ");
    					type = splitType[1].split(" ")[0];
    				}
    				if (a.contains("world:")) {
    					String[] splitType = a.split("world: ");
    					worldName = splitType[1].split(" }")[0];
    				}
    			}
    			World world = Bukkit.getWorld(worldName);
    			Location loc = new Location(world, x, y, z);
    			Block block = new Block(loc, type);
    			list.add(block);
    		}
    		reader.close();
    		return list;
	    } catch (FileNotFoundException e) {
	    	System.out.println("An error occurred.");
	    	e.printStackTrace();
	    	return null;
	    }
    }
    
    public void createMap(Location loc, ArrayList<Block> mapData) {
        for (Block block : mapData) {
        	Location blockLoc = block.getLocation();
        	World world = loc.getWorld();
        	
        	Location newLoc = blockLoc.add(loc);
        	world.getBlockAt(newLoc).setType(block.getType());
        	if (block.getType().toString() != "AIR") {
        		this.log(block.getLocation().toString());
            	//this.log("Placing " + block.getType() + " at x: " + newLoc.getBlockX() + " y: " + newLoc.getBlockY() + " z: " + newLoc.getBlockZ());	
        	}
        }
    }
    
    public void log(String str) {
    	Bukkit.getLogger().info(str);
    }
}
