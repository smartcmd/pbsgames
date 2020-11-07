package top.mcpbs.games.friend;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindowSimple;
import top.mcpbs.games.FormID;

public class FriendCmd extends Command {
    public FriendCmd(String name, String description) {
        super(name, description);
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        if(sender instanceof ConsoleCommandSender){
            sender.sendMessage("控制台不能使用此指令");
            return true;
        }else{
            Forms.showFriendSystemMainForm((Player) sender);
            return true;
        }
    }
}
