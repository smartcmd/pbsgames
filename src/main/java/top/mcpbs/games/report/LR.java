package top.mcpbs.games.report;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.utils.Config;
import top.mcpbs.games.Main;
import top.mcpbs.games.FormID;
import top.mcpbs.games.util.DateUtil;

import java.util.HashMap;

public class LR implements Listener {
    @EventHandler
    public void onForm(PlayerFormRespondedEvent event){
        if(event.getFormID() == FormID.REPORT_FORM){
            FormResponseCustom response = (FormResponseCustom) event.getResponse();
            if (response == null){
                return;
            }
            HashMap m = new HashMap();
            m.put("time", DateUtil.getDate("yyyy/MM/dd"));
            String berp = null;
            if (!response.getDropdownResponse(0).getElementContent().equals("其他")){
                m.put("player",response.getDropdownResponse(0).getElementContent());
                berp = response.getDropdownResponse(0).getElementContent();
            }else{
                m.put("player",response.getInputResponse(1));
                berp = response.getInputResponse(1);
            }
            if (!response.getDropdownResponse(2).getElementContent().equals("其他作弊类型")){
                m.put("type",response.getDropdownResponse(2).getElementContent());
            }else{
                m.put("type",response.getInputResponse(3));
            }
            m.put("reporter",event.getPlayer().getName());
            Config c = new Config(Main.plugin.getDataFolder() + "/report.yml");
            c.set(berp + " is reported by " + event.getPlayer().getName(),m);
            c.save();
            event.getPlayer().sendMessage("§a举报成功！等待管理员处理！感谢你的举报！");
            for(Player opss : Server.getInstance().getOnlinePlayers().values()){
                if (opss.isOp()){
                    opss.sendMessage("§a玩家 " + event.getPlayer().getName() + " §a提交了一个举报，请及时处理！");
                    opss.sendMessage("§6内容如下: §e" + m);
                }
            }
        }
    }
}
