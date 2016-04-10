package server.handlers.moves;

import server.commands.CommandExecutionResult;
import server.controllers.MovesController;
import server.persistence.provider.IPersistenceProvider;
import server.persistence.provider.PersistenceProvider;
import shared.dto.CookieWrapperDTO;
import shared.dto.RoadBuildingDTO;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * @author Derek Argueta
 * {@link} http://sparkjava.com/documentation.html#routes
 */
public final class RoadBuildingHandler implements Route {
    private final IPersistenceProvider persistence = PersistenceProvider.getInstance();

    @Override
    public Object handle(final Request request, final Response response) throws Exception {
        // TODO - validation

        final CookieWrapperDTO dto = new CookieWrapperDTO(new RoadBuildingDTO(request.body()));
        dto.extractCookieInfo(request.cookies());

        final CommandExecutionResult result = MovesController.roadBuilding(dto);
        if(result.errorOccurred()) {
            response.status(result.getStatus());
        } else {
            response.status(200);

            //Save the command to the db
            persistence.getCommandDAO();//.storeCommand(dto);
        }

        return result.getBody();
    }
}
