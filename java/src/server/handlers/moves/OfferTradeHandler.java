package server.handlers.moves;

import server.commands.CommandExecutionResult;
import server.controllers.MovesController;
import shared.dto.CookieWrapperDTO;
import shared.dto.OfferTradeDTO;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Handles HTTP requests for offering a trade to another player
 *
 * @author Derek Argueta
 * {@link} http://sparkjava.com/documentation.html#routes
 */
public class OfferTradeHandler implements Route {
//    private final IPersistenceProvider persistence = PersistenceProvider.getInstance();

    @Override
    public Object handle(Request request, Response response) throws Exception {
        // TODO - validation

        CookieWrapperDTO dto = new CookieWrapperDTO(new OfferTradeDTO(request.body()));
        dto.extractCookieInfo(request.cookies());

        CommandExecutionResult result = MovesController.offerTrade(dto);
        if(result.errorOccurred()) {
            response.status(result.getStatus());
        } else {
            response.status(200);

            //Save the command to the db
//            persistence.getCommandDAO();//.storeCommand(dto);
        }

        return result.getBody();
    }
}
