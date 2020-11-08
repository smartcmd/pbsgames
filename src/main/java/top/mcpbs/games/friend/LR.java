package top.mcpbs.games.friend;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseSimple;
import top.mcpbs.games.FormID;
import top.mcpbs.games.helloform.HelloForm;
import top.mcpbs.games.playerinfo.PlayerInfoTool;
import top.mcpbs.games.util.SElementButton;

import java.util.ArrayList;

public class LR implements Listener {
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event){
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player){
            Player player = (Player)event.getEntity();
            Player player1 = (Player)event.getDamager();
            if (player.getLevel().getName().equals("world")){
                event.setCancelled();
                Forms.showPlayerHomepage(player1,player.getName());
            }
        }
    }

    @EventHandler
    public void onFormResponded(PlayerFormRespondedEvent event){
        if (event.getFormID() == FormID.FRIENDMAINSYSTEM_FORM){
            FormResponseSimple response = (FormResponseSimple) event.getResponse();
            if (response == null)return;
            switch(response.getClickedButtonId()){
                case 0:
                    Forms.showPlayerAllFriend(event.getPlayer());
                    break;
                case 1:
                    Forms.showAddFriendByNameForm(event.getPlayer());
                    break;
                case 2:
                    Forms.showApplicationList(event.getPlayer());
                    break;
                case 3:
                    Forms.showChangePDForm(event.getPlayer());
                    break;
            }
        }
        if (event.getFormID() == FormID.PLAYERFRIEND_LIST_FORM){
            FormResponseSimple response = (FormResponseSimple) event.getResponse();
            if (response == null)return;
            Forms.showPlayerAnotherDetails(event.getPlayer(), (String) ((SElementButton)response.getClickedButton()).s);
        }
        if (event.getFormID() == FormID.PLAYER_PH_FORM){
            FormResponseSimple response = (FormResponseSimple) event.getResponse();
            if (response == null){
                return;
            }
            if (response.getClickedButtonId() == 0){
                String another = (String) ((SElementButton)response.getClickedButton()).s;
                if (FriendTool.getPlayerAllFriend(event.getPlayer()).contains(another)){
                    event.getPlayer().sendMessage("§e好友 §7» §c你已经有他/她的好友了");
                    return;
                }
                if (FriendTool.getPlayerAllApplication(event.getPlayer()).containsKey(another)){
                    event.getPlayer().sendMessage("§e好友 §7» §c你已经向他发送过好友申请了");
                    return;
                }
                FriendTool.addFriendApplication(another,event.getPlayer().getName());
                event.getPlayer().sendMessage("§e好友 §7» §a成功发送好友申请,等待对方同意");
            }
        }
        if (event.getFormID() == FormID.PLAYERFRIEND_LIST_NEXT_FORM){
            FormResponseSimple response = (FormResponseSimple) event.getResponse();
            if (response == null)return;
            String another = (String) ((SElementButton)response.getClickedButton()).s;
            if (response.getClickedButtonId() == 0){
                FriendTool.playerRemFriend(another,event.getPlayer().getName());
                FriendTool.playerRemFriend(event.getPlayer().getName(),another);//rem friend
                event.getPlayer().sendMessage("§e好友 §7» §a成功删除好友");
            }
        }
        if (event.getFormID() == FormID.FRIEND_ADDFRIENDBYNAME_FORM){
            FormResponseCustom response = (FormResponseCustom) event.getResponse();
            if (response == null)return;
            String name = response.getInputResponse(0);
            if(PlayerInfoTool.isHasConfig(name) == false){
                event.getPlayer().sendMessage("§e好友 §7» §c未找到玩家信息，请重试!");
                return;
            }
            Forms.showPlayerHomepage(event.getPlayer(),name);
        }
        if (event.getFormID() == FormID.FRIEND_APPLICATION_LIST){
            FormResponseSimple response = (FormResponseSimple) event.getResponse();
            if (response == null)return;
            Forms.showApplicationIsAcceptForm(event.getPlayer(), (String) ((SElementButton)response.getClickedButton()).s);
        }
        if (event.getFormID() == FormID.FRIEND_APPLICATION_LIST_NEXT){
            FormResponseSimple response = (FormResponseSimple) event.getResponse();
            if (response == null)return;
            String another = (String) ((SElementButton)response.getClickedButton()).s;
            FriendTool.remFriendApplication(another,event.getPlayer().getName());
            FriendTool.playerAddFriend(another,event.getPlayer().getName());
            FriendTool.playerAddFriend(event.getPlayer().getName(),another);
            event.getPlayer().sendMessage("§e好友 §7» §a添加好友成功");
        }
        if (event.getFormID() == FormID.FRIEND_CHANGEPD_FORM){
            FormResponseCustom response = (FormResponseCustom) event.getResponse();
            if (response == null)return;
            String pd = response.getInputResponse(0);
            FriendTool.changePlayerPD(event.getPlayer(),pd);
            event.getPlayer().sendMessage("§e好友 §7» §a更改个人描述成功");
        }
    }

    @EventHandler
    public void onPlayerJoinPacket(DataPacketReceiveEvent event){
        if (event.getPacket().pid() == 113){
            ArrayList<String> friend = FriendTool.getPlayerAllFriend(event.getPlayer());
            for (Player player : Server.getInstance().getOnlinePlayers().values()){
                if (friend.contains(player.getName())){
                    player.sendMessage("§e好友 §7» §a你的好友 " + event.getPlayer().getName() + " 上线了!");
                }
            }
        }
    }
}
