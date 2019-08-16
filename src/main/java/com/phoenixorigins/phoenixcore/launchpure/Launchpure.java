package com.phoenixorigins.phoenixcore.launchpure;

public class Launchpure
{
	private boolean configEnabled;
	public boolean launchEnabled;

	public Launchpure(boolean configEnabled)
	{
		this.configEnabled = configEnabled;
		launchEnabled = false;
	}

	public boolean isConfigEnabled()
	{
		return configEnabled;
	}
}