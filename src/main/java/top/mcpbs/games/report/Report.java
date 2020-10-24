package top.mcpbs.games.report;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.window.FormWindowCustom;
import top.mcpbs.games.FormID;

import java.util.ArrayList;

public class Report extends Command {
    public Report(String name, String description) {
        super(name, description);
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        if (sender instanceof ConsoleCommandSender){
            sender.sendMessage("控制台不能使用此指令");
            return true;
        }else{
            FormWindowCustom f = new FormWindowCustom("§ereport");
            ArrayList<String> ll = new ArrayList();
            for (Player p : ((Player)sender).getLevel().getPlayers().values()){
                ll.add(p.getName());
            }
            ll.add("其他");
            f.addElement(new ElementDropdown("§c选择作弊玩家",ll));
            f.addElement(new ElementInput("§a作弊玩家跑了？名字写在这里即可~(将上一个选项选为“其他”生效)"));
            ArrayList<String> l = new ArrayList();
            l.add("飞行");
            l.add("加速");
            l.add("刷物品");
            l.add("杀戮光环");
            l.add("连点器");
            l.add("锁血");
            l.add("其他作弊类型");
            f.addElement(new ElementDropdown("§e作弊类型",l));
            f.addElement(new ElementInput("§b其他作弊类型(将上一个选项选为“其他作弊类型”生效)"));
            ((Player)sender).showFormWindow(f, FormID.REPORT_FORM);
        }
        return true;
    }
}
