package com.phoenixorigins;

import com.phoenixorigins.commands.DonateCmd;
import com.phoenixorigins.commands.HelpCmd;
import com.phoenixorigins.commands.WebsiteCmd;
import com.phoenixorigins.config.PCLocale;
import com.phoenixorigins.nightvision.NVCmd;
import com.phoenixorigins.nightvision.NVListCmd;
import com.phoenixorigins.commands.PhoenixCoreCmd;
import com.phoenixorigins.commands.VoteCmd;
import com.phoenixorigins.config.PCConfig;
import com.phoenixorigins.config.PCSettings;
import com.phoenixorigins.nightvision.NVQuitListener;
import com.phoenixorigins.nightvision.NightVisionTask;
import com.phoenixorigins.tripwire.TripwireBreakListener;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class PhoenixCore extends JavaPlugin
{
	private PCConfig config;

	private boolean nvEnabled;
	private NightVisionTask task;
	public HashMap<String, Player> nightVisionPlayers;

	public PhoenixCore()
	{
		nvEnabled = false;
		task = new NightVisionTask(this);
		nightVisionPlayers = new HashMap<String, Player>();
	}

	@Override
	public void onEnable()
	{
		/* Initialize configuration and language files */
		if (!initConfigs())
		{
			return;
		}

		nvEnabled = getMainConfig()
				.getBoolean(PCSettings.NIGHT_VISION.getPath(), (boolean) PCSettings.NIGHT_VISION.getDefault());

		/* Register commands */
		if (!registerCommands())
		{
			return;
		}

		/* Tripwire listener */
		if (getMainConfig().getBoolean(PCSettings.TRIPWIRE_DUPE_CHECK.getPath(),
				(boolean) PCSettings.TRIPWIRE_DUPE_CHECK.getDefault()))
		{
			getServer().getPluginManager().registerEvents(new TripwireBreakListener(this), this);
		}

		/* Night vision listener */
		if (nvEnabled)
		{
			getServer().getPluginManager().registerEvents(new NVQuitListener(this), this);
			getServer().getScheduler().scheduleSyncRepeatingTask(this, task, 0L, 100L);
		}
	}

	@Override
	public void onDisable()
	{
		if (nvEnabled)
		{
			for (Player p : nightVisionPlayers.values())
			{
				if (p.isOnline())
				{
					p.removePotionEffect(PotionEffectType.NIGHT_VISION);
				}
			}
		}
	}

	public YamlConfiguration getMainConfig()
	{
		return config.getMain();
	}

	public String getMessage(PCLocale message, boolean usePrefix, String... args)
	{
		return config.getMessage(message, usePrefix, args);
	}

	public boolean hasNightVision(String username)
	{
		return nightVisionPlayers.get(username) != null;
	}

	/**
	 * Initialize the configuration files.
	 *
	 * @return true if the initialization was successful, false if there were errors.
	 */
	private boolean initConfigs()
	{
		getLogger().info("Initializing configuration files...");
		config = new PCConfig(this);
		return config.generateConfigs();
	}

	/**
	 * Register all commands for the PhoenixCore plugin.
	 *
	 * @return true if registering was successful, false if there were errors.
	 */
	private boolean registerCommands()
	{
		getLogger().info("Registering commands...");
		try
		{
			getCommand("phoenixcore").setExecutor(new PhoenixCoreCmd(this));
			getCommand("donate").setExecutor(new DonateCmd(this));
			getCommand("help").setExecutor(new HelpCmd(this));
			getCommand("nv").setExecutor(new NVCmd(this, nvEnabled));
			getCommand("nvlist").setExecutor(new NVListCmd(this, nvEnabled));
			getCommand("vote").setExecutor(new VoteCmd(this));
			getCommand("website").setExecutor(new WebsiteCmd(this));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
}