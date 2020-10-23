package top.mcpbs.games.designation;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.utils.Config;
import top.mcpbs.games.Main;
import top.mcpbs.games.MenuID;

import java.util.HashMap;

public class LR implements Listener {

    @EventHandler
    public void onPlayerFormRespondedEvent(PlayerFormRespondedEvent event){
        if (event.getFormID() == MenuID.CH_USE_FORM){
            Config config = new Config(Main.plugin.getDataFolder() + "/playerdata/" + event.getPlayer().getName() + ".yml");
            HashMap m = (HashMap)config.get("ch");
            FormResponseSimple response = (FormResponseSimple) event.getResponse();
            if (response == null){
                return;
            }
            if ((boolean)m.get((m.keySet().toArray())[response.getClickedButtonId()])){
                event.getPlayer().sendMessage("§e你正在使用这个称号!");
            }else{
                for (Object s : m.keySet()){
                    m.put(s,false);
                }
                m.put((m.keySet().toArray())[response.getClickedButtonId()],true);
                config.set("ch",m);
                config.save();
                event.getPlayer().sendMessage("§a启用成功!");
                SetName.ReloadToDefaultDesignation(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        SetName.ReloadToDefaultDesignation(event.getPlayer());
    }
}
