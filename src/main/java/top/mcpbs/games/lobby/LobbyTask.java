package top.mcpbs.games.lobby;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.item.Item;
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
        Item sword = Item.get(276);
        Item book = Item.get(403);
        Item personality = Item.get(264);
        Item head = Item.get(397,3,1);
        Item chest = Item.get(130);
        sword.setCustomName("§a小游戏列表");
        book.setCustomName("§b个人信息");
        personality.setCustomName("§c个性工坊");
        head.setCustomName("§d好友系统");
        chest.setCustomName("§e抽奖箱(在?来一发?)");
        for(Player player : lobby.getPlayers().values()) {
            if (player.isOnline()) {
                player.setHealth(player.getMaxHealth());
                player.getFoodData().setLevel(20);
                if (player.getInventory().isEmpty()) {
                    player.getInventory().setItem(1, sword);
                    player.getInventory().setItem(2, personality);
                    player.getInventory().setItem(3, head);
                    player.getInventory().setItem(4, chest);
                }
                if (player.getY() <= 0) {
                    player.sendMessage("§a已将你传送回主城!");
                    player.teleport(Main.lobby);
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
