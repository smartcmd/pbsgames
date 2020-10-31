package top.mcpbs.games.designation;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.form.response.FormResponseSimple;
import top.mcpbs.games.FormID;
import top.mcpbs.games.Name.NameTool;
import top.mcpbs.games.util.SElementButton;

import java.util.Map;

public class LR implements Listener {
    @EventHandler
    public void onFormResponse(PlayerFormRespondedEvent event){
        if (event.getFormID() == FormID.CH_USE_FORM){
            FormResponseSimple response = (FormResponseSimple) event.getResponse();
            if (response == null){
                return;
            }
            SElementButton button = (SElementButton) response.getClickedButton();
            Map.Entry<String, Boolean> e = (Map.Entry<String, Boolean>)button.s;
            if (e.getValue() == true){
                event.getPlayer().sendMessage("§c>>你正在使用这个称号!");
                return;
            }
            NameTool.setPlayerUseDesignation(event.getPlayer(),e.getKey());
            event.getPlayer().sendMessage("§a>>你的称号已变更为 " + e.getKey() + "!");
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        NameTool.returnToDefaultName(event.getPlayer());
    }
}
