package com.phoenixorigins.phoenixcore.modules.nightvision;

import com.phoenixorigins.phoenixcore.PhoenixCore;
import com.phoenixorigins.phoenixcore.config.PCLocale;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Iterator;

public class NVListCmd implements CommandExecutor
{
	private PhoenixCore pc;
	private NightVision nvModule;

	public NVListCmd(PhoenixCore plugin, NightVision nvModule)
	{
		this.pc = plugin;
		this.nvModule = nvModule;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		/* Check if enabled */
		if (!nvModule.isEnabled())
		{
			sender.sendMessage(pc.msg(PCLocale.FEATURE_DISABLED, true));
			return true;
		}

		/* Permission */
		if (!sender.hasPermission("phoenixcore.nvlist"))
		{
			sender.sendMessage(pc.msg(PCLocale.NIGHTVISION_NO_PERMISSION_LIST, true));
			return true;
		}

		/* List is empty */
		if (nvModule.getPlayers().isEmpty())
		{
			sender.sendMessage(pc.msg(PCLocale.NIGHTVISION_NO_PLAYERS, true));
		}
		else
		{
			sender.sendMessage(pc.msg(PCLocale.NIGHTVISION_LIST, true));

			String list = "";
			Iterator<String> it = nvModule.getPlayers().keySet().iterator();
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
			sender.sendMessage(pc.msg(PCLocale.NIGHTVISION_PLAYERS, false, list));
		}

		return true;
	}
}