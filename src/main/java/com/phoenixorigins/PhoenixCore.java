package com.phoenixorigins;

import com.phoenixorigins.commands.PhoenixCoreCmd;
import com.phoenixorigins.listeners.TripwireBreakListener;
import org.bukkit.plugin.java.JavaPlugin;

public class PhoenixCore extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		getCommand("phoenixcore").setExecutor(new PhoenixCoreCmd(this));
		getServer().getPluginManager().registerEvents(new TripwireBreakListener(this), this);
	}

	@Override
	public void onDisable()
	{

	}
}