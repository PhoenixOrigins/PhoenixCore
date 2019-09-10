package com.phoenixorigins.phoenixcore.modules.launchpure;

import com.phoenixorigins.phoenixcore.PhoenixCore;
import com.phoenixorigins.phoenixcore.config.PCLocale;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TogglelaunchpureCmd implements CommandExecutor
{
	private PhoenixCore pc;
	private Launchpure lpModule;

	public TogglelaunchpureCmd(PhoenixCore pc, Launchpure lpModule)
	{
		this.pc = pc;
		this.lpModule = lpModule;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (lpModule.isEnabled())
		{
			if (sender.hasPermission("phoenixcore.launchpure.toggle"))
			{
				if (lpModule.toggleLaunchEnabled())
				{
					sender.sendMessage(pc.msg(PCLocale.LAUNCHPURE_TOGGLE_ON, false));
				}
				else
				{
					sender.sendMessage(pc.msg(PCLocale.LAUNCHPURE_TOGGLE_OFF, false));
				}
			}
			else
			{
				sender.sendMessage(pc.msg(PCLocale.LAUNCHPURE_NO_PERMISSION_TOGGLE, false));
			}
		}
		else
		{
			sender.sendMessage(pc.msg(PCLocale.FEATURE_DISABLED, true));
		}
		return true;
	}
}