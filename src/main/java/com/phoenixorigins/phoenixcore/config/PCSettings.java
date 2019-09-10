package com.phoenixorigins.phoenixcore.config;

public enum PCSettings
{
	SERVER("server", "survival"),
	LOCALE("locale", "en_US"),
	COMMANDS_DISCORD("commands.discord", 0),
	COMMANDS_DONATE("commands.donate", 0),
	COMMANDS_HELP("commands.help", 0),
	COMMANDS_VOTE("commands.vote", 0),
	COMMANDS_WEBSITE("commands.website", 0),
	NIGHT_VISION("features.night-vision", false),
	TRIPWIRE_DUPE_CHECK("features.tripwire-dupe-check", false),
	LAUNCHPURE("features.launchpure.enabled", false),
	PARANORMAL_PIGMEN("features.paranormal-pigmen", false),
	CONFIG_VERSION("do-not-change-this-value.config-version", 1);

	private String path;
	private Object def;

	PCSettings(String path, Object def)
	{
		this.path = path;
		this.def = def;
	}

	public String path()
	{
		return path;
	}

	public Object def()
	{
		return def;
	}
}