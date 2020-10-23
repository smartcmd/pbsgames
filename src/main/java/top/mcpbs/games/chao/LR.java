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
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.item.Item;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.Config;
import top.mcpbs.games.Main;
import top.mcpbs.games.MenuID;
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
        if (event.getEntity() instanceof Player && event.getCause() == EntityDamageEvent.DamageCause.FALL && ChaoTask.players.containsKey(event.getEntity())){
            event.setCancelled();
            return;
        }
        if (event.getEntity() instanceof Player && ChaoTask.players.containsKey(event.getEntity()) && ChaoTask.players.get(event.getEntity()) == false){
            event.setCancelled();
            return;
        }
        if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK && ChaoTask.players.containsKey(event.getEntity())) {
            Player damager = (Player) ((EntityDamageByEntityEvent)event).getDamager();
            if (damager.getName().equals(event.getEntity().getName())) {
                event.setCancelled();
                return;
            }
            if (event.getEntity() instanceof Player && (event.getEntity().getHealth() - event.getFinalDamage()) < 1 && ChaoTask.players.containsKey(event.getEntity()) && ChaoTask.players.get(event.getEntity()) == true) {
                event.setCancelled();
                Player player = ((Player) event.getEntity()).getPlayer();
                player.sendTitle("§c你阵亡了!");
                ChaoTask.chaoplayerinfo.get(player).put("death", (int) ChaoTask.chaoplayerinfo.get(player).get("death") + 1);
                Config config1 = new Config(Main.plugin.getDataFolder() + "/playerdata/" + player.getName() + ".yml");
                config1.set("chao", ChaoTask.chaoplayerinfo.get(player));
                config1.save();
                for (Player p : ChaoTask.chao.getPlayers().values()) {
                    p.sendMessage("§6玩家 §e" + player.getName() + " §d被玩家 §e" + damager.getName() + " §a击败了!");
                }
                player.setGamemode(3);
                player.setHealth(player.getMaxHealth());
                damager.getInventory().addItem(Item.get(322, 0, 1));
                ChaoTask.chaoplayerinfo.get(damager).put("kill", (int) ChaoTask.chaoplayerinfo.get(damager).get("kill") + 1);
                ChaoTask.chaoplayerinfo.get(damager).put("coin", Double.valueOf(String.format("%.2f", (double)ChaoTask.chaoplayerinfo.get(damager).get("coin") + 2 * (double) ChaoTask.chaoplayerinfo.get(damager).get("draw rate"))));
                damager.sendMessage("§a你获得了 §e2 * " + ChaoTask.chaoplayerinfo.get(damager).get("draw rate") + " §6的乱斗币!,§a输入§b/chaostore即可打开大乱斗商店!");
                Score.addScore(player,1);
                damager.sendMessage("§a你获得了 §e1 §6XP！");
                Effect eff = Effect.getEffect(Effect.REGENERATION);
                eff.setAmplifier(2);
                eff.setDuration(10 * 20);
                damager.addEffect(eff);
                Config config = new Config(Main.plugin.getDataFolder() + "/playerdata/" + damager.getName() + ".yml");
                config.set("chao", ChaoTask.chaoplayerinfo.get(damager));
                config.save();
                Server.getInstance().getScheduler().scheduleDelayedTask(new TeleportTask(Main.plugin, player), 20 * 5);
            }
        }else if (ChaoTask.players.containsKey(event.getEntity()) && event.getCause() != EntityDamageEvent.DamageCause.FALL){
            if (event.getEntity() instanceof Player && (event.getEntity().getHealth() - event.getFinalDamage()) < 1 && ChaoTask.players.containsKey(event.getEntity()) && ChaoTask.players.get(event.getEntity()) == true) {
                event.setCancelled();
                Player player = ((Player) event.getEntity()).getPlayer();
                player.sendTitle("§c你阵亡了!");
                player.sendMessage("§e你失去了1分数");
                Score.remScore(player,1);
                ChaoTask.chaoplayerinfo.get(player).put("death", (int) ChaoTask.chaoplayerinfo.get(player).get("death") + 1);
                Config config1 = new Config(Main.plugin.getDataFolder() + "/playerdata/" + player.getName() + ".yml");
                config1.set("chao", ChaoTask.chaoplayerinfo.get(player));
                config1.save();
                for (Player p : ChaoTask.chao.getPlayers().values()) {
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
        if (event.getMessage().equals("/hub") && ChaoTask.players.containsKey(event.getPlayer())){
            this.PlayerAccidentQuit(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        if (ChaoTask.players.containsKey(event.getPlayer())) {
            this.PlayerAccidentQuit(event.getPlayer());
        }
    }

    public void PlayerAccidentQuit(Player player){
        ChaoTask.players.remove(player);
        ChaoTask.chaoplayerinfo.remove(player);
        Room.aplaying.remove(player);
        player.setMaxHealth(20);
        player.setHealth(player.getMaxHealth());
    }

    @EventHandler
    public void onFormResponse(PlayerFormRespondedEvent event){
        if (event.getFormID() == MenuID.CHAO_STORE_LIST){
            FormResponseSimple response = (FormResponseSimple) event.getResponse();
            if (response == null){
                return;
            }
            switch (response.getClickedButtonId()){
                case 0:
                    FormWindowSimple form0 = new FormWindowSimple("§a装备商店","§e看看有什么需要的吧~");
                    event.getPlayer().showFormWindow(form0,MenuID.CHAO_STORE_EQUIPMENT);//装备商店还没加商品~
                    break;
                case 1:
                    FormWindowSimple form1 = new FormWindowSimple("§a天赋强化","§e看看有什么想要强化的吧~");
                    form1.addButton(new ElementButton("§a增加 §6百分之20 §d乱斗币吸取倍率" + "\n§e需要50乱斗币",new ElementButtonImageData("path","textures/ui/anvil_icon")));
                    form1.addButton(new ElementButton("§e增加 §d2 §c血量" + "\n§e需要60乱斗币",new ElementButtonImageData("path","textures/ui/anvil_icon")));
                    event.getPlayer().showFormWindow(form1,MenuID.CHAO_STORE_TALENT);
            }
        }
        if (event.getFormID() == MenuID.CHAO_STORE_TALENT){
            FormResponseSimple response = (FormResponseSimple) event.getResponse();
            if (response == null){
                return;
            }
            switch(response.getClickedButtonId()){
                case 0:
                    if ((double)ChaoTask.chaoplayerinfo.get(event.getPlayer()).get("coin") < 50){
                        event.getPlayer().sendMessage("§c你的乱斗币不足!");
                    }else{
                        ChaoTask.chaoplayerinfo.get(event.getPlayer()).put("coin",((double)ChaoTask.chaoplayerinfo.get(event.getPlayer()).get("coin")) - 50);
                        ChaoTask.chaoplayerinfo.get(event.getPlayer()).put("draw rate", ((double)ChaoTask.chaoplayerinfo.get(event.getPlayer()).get("draw rate") + 0.2));
                        Config config0 = new Config(Main.plugin.getDataFolder() + "/playerdata/" + event.getPlayer().getName() + ".yml");
                        config0.set("chao",ChaoTask.chaoplayerinfo.get(event.getPlayer()));
                        event.getPlayer().sendMessage("§a购买成功！");
                    }
                    break;
                case 1:
                    if ((double)ChaoTask.chaoplayerinfo.get(event.getPlayer()).get("coin") < 60){
                        event.getPlayer().sendMessage("§c你的乱斗币不足!");
                    }else{
                        ChaoTask.chaoplayerinfo.get(event.getPlayer()).put("coin",(double)ChaoTask.chaoplayerinfo.get(event.getPlayer()).get("coin") - 60);
                        ChaoTask.chaoplayerinfo.get(event.getPlayer()).put("health", ((int)(ChaoTask.chaoplayerinfo.get(event.getPlayer()).get("health")) + 2));
                        Config config0 = new Config(Main.plugin.getDataFolder() + "/playerdata/" + event.getPlayer().getName() + ".yml");
                        config0.set("chao",ChaoTask.chaoplayerinfo.get(event.getPlayer()));
                        event.getPlayer().sendMessage("§a购买成功！");
                        event.getPlayer().setMaxHealth((int) ((int)(ChaoTask.chaoplayerinfo.get(event.getPlayer()).get("health")) + 2));
                        event.getPlayer().setHealth(event.getPlayer().getMaxHealth());
                    }
                    break;
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if (ChaoTask.players.containsKey(event.getPlayer()) && ChaoTask.players.get(event.getPlayer()) == false && event.getPlayer().getInventory().getItemInHand().getId() == 355){
            Server.getInstance().getCommandMap().dispatch(event.getPlayer(),"hub");
            Server.getInstance().getPluginManager().callEvent(new PlayerCommandPreprocessEvent(event.getPlayer(),"/hub"));
        }
    }
}
