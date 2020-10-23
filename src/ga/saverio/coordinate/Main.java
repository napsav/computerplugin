package ga.saverio.coordinate;

import java.util.ArrayList;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import com.github.crashdemons.playerheads.api.PlayerHeads;
import com.github.crashdemons.playerheads.api.PlayerHeadsAPI;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.SkullMeta;

public class Main extends JavaPlugin {
    private boolean PHEnabled=false;
    public boolean hasPlayerheads(){return PHEnabled;} //quick later check
    private boolean isPlayerHeadsLoaded(){ //slower, first-time check
        return (this.getServer().getPluginManager().getPlugin("PlayerHeads") != null);
    }
	public ItemStack getComputerHead() {
		PlayerHeadsAPI ph_api = PlayerHeads.getApiInstance();
		UUID id = UUID.fromString("27c25c0b-77e6-47d7-9f6b-f9579e1fe576");
		OfflinePlayer player = Bukkit.getOfflinePlayer(id);
        ItemStack item = ph_api.getHeadItem("zasf", 1, false);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.setDisplayName("Computer");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("Computer epico");
        skull.setLore(lore);
        item.setItemMeta(skull);

        return item;
    }
	@Override
	public void onEnable() {
//		getServer().getPluginManager().registerEvents(new ClickListener(), this);
		getServer().getPluginManager().registerEvents(new ComputerGui(), this);
        PHEnabled=isPlayerHeadsLoaded();
		//...
        if(hasPlayerheads()){
            getLogger().info("PlayerHeads support detected.");
			//do PlayerHeads related things here, register listeners, etc.
        }
        getLogger().info("PH Enabled.");
		getLogger().info("Plugin Coordinate attivato con successo!");
		this.getCommand("c").setExecutor(new CoordsCommand());
		ItemStack item = getComputerHead();
		NamespacedKey key = new NamespacedKey(this, "computer");
		ShapedRecipe recipe = new ShapedRecipe(key, item);
		recipe.shape("GGG", "SES", "SSS");
        recipe.setIngredient('E', Material.REDSTONE);
        recipe.setIngredient('S', Material.STONE);
        recipe.setIngredient('G', Material.GLASS);
        Bukkit.addRecipe(recipe);
	}
	
	@Override
	public void onDisable() {
//		HandlerList.unregisterAll(ClickListener);
		
	}

	

	
	
}
