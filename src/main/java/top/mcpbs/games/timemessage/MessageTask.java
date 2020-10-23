package top.mcpbs.games.timemessage;

import cn.nukkit.Server;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.Config;

import java.util.HashMap;
import java.util.Map;

public class MessageTask extends PluginTask {

    public int tick;
    public int maxtick;
    public HashMap<String, Integer> message = new HashMap();

    public MessageTask(Plugin owner) {
        super(owner);
        this.tick = 0;
        Config c = new Config(owner.getDataFolder() + "/timemessage.yml");
        for (Map.Entry<String, Object> e: c.getAll().entrySet()){
            this.maxtick += (int)e.getValue();
            this.message.put(e.getKey(),this.maxtick);
        }
    }

    @Override
    public void onRun(int i) {
        this.tick += 1;
        for (Map.Entry<String, Integer> e : this.message.entrySet()){
            if (this.tick == e.getValue()){
                Server.getInstance().broadcastMessage(e.getKey());
            }
        }
        if (this.tick == this.maxtick){
            this.tick = 0;
        }
    }
}
