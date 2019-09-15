package com.phoenixorigins.phoenixcore.modules.tripwire;

import com.phoenixorigins.phoenixcore.PhoenixCore;
import com.phoenixorigins.phoenixcore.modules.PCModule;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class Tripwire extends PCModule implements Listener
{
	private PhoenixCore pc;

	public Tripwire(PhoenixCore pc, boolean enabled)
	{
		super(enabled);
		this.pc = pc;

		if (enabled)
		{
			pc.getServer().getPluginManager().registerEvents(this, pc);
		}
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