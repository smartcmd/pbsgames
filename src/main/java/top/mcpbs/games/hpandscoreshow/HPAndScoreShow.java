package top.mcpbs.games.hpandscoreshow;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.PluginTask;
import top.mcpbs.games.playerinfo.score.Score;

public class HPAndScoreShow extends PluginTask {
    public HPAndScoreShow(Plugin owner) {
        super(owner);
    }

    @Override
    public void onRun(int i) {
        for(Player p : Server.getInstance().getOnlinePlayers().values()){
            p.setNameTag(p.getDisplayName() + "\n" + "Score: §a" + Score.getScore(p) + "\n" + (int)p.getHealth() + "§c❤");
            /**
             * setNameTag()是指玩家头上那个东西
             * setDisplayName()是指玩家聊天栏显示的名字...
             */
        }
    }
}
