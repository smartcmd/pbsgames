package top.mcpbs.games.room;

import cn.nukkit.Player;
import cn.nukkit.level.Level;
import top.mcpbs.games.waitroom.WaitRoom;

import java.util.ArrayList;
import java.util.HashMap;


public abstract class Room {
    public static HashMap<Player, Room> awaiting = new HashMap<>();
    public static HashMap<Player, Room> aplaying = new HashMap<>();
    public ArrayList<Player> waiting = new ArrayList<>();
    public ArrayList<Player> playing = new ArrayList<>();
    public abstract void gameStart();
    public abstract void gameEnd();
    public abstract void joinRoom(Player player);
    public abstract void playerAccidentQuit(Player player);
    public abstract boolean canJoin();
    public boolean isPlaying = false;
    public int roomId;
    public boolean isStartChemical = false;
    public Level roomlevel;
    public String mapname;
    public boolean isend = false;
    public WaitRoom waitRoom;
}
