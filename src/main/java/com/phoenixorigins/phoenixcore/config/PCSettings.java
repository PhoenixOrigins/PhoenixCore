package com.phoenixorigins.phoenixcore.config;

import java.util.ArrayList;

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
	RAINING_BLOCKS_ENABLED("features.raining-blocks.enabled", false),
	RAINING_BLOCKS_TICK_DELAY("features.raining-blocks.tick-delay", 10),
	RAINING_BLOCKS_RESET_DELAY("features.raining-blocks.reset-delay", 24000),
	RAINING_BLOCKS_WORLD("features.raining-blocks.world", "world"),
	RAINING_BLOCKS_FA_MIN_X("features.raining-blocks.falling-area.min-x", 0),
	RAINING_BLOCKS_FA_MIN_Y("features.raining-blocks.falling-area.min-y", 0),
	RAINING_BLOCKS_FA_MIN_Z("features.raining-blocks.falling-area.min-z", 0),
	RAINING_BLOCKS_FA_MAX_X("features.raining-blocks.falling-area.max-x", 0),
	RAINING_BLOCKS_FA_MAX_Y("features.raining-blocks.falling-area.max-y", 0),
	RAINING_BLOCKS_FA_MAX_Z("features.raining-blocks.falling-area.max-z", 0),
	RAINING_BLOCKS_RA_MIN_X("features.raining-blocks.reset-area.min-x", 0),
	RAINING_BLOCKS_RA_MIN_Y("features.raining-blocks.reset-area.min-y", 0),
	RAINING_BLOCKS_RA_MIN_Z("features.raining-blocks.reset-area.min-z", 0),
	RAINING_BLOCKS_RA_MAX_X("features.raining-blocks.reset-area.max-x", 0),
	RAINING_BLOCKS_RA_MAX_Y("features.raining-blocks.reset-area.max-y", 0),
	RAINING_BLOCKS_RA_MAX_Z("features.raining-blocks.reset-area.max-z", 0),
	RAINING_BLOCKS_BLOCK_TABLE("features.raining-blocks.block-table", new ArrayList<String>()),
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