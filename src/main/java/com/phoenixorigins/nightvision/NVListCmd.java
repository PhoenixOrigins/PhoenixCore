package com.phoenixorigins.nightvision;

import com.phoenixorigins.PhoenixCore;
import com.phoenixorigins.config.PCLocale;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class NVListCmd implements CommandExecutor
{
	private PhoenixCore plugin;
	private boolean isEnabled;

	public NVListCmd(PhoenixCore plugin, boolean enabled)
	{
		this.plugin = plugin;
		isEnabled = enabled;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		/* Check if enabled */
		if (!isEnabled)
		{
			sender.sendMessage(plugin.getMessage(PCLocale.FEATURE_DISABLED, true));
			return true;
		}

		sender.sendMessage(plugin.getMessage(PCLocale.FEATURE_NOT_IMPLEMENTED, true));
		return true;
	}
}