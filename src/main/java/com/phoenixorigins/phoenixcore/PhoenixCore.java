package com.phoenixorigins.phoenixcore;

import com.phoenixorigins.phoenixcore.commands.DonateCmd;
import com.phoenixorigins.phoenixcore.commands.HelpCmd;
import com.phoenixorigins.phoenixcore.commands.WebsiteCmd;
import com.phoenixorigins.phoenixcore.commands.launchpure.LaunchpureCmd;
import com.phoenixorigins.phoenixcore.commands.launchpure.TogglelaunchpureCmd;
import com.phoenixorigins.phoenixcore.config.PCLocale;
import com.phoenixorigins.phoenixcore.commands.nightvision.NVCmd;
import com.phoenixorigins.phoenixcore.commands.nightvision.NVListCmd;
import com.phoenixorigins.phoenixcore.commands.PhoenixCoreCmd;
import com.phoenixorigins.phoenixcore.commands.VoteCmd;
import com.phoenixorigins.phoenixcore.config.PCConfig;
import com.phoenixorigins.phoenixcore.config.PCSettings;
import com.phoenixorigins.phoenixcore.launchpure.Launchpure;
import com.phoenixorigins.phoenixcore.nightvision.NVQuitListener;
import com.phoenixorigins.phoenixcore.nightvision.NightVisionTask;
import com.phoenixorigins.phoenixcore.tripwire.TripwireBreakListener;
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

	private Launchpure module;

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

		/* launchpure */
		boolean launchpureEnabled = getMainConfig().getBoolean(PCSettings.LAUNCHPURE.getPath(), (boolean) PCSettings.LAUNCHPURE.getDefault());
		module = new Launchpure(launchpureEnabled);

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
			getCommand("commands/donate").setExecutor(new DonateCmd(this));
			getCommand("commands/help").setExecutor(new HelpCmd(this));
			getCommand("launchpure").setExecutor(new LaunchpureCmd(this, module));
			getCommand("nv").setExecutor(new NVCmd(this, nvEnabled));
			getCommand("nvlist").setExecutor(new NVListCmd(this, nvEnabled));
			getCommand("togglelaunchpure").setExecutor(new TogglelaunchpureCmd(module));
			getCommand("commands/vote").setExecutor(new VoteCmd(this));
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