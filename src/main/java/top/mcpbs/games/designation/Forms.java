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
                HashMap tmp = new HashMap();
                tmp.put("used",false);
                tmp.put("d",e.getKey());
                form.addButton(new SElementButton("§6[点击使用]§f" + e.getKey(),tmp));
            }
            if (e.getValue() == true){
                HashMap tmp = new HashMap();
                tmp.put("used",true);
                tmp.put("d",e.getKey());
                form.addButton(new SElementButton("§a[正在使用]§f" + e.getKey(),tmp));
            }
        }
        player.showFormWindow(form, FormID.CH_USE_FORM);
    }
}
