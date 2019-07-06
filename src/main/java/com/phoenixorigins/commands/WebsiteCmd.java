package com.phoenixorigins.commands;

import com.phoenixorigins.PhoenixCore;
import com.phoenixorigins.config.PCLocale;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class WebsiteCmd implements CommandExecutor
{
	private PhoenixCore plugin;

	public WebsiteCmd(PhoenixCore plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		sender.sendMessage(plugin.getMessage(PCLocale.FEATURE_NOT_IMPLEMENTED, true));
		return true;
	}
}
