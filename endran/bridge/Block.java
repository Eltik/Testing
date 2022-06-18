package endran.bridge;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class Block {
	private org.bukkit.block.Block block;
	private Material type;
	private Location offset;
	public Block(Location offset, org.bukkit.block.Block block) {
		this.block = block;
		this.type = block.getType();
		this.offset = offset;
	}
	
	public Block(Location offset, String type) {
		this.offset = offset;
		this.type = Material.getMaterial(type);
	}

	public Location getLocation() {
		return this.offset;
	}
	
	public Material getType() {
		return this.type;
	}
	
	public World getWorld() {
		return this.getLocation().getWorld();
	}
	
	public String toString() {
		double x = offset.getBlockX();
		double y = offset.getY();
		double z = offset.getZ();
		Material type = this.getType();
		return "{ x: " + x + ", y: " + y + ", z: " + z + ", type: " + type + ", world: " + this.offset.getWorld().getName() + " }";
	}
}
