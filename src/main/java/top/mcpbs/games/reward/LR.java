package top.mcpbs.games.reward;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.utils.Config;
import top.mcpbs.games.Main;
import top.mcpbs.games.playerinfo.diamond.Diamond;
import top.mcpbs.games.playerinfo.score.Score;
import top.mcpbs.games.util.DateUtil;

public class LR implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        Config c = new Config(Main.plugin.getDataFolder() + "/每日奖励.yml");
        if(!c.exists(p.getName())){
            c.set(p.getName(), DateUtil.getDate("yyyy/MM/dd"));
            c.save();
            Score.addScore(p,5);
            Diamond.addDiamond(p,2);
            p.sendMessage("§e每日登录奖励 §bdiamond + 2 §dscore + 5");
        }else if(!c.get(p.getName()).equals(DateUtil.getDate("yyyy/MM/dd"))) {
            c.remove(p.getName());
            c.save();
            c.set(p.getName(),DateUtil.getDate("yyyy/MM/dd"));
            c.save();
            Score.addScore(p,5);
            Diamond.addDiamond(p,2);
            p.sendMessage("§e每日登录奖励 §bdiamond + 2 §dscore + 5");
        }
    }
}
