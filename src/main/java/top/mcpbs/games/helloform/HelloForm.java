package top.mcpbs.games.helloform;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.Config;
import top.mcpbs.games.Main;
import top.mcpbs.games.MenuID;

import java.util.HashMap;

public class HelloForm{

    private static String content = null;
    private static String title = null;
    public static void showForm(Player player){
        if (content == null || title == null){
            Config config = new Config(Main.plugin.getDataFolder() + "/helloform.yml");
            content = config.getString("Content");
            title = config.getString("Title");
        }
        FormWindowSimple form = new FormWindowSimple(title,content);
        form.addButton(new ElementButton("好的"));
        player.showFormWindow(form, MenuID.LOBBY_JOIN_FORM);
    }
}
