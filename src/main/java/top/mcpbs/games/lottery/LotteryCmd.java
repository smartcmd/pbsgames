package top.mcpbs.games.lottery;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.Config;
import top.mcpbs.games.MenuID;
import top.mcpbs.games.util.SElementButton;

import java.util.HashMap;

public class LotteryCmd extends Command {

    Plugin plugin;

    public LotteryCmd(String name, String description,Plugin plugin) {
        super(name, description);
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        if (sender instanceof ConsoleCommandSender){
            sender.sendMessage("控制台不能使用此指令!");
            return true;
        }else{
            Player player = (Player)sender;
            FormWindowSimple form = new FormWindowSimple("§b抽奖箱","快来开启一个抽奖箱吧！");
            Config c = new Config(this.plugin.getDataFolder() + "/lottery.yml");
            for (String ss : c.getAll().keySet()){
                form.addButton(new SElementButton((String)((HashMap)c.get(ss)).get("name"),new ElementButtonImageData("path","textures/ui/gift_square"),ss));
            }
            player.showFormWindow(form, MenuID.LOTTERY_LIST_FORM);
        }
        return true;
    }
}
