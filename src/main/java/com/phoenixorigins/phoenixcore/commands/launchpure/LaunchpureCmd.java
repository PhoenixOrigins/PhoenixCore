package com.phoenixorigins.phoenixcore.commands.launchpure;

import com.phoenixorigins.phoenixcore.PhoenixCore;
import com.phoenixorigins.phoenixcore.config.PCLocale;
import com.phoenixorigins.phoenixcore.launchpure.Launchpure;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class LaunchpureCmd implements CommandExecutor
{
	private PhoenixCore plugin;
	private Launchpure module;

	public LaunchpureCmd(PhoenixCore plugin, Launchpure module)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (module.isConfigEnabled())
		{
			if (module.launchEnabled || sender.hasPermission("phoenixcore.launchpure.launch"))
			{
				if (plugin.getServer().getPlayer("purejays") != null)
				{
					Player pure = plugin.getServer().getPlayer("purejays");
					if (pure.getLocation().getY() < 300)
					{
						pure.setVelocity(new Vector(0, 3.5, 0));
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&oThere he goes..."));
						Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&f" + sender.getName() + " &7&olaunched pure!"));
					}
					else
					{
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&7&oYou can't launch pure when he's already in space!"));
					}
				}
				else
				{
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&oLaunch who?"));
				}
			}
			else
			{
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&oYou cannot do this now..."));
			}
		}
		else
		{
			sender.sendMessage(plugin.getMessage(PCLocale.FEATURE_DISABLED, true));
		}
		return true;
	}
}