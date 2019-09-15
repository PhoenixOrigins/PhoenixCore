package com.phoenixorigins.phoenixcore.modules.rainingblocks;

import com.phoenixorigins.phoenixcore.PhoenixCore;
import com.phoenixorigins.phoenixcore.config.PCSettings;
import com.phoenixorigins.phoenixcore.modules.PCModule;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RainingBlocks extends PCModule
{
	private PhoenixCore pc;

	private int totalWeight;
	private HashMap<Material, Integer> blockTable;

	private FallingBlockTask fbt;
	private ResetAreaTask rat;

	public RainingBlocks(PhoenixCore pc, boolean enabled)
	{
		super(enabled);
		this.pc = pc;

		if (enabled)
		{
			totalWeight = 0;
			blockTable = new HashMap<Material, Integer>();
			initBlockTable();

			int fallingBlockDelay = pc.getMainConfig().getInt(PCSettings.RAINING_BLOCKS_TICK_DELAY.path(), (int) PCSettings.RAINING_BLOCKS_TICK_DELAY.def());
			fbt = new FallingBlockTask(pc, this);
			pc.getServer().getScheduler().scheduleSyncRepeatingTask(pc, fbt, 0L, fallingBlockDelay);

			int resetDelay = pc.getMainConfig().getInt(PCSettings.RAINING_BLOCKS_RESET_DELAY.path(), (int) PCSettings.RAINING_BLOCKS_RESET_DELAY.def());
			rat = new ResetAreaTask(pc, this);
			pc.getServer().getScheduler().scheduleSyncRepeatingTask(pc, rat, resetDelay, resetDelay);
		}
	}

	Material getNextMaterial()
	{
		int rand = (int) (Math.random() * totalWeight);

		for (Map.Entry<Material, Integer> pair : blockTable.entrySet())
		{
			rand -= pair.getValue();
			if (rand <= 0)
			{
				return pair.getKey();
			}
		}

		return Material.DIRT;
	}

	private void initBlockTable()
	{
		List<String> entries = (List<String>) pc.getMainConfig().getList(PCSettings.RAINING_BLOCKS_BLOCK_TABLE.path());

		if (entries == null)
		{
			return;
		}

		for (String s : entries)
		{
			String[] parts = s.split(":");
			Material mat = Material.valueOf(parts[0]);
			int weight = Integer.parseInt(parts[1]);

			totalWeight += weight;
			blockTable.put(mat, weight);
		}
	}
}