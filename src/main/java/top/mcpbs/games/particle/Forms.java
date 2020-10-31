package top.mcpbs.games.particle;

import cn.nukkit.Player;
import cn.nukkit.form.element.*;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowSimple;
import top.mcpbs.games.FormID;
import top.mcpbs.games.playerinfo.PlayerInfoTool;
import top.mcpbs.games.util.SElementButton;

import java.util.HashMap;

public class Forms {
    public static void showParticleSelectForm(Player player){
        FormWindowSimple form = new FormWindowSimple("粒子特效","awa~");
        form.addButton(new ElementButton("§b查看你拥有的粒子&设置使用粒子",new ElementButtonImageData("path","textures/items/book_enchanted")));
        form.addButton(new ElementButton("§e关闭粒子",new ElementButtonImageData("path","textures/blocks/barrier")));
        form.addButton(new ElementButton("§d调整粒子发射速度",new ElementButtonImageData("path","textures/items/book_writable")));
        player.showFormWindow(form, FormID.PARTICLE_FORM);
    }

    public static void showParticleUseForm(Player player){
        FormWindowSimple form = new FormWindowSimple("§e你的粒子特效","§b以下是你拥有的粒子");
        HashMap<String,Boolean> m = PlayerInfoTool.getInfo(player,"particle.list",new HashMap<>());
        for (String s : m.keySet()){
            if (m.get(s) == true){
                form.addButton(new SElementButton(s + " §a[正在使用]",new ElementButtonImageData("path","textures/items/book_portfolio"),s));
            }
            if (m.get(s) == false){
                form.addButton(new SElementButton(s + " §e[点击使用]",new ElementButtonImageData("path","textures/items/book_portfolio"),s));
            }
        }
        player.showFormWindow(form, FormID.PARTICLE_USE_FORM);
    }

    public static void showParticleSpeedForm(Player player){
        FormWindowCustom form = new FormWindowCustom("§e更改粒子发射时间间隔");
        form.addElement(new ElementLabel("§b数值范围在1-20之间"));
        form.addElement(new ElementSlider("§6选择数值",1,20,1));
        player.showFormWindow(form, FormID.PARTICLE_SPEED_FORM);
    }
}
