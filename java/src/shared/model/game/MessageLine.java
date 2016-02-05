package shared.model.game;

import com.google.gson.JsonObject;

/**
 * Created by boscho on 2/3/16.
 */
public class MessageLine {

    private String source;
    private String content;

    public MessageLine(String person, String c){
        source = person;
        content = c;
    }

    public MessageLine(JsonObject jo){
        content = jo.get("message").getAsString();
        source = jo.get("source").getAsString();
    }

    public String getPlayer(){
        return source;
    }

    public String getMessage(){
        return content;
    }
}