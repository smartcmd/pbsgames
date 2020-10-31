package top.mcpbs.games.chao;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindowSimple;
import top.mcpbs.games.FormID;

public class Forms {
    public static void showEquipmentStoreForm(Player player){
        FormWindowSimple form0 = new FormWindowSimple("§a装备商店","§e看看有什么需要的吧~");
        player.showFormWindow(form0, FormID.CHAO_STORE_EQUIPMENT);//装备商店还没加商品~
    }

    public static void showGiftShopForm(Player player){
        FormWindowSimple form1 = new FormWindowSimple("§a天赋强化","§e看看有什么想要强化的吧~");
        form1.addButton(new ElementButton("§a增加 §6百分之20 §d乱斗币吸取倍率" + "\n§e需要50乱斗币",new ElementButtonImageData("path","textures/ui/anvil_icon")));
        form1.addButton(new ElementButton("§e增加 §d2 §c血量" + "\n§e需要60乱斗币",new ElementButtonImageData("path","textures/ui/anvil_icon")));
        player.showFormWindow(form1, FormID.CHAO_STORE_TALENT);
    }
}
