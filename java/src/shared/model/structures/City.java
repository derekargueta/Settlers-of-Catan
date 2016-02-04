package shared.model.structures;

/**
 * A City is a second-level structure for players, and can only be built from a previously existing settlement.
 *
 * @author Joel Bradley
 */
public class City {

    private int playerID;

    public City(int playerID) {
        this.playerID = playerID;
    }

    public int getPlayerID() {
        return playerID;
    }
}