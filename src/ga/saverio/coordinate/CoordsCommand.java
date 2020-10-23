package ga.saverio.coordinate;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoordsCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		int x = 0, y = 0, z= 0;
		Environment mondo = null;
		String error;
		Player selezionato = Bukkit.getPlayer(args[0]);
		
		if ((selezionato != null) && selezionato.isOnline()) {
			Location playerLocation = selezionato.getLocation();
			x = playerLocation.getBlockX();
			y = playerLocation.getBlockY();
			z = playerLocation.getBlockZ();
			mondo = playerLocation.getWorld().getEnvironment();
			error = null;
		} else {
			error = "Giocatore non esistente o non online";
		}
		
		if (sender instanceof Player) {
			Player mittente = (Player)sender;
			if (error == null) {
				mittente.sendMessage(ChatColor.RED + "Coordinate del giocatore: " + ChatColor.WHITE + x + " | " + y + " | " + z + " | " + ChatColor.GREEN + "Mondo: " + ChatColor.WHITE + mondo);
			} else {
				mittente.sendMessage(error);
			}
		} else {
			if (error == null) {
				Bukkit.getConsoleSender().sendMessage("[PLUGIN COORDINATE] " + ChatColor.RED + "Coordinate del giocatore: " + ChatColor.WHITE + x + " | " + y + " | " + z + " | " + ChatColor.GREEN + "Mondo: " + ChatColor.WHITE + mondo);
			} else {
				Bukkit.getConsoleSender().sendMessage("[PLUGIN COORDINATE] " + error);
			}
		}
		return true;
	}
	
}

