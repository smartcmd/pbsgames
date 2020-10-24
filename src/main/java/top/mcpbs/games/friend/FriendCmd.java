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
            FormWindowSimple form = new FormWindowSimple("§e好友系统","");
            form.addButton(new ElementButton("§b我的好友",new ElementButtonImageData("path","textures/ui/dressing_room_skins")));
            form.addButton(new ElementButton("§a添加好友(也可以在大厅点击玩家添加)",new ElementButtonImageData("path","textures/ui/color_plus")));
            form.addButton(new ElementButton("§e好友申请",new ElementButtonImageData("path","textures/ui/invite_pressed")));
            form.addButton(new ElementButton("§6系统信息",new ElementButtonImageData("path","textures/ui/altOffersIcon")));
            ((Player)sender).showFormWindow(form, FormID.FRIEND_FORM);
            return true;
        }
    }
}
