package top.mcpbs.games.chao;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.plugin.Plugin;
import top.mcpbs.games.FormID;

public class ChaoStore extends Command {

    public ChaoStore(String name, String description) {
        super(name, description);
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        if (sender instanceof ConsoleCommandSender){
            sender.sendMessage("控制台不能使用此指令哦！");
        }else{
            Player player = (Player)sender;
            FormWindowSimple form = new FormWindowSimple("§e大乱斗商店","§6你想做什么？");
            form.addButton(new ElementButton("§a购买装备",new ElementButtonImageData("path","textures/items/iron_sword")));
            form.addButton(new ElementButton("§b天赋强化",new ElementButtonImageData("path","textures/ui/anvil_icon")));
            player.showFormWindow(form, FormID.CHAO_STORE_LIST);
        }
        return true;
    }
}
