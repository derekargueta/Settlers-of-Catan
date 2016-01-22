package shared.model.map;
import shared.definitions.HexType;
import shared.locations.HexLocation;

/**
 *
 * Created by Corbin on 1/16/2016.
 */
public class Hex {

    private HexLocation loc;
    private HexType type;
    private int chit;

    public Hex(HexLocation l, HexType t, int c){
        loc = l;
        type = t;
        chit = c;
    }

    public void setType(HexType t)
    {
        type = t;
    }

    public void setChit(int c)
    {
        chit = c;
    }

    public HexLocation getLocation()
    {
        return loc;
    }

    public HexType getType()
    {
        return type;
    }

    public int getChit()
    {
        return chit;
    }
}