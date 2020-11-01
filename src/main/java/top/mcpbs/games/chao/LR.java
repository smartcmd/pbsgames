package top.mcpbs.games.chao;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.player.PlayerCommandPreprocessEvent;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.item.Item;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.Config;
import top.mcpbs.games.FormID;
import top.mcpbs.games.Main;
import top.mcpbs.games.playerinfo.score.Score;
import top.mcpbs.games.room.Room;

public class LR implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if (event.getBlock().getLevel().getName().equals("chao") && event.getPlayer().getGamemode() != 1){
            event.setCancelled();
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        if (event.getBlock().getLevel().getName().equals("chao") && event.getPlayer().getGamemode() != 1){
            event.setCancelled();
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event){
        if (event.getEntity() instanceof Player && event.getCause() == EntityDamageEvent.DamageCause.FALL && Chao.players.containsKey(event.getEntity())){
            event.setCancelled();
            return;
        }
        if (event.getEntity() instanceof Player && Chao.players.containsKey(event.getEntity()) && Chao.players.get(event.getEntity()) == false){
            event.setCancelled();
            return;
        }
        if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK && Chao.players.containsKey(event.getEntity())) {
            Player damager = (Player) ((EntityDamageByEntityEvent)event).getDamager();
            if (damager.getName().equals(event.getEntity().getName())) {
                event.setCancelled();
                return;
            }
            if (event.getEntity() instanceof Player && (event.getEntity().getHealth() - event.getFinalDamage()) < 1 && Chao.players.containsKey(event.getEntity()) && Chao.players.get(event.getEntity()) == true) {
                event.setCancelled();
                Player player = ((Player) event.getEntity()).getPlayer();
                player.sendTitle("§c你阵亡了!");
                Chao.chaoplayerinfo.get(player).put("death", (int) Chao.chaoplayerinfo.get(player).get("death") + 1);
                Config config1 = new Config(Main.plugin.getDataFolder() + "/playerdata/" + player.getName() + ".yml");
                config1.set("chao", Chao.chaoplayerinfo.get(player));
                config1.save();
                for (Player p : Chao.chao.getPlayers().values()) {
                    p.sendMessage("§6玩家 §e" + player.getName() + " §d被玩家 §e" + damager.getName() + " §a击败了!");
                }
                player.setGamemode(3);
                player.setHealth(player.getMaxHealth());
                damager.getInventory().addItem(Item.get(322, 0, 1));
                Chao.chaoplayerinfo.get(damager).put("kill", (int) Chao.chaoplayerinfo.get(damager).get("kill") + 1);
                Chao.chaoplayerinfo.get(damager).put("coin", Double.valueOf(String.format("%.2f", (double) Chao.chaoplayerinfo.get(damager).get("coin") + 2 * (double) Chao.chaoplayerinfo.get(damager).get("draw rate"))));
                damager.sendMessage("§a你获得了 §e2 * " + Chao.chaoplayerinfo.get(damager).get("draw rate") + " §6的乱斗币!,§a输入§b/chaostore即可打开大乱斗商店!");
                Score.addScore(player,1);
                damager.sendMessage("§a你获得了 §e1 §6XP！");
                Effect eff = Effect.getEffect(Effect.REGENERATION);
                eff.setAmplifier(2);
                eff.setDuration(10 * 20);
                damager.addEffect(eff);
                Config config = new Config(Main.plugin.getDataFolder() + "/playerdata/" + damager.getName() + ".yml");
                config.set("chao", Chao.chaoplayerinfo.get(damager));
                config.save();
                Server.getInstance().getScheduler().scheduleDelayedTask(new TeleportTask(Main.plugin, player), 20 * 5);
            }
        }else if (Chao.players.containsKey(event.getEntity()) && event.getCause() != EntityDamageEvent.DamageCause.FALL){
            if (event.getEntity() instanceof Player && (event.getEntity().getHealth() - event.getFinalDamage()) < 1 && Chao.players.containsKey(event.getEntity()) && Chao.players.get(event.getEntity()) == true) {
                event.setCancelled();
                Player player = ((Player) event.getEntity()).getPlayer();
                player.sendTitle("§c你阵亡了!");
                player.sendMessage("§e你失去了1分数");
                Score.remScore(player,1);
                Chao.chaoplayerinfo.get(player).put("death", (int) Chao.chaoplayerinfo.get(player).get("death") + 1);
                Config config1 = new Config(Main.plugin.getDataFolder() + "/playerdata/" + player.getName() + ".yml");
                config1.set("chao", Chao.chaoplayerinfo.get(player));
                config1.save();
                for (Player p : Chao.chao.getPlayers().values()) {
                    p.sendMessage("§6玩家 §e" + player.getName() + " §6不知怎么的就死了!");
                }
                player.setGamemode(3);
                player.setHealth(player.getMaxHealth());
                Server.getInstance().getScheduler().scheduleDelayedTask(new TeleportTask(Main.plugin, player), 20 * 5);
            }
        }
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event){
        if (event.getMessage().equals("/hub") && Chao.players.containsKey(event.getPlayer())){
            this.PlayerAccidentQuit(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        if (Chao.players.containsKey(event.getPlayer())) {
            this.PlayerAccidentQuit(event.getPlayer());
        }
    }

    public void PlayerAccidentQuit(Player player){
        Chao.players.remove(player);
        Chao.chaoplayerinfo.remove(player);
        Room.aplaying.remove(player);
        player.setMaxHealth(20);
        player.setHealth(player.getMaxHealth());
    }

    @EventHandler
    public void onFormResponse(PlayerFormRespondedEvent event){
        if (event.getFormID() == FormID.CHAO_STORE_LIST){
            FormResponseSimple response = (FormResponseSimple) event.getResponse();
            if (response == null){
                return;
            }
            switch (response.getClickedButtonId()){
                case 0:
                    Forms.showEquipmentStoreForm(event.getPlayer());
                    break;
                case 1:
                    Forms.showGiftShopForm(event.getPlayer());
                    break;
            }
        }
        if (event.getFormID() == FormID.CHAO_STORE_TALENT){
            FormResponseSimple response = (FormResponseSimple) event.getResponse();
            if (response == null){
                return;
            }
            switch(response.getClickedButtonId()){
                case 0:
                    if ((double) Chao.chaoplayerinfo.get(event.getPlayer()).get("coin") < 50){
                        event.getPlayer().sendMessage("§c你的乱斗币不足!");
                    }else{
                        Chao.chaoplayerinfo.get(event.getPlayer()).put("coin",((double) Chao.chaoplayerinfo.get(event.getPlayer()).get("coin")) - 50);
                        Chao.chaoplayerinfo.get(event.getPlayer()).put("draw rate", ((double) Chao.chaoplayerinfo.get(event.getPlayer()).get("draw rate") + 0.2));
                        Config config0 = new Config(Main.plugin.getDataFolder() + "/playerdata/" + event.getPlayer().getName() + ".yml");
                        config0.set("chao", Chao.chaoplayerinfo.get(event.getPlayer()));
                        event.getPlayer().sendMessage("§a购买成功！");
                    }
                    break;
                case 1:
                    if ((double) Chao.chaoplayerinfo.get(event.getPlayer()).get("coin") < 60){
                        event.getPlayer().sendMessage("§c你的乱斗币不足!");
                    }else{
                        Chao.chaoplayerinfo.get(event.getPlayer()).put("coin",(double) Chao.chaoplayerinfo.get(event.getPlayer()).get("coin") - 60);
                        Chao.chaoplayerinfo.get(event.getPlayer()).put("health", ((int)(Chao.chaoplayerinfo.get(event.getPlayer()).get("health")) + 2));
                        Config config0 = new Config(Main.plugin.getDataFolder() + "/playerdata/" + event.getPlayer().getName() + ".yml");
                        config0.set("chao", Chao.chaoplayerinfo.get(event.getPlayer()));
                        event.getPlayer().sendMessage("§a购买成功！");
                        event.getPlayer().setMaxHealth((int) ((int)(Chao.chaoplayerinfo.get(event.getPlayer()).get("health")) + 2));
                        event.getPlayer().setHealth(event.getPlayer().getMaxHealth());
                    }
                    break;
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if (Chao.players.containsKey(event.getPlayer()) && Chao.players.get(event.getPlayer()) == false && event.getPlayer().getInventory().getItemInHand().getId() == 355){
            Server.getInstance().getCommandMap().dispatch(event.getPlayer(),"hub");
            Server.getInstance().getPluginManager().callEvent(new PlayerCommandPreprocessEvent(event.getPlayer(),"/hub"));
        }
    }
}
