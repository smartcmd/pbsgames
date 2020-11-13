package top.mcpbs.games.lobby;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.item.Item;
import cn.nukkit.level.Position;
import top.mcpbs.games.name.NameTool;

import static top.mcpbs.games.name.NameTool.getPlayerUseDesignation;

public class LobbyTool {

    public static Position lobby;
    public static Item sword = Item.get(276);
    public static Item book = Item.get(403);
    public static Item personality = Item.get(264);
    public static Item head = Item.get(397,3,1);
    public static Item chest = Item.get(130);

    static {
        sword.setCustomName("§a小游戏列表");
        book.setCustomName("§b个人信息");
        personality.setCustomName("§c个性工坊");
        head.setCustomName("§d好友系统");
        chest.setCustomName("§e抽奖箱(在?来一发?)");
        lobby = new Position(-5, 25, 19, Server.getInstance().getLevelByName("world"));
    }
    public static void returnToLobby(Player player){
        player.getInventory().clearAll();
        player.getInventory().setItem(1, sword);
        player.getInventory().setItem(2, personality);
        player.getInventory().setItem(3, head);
        player.getInventory().setItem(4, chest);
        String ch = getPlayerUseDesignation(player);
        NameTool.setPlayerNameTag(player,"§7" + "/name/" + "\n" + "/ch/");
        NameTool.setPlayerDisplayName(player,"§7" + "/name/");
        player.teleport(LobbyTool.lobby);
    }
}
