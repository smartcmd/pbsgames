package top.mcpbs.games.uhc;

import cn.nukkit.Player;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.level.format.generic.BaseFullChunk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Team {

    public String color;
    public ArrayList<Player> player;
    public String teamname;
    public Position spawnpos;

    public Team(ArrayList<Player> player, int id, Level level){
        this.player = player;
        switch (id){
            case 1:
                this.color = "§a";
                this.teamname = "§a绿队";
                break;
            case 2:
                this.color = "§b";
                this.teamname = "§b蓝队";
                break;
            case 3:
                this.color = "§c";
                this.teamname = "§c红队";
                break;
            case 4:
                this.color = "§d";
                this.teamname = "§d紫队";
                break;
            case 5:
                this.color = "§e";
                this.teamname = "§e黄队";
                break;
            case 6:
                this.color = "§6";
                this.teamname = "§6橙队";
                break;
            case 7:
                this.color = "§7";
                this.teamname = "§7灰队";
                break;
            case 8:
                this.color = "§f";
                this.teamname = "§f白队";
                break;
        }
        Random r = new Random();
        this.spawnpos = new Position((double)(r.nextInt(2001) - 1000), 250.0D, (double)(r.nextInt(2001) - 1000),level);
        BaseFullChunk chunk = (BaseFullChunk) this.spawnpos.getChunk();
        try {
            chunk.load(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.spawnpos = this.spawnpos.level.getSafeSpawn(this.spawnpos);
    }
}
