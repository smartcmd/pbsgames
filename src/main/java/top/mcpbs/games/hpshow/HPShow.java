package top.mcpbs.games.hpshow;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.PluginTask;
import top.mcpbs.games.playerinfo.score.Score;

import java.util.HashMap;

public class HPShow extends PluginTask {

    public static HashMap<Player,Integer> hp = new HashMap();

    public HPShow(Plugin owner) {
        super(owner);
    }

    @Override
    public void onRun(int i) {
        for(Player p : Server.getInstance().getOnlinePlayers().values()){
            if (!hp.containsKey(p)){
                hp.put(p, (int) p.getHealth());
                p.setNameTag(p.getNameTag() + "\n" + hp.get(p) + " §c❤");
                continue;
            }
            String name = p.getNameTag();
            String name2 = name.replace(hp.get(p) + " §c❤",(int)p.getHealth() + " §c❤");
            hp.put(p, (int) p.getHealth());
            /**
             * setNameTag()是指玩家头上那个东西
             * setDisplayName()是指玩家聊天栏显示的名字...
             */
        }
    }
}
