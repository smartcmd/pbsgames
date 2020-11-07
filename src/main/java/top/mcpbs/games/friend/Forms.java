package top.mcpbs.games.friend;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowSimple;
import top.mcpbs.games.FormID;
import top.mcpbs.games.playerinfo.PlayerInfoTool;
import top.mcpbs.games.util.SElementButton;

import java.util.ArrayList;
import java.util.Map;

public class Forms {
    public static void showFriendSystemMainForm(Player player){
        FormWindowSimple form = new FormWindowSimple("§e好友系统","");
        form.addButton(new ElementButton("§a我的好友",new ElementButtonImageData("path","textures/ui/dressing_room_skins")));
        form.addButton(new ElementButton("§a添加好友",new ElementButtonImageData("path","textures/ui/color_plus")));
        form.addButton(new ElementButton("§a好友申请列表",new ElementButtonImageData("path","textures/ui/invite_pressed")));
        form.addButton(new ElementButton("§a更改我的个人信息",new ElementButtonImageData("path","textures/items/book_writable")));
        player.showFormWindow(form, FormID.FRIENDMAINSYSTEM_FORM);
    }

    public static void showPlayerHomepage(Player player,String another){
        if (PlayerInfoTool.isHasConfig(another) == false)return;
        FormWindowSimple form = new FormWindowSimple("§e" + another,"个人介绍: " + FriendTool.getPlayerPD(another));
        form.addButton(new SElementButton("§a添加好友",new ElementButtonImageData("path","textures/ui/color_plus"),another));
        player.showFormWindow(form,FormID.PLAYER_PH_FORM);
    }

    public static void showPlayerAllFriend(Player player){
        ArrayList<String> friend = FriendTool.getPlayerAllFriend(player);
        ArrayList<String> onlinefriend = FriendTool.getPlayerAllOnlineFriend(player);
        FormWindowSimple form = new FormWindowSimple("§e好友列表","§a以下是你的好友");
        for (String s : friend){
            if (onlinefriend.contains(s)){
                form.addButton(new SElementButton("§a[在线] " + s,new ElementButtonImageData("path","textures/ui/icon_steve"),s));
            }else{
                form.addButton(new SElementButton("§7[离线] " + s,new ElementButtonImageData("path","textures/ui/icon_steve"),s));
            }
        }
        player.showFormWindow(form,FormID.PLAYERFRIEND_LIST_FORM);
    }

    public static void showPlayerAnotherDetails(Player player,String another){
        FormWindowSimple form = new FormWindowSimple("§a" + another,"");
        form.addButton(new SElementButton("§c删除好友",new ElementButtonImageData("path","textures/ui/cancel"),another));
        player.showFormWindow(form,FormID.PLAYERFRIEND_LIST_NEXT_FORM);
    }

    public static void showAddFriendByNameForm(Player player){
        FormWindowCustom form = new FormWindowCustom("§e通过名字添加好友");
        form.addElement(new ElementInput("§a玩家名称"));
        player.showFormWindow(form,FormID.FRIEND_ADDFRIENDBYNAME_FORM);
    }

    public static void showApplicationList(Player player){
        FormWindowSimple form = new FormWindowSimple("§e好友申请列表","");
        for (Map.Entry e : FriendTool.getPlayerAllApplication(player).entrySet()){
            form.addButton(new SElementButton(e.getKey() + " " + e.getValue(),new ElementButtonImageData("path","textures/ui/color_plus"),e.getKey()));
        }
        player.showFormWindow(form,FormID.FRIEND_APPLICATION_LIST);
    }

    public static void showApplicationIsAcceptForm(Player player,String another){
        FormWindowSimple form = new FormWindowSimple("§a好友申请","");
        form.addButton(new SElementButton("§a通过申请",new ElementButtonImageData("path","textures/ui/color_plus"),another));
        player.showFormWindow(form,FormID.FRIEND_APPLICATION_LIST_NEXT);
    }

    public static void showChangePDForm(Player player){
        FormWindowCustom form = new FormWindowCustom("§e更改个人描述");
        form.addElement(new ElementInput("§a输入新的描述"));
    }
}
