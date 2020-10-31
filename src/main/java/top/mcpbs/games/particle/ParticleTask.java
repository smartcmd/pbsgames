package top.mcpbs.games.particle;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.level.ParticleEffect;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.PluginTask;

public class ParticleTask extends PluginTask {

    private int tick;

    public ParticleTask(Plugin owner) {
        super(owner);
        this.tick = 0;
    }

    @Override
    public void onRun(int i) {
        this.tick += 1;
        for (Player player : Server.getInstance().getOnlinePlayers().values()) {
            String use = ParticleTool.getPlayerUseParticle(player);
            if (use == null){
                continue;
            }
            int speed = ParticleTool.getPlayerParticleSpeed(player);
            if ((this.tick % speed) != 0){
                continue;
            }
            switch(use){
                case "happyvillager":
                    player.getLevel().addParticleEffect(player.getPosition().add(0, 0.5, 0), ParticleEffect.VILLAGER_HAPPY);
                    break;
                case "blueflame":
                    player.getLevel().addParticleEffect(player.getPosition().add(0, 0.5, 0), ParticleEffect.BLUE_FLAME);
                    break;
                case "lavadrip":
                    player.getLevel().addParticleEffect(player.getPosition().add(0, 0.5, 0), ParticleEffect.LAVA_DRIP);
                    player.getLevel().addParticleEffect(player.getPosition().add(0, 0.5, 0), ParticleEffect.LAVA_PARTICLE);
                    break;
                case "angryvillager":
                    player.getLevel().addParticleEffect(player.getPosition().add(0, 0.5, 0), ParticleEffect.VILLAGER_ANGRY);
                    break;
                case "heart":
                    player.getLevel().addParticleEffect(player.getPosition().add(0, 0.5, 0), ParticleEffect.HEART);
                    break;
            }
        }
        if (this.tick >= 20) {
            this.tick = 0;
        }
    }
}
