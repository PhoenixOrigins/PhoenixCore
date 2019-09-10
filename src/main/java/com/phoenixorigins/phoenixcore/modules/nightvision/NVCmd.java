package com.phoenixorigins.phoenixcore.modules.nightvision;

import com.phoenixorigins.phoenixcore.PhoenixCore;
import com.phoenixorigins.phoenixcore.config.PCLocale;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NVCmd implements CommandExecutor
{
	private PhoenixCore pc;
	private NightVision nvModule;

	public NVCmd(PhoenixCore pc, NightVision nvModule)
	{
		this.pc = pc;
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

		/* Check if player */
		if (!(sender instanceof Player))
		{
			sender.sendMessage(pc.msg(PCLocale.MUST_BE_PLAYER, true));
			return true;
		}

		/* Check if player has permission */
		if (sender.hasPermission("phoenixcore.nv"))
		{
			Player player = (Player) sender;
			String username = sender.getName();

			switch (args.length)
			{
				case 0:
					if (nvModule.hasNightVision(username))
					{
						player.removePotionEffect(PotionEffectType.NIGHT_VISION);
						nvModule.getPlayers().remove(username);
						player.sendMessage(pc.msg(PCLocale.NIGHTVISION_OFF, true));
						notify(sender, false);
					}
					else
					{
						nvModule.getPlayers().put(username, player);
						player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, true, false));
						player.sendMessage(pc.msg(PCLocale.NIGHTVISION_ON, true));
						notify(sender, true);
					}
					break;
				case 1:
					switch (args[0])
					{
						case "on":
							if (nvModule.hasNightVision(username))
							{
								player.sendMessage(pc.msg(PCLocale.NIGHTVISION_ALREADY_ON, true));
							}
							else
							{
								nvModule.getPlayers().put(username, player);
								player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, true, false));
								player.sendMessage(pc.msg(PCLocale.NIGHTVISION_ON, true));
								notify(sender, true);
							}
							break;
						case "off":
							if (!nvModule.hasNightVision(username))
							{
								/* Player already does not have night vision! */
								sender.sendMessage(pc.msg(PCLocale.NIGHTVISION_ALREADY_OFF, true));
							}
							else
							{
								player.removePotionEffect(PotionEffectType.NIGHT_VISION);
								nvModule.getPlayers().remove(username);
								player.sendMessage(pc.msg(PCLocale.NIGHTVISION_OFF, true));
								notify(sender, false);
							}
							break;
						case "help":
							break;
						default:
							player.sendMessage(pc.msg(PCLocale.NIGHTVISION_INVALID_SUBCOMMAND, true));
							break;
					}
					break;
				default:
					player.sendMessage(pc.msg(PCLocale.NIGHTVISION_INVALID_SUBCOMMAND, true));
					break;
			}
		}
		else
		{
			sender.sendMessage(pc.msg(PCLocale.NIGHTVISION_NO_PERMISSION_TOGGLE, true));
		}
		return true;
	}

	public void notify(CommandSender sender, boolean nvOn)
	{
		for (Player p : pc.getServer().getOnlinePlayers())
		{
			if (p.hasPermission("phoenixcore.nvnotify"))
			{
				if (nvOn)
				{
					p.sendMessage(pc.msg(PCLocale.NIGHTVISION_NOTIFY_ON, true, sender.getName()));
				}
				else
				{
					p.sendMessage(pc.msg(PCLocale.NIGHTVISION_NOTIFY_OFF, true, sender.getName()));
				}
			}
		}
	}
}