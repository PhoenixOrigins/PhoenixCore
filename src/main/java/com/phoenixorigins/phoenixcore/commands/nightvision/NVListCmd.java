package com.phoenixorigins.phoenixcore.commands.nightvision;

import com.phoenixorigins.phoenixcore.PhoenixCore;
import com.phoenixorigins.phoenixcore.config.PCLocale;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Iterator;

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

		/* Permission */
		if (!sender.hasPermission("phoenixcore.nvlist"))
		{
			sender.sendMessage(plugin.getMessage(PCLocale.NIGHTVISION_NO_PERMISSION_LIST, true));
			return true;
		}

		/* List is empty */
		if (plugin.nightVisionPlayers.isEmpty())
		{
			sender.sendMessage(plugin.getMessage(PCLocale.NIGHTVISION_NO_PLAYERS, true));
		}
		else
		{
			sender.sendMessage(plugin.getMessage(PCLocale.NIGHTVISION_LIST, true));

			String list = "";
			Iterator<String> it = plugin.nightVisionPlayers.keySet().iterator();
			do
			{
				list = list.concat(it.next());
				if (it.hasNext())
				{
					/* Append comma and space */
					list = list.concat(", ");
				}
			}
			while (it.hasNext());
			sender.sendMessage(plugin.getMessage(PCLocale.NIGHTVISION_PLAYERS, false, list));
		}

		return true;
	}
}