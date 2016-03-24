package server.commands.moves;

import server.commands.CommandExecutionResult;
import server.commands.ICommand;
import server.exceptions.AcceptTradeException;
import server.exceptions.CommandExecutionFailedException;
import server.main.Config;
import shared.dto.CookieWrapperDTO;
import shared.dto.IDTO;
import shared.dto.TradeOfferResponseDTO;

/**
 * A command object that accepts a trade
 *
 * @author Joel Bradley
 */
public final class AcceptTradeCommand implements ICommand {

    private int gameId = -1;
    private int playerIndex = -1;
    private boolean willAccept;

    /**
     * Communicates with the ServerFacade to carry out the Accept Trade command
     * @return IDTO
     */
    @Override
    public CommandExecutionResult execute() throws CommandExecutionFailedException {
        assert this.gameId >= 0;
        assert this.playerIndex >= 0;
        assert this.playerIndex < 4;

        try {
            return Config.facade.acceptTrade(this.gameId, this.playerIndex, this.willAccept);
        } catch(AcceptTradeException e) {
            throw new CommandExecutionFailedException(e.getMessage());
        }
    }

    @Override
    public void setParams(final IDTO dto) {
        final CookieWrapperDTO cookieDTO = (CookieWrapperDTO)dto;
        final TradeOfferResponseDTO tmpDTO = (TradeOfferResponseDTO)cookieDTO.getDto();
        this.gameId = cookieDTO.getGameId();
        this.playerIndex = tmpDTO.getPlayerIndex();
        this.willAccept = tmpDTO.willAccept();
    }

}