package com.phoenixorigins.phoenixcore.modules.launchpure;

import com.phoenixorigins.phoenixcore.modules.PCModule;

public class Launchpure extends PCModule
{
	private boolean launchEnabled;

	public Launchpure(boolean enabled)
	{
		super(enabled);
		launchEnabled = false;
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