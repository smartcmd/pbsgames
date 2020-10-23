package top.mcpbs.games.sb;

import cn.nukkit.Player;
import de.theamychan.scoreboard.api.ScoreboardAPI;
import de.theamychan.scoreboard.network.DisplaySlot;
import de.theamychan.scoreboard.network.Scoreboard;
import de.theamychan.scoreboard.network.ScoreboardDisplay;

import java.util.HashMap;
import java.util.List;

/**
 * @author lt_name
 */
public class ScoreboardDe implements IScoreboard {

    private final HashMap<Player, Scoreboard> scoreboards = new HashMap<>();

    @Override
    public void showScoreboard(Player player, String title, List<String> message) {
        Scoreboard scoreboard = ScoreboardAPI.createScoreboard();
        ScoreboardDisplay scoreboardDisplay = scoreboard.addDisplay(DisplaySlot.SIDEBAR, title, title);
        if (this.scoreboards.containsKey(player)) {
            this.scoreboards.get(player).hideFor(player);
        }
        for (int line = 0; line < message.size(); line++) {
            scoreboardDisplay.addLine(message.get(line), line);
        }
        scoreboard.showFor(player);
        this.scoreboards.put(player, scoreboard);
    }

    @Override
    public void closeScoreboard(Player player) {
        if (this.scoreboards.containsKey(player)) {
            Scoreboard scoreboard = this.scoreboards.get(player);
            scoreboard.hideFor(player);//对玩家隐藏
        }
    }

    @Override
    public void delCache(Player player) {
        this.closeScoreboard(player);//先隐藏
        this.scoreboards.remove(player);//清除计分板缓存(似乎缓存是存在Scoreboard里面的，不是Player)
    }

}
