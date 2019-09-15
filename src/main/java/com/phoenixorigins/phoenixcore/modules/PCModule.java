package com.phoenixorigins.phoenixcore.modules;

public abstract class PCModule
{
	protected boolean enabled;

	public PCModule(boolean enabled)
	{
		this.enabled = enabled;
	}

	public boolean isEnabled()
	{
		return enabled;
	}
}