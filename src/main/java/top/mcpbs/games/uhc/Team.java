package top.mcpbs.games.uhc;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockAir;
import cn.nukkit.block.BlockID;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.level.format.generic.BaseFullChunk;
import cn.nukkit.math.Vector3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Team {

    public String color;
    public ArrayList<Player> player;
    public String teamname;
    public Position spawnpos;
    public Block fillblock;

    public Team(ArrayList<Player> player, int id, Level level){
        this.player = player;
        switch (id){
            case 1:
                this.color = "§a";
                this.teamname = "§a绿队";
                this.fillblock = Block.get(241,5);
                break;
            case 2:
                this.color = "§b";
                this.teamname = "§b蓝队";
                this.fillblock = Block.get(241,3);
                break;
            case 3:
                this.color = "§c";
                this.teamname = "§c红队";
                this.fillblock = Block.get(241,14);
                break;
            case 4:
                this.color = "§d";
                this.teamname = "§d紫队";
                this.fillblock = Block.get(241,10);
                break;
            case 5:
                this.color = "§e";
                this.teamname = "§e黄队";
                this.fillblock = Block.get(241,4);
                break;
            case 6:
                this.color = "§6";
                this.teamname = "§6橙队";
                this.fillblock = Block.get(241,1);
                break;
            case 7:
                this.color = "§7";
                this.teamname = "§7灰队";
                this.fillblock = Block.get(241,7);
                break;
            case 8:
                this.color = "§f";
                this.teamname = "§f白队";
                this.fillblock = Block.get(241,0);
                break;
        }
        Random r = new Random();
        this.spawnpos = new Position((r.nextInt(2001) - 1000), 150D,(r.nextInt(2001) - 1000),level);
        this.spawnpos.level.loadChunk(this.spawnpos.getChunkX(),this.spawnpos.getChunkZ(),true);
        for (int i = -1;i <= 1;i++){
            for (int j = -1;j <= 1;j++){
                this.spawnpos.level.setBlock(new Vector3(this.spawnpos.getFloorX() + i,this.spawnpos.getFloorY(),this.spawnpos.getFloorZ() + j),Block.get(0));
                this.spawnpos.level.setBlock(new Vector3(this.spawnpos.getFloorX() + i,this.spawnpos.getFloorY() + 1,this.spawnpos.getFloorZ() + j),Block.get(0));
            }
        }
        for (int i = -1;i <= 1;i++){
            for (int j = -1;j <= 1;j++){
                this.spawnpos.level.setBlock(new Vector3(this.spawnpos.getFloorX() + i,this.spawnpos.getFloorY() - 1,this.spawnpos.getFloorZ() + j),this.fillblock);
            }
        }
    }

    public void cleanBlock(){
        for (int i = -1;i <= 1;i++){
            for (int j = -1;j <= 1;j++){
                this.spawnpos.level.setBlock(new Vector3(this.spawnpos.getFloorX() + i,this.spawnpos.getFloorY() - 1,this.spawnpos.getFloorZ() + j),Block.get(BlockID.AIR));
            }
        }
    }
}
