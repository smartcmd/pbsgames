package top.mcpbs.games.npc;

import cn.nukkit.Player;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.item.Item;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.MobArmorEquipmentPacket;
import cn.nukkit.network.protocol.MobEquipmentPacket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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

    public boolean onUpdate(int currentTick) {
        boolean b = super.onUpdate(currentTick);
        List<String> npd = new ArrayList();
        if (!this.getLevel().getPlayers().isEmpty()) {
            try {
                Iterator var4 = this.getLevel().getPlayers().values().iterator();

                while(var4.hasNext()) {
                    Player player = (Player)var4.next();
                    double distance = player.x - this.x + (player.y - this.y) + (player.z - this.z);
                    npd.add(player.getName() + "@" + distance);
                }

                npd.sort((mapping1, mapping2) -> {
                    String[] nameMapNum1 = mapping1.split("@");
                    String[] nameMapNum2 = mapping2.split("@");
                    double compare = Double.parseDouble(nameMapNum1[1]) - Double.parseDouble(nameMapNum2[1]);
                    if (compare > 0.0D) {
                        return 1;
                    } else {
                        return compare == 0.0D ? 0 : -1;
                    }
                });
                String name = ((String)npd.get(0)).split("@")[0];
                boolean hasplayer = false;
                Iterator var23 = this.getLevel().getPlayers().values().iterator();

                while(var23.hasNext()) {
                    Player player = (Player)var23.next();
                    if (player.getName().equals(name)) {
                        hasplayer = true;
                    }
                }

                if (hasplayer && this.keepface) {
                    try {
                        Player playera = this.getServer().getPlayer(name);
                        if (playera == null) {
                            return false;
                        }

                        if (playera.getLevel().getName().equals(this.getLevel().getName()) && this.getServer().getOnlinePlayers().containsValue(playera)) {
                            double npcx = this.x - playera.x;
                            double npcy = this.y - playera.y;
                            double npcz = this.z - playera.z;
                            double yaw = Math.asin(npcx / Math.sqrt(npcx * npcx + npcz * npcz)) / 3.14D * 180.0D;
                            double pitch = (double)Math.round(Math.asin(npcy / Math.sqrt(npcx * npcx + npcz * npcz + npcy * npcy)) / 3.14D * 180.0D);
                            if (npcz > 0.0D) {
                                yaw = -yaw + 180.0D;
                            }

                            MobEquipmentPacket pk = new MobEquipmentPacket();
                            pk.eid = this.getId();
                            pk.item = this.itemHand;
                            pk.inventorySlot = 0;
                            MobArmorEquipmentPacket pks = new MobArmorEquipmentPacket();
                            pks.eid = this.getId();
                            pks.slots = this.armor;
                            playera.dataPacket(pk);
                            playera.dataPacket(pks);
                            this.yaw = yaw;
                            this.pitch = pitch;
                        }
                    } catch (Exception var19) {
                    }
                }
            } catch (Exception var20) {
            }
        }

        return b;
    }

    public void reload(){
        HashMap<String,Object> option = NPCTool.getConfigNPCOption(this.id);
        this.cmd = (String) option.get("cmd");
    }
}
