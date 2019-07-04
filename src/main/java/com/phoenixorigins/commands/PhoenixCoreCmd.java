package com.phoenixorigins.commands;

import com.phoenixorigins.PhoenixCore;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PhoenixCoreCmd implements CommandExecutor
{
	private String main1, main2;

	public PhoenixCoreCmd(PhoenixCore plugin)
	{
		main1 = "&4&lPhoenix&f&lCore&b by jstnf";
		main2 = "&fVersion: " + plugin.getDescription().getVersion() + " for MC" + plugin.getDescription()
				.getAPIVersion();
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main1));
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main2));

		return true;
	}
}