package top.mcpbs.games.uhc;

import cn.nukkit.Player;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.PluginTask;

public class SettlementFormTask extends PluginTask {

    boolean iswinner;
    Player player;

    public SettlementFormTask(Plugin owner, Player player, boolean iswinner) {
        super(owner);
        this.iswinner = iswinner;
        this.player = player;
    }

    @Override
    public void onRun(int i) {
        Form.IncomeSettlementForm(player,iswinner);
    }
}
