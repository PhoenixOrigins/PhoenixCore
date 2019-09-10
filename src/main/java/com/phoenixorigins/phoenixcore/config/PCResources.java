package com.phoenixorigins.phoenixcore.config;

import java.io.File;

public class PCResources
{
	public static boolean generateFilesFromResources()
	{
		try
		{
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public static File grabFile(String path)
	{
		try
		{
			File f = new File(path);
			return f;
		}
		catch (Exception e)
		{
			return null;
		}
	}
}