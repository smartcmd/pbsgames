package top.mcpbs.games.name;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.PluginTask;
import top.mcpbs.games.playerinfo.coin.Coin;
import top.mcpbs.games.playerinfo.diamond.Diamond;
import top.mcpbs.games.playerinfo.score.Score;

public class NameRefreshTask extends PluginTask {
    public NameRefreshTask(Plugin owner) {
        super(owner);
    }

    @Override
    public void onRun(int i) {
        for (Player player : Server.getInstance().getOnlinePlayers().values()){
            if (!NameTool.playernametag.containsKey(player)){
                NameTool.playernametag.put(player,player.getName());
            }
            String sourcenametag = NameTool.playernametag.get(player);
            sourcenametag = sourcenametag.replaceAll("(score)", String.valueOf(Score.getScore(player)));
            sourcenametag = sourcenametag.replaceAll("(coin)", String.valueOf(Coin.getCoinNum(player)));
            sourcenametag = sourcenametag.replaceAll("(diamond)", String.valueOf(Diamond.getDiamondNum(player)));
            sourcenametag = sourcenametag.replaceAll("(health)", String.valueOf((int)player.getHealth()));
            sourcenametag = sourcenametag.replaceAll("(maxhealth)", String.valueOf((int)player.getMaxHealth()));
            sourcenametag = sourcenametag.replaceAll("(ch)", NameTool.getPlayerUseDesignation(player));
            sourcenametag = sourcenametag.replaceAll("(name)", player.getName());
            player.setNameTag(sourcenametag);

            if (!NameTool.playerdisplayname.containsKey(player)){
                NameTool.playerdisplayname.put(player,player.getName());
            }
            String sourcedisplayname = NameTool.playerdisplayname.get(player);
            sourcedisplayname = sourcedisplayname.replaceAll("(score)", String.valueOf(Score.getScore(player)));
            sourcedisplayname = sourcedisplayname.replaceAll("(coin)", String.valueOf(Coin.getCoinNum(player)));
            sourcedisplayname = sourcedisplayname.replaceAll("(diamond)", String.valueOf(Diamond.getDiamondNum(player)));
            sourcedisplayname = sourcedisplayname.replaceAll("(health)", String.valueOf((int)player.getHealth()));
            sourcedisplayname = sourcedisplayname.replaceAll("(maxhealth)", String.valueOf(player.getMaxHealth()));
            sourcedisplayname = sourcedisplayname.replaceAll("(ch)", NameTool.getPlayerUseDesignation(player));
            sourcedisplayname = sourcedisplayname.replaceAll("(name)", player.getName());
            player.setDisplayName(sourcedisplayname);
        }
    }
}
