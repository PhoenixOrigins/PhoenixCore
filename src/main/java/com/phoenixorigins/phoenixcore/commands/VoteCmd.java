package com.phoenixorigins.phoenixcore.commands;

import com.phoenixorigins.phoenixcore.PhoenixCore;
import com.phoenixorigins.phoenixcore.config.PCLocale;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class VoteCmd implements CommandExecutor
{
	private PhoenixCore plugin;

	public VoteCmd(PhoenixCore plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		sender.sendMessage(plugin.msg(PCLocale.FEATURE_NOT_IMPLEMENTED, true));
		return true;
	}
}