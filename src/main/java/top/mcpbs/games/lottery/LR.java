package top.mcpbs.games.lottery;

import cn.nukkit.Player;
import cn.nukkit.entity.weather.EntityLightning;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.level.Position;
import cn.nukkit.utils.Config;
import top.mcpbs.games.Main;
import top.mcpbs.games.MenuID;
import top.mcpbs.games.playerinfo.diamond.Diamond;
import top.mcpbs.games.util.SElementButton;

import java.util.HashMap;
import java.util.Random;

public class LR implements Listener {

    @EventHandler
    public void onPlayerFormRespondedEvent(PlayerFormRespondedEvent event){
        if (event.getFormID() == MenuID.LOTTERY_LIST_FORM){
            FormResponseSimple response = (FormResponseSimple) event.getResponse();
            if (response == null){
                return;
            }
            String s = "§b此抽奖箱包含的奖品:";
            Config c = new Config(Main.plugin.getDataFolder() + "/lottery.yml");
            for (Object ss : ((HashMap)((HashMap)c.get((String) ((SElementButton)response.getClickedButton()).s)).get("things")).keySet()){
                if (((HashMap)((HashMap)c.get((String) ((SElementButton)response.getClickedButton()).s)).get("things")).get(ss).equals("ch")){
                    s += "\n§a称号 - §f『" + ss + "§f』";
                }else if (((HashMap)((HashMap)c.get((String) ((SElementButton)response.getClickedButton()).s)).get("things")).get(ss).equals("p")){
                    s += "\n§b粒子特效 - §e" + ss;
                }
            }
            s += "\n§6需要花费: §f" + ((HashMap)c.get((String) ((SElementButton)response.getClickedButton()).s)).get("price") + " §bdiamond";
            s += "\n§e抽奖箱介绍: §f" + ((HashMap)c.get((String) ((SElementButton)response.getClickedButton()).s)).get("description");
            FormWindowSimple form = new FormWindowSimple(response.getClickedButton().getText(),s);
            form.addButton(new SElementButton("§a开启此抽奖箱",new ElementButtonImageData("path","textures/ui/gift_square"), ((SElementButton) response.getClickedButton()).s));
            form.addButton(new ElementButton("§c取消",new ElementButtonImageData("path","textures/ui/cancel")));
            event.getPlayer().showFormWindow(form,MenuID.LOTTERY_BUY_FORM);
        }
        if (event.getFormID() == MenuID.LOTTERY_BUY_FORM){
            FormResponseSimple response = (FormResponseSimple) event.getResponse();
            if (response == null){
                return;
            }
            Config c = new Config(Main.plugin.getDataFolder() + "/lottery.yml");
            if (response.getClickedButtonId() == 0){
                if (Diamond.getDiamondNum(event.getPlayer()) < (int)((HashMap)c.get((String) ((SElementButton)response.getClickedButton()).s)).get("price")){
                    event.getPlayer().sendMessage("§c钻石不足!");
                }else if (Diamond.getDiamondNum(event.getPlayer()) >= (int)((HashMap)c.get((String) ((SElementButton)response.getClickedButton()).s)).get("price")){
                    Diamond.remDiamond(event.getPlayer(),100);
                    event.getPlayer().sendMessage("§a成功支付§f" + ((HashMap)c.get((String) ((SElementButton)response.getClickedButton()).s)).get("price") + "§bdiamond");
                    for (Player p : event.getPlayer().getLevel().getPlayers().values()){
                        p.sendMessage("§e玩家 §b" +event.getPlayer().getName() + " §a正在开启 §f" + ((SElementButton) response.getClickedButton()).s);
                    }
                    Random r = new Random();
                    HashMap m = ((HashMap)((HashMap)c.get((String) ((SElementButton)response.getClickedButton()).s)).get("things"));
                    String s = (String)(m.keySet().toArray())[r.nextInt(m.size())];
                    if (m.get(s).equals("ch")){
                        for (Player p : event.getPlayer().getLevel().getPlayers().values()){
                            p.sendMessage("§e恭喜玩家 §b" +event.getPlayer().getName() + "§a获得了称号 §f" + "『" + s +"』");
                        }
                        event.getPlayer().sendMessage("§a恭喜你获得了称号 §f" + "『" + s +"』,快去个性工坊启用吧!");
                        event.getPlayer().sendTitle("§a恭喜你获得了称号 §f" + "『" + s +"』","§e快去个性工坊启用吧!");
                        Config cc = new Config(Main.plugin.getDataFolder() + "/playerdata/" + event.getPlayer().getName() + ".yml");
                        HashMap mm = (HashMap)cc.get("ch");
                        this.Lightning(event.getPlayer().getPosition());
                        if (mm.containsKey(s)){
                            event.getPlayer().sendMessage("§e你已经拥有此称号了，钻石已退还~");
                            Diamond.addDiamond(event.getPlayer(),(int)((HashMap)c.get((String) ((SElementButton)response.getClickedButton()).s)).get("price"));
                            return;
                        }
                        mm.put(s,false);
                        cc.set("ch",mm);
                        cc.save();
                    }else if (m.get(s).equals("p")){
                        for (Player p : event.getPlayer().getLevel().getPlayers().values()){
                            p.sendMessage("§e恭喜玩家 §b" +event.getPlayer().getName() + "§a获得了粒子效果 §f" + s);
                        }
                        event.getPlayer().sendMessage("§a恭喜你获得了粒子效果 §f" + s + "§e快去个性工坊启用吧!");
                        event.getPlayer().sendTitle("§a恭喜你获得了粒子效果 §f" + s,"§e快去个性工坊启用吧!");
                        Config cc = new Config(Main.plugin.getDataFolder() + "/playerdata/" + event.getPlayer().getName() + ".yml");
                        this.Lightning(event.getPlayer().getPosition());
                        HashMap mm = (HashMap)cc.get("particle");
                        if (mm.containsKey(s)){
                            event.getPlayer().sendMessage("§e你已经拥有此粒子效果了，钻石已退还~");
                            Diamond.addDiamond(event.getPlayer(),(int)((HashMap)c.get((String) ((SElementButton)response.getClickedButton()).s)).get("price"));
                            return;
                        }
                        mm.put(s,false);
                        cc.set("particle",mm);
                        cc.save();
                    }
                }
            }
        }
    }

    public void Lightning(Position position) {
        EntityLightning l = new EntityLightning(position.getChunk(),EntityLightning.getDefaultNBT(position));
        l.setEffect(false);
        l.spawnToAll();
    }
}
