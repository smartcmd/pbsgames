package top.mcpbs.games.designation;

import cn.nukkit.Player;
import cn.nukkit.form.window.FormWindowSimple;
import top.mcpbs.games.FormID;
import top.mcpbs.games.Name.NameTool;
import top.mcpbs.games.util.SElementButton;

import java.util.HashMap;
import java.util.Map;

public class Forms {
    public static void showDesignationUseForm(Player player){
        FormWindowSimple form = new FormWindowSimple("称号列表","点击启用");
        HashMap<String, Boolean> ds = NameTool.getPlayerAllDesignation(player);
        for (Map.Entry<String, Boolean> e : ds.entrySet()){
            if (e.getValue() == false){
                form.addButton(new SElementButton("§6[点击使用]§f" + e.getKey(),e));
            }
            if (e.getValue() == true){
                form.addButton(new SElementButton("§a[正在使用]§f" + e.getKey(),e));
            }
        }
        player.showFormWindow(form, FormID.CH_USE_FORM);
    }
}
