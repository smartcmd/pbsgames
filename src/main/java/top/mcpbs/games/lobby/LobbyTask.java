package top.mcpbs.games.lobby;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.Level;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.PluginTask;
import top.mcpbs.games.Main;
import top.mcpbs.games.playerinfo.coin.Coin;
import top.mcpbs.games.playerinfo.diamond.Diamond;
import top.mcpbs.games.playerinfo.score.Score;

import java.io.File;
import java.util.ArrayList;

public class LobbyTask extends PluginTask {

    Level lobby;

    public LobbyTask(Plugin owner) {
        super(owner);
        this.lobby = Server.getInstance().getLevelByName("world");
    }

    @Override
    public void onRun(int i) {
        for(Player player : lobby.getPlayers().values()) {
            if (player.isOnline()) {
                player.setHealth(player.getMaxHealth());
                player.getFoodData().setLevel(20);
                if (player.getY() <= 0) {
                    player.sendMessage("§a>>已将你传送回主城!");
                    LobbyTool.returnToLobby(player);
                }
                if (new File(owner.getDataFolder() + "/playerdata/" + player.getName() + ".yml").exists()) {
                    ArrayList l = new ArrayList();
                    l.add("玩家ID");
                    l.add("§a" + player.getName());
                    l.add(" ");
                    l.add("你的硬币: §a" + Coin.getCoinNum(player));
                    l.add("你的钻石: §a" + Diamond.getDiamondNum(player));
                    l.add("你的分数: §a" + Score.getScore(player));
                    l.add("  ");
                    l.add("Ping: §a" + player.getPing() + "ms");
                    l.add("Online: §a" + Server.getInstance().getOnlinePlayers().values().toArray().length);
                    l.add("   ");
                    l.add("§eplay.mcpbs.top");
                    Main.s.showScoreboard(player, "§l§epbsgames", l);
                }
            }
        }
    }
}
