package shared.dto;

import com.google.gson.JsonObject;
import shared.model.JsonSerializable;

/**
 * @author Derek Argueta
 */
public final class RollNumberDTO implements IDTO,JsonSerializable {

    private int playerIndex;
    private int numberRolled;

    public RollNumberDTO(int playerIndex, int numberRolled) {
        assert playerIndex >= 0;
        assert numberRolled > 0;

        this.playerIndex = playerIndex;
        this.numberRolled = numberRolled;
    }

    public int getValue(){
        return this.numberRolled;
    }

    public int getPlayerIndex(){
        return this.playerIndex;
    }

    /**
     * Converts the object to JSON
     *
     * @return The JSON representation of the object
     */
    @Override
    public JsonObject toJSON() {
        JsonObject obj = new JsonObject();
        obj.addProperty("type", "rollNumber");
        obj.addProperty("playerIndex", this.playerIndex);
        obj.addProperty("number", this.numberRolled);
        return obj;
    }
}
