package top.mcpbs.games.helloform;


import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.server.DataPacketReceiveEvent;

public class LR implements Listener {
    @EventHandler
    public void onPlayerJoinPacket(DataPacketReceiveEvent event){
        if (event.getPacket().pid() == 113){
            HelloForm.showForm(event.getPlayer());
        }
    }
}
