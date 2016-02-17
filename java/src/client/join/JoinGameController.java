package client.join;

import client.services.ServerProxy;
import client.services.UserCookie;
import shared.definitions.CatanColor;
import client.base.*;
import client.data.*;
import client.misc.*;
import shared.dto.CreateGameDTO;
import shared.dto.JoinGameDTO;


/**
 * Implementation for the join game controller
 */
public class JoinGameController extends Controller implements IJoinGameController {

	private INewGameView newGameView;
	private ISelectColorView selectColorView;
	private IMessageView messageView;
	private IAction joinAction;
	
	/**
	 * JoinGameController constructor
	 * 
	 * @param view Join game view
	 * @param newGameView New game view
	 * @param selectColorView Select color view
	 * @param messageView Message view (used to display error messages that occur while the user is joining a game)
	 */
	public JoinGameController(IJoinGameView view, INewGameView newGameView, 
								ISelectColorView selectColorView, IMessageView messageView) {

		super(view);

		setNewGameView(newGameView);
		setSelectColorView(selectColorView);
		setMessageView(messageView);
	}
	
	public IJoinGameView getJoinGameView() {
		return (IJoinGameView)super.getView();
	}
	
	/**
	 * Returns the action to be executed when the user joins a game
	 * 
	 * @return The action to be executed when the user joins a game
	 */
	public IAction getJoinAction() {
		return joinAction;
	}

	/**
	 * Sets the action to be executed when the user joins a game
	 * 
	 * @param value The action to be executed when the user joins a game
	 */
	public void setJoinAction(IAction value) {
		joinAction = value;
	}
	
	public INewGameView getNewGameView() {
		return newGameView;
	}

	public void setNewGameView(INewGameView newGameView) {
		this.newGameView = newGameView;
	}
	
	public ISelectColorView getSelectColorView() {
		return selectColorView;
	}

	public void setSelectColorView(ISelectColorView selectColorView) {
		this.selectColorView = selectColorView;
	}
	
	public IMessageView getMessageView() {
		return messageView;
	}

	public void setMessageView(IMessageView messageView) {
		this.messageView = messageView;
	}

	@Override
	public void start() {
		getJoinGameView().showModal();
	}

	@Override
	public void startCreateNewGame() {
		getNewGameView().showModal();
	}

	@Override
	public void cancelCreateNewGame() {
		getNewGameView().closeModal();
	}

	@Override
	public void createNewGame() {
        final boolean randomHexes = getNewGameView().getRandomlyPlaceHexes();
        final boolean randomNumbers = getNewGameView().getRandomlyPlaceNumbers();
        final boolean randomPorts = getNewGameView().getUseRandomPorts();
        final String gameName = getNewGameView().getTitle();
        final CreateGameDTO dto = new CreateGameDTO(randomHexes, randomNumbers, randomPorts, gameName);
        final GameInfo newGame = ServerProxy.getInstance().createNewGame(dto);
		getNewGameView().closeModal();

        // TODO - verify this is the required functionality
        this.startJoinGame(newGame);
	}

	@Override
	public void startJoinGame(GameInfo game) {
		getSelectColorView().showModal();
	}

	@Override
	public void cancelJoinGame() {
		getJoinGameView().closeModal();
	}

	@Override
	public void joinGame(CatanColor color) {
        final JoinGameDTO dto = new JoinGameDTO(UserCookie.getInstance().getPlayerId(), color);
        ServerProxy.getInstance().joinGame(dto);

        // TODO - create/update game instance

		// If join succeeded
		getSelectColorView().closeModal();
		getJoinGameView().closeModal();
		joinAction.execute();
	}
}
