package com.phoenixorigins.config;

import com.phoenixorigins.PhoenixCore;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PCConfig
{
	private final int CONFIG_VERSION = (int) PCSettings.CONFIG_VERSION.getDefault();
	private final int LOCALE_VERSION = (int) PCLocale.LOCALE_VERSION.getDefault();
	private PhoenixCore plugin;
	private File mainConfigFile, langConfigFile;
	private YamlConfiguration main, lang;
	private String loadedLocale;

	public PCConfig(PhoenixCore plugin)
	{
		this.plugin = plugin;
	}

	/**
	 * Generate PhoenixCore configuration files.
	 *
	 * @return true if generation/updating was successful, false otherwise.
	 */
	public boolean generateConfigs()
	{
		generateFolders();
		if (generateMainConfig())
		{
			loadedLocale = main.getString(PCSettings.LOCALE.getPath(), (String) PCSettings.LOCALE.getDefault());
			return generateLangConfig();
		}
		return false;
	}

	/**
	 * Get the main configuration file.
	 *
	 * @return YamlConfiguration for config.yml.
	 */
	public YamlConfiguration getMain()
	{
		return main;
	}

	public String getMessage(PCLocale message, boolean usePrefix, String... args)
	{
		String toReturn = PCLocale.format(lang.getString(message.getPath(), (String) message.getDefault()), args);
		if (usePrefix)
		{
			toReturn = PCLocale.format(lang.getString(PCLocale.PREFIX.getPath(), (String) PCLocale.PREFIX.getDefault())) + toReturn;
		}
		return toReturn;
	}

	/**
	 * Generates or version checks the main configuration file (config.yml).
	 * If the process fails, the plugin will be disabled.
	 *
	 * @return true if successfully created/checked configs, false if there is an error.
	 */
	private boolean generateMainConfig()
	{
		main = new YamlConfiguration();
		mainConfigFile = new File(plugin.getDataFolder(), "config.yml");

		/* Check if config.yml has been generated already */
		if (!mainConfigFile.exists())
		{
			/* config.yml hasn't been generated yet */
			try
			{
				/* Try generating config.yml from resources and assigning it */
				plugin.saveResource("config.yml", false);
				main.load(mainConfigFile);
			}
			catch (Exception e)
			{
				/* Something went wrong! */
				e.printStackTrace();
				plugin.getLogger().severe("Could not generate config files.");
				plugin.getLogger().severe("PhoenixCore will now be disabled.");
				plugin.getServer().getPluginManager().disablePlugin(plugin);
				return false;
			}
		}
		else
		{
			/* Check config version */
			try
			{
				/* Match config version */
				main.load(mainConfigFile);
				int currentConfigVersion = main.getInt(PCSettings.CONFIG_VERSION.getPath(), 0);
				if (currentConfigVersion != CONFIG_VERSION)
				{
					/* Uh oh! The config version does not match! */
					plugin.getLogger().warning("Your config version (" + currentConfigVersion
							+ ") does not match the current config version (" + CONFIG_VERSION + ").");
					plugin.getLogger().warning("Your current configuration will be saved to config.yml.backup.");

					/* Begin config backup */
					plugin.getLogger().info("Commencing backup...");

					File backup = new File(plugin.getDataFolder(), "config.yml.backup");
					backup.createNewFile();

					BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(mainConfigFile)));
					BufferedWriter out = new BufferedWriter(new FileWriter(backup, true));

					String currLine = "";
					while ((currLine = in.readLine()) != null)
					{
						out.write(currLine);
						out.newLine();
					}

					in.close();
					out.close();

					plugin.getLogger().info("Configuration backup successful!");

					/* Generate new config.yml */
					plugin.getLogger().info("Generating a new config.yml...");
					plugin.saveResource("config.yml", true);

					main.load(mainConfigFile);
				}
			}
			catch (Exception e)
			{
				/* Something went wrong loading the config! */
				e.printStackTrace();
				plugin.getLogger().severe("Could not load config files.");
				plugin.getLogger().severe("PhoenixCore will now be disabled.");
				plugin.getServer().getPluginManager().disablePlugin(plugin);
				return false;
			}
		}
		return true;
	}

	/**
	 * Generates or version checks the language file (default: en_US.yml).
	 * If the process fails, the plugin will be disabled.
	 *
	 * @return true if successfully created/checked configs, false if there is an error.
	 */
	private boolean generateLangConfig()
	{
		lang = new YamlConfiguration();
		langConfigFile = new File(plugin.getDataFolder() + File.separator + "locales", loadedLocale + ".yml");

		/* Check if locale file has been generated already */
		if (!langConfigFile.exists())
		{
			/* Locale file hasn't been generated yet */
			try
			{
				/* Try generating locale file from resources and assigning it */
				plugin.saveResource("locales" + File.separator + loadedLocale + ".yml", false);
				lang.load(langConfigFile);
			}
			catch (Exception e)
			{
				/* Could not find locale! */
				e.printStackTrace();
				plugin.getLogger().severe("Could not generate locale files.");
				plugin.getLogger().severe("The locale " + loadedLocale + " was not found in the plugin's resources.");
				plugin.getLogger().info("PhoenixCore will now default to en_US.");
				loadedLocale = "en_US";
				try
				{
					plugin.saveResource("locales" + File.separator + loadedLocale + ".yml", false);
					lang.load(langConfigFile);
					return true;
				}
				catch (Exception e2)
				{
					/* Something went wrong! */
					e2.printStackTrace();
					plugin.getLogger().severe("The locale could not be loaded.");
					plugin.getLogger().severe("Contact the developer!");
					plugin.getLogger().severe("PhoenixCore will now be disabled.");
					return false;
				}
			}
		}
		else
		{
			/* Check locale version */
			try
			{
				/* Match locale version */
				lang.load(langConfigFile);
				int currentConfigVersion = lang.getInt(PCLocale.LOCALE_VERSION.getPath(), 0);
				if (currentConfigVersion != LOCALE_VERSION)
				{
					/* Uh oh! The locale version does not match! */
					plugin.getLogger().warning("Your locale version (" + currentConfigVersion
							+ ") does not match the current locale version (" + LOCALE_VERSION + ").");
					plugin.getLogger()
							.warning("Your current configuration will be saved to " + loadedLocale + ".yml.backup.");

					/* Begin locale backup */
					plugin.getLogger().info("Commencing backup...");

					File backup = new File(plugin.getDataFolder() + File.separator + "locales",
							loadedLocale + ".yml.backup");
					backup.createNewFile();

					BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(mainConfigFile)));
					BufferedWriter out = new BufferedWriter(new FileWriter(backup, true));

					String currLine = "";
					while ((currLine = in.readLine()) != null)
					{
						out.write(currLine);
						out.newLine();
					}

					in.close();
					out.close();

					plugin.getLogger().info("Locale backup successful!");

					/* Generate new config.yml */
					plugin.getLogger().info("Generating a new locale file...");
					plugin.saveResource("locales" + File.separator + loadedLocale + ".yml", true);

					lang.load(langConfigFile);
				}
			}
			catch (Exception e)
			{
				/* Something went wrong loading the locale! */
				e.printStackTrace();
				plugin.getLogger().severe("Could not load locale files.");
				plugin.getLogger().severe("InfinityWarps will now be disabled.");
				plugin.getServer().getPluginManager().disablePlugin(plugin);
				return false;
			}
		}
		return true;
	}

	private void generateFolders()
	{
		ArrayList<File> folders = new ArrayList<File>();

		folders.add(plugin.getDataFolder());
		folders.add(new File(plugin.getDataFolder() + File.separator + "donate"));
		folders.add(new File(plugin.getDataFolder() + File.separator + "help"));
		folders.add(new File(plugin.getDataFolder() + File.separator + "vote"));

		for (File folder : folders)
		{
			if (!folder.exists())
			{
				folder.mkdir();
			}
		}
	}
}