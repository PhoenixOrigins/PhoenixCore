package com.phoenixorigins.phoenixcore.commands;

import com.phoenixorigins.phoenixcore.PhoenixCore;
import com.phoenixorigins.phoenixcore.config.PCLocale;
import com.phoenixorigins.phoenixcore.config.PCSettings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PhoenixCoreCmd implements CommandExecutor
{
	private PhoenixCore pc;
	private String v, api, server;

	public PhoenixCoreCmd(PhoenixCore pc)
	{
		this.pc = pc;

		v = pc.getDescription().getVersion();
		api = pc.getDescription().getAPIVersion();
		server = pc.getMainConfig().getString(PCSettings.SERVER.path(), "undefined").toUpperCase();
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		sender.sendMessage(pc.msg(PCLocale.INFO1, false));
		sender.sendMessage(pc.msg(PCLocale.INFO2, false, v, api));
		sender.sendMessage(pc.msg(PCLocale.INFO3, false, server));
		sender.sendMessage(pc.msg(PCLocale.INFO4, false));

		return true;
	}
}