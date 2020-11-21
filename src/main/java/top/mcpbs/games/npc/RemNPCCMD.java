package top.mcpbs.games.npc;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;

public class RemNPCCMD extends Command {
    public RemNPCCMD(String name, String description) {
        super(name, description);
        this.setPermission("op");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        if (sender instanceof ConsoleCommandSender){
            sender.sendMessage("控制台不能使用此指令");
        }else{
            NPC npc = NPCTool.getAllNPC().get(strings[0]);
            npc.close();
        }
        return true;
    }
}
