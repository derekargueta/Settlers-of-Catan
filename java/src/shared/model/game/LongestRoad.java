package shared.model.game;

/**
 * Representation of Longest Road Card
 */
public class LongestRoad {
    private int playerID;
    private static int pointWorth = 3;

    /**
     * Default Constructor
     */
    public LongestRoad(){
        this.playerID = -1;
    }

    /**
     * Get the current owner of the card
     * @return owner of the card by id
     */
    public int getOwner(){
        return this.playerID;
    }

    /**
     * Set the owner by player id
     * @param id id of the player owning this card (-1 for no owner)
     */
    public void setOwner(int id){
        this.playerID = id;
    }

    /**
     * Get the points this card is worth
     * @return Point Value
     */
    public int getPointWorth(){
        return pointWorth;
    }
}