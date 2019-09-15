package com.phoenixorigins.phoenixcore.modules.rainingblocks;

import com.boydti.fawe.util.EditSessionBuilder;
import com.phoenixorigins.phoenixcore.PhoenixCore;
import com.phoenixorigins.phoenixcore.config.PCSettings;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.blocks.Blocks;
import com.sk89q.worldedit.extension.factory.PatternFactory;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.block.BaseBlock;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldedit.world.block.BlockTypes;
import org.bukkit.World;

public class ResetAreaTask implements Runnable
{
	private RainingBlocks rb;

	private int x1, x2, y1, y2, z1, z2;
	private World world;

	public ResetAreaTask(PhoenixCore pc, RainingBlocks rb)
	{
		this.rb = rb;

		x1 = pc.getMainConfig().getInt(PCSettings.RAINING_BLOCKS_RA_MIN_X.path());
		y1 = pc.getMainConfig().getInt(PCSettings.RAINING_BLOCKS_RA_MIN_Y.path());
		z1 = pc.getMainConfig().getInt(PCSettings.RAINING_BLOCKS_RA_MIN_Z.path());
		x2 = pc.getMainConfig().getInt(PCSettings.RAINING_BLOCKS_RA_MAX_X.path());
		y2 = pc.getMainConfig().getInt(PCSettings.RAINING_BLOCKS_RA_MAX_Y.path());
		z2 = pc.getMainConfig().getInt(PCSettings.RAINING_BLOCKS_RA_MAX_Z.path());
		world = pc.getServer().getWorld(pc.getMainConfig().getString(PCSettings.RAINING_BLOCKS_WORLD.path()));
	}

	@Override
	public void run()
	{
		EditSession session = new EditSessionBuilder(world.getName()).fastmode(true).build();
		BaseBlock air = new BaseBlock(BlockTypes.AIR.getDefaultState());
		session.setBlocks(new CuboidRegion(BlockVector3.at(x1, y1, z1), BlockVector3.at(x2, y2, z2)), air);
		session.flushQueue();
	}
}