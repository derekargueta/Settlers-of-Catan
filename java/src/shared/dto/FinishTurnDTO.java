package shared.dto;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import shared.model.JsonSerializable;

/**
 * @author Derek Argueta
 */
public final class FinishTurnDTO implements IDTO,JsonSerializable {

    // -- JSON keys
    private static final String kType = "type";
    private static final String kPlayerIndex = "playerIndex";

    // -- class members
    private int playerIndex;

    /**
     *
     * @param playerIndex Who's sending this command (0-3)
     */
    public FinishTurnDTO(final int playerIndex) {
        assert playerIndex >= 0;
        assert playerIndex <= 3;

        this.playerIndex = playerIndex;
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
        final JsonObject obj = new JsonObject();
        obj.addProperty(kType, "finishTurn");
        obj.addProperty(kPlayerIndex, this.playerIndex);
        return obj;
    }

    public static boolean isValidRequestJson(String json) {
        final JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
        final boolean hasType = obj.has(kType) && obj.get(kType).isJsonPrimitive();
        final boolean hasPlayerIndex = obj.has(kPlayerIndex) && obj.get(kPlayerIndex).isJsonPrimitive();

        return hasType && hasPlayerIndex;
    }
}
