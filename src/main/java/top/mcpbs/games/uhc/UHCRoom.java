package top.mcpbs.games.uhc;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.item.Item;
import cn.nukkit.level.GameRule;
import cn.nukkit.math.Vector3;
import top.mcpbs.games.Main;
import top.mcpbs.games.designation.SetName;
import top.mcpbs.games.playerinfo.score.Score;
import top.mcpbs.games.room.Room;
import top.mcpbs.games.uhc.uhcworldgenerator.NormalGenerator;
import top.mcpbs.games.util.FileUtil;
import top.mcpbs.games.waitroom.WaitRoom;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class UHCRoom extends Room {

    public static HashMap<Integer,UHCRoom> uhcrooms = new HashMap();
    public HashMap<Player, Integer> killnum = new HashMap<>();
    public HashMap<Player, Team> playerteam = new HashMap<>();
    public HashMap<Player, Boolean>isdead = new HashMap<>();
    public ArrayList<Team> team = new ArrayList<>();
    public double boundary = 1000;
    int waittime = 60;
    int gametime = 60 * 25;

    public UHCRoom(){
        int id = 0;
        int max = 0;
        ArrayList<Integer> tmp = new ArrayList();
        for (int idt : UHCRoom.uhcrooms.keySet()){
            tmp.add(idt);
            max = idt;
        }
        for (int idtt : tmp){
            if (idtt > max){
                max = idtt;
            }
        }
        for (int i = 1; i <= (max + 1); i++){
            if (!UHCRoom.uhcrooms.containsKey(i)){
                id = i;
                break;
            }
        }
        if (id == 0){
            id = 1;
        }
        this.roomId = id;

        FileUtil f = new FileUtil();
        f.deleteDirectory(new File(Server.getInstance().getDataPath() + "/worlds/uhclevel" + this.roomId));
        Server.getInstance().generateLevel("uhclevel" + this.roomId,(new Random()).nextLong(), NormalGenerator.class);
        this.roomlevel = Server.getInstance().getLevelByName("uhclevel" + this.roomId);
        this.roomlevel.setTime(6000);
        this.roomlevel.getGameRules().setGameRule(GameRule.DO_DAYLIGHT_CYCLE,false);
        this.roomlevel.getGameRules().setGameRule(GameRule.SHOW_COORDINATES,true);
        this.roomlevel.getGameRules().setGameRule(GameRule.NATURAL_REGENERATION,false);
        this.roomlevel.setSpawnLocation(new Vector3(0,0,0));

        for (int i = 1;i <= 8;i++){
            Team team = new Team(new ArrayList<>(),i,this.roomlevel);
            this.team.add(i - 1,team);
        }

        UHCRoom.uhcrooms.put(this.roomId,this);
        this.waitRoom = new WaitRoom();
    }

    @Override
    public void gameStart() {
        int tmp = 0;
        for (Player player : waiting){
            Team team = this.team.get(tmp);
            team.player.add(player);
            this.playerteam.put(player,team);
            tmp += 1;
            if (tmp == 8){
                tmp = 0;
            }
            SetName.setName(player,this.playerteam.get(player).color + "[" + this.playerteam.get(player).teamname + "]" + player.getName());
            player.getInventory().clearAll();
            Item compass = Item.get(345);
            compass.setCustomName("§e最近的敌人");
            player.getInventory().addItem(compass);
            player.getInventory().addItem(Item.get(333,0,1));//boat
            player.teleport(team.spawnpos);
            Room.aplaying.put(player,this);
            Room.awaiting.remove(player,this);
            player.sendTitle("§a游戏开始!","§b努力存活下去吧!");
            player.sendMessage("§a世界正在生成，如果出现一次回弹为正常现象~");
            player.setGamemode(0);
            player.setMaxHealth(40);
            player.setHealth(player.getMaxHealth());
            player.getFoodData().setLevel(20);
        }
        this.playing.addAll(waiting);
        this.waiting.clear();
        this.isPlaying = true;
        this.waittime = 60;
        this.isStartChemical = false;
        this.waitRoom.remWaitRoom();
    }

    @Override
    public void gameEnd() {
        this.isPlaying = false;
        if (playing != null) {
            for (Player player : this.playing) {
                if (player.isOnline()) {
                    Room.aplaying.remove(player);
                    player.setGamemode(0);
                    player.getInventory().clearAll();
                    player.teleport(Main.lobby);
                    player.setMaxHealth(20);
                    SetName.ReloadToDefaultDesignation(player);
                }
            }
        }
        this.playing.clear();
        this.team.clear();
        UHCRoom.uhcrooms.remove(this.roomId);
        FileUtil f = new FileUtil();
        this.roomlevel.unload();
        f.deleteDirectory(new File(Server.getInstance().getDataPath() + "/worlds/" + "uhclevel" + this.roomId));
    }

    @Override
    public void joinRoom(Player player) {
        player.getInventory().clearAll();
        Item hub = Item.get(355,0,1);
        hub.setCustomName("退出等待");
        player.getInventory().setItem(2,hub);
        this.waiting.add(player);
        Room.awaiting.put(player,this);
        this.killnum.put(player,0);
        this.isdead.put(player,false);
        this.waitRoom.joinWaitRoom(player);
        player.sendMessage("§a成功加入房间 §eUHC-" + this.roomId + "!");
        player.sendMessage("§a输入/hub即可退出当前房间!");
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
            this.isdead.put(player,true);
        }
        player.setGamemode(0);
        player.setMaxHealth(20);
        SetName.ReloadToDefaultDesignation(player);
    }

    @Override
    public boolean canJoin() {
        if (this.isPlaying != true && this.waiting.size() < 32){
            return true;
        }else {
            return false;
        }
    }

    public static int getAllWaiting(){
        int waiting = 0;
        for(UHCRoom room : UHCRoom.uhcrooms.values()){
            waiting += room.waiting.size();
        }
        return waiting;
    }

    public static int getAllPlaying(){
        int playing = 0;
        for(UHCRoom room : UHCRoom.uhcrooms.values()){
            playing += room.playing.size();
        }
        return playing;
    }
}
