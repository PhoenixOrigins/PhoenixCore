package com.phoenixorigins.phoenixcore.modules.launchpure;

public class Launchpure
{
	private boolean enabled;
	private boolean launchEnabled;

	public Launchpure(boolean enabled)
	{
		this.enabled = enabled;
		launchEnabled = false;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public boolean isLaunchEnabled()
	{
		return launchEnabled;
	}

	public boolean toggleLaunchEnabled()
	{
		return launchEnabled = !launchEnabled;
	}
}