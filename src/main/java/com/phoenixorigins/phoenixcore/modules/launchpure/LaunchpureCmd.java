package com.phoenixorigins.phoenixcore.modules.launchpure;

import com.phoenixorigins.phoenixcore.PhoenixCore;
import com.phoenixorigins.phoenixcore.config.PCLocale;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class LaunchpureCmd implements CommandExecutor
{
	private PhoenixCore pc;
	private Launchpure lpModule;

	public LaunchpureCmd(PhoenixCore pc, Launchpure lpModule)
	{
		this.pc = pc;
		this.lpModule = lpModule;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (lpModule.isEnabled())
		{
			if (lpModule.isLaunchEnabled() || sender.hasPermission("phoenixcore.launchpure.launch"))
			{
				if (pc.getServer().getPlayer("purejays") != null)
				{
					Player pure = pc.getServer().getPlayer("purejays");
					if (pure.getLocation().getY() < 300)
					{
						pure.setVelocity(new Vector(0, 3.5, 0));
						sender.sendMessage(pc.msg(PCLocale.LAUNCHPURE_SUCCESS, false));
						Bukkit.broadcastMessage(pc.msg(PCLocale.LAUNCHPURE_NOTIFICATION, false, sender.getName()));
					}
					else
					{
						sender.sendMessage(pc.msg(PCLocale.LAUNCHPURE_TOO_HIGH, false));
					}
				}
				else
				{
					sender.sendMessage(pc.msg(PCLocale.LAUNCHPURE_NOT_ONLINE, false));
				}
			}
			else
			{
				sender.sendMessage(pc.msg(PCLocale.LAUNCHPURE_NO_PERMISSION_LAUNCH, false));
			}
		}
		else
		{
			sender.sendMessage(pc.msg(PCLocale.FEATURE_DISABLED, true));
		}
		return true;
	}
}