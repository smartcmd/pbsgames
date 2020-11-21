package top.mcpbs.games.lottery;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.Config;
import top.mcpbs.games.FormID;
import top.mcpbs.games.util.SElementButton;

import java.util.HashMap;

public class LotteryCmd extends Command {

    public LotteryCmd(String name, String description) {
        super(name, description);
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        if (sender instanceof ConsoleCommandSender){
            sender.sendMessage("控制台不能使用此指令!");
            return true;
        }else{
            Player player = (Player)sender;
            Forms.showLotteryMainForm(player);
        }
        return true;
    }
}
