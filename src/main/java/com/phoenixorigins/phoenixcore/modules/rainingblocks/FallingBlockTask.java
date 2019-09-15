package com.phoenixorigins.phoenixcore.modules.rainingblocks;

import com.phoenixorigins.phoenixcore.PhoenixCore;
import com.phoenixorigins.phoenixcore.config.PCSettings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class FallingBlockTask implements Runnable
{
	private RainingBlocks rb;

	private int x1, x2, z1, z2, y1, y2;
	private World world;

	public FallingBlockTask(PhoenixCore pc, RainingBlocks rb)
	{
		this.rb = rb;

		x1 = pc.getMainConfig().getInt(PCSettings.RAINING_BLOCKS_FA_MIN_X.path());
		y1 = pc.getMainConfig().getInt(PCSettings.RAINING_BLOCKS_FA_MIN_Y.path());
		z1 = pc.getMainConfig().getInt(PCSettings.RAINING_BLOCKS_FA_MIN_Z.path());
		x2 = pc.getMainConfig().getInt(PCSettings.RAINING_BLOCKS_FA_MAX_X.path());
		y2 = pc.getMainConfig().getInt(PCSettings.RAINING_BLOCKS_FA_MAX_Y.path());
		z2 = pc.getMainConfig().getInt(PCSettings.RAINING_BLOCKS_FA_MAX_Z.path());
		world = pc.getServer().getWorld(pc.getMainConfig().getString(PCSettings.RAINING_BLOCKS_WORLD.path()));
	}

	@Override
	public void run()
	{
		if (Math.random() < 0.3) // 30% chance to fail
		{
			return;
		}

		int xFactor = x2 - x1;
		int yFactor = y2 - y1;
		int zFactor = z2 - z1;

		double x = (int) (Math.random() * xFactor) + x1 + 0.5;
		double y = (int) (Math.random() * yFactor) + y1;
		double z = (int) (Math.random() * zFactor) + z1 + 0.5;

		Location spawnLocation = new Location(world, x, y, z); // snap to grid

		world.spawnFallingBlock(spawnLocation, Bukkit.createBlockData(rb.getNextMaterial()));
	}
}