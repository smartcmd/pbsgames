package top.mcpbs.games;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import top.mcpbs.games.chao.Chao;
import top.mcpbs.games.duel.LoadCmd;
import top.mcpbs.games.getid.GetBlockID;
import top.mcpbs.games.hub.Hub;
import top.mcpbs.games.pbc.AddPBC;
import top.mcpbs.games.pbc.RemPBC;
import top.mcpbs.games.report.Report;
import top.mcpbs.games.room.Room;
import top.mcpbs.games.sb.ScoreboardDe;

import java.io.File;
import java.util.HashMap;

public class Main extends PluginBase {

    public static ScoreboardDe s;
    public static Plugin plugin;
    public static HashMap<Player, Room> waiting = new HashMap<>();
    public static HashMap<Player, Room> playing = new HashMap<>();

    @Override
    public void onLoad() {
        this.getLogger().info("准备加载插件");
    }

    @Override
    public void onEnable() {
        plugin = this;
        s = new ScoreboardDe();
        this.getLogger().info("      ___.                                              ");
        this.getLogger().info("______\\_ |__   ______ _________    _____   ____   ______");
        this.getLogger().info("\\____ \\| __ \\ /  ___// ___\\__  \\  /     \\_/ __ \\ /  ___/");
        this.getLogger().info("|  |_> > \\_\\ \\\\___ \\/ /_/  > __ \\|  Y Y  \\  ___/ \\___ \\ ");
        this.getLogger().info("|   __/|___  /____  >___  (____  /__|_|  /\\___  >____  >");
        this.getLogger().info("|__|       \\/     \\/_____/     \\/      \\/     \\/     \\/ ");
        this.saveResource(this.getDataFolder() + "/helloform.yml",false);
        this.saveResource(this.getDataFolder() + "/report.yml",false);
        this.saveResource(this.getDataFolder() + "/duellevel.yml",false);
        this.saveResource(this.getDataFolder() + "/duelmode.yml",false);
        this.saveResource(this.getDataFolder() + "/lottery.yml",false);
        this.saveResource(this.getDataFolder() + "/屏蔽词.yml",false);
        this.saveResource(this.getDataFolder() + "/每日奖励.yml",false);
        this.saveResource(this.getDataFolder() + "/chao.yml",false);
        this.saveResource(this.getDataFolder() + "/rushlevel.yml",false);
        this.saveResource(this.getDataFolder() + "/timemessage.yml",false);
        File f = new File(Main.plugin.getDataFolder() + "/playerdata");
        if (!f.exists()){
            f.mkdir();
        }//检查playerdata文件夹情况
        Server.getInstance().getPluginManager().registerEvents(new top.mcpbs.games.reward.LR(),this);
        Server.getInstance().getPluginManager().registerEvents(new top.mcpbs.games.friend.LR(),this);
        Server.getInstance().getPluginManager().registerEvents(new top.mcpbs.games.playerinfo.LR(),this);
        Server.getInstance().getPluginManager().registerEvents(new top.mcpbs.games.report.LR(),this);
        Server.getInstance().getPluginManager().registerEvents(new top.mcpbs.games.pbc.LR(),this);
        Server.getInstance().getPluginManager().registerEvents(new top.mcpbs.games.helloform.LR(),this);
        Server.getInstance().getPluginManager().registerEvents(new top.mcpbs.games.rush.LR(),this);
        Server.getInstance().getPluginManager().registerEvents(new top.mcpbs.games.hub.LR(),this);
        Server.getInstance().getPluginManager().registerEvents(new top.mcpbs.games.LR(), this);
        Server.getInstance().getPluginManager().registerEvents(new top.mcpbs.games.lobby.LR(), this);
        Server.getInstance().getPluginManager().registerEvents(new top.mcpbs.games.particle.LR(), this);
        Server.getInstance().getPluginManager().registerEvents(new top.mcpbs.games.lottery.LR(), this);
        Server.getInstance().getPluginManager().registerEvents(new top.mcpbs.games.chao.LR(), this);
        Server.getInstance().getPluginManager().registerEvents(new top.mcpbs.games.uhc.LR(), this);
        Server.getInstance().getPluginManager().registerEvents(new top.mcpbs.games.duel.LR(), this);
        Server.getInstance().getPluginManager().registerEvents(new top.mcpbs.games.waitroom.LR(), this);
        Server.getInstance().getPluginManager().registerEvents(new top.mcpbs.games.designation.LR(), this);
        LoadCmd.loadCmd();
        Server.getInstance().getCommandMap().register("",new Hub("hub","返回主城"));
        Server.getInstance().getCommandMap().register("",new AddPBC("addpbc","增加一个屏蔽词 <屏蔽词> <replace to>",this));
        Server.getInstance().getCommandMap().register("",new RemPBC("rempbc","删除一个屏蔽词 <屏蔽词>",this));
        Server.getInstance().getCommandMap().register("",new Report("report","举报玩家作弊"));
        Server.getInstance().getCommandMap().register("",new top.mcpbs.games.rush.JoinRoom("joinrush","快速加入rush房间",this));
        Server.getInstance().getCommandMap().register("",new top.mcpbs.games.particle.Particle("particle","打开粒子系统"));
        Server.getInstance().getCommandMap().register("",new top.mcpbs.games.friend.FriendCmd("friend","好友系统"));
        Server.getInstance().getCommandMap().register("",new top.mcpbs.games.lottery.LotteryCmd("lottery","抽奖箱系统",this));
        Server.getInstance().getCommandMap().register("",new top.mcpbs.games.laba.LabaCmd("laba","小喇叭（发送全服消息）",this));
        Server.getInstance().getCommandMap().register("",new top.mcpbs.games.chao.JoinChao("chao","加入大乱斗"));
        Server.getInstance().getCommandMap().register("",new top.mcpbs.games.getid.GetIDInHand("getid",""));
        Server.getInstance().getCommandMap().register("",new GetBlockID("getblockid",""));
        Server.getInstance().getCommandMap().register("",new top.mcpbs.games.chao.ChaoStore("chaostore","大乱斗商店",this));
        Server.getInstance().getCommandMap().register("",new top.mcpbs.games.uhc.JoinRoom("joinuhc","UHC"));
        Server.getInstance().getCommandMap().register("",new top.mcpbs.games.designation.Designation("designation","designation"));
        this.getServer().getScheduler().scheduleRepeatingTask(new top.mcpbs.games.lobby.LobbyTask(this),5);
        this.getServer().getScheduler().scheduleRepeatingTask(new top.mcpbs.games.rush.RushTask(this),5);
        this.getServer().getScheduler().scheduleRepeatingTask(new top.mcpbs.games.particle.ParticleTask(this),1);
        this.getServer().getScheduler().scheduleRepeatingTask(new top.mcpbs.games.lobby.BossBarTask(this),1);
        this.getServer().getScheduler().scheduleRepeatingTask(new Chao(this),5);
        this.getServer().getScheduler().scheduleRepeatingTask(new top.mcpbs.games.timemessage.MessageTask(this),20);
        this.getServer().getScheduler().scheduleRepeatingTask(new top.mcpbs.games.uhc.TimeTask(this),20);
        this.getServer().getScheduler().scheduleRepeatingTask(new top.mcpbs.games.uhc.UHCTask(this),5);
        this.getServer().getScheduler().scheduleRepeatingTask(new top.mcpbs.games.uhc.TestWinTask(this),5);
        this.getServer().getScheduler().scheduleRepeatingTask(new top.mcpbs.games.duel.TestWinTask(this),5);
        this.getServer().getScheduler().scheduleRepeatingTask(new top.mcpbs.games.duel.DuelTask(this),5);
        this.getServer().getScheduler().scheduleRepeatingTask(new top.mcpbs.games.rush.TestWinTask(this),5);
    }

    @Override
    public void onDisable() {
        this.getLogger().info("正在关闭插件");
    }

}
