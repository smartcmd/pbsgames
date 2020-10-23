package top.mcpbs.games.helloform;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.Config;
import top.mcpbs.games.MenuID;

import java.util.HashMap;

public class HelloForm{
    public static void showForm(Plugin p,Player player){
        Config config = new Config(p.getDataFolder() + "/helloform.yml");
        HashMap m = (HashMap)config.get("HelloForm");
        FormWindowSimple form = new FormWindowSimple((String)m.get("Title"),(String)m.get("Content"));
        form.addButton(new ElementButton("好的"));
        player.showFormWindow(form, MenuID.LOBBY_JOIN_FORM);
    }
}
