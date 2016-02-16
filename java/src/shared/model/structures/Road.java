package shared.model.structures;

/**
 * A Road is built on an edge location
 *
 * @author Joel Bradley
 */
public final class Road {

    private int playerID;
    private boolean visited;

    public Road(int playerID) {
        assert playerID >= 0;

        this.playerID = playerID;
        visited = false;
    }

    public int getPlayerID() {
        return playerID;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}