package ga.saverio.coordinate;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import ga.saverio.coordinate.Interpreter;
import ga.saverio.coordinate.Interpreter.Token;
import net.md_5.bungee.api.ChatColor;

public class ComputerGui implements Listener {
    private final Inventory inv;

    public ComputerGui() {
        // Create a new inventory, with no owner (as this isn't a real inventory), a size of nine, called example
        inv = Bukkit.createInventory(null, 9, "Computer");

        // Put the items into the inventory
        initializeItems();
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {
        inv.setItem(0, createGuiItem(Material.GRAY_STAINED_GLASS_PANE, "BrumbrumOS", "§aVersione 0.0.1", "§bInterprete CraftBASIC 0.0.1"));
        inv.setItem(1, createGuiItem(Material.GRAY_STAINED_GLASS_PANE, "BrumbrumOS", "§aVersione 0.0.1", "§bInterprete CraftBASIC 0.0.1"));
        inv.setItem(2, createGuiItem(Material.GRAY_STAINED_GLASS_PANE, "BrumbrumOS", "§aVersione 0.0.1", "§bInterprete CraftBASIC 0.0.1"));
        inv.setItem(3, createGuiItem(Material.GRAY_STAINED_GLASS_PANE, "BrumbrumOS", "§aVersione 0.0.1", "§bInterprete CraftBASIC 0.0.1"));
        inv.setItem(4,  null);
        inv.setItem(5, createGuiItem(Material.GRAY_STAINED_GLASS_PANE, "BrumbrumOS", "§aVersione 0.0.1", "§bInterprete CraftBASIC 0.0.1"));
        inv.setItem(6, createGuiItem(Material.GRAY_STAINED_GLASS_PANE, "BrumbrumOS", "§aVersione 0.0.1", "§bInterprete CraftBASIC 0.0.1"));
        inv.setItem(7, createGuiItem(Material.GRAY_STAINED_GLASS_PANE, "BrumbrumOS", "§aVersione 0.0.1", "§bInterprete CraftBASIC 0.0.1"));
        inv.setItem(8, createGuiItem(Material.GRAY_STAINED_GLASS_PANE, "BrumbrumOS", "§aVersione 0.0.1", "§bInterprete CraftBASIC 0.0.1"));
    }

    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Titolo dell'oggetto
        meta.setDisplayName(name);

        // Lore dell'oggetto
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    // Funzione per aprire l'inventario
    public void openInventory(final HumanEntity ent) {
        ent.openInventory(inv);
    }

    // Controllare i click dell'inventario
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
    	if (e.getInventory() == inv) {
        final ItemStack clickedItem = e.getCurrentItem();
        if (!(clickedItem.getType() == Material.WRITABLE_BOOK)) {
        	e.setCancelled(true);
        	return;
        }
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

    }
    }
    
    // Controllare i drag dell'inventario
    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
    	if (e.getInventory() == inv) {
        final ItemStack clickedItem = e.getCursor();
    	if (!(clickedItem.getType() == Material.WRITABLE_BOOK)) {
        	e.setCancelled(true);
        	return;
        }
    	}
    }
    
    // Evento di chiusura dell'inventario
    @EventHandler
    public void onUserClose(InventoryCloseEvent e) {
    	if (e.getInventory() == inv) {
    	ItemStack book = inv.getItem(4);
    	if (book.getType() == Material.WRITABLE_BOOK) {
    		BookMeta bookData = (BookMeta) book.getItemMeta();
    		Player p = (Player) e.getPlayer();
    		Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "[CraftBASIC] [Interprete fiero attivato]");
    		String data = "";
    		for (String page : bookData.getPages()) {
    			data += page;	
    		}
			Interpreter interpreter = new Interpreter();
			List<Token> list = interpreter.tokenize(data);
			for (Token tok : list) {
				Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + "[CraftBASIC Debugger] " + ChatColor.WHITE + "body: " + tok.text + " type:" + tok.type.toString());
			}
			interpreter.parser(list);
    		
    	}
    	}
    }
    
	@EventHandler
	public void onUserClick(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.PLAYER_HEAD &&
				e.getHand() == EquipmentSlot.HAND) {
			HumanEntity p = e.getPlayer();
			e.getPlayer().sendMessage("Hai cliccato sul computer");
			this.openInventory(p);
		}
	}
}