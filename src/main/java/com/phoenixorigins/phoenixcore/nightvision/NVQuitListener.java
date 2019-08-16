package com.phoenixorigins.phoenixcore.nightvision;

import com.phoenixorigins.phoenixcore.PhoenixCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;

public class NVQuitListener implements Listener
{
	private PhoenixCore plugin;

	public NVQuitListener(PhoenixCore plugin)
	{
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e)
	{
		Player p = e.getPlayer();
		p.removePotionEffect(PotionEffectType.NIGHT_VISION);
		plugin.nightVisionPlayers.remove(p.getName());
	}
}