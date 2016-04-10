package server.persistence;

import server.main.Config;
import server.managers.UserManager;
import server.persistence.dto.CommandDTO;
import server.persistence.dto.GameDTO;
import server.persistence.dto.UserDTO;
import shared.model.game.Game;

import java.util.HashMap;

/**
 * A wrapper around the plugin database to add any additional tracking and
 * analytics that the main program may need to know, such as keeping track of
 * how many commands have been committed and thus when to flush the store.
 *
 * @author Derek Argueta
 */
public class PersistenceCoordinator {

    private java.util.Map<Integer, Integer> commandCommitCount;
    private static PersistenceCoordinator instance;
    private IDatabase database;

    private PersistenceCoordinator() {
        this.commandCommitCount = new HashMap<>();
        this.database = null;
    }

    public static PersistenceCoordinator getInstance() {
        if(instance == null) {
            instance = new PersistenceCoordinator();
        }
        return instance;
    }

    public static void setDatabase(IDatabase db) {
        getInstance().database = db;
    }

    public static IDatabase getDatabase() {
        return getInstance().database;
    }

    public static void addUser(UserDTO dto) {
        int oldId = dto.getId();
        int newId = getInstance().database.addUser(dto);
        if(oldId != newId) {
            UserManager.getInstance().setNewIdForUser(oldId, newId);
        }
    }

    public static void addCommand(CommandDTO dto) {
        getInstance().database.addCommand(dto);
        int commitCount = getInstance().commandCommitCount.get(dto.getGameID());
        commitCount++;
        getInstance().commandCommitCount.put(dto.getGameID(), commitCount);
        if (commitCount % Config.commandCount == 0) {
            getInstance().commandCommitCount.put(dto.getGameID(), 0);
            Game game = Config.facade.getGameByID(dto.getGameID());
            GameDTO gameDTO = new GameDTO(dto.getGameID(), game.getTitle(), game.toJSON().toString());
            getInstance().database.updateGame(gameDTO);
            getInstance().database.deleteCommandsFromGame(dto.getGameID());
        }
    }

    public static void addGame(GameDTO dto) {
        getInstance().commandCommitCount.put(dto.getGameID(), 0);
        getInstance().database.addGame(dto);
    }
}