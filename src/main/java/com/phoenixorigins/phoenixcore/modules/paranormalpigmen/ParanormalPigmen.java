package com.phoenixorigins.phoenixcore.modules.paranormalpigmen;

import com.phoenixorigins.phoenixcore.PhoenixCore;
import com.phoenixorigins.phoenixcore.modules.PCModule;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class ParanormalPigmen extends PCModule implements Listener
{
	public ParanormalPigmen(PhoenixCore pc, boolean enabled)
	{
		super(enabled);

		if (enabled)
		{
			pc.getServer().getPluginManager().registerEvents(this, pc);
		}
	}

	@EventHandler
	public void onPigmenSpawn(CreatureSpawnEvent e)
	{
		if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NETHER_PORTAL && e.getEntityType() == EntityType.PIG_ZOMBIE)
		{
			e.getEntity().setCollidable(false);
		}
	}
}