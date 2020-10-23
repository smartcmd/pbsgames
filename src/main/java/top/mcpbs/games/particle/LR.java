package top.mcpbs.games.particle;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.Config;
import top.mcpbs.games.Main;
import top.mcpbs.games.MenuID;

import java.util.HashMap;

public class LR implements Listener {

    @EventHandler
    public void onForm(PlayerFormRespondedEvent event){
        if (event.getFormID() == MenuID.PARTICLE_FORM){
            FormResponseSimple response = (FormResponseSimple) event.getResponse();
            if (response == null){
                return;
            }
            switch (response.getClickedButtonId()){
                case 0:
                    FormWindowSimple form1 = new FormWindowSimple("§eYour's §6Particle","§b以下是你拥有的粒子！");
                    Config config = new Config(Main.plugin.getDataFolder() + "/playerdata/" + event.getPlayer().getName() + ".yml");
                    HashMap<String,Boolean> m = (HashMap)config.get("particle");
                    if (m.isEmpty()){
                        event.getPlayer().sendMessage("§c你未拥有任何粒子！");
                        return;
                    }
                    for (String s : m.keySet()){
                        if (m.get(s) == true){
                            form1.addButton(new ElementButton(s + " §a[正在使用]",new ElementButtonImageData("path","textures/items/book_portfolio")));
                        }
                        if (m.get(s) == false){
                            form1.addButton(new ElementButton(s + " §e[点击使用]",new ElementButtonImageData("path","textures/items/book_portfolio")));
                        }
                    }
                    event.getPlayer().showFormWindow(form1,MenuID.PARTICLE_USE_FORM);
                    break;
                case 1:
                    Config config1 = new Config(Main.plugin.getDataFolder() + "/playerdata/" + event.getPlayer().getName() + ".yml");
                    HashMap mm = (HashMap)config1.get("particle");
                    for (Object s : mm.keySet()){
                        mm.put(s,false);
                    }
                    config1.set("particle",mm);
                    config1.save();
                    ParticleTask.use.put(event.getPlayer(),"off");
                    event.getPlayer().sendMessage("§a已关闭粒子效果!");
                    break;
                case 2:
                    FormWindowCustom form2 = new FormWindowCustom("§e更改粒子发射速度");
                    form2.addElement(new ElementLabel("§b数值范围在1-20之间(这里的数值指的是粒子发射间隔，单位1/20秒，所以说1是最快的，20是最慢的)"));
                    form2.addElement(new ElementInput("§6在下面输入数值:"));
                    event.getPlayer().showFormWindow(form2,MenuID.PARTICLE_SPEED_FORM);
            }

        }
        if (event.getFormID() == MenuID.PARTICLE_USE_FORM){
            Config config = new Config(Main.plugin.getDataFolder() + "/playerdata/" + event.getPlayer().getName() + ".yml");
            HashMap m = (HashMap)config.get("particle");
            FormResponseSimple response = (FormResponseSimple) event.getResponse();
            if (response == null){
                return;
            }
            if ((boolean)m.get((m.keySet().toArray())[response.getClickedButtonId()])){
                event.getPlayer().sendMessage("§e你正在使用这个粒子!");
            }else{
                for (Object s : m.keySet()){
                    m.put(s,false);
                }
                m.put((m.keySet().toArray())[response.getClickedButtonId()],true);
                config.set("particle",m);
                config.save();
                ParticleTask.use.put(event.getPlayer(), (String) (m.keySet().toArray())[response.getClickedButtonId()]);
                event.getPlayer().sendMessage("§a启用成功!");
            }
        }
        if (event.getFormID() == MenuID.PARTICLE_SPEED_FORM){
            if (event.getResponse() == null){
                return;
            }
            FormResponseCustom response = (FormResponseCustom) event.getResponse();
            Config config = new Config(Main.plugin.getDataFolder() + "/playerdata/" + event.getPlayer().getName() + ".yml");
            if (Integer.parseInt(response.getInputResponse(1)) >= 1 && Integer.parseInt(response.getInputResponse(1)) <= 20){
                config.set("particle speed",Integer.parseInt(response.getInputResponse(1)));
                config.save();
                ParticleTask.speed.put(event.getPlayer(), Integer.parseInt(response.getInputResponse(1)));
                event.getPlayer().sendMessage("§a更改成功!");
                return;
            }
            event.getPlayer().sendMessage("§c数值超出范围或输入了非法的字符！");
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        ParticleTask.use.remove(event.getPlayer());
        ParticleTask.speed.remove(event.getPlayer());
    }
}
