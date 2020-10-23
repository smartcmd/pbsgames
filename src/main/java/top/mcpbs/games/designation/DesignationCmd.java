package top.mcpbs.games.designation;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.Config;
import top.mcpbs.games.Main;
import top.mcpbs.games.MenuID;

import java.util.HashMap;

public class DesignationCmd extends Command {

    public DesignationCmd(String name, String description) {
        super(name, description);
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        FormWindowSimple form = new FormWindowSimple("§b你的称号","§e以下是你拥有的称号");
        Player player = (Player)sender;
        Config config = new Config(Main.plugin.getDataFolder() + "/playerdata/" + player.getName() + ".yml");
        HashMap<String,Boolean> m = (HashMap)config.get("ch");
        for (String ss : m.keySet()){
            if (m.get(ss) == true){
                form.addButton(new ElementButton(ss + " §a[正在使用]",new ElementButtonImageData("path","textures/items/book_portfolio")));
            }
            if (m.get(ss) == false){
                form.addButton(new ElementButton(ss + " §e[点击使用]",new ElementButtonImageData("path","textures/items/book_portfolio")));
            }
        }
        player.showFormWindow(form, MenuID.CH_USE_FORM);
        return true;
    }
}
