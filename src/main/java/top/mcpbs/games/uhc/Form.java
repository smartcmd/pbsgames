package top.mcpbs.games.uhc;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.item.Item;
import top.mcpbs.games.FormID;
import top.mcpbs.games.room.Room;

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
        player.showFormWindow(form, FormID.UHC_POTSTORE);
    }

    public static void IncomeSettlementForm(Player player,boolean iswinner){
        UHCRoom room = (UHCRoom) Room.aplaying.get(player);
        FormWindowCustom form = new FormWindowCustom("游戏结束!");
        int killnum = room.killnum.get(player);
        int result = 0;
        if (iswinner == true) {
            result = 5 + 2 * killnum;
            form.addElement(new ElementLabel("§7§l>>你在本次游戏中获得了 §e5 + " + killnum + " * 2 = " + result + " 的硬币和分数!恭喜胜利!"));
            form.addElement(new ElementLabel(" §c你的击杀数: §6" + killnum));
        }else if (iswinner == false){
            result = 2 * killnum;
            form.addElement(new ElementLabel("§7§l>>你在本次游戏中获得了 §e" + killnum + " * 2 = " + result + " 的硬币和分数!再接再厉!"));
            form.addElement(new ElementLabel(" §c你的击杀数: §6" + killnum));
        }
        player.showFormWindow(form, FormID.UHC_SETTLEMENT_FORM);
    }
}
