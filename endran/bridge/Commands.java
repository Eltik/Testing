package endran.bridge;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
	
	App app = App.getPlugin(App.class);
	
	public Commands(App app) {
		this.app = app;
		Bukkit.getPluginCommand("setmap").setExecutor(this);
		Bukkit.getPluginCommand("createmap").setExecutor(this);
		Bukkit.getPluginCommand("test").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to access this command!");
			return false;
		}
		Player player = (Player) sender;
		String prefix = ChatColor.WHITE + "[" + ChatColor.RED + "Br" + ChatColor.BLUE + "id" + ChatColor.RED + "ge" + ChatColor.WHITE + "]" + ChatColor.RESET + " ";
		Helpers helper = new Helpers();
		
		if (cmd.getName().equalsIgnoreCase("setmap")) {
			if (args.length == 2) {
				if (!helper.isInt(args[0])) {
					player.sendMessage(prefix + ChatColor.RED + args[0] + " is not a valid argument!");
					return false;
				}
				int radius = Integer.parseInt(args[0]);
				String mapName = args[1];
				
				player.sendMessage(prefix + ChatColor.YELLOW + "Setting the map for location " + ChatColor.AQUA + player.getLocation().toString() + ChatColor.YELLOW + " with a radius of " + ChatColor.AQUA + radius);
				ArrayList<Block> list = helper.getBlocks(new Block(player.getLocation(), player.getLocation().getBlock()), radius);
				
				WorldMap map = new WorldMap(mapName, list, player.getLocation().multiply(2), player.getLocation());
				if (map.saveMap()) {
					player.sendMessage(prefix + ChatColor.GREEN + "Successfully set the map.");
				} else {
					player.sendMessage(prefix + ChatColor.RED + "Couldn't set the map.");
				}
			} else {
				player.sendMessage(prefix + ChatColor.RED + "Incorrect usage. /setmap <radius> <map_name>");
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("createmap")) {
			if (args.length == 1) {
				ArrayList<Block> mapData = helper.getMap(args[0]);
				helper.createMap(player.getLocation(), mapData);
				player.sendMessage(prefix + "Created map.");
			} else {
				player.sendMessage(prefix + ChatColor.RED + "Incorrect usage. /createmap <map_name>");
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("test")) {
			if (args.length == 2) {
				String mapName = args[0];
				Player target = Bukkit.getPlayer(args[1]);
				if (target.isOnline()) {
					ArrayList<Player> firstTeam = new ArrayList<Player>();
					firstTeam.add(target);
					ArrayList<Player> secondTeam = new ArrayList<Player>();
					secondTeam.add(player);
					
					Team t1 = new Team(firstTeam);
					Team t2 = new Team(secondTeam);
					Game game = new Game(t1, t2);
					ArrayList<Block> mapData = helper.getMap(mapName);
					WorldMap map = new WorldMap(mapName, mapData, null, null);
					game.startGame(map, player.getLocation());
				} else {
					player.sendMessage(prefix + ChatColor.RED + "That player isn't online.");
				}
			} else {
				player.sendMessage(prefix + ChatColor.RED + "Incorrect usage. /test <map_name> <player>");
			}
		}
		
		
		return false;
	}

}
