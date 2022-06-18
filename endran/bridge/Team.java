package endran.bridge;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Team {
    ArrayList<Player> players = new ArrayList<Player>();
    public Team(ArrayList<Player> players) {
        this.players = players;
    }

    public void teleport(Location loc) {
        for (Player player : players) {
            player.teleport(loc);
        }
    }

    public void giveKit() {
        for (Player player : players) {
            player.getInventory().clear();
            // Give kit to player
        }
    }
}
