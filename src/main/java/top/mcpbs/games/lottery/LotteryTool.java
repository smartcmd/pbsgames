package top.mcpbs.games.lottery;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import top.mcpbs.games.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LotteryTool {

    private static Config c = new Config(Main.plugin.getDataFolder() + "/lottery.yml");
    private static Random r = new Random();

    public static HashMap<String, String> getLotteryAllPrize(String lname){
        HashMap<String, String> all = (HashMap) c.get(lname);
        HashMap<String, String> result = new HashMap<>();
        for (Map.Entry<String, String> e : all.entrySet()){
            if (e.getKey().equals("ch")){
                result.put("ch",e.getValue());
            }
            if (e.getKey().equals("p")){
                result.put("p",e.getValue());
            }
        }
        return result;
    }

    public static HashMap<String, Object> getLotteryPriceInfo(String lname){
        HashMap<String, String> all = (HashMap) c.get(lname);
        HashMap<String, Object> result = new HashMap<>();
        result.put("type",all.get("price.type"));
        result.put("price",all.get("price.price"));
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
        int rannum = r.nextInt(count) + 1;
        String prize = prizes.values().toArray(new String[0])[rannum];
        String type = null;
        for (Map.Entry<String,String> e : prizes.entrySet()){
            if (e.getValue().equals(prize)){
                type = e.getKey();
                break;
            }
        }
        return new DrawPrizeModel(type,prize);
    }

    public static void givePrizes(Player player, DrawPrizeModel model){
        switch(model.type){
            case "ch":
                //待更新
                break;
            case "p":
                //待更新
                break;
        }
    }
}

class DrawPrizeModel{

    public String type;
    public String prize;

    public DrawPrizeModel(String type, String prize){
        this.type = type;
        this.prize = prize;
    }
}