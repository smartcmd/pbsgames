package top.mcpbs.games.npc;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;

public class LR implements Listener {
    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if (event.getEntity() instanceof NPC){
            event.setCancelled();
            if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK){
                EntityDamageByEntityEvent event1 = (EntityDamageByEntityEvent) event;
                if (event1.getDamager() instanceof Player){
                    NPC npc = (NPC) event.getEntity();
                    if (Server.getInstance().getCommandMap().getCommands().containsKey(npc.cmd)){
                        Server.getInstance().dispatchCommand((Player)event1.getDamager(),npc.cmd);
                    }
                }
            }
        }
    }
}
