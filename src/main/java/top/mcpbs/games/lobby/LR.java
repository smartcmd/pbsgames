package top.mcpbs.games.lobby;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.player.PlayerCommandPreprocessEvent;
import cn.nukkit.event.player.PlayerDropItemEvent;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.server.ServerCommandEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindowSimple;
import top.mcpbs.games.FormID;
import top.mcpbs.games.chao.Chao;
import top.mcpbs.games.duel.DuelRoom;
import top.mcpbs.games.rush.RushRoom;
import top.mcpbs.games.uhc.UHCRoom;

public class LR implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event){
        if (event.getEntity() instanceof Player){
            Player player = (Player)event.getEntity();
            if (player.getLevel().getName().equals("world")){
                event.setCancelled();
            }
        }
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (player.getLevel().getName().equals("world")) {
            switch (player.getInventory().getItemInHand().getId()) {
                case 276:
                    FormWindowSimple xyx = new FormWindowSimple("§c小游戏列表", "§6选择一个小游戏开始游玩吧！");
                    xyx.addButton(new ElementButton("§l§d药水决斗" + "\n§l§f游戏中: §a" + DuelRoom.getModePlaying("potpvp") + " §f等待中: §a" + DuelRoom.getModeWaiting("potpvp"), new ElementButtonImageData("path", "textures/items/potion_bottle_splash_heal")));
                    xyx.addButton(new ElementButton("§l§e决战§6黎明" + "\n§l§f游戏中: §a" + DuelRoom.getModePlaying("builduhc") + " §f等待中: §a" + DuelRoom.getModeWaiting("builduhc"),new ElementButtonImageData("path","textures/blocks/log_oak_top")));
                    xyx.addButton(new ElementButton("§l§6战桥" + "\n§l§f游戏中: §a" + RushRoom.getAllPlaying() + " §f等待中: §a" + RushRoom.getAllWaiting(),new ElementButtonImageData("path","textures/blocks/cake_top")));
                    xyx.addButton(new ElementButton("§l§a归心似箭" + "\n§l§f游戏中: §a" + DuelRoom.getModePlaying("bowpvp") + " §f等待中: §a" + DuelRoom.getModeWaiting("bowpvp"),new ElementButtonImageData("path","textures/items/bow_pulling_2")));
                    xyx.addButton(new ElementButton("§l§b空手决斗" + "\n§l§f游戏中: §a" + DuelRoom.getModePlaying("handpvp") + " §f等待中: §a" + DuelRoom.getModeWaiting("handpvp"),new ElementButtonImageData("path","textures/items/bone")));
                    xyx.addButton(new ElementButton("§l§c天坑大乱斗" + "\n§l§f游戏中: §a" + Chao.getPlaying(),new ElementButtonImageData("path","textures/items/stone_sword")));
                    xyx.addButton(new ElementButton("§l§e极限生存冠军" + "\n§l§f游戏中: §a" + UHCRoom.getAllPlaying() + " §f等待中: §a" + UHCRoom.getAllWaiting(),new ElementButtonImageData("path","textures/items/diamond_pickaxe")));
                    player.showFormWindow(xyx, FormID.LOBBY_GAMESLIST_FORM);
                    break;
                case 264:
                    FormWindowSimple gx = new FormWindowSimple("§c个性工坊","§6欢迎来到个性工坊!");
                    gx.addButton(new ElementButton("§e我的粒子",new ElementButtonImageData("path","textures/ui/realms_particles")));
                    gx.addButton(new ElementButton("§6我的称号",new ElementButtonImageData("path","textures/items/book_enchanted")));
                    player.showFormWindow(gx, FormID.PERSONALITY_FORM);
                    break;
                case 397:
                    Server.getInstance().getCommandMap().dispatch(player,"friend");
                    break;
                case 130:
                    Server.getInstance().getCommandMap().dispatch(player,"lottery");
                    break;
            }
        }
    }
    @EventHandler
    public void onFormResponded(PlayerFormRespondedEvent event){
        Player player = event.getPlayer();
        if (event.getFormID() == FormID.LOBBY_GAMESLIST_FORM) {
            FormResponseSimple response = (FormResponseSimple) event.getResponse();
            if (response == null){
                return;
            }
            switch (response.getClickedButtonId()) {
                case 0:
                    Server.getInstance().getCommandMap().dispatch(player,"potpvp");
                    break;
                case 1:
                    Server.getInstance().getCommandMap().dispatch(player,"builduhc");
                    break;
                case 2:
                    Server.getInstance().getCommandMap().dispatch(player,"joinrush");
                    break;
                case 3:
                    Server.getInstance().getCommandMap().dispatch(player,"bowpvp");
                    break;
                case 4:
                    Server.getInstance().getCommandMap().dispatch(player,"handpvp");
                    break;
                case 5:
                    Server.getInstance().getCommandMap().dispatch(player,"chao");
                    break;
                case 6:
                    Server.getInstance().getCommandMap().dispatch(player,"joinuhc");
                    break;
            }
        }
        if (event.getFormID() == FormID.PERSONALITY_FORM){
            FormResponseSimple response = (FormResponseSimple) event.getResponse();
            if (response == null){
                return;
            }
            switch (response.getClickedButtonId()){
                case 0:
                    Server.getInstance().getCommandMap().dispatch(player,"particle");
                    break;
                case 1:
                    Server.getInstance().getCommandMap().dispatch(player,"designation");
                    break;
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if (event.getBlock().getLevel().getName().equals("world") && event.getPlayer().getGamemode() != 1){
            event.setCancelled();
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        if (event.getPlayer().getLevel().getName().equals("world") && event.getPlayer().getGamemode() != 1){
            event.setCancelled();
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event){
        if (event.getPlayer().getLevel().getName().equals("world") && event.getPlayer().getGamemode() != 1){
            event.setCancelled();
        }
    }

    @EventHandler
    public void onServerReload(ServerCommandEvent event){
        if (event.getCommand().equals("reload")){
            for (Player p : Server.getInstance().getOnlinePlayers().values()){
                for (Long l : p.getDummyBossBars().keySet()){
                    p.removeBossBar(l);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerReload(PlayerCommandPreprocessEvent event){
        if (event.getMessage().equals("/reload")){
            for (Player p : Server.getInstance().getOnlinePlayers().values()){
                for (Long l : p.getDummyBossBars().keySet()){
                    p.removeBossBar(l);
                }
            }
        }
    }
}
