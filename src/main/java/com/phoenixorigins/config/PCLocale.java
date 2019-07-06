package com.phoenixorigins.config;

import org.bukkit.ChatColor;

public enum PCLocale
{
	PREFIX("phoenixcore.prefix", "&8[&c&lPhoenix&fCore&8]&f "),
	COMMAND_DISABLED("phoenixcore.command-disabled", "&cThis command is disabled!"),
	FEATURE_DISABLED("phoenixcore.feature-disabled", "&cThis feature is disabled!"),
	MUST_BE_PLAYER("phoenixcore.must-be-player", "&cYou must be a player to execute this command!"),
	FEATURE_NOT_IMPLEMENTED("phoenixcore.feature-not-implemented", "&cThis feature is currently not implemented!"),
	NIGHTVISION_ON("phoenixcore.nightvision.on", "&fNight vision has been toggled &aON&f!"),
	NIGHTVISION_OFF("phoenixcore.nightvision.off", "&fNight vision has been toggled &cOFF&f!"),
	NIGHTVISION_ALREADY_ON("phoenixcore.nightvision.already-on", "&cYou already have night vision on!"),
	NIGHTVISION_ALREADY_OFF("phoenixcore.nightvision.already-off", "&cYou already have night vision off!"),
	NIGHTVISION_LIST("phoenixcore.nightvision.list", "&fThe following players have night vision:"),
	NIGHTVISION_PLAYERS("phoenixcore.nightvision.players", "&fPlayers: "),
	NIGHTVISION_NO_PLAYERS("phoenixcore.nightvision.no-players", "&fNo players have night vision!"),
	NIGHTVISION_NOTIFY_ON("phoenixcore.nightvision.notify-on", "&f{0} toggled night vision &aON&f!"),
	NIGHTVISION_NOTIFY_OFF("phoenixcore.nightvision.notify-off", "&f{0} toggled night vision &cOFF&f!"),
	NIGHTVISION_NO_PERMISSION_TOGGLE("phoenixcore.nightvision.no-permission-toggle", "&cYou do not have permission to toggle night vision!"),
	NIGHTVISION_NO_PERMISSION_LIST("phoenixcore.nightvision.no-permission-list", "&cYou do not have permission to list players with night vision!"),
	NIGHTVISION_INVALID_SUBCOMMAND("phoenixcore.nightvision.invalid-subcommand", "&cInvalid subcommand. &fUse &b/nv help &ffor command help."),
	NIGHTVISION_HELP_HEADER("phoenixcore.nightvision.help-header", "&cPhoenix&3NightVision &fCommand Help"),
	NIGHTVISION_HELP("phoenixcore.nightvision.help", "&3/nv&f - Toggle night vision on and off."),
	NIGHTVISION_HELP_ON("phoenixcore.nightvision.help-on", "&3/nv on&f - Toggle night vision on."),
	NIGHTVISION_HELP_OFF("phoenixcore.nightvision.help-off", "&3/nv off&f - Toggle night vision off."),
	NIGHTVISION_HELP_LIST("phoenixcore.nightvision.help-list", "&3/nvlist&f - List all players with night vision on."),
	LOCALE_VERSION("do-not-change-this-value.locale-version", 1);

	private String path;
	private Object value;

	PCLocale(String path, Object value)
	{
		this.path = path;
		this.value = value;
	}

	public String getPath()
	{
		return path;
	}

	public Object getDefault()
	{
		return value;
	}

	public static String format(String s, String... args)
	{
		String manip = s;
		int index = 0;
		for (String rep : args)
		{
			String replaceMarker = "{" + index + "}";

			int indexOf = manip.indexOf(replaceMarker);
			if (indexOf != -1)
			{
				manip = manip.substring(0, indexOf) + "&f" /* Replace color back to white */ + rep + manip
						.substring(indexOf + replaceMarker.length());
			}

			index++;
		}
		return ChatColor.translateAlternateColorCodes('&', manip);
	}
}