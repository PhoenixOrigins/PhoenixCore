package com.phoenixorigins.phoenixcore.commands;

import com.phoenixorigins.phoenixcore.PhoenixCore;
import com.phoenixorigins.phoenixcore.config.PCLocale;
import com.phoenixorigins.phoenixcore.config.PCSettings;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DiscordCmd implements CommandExecutor
{
	private PhoenixCore pc;
	private int status;

	public DiscordCmd(PhoenixCore pc)
	{
		this.pc = pc;
		status = pc.getMainConfig().getInt(PCSettings.COMMANDS_DISCORD.path(), (int) PCSettings.COMMANDS_DISCORD.def());
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		switch (status)
		{
			case 0:
				sender.sendMessage(pc.msg(PCLocale.COMMAND_DISABLED, true));
				break;
			case 1:
				sender.sendMessage(pc.msg(PCLocale.UNKNOWN_COMMAND, false));
				break;
			default:
				String msg = pc.getResources().getSection("discord", pc.getConfigServer(), "(((base)))");
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
				break;
		}
		return true;
	}
}