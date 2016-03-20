package server.facade;

import client.data.GameInfo;
import server.commands.CommandExecutionResult;
import server.exceptions.*;
import server.managers.GameManager;
import server.managers.UserManager;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.dto.*;
import shared.exceptions.*;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.ai.AIType;
import shared.model.bank.InvalidTypeException;
import shared.model.game.Game;
import shared.model.game.MessageLine;
import shared.model.game.trade.Trade;
import shared.model.player.Player;

import javax.naming.InsufficientResourcesException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kyle Cornelison
 */
public class ServerFacade implements IFacade {
    private static IFacade _instance;
    private GameManager gameManager;
    private UserManager userManager;

    /**
     * Default Constructor - Private
     */
    private ServerFacade(){
        gameManager = GameManager.getInstance();
        userManager = UserManager.getInstance();
    }

    /**
     * Singleton - get instance method
     * @return
     */
    public static IFacade getInstance(){
        if(_instance == null) {
            _instance = new ServerFacade();
        }
        return _instance;
    }

    /**
     * Logs a player into the server
     *
     * @param username
     * @param password
     * @throws LoginException
     * @return CommandExecutionResult
     */
    @Override
    public boolean login(final String username, final String password) throws LoginException {
        assert username != null;
        assert username.length() > 0;
        assert password != null;
        assert password.length() > 0;

        return UserManager.getInstance().authenticateUser(username, password);
    }

    /**
     * Registers a user
     *
     * @param username
     * @param password
     * @throws RegisterException
     * @return CommandExecutionResult
     */
    @Override
    public CommandExecutionResult register(final String username, final String password) throws RegisterException {
        assert username != null;
        assert username.length() > 0;
        assert password != null;
        assert password.length() > 0;

        //TODO: make this method work fool, i mean if you want to, you don't have to
        return null;
    }

    /**
     * Adds an AI to the game
     *
     * @param type
     * @throws AddAIException
     * @return CommandExecutionResult
     */
    @Override
    public CommandExecutionResult addAI(final int gameId, final AIType type) throws AddAIException {
        assert gameId > 0;
        assert gameId < this.gameManager.getNumGames();
        assert type != null;

        final Game game = gameManager.getGameByID(gameId);

        if(game.canAddAI()) {
            game.addAI(type);
            return new CommandExecutionResult(game.getDTO().toJSON().getAsString());
        } else {
            throw new AddAIException("AI player can't be added!");
        }
    }

    /**
     * List the available AI types
     *
     * @throws ListAIException
     * @return CommandExecutionResult
     */
    @Override
    public CommandExecutionResult listAI(final int gameId) throws ListAIException {
        assert gameId >= 0;
        assert gameId < this.gameManager.getNumGames();

        //TODO: get this to work fool
        //return new ListAIDTO(AIFactory.listAITypes());

        return null;
    }

    /**
     * List the current games
     *
     * @return CommandExecutionResult
     * @throws ListException
     */
    @Override
    public CommandExecutionResult list() {
        assert this.gameManager != null;

        final List<GameInfo> games = this.gameManager.getGamesInfos();
        final ListGamesDTO dto = new ListGamesDTO(games);
        final String jsonString = dto.toJSONArr().toString();
        return new CommandExecutionResult(jsonString);
    }

    /**
     * Creates a new game
     *
     * @param name
     * @param randomTiles
     * @param randomNumbers
     * @param randomPorts
     * @return CommandExecutionResult
     * @throws CreateGameException
     */
    @Override
    public CommandExecutionResult create(final String name, final boolean randomTiles, final boolean randomNumbers, boolean randomPorts) {
        assert name != null;
        assert name.length() > 0;
        assert this.gameManager != null;

        // create the game in the model
        final Game game = new Game(name, randomPorts, randomNumbers, randomTiles);
        game.setId(this.gameManager.getNumGames());
        this.gameManager.addGame(game);

        // construct an info object to send back
        final GameInfo gameInfo = game.getAsGameInfo();
        gameInfo.setPlayers(new ArrayList<>());

        // construct the response
        final GameInfoDTO dto = new GameInfoDTO(gameInfo);
        final String jsonString = dto.toJSON().toString();
        final CommandExecutionResult result = new CommandExecutionResult(jsonString);
        result.addCookie("catan.game", Integer.toString(game.getId()));

        return result;
    }

    /**
     * Joins a player to the specified game
     *
     * @param gameID
     * @param color
     * @throws JoinGameException
     * @return CommandExecutionResult
     */
    @Override
    public CommandExecutionResult join(final int gameID, final CatanColor color) throws JoinGameException {
        assert gameID >= 0;
        assert gameID < this.gameManager.getNumGames();
        assert color != null;

        // get the game
        final Game game = this.gameManager.getGameByID(gameID);

        // TODO
        if(game.canAddPlayer()) {
//            Player newPlayer = new Player(0, color, );
//            game.getPlayerManager().addPlayer();
        }

        //TODO: make this method work fool
        return null;
    }

    /**
     * Sends a chat message
     *
     * @param player  index of the player
     * @param message
     * @throws SendChatException
     * @return CommandExecutionResult
     */
    @Override
    public CommandExecutionResult sendChat(final int gameID, final int player, final String message) throws SendChatException {
        assert gameID >= 0;
        assert gameID < this.gameManager.getNumGames();
        assert player >= 0;
        assert player < 4;
        assert message != null;
        assert message.length() >= 0;

        final Game game = gameManager.getGameByID(gameID);
        try {
            final String playerName = game.getPlayerNameByIndex(player);
            final MessageLine line = new MessageLine(playerName, message);
            game.getChat().addMessage(line);
        } catch (PlayerExistsException e) {
            e.printStackTrace();
            throw new SendChatException("Failed to send the chat message!");
        }

        return new CommandExecutionResult(game.getDTO().toJSON().getAsString());
    }

    /**
     * Rolls the specified value
     *
     * @param player index of the player
     * @param value
     * @throws RollNumberException
     * @return CommandExecutionResult
     */
    @Override
    public CommandExecutionResult rollNumber(final int gameID, final int player, final int value) throws RollNumberException {
        assert gameID >= 0;
        assert gameID < this.gameManager.getNumGames();
        assert player >= 0;
        assert player < 4;

        final Game game = gameManager.getGameByID(gameID);
        try {
            game.rollNumber(value);
        } catch (Exception e) {
            throw new RollNumberException("Error while rolling!");
        }

        return new CommandExecutionResult(game.getDTO().toJSON().getAsString());
    }

    /**
     * Robs the specified player
     *
     * @param player      index of the player robbing
     * @param newLocation
     * @param victim      index of the player being robbed
     * @return CommandExecutionResult
     * @throws RobPlayerException
     */
    @Override
    public CommandExecutionResult robPlayer(final int gameID, final int player, final HexLocation newLocation, final int victim) throws RobPlayerException {
        assert gameID >= 0;
        assert gameID < this.gameManager.getNumGames();
        assert player >= 0;
        assert newLocation != null;
        assert victim >= 0;

        Game game = gameManager.getGameByID(gameID);
        try {
            if(game.canPlaceRobber(player, newLocation)) {
                game.rob(player, victim, newLocation);
            }
        } catch (InvalidTypeException | InsufficientResourcesException | MoveRobberException | AlreadyRobbedException | PlayerExistsException | InvalidLocationException e) {
            throw new RobPlayerException(e.getMessage());
        }

        return new CommandExecutionResult(game.getDTO().toJSON().getAsString());
    }

    /**
     * Ends the current player's turn making it the next player's turn
     *
     * @param player index of the player
     * @throws FinishTurnException
     * @return CommandExecutionResult
     */
    @Override
    public CommandExecutionResult finishTurn(final int gameID, final int player) throws FinishTurnException {
        assert gameID >= 0;
        assert gameID < this.gameManager.getNumGames();
        assert player >= 0;
        assert player < 4;

        final Game game = gameManager.getGameByID(gameID);
        if(game.canFinishTurn(player)) {
            try {
                game.finishTurn(player);
            } catch (Exception e) {
                throw new FinishTurnException("Failed to end the player's turn!");
            }
        } else {
            throw new FinishTurnException("Player can't end their turn yet!");
        }

        return new CommandExecutionResult(game.getDTO().toJSON().getAsString());
    }

    /**
     * Buys a new dev card
     *
     * @param playerIndex index of the player
     * @throws BuyDevCardException
     * @return CommandExecutionResult
     */
    @Override
    public CommandExecutionResult buyDevCard(final int gameID, final int playerIndex) throws BuyDevCardException {
        assert gameID >= 0;
        assert gameID < this.gameManager.getNumGames();
        assert playerIndex >= 0;
        assert playerIndex < 4;

        final Game game = gameManager.getGameByID(gameID);
        try {
            game.buyDevelopmentCard(playerIndex);
        } catch (Exception e) {
            throw new BuyDevCardException("Something went wrong while trying to buy a dev card");
        }
        return new CommandExecutionResult(game.getDTO().toJSON().getAsString());
    }

    /**
     * Handles playing Year of Plenty
     *
     * @param playerIndex      index of the player
     * @param resourceOne first resource to receive
     * @param resourceTwo second resource to receive
     * @throws YearOfPlentyException
     * @return CommandExecutionResult
     */
    @Override
    public CommandExecutionResult yearOfPlenty(final int gameID, final int playerIndex, final ResourceType resourceOne, final ResourceType resourceTwo) throws YearOfPlentyException {
        assert gameID >= 0;
        assert gameID < this.gameManager.getNumGames();
        assert playerIndex >= 0;
        assert playerIndex < 4;
        assert resourceOne != null;
        assert resourceTwo != null;

        final Game game = gameManager.getGameByID(gameID);
        try {
            game.useYearOfPlenty(playerIndex, resourceOne, resourceTwo);
        } catch (PlayerExistsException | DevCardException | InsufficientResourcesException | InvalidTypeException e) {
            throw new YearOfPlentyException("yearOfPlenty failed in the model on the server.");
        }

        return new CommandExecutionResult(game.getDTO().toJSON().getAsString());
    }

    /**
     * Handles playing Road Building
     *
     * @param player      index of the player
     * @param locationOne location for the first road
     * @param locationTwo location for the second road
     * @return CommandExecutionResult
     * @throws RoadBuildingException
     */
    @Override
    public CommandExecutionResult roadBuilding(final int gameID, final int player, final EdgeLocation locationOne, EdgeLocation locationTwo) throws RoadBuildingException {
        assert gameID >= 0;
        assert gameID < this.gameManager.getNumGames();
        assert player >= 0;
        assert locationOne != null;
        assert locationTwo != null;

        final Game game = gameManager.getGameByID(gameID);
        try {
            if(game.canUseRoadBuilding(player)) {
                game.useRoadBuilder(player, locationOne, locationTwo);
            }
        } catch (InvalidPlayerException | InvalidLocationException | PlayerExistsException | StructureException | DevCardException e) {
            throw new RoadBuildingException(e.getMessage());
        }

        return new CommandExecutionResult(game.getDTO().toJSON().getAsString());
    }

    /**
     * Handles playing Soldier
     *
     * @param player index of the player
     * @param newLocation
     * @param victim index of the player being robbed
     * @return CommandExecutionResult
     * @throws SoldierException
     */
    @Override
    public CommandExecutionResult soldier(final int gameID, final int player, final HexLocation newLocation, final int victim) throws SoldierException {
        assert gameID >= 0;
        assert gameID < this.gameManager.getNumGames();
        assert player >= 0;
        assert newLocation != null;
        assert victim >= 0;
        assert victim < 4;

        Game game = gameManager.getGameByID(gameID);
        try{
            if(game.canUseSoldier(player)) {
                game.useSoldier(player, victim, newLocation);
            }
        } catch(MoveRobberException | InvalidTypeException | InsufficientResourcesException | DevCardException | PlayerExistsException | AlreadyRobbedException | InvalidLocationException e) {
            throw new SoldierException(e.getMessage());
        }

        return new CommandExecutionResult(game.getDTO().toJSON().getAsString());
    }

    /**
     * Handles playing Monopoly
     *
     * @param playerIndex   index of the player
     * @param resource resource to take
     * @throws MonopolyException
     * @return CommandExecutionResult
     */
    @Override
    public CommandExecutionResult monopoly(final int gameID, final int playerIndex, final ResourceType resource) throws MonopolyException {
        assert gameID >= 0;
        assert gameID < this.gameManager.getNumGames();
        assert playerIndex >= 0;
        assert playerIndex < 4;
        assert resource != null;

        final Game game = gameManager.getGameByID(gameID);
        try {
            game.useMonopoly(playerIndex, resource);
        } catch (PlayerExistsException | DevCardException | InvalidTypeException | InsufficientResourcesException e) {
            throw new MonopolyException(e.getMessage());
        }

        return new CommandExecutionResult(game.getDTO().toJSON().getAsString());
    }

    /**
     * Handles playing Monument
     *
     * @param playerIndex index of the player
     * @throws MonumentException
     * @return CommandExecutionResult
     */
    @Override
    public CommandExecutionResult monument(final int gameID, final int playerIndex) throws MonumentException {
        assert gameID >= 0;
        assert gameID < this.gameManager.getNumGames();
        assert playerIndex >= 0;
        assert playerIndex < 4;

        try {
            gameManager.getGameByID(gameID).useMonument(playerIndex);
        } catch (PlayerExistsException | DevCardException e) {
            e.printStackTrace();
            throw new MonumentException(e.getMessage());
        }

        return new CommandExecutionResult(gameManager.getGameByID(gameID).getDTO().toJSON().getAsString());
    }

    /**
     * Builds a road
     *
     * @param player   index of the player
     * @param location
     * @return CommandExecutionResult
     * @throws BuildRoadException
     */
    @Override
    public CommandExecutionResult buildRoad(final int gameID, final int player, final EdgeLocation location) throws BuildRoadException {
        assert gameID >= 0;
        assert gameID < this.gameManager.getNumGames();
        assert player >= 0;
        assert player < 4;
        assert location != null;

        final Game game = gameManager.getGameByID(gameID);
        try {
            if(game.canInitiateRoad(player, location)) {
                game.initiateRoad(player, location);
            } else if(game.canBuildRoad(player, location)) {
                game.buildRoad(player, location);
            }
        } catch (InvalidPlayerException | InvalidLocationException | PlayerExistsException | StructureException e) {
            throw new BuildRoadException(e.getMessage());
        }

        return new CommandExecutionResult(game.getDTO().toJSON().getAsString());
    }

    /**
     * Builds a settlement
     *
     * @param player index of the player
     * @param location
     * @return CommandExecutionResult
     * @throws BuildSettlementException
     */
    @Override
    public CommandExecutionResult buildSettlement(final int gameID, final int player, final VertexLocation location) throws BuildSettlementException {
        assert gameID >= 0;
        assert gameID < this.gameManager.getNumGames();
        assert player >= 0;
        assert player < 4;
        assert location != null;

        final Game game = gameManager.getGameByID(gameID);
        try {
            if(game.canInitiateSettlement(player, location)) {
                game.initiateSettlement(player, location);
            } else if(game.canBuildSettlement(player, location)) {
                game.buildSettlement(player, location);
            }
        } catch (InvalidPlayerException | InvalidLocationException | PlayerExistsException | StructureException e) {
            throw new BuildSettlementException(e.getMessage());
        }

        return new CommandExecutionResult(game.getDTO().toJSON().getAsString());
    }

    /**
     * Builds a city
     *
     * @param gameID id of the game
     * @param player index of the player
     * @param location
     * @return CommandExecutionResult
     * @throws BuildCityException
     */
    @Override
    public CommandExecutionResult buildCity(final int gameID, final int player, final VertexLocation location) throws BuildCityException {
        assert gameID >= 0;
        assert gameID < this.gameManager.getNumGames();
        assert player >= 0;
        assert player < 4;
        assert location != null;

        final Game game = gameManager.getGameByID(gameID);
        try {
            if(game.canBuildCity(player, location)) {
                game.buildCity(player, location);
            }
        } catch (InvalidPlayerException | InvalidLocationException | PlayerExistsException | StructureException e) {
            throw new BuildCityException(e.getMessage());
        }

        return new CommandExecutionResult(game.getDTO().toJSON().getAsString());
    }

    /**
     * Offers a trade to the specified player
     *
     * @throws OfferTradeException
     * @return CommandExecutionResult
     */
    @Override
    public CommandExecutionResult offerTrade(final int gameID, final OfferTradeDTO dto) throws OfferTradeException {
        assert gameID >= 0;
        assert gameID < this.gameManager.getNumGames();
        assert dto != null;

        final int sender = dto.getSender();
        final int receiver = dto.getReceiver();
        final Trade offer = dto.getOffer();
        final List<ResourceType> send = offer.getPackage1().getResources();
        final List<ResourceType> receive = offer.getPackage2().getResources();
        try {
            gameManager.getGameByID(gameID).offerTrade(sender, receiver, send, receive);
        } catch(InvalidTypeException | PlayerExistsException | InsufficientResourcesException e){
            throw new OfferTradeException(e.getMessage());
        }

        return new CommandExecutionResult(gameManager.getGameByID(gameID).getDTO().toJSON().getAsString());
    }

    /**
     * Accepts a trade offer
     *
     * @param player     index of the player accepting the trade
     * @param willAccept whether or not the player accepts
     * @throws AcceptTradeException
     * @return CommandExecutionResult
     */
    @Override
    public CommandExecutionResult acceptTrade(final int gameID, final int player, final boolean willAccept) throws AcceptTradeException {
        assert gameID >= 0;
        assert gameID < this.gameManager.getNumGames();
        assert player >= 0;
        assert player < 4;

        try {
            gameManager.getGameByID(gameID).acceptTrade(player,willAccept);
        } catch (PlayerExistsException | InsufficientResourcesException | InvalidTypeException e) {
            throw new AcceptTradeException(e.getMessage());
        }

        return new CommandExecutionResult(gameManager.getGameByID(gameID).getDTO().toJSON().getAsString());
    }

    /**
     * Performs a maritime trade (trade with the bank)
     *
     * @throws MaritimeTradeException
     * @return CommandExecutionResult
     */
    @Override
    public CommandExecutionResult maritimeTrade(final int gameID, final MaritimeTradeDTO dto) throws MaritimeTradeException {
        assert gameID >= 0;
        assert gameID < this.gameManager.getNumGames();
        assert dto != null;

        try {
            gameManager.getGameByID(gameID).maritimeTrade(dto.getPlayerIndex(), dto.getRatio(), ResourceType.translateFromString(dto.getInputResource()), ResourceType.translateFromString(dto.getOutputResource()));
        } catch(InvalidPlayerException | InsufficientResourcesException | InvalidTypeException | PlayerExistsException e){
            throw new MaritimeTradeException(e.getMessage());
        }

        return new CommandExecutionResult(gameManager.getGameByID(gameID).getDTO().toJSON().getAsString());
    }

    /**
     * Discards the specified cards from the player's hand
     *
     * @throws DiscardCardsException
     * @return CommandExecutionResult
     */
    @Override
    public CommandExecutionResult discardCards(final int gameID, final DiscardCardsDTO dto) throws DiscardCardsException {
        assert this.gameManager != null;
        assert gameID >= 0;
        assert gameID < this.gameManager.getNumGames();
        assert dto != null;

        final List<ResourceType> cards = new ArrayList<>();
        for(int i = 0; i < dto.getBrickCount(); i++) {
            cards.add(ResourceType.BRICK);
        }
        for(int i = 0; i < dto.getWoodCount(); i++) {
            cards.add(ResourceType.WOOD);
        }
        for(int i = 0; i < dto.getOreCount(); i++) {
            cards.add(ResourceType.ORE);
        }
        for(int i = 0; i < dto.getWheatCount(); i++) {
            cards.add(ResourceType.WHEAT);
        }
        for(int i = 0; i < dto.getSheepCount(); i++) {
            cards.add(ResourceType.SHEEP);
        }

        try {
            gameManager.getGameByID(gameID).discardCards(dto.getPlayerIndex(), cards);
        } catch(PlayerExistsException | InvalidTypeException | InsufficientResourcesException e) {
            throw new DiscardCardsException(e.getMessage());
        }

        final String jsonString = gameManager.getGameByID(gameID).getDTO().toJSON().getAsString();
        return new CommandExecutionResult(jsonString);
    }
}
