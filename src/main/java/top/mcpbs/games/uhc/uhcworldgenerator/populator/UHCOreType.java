package top.mcpbs.games.uhc.uhcworldgenerator.populator;

public class UHCOreType{
    public int amount;
    public int blockid;
    public int maxh;
    public int minh;

    public UHCOreType(int blockid, int amount,int maxh,int minh){
        this.amount = amount;
        this.blockid = blockid;
        this.maxh = maxh;
        this.minh = minh;
    }
}
