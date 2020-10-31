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

                        DrawPrizeModel prize = LotteryTool.drawPrize(lname);
                        for (Player p : event.getPlayer().getLevel().getPlayers().values()) {
                            if (prize.type.equals("ch")) {
                                p.sendMessage("§e>>恭喜玩家 §b" + event.getPlayer().getName() + "§a获得了称号 §f" + "[" + prize.prize + "]");
                            }
                            if (prize.type.equals("p")){
                                p.sendMessage("§e>>恭喜玩家 §b" +event.getPlayer().getName() + "§a获得了粒子效果 §f" + prize.prize);
                            }
                        }
                        event.getPlayer().sendMessage("§a>>快去个人中心启用吧!");
                }
//                if (Diamond.getDiamondNum(event.getPlayer()) < ){
//                    event.getPlayer().sendMessage("§c钻石不足!");
//                }else if (Diamond.getDiamondNum(event.getPlayer()) >= (int)((HashMap)c.get((String) ((SElementButton)response.getClickedButton()).s)).get("price")){
//                    Diamond.remDiamond(event.getPlayer(),100);
//                    event.getPlayer().sendMessage("§a成功支付§f" + ((HashMap)c.get((String) ((SElementButton)response.getClickedButton()).s)).get("price") + "§bdiamond");
//                    for (Player p : event.getPlayer().getLevel().getPlayers().values()){
//                        p.sendMessage("§e玩家 §b" +event.getPlayer().getName() + " §a正在开启 §f" + ((SElementButton) response.getClickedButton()).s);
//                    }
//                    Random r = new Random();
//                    HashMap m = ((HashMap)((HashMap)c.get((String) ((SElementButton)response.getClickedButton()).s)).get("things"));
//                    String s = (String)(m.keySet().toArray())[r.nextInt(m.size())];
//                    if (m.get(s).equals("ch")){
//                        for (Player p : event.getPlayer().getLevel().getPlayers().values()){
//                            p.sendMessage("§e恭喜玩家 §b" +event.getPlayer().getName() + "§a获得了称号 §f" + "『" + s +"』");
//                        }
//                        event.getPlayer().sendMessage("§a恭喜你获得了称号 §f" + "『" + s +"』,快去个性工坊启用吧!");
//                        event.getPlayer().sendTitle("§a恭喜你获得了称号 §f" + "『" + s +"』","§e快去个性工坊启用吧!");
//                        Config cc = new Config(Main.plugin.getDataFolder() + "/playerdata/" + event.getPlayer().getName() + ".yml");
//                        HashMap mm = (HashMap)cc.get("ch");
//                        this.Lightning(event.getPlayer().getPosition());
//                        if (mm.containsKey(s)){
//                            event.getPlayer().sendMessage("§e你已经拥有此称号了，钻石已退还~");
//                            Diamond.addDiamond(event.getPlayer(),(int)((HashMap)c.get((String) ((SElementButton)response.getClickedButton()).s)).get("price"));
//                            return;
//                        }
//                        mm.put(s,false);
//                        cc.set("ch",mm);
//                        cc.save();
//                    }else if (m.get(s).equals("p")){
//                        for (Player p : event.getPlayer().getLevel().getPlayers().values()){
//                            p.sendMessage("§e恭喜玩家 §b" +event.getPlayer().getName() + "§a获得了粒子效果 §f" + s);
//                        }
//                        event.getPlayer().sendMessage("§a恭喜你获得了粒子效果 §f" + s + "§e快去个性工坊启用吧!");
//                        event.getPlayer().sendTitle("§a恭喜你获得了粒子效果 §f" + s,"§e快去个性工坊启用吧!");
//                        Config cc = new Config(Main.plugin.getDataFolder() + "/playerdata/" + event.getPlayer().getName() + ".yml");
//                        this.Lightning(event.getPlayer().getPosition());
//                        HashMap mm = (HashMap)cc.get("particle");
//                        if (mm.containsKey(s)){
//                            event.getPlayer().sendMessage("§e你已经拥有此粒子效果了，钻石已退还~");
//                            Diamond.addDiamond(event.getPlayer(),(int)((HashMap)c.get((String) ((SElementButton)response.getClickedButton()).s)).get("price"));
//                            return;
//                        }
//                        mm.put(s,false);
//                        cc.set("particle",mm);
//                        cc.save();
//                    }
//                }
            }
        }
    }

    public void Lightning(Position position) {
        EntityLightning l = new EntityLightning(position.getChunk(),EntityLightning.getDefaultNBT(position));
        l.setEffect(false);
        l.spawnToAll();
    }
}
