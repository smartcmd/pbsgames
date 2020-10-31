package top.mcpbs.games.lottery;

import cn.nukkit.Player;
import cn.nukkit.entity.weather.EntityLightning;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.level.Position;
import top.mcpbs.games.FormID;
import top.mcpbs.games.playerinfo.coin.Coin;
import top.mcpbs.games.util.SElementButton;

public class LR implements Listener {

    @EventHandler
    public void onPlayerFormRespondedEvent(PlayerFormRespondedEvent event){
        if (event.getFormID() == FormID.LOTTERY_LIST_FORM){
            FormResponseSimple response = (FormResponseSimple) event.getResponse();
            if (response == null){
                return;
            }
            Forms.showLotteryBuyForm((String)((SElementButton)response.getClickedButton()).s,event.getPlayer());
            return;
        }
        if (event.getFormID() == FormID.LOTTERY_BUY_FORM){
            FormResponseSimple response = (FormResponseSimple) event.getResponse();
            if (response == null){
                return;
            }
            String lname = (String) ((SElementButton)response.getClickedButton()).s;
            String pricetype = (String) LotteryTool.getLotteryPriceInfo(lname).get("type");
            int price = (int) LotteryTool.getLotteryPriceInfo(lname).get("price");
            if (response.getClickedButtonId() == 0){
                switch (pricetype){
                    case "coin":
                        if (Coin.getCoinNum(event.getPlayer()) < price){
                            event.getPlayer().sendMessage("§c>>硬币不足!");
                            break;
                        }
                        Coin.remCoin(event.getPlayer(),price);
                        for (Player p : event.getPlayer().getLevel().getPlayers().values()){
                            p.sendMessage("§e>>玩家 §b" +event.getPlayer().getName() + " §a正在开启 §f" + lname);
                        }
                        LotteryTool.givePrizes(event.getPlayer(),LotteryTool.drawPrize(lname));
                        event.getPlayer().sendMessage("§a>>快去个人中心启用吧!");
                }
            }
        }
    }
}
