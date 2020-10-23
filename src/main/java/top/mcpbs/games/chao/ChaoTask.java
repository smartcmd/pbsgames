package top.mcpbs.games.chao;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.level.Level;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.potion.Effect;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.Config;
import top.mcpbs.games.Main;
import top.mcpbs.games.util.DateUtil;

import java.util.ArrayList;
import java.util.HashMap;

public class ChaoTask extends PluginTask {

    public static Level chao;
    public static HashMap<Player, Boolean> players = new HashMap();
    public static HashMap<Player, HashMap<String, Number>> chaoplayerinfo = new HashMap();
    public static Config chaoconfig;

    public ChaoTask(Plugin owner) {
        super(owner);
        chaoconfig = new Config(owner.getDataFolder() + "/chao.yml");
        Server.getInstance().loadLevel((String) chaoconfig.get("world"));
        chao = Server.getInstance().getLevelByName((String) chaoconfig.get("world"));
        chao.setTime(6000);
    }

    @Override
    public void onRun(int i) {
        for (Player player : ChaoTask.players.keySet()){
            player.getFoodData().setLevel(20);
            if (player.getY() <= (int)this.chaoconfig.get("line") && players.get(player) == false){
                player.sendTitle("§cPVP模式§e开启！","§a努力击杀其他玩家吧！");
                Effect n = Effect.getEffect(Effect.NIGHT_VISION);
                n.setDuration(99999*20);
                player.addEffect(n);
                player.getInventory().clearAll();
                if (player.getInventory().getHelmet().isNull()){
                    Item hel = Item.get(302,0);
                    hel.addEnchantment(Enchantment.get(17).setLevel(32767,false));
                    player.getInventory().setHelmet(hel);
                }
                if (player.getInventory().getChestplate().isNull()){
                    Item che = Item.get(303,0);
                    che.addEnchantment(Enchantment.get(17).setLevel(32767,false));
                    player.getInventory().setChestplate(che);
                }
                if (player.getInventory().getLeggings().isNull()){
                    Item leg = Item.get(304,0);
                    leg.addEnchantment(Enchantment.get(17).setLevel(32767,false));
                    player.getInventory().setLeggings(leg);
                }
                if (player.getInventory().getBoots().isNull()){
                    Item boo = Item.get(305,0);
                    boo.addEnchantment(Enchantment.get(17).setLevel(32767,false));
                    player.getInventory().setBoots(boo);
                }
                Item bow = Item.get(261);
                bow.addEnchantment(Enchantment.get(22).setLevel(1));
                bow.addEnchantment(Enchantment.get(17).setLevel(32767,false));
                player.getInventory().addItem(bow);
                Item a = Item.get(262,0,64);
                player.getInventory().addItem(a);
                Item c = Item.get(322,0,1);
                player.getInventory().addItem(c);
                Item d = Item.get(272,0,1);
                d.addEnchantment(Enchantment.get(17).setLevel(32767,false));
                player.getInventory().addItem(d);
                Item e = Item.get(346,0,1);
                e.addEnchantment(Enchantment.get(17).setLevel(32767,false));
                player.getInventory().addItem(e);
                Item f = Item.get(332,0,16);
                player.getInventory().addItem(f);
                players.put(player,true);
            }
            ArrayList l = new ArrayList();
            l.add("§7#" + DateUtil.getDate("yyyy/MM/dd"));
            l.add(" ");
            l.add("击杀");
            l.add("§a" + chaoplayerinfo.get(player).get("kill") + " ");
            l.add("死亡");
            l.add("§a" + chaoplayerinfo.get(player).get("death") + "  ");
            int kill = (Integer) chaoplayerinfo.get(player).get("kill");
            int death = (Integer) chaoplayerinfo.get(player).get("death");
            if (kill == 0){
                kill = 1;
            }
            if (death == 0){
                death = 1;
            }
            double kdr = (double)kill / (double)death;
            l.add("KDR");
            l.add("§a" + String.format("%.3f", kdr)  + "   ");
            l.add("乱斗币");
            l.add("§a" + String.format("%.2f", chaoplayerinfo.get(player).get("coin")) + "    ");
            l.add("     ");
            l.add("§eplay.mcpbs.top");
            Main.s.showScoreboard(player, "§l§e天坑大乱斗", l);
        }
    }

    public static int getPlaying(){
        return ChaoTask.players.size();
    }
}
