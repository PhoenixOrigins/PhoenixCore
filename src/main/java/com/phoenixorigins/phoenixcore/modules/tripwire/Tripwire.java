package com.phoenixorigins.phoenixcore.modules.tripwire;

import com.phoenixorigins.phoenixcore.PhoenixCore;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class Tripwire implements Listener
{
	private PhoenixCore pc;
	private boolean enabled;

	public Tripwire(PhoenixCore pc, boolean enabled)
	{
		this.pc = pc;
		this.enabled = enabled;

		if (enabled)
		{
			pc.getServer().getPluginManager().registerEvents(this, pc);
		}
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onBlockBreak(final BlockBreakEvent e)
	{
		if (e.getBlock().getBlockData().getMaterial() == Material.TRIPWIRE)
		{
			if (!e.isCancelled())
			{
				pc.getServer().getScheduler().scheduleSyncDelayedTask(pc, () -> e.getBlock().setType(Material.AIR));
			}
		}
	}
}