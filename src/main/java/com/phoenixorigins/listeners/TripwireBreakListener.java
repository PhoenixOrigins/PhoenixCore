package com.phoenixorigins.listeners;

import com.phoenixorigins.PhoenixCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Fix the string duplication glitch
 */
public class TripwireBreakListener implements Listener
{
	private PhoenixCore plugin;

	public TripwireBreakListener(PhoenixCore plugin)
	{
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(final BlockBreakEvent e)
	{
		if (e.getBlock().getBlockData().getMaterial() == Material.TRIPWIRE)
		{
			if (!e.isCancelled())
			{
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
				{
					public void run()
					{
						e.getBlock().setType(Material.AIR);
					}
				});
			}
		}
	}
}