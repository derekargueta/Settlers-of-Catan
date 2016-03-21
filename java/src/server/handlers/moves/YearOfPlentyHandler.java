package server.handlers.moves;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import server.commands.CommandExecutionResult;
import server.controllers.MovesController;
import shared.dto.CookieWrapperDTO;
import shared.dto.PlayYOPCardDTO;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Handles HTTP requests for playing the Year of Plenty card
 *
 * @author Derek Argueta
 * {@link} http://sparkjava.com/documentation.html#routes
 */
public class YearOfPlentyHandler implements Route {

    @Override
    public Object handle(Request request, Response response) throws Exception {
        if(!PlayYOPCardDTO.isValidRequestJson(request.body())) {
            response.status(400);
            return "Invalid request.";
        }

        // TODO - validation

        CookieWrapperDTO dto = new CookieWrapperDTO(new PlayYOPCardDTO(request.body()));
        dto.extractCookieInfo(request.cookies());

        CommandExecutionResult result = MovesController.yearOfPlenty(dto);
        if(result.errorOccurred()) {
            response.status(result.getStatus());
        } else {
            response.status(200);
        }

        return result.getBody();
    }
}
