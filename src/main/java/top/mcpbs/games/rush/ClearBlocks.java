package top.mcpbs.games.rush;

import cn.nukkit.block.Block;
import cn.nukkit.math.Vector3;

public class ClearBlocks {
    public static void clearBlocks(RushRoom room){
        for (Vector3 v : room.rushblocks.keySet()){
            room.roomlevel.setBlock(v,Block.get(room.rushblocks.get(v)));
        }
        room.rushblocks.clear();
    }
}
