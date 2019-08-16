package com.phoenixorigins.phoenixcore.commands;

import com.phoenixorigins.phoenixcore.PhoenixCore;
import com.phoenixorigins.phoenixcore.config.PCLocale;
import com.phoenixorigins.phoenixcore.config.PCSettings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HelpCmd implements CommandExecutor
{
	private PhoenixCore plugin;

	public HelpCmd(PhoenixCore plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		sender.sendMessage(plugin.getMessage(PCLocale.FEATURE_NOT_IMPLEMENTED, true));
		if (sender.hasPermission("phoenixcore.help"))
		{
			String server = plugin.getMainConfig()
					.getString(PCSettings.SERVER.getPath(), (String) PCSettings.SERVER.getDefault());
		}
		return true;
	}
}