package top.mcpbs.games.uhc;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.PluginTask;
import top.mcpbs.games.MenuID;
import top.mcpbs.games.playerinfo.coin.Coin;
import top.mcpbs.games.playerinfo.score.Score;

import java.util.ArrayList;

public class TestWinTask extends PluginTask {
    public TestWinTask(Plugin owner) {
        super(owner);
    }

    @Override
    public void onRun(int i) {
        for (UHCRoom room : UHCRoom.uhcrooms.values()) {
            if (room.isPlaying == true && room.isend == false) {
                ArrayList<Player> aliveplayer = new ArrayList<>();//所有存活玩家
                for (Player player : room.playing) {
                    if (room.isdead.get(player) == false) {
                        aliveplayer.add(player);
                    }
                }
                Team winteam = room.playerteam.get(aliveplayer.get(0));
                if (winteam.player.containsAll(aliveplayer)) {
                    String s = "";
                    for (Player p : winteam.player){
                        if (room.playing.contains(p)) {
                            s += " " + winteam.color + p.getName();
                        }
                    }
                    for (Player player : room.playing) {
                        player.sendTitle("§l§e>> §c游戏结束 §e<<", winteam.teamname + s + " §e是最后的胜利者！");
                        player.sendMessage(winteam.teamname + s + " §e是最后的胜利者！");
                    }
                    for (Player p : winteam.player) {
                        if (room.playing.contains(p)) {
                            String res = "";
                            res += "§a你的击杀数: " + room.killnum.get(p) + "\n";
                            res += "§a你获得了 5 + 2 * " + room.killnum.get(p) + " 的硬币和分数!恭喜";
                            FormWindowSimple form = new FormWindowSimple("§e你的收益", res);
                            form.addButton(new ElementButton("§e我知道了"));
                            p.showFormWindow(form, MenuID.UHC_WIN_FORM);
                            int num = 5 + 2 * room.killnum.get(p);
                            Coin.addCoin(p, num);
                            Score.addScore(p, num);
                        }
                    }
                    room.isend = true;
                    Server.getInstance().getScheduler().scheduleDelayedTask(new GameEndTask(owner, room), 20 * 5);
                }
            }
        }
    }
}
