package com.phoenixorigins.phoenixcore.commands.nightvision;

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
	private PhoenixCore plugin;
	private boolean isEnabled;

	public NVCmd(PhoenixCore plugin, boolean enabled)
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

		/* Check if player */
		if (!(sender instanceof Player))
		{
			sender.sendMessage(plugin.getMessage(PCLocale.MUST_BE_PLAYER, true));
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
					if (plugin.hasNightVision(username))
					{
						player.removePotionEffect(PotionEffectType.NIGHT_VISION);
						plugin.nightVisionPlayers.remove(username);
						player.sendMessage(plugin.getMessage(PCLocale.NIGHTVISION_OFF, true));
						notify(sender, false);
					}
					else
					{
						plugin.nightVisionPlayers.put(username, player);
						player.addPotionEffect(
								new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, true, false));
						player.sendMessage(plugin.getMessage(PCLocale.NIGHTVISION_ON, true));
						notify(sender, true);
					}
					break;
				case 1:
					switch (args[0])
					{
						case "on":
							if (plugin.hasNightVision(username))
							{
								player.sendMessage(plugin.getMessage(PCLocale.NIGHTVISION_ALREADY_ON, true));
							}
							else
							{
								plugin.nightVisionPlayers.put(username, player);
								player.addPotionEffect(
										new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, true,
												false));
								player.sendMessage(plugin.getMessage(PCLocale.NIGHTVISION_ON, true));
								notify(sender, true);
							}
							break;
						case "off":
							if (!plugin.hasNightVision(username))
							{
								/* Player already does not have night vision! */
								sender.sendMessage(plugin.getMessage(PCLocale.NIGHTVISION_ALREADY_OFF, true));
							}
							else
							{
								player.removePotionEffect(PotionEffectType.NIGHT_VISION);
								plugin.nightVisionPlayers.remove(username);
								player.sendMessage(plugin.getMessage(PCLocale.NIGHTVISION_OFF, true));
								notify(sender, false);
							}
							break;
						case "commands/help":
							break;
						default:
							player.sendMessage(plugin.getMessage(PCLocale.NIGHTVISION_INVALID_SUBCOMMAND, true));
							break;
					}
					break;
				default:
					player.sendMessage(plugin.getMessage(PCLocale.NIGHTVISION_INVALID_SUBCOMMAND, true));
					break;
			}
		}
		else
		{
			sender.sendMessage(plugin.getMessage(PCLocale.NIGHTVISION_NO_PERMISSION_TOGGLE, true));
		}
		return true;
	}

	public void notify(CommandSender sender, boolean nvOn)
	{
		for (Player p : plugin.getServer().getOnlinePlayers())
		{
			if (p.hasPermission("phoenixcore.nvnotify"))
			{
				if (nvOn)
				{
					p.sendMessage(plugin.getMessage(PCLocale.NIGHTVISION_NOTIFY_ON, true, sender.getName()));
				}
				else
				{
					p.sendMessage(plugin.getMessage(PCLocale.NIGHTVISION_NOTIFY_OFF, true, sender.getName()));
				}
			}
		}
	}
}