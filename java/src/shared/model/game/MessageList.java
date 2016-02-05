package shared.model.game;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by boscho on 2/3/16.
 */
public class MessageList {

    private List<MessageLine> chat = new ArrayList<MessageLine>();

    public MessageList(){}

    public void addMessage(MessageLine m){
        chat.add(m);
    }

    public void loadJSON(JsonObject js){
       // HashMap<String,Object> result = new ObjectMapper().readValue(js, HashMap.class);
        chat = new ArrayList<MessageLine>();

        Gson gs = new Gson();
        makeMessageLog(gs.fromJson(js.getAsJsonArray("lines"), JsonArray.class));
    }

    public void makeMessageLog(JsonArray jarray){
        Gson gs = new Gson();
        for (JsonElement je: jarray){
            JsonObject json = je.getAsJsonObject();
            MessageLine ml = new MessageLine(gs.fromJson(json.get("line"), JsonObject.class));
        }
    }
}
