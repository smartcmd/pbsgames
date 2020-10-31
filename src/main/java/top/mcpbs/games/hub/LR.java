package top.mcpbs.games.hub;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import top.mcpbs.games.lobby.LobbyTool;

public class LR implements Listener {
    @EventHandler
    public void onPlayerJoinPacket(DataPacketReceiveEvent event){
        if (event.getPacket().pid() == 113){
            LobbyTool.returnToLobby(event.getPlayer());
        }
    }
}
