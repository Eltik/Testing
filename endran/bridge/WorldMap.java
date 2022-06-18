package endran.bridge;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class WorldMap {
	private ArrayList<Block> mapData = new ArrayList<Block>();
    private Location pos1;
    private Location pos2;
    private String mapName;
    
    private Helpers helper = new Helpers();
    
    public WorldMap(String mapName, ArrayList<Block> mapData, Location pos1, Location pos2) {
        this.mapData = mapData;
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.mapName = mapName;
        this.mapData = mapData;
    }

    public Location[] getLocations() {
    	File file = new File(mapName + "-config.txt");
    	if (!file.exists() || !file.canRead()) {
    		return null;
    	}
    	try {
    		Scanner reader = new Scanner(file);
    		Location[] locs = new Location[2];
    		while (reader.hasNextLine()) {
    			String data = reader.nextLine();
    			String[] split = data.split(", ");
    			double x = 0;
    			double x2 = 0;
    			double y = 0;
    			double y2 = 0;
    			double z = 0;
    			double z2 = 0;
    			for (String a : split) {
    				if (a.contains("pos1-x:")) {
    					String[] splitX = a.split("x: ");
    					x = Double.parseDouble(splitX[1]);
    				}
    				if (a.contains("pos1-y:")) {
    					String[] splitY = a.split("y: ");
    					y = Double.parseDouble(splitY[1]);
    				}
    				if (a.contains("pos1-z:")) {
    					String[] splitZ = a.split("z: ");
    					z = Double.parseDouble(splitZ[1].split(" }")[0]);
    				}
    				if (a.contains("pos2-x:")) {
    					String[] splitX = a.split("x: ");
    					x2 = Double.parseDouble(splitX[1]);
    				}
    				if (a.contains("pos2-y:")) {
    					String[] splitY = a.split("y: ");
    					y2 = Double.parseDouble(splitY[1]);
    				}
    				if (a.contains("pos2-z:")) {
    					String[] splitZ = a.split("z: ");
    					z2 = Double.parseDouble(splitZ[1].split(" }")[0]);
    				}
    			}
    			World world = Bukkit.getWorld(mapName);
    			Location loc = new Location(world, x, y, z);
    			Location loc2 = new Location(world, x2, y2, z2);
    			locs[0] = loc;
    			locs[1] = loc2;
    		}
    		reader.close();
    		return locs;
	    } catch (FileNotFoundException e) {
	    	System.out.println("An error occurred.");
	    	e.printStackTrace();
	    	return null;
	    }
    }
    
    public boolean mapExists() {
    	File map = new File(mapName + ".txt");
    	if (map.exists()) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public boolean saveMap() {
    	try {
        	File map = new File(mapName + ".txt");
        	File mapConfig = new File(mapName + "-config.txt");
        	if (map.createNewFile()) {
        		helper.log("Created file " + mapName + ".txt");
        		if (mapConfig.createNewFile()) {
        			FileWriter writer = new FileWriter(mapConfig);
        			try {
        				writer.write("{ pos1-x: " + pos1.getBlockX() + ", pos1-y: " + pos1.getBlockY() + ", pos1-z: " + pos1.getBlockZ() + " }");
        				writer.write("\n");
        				writer.write("{ pos2-x: " + pos2.getBlockX() + ", pos2-y: " + pos2.getBlockY() + ", pos2-z: " + pos2.getBlockZ() + " }");
        				writer.close();
        			} catch (IOException e) {
        				e.printStackTrace();
        			}
        		}
        		FileWriter writer = new FileWriter(map);
        		try {
        			for (int i = 0; i < mapData.size(); i++) {
            			writer.write(mapData.get(i).toString());
            			writer.write("\n");
        			}
        			writer.close();
            		return true;
        		} catch (IOException e) {
        			e.printStackTrace();
            		return false;
        		}
        	} else {
        		helper.log("File already exists.");
        		return false;
        	}
    	} catch (IOException e) {
    		e.printStackTrace();
    		return false;
    	}
    }
    
    public ArrayList<Block> getMap() {
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
    
    public void createMap(Location loc) {
        for (Block block : mapData) {
        	Location blockLoc = block.getLocation();
        	World world = loc.getWorld();
        	
        	Location newLoc = blockLoc.subtract(loc);
        	world.getBlockAt(newLoc).setType(block.getType());
        }
    }
}
