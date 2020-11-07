package top.mcpbs.games.hpshow;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.PluginTask;
import top.mcpbs.games.playerinfo.score.Score;

public class HPShow extends PluginTask {
    public HPShow(Plugin owner) {
        super(owner);
    }

    @Override
    public void onRun(int i) {
        for(Player p : Server.getInstance().getOnlinePlayers().values()){
            String name = p.getNameTag();
            String name2 = name.substring(0,name.length() - 8);
            p.setNameTag(name2 + (int)p.getHealth() + " §c❤");
            /**
             * setNameTag()是指玩家头上那个东西
             * setDisplayName()是指玩家聊天栏显示的名字...
             */
        }
    }
}
