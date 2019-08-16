package com.phoenixorigins.phoenixcore.commands.launchpure;

import com.phoenixorigins.phoenixcore.PhoenixCore;
import com.phoenixorigins.phoenixcore.config.PCLocale;
import com.phoenixorigins.phoenixcore.launchpure.Launchpure;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TogglelaunchpureCmd implements CommandExecutor
{
	private PhoenixCore plugin;
	private Launchpure module;

	public TogglelaunchpureCmd(PhoenixCore plugin, Launchpure module)
	{
		this.plugin = plugin;
		this.module = module;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (module.isConfigEnabled())
		{
			if (sender.hasPermission("phoenixcore.launchpure.toggle"))
			{
				module.launchEnabled = !module.launchEnabled;
				if (module.launchEnabled)
				{
					sender.sendMessage("/launchpure is now accessible to all players.");
				}
				else
				{
					sender.sendMessage("/launchpure is now disabled for all players.");
				}
			}
			else
			{
				sender.sendMessage("You do not have permission to do this.");
			}
		}
		else
		{
			sender.sendMessage(plugin.getMessage(PCLocale.FEATURE_DISABLED, true));
		}
		return true;
	}
}