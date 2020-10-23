package top.mcpbs.games.particle;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.ParticleEffect;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.Config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ParticleTask extends PluginTask {

    private int tick;
    public static HashMap<Player, Integer> speed = new HashMap();
    public static HashMap<Player, String> use = new HashMap();

    public ParticleTask(Plugin owner) {
        super(owner);
        this.tick = 0;
    }

    @Override
    public void onRun(int i) {
        this.tick += 1;
        for (Player player : Server.getInstance().getOnlinePlayers().values()){
            if (!speed.containsKey(player)){
                if (new File(owner.getDataFolder() + "/playerdata/" + player.getName() + ".yml").exists()){
                    Config c = new Config(owner.getDataFolder() + "/playerdata/" + player.getName() + ".yml");
                    if (c.exists("particle speed")) {
                        speed.put(player, (int) c.get("particle speed"));
                    }else{
                        continue;
                    }
                }else{
                    continue;
                }
            }

            if (!use.containsKey(player)){
                if (new File(owner.getDataFolder() + "/playerdata/" + player.getName() + ".yml").exists()){
                    Config c = new Config(owner.getDataFolder() + "/playerdata/" + player.getName() + ".yml");
                    use.put(player, "off");
                    if (!c.exists("particle")){
                        continue;
                    }
                    for (Map.Entry<String, Boolean> e : ((HashMap<String, Boolean>)c.get("particle")).entrySet()){
                        if (e.getValue() == true){
                            use.put(player, e.getKey());
                        }
                    }
                    if (use.get(player) == "off"){
                        continue;
                    }
                }
            }
            if (use.get(player).equals("happyvillager") && (this.tick % speed.get(player)) == 0){
                player.getLevel().addParticleEffect(player.getPosition().add(0, 0.5, 0), ParticleEffect.VILLAGER_HAPPY);
            }
            if (use.get(player).equals("blueflame") && (this.tick % speed.get(player)) == 0){
                player.getLevel().addParticleEffect(player.getPosition().add(0, 0.5, 0), ParticleEffect.BLUE_FLAME);
            }
            if (use.get(player).equals("lavadrip") && (this.tick % speed.get(player)) == 0){
                player.getLevel().addParticleEffect(player.getPosition().add(0, 0.5, 0), ParticleEffect.LAVA_DRIP);
                player.getLevel().addParticleEffect(player.getPosition().add(0, 0.5, 0), ParticleEffect.LAVA_PARTICLE);
            }
            if (use.get(player).equals("angryvillager") && (this.tick % speed.get(player)) == 0){
                player.getLevel().addParticleEffect(player.getPosition().add(0, 0.5, 0), ParticleEffect.VILLAGER_ANGRY);
            }
            if (use.get(player).equals("heart") && (this.tick % speed.get(player)) == 0){
                player.getLevel().addParticleEffect(player.getPosition().add(0, 0.5, 0), ParticleEffect.HEART);
            }
        }
        if (this.tick >= 20){
            this.tick = 0;
        }
    }
}
