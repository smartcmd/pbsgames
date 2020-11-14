package top.mcpbs.games.lottery;

import cn.nukkit.Player;
import cn.nukkit.entity.weather.EntityLightning;
import cn.nukkit.utils.Config;
import top.mcpbs.games.Main;
import top.mcpbs.games.name.NameTool;
import top.mcpbs.games.particle.ParticleTool;
import top.mcpbs.games.playerinfo.coin.Coin;
import top.mcpbs.games.playerinfo.diamond.Diamond;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LotteryTool {

    public static Config c = new Config(Main.plugin.getDataFolder() + "/lottery.yml");
    private static Random r = new Random();

    public static HashMap<String, String> getLotteryAllPrize(String lname){
        return (HashMap<String, String>) c.get(lname + ".things");
    }

    public static HashMap<String, Object> getLotteryPriceInfo(String lname){
        HashMap<String, Object> result = new HashMap<>();
        result.put("type",c.get(lname + ".price.type"));
        result.put("price",c.get(lname + ".price.price"));
        return result;
    }

    public static String getLotteryName(String lname){
        return (String) c.get(lname + ".name");
    }

    public static String getLotteryDescription(String lname){
        return (String) c.get(lname + ".description");
    }

    public static ArrayList<HashMap> getAllLottery(){
        ArrayList<HashMap> result = new ArrayList<>();
        for(Map.Entry e : c.getAll().entrySet()){
            result.add((HashMap) e.getValue());
        }
        return result;
    }

    public static DrawPrizeModel drawPrize(String lname){
        HashMap<String, String> prizes = getLotteryAllPrize(lname);
        int count = prizes.size();
        int rannum = r.nextInt(count);
        String prize = prizes.keySet().toArray(new String[0])[rannum];
        String type = null;
        for (Map.Entry<String,String> e : prizes.entrySet()){
            if (e.getKey().equals(prize)){
                type = e.getValue();
                break;
            }
        }
        return new DrawPrizeModel(type,prize);
    }

    public static void givePrizes(Player player, DrawPrizeModel model){
        for (Player p : player.getLevel().getPlayers().values()) {
            if (model.type.equals("ch")) {
                p.sendMessage("§6抽奖箱 §7» §e恭喜玩家 §b" + player.getName() + "§a获得了称号 §f" + "[" + model.prize + "]");
            }
            if (model.type.equals("p")){
                p.sendMessage("§6抽奖箱 §7» §e恭喜玩家 §b" +player.getName() + "§a获得了粒子效果 §f" + model.prize);
            }
            if (model.type.equals("diamond")){
                p.sendMessage("§6抽奖箱 §7» §e恭喜玩家 §b" +player.getName() + "§a获得了钻石 §f" + model.prize + " §a个");
            }
            if (model.type.equals("coin")){
                p.sendMessage("§6抽奖箱 §7» §e恭喜玩家 §b" +player.getName() + "§a获得了硬币 §f" + model.prize + " §a个");
            }
        }
        switch(model.type){
            case "ch":
                NameTool.PlayerAddDesignation(player, (String) model.prize);
                break;
            case "p":
                ParticleTool.addPlayerParticle(player, (String) model.prize);
                break;
            case "diamond":
                Diamond.addDiamond(player, (Integer) model.prize);
                break;
            case "coin":
                Coin.addCoin(player, (Integer) model.prize);
                break;
        }
        EntityLightning l = new EntityLightning(player.getPosition().getChunk(),EntityLightning.getDefaultNBT(player.getPosition()));
        l.setEffect(false);
        l.spawnToAll();
    }
}

class DrawPrizeModel{

    public String type;
    public Object prize;

    public DrawPrizeModel(String type, Object prize){
        this.type = type;
        this.prize = prize;
    }
}