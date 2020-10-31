package top.mcpbs.games.friend;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.Config;
import top.mcpbs.games.FormID;
import top.mcpbs.games.Main;
import top.mcpbs.games.util.DateUtil;
import top.mcpbs.games.util.SElementButton;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class LR implements Listener {

    @EventHandler
    public void onPlayerFormRespondedEvent(PlayerFormRespondedEvent event){
        if (event.getFormID() == FormID.FRIEND_FORM){
            FormResponseSimple response = (FormResponseSimple) event.getResponse();
            if (response == null){
                return;
            }
            switch(response.getClickedButtonId()){
                case 0:
                    FormWindowSimple form0 = new FormWindowSimple("§a你的好友","§e以下是你的好友");
                    Config c = new Config(Main.plugin.getDataFolder() + "/playerdata/" + event.getPlayer().getName() + ".yml");
                    ArrayList<String> l = (ArrayList)c.get("friend");
                    ArrayList<String> onlineplayername = new ArrayList();
                    for (Player p : Server.getInstance().getOnlinePlayers().values()){
                        onlineplayername.add(p.getName());
                    }
                    for (String s : l){
                        if (onlineplayername.contains(s)){
                            form0.addButton(new ElementButton(s + "§a[在线]",new ElementButtonImageData("path","textures/ui/dressing_room_customization")));
                        }
                        if (!onlineplayername.contains(s)){
                            form0.addButton(new ElementButton("§7" + s + "§c[离线]",new ElementButtonImageData("path","textures/ui/dressing_room_customization")));
                        }
                    }
                    event.getPlayer().showFormWindow(form0, FormID.FRIEND_LIST);
                    break;
                case 1:
                    FormWindowCustom form1 = new FormWindowCustom("§a添加好友");
                    form1.addElement(new ElementInput("§e输入玩家名称(此玩家必须登录过服务器,否则无法添加)"));
                    event.getPlayer().showFormWindow(form1, FormID.FRIEND_ADD);
                    break;
                case 2:
                    FormWindowSimple form2 = new FormWindowSimple("§a好友申请","§e以下是玩家发送给你的好友申请");
                    Config cc = new Config(Main.plugin.getDataFolder() + "/playerdata/" + event.getPlayer().getName() + ".yml");
                    HashMap<String,String> m = ((HashMap)cc.get("friend application"));
                    for (String s : m.keySet()){
                        form2.addButton(new ElementButton(s + " §aTime: §e" + m.get(s),new ElementButtonImageData("path","textures/ui/invite_pressed")));
                    }
                    event.getPlayer().showFormWindow(form2, FormID.FRIEND_APPLICATION);
                    break;
                case 3:
                    FormWindowSimple form3 = new FormWindowSimple("§a系统信息","§e以下为系统信息");
                    Config ccc = new Config(Main.plugin.getDataFolder() + "/playerdata/" + event.getPlayer().getName() + ".yml");
                    ArrayList<String> ll = (ArrayList)ccc.get("friend system info");
                    form3.addButton(new ElementButton("§a全部已读",new ElementButtonImageData("path","textures/ui/check")));
                    for (String s : ll){
                        form3.addButton(new ElementButton(s,new ElementButtonImageData("path","textures/ui/altOffersIcon")));
                    }
                    event.getPlayer().showFormWindow(form3, FormID.FRIEND_SYSTEM_INFO);
                    break;
            }
        }
        if (event.getFormID() == FormID.FRIEND_LIST){
            FormResponseSimple response = (FormResponseSimple) event.getResponse();
            if (response == null){
                return;
            }
            Config c = new Config(Main.plugin.getDataFolder() + "/playerdata/" + event.getPlayer().getName() + ".yml");
            ArrayList<String> l = (ArrayList)c.get("friend");
            String f = l.get(response.getClickedButtonId());
            FormWindowSimple form = new FormWindowSimple(f,"§e你想对此好友执行什么操作?");
            form.addButton(new SElementButton("§c删除此好友",new ElementButtonImageData("path","textures/ui/cancel"),f));
            event.getPlayer().showFormWindow(form, FormID.FRIEND_REM);
        }
        if (event.getFormID() == FormID.FRIEND_REM){
            FormResponseSimple response = (FormResponseSimple) event.getResponse();
            if (response == null){
                return;
            }
            Config c = new Config(Main.plugin.getDataFolder() + "/playerdata/" + event.getPlayer().getName() + ".yml");
            Config fc = new Config(Main.plugin.getDataFolder() + "/playerdata/" + ((SElementButton)response.getClickedButton()).s + ".yml");
            ArrayList fl = (ArrayList) fc.get("friend");
            ArrayList fll = (ArrayList) fc.get("friend system info");
            ArrayList cl = (ArrayList) c.get("friend");
            fl.remove(event.getPlayer().getName());
            cl.remove(((SElementButton)response.getClickedButton()).s);
            fll.add("§e玩家 §b" + event.getPlayer().getName() + " §e解除了与你的好友关系~");
            c.set("friend",cl);
            fc.set("friend",fl);
            fc.set("friend system info",fll);
            c.save();
            fc.save();
            event.getPlayer().sendMessage("§e删除好友成功!");
        }
        if (event.getFormID() == FormID.FRIEND_ADD){
            FormResponseCustom response = (FormResponseCustom) event.getResponse();
            if (response == null){
                return;
            }
            String input = response.getInputResponse(0);
            if (!new File(Main.plugin.getDataFolder() + "/playerdata/" + input + ".yml").exists()){
                event.getPlayer().sendMessage("§c未找到玩家，好友申请失败!");
            }else if (input.equals(event.getPlayer().getName())){
                event.getPlayer().sendMessage("§c玩家名不能是你自己的名字...");
            }else if (((ArrayList)new Config(Main.plugin.getDataFolder() + "/playerdata/" + event.getPlayer().getName() + ".yml").get("friend")).contains(input)){
                event.getPlayer().sendMessage("§c你已经有此玩家的好友了...");
            }else if (((HashMap)new Config(Main.plugin.getDataFolder() + "/playerdata/" + input + ".yml").get("friend application")).containsKey(input)){
                event.getPlayer().sendMessage("§c你已经申请过此玩家了...");
            }else{
                Config c = new Config(Main.plugin.getDataFolder() + "/playerdata/" + input + ".yml");
                HashMap m = (HashMap) c.get("friend application");
                m.put(event.getPlayer().getName(), DateUtil.getDate("yyyy/MM/dd"));
                c.set("friend application",m);
                c.save();
                event.getPlayer().sendMessage("§a好友申请成功!等待对方同意!");
                if (Server.getInstance().getPlayer(input).isOnline()){
                    Server.getInstance().getPlayer(input).sendMessage("§e你收到了一条好友申请，请前往好友系统查看!");
                }
            }
        }
        if (event.getFormID() == FormID.FRIEND_APPLICATION){
            FormResponseSimple response = (FormResponseSimple) event.getResponse();
            if (response == null){
                return;
            }
            FormWindowSimple form = new FormWindowSimple("§a操作","§e你想拒绝还是同意？");
            Config c = new Config(Main.plugin.getDataFolder() + "/playerdata/" + event.getPlayer().getName() + ".yml");
            String aname = (String) ((((HashMap)c.get("friend application")).keySet()).toArray())[response.getClickedButtonId()];
            form.addButton(new SElementButton("§a同意",new ElementButtonImageData("path","textures/ui/check"),aname));
            form.addButton(new SElementButton("§c拒绝",new ElementButtonImageData("path","textures/ui/cancel"),aname));
            event.getPlayer().showFormWindow(form, FormID.FRIEND_APPLICATION_2);
        }
        if (event.getFormID() == FormID.FRIEND_APPLICATION_2){
            FormResponseSimple response = (FormResponseSimple) event.getResponse();
            if (response == null){
                return;
            }
            Config c = new Config(Main.plugin.getDataFolder() + "/playerdata/" + event.getPlayer().getName() + ".yml");
            HashMap m = (HashMap) c.get("friend application");
            Config cc = new Config(Main.plugin.getDataFolder() + "/playerdata/" + ((SElementButton)response.getClickedButton()).s + ".yml");
            ArrayList ll = (ArrayList) cc.get("friend system info");
            ArrayList lll = (ArrayList) cc.get("friend");
            ArrayList l = (ArrayList)c.get("friend");
            switch (response.getClickedButtonId()){
                case 0:
                    m.remove(((SElementButton)response.getClickedButton()).s);
                    c.set("friend application",m);
                    l.add(((SElementButton) response.getClickedButton()).s);
                    c.set("friend",l);
                    c.save();
                    ll.add("§a你向玩家 §f" + event.getPlayer().getName() + " §a发送的好友申请通过了！");
                    lll.add(event.getPlayer().getName());
                    cc.set("friend system info",ll);
                    cc.set("friend",lll);
                    cc.save();
                    event.getPlayer().sendMessage("§a添加成功！");
                    break;
                case 1:
                    m.remove(((SElementButton)response.getClickedButton()).s);
                    c.set("friend application",m);
                    c.save();
                    ll.add("§e你向玩家 §f" + event.getPlayer().getName() + " §e发送的好友申请被拒绝了！");
                    cc.set("friend system info",ll);
                    cc.save();
                    event.getPlayer().sendMessage("§e拒绝成功！");
                    break;
            }
        }
        if (event.getFormID() == FormID.FRIEND_SYSTEM_INFO){
            FormResponseSimple response = (FormResponseSimple) event.getResponse();
            if (response == null){
                return;
            }
            Config c = new Config(Main.plugin.getDataFolder() + "/playerdata/" + event.getPlayer().getName() + ".yml");
            if (response.getClickedButtonId() == 0){
                c.set("friend system info",new ArrayList());
                c.save();
                event.getPlayer().sendMessage("§a已标记全部为已读!");
            }else{
                event.getPlayer().sendMessage("§e什么也没发生...");
            }
        }
        if (event.getFormID() == FormID.FRIEND_APPLICATION_TOUCHING){
            FormResponseSimple response = (FormResponseSimple) event.getResponse();
            if (response == null){
                return;
            }
            Config c = new Config(Main.plugin.getDataFolder() + "/playerdata/" + ((SElementButton)response.getClickedButton()).s + ".yml");
            if (((ArrayList)new Config(Main.plugin.getDataFolder() + "/playerdata/" + event.getPlayer().getName() + ".yml").get("friend")).contains(((SElementButton)response.getClickedButton()).s)){
                event.getPlayer().sendMessage("§c你已经有此玩家的好友了...");
            }else if (((HashMap)c.get("friend application")).containsKey(event.getPlayer().getName())){
                event.getPlayer().sendMessage("§c你已经申请过此玩家了...");
            }else if (response.getClickedButtonId() == 0){
                event.getPlayer().sendMessage("§a申请成功!");
                HashMap m = (HashMap) c.get("friend application");
                m.put(event.getPlayer().getName(),DateUtil.getDate("yyyy/MM/dd"));
                c.set("friend application",m);
                c.save();
                Server.getInstance().getPlayer((String) ((SElementButton)response.getClickedButton()).s).sendMessage("§e你收到了一条好友申请，请前往好友系统查看!");
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        for(Player p : Server.getInstance().getOnlinePlayers().values()){
            Config c = new Config(Main.plugin.getDataFolder() + "/playerdata/" + p.getName() + ".yml");
            ArrayList l = (ArrayList) c.get("friend");
            if(l != null && l.contains(event.getPlayer().getName())){
                p.sendMessage("§a你的好友 §f" + event.getPlayer().getName() + " §a上线了!");
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event){
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player){
            Player player = (Player)event.getEntity();
            Player player1 = (Player)event.getDamager();
            if (player.getLevel().getName().equals("world")){
                FormWindowSimple form = new FormWindowSimple(player.getName(),"加好友?");
                form.addButton(new SElementButton("§a发出好友申请",new ElementButtonImageData("path","textures/ui/invite_pressed"),player.getName()));
                player1.showFormWindow(form, FormID.FRIEND_APPLICATION_TOUCHING);
            }
        }
    }
}
