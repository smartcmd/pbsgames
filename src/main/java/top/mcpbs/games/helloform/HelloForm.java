package top.mcpbs.games.helloform;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.Config;
import top.mcpbs.games.Main;
import top.mcpbs.games.FormID;

public class HelloForm{

    private static String content = null;
    private static String title = null;
    public static void showForm(Player player){
        if (content == null || title == null){
            Config config = new Config(Main.plugin.getDataFolder() + "/helloform.yml");
            content = config.getString("content");
            title = config.getString("title");
        }
        FormWindowModal form = new FormWindowModal(title,content,"确定","取消");
        player.showFormWindow(form, FormID.LOBBY_JOIN_FORM);
    }
}
