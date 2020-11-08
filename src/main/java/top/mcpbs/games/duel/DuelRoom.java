package top.mcpbs.games.duel;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.level.Position;
import cn.nukkit.utils.Config;
import top.mcpbs.games.Main;
import top.mcpbs.games.name.NameTool;
import top.mcpbs.games.lobby.LobbyTool;
import top.mcpbs.games.playerinfo.score.Score;
import top.mcpbs.games.room.Room;
import top.mcpbs.games.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DuelRoom extends Room {
    public static HashMap<Integer, DuelRoom> duelrooms = new HashMap();
    public Position pos1;
    public Position pos2;
    public Position pos3;
    public int waittime = 15;
    public String mode;
    public ArrayList<Item> item = new ArrayList<>();
    public HashMap<String, Item> armor = new HashMap<>();
    public String modename;

    public DuelRoom(String mode){
        Config duellevel = new Config(Main.plugin.getDataFolder() + "/duellevel.yml");
        Config duelmode = new Config(Main.plugin.getDataFolder() + "/duelmode.yml");

        this.mode = mode;

        this.modename = (String) duelmode.get(mode + ".modename");

        Random r = new Random();
        int rnum = r.nextInt(duellevel.getAll().size()) + 1;
        HashMap<String, Object> levelinfo = (HashMap<String, Object>) duellevel.get("dueltmp" + rnum);
        this.mapname = (String) levelinfo.get("mapname");

        if (((HashMap)duelmode.get(mode)).containsKey("item")) {
            HashMap<ArrayList<Integer>, HashMap<String, Object>> itemtmp = (HashMap<ArrayList<Integer>, HashMap<String, Object>>) duelmode.get(mode + ".item");
            for (Map.Entry<ArrayList<Integer>, HashMap<String, Object>> e : itemtmp.entrySet()) {
                ArrayList<Integer> idata = e.getKey();
                Item item = Item.get(idata.get(0), idata.get(1), idata.get(2));
                if (e.getValue() != null && e.getValue().containsKey("enchantment")) {
                    HashMap<Integer, Integer> enchantment = (HashMap) e.getValue().get("enchantment");
                    for (Map.Entry<Integer, Integer> ee : enchantment.entrySet()) {
                        Enchantment enchantment1 = Enchantment.get(ee.getKey());
                        enchantment1.setLevel(ee.getValue());
                        item.addEnchantment(enchantment1);
                    }
                }
                if (e.getValue() != null && e.getValue().containsKey("name")) {
                    item.setCustomName((String) e.getValue().get("name"));
                }
                this.item.add(item);
            }
        }

        if (((HashMap)duelmode.get(mode)).containsKey("armor")) {
            HashMap<Integer, HashMap<String, Object>> armortmp = (HashMap<Integer, HashMap<String, Object>>) duelmode.get(mode + ".armor");
            for (Map.Entry<Integer, HashMap<String, Object>> e1 : armortmp.entrySet()) {
                Item item1 = Item.get(e1.getKey(), 0, 1);
                if (e1.getValue() != null && e1.getValue().containsKey("enchantment")) {
                    HashMap<Integer, Integer> enchantment = (HashMap) e1.getValue().get("enchantment");
                    for (Map.Entry<Integer, Integer> ee1 : enchantment.entrySet()) {
                        Enchantment enchantment1 = Enchantment.get(ee1.getKey());
                        enchantment1.setLevel(ee1.getValue());
                        item1.addEnchantment(enchantment1);
                    }
                }
                if (e1.getValue().get("place").equals("helmet")){
                    this.armor.put("helmet",item1);
                }
                if (e1.getValue().get("place").equals("chestplate")){
                    this.armor.put("chestplate",item1);
                }
                if (e1.getValue().get("place").equals("leggings")){
                    this.armor.put("leggings",item1);
                }
                if (e1.getValue().get("place").equals("boots")){
                    this.armor.put("boots",item1);
                }
            }
        }

        int id = 0;
        int max = 0;
        ArrayList<Integer> tmp = new ArrayList();
        for (int idt : DuelRoom.duelrooms.keySet()){
            tmp.add(idt);
            max = idt;
        }
        for (int idtt : tmp){
            if (idtt > max){
                max = idtt;
            }
        }
        for (int i = 1; i <= (max + 1); i++){
            if (!DuelRoom.duelrooms.containsKey(i)){
                id = i;
                break;
            }
        }
        if (id == 0){
            id = 1;
        }
        this.roomId = id;

        FileUtil f = new FileUtil();
        if (new File(Server.getInstance().getDataPath() + "/worlds/duellevel" + id).exists()){
            f.deleteDirectory(new File(Server.getInstance().getDataPath() + "/worlds/duellevel" + id));
        }
        new File(Server.getInstance().getDataPath() + "/worlds/duellevel" + id + "/region").mkdirs();
        f.copyDir(Server.getInstance().getDataPath() + "/worlds/dueltmp" + rnum +"/region",Server.getInstance().getDataPath() + "/worlds/duellevel" + id + "/region");
        Server.getInstance().generateLevel("duellevel" + id);
        this.roomlevel = Server.getInstance().getLevelByName("duellevel" + this.roomId);

        ArrayList<Double> pos1tmp = (ArrayList) duellevel.get("dueltmp" + rnum + ".pos1");
        ArrayList<Double> pos2tmp = (ArrayList) duellevel.get("dueltmp" + rnum + ".pos2");
        ArrayList<Double> pos3tmp = (ArrayList) duellevel.get("dueltmp" + rnum + ".pos3");
        pos1 = new Position(pos1tmp.get(0),pos1tmp.get(1),pos1tmp.get(2),this.roomlevel);
        pos2 = new Position(pos2tmp.get(0),pos2tmp.get(1),pos2tmp.get(2),this.roomlevel);
        pos3 = new Position(pos3tmp.get(0),pos3tmp.get(1),pos3tmp.get(2),this.roomlevel);

        duelrooms.put(this.roomId, this);
    }

    @Override
    public void gameStart() {
        for (Player p : this.waiting) {
            p.getInventory().clearAll();
            for (Item item : this.item) {
                p.getInventory().addItem(item);
            }
            if (armor.containsKey("helmet")) {
                p.getInventory().setHelmet(armor.get("helmet"));
            }
            if (armor.containsKey("chestplate")) {
                p.getInventory().setChestplate(armor.get("chestplate"));
            }
            if (armor.containsKey("leggings")) {
                p.getInventory().setLeggings(armor.get("leggings"));
            }
            if (armor.containsKey("boots")) {
                p.getInventory().setBoots(armor.get("boots"));
            }
            Room.awaiting.remove(p);
            Room.aplaying.put(p, this);
            p.sendTitle("§a决斗开始!", "§b努力击败对手吧!");
        }
        this.waiting.get(0).teleport(pos2);
        this.waiting.get(1).teleport(pos3);
        this.playing.addAll(this.waiting);
        this.waiting.clear();
        this.isStartChemical = false;
        this.waittime = 15;
        this.isPlaying = true;
    }

    @Override
    public void gameEnd() {
        if (playing != null) {
            for (Player player : this.playing) {
                if (player.isOnline()) {
                    Room.aplaying.remove(player);
                    player.setGamemode(0);
                    player.getInventory().clearAll();
                    LobbyTool.returnToLobby(player);
                }
            }
        }
        this.playing.clear();
        this.isPlaying = false;
        DuelRoom.duelrooms.remove(this.roomId);
        this.roomlevel.unload();
        FileUtil f = new FileUtil();
        f.deleteDirectory(new File(Server.getInstance().getDataPath() + "/worlds/" + "duellevel" + this.roomId));
    }

    @Override
    public void joinRoom(Player player) {
        player.getInventory().clearAll();
        Item hub = Item.get(355,0,1);
        hub.setCustomName("退出等待");
        player.getInventory().setItem(2,hub);
        this.waiting.add(player);
        Room.awaiting.put(player, this);
        player.teleport(this.pos1);
        player.sendMessage("§a成功加入房间 §eDuel-" + this.roomId + "!");
        player.sendMessage("§a输入/hub即可退出当前房间!");
        player.sendTitle("§e地图名称: " + this.mapname);
        player.setGamemode(0);
        NameTool.setPlayerNameTag(player,"(name)" + "\n" + "(health)" + " §c❤");
        NameTool.setPlayerDisplayName(player,"(name)");
    }

    @Override
    public void playerAccidentQuit(Player player) {
        if (this.waiting.contains(player)){
            this.waiting.remove(player);
            Room.awaiting.remove(player);
        }
        if (this.playing.contains(player)){
            this.playing.remove(player);
            Room.aplaying.remove(player);
            for (Player p : this.roomlevel.getPlayers().values()){
                p.sendMessage("§c玩家 §e" + player.getName() + " §c中途退出游戏，已被扣除分数5!");
            }
            Score.remScore(player,5);
        }
        player.setGamemode(0);
    }

    public boolean canJoin(){
        if (this.isPlaying != true && this.waiting.size() < 2){
            return true;
        }else{
            return false;
        }
    }

    public static int getModeWaiting(String mode){
        int waiting = 0;
        for(DuelRoom room : DuelRoom.duelrooms.values()){
            if (room.mode.equals(mode) && room.isPlaying != true){
                waiting += room.waiting.size();
            }
        }
        return waiting;
    }

    public static int getModePlaying(String mode){
        int playing = 0;
        for(DuelRoom room : DuelRoom.duelrooms.values()){
            if (room.mode.equals(mode) && room.isPlaying == true){
                playing += room.playing.size();
            }
        }
        return playing;
    }
}
