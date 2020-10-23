package top.mcpbs.games.particle;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindowSimple;
import top.mcpbs.games.MenuID;

public class Particle extends Command {
    public Particle(String name, String description) {
        super(name, description);
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        if (sender instanceof ConsoleCommandSender){
            sender.sendMessage("不能在控制台使用此指令!");
        }else{
            Player player = (Player)sender;
            FormWindowSimple form = new FormWindowSimple("§6粒子效果","");
            form.addButton(new ElementButton("§b查看你拥有的粒子&设置使用粒子",new ElementButtonImageData("path","textures/items/book_enchanted")));
            form.addButton(new ElementButton("§e关闭粒子",new ElementButtonImageData("path","textures/blocks/barrier")));
            form.addButton(new ElementButton("§d调整粒子发射速度",new ElementButtonImageData("path","textures/items/book_writable")));
            player.showFormWindow(form, MenuID.PARTICLE_FORM);
        }
        return true;
    }
}
