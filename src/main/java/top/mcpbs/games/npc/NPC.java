package top.mcpbs.games.npc;

import cn.nukkit.Player;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.item.Item;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.MobArmorEquipmentPacket;
import cn.nukkit.network.protocol.MobEquipmentPacket;
import cn.nukkit.network.protocol.MoveEntityAbsolutePacket;
import cn.nukkit.network.protocol.SetEntityMotionPacket;

import java.util.HashMap;

public class NPC extends EntityHuman{

    public static HashMap<Long,NPC> npc = new HashMap<>();
    private Item itemHand = Item.get(0, 0);//do not used now...
    private Item[] armor = new Item[0];//...
    boolean keepface;
    boolean alwayssave;
    String cmd;
    long id;

    public NPC(FullChunk chunk, CompoundTag nbt, String name,boolean alwayssave,boolean keepface,String cmd) {
        super(chunk, nbt);
        this.setNameTagAlwaysVisible();
        this.setNameTagVisible();
        this.setNameTag(name);
        this.setDataFlag(37, -1);
        this.setHealth(20.0F);
        this.setMaxHealth(20);
        this.keepface = keepface;
        this.cmd = cmd;
        this.id = NPCTool.nextID();
        this.alwayssave = alwayssave;
        if (alwayssave){
            NPCTool.saveNPCToConfig(this);
        }
        this.spawnToAll();
        npc.put(this.id,this);
    }

    public void setItemHand(Item itemHand) {
        this.itemHand = itemHand;
    }

    public void setArmor(Item[] armor) {
        this.armor = armor;
    }

    @Override
    public boolean onUpdate(int currentTick) {
        boolean b = super.onUpdate(currentTick);
        if (b) {
            for (Player p : this.getLevel().getPlayers().values()) {
                MoveEntityAbsolutePacket packet = new MoveEntityAbsolutePacket();
                double npcx = this.x - p.x;
                double npcy = this.y - p.y;
                double npcz = this.z - p.z;
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
        return b;
    }

    public void reload(){
        HashMap<String,Object> option = NPCTool.getConfigNPCOption(this.id);
        this.cmd = (String) option.get("cmd");
    }

    @Override
    public void close() {
        super.close();
        npc.remove(this.id);
        if (this.alwayssave){
            NPCTool.remNPCFromConfig(this.id);
        }
    }
}
