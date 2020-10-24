package top.mcpbs.games.uhc;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.BlockID;
import cn.nukkit.entity.weather.EntityLightning;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityRegainHealthEvent;
import cn.nukkit.event.player.*;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.item.Item;
import cn.nukkit.potion.Effect;
import top.mcpbs.games.FormID;
import top.mcpbs.games.Main;
import top.mcpbs.games.duel.DuelRoom;
import top.mcpbs.games.playerinfo.diamond.Diamond;
import top.mcpbs.games.playerinfo.score.Score;
import top.mcpbs.games.room.Room;

import java.util.Random;

public class LR implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        if(Room.aplaying.containsKey(event.getPlayer()) && Room.aplaying.get(event.getPlayer()) instanceof UHCRoom){
            Room.aplaying.get(event.getPlayer()).playerAccidentQuit(event.getPlayer());
        }
        if (Room.awaiting.containsKey(event.getPlayer()) && Room.awaiting.get(event.getPlayer()) instanceof UHCRoom){
            Room.awaiting.get(event.getPlayer()).playerAccidentQuit(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerCmd(PlayerCommandPreprocessEvent event){
        if (event.getMessage().equals("/hub") && Room.aplaying.containsKey(event.getPlayer()) && Room.aplaying.get(event.getPlayer()) instanceof UHCRoom && Room.aplaying.get(event.getPlayer()).isend == false && !((UHCRoom) Room.aplaying.get(event.getPlayer())).isdead.get(event.getPlayer())){
            Room.aplaying.get(event.getPlayer()).playerAccidentQuit(event.getPlayer());
        }
        if (event.getMessage().equals("/hub") && Room.awaiting.containsKey(event.getPlayer()) && Room.awaiting.get(event.getPlayer()) instanceof UHCRoom){
            Room.awaiting.get(event.getPlayer()).playerAccidentQuit(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event){
        if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            EntityDamageByEntityEvent event1 = (EntityDamageByEntityEvent)event;
            if (event.getEntity() instanceof Player && event1.getDamager() instanceof Player && Room.aplaying.get(event1.getDamager()) instanceof UHCRoom) {
                Player damager = (Player) event1.getDamager();
                Player entity = (Player) event.getEntity();
                if (((UHCRoom) Room.aplaying.get(damager)).gametime > 60 * 15) {
                    event.setCancelled();
                    damager.sendMessage("§e现在是发育阶段，PVP暂未开启哦");
                    return;
                }
                for (Team t : ((UHCRoom) Room.aplaying.get(damager)).team){
                    if (t.player.contains(entity) && t.player.contains(damager)){
                        event.setCancelled();
                        damager.sendMessage("§e在？为啥要打队友？");
                    }
                }
                if ((entity.getHealth() - event.getFinalDamage()) < 1) {
                    event.setCancelled();
                    entity.setGamemode(3);
                    entity.sendTitle("§c你死了!", "§6不要灰心，下局加油吧！");
                    entity.sendMessage("§c你死了!§6不要灰心，下局加油吧！");
                    entity.sendMessage("§a输入/hub返回大厅，或继续观战~");
                    entity.sendMessage("§e你由于被淘汰而失去了5分数!");

                    for (Item drop : entity.getInventory().getContents().values()){
                        entity.dropItem(drop);
                    }

                    ((UHCRoom)Room.aplaying.get(entity)).isdead.put(entity,true);
                    Score.remScore(entity,5);
                    entity.setHealth(entity.getMaxHealth());
                    entity.getInventory().clearAll();
                    Item hub = Item.get(355,0,1);
                    hub.setCustomName("返回主城");
                    Item again = Item.get(339,0,1);
                    again.setCustomName("再来一局");
                    entity.getInventory().setItem(2,hub);
                    entity.getInventory().setItem(5,again);

                    UHCRoom room = (UHCRoom) Room.aplaying.get(entity);
                    int killnum = room.killnum.get(entity);
                    Score.addScore(entity,killnum * 2);
                    Diamond.addDiamond(entity,killnum * 2);

                    Server.getInstance().getScheduler().scheduleDelayedTask(new SettlementFormTask(Main.plugin,entity,false),3 * 20);

                    String color = ((UHCRoom)Room.aplaying.get(entity)).playerteam.get(entity).color;;
                    String color1 = ((UHCRoom)Room.aplaying.get(damager)).playerteam.get(damager).color;;
                    damager.sendTitle("", "§a你击杀了玩家 " + color + entity.getName());
                    damager.sendMessage("§a你获得了 §e金苹果 * 2");
                    damager.sendMessage("§a你获得了 §b速度 * 30s");
                    ((UHCRoom) Room.aplaying.get(damager)).killnum.put(damager,((UHCRoom) Room.aplaying.get(damager)).killnum.get(damager) + 1);
                    for (Player player : Room.aplaying.get(damager).roomlevel.getPlayers().values()) {
                        player.sendMessage("§e玩家 " + color1 + damager.getName() + " §a击杀了玩家 " + color + entity.getName());
                    }
                    Effect speed = Effect.getEffect(1);
                    speed.setDuration(30 * 20);
                    speed.setAmplifier(1);
                    damager.addEffect(speed);
                    damager.getInventory().addItem(Item.get(322, 0, 2));
                    EntityLightning l = new EntityLightning(entity.getChunk(), EntityLightning.getDefaultNBT(entity.getPosition()));
                    l.setEffect(false);
                    l.spawnToAll();
                }
            }
        }else if (event.getEntity() instanceof Player && Room.aplaying.get(event.getEntity()) instanceof UHCRoom){
            Player entity = (Player) event.getEntity();
            if ((entity.getHealth() - event.getFinalDamage()) < 1) {
                event.setCancelled();
                entity.setGamemode(3);
                entity.sendTitle("§c你死了!", "§6不要灰心，下局加油吧！");
                entity.sendMessage("§c你死了!§6不要灰心，下局加油吧！");
                entity.sendMessage("§a输入/hub返回大厅，或继续观战~");
                entity.sendMessage("§e你失去了5分数");

                UHCRoom room = (UHCRoom) Room.aplaying.get(entity);
                int killnum = room.killnum.get(entity);
                Score.addScore(entity,killnum * 2);
                Diamond.addDiamond(entity,killnum * 2);

                Server.getInstance().getScheduler().scheduleDelayedTask(new SettlementFormTask(Main.plugin,entity,false),3 * 20);

                for (Item drop : entity.getInventory().getContents().values()){
                    entity.dropItem(drop);
                }

                entity.getInventory().clearAll();
                Item hub = Item.get(355,0,1);
                hub.setCustomName("返回主城");
                Item again = Item.get(339,0,1);
                again.setCustomName("再来一局");
                entity.getInventory().setItem(2,hub);
                entity.getInventory().setItem(5,again);

                ((UHCRoom)Room.aplaying.get(entity)).isdead.put(entity,true);
                Score.remScore(entity,5);
                entity.setHealth(entity.getMaxHealth());
                String color = ((UHCRoom)Room.aplaying.get(entity)).playerteam.get(entity).color;
                for (Player player : Room.aplaying.get(entity).roomlevel.getPlayers().values()) {
                    player.sendMessage("§e玩家 §a" + color + entity.getName() + "§e不知怎么的就死了！");
                }
                EntityLightning l = new EntityLightning(entity.getChunk(), EntityLightning.getDefaultNBT(entity.getPosition()));
                l.setEffect(false);
                l.spawnToAll();
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if (Room.awaiting.containsKey(event.getPlayer()) && Room.awaiting.get(event.getPlayer()) instanceof UHCRoom){
            event.setCancelled();
        }
        if (Room.aplaying.containsKey(event.getPlayer()) && Room.aplaying.get(event.getPlayer()) instanceof UHCRoom){
            if (event.getBlock().getId() == 14){//gold
                event.setDrops(new Item[]{Item.get(266,0,1)});
                event.setDropExp(30);
            }
            if (event.getBlock().getId() == 15){//iron
                event.setDrops(new Item[]{Item.get(265,0,1)});
                event.setDropExp(30);
            }
            if (event.getBlock().getId() == 16){//coal
                event.setDrops(new Item[]{Item.get(50,0,3)});
                event.setDropExp(30);
            }
            if (event.getBlock().getId() == 21){//lapis
                event.setDrops(new Item[]{Item.get(351,4,2),Item.get(340,0,2)});
                event.setDropExp(30);
            }
            if (event.getBlock().getId() == 56){//diamond
                event.setDrops(new Item[]{Item.get(264,0,1)});
                event.setDropExp(30);
            }
            if (event.getBlock().getId() == 73){//redstone
                event.setDropExp(30);
                Random r = new Random();
                int rnum = r.nextInt(100) + 1;
                if (rnum <= 5){
                    event.setDrops(new Item[]{Item.get(372,0,2),Item.get(379,0,1)});
                }else{
                    event.setDrops(new Item[]{Item.get(372,0,2)});
                }
            }
            if (event.getBlock().getId() == BlockID.EMERALD_ORE){//emer
                event.setDrops(new Item[]{});
                event.setDropExp(100);
            }
            if (event.getBlock().getId() == BlockID.TALL_GRASS || event.getBlock().getId() == 175){
                event.setDrops(new Item[]{Item.get(296,0,2)});
            }
            if (event.getBlock().getId() == BlockID.GRAVEL){
                event.setDrops(new Item[]{Item.get(318,0,2),Item.get(288,0,2)});
            }
            if (event.getBlock().getId() == 18 || event.getBlock().getId() == 161){//leave
                Random r = new Random();
                int rnum = r.nextInt(100) + 1;
                if (rnum <= 5){
                    event.setDrops(new Item[]{Item.get(287,0,3),Item.get(260,0,1)});
                }
            }
        }
    }

    @EventHandler
    public void onForm(PlayerFormRespondedEvent event){
        if(event.getFormID() == FormID.UHC_POTSTORE){
            FormResponseSimple response = (FormResponseSimple) event.getResponse();
            if (this.getPlayerYNum(event.getPlayer()) < 10){
                event.getPlayer().sendMessage("§c你的地狱疣不够哦~");
                return;
            }
            switch (response.getClickedButtonId()){
                case 0:
                    event.getPlayer().getInventory().addItem(Item.get(373,1,1));
                    break;
                case 1:
                    event.getPlayer().getInventory().addItem(Item.get(373,5,1));
                    break;
                case 2:
                    event.getPlayer().getInventory().addItem(Item.get(373,12,1));
                    break;
            }
            event.getPlayer().getInventory().remove(Item.get(50,0,10));
        }
    }

    public int getPlayerYNum(Player player) {
        int ynum = 0;
        if (!player.getInventory().getContents().isEmpty()) {
            for (Item i : player.getInventory().getContents().values()) {
                if (i.getId() == 50) {
                    ynum += 1;
                }
            }
        }
        return  ynum;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if (Room.aplaying.containsKey(event.getPlayer()) && Room.aplaying.get(event.getPlayer()) instanceof UHCRoom){
            if (event.getBlock().getId() == 117 && !((UHCRoom) Room.aplaying.get(event.getPlayer())).isdead.containsKey(event.getPlayer())){
                event.setCancelled();
                Form.potStore(event.getPlayer());
                return;
            }
            if (((UHCRoom) Room.aplaying.get(event.getPlayer())).isdead.containsKey(event.getPlayer()) || Room.aplaying.get(event.getPlayer()).isend == true) {
                if (event.getPlayer().getInventory().getItemInHand().getId() == 355) {
                    Server.getInstance().getCommandMap().dispatch(event.getPlayer(), "hub");
                    Server.getInstance().getPluginManager().callEvent(new PlayerCommandPreprocessEvent(event.getPlayer(), "/hub"));
                }
                if (event.getPlayer().getInventory().getItemInHand().getId() == 339){
                    Server.getInstance().getCommandMap().dispatch(event.getPlayer(),"hub");
                    Server.getInstance().getPluginManager().callEvent(new PlayerCommandPreprocessEvent(event.getPlayer(), "/hub"));
                    Server.getInstance().getCommandMap().dispatch(event.getPlayer(),"joinuhc");
                }
            }
        }
    }

    @EventHandler
    public void onPlayerHealthBack(EntityRegainHealthEvent event){
        if (Room.aplaying.containsKey(event.getEntity()) && Room.aplaying.get(event.getEntity()) instanceof UHCRoom){
            if (event.getRegainReason() == EntityRegainHealthEvent.CAUSE_REGEN){
                event.setAmount(0F);
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        if (Room.aplaying.containsKey(event.getPlayer()) && Room.aplaying.get(event.getPlayer()) instanceof UHCRoom && ((UHCRoom) Room.aplaying.get(event.getPlayer())).waittime <= 15){
            event.setCancelled();
        }
    }
}
