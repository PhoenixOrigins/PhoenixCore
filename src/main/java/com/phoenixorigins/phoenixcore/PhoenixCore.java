package com.phoenixorigins.phoenixcore;

import com.phoenixorigins.phoenixcore.commands.DonateCmd;
import com.phoenixorigins.phoenixcore.commands.HelpCmd;
import com.phoenixorigins.phoenixcore.commands.PhoenixCoreCmd;
import com.phoenixorigins.phoenixcore.commands.VoteCmd;
import com.phoenixorigins.phoenixcore.commands.WebsiteCmd;
import com.phoenixorigins.phoenixcore.config.PCConfig;
import com.phoenixorigins.phoenixcore.config.PCLocale;
import com.phoenixorigins.phoenixcore.config.PCSettings;
import com.phoenixorigins.phoenixcore.modules.launchpure.Launchpure;
import com.phoenixorigins.phoenixcore.modules.launchpure.LaunchpureCmd;
import com.phoenixorigins.phoenixcore.modules.launchpure.TogglelaunchpureCmd;
import com.phoenixorigins.phoenixcore.modules.nightvision.NVCmd;
import com.phoenixorigins.phoenixcore.modules.nightvision.NVListCmd;
import com.phoenixorigins.phoenixcore.modules.nightvision.NightVision;
import com.phoenixorigins.phoenixcore.modules.tripwire.Tripwire;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class PhoenixCore extends JavaPlugin
{
	private PCConfig config;

	private Launchpure lpModule;
	private NightVision nvModule;
	private Tripwire twModule;

	@Override
	public void onEnable()
	{
		/* Initialize configuration and language files */
		if (!initConfigs())
		{
			return;
		}

		initModules();
		registerCommands();

		getLogger().info("PhoenixCore loaded with following module settings:");
		getLogger().info("Launchpure: " + lpModule.isEnabled());
		getLogger().info("Night Vision: " + nvModule.isEnabled());
		getLogger().info("Paranormal Pigmen: " + "TEMP! (to-do)");
		getLogger().info("Tripwire Dupe Check: " + twModule.isEnabled());
	}

	@Override
	public void onDisable()
	{
		nvModule.onDisable();
	}

	public YamlConfiguration getMainConfig()
	{
		return config.getMain();
	}

	public String msg(PCLocale message, boolean usePrefix, String... args)
	{
		return config.getMessage(message, usePrefix, args);
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
	 * Initialize the PhoenixCore modules based on configuration values.
	 */
	private void initModules()
	{
		boolean nvEnabled = getMainConfig().getBoolean(PCSettings.NIGHT_VISION.path(), (boolean) PCSettings.NIGHT_VISION.def());
		nvModule = new NightVision(this, nvEnabled);

		boolean lpEnabled = getMainConfig().getBoolean(PCSettings.LAUNCHPURE.path(), (boolean) PCSettings.LAUNCHPURE.def());
		lpModule = new Launchpure(lpEnabled);

		boolean twEnabled = getMainConfig().getBoolean(PCSettings.TRIPWIRE_DUPE_CHECK.path(), (boolean) PCSettings.TRIPWIRE_DUPE_CHECK.def());
		twModule = new Tripwire(this, twEnabled);
	}

	/**
	 * Register all commands for the plugin.
	 */
	private void registerCommands()
	{
		getLogger().info("Registering commands...");
		getCommand("phoenixcore").setExecutor(new PhoenixCoreCmd(this));
		getCommand("donate").setExecutor(new DonateCmd(this));
		getCommand("help").setExecutor(new HelpCmd(this));
		getCommand("launchpure").setExecutor(new LaunchpureCmd(this, lpModule));
		getCommand("nv").setExecutor(new NVCmd(this, nvModule));
		getCommand("nvlist").setExecutor(new NVListCmd(this, nvModule));
		getCommand("togglelaunchpure").setExecutor(new TogglelaunchpureCmd(this, lpModule));
		getCommand("vote").setExecutor(new VoteCmd(this));
		getCommand("website").setExecutor(new WebsiteCmd(this));
	}
}