package top.mcpbs.games.npc;

import cn.nukkit.Player;
import cn.nukkit.network.protocol.MoveEntityAbsolutePacket;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.PluginTask;

public class NPCTask extends PluginTask {
    public NPCTask(Plugin owner) {
        super(owner);
    }

    @Override
    public void onRun(int i) {
        for (NPC npc : NPC.npc.values()) {
            for (Player p : npc.getLevel().getPlayers().values()) {
                MoveEntityAbsolutePacket packet = new MoveEntityAbsolutePacket();
                double npcx = npc.x - p.x;
                double npcy = npc.y - p.y;
                double npcz = npc.z - p.z;
                double yaw = Math.asin(npcx / Math.sqrt(npcx * npcx + npcz * npcz)) / 3.14D * 180.0D;
                double pitch = (double) Math.round(Math.asin(npcy / Math.sqrt(npcx * npcx + npcz * npcz + npcy * npcy)) / 3.14D * 180.0D);
                if (npcz > 0.0D) {
                    yaw = -yaw + 180.0D;
                }
                packet.yaw = yaw;
                packet.pitch = pitch;
                p.dataPacket(packet);
            }
        }
    }
}
