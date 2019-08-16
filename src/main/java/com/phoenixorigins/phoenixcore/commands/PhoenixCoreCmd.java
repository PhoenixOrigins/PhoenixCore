package com.phoenixorigins.phoenixcore.commands;

import com.phoenixorigins.phoenixcore.PhoenixCore;
import com.phoenixorigins.phoenixcore.config.PCSettings;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PhoenixCoreCmd implements CommandExecutor
{
	private String main1, main2, main3;

	public PhoenixCoreCmd(PhoenixCore plugin)
	{
		main1 = "&c&lPhoenix&fCore";
		main2 = "&fv" + plugin.getDescription().getVersion() + " for MC " + plugin.getDescription().getAPIVersion();
		main3 = "&fRunning on &3" + plugin.getMainConfig().getString(PCSettings.SERVER.getPath(), "undefined")
				.toUpperCase() + "&f server.";
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main1));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main2));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main3));

		return true;
	}
}