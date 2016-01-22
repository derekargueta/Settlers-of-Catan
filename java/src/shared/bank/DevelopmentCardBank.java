package shared.bank;

import shared.devcards.DevelopmentCard;
import shared.model.Game;
import shared.model.Player;

import java.util.ArrayList;


/**
 * A bank owned by either a Player or a Game which holds all the owners DevelopmentCards
 *
 * Created by Danny on 1/18/16.
 */
public class DevelopmentCardBank {
    static final int SOLDIER_CARDS = 14;
    static final int MONUMENT_CARDS = 5;
    static final int MONOPOLY_CARDS = 2;
    static final int YEAR_OF_PLENTY_CARDS = 2;
    static final int ROAD_BUILD_CARDS = 2;

    private int numberSoldierCards;

    private ArrayList<DevelopmentCard> developmentCards;

    /**
     * Creates a full DevelopmentCardBank
     * @param game The object that contains the DevelopmentCardBank
     */
    DevelopmentCardBank(Game game) {

    }

    /**
     * Creates an empty DevelopmentCardBank
     * @param player The object that contains the DevelopmentCardBank
     */
    DevelopmentCardBank(Player player) {

    }

}
