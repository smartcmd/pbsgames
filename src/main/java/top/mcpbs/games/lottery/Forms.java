package top.mcpbs.games.lottery;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindowSimple;
import top.mcpbs.games.FormID;
import top.mcpbs.games.util.SElementButton;

import java.util.HashMap;
import java.util.Map;

public class Forms {
    public static void showLotteryBuyForm(String lname, Player player){
        String s = "§b此抽奖箱包含的奖品:";
        for(Map.Entry<String,String> e : LotteryTool.getLotteryAllPrize(lname).entrySet()){
            if (e.getValue().equals("ch")){
                s += "\n§a>>称号 - §f[" + e.getKey() + "§f]";
                continue;
            }
            if (e.getValue().equals("p")){
                s += "\n§a>>粒子特效 - §f" + e.getKey();
                continue;
            }
            if (e.getValue().equals("diamond")){
                s += "\n§a>>钻石 - §f" + e.getKey();
                continue;
            }
            if (e.getValue().equals("coin")){
                s += "\n§a>>硬币 - §f" + e.getKey();
                continue;
            }
        }
        HashMap<String, Object> priceinfo = LotteryTool.getLotteryPriceInfo(lname);
        s += "\n§6>>需要花费: §f" + priceinfo.get("price") + " " + priceinfo.get("type");
        s += "\n§e>>抽奖箱介绍: §f" + LotteryTool.getLotteryDescription(lname);
        FormWindowSimple form = new FormWindowSimple(lname,s);
        form.addButton(new SElementButton("§a开启此抽奖箱",new ElementButtonImageData("path","textures/ui/gift_square"), lname));
        form.addButton(new ElementButton("§c取消",new ElementButtonImageData("path","textures/ui/cancel")));
        player.showFormWindow(form, FormID.LOTTERY_BUY_FORM);
    }
}
