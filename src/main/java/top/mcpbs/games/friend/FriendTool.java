package top.mcpbs.games.friend;

import cn.nukkit.Player;
import cn.nukkit.Server;
import top.mcpbs.games.playerinfo.PlayerInfoTool;
import top.mcpbs.games.util.DateUtil;

import java.util.ArrayList;
import java.util.HashMap;

public class FriendTool {
    public static ArrayList getPlayerAllFriend(Player player){
        return PlayerInfoTool.getInfo(player,"friend.list",new ArrayList());
    }

    public static ArrayList getPlayerAllOnlineFriend(Player player){
        ArrayList tmp = (ArrayList) PlayerInfoTool.getInfo(player,"friend.list",new ArrayList());
        ArrayList tmp2 = new ArrayList();
        for (Player p : Server.getInstance().getOnlinePlayers().values()){
            if (tmp.contains(p.getName())){
                tmp2.add(p.getName());
            }
        }
        return tmp2;
    }

    public static void playerAddFriend(String player,String name){
        ArrayList tmp = PlayerInfoTool.getOfflinePlayerInfo(player,"friend.list",new ArrayList());
        tmp.add(name);
        PlayerInfoTool.setOfflinePlayerInfo(player,"friend.list",tmp);
    }

    public static boolean playerRemFriend(String player,String name){
        ArrayList tmp = PlayerInfoTool.getOfflinePlayerInfo(player,"friend.list",new ArrayList());
        if (tmp.contains(name)){
            tmp.remove(name);
        }else{
            return false;
        }
        PlayerInfoTool.setOfflinePlayerInfo(player,"friend.list",tmp);
        return true;
    }

    public static boolean addFriendApplication(String respondent,String applicant){
        if (PlayerInfoTool.isHasConfig(respondent) == true){
            HashMap tmp = PlayerInfoTool.getOfflinePlayerInfo(respondent,"friend.application",new HashMap());
            tmp.put(applicant, DateUtil.getDate("yyyy/MM/dd"));
            PlayerInfoTool.setOfflinePlayerInfo(respondent,"friend.application",tmp);
            return true;
        }
        return false;
    }

    public static boolean remFriendApplication(String player,String applicant){
        if (PlayerInfoTool.isHasConfig(player) == true){
            HashMap tmp = PlayerInfoTool.getOfflinePlayerInfo(player,"friend.application",new HashMap());
            tmp.remove(applicant);
            PlayerInfoTool.setOfflinePlayerInfo(player,"friend.application",tmp);
            return true;
        }
        return false;
    }

    public static HashMap<String,String> getPlayerAllApplication(Player player){
        return PlayerInfoTool.getInfo(player,"friend.application",new HashMap());
    }

    public static void changePlayerPD(Player player,String newPD){
        PlayerInfoTool.setInfo(player,"friend.pd",newPD);
    }
    public static String getPlayerPD(String player){
        return PlayerInfoTool.getOfflinePlayerInfo(player,"friend.pd","§e这个人很神秘，什么都没有写...");
    }
}
