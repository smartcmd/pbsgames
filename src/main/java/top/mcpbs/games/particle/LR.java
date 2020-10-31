package top.mcpbs.games.particle;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseSimple;
import top.mcpbs.games.FormID;
import top.mcpbs.games.playerinfo.PlayerInfoTool;
import top.mcpbs.games.util.SElementButton;

import java.util.HashMap;

public class LR implements Listener {

    @EventHandler
    public void onForm(PlayerFormRespondedEvent event){
        if (event.getFormID() == FormID.PARTICLE_FORM){
            FormResponseSimple response = (FormResponseSimple) event.getResponse();
            if (response == null){
                return;
            }
            switch (response.getClickedButtonId()){
                case 0:
                    Forms.showParticleUseForm(event.getPlayer());
                    break;
                case 1:
                    HashMap m = PlayerInfoTool.getInfo(event.getPlayer(),"particle.list",new HashMap());
                    for (Object s : m.keySet()){
                        m.put(s,false);
                    }
                    PlayerInfoTool.setInfo(event.getPlayer(),"particle.list",m);
                    event.getPlayer().sendMessage("§a>>已关闭粒子效果!");
                    break;
                case 2:
                    Forms.showParticleSpeedForm(event.getPlayer());
                    break;
            }

        }
        if (event.getFormID() == FormID.PARTICLE_USE_FORM){
            FormResponseSimple response = (FormResponseSimple) event.getResponse();
            if (response == null) {
                return;
            }
            String use = (String) ((SElementButton)response.getClickedButton()).s;
            HashMap<String,Boolean> p = ParticleTool.getPlayerAllParticle(event.getPlayer());
            if (p.get(use) == true){
                event.getPlayer().sendMessage("§e>>你正在使用这个粒子!");
                return;
            }
            ParticleTool.setPlayerUseParticle(event.getPlayer(), use);
            event.getPlayer().sendMessage("§a>>启用成功!");
        }
        if (event.getFormID() == FormID.PARTICLE_SPEED_FORM){
            FormResponseCustom response = (FormResponseCustom) event.getResponse();
            if (response == null){
                return;
            }
            ParticleTool.setPlayerParticleSpeed(event.getPlayer(), (int) response.getSliderResponse(1));
            event.getPlayer().sendMessage("§a>>设置成功!");
        }
    }
}
