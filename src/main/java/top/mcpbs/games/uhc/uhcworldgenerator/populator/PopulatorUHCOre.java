package top.mcpbs.games.uhc.uhcworldgenerator.populator;

import cn.nukkit.block.BlockID;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.level.generator.populator.type.Populator;
import cn.nukkit.math.NukkitRandom;

import java.util.ArrayList;

public class PopulatorUHCOre extends Populator {
    public ArrayList<UHCOreType> ore;

    public PopulatorUHCOre(ArrayList<UHCOreType> ore){
        this.ore = ore;
    }

    @Override
    public void populate(ChunkManager level, int chunkx, int chunkZ, NukkitRandom random, FullChunk chunk) {
        for (UHCOreType o : this.ore) {
            for (int i = 1;i <= o.amount;i++){
                int y = random.nextBoundedInt(o.maxh - o.minh + 1) + o.minh;
                int x = random.nextBoundedInt(17);
                int z = random.nextBoundedInt(17);
                for (int xx = -1;xx <= 1;xx++){
                    for (int yy = -1;yy <= 1;yy++){
                        for (int zz = -1;zz <= 1;zz++){
                            int id = chunk.getBlockId(x + xx,y + yy,z + zz);
                            if (id == BlockID.STONE){
                                level.setBlockFullIdAt(x + xx,y + yy,z + zz,o.blockid);
                            }
                        }
                    }
                }
            }
        }
    }
}