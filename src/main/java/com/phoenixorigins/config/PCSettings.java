package com.phoenixorigins.config;

public enum PCSettings
{
	SERVER("server", "survival"),
	LOCALE("locale", "en_US"),
	COMMANDS_DONATE("commands.donate", 0),
	COMMANDS_HELP("commands.help", 0),
	COMMANDS_VOTE("commands.vote", 0),
	COMMANDS_WEBSITE("commands.website", 0),
	NIGHT_VISION("features.night-vision", false),
	TRIPWIRE_DUPE_CHECK("features.tripwire-dupe-check", false),
	CONFIG_VERSION("do-not-change-this-value.config-version", 1);

	private String path;
	private Object value;

	PCSettings(String path, Object value)
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
}