package com.phoenixorigins.phoenixcore.config;

import com.phoenixorigins.phoenixcore.PhoenixCore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class PCResources
{
	private PhoenixCore pc;
	private final String[] servers = { "zombies", "hub", "survival", "skyblock" };
	private final String[] commands = { "discord", "donate", "help", "vote", "website" };

	private HashMap<String, String> sections;

	public PCResources(PhoenixCore pc)
	{
		this.pc = pc;
		sections = new HashMap<String, String>();
	}

	public void generateFiles()
	{
		for (String command : commands)
		{
			for (String server : servers)
			{
				try
				{
					String filePath = pc.getDataFolder() + File.separator + "commands" + File.separator + command + File.separator + server + ".txt";
					String resourcePath = "commands" + File.separator + command + File.separator + server + ".txt";

					File f = new File(filePath);
					if (!f.exists())
					{
						pc.getLogger().warning("Could not find " + server + " file for command " + command + ". Generating a new one from resources!");
						pc.saveResource(resourcePath, false);
					}
					else
					{
						pc.getLogger().info("Found " + server + " file for command " + command + ", aborting generating this file.");
					}

					if (f.exists())
					{
						ArrayList<String> lines = new ArrayList<String>();
						BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8));
						String l;
						while ((l = br.readLine()) != null)
						{
							lines.add(l);
						}

						int index = 0;
						while (index < lines.size())
						{
							if (lines.get(index).contains("##"))
							{
								String section = lines.get(index).substring(2);
								String key = command + ":" + server + ":" + section;
								index++;
								StringBuilder sb = new StringBuilder();
								while (index < lines.size() && !lines.get(index).contains("##"))
								{
									sb.append(lines.get(index));
									index++;
									if (index < lines.size() && !lines.get(index).contains("##"))
									{
										sb.append("\n");
									}
									else
									{
										sections.put(key, sb.toString());
										pc.getLogger().info("Found section " + key + "!");
									}
								}
							}
						}
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public String getSection(String command, String server, String section)
	{
		return sections.get(command + ":" + server + ":" + section);
	}
}