package com.phoenixorigins.phoenixcore.modules.nightvision;

import com.phoenixorigins.phoenixcore.PhoenixCore;
import com.phoenixorigins.phoenixcore.modules.PCModule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class NightVision extends PCModule implements Runnable, Listener
{
	private HashMap<String, Player> nvPlayers;

	public NightVision(PhoenixCore pc, boolean enabled)
	{
		super(enabled);

		if (enabled)
		{
			nvPlayers = new HashMap<String, Player>();
			pc.getServer().getPluginManager().registerEvents(this, pc);
			pc.getServer().getScheduler().scheduleSyncRepeatingTask(pc, this, 0L, 100L);
		}
	}

	boolean hasNightVision(String username)
	{
		return nvPlayers.get(username) != null;
	}

	HashMap<String, Player> getPlayers()
	{
		return nvPlayers;
	}

	public void onDisable()
	{
		if (enabled)
		{
			for (Player p : nvPlayers.values())
			{
				if (p.isOnline())
				{
					p.removePotionEffect(PotionEffectType.NIGHT_VISION);
				}
			}
		}
	}

	@Override
	public void run()
	{
		for (Player p : nvPlayers.values())
		{
			if (p.isOnline())
			{
				p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, true, false));
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent e)
	{
		Player p = e.getPlayer();
		p.removePotionEffect(PotionEffectType.NIGHT_VISION);
		nvPlayers.remove(p.getName());
	}
}