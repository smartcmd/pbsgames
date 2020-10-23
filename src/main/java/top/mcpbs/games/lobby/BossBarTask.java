package top.mcpbs.games.lobby;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Level;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.DummyBossBar;
import top.mcpbs.games.playerinfo.coin.Coin;
import top.mcpbs.games.playerinfo.diamond.Diamond;
import top.mcpbs.games.playerinfo.score.Score;

import java.util.HashMap;
import java.util.Set;

public class BossBarTask extends PluginTask {

    public HashMap<Player, DummyBossBar> bossbarmap;
    public int tick;
    public Level lobby;

    public BossBarTask(Plugin owner) {
        super(owner);
        bossbarmap = new HashMap();
        this.tick = 0;
        this.lobby = Server.getInstance().getLevelByName("world");
    }

    @Override
    public void onRun(int i) {
        tick += 1;
        for (Player p : (Set<Player>) ((HashMap)bossbarmap.clone()).keySet()){
            if (!lobby.getPlayers().containsValue(p)){
                p.removeBossBar(bossbarmap.get(p).getBossBarId());
                bossbarmap.remove(p);
            }
        }
        for (Player player : lobby.getPlayers().values()) {
            if (player.isOnline()) {
                if (!bossbarmap.containsKey(player)) {
                    DummyBossBar bossbar = new DummyBossBar.Builder(player).build();
                    bossbarmap.put(player, bossbar);
                    player.createBossBar(bossbar);
                }
                if (1 <= tick && tick <= 100) {
                    bossbarmap.get(player).setText("§a你正在游玩§bPBS§cgames§d小游戏服务器!");
                    bossbarmap.get(player).setLength(tick);
                }
                if (101 <= tick && tick <= 200) {
                    bossbarmap.get(player).setText("§ePLAY.MCPBS.TOP");
                    bossbarmap.get(player).setLength(tick - 100);
                }
                if (201 <= tick && tick <= 300) {
                    bossbarmap.get(player).setText("§6你目前拥有的diamond: §a" + Diamond.getDiamondNum(player));
                    bossbarmap.get(player).setLength(tick - 200);
                }
                if (301 <= tick && tick <= 400) {
                    bossbarmap.get(player).setText("§b你的分数: §c" + Score.getScore(player));
                    bossbarmap.get(player).setLength(tick - 300);
                }
                if (401 <= tick && tick <= 500) {
                    bossbarmap.get(player).setText("§b你的硬币: §c" + Coin.getCoinNum(player));
                    bossbarmap.get(player).setLength(tick - 400);
                }
                bossbarmap.get(player).reshow();
            }
        }
        if (tick >= 500){
            tick = 0;
        }
    }
}
