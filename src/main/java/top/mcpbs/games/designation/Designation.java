package top.mcpbs.games.designation;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;

public class Designation extends Command {

    public Designation(String name, String description) {
        super(name, description);
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        if (sender instanceof ConsoleCommandSender){
            sender.sendMessage("控制台不能使用。。。");
        }else{
            Forms.showDesignationUseForm((Player)sender);
        }
        return true;
    }
}
