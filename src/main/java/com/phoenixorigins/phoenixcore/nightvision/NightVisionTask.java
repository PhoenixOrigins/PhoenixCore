package com.phoenixorigins.phoenixcore.nightvision;

import com.phoenixorigins.phoenixcore.PhoenixCore;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NightVisionTask implements Runnable
{
	private PhoenixCore plugin;

	public NightVisionTask(PhoenixCore plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public void run()
	{
		for (Player p : plugin.nightVisionPlayers.values())
		{
			if (p.isOnline())
			{
				p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, true, false));
			}
		}
	}
}