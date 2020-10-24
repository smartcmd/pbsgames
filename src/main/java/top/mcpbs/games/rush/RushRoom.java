package top.mcpbs.games.rush;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.Config;
import top.mcpbs.games.Main;
import top.mcpbs.games.playerinfo.score.Score;
import top.mcpbs.games.room.Room;
import top.mcpbs.games.util.FileUtil;
import top.mcpbs.games.waitroom.WaitRoom;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class RushRoom extends Room {

    public static HashMap<Integer, RushRoom> RushRooms = new HashMap();
    public HashMap<Vector3, Integer> rushblocks = new HashMap();
    public HashMap<Player, Integer> scores = new HashMap<>();
    public HashMap<Player, Position> pos = new HashMap<>();
    public int waittime = 15;
    public Position pos1;
    public Position pos2;
    public Position pos3;

    public RushRoom(){
        Config rushlevel = new Config(Main.plugin.getDataFolder() + "/rushlevel.yml");

        Random r = new Random();
        int rnum = r.nextInt(rushlevel.getAll().size()) + 1;
        HashMap<String, Object> levelinfo = (HashMap<String, Object>) rushlevel.get("rushtmp" + rnum);
        this.mapname = (String) levelinfo.get("mapname");

        int id = 0;
        int max = 0;
        ArrayList<Integer> tmp = new ArrayList();
        for (int idt : RushRoom.RushRooms.keySet()){
            tmp.add(idt);
            max = idt;
        }
        for (int idtt : tmp){
            if (idtt > max){
                max = idtt;
            }
        }
        for (int i = 1; i <= (max + 1); i++){
            if (!RushRoom.RushRooms.containsKey(i)){
                id = i;
                break;
            }
        }
        if (id == 0){
            id = 1;
        }
        this.roomId = id;

        FileUtil f = new FileUtil();
        f.deleteDirectory(new File(Server.getInstance().getDataPath() + "/worlds/rushlevel" + id));
        new File(Server.getInstance().getDataPath() + "/worlds/rushlevel" + id + "/region").mkdirs();
        f.copyDir(Server.getInstance().getDataPath() + "/worlds/rushtmp" + rnum +"/region",Server.getInstance().getDataPath() + "/worlds/rushlevel" + id + "/region");
        Server.getInstance().generateLevel("rushlevel" + id);
        this.roomlevel = Server.getInstance().getLevelByName("rushlevel" + this.roomId);

        ArrayList<Double> pos1tmp = (ArrayList) rushlevel.get("rushtmp" + rnum + ".pos1");
        ArrayList<Double> pos2tmp = (ArrayList) rushlevel.get("rushtmp" + rnum + ".pos2");
        ArrayList<Double> pos3tmp = (ArrayList) rushlevel.get("rushtmp" + rnum + ".pos3");
        pos1 = new Position(pos1tmp.get(0),pos1tmp.get(1),pos1tmp.get(2),this.roomlevel);
        pos2 = new Position(pos2tmp.get(0),pos2tmp.get(1),pos2tmp.get(2),this.roomlevel);
        pos3 = new Position(pos3tmp.get(0),pos3tmp.get(1),pos3tmp.get(2),this.roomlevel);

        RushRooms.put(this.roomId,this);
    }
    @Override
    public void gameStart() {
        this.roomlevel.setBlock(pos2, Block.get(92));
        this.roomlevel.setBlock(pos3, Block.get(92));
        for (Player player : waiting){
            player.getInventory().clearAll();
            Room.aplaying.put(player,this);
            Room.awaiting.remove(player);

            player.getInventory().addItem(Item.get(24,0,256));
            Item pickaxe = Item.get(278);
            pickaxe.addEnchantment(Enchantment.get(Enchantment.ID_EFFICIENCY).setLevel(5,false));
            player.getInventory().addItem(pickaxe);
            Item stick = Item.get(280);
            stick.addEnchantment(Enchantment.get(12).setLevel(5,false));
            player.getInventory().addItem(stick);

            player.sendTitle("§a游戏开始!","§b努力击败对手吧!");
        }
        this.waiting.get(0).teleport(pos2);
        this.scores.put(this.waiting.get(0),0);
        this.pos.put(this.waiting.get(0),pos2);

        this.waiting.get(1).teleport(pos3);
        this.scores.put(this.waiting.get(1),0);
        this.pos.put(this.waiting.get(1),pos3);

        this.playing.addAll(waiting);
        this.waiting.clear();
        this.isPlaying = true;
        this.isStartChemical = false;
        this.waittime = 15;
    }

    @Override
    public void gameEnd() {
        if (playing != null) {
            for (Player player : this.playing) {
                if (player.isOnline()) {
                    Room.aplaying.remove(player);
                    player.setGamemode(0);
                    player.getInventory().clearAll();
                    player.teleport(Main.lobby);
                }
            }
        }
        this.playing.clear();
        this.isPlaying = false;
        this.pos.clear();
        this.scores.clear();
        RushRoom.RushRooms.remove(this.roomId);
        FileUtil f = new FileUtil();
        this.roomlevel.unload();
        f.deleteDirectory(new File(Server.getInstance().getDataPath() + "/worlds/" + "rushlevel" + this.roomId));
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
        player.sendMessage("§a成功加入房间 §erush-" + this.roomId + "!");
        player.sendMessage("§a输入/hub即可退出当前房间!");
        player.sendTitle("§e地图名称: " + this.mapname);
        player.setGamemode(0);
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
                p.sendMessage("§c玩家 §e" + player.getName() + " §c消极比赛，已被扣除分数5!");
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

    public static int getAllWaiting(){
        int waiting = 0;
        for(RushRoom room : RushRoom.RushRooms.values()){
            waiting += room.waiting.size();
        }
        return waiting;
    }

    public static int getAllPlaying(){
        int playing = 0;
        for(RushRoom room : RushRoom.RushRooms.values()){
            playing += room.playing.size();
        }
        return playing;
    }
}
