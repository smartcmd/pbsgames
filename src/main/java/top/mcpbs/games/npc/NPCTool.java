package top.mcpbs.games.npc;

import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.utils.Config;
import top.mcpbs.games.Main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class NPCTool {

    public static Config config = new Config(Main.plugin.getDataFolder() + "/npc.yml");

    public static void saveNPCToConfig(NPC npc){
        Config config = new Config(Main.plugin.getDataFolder() + "/npc.yml");
        HashMap<String, Object> option = new HashMap<>();
        option.put("cmd",npc.cmd);
        option.put("skin",npc.skin);
        ArrayList l = new ArrayList();
        l.add(npc.getPosition().getX());
        l.add(npc.getPosition().getY());
        l.add(npc.getPosition().getZ());
        l.add(npc.getPosition().getLevel().getName());
        option.put("position",l);
        config.set(String.valueOf(npc.id),option);
        config.save();
    }

    public static long nextID(){
        long id = 0;
        long max = 0;
        ArrayList<Long> tmp = new ArrayList();
        for (long idt : NPC.npc.keySet()){
            tmp.add(idt);
            max = idt;
        }
        for (long idtt : tmp){
            if (idtt > max){
                max = idtt;
            }
        }
        for (int i = 1; i <= (max + 1); i++){
            if (!NPC.npc.containsKey(i)){
                id = i;
                break;
            }
        }
        if (id == 0){
            id = 1;
        }
        return id;
    }


    public static HashMap<String,Object> getConfigNPCOption(long id){
        return (HashMap<String, Object>) config.get(String.valueOf(id));
    }


    public static void loadConfigAllNPC(){
        HashMap<Long,HashMap> all = (HashMap) config.getAll();
        HashMap tmp = getAllNPC();
        HashSet set = (HashSet) tmp.keySet();
        for(Map.Entry<Long,HashMap> e : all.entrySet()){
            if (!set.contains(e.getKey())){
                ArrayList l = (ArrayList) e.getValue().get("position");
                Position pos = new Position((double)l.get(0),(double)l.get(1),(double)l.get(2),Server.getInstance().getLevelByName(String.valueOf(l.get(3))));
                NPC npc = new NPC(pos.getChunk(),NPC.getDefaultNBT(pos),String.valueOf(e.getKey()),true,true,String.valueOf(e.getValue().get("cmd")),null);
            }else{
                NPC.npc.put(e.getKey(), (NPC) tmp.get(e.getKey()));
            }
        }
    }

    public static HashMap<Long, NPC> getAllNPC(){
        HashMap<Long, NPC> m = new HashMap();
        for (Level level : Server.getInstance().getLevels().values()){
            for (Entity e : level.getEntities()){
                if (e instanceof NPC){
                    m.put(((NPC)e).id,(NPC)e);
                }
            }
        }
        return m;
    }

    public static NPC getNPCByID(long id){//can null
       return getAllNPC().get(id);
    }

    public static void remNPCFromConfig(long id){
        config.remove(String.valueOf(id));
        config.save();
    }
}
