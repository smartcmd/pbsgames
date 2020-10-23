package top.mcpbs.games.uhc;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.item.Item;
import top.mcpbs.games.MenuID;

public class Form {
    public static void potStore(Player player){
        int ynum = 0;
        if (!player.getInventory().getContents().isEmpty()) {
            for (Item i : player.getInventory().getContents().values()) {
                if (i.getId() == 372) {
                    ynum += 1;
                }
            }
        }
        FormWindowSimple form = new FormWindowSimple("§a药水商店","§a你目前拥有 " + ynum + " 个地狱疣");
        form.addButton(new ElementButton("§e速度药水" + "\n需要 10 地狱疣",new ElementButtonImageData("path","textures/items/potion_bottle_moveSpeed")));
        form.addButton(new ElementButton("§e力量药水" + "\n需要 10 地狱疣",new ElementButtonImageData("path","textures/items/potion_bottle_saturation")));
        form.addButton(new ElementButton("§e抗火药水" + "\n需要 10 地狱疣",new ElementButtonImageData("path","textures/items/potion_bottle_fireResistance")));
        player.showFormWindow(form, MenuID.UHC_POTSTORE);
    }
}
