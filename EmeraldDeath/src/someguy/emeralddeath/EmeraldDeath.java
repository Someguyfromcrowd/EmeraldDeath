package someguy.emeralddeath;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Someguyfromcrowd
 *
 */
public class EmeraldDeath extends JavaPlugin implements Listener
{
	private int baseEmeralds;
	private int emeraldsPerLevel;
	private int stackSize;
	
	@Override
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		this.saveDefaultConfig();
		baseEmeralds = this.getConfig().getInt("baseEmeralds");
		if (baseEmeralds < 0)
		{
			this.getConfig().set("baseEmeralds", 0);
			baseEmeralds = 0;
		}
		emeraldsPerLevel = this.getConfig().getInt("emeraldsPerLevel");
		if (emeraldsPerLevel < 0)
		{
			this.getConfig().set("emeraldsPerLevel", 0);
			emeraldsPerLevel = 0;
		}
		stackSize = this.getConfig().getInt("stackSize");
		if (stackSize <= 0)
		{
			this.getConfig().set("stackSize", 1);
			stackSize = 1;
		}
		if (stackSize > 64)
		{
			this.getConfig().set("stackSize", 64);
			stackSize = 64;
		}
		this.saveConfig();
		System.out.println(emeraldsPerLevel + " emeralds");
	}
	
	/**
	 * @param event
	 */
	@EventHandler (priority = EventPriority.MONITOR)
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		int level = event.getEntity().getLevel();
		
		
		int emeralds = level * emeraldsPerLevel + baseEmeralds;
		
		System.out.println("Spawning : " + emeralds);
		
		while (emeralds > 0)
		{
			if (emeralds > stackSize)
			{
				emeralds-=stackSize;
				event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), new ItemStack(Material.EMERALD,stackSize));
			}
			else
			{
				event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), new ItemStack(Material.EMERALD,emeralds));
				emeralds=0;
			}
		}
	}
}
