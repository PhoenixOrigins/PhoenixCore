package com.phoenixorigins.phoenixcore;

import com.phoenixorigins.phoenixcore.commands.DiscordCmd;
import com.phoenixorigins.phoenixcore.commands.DonateCmd;
import com.phoenixorigins.phoenixcore.commands.HelpCmd;
import com.phoenixorigins.phoenixcore.commands.PhoenixCoreCmd;
import com.phoenixorigins.phoenixcore.commands.VoteCmd;
import com.phoenixorigins.phoenixcore.commands.WebsiteCmd;
import com.phoenixorigins.phoenixcore.config.PCConfig;
import com.phoenixorigins.phoenixcore.config.PCLocale;
import com.phoenixorigins.phoenixcore.config.PCResources;
import com.phoenixorigins.phoenixcore.config.PCSettings;
import com.phoenixorigins.phoenixcore.modules.launchpure.Launchpure;
import com.phoenixorigins.phoenixcore.modules.launchpure.LaunchpureCmd;
import com.phoenixorigins.phoenixcore.modules.launchpure.TogglelaunchpureCmd;
import com.phoenixorigins.phoenixcore.modules.nightvision.NVCmd;
import com.phoenixorigins.phoenixcore.modules.nightvision.NVListCmd;
import com.phoenixorigins.phoenixcore.modules.nightvision.NightVision;
import com.phoenixorigins.phoenixcore.modules.paranormalpigmen.ParanormalPigmen;
import com.phoenixorigins.phoenixcore.modules.rainingblocks.RainingBlocks;
import com.phoenixorigins.phoenixcore.modules.tripwire.Tripwire;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class PhoenixCore extends JavaPlugin
{
	private PCConfig config;
	private PCResources resources;

	private String server;

	private Launchpure lpModule;
	private NightVision nvModule;
	private ParanormalPigmen ppModule;
	private RainingBlocks rbModule;
	private Tripwire twModule;

	@Override
	public void onEnable()
	{
		/* Initialize configuration and language files */
		if (!initConfigs())
		{
			return;
		}

		server = getMainConfig().getString(PCSettings.SERVER.path(), (String) PCSettings.SERVER.def());

		initModules();
		resources = new PCResources(this);
		resources.generateFiles();
		registerCommands();

		getLogger().info("PhoenixCore loaded with following module settings:");
		getLogger().info("Commands:");
		getLogger().info("/discord - " + getCmdStatusFromValue(getMainConfig().getInt(PCSettings.COMMANDS_DISCORD.path(), (int) PCSettings.COMMANDS_DISCORD.def())));
		getLogger().info("/donate - " + getCmdStatusFromValue(getMainConfig().getInt(PCSettings.COMMANDS_DONATE.path(), (int) PCSettings.COMMANDS_DONATE.def())));
		getLogger().info("/help - " + getCmdStatusFromValue(getMainConfig().getInt(PCSettings.COMMANDS_HELP.path(), (int) PCSettings.COMMANDS_HELP.def())));
		getLogger().info("/vote - " + getCmdStatusFromValue(getMainConfig().getInt(PCSettings.COMMANDS_VOTE.path(), (int) PCSettings.COMMANDS_VOTE.def())));
		getLogger().info("/website - " + getCmdStatusFromValue(getMainConfig().getInt(PCSettings.COMMANDS_WEBSITE.path(), (int) PCSettings.COMMANDS_WEBSITE.def())));
		getLogger().info("Modules:");
		getLogger().info("Launchpure: " + lpModule.isEnabled());
		getLogger().info("Night Vision: " + nvModule.isEnabled());
		getLogger().info("Paranormal Pigmen: " + ppModule.isEnabled());
		getLogger().info("RainingBlocks: " + rbModule.isEnabled());
		getLogger().info("  - List settings here (to-do)");
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

	public PCResources getResources()
	{
		return resources;
	}

	public String getConfigServer()
	{
		return server;
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

		boolean ppEnabled = getMainConfig().getBoolean(PCSettings.PARANORMAL_PIGMEN.path(), (boolean) PCSettings.PARANORMAL_PIGMEN.def());
		ppModule = new ParanormalPigmen(this, ppEnabled);

		boolean rbEnabled = getMainConfig().getBoolean(PCSettings.RAINING_BLOCKS_ENABLED.path(), (boolean) PCSettings.RAINING_BLOCKS_ENABLED.def());
		rbModule = new RainingBlocks(this, rbEnabled);

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
		getCommand("discord").setExecutor(new DiscordCmd(this));
		getCommand("donate").setExecutor(new DonateCmd(this));
		getCommand("help").setExecutor(new HelpCmd(this));
		getCommand("launchpure").setExecutor(new LaunchpureCmd(this, lpModule));
		getCommand("nv").setExecutor(new NVCmd(this, nvModule));
		getCommand("nvlist").setExecutor(new NVListCmd(this, nvModule));
		getCommand("togglelaunchpure").setExecutor(new TogglelaunchpureCmd(this, lpModule));
		getCommand("vote").setExecutor(new VoteCmd(this));
		getCommand("website").setExecutor(new WebsiteCmd(this));
	}

	private String getCmdStatusFromValue(int value)
	{
		switch (value)
		{
			case 0:
				return "Disabled";
			case 1:
				return "Fake unknown command";
			case 2:
				return "Enabled";
			default:
				return "Unknown config value (" + value + ")";
		}
	}
}