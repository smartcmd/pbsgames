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
import top.mcpbs.games.playerinfo.PlayerInfoTool;
import top.mcpbs.games.playerinfo.coin.Coin;
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
            return;//fall cancelled
        }

        if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK){
            EntityDamageByEntityEvent eventd = (EntityDamageByEntityEvent)event;
            if (Chao.players.containsKey(eventd.getEntity()) && Chao.players.containsKey(eventd.getDamager())){

                if (!Chao.players.get(eventd.getEntity()) || !Chao.players.get(eventd.getDamager())){
                    event.setCancelled();
                    return;//如果pvp未开启就撤回
                }

                if ((eventd.getEntity().getHealth() - eventd.getFinalDamage()) < 1){//kill by player
                    event.setCancelled();
                    playerDead((Player)eventd.getEntity(),(Player)eventd.getDamager());
                    return;
                }
            }
        }

        if ((event.getEntity().getHealth() - event.getFinalDamage()) < 1 && Chao.players.containsKey(event.getEntity())){//dead by nothing...
            event.setCancelled();
            playerDead((Player)event.getEntity(),null);
            return;
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
        Room.aplaying.remove(player);
        player.setMaxHealth(20);
        player.setHealth(player.getMaxHealth());
        player.removeAllEffects();
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
                    if (Chao.getCoin(event.getPlayer()) < 50){
                        event.getPlayer().sendMessage("§c你的乱斗币不足!");
                    }else{
                        Chao.remCoin(event.getPlayer(),50);
                        Chao.addDrawRate(event.getPlayer(),0.2);
                        event.getPlayer().sendMessage("§a购买成功！");
                    }
                    break;
                case 1:
                    if (Chao.getCoin(event.getPlayer()) < 60){
                        event.getPlayer().sendMessage("§c你的乱斗币不足!");
                    }else{
                        Chao.remCoin(event.getPlayer(),60);
                        Chao.addHealth(event.getPlayer(),2);
                        event.getPlayer().sendMessage("§a购买成功！");
                        event.getPlayer().setMaxHealth(Chao.getHealth(event.getPlayer()));
                        event.getPlayer().setHealth(event.getPlayer().getMaxHealth());
                    }
                    break;
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if (Chao.players.containsKey(event.getPlayer()) && Chao.players.get(event.getPlayer()) == false && event.getPlayer().getInventory().getItemInHand().getId() == 355){
            Server.getInstance().dispatchCommand(event.getPlayer(),"hub");
            Server.getInstance().getPluginManager().callEvent(new PlayerCommandPreprocessEvent(event.getPlayer(),"/hub"));
        }
    }

    public void playerDead(Player dead,Player killer){
        if (killer != null){
            dead.getInventory().clearAll();
            dead.setGamemode(3);
            dead.sendTitle("§b游戏 §7» §c你阵亡了!");
            dead.setHealth(dead.getMaxHealth());
            Chao.remCoin(dead,0.25);
            Chao.players.put(dead,false);
            Server.getInstance().getScheduler().scheduleDelayedTask(new TeleportTask(Main.plugin, dead), 20 * 5);//dead

            killer.sendMessage("§b游戏 §7» §a你杀死了玩家 " + dead.getName());
            killer.sendMessage("§b游戏 §7» §a你获得了1 * " + Chao.getdrawRate(killer));
            Chao.addCoin(killer,1 * Chao.getdrawRate(killer));

            for (Player player : killer.getLevel().getPlayers().values()){
                player.sendMessage("§b游戏 §7» §e玩家 " + killer.getName() + " 杀死了玩家 " + dead.getName());
            }
        }else if (killer == null){
            dead.getInventory().clearAll();
            dead.setGamemode(3);
            dead.sendTitle("§b游戏 §7» §c你阵亡了!");
            dead.setHealth(dead.getMaxHealth());
            Chao.remCoin(dead,0.25);
            Chao.players.put(dead,false);
            Server.getInstance().getScheduler().scheduleDelayedTask(new TeleportTask(Main.plugin, dead), 20 * 5);//dead

            for (Player player : killer.getLevel().getPlayers().values()){
                player.sendMessage("§b游戏 §7» §e玩家 " + dead.getName() + " 不知怎么的就死了...");
            }
        }
    }
}
