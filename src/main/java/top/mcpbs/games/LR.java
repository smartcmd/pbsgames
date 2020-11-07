package top.mcpbs.games;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.item.EntityFishingHook;
import cn.nukkit.entity.weather.EntityLightning;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerCommandPreprocessEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.event.server.ServerCommandEvent;
import cn.nukkit.level.Level;
import cn.nukkit.level.ParticleEffect;
import cn.nukkit.level.Position;
import top.mcpbs.games.lobby.LobbyTool;
import top.mcpbs.games.room.Room;

public class LR implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        event.setJoinMessage("§a登入 » §7 " + event.getPlayer().getName() + " 登入了服务器 " + "§8[在线：" + Server.getInstance().getOnlinePlayers().size() + "]");
        this.Lightning(LobbyTool.lobby);
    }

    public void Lightning(Position position) {
        EntityLightning l = new EntityLightning(position.getChunk(),EntityLightning.getDefaultNBT(position));
        l.setEffect(false);
        l.spawnToAll();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Main.s.delCache(event.getPlayer());
        event.getQuitMessage().setText("§c登出 » §7 " + event.getPlayer().getName() + " 登出了服务器 " + "§8[在线：" + (Server.getInstance().getOnlinePlayers().size() - 1) + "]");
        event.getPlayer().getInventory().clearAll();
    }

    @EventHandler
    public void onPlayerChat(PlayerChatEvent event){
        event.setCancelled();
        Level level = event.getPlayer().getLevel();
        for (Player p : level.getPlayers().values()){
            p.sendMessage(event.getPlayer().getDisplayName() + "§f » " + event.getMessage());
        }
    }

    @EventHandler
    public void onEntityAttack(EntityDamageByEntityEvent event){
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player){
            event.getEntity().getLevel().addParticleEffect(event.getEntity().getPosition(), ParticleEffect.CRITICAL_HIT);
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
