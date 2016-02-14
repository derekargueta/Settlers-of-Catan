package model.map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import shared.definitions.ResourceType;
import shared.exceptions.*;
import shared.locations.*;
import shared.model.map.Map;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests the Map class
 *
 * @author Joel Bradley
 */
public class MapTest {

    private Map map;
    private Map jsonMap;


    @Before
    public void setUp() {
        map = new Map(false, false, false);
        String json = "{" +
                "    \"hexes\": [" +
                "      {" +
                "        \"location\": {" +
                "          \"x\": 0," +
                "          \"y\": -2" +
                "        }" +
                "      }," +
                "      {" +
                "        \"resource\": \"brick\"," +
                "        \"location\": {" +
                "          \"x\": 1," +
                "          \"y\": -2" +
                "        }," +
                "        \"number\": 4" +
                "      }," +
                "      {" +
                "        \"resource\": \"wood\"," +
                "        \"location\": {" +
                "          \"x\": 2," +
                "          \"y\": -2" +
                "        }," +
                "        \"number\": 11" +
                "      }," +
                "      {" +
                "        \"resource\": \"brick\"," +
                "        \"location\": {" +
                "          \"x\": -1," +
                "          \"y\": -1" +
                "        }," +
                "        \"number\": 8" +
                "      }," +
                "      {" +
                "        \"resource\": \"wood\"," +
                "        \"location\": {" +
                "          \"x\": 0," +
                "          \"y\": -1" +
                "        }," +
                "        \"number\": 3" +
                "      }," +
                "      {" +
                "        \"resource\": \"ore\"," +
                "        \"location\": {" +
                "          \"x\": 1," +
                "          \"y\": -1" +
                "        }," +
                "        \"number\": 9" +
                "      }," +
                "      {" +
                "        \"resource\": \"sheep\"," +
                "        \"location\": {" +
                "          \"x\": 2," +
                "          \"y\": -1" +
                "        }," +
                "        \"number\": 12" +
                "      }," +
                "      {" +
                "        \"resource\": \"ore\"," +
                "        \"location\": {" +
                "          \"x\": -2," +
                "          \"y\": 0" +
                "        }," +
                "        \"number\": 5" +
                "      }," +
                "      {" +
                "        \"resource\": \"sheep\"," +
                "        \"location\": {" +
                "          \"x\": -1," +
                "          \"y\": 0" +
                "        }," +
                "        \"number\": 10" +
                "      }," +
                "      {" +
                "        \"resource\": \"wheat\"," +
                "        \"location\": {" +
                "          \"x\": 0," +
                "          \"y\": 0" +
                "        }," +
                "        \"number\": 11" +
                "      }," +
                "      {" +
                "        \"resource\": \"brick\"," +
                "        \"location\": {" +
                "          \"x\": 1," +
                "          \"y\": 0" +
                "        }," +
                "        \"number\": 5" +
                "      }," +
                "      {" +
                "        \"resource\": \"wheat\"," +
                "        \"location\": {" +
                "          \"x\": 2," +
                "          \"y\": 0" +
                "        }," +
                "        \"number\": 6" +
                "      }," +
                "      {" +
                "        \"resource\": \"wheat\"," +
                "        \"location\": {" +
                "          \"x\": -2," +
                "          \"y\": 1" +
                "        },\n" +
                "        \"number\": 2" +
                "      }," +
                "      {" +
                "        \"resource\": \"sheep\"," +
                "        \"location\": {" +
                "          \"x\": -1," +
                "          \"y\": 1" +
                "        }," +
                "        \"number\": 9" +
                "      }," +
                "      {" +
                "        \"resource\": \"wood\"," +
                "        \"location\": {" +
                "          \"x\": 0," +
                "          \"y\": 1" +
                "        }," +
                "        \"number\": 4" +
                "      }," +
                "      {" +
                "        \"resource\": \"sheep\"," +
                "        \"location\": {" +
                "          \"x\": 1," +
                "          \"y\": 1" +
                "        }," +
                "        \"number\": 10" +
                "      }," +
                "      {" +
                "        \"resource\": \"wood\"," +
                "        \"location\": {" +
                "          \"x\": -2," +
                "          \"y\": 2" +
                "        }," +
                "        \"number\": 6" +
                "      }," +
                "      {" +
                "        \"resource\": \"ore\"," +
                "        \"location\": {" +
                "          \"x\": -1," +
                "          \"y\": 2" +
                "        }," +
                "        \"number\": 3" +
                "      }," +
                "      {" +
                "        \"resource\": \"wheat\"," +
                "        \"location\": {" +
                "          \"x\": 0," +
                "          \"y\": 2" +
                "        }," +
                "        \"number\": 8" +
                "      }" +
                "    ]," +
                "    \"roads\": []," +
                "    \"cities\": []," +
                "    \"settlements\": []," +
                "    \"radius\": 3," +
                "    \"ports\": [" +
                "      {" +
                "        \"ratio\": 2," +
                "        \"resource\": \"brick\"," +
                "        \"direction\": \"NE\"," +
                "        \"location\": {" +
                "          \"x\": -2," +
                "          \"y\": 3" +
                "        }" +
                "      }," +
                "      {" +
                "        \"ratio\": 3," +
                "        \"direction\": \"SE\"," +
                "        \"location\": {" +
                "          \"x\": -3," +
                "          \"y\": 0" +
                "        }" +
                "      }," +
                "      {" +
                "        \"ratio\": 2," +
                "        \"resource\": \"sheep\"," +
                "        \"direction\": \"NW\"," +
                "        \"location\": {" +
                "          \"x\": 3," +
                "          \"y\": -1" +
                "        }" +
                "      }," +
                "      {" +
                "        \"ratio\": 3," +
                "        \"direction\": \"NW\"," +
                "        \"location\": {" +
                "          \"x\": 2," +
                "          \"y\": 1" +
                "        }" +
                "      }," +
                "      {" +
                "        \"ratio\": 3," +
                "        \"direction\": \"N\"," +
                "        \"location\": {" +
                "          \"x\": 0," +
                "          \"y\": 3" +
                "        }" +
                "      }," +
                "      {" +
                "        \"ratio\": 2," +
                "        \"resource\": \"ore\"," +
                "        \"direction\": \"S\"," +
                "        \"location\": {" +
                "          \"x\": 1," +
                "          \"y\": -3" +
                "        }" +
                "      }," +
                "      {" +
                "        \"ratio\": 3," +
                "        \"direction\": \"SW\"," +
                "        \"location\": {" +
                "          \"x\": 3," +
                "          \"y\": -3" +
                "        }" +
                "      }," +
                "      {" +
                "        \"ratio\": 2," +
                "        \"resource\": \"wheat\"," +
                "        \"direction\": \"S\"," +
                "        \"location\": {" +
                "          \"x\": -1," +
                "          \"y\": -2" +
                "        }" +
                "      }," +
                "      {" +
                "        \"ratio\": 2," +
                "        \"resource\": \"wood\"," +
                "        \"direction\": \"NE\"," +
                "        \"location\": {" +
                "          \"x\": -3," +
                "          \"y\": 2" +
                "        }" +
                "      }" +
                "    ]," +
                "    \"robber\": {" +
                "      \"x\": 0," +
                "      \"y\": -2" +
                "    }" +
                "  }";
        final JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        jsonMap = new Map(jsonObject);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testMoveRobber() {
        // TODO --
    }

    @Test
    public void testWhoCanGetRobbed() {
        // TODO --
    }

    @Test
    public void testGetPortTypes() {
        // TODO --
    }

    @Test
    public void testGetResources() throws Exception {
        final int playerOne = 1;
        final int playerTwo = 2;
        final HexLocation hexLocOneFirst = new HexLocation(0,0);
        final VertexLocation vertexLocOneFirst = new VertexLocation(hexLocOneFirst, VertexDirection.West);
        final EdgeLocation edgeLocOneFirst = new EdgeLocation(hexLocOneFirst, EdgeDirection.NorthWest);
        final HexLocation hexLocOneSecond = new HexLocation(0,1);
        final VertexLocation vertexLocOneSecond = new VertexLocation(hexLocOneSecond, VertexDirection.SouthEast);
        final EdgeLocation edgeLocOneSecond = new EdgeLocation(hexLocOneSecond, EdgeDirection.South);
        final HexLocation hexLocTwoFirst = new HexLocation(0,-2);
        final VertexLocation vertexLocTwoFirst = new VertexLocation(hexLocTwoFirst, VertexDirection.NorthWest);
        final EdgeLocation edgeLocTwoFirst = new EdgeLocation(hexLocTwoFirst, EdgeDirection.NorthWest);
        final HexLocation hexLocTwoSecond = new HexLocation(-2,-2);
        final VertexLocation vertexLocTwoSecond = new VertexLocation(hexLocTwoSecond, VertexDirection.SouthWest);
        final EdgeLocation edgeLocTwoSecond = new EdgeLocation(hexLocTwoSecond, EdgeDirection.South);
        try{
            if(map.canInitiateSettlement(playerOne, vertexLocOneFirst)) {
                map.initiateSettlement(playerOne, vertexLocOneFirst);
            }
            if(map.canInitiateRoad(playerOne, edgeLocOneFirst, vertexLocOneFirst)) {
                map.initiateRoad(playerOne, edgeLocOneFirst, vertexLocOneFirst);
            }
            if(map.canInitiateSettlement(playerTwo, vertexLocTwoFirst)) {
                map.initiateSettlement(playerTwo, vertexLocTwoFirst);
            }
            if(map.canInitiateRoad(playerTwo, edgeLocTwoFirst, vertexLocTwoFirst)) {
                map.initiateRoad(playerTwo, edgeLocTwoFirst, vertexLocTwoFirst);
            }
            if(map.canInitiateSettlement(playerTwo, vertexLocTwoSecond)) {
                map.initiateSettlement(playerTwo, vertexLocTwoSecond);
            }
            if(map.canInitiateRoad(playerTwo, edgeLocTwoSecond, vertexLocTwoSecond)) {
                map.initiateRoad(playerTwo, edgeLocTwoSecond, vertexLocTwoSecond);
            }
            if(map.canInitiateSettlement(playerOne, vertexLocOneSecond)) {
                map.initiateSettlement(playerOne, vertexLocOneSecond);
            }
            if(map.canInitiateRoad(playerOne, edgeLocOneSecond, vertexLocOneSecond)) {
                map.initiateRoad(playerOne, edgeLocOneSecond, vertexLocOneSecond);
            }
            final EdgeLocation newRoad = new EdgeLocation(new HexLocation(-1,-3), EdgeDirection.South);
            final VertexLocation newSettlement = new VertexLocation(new HexLocation(-2,-3), VertexDirection.East);
            final VertexLocation newCity = new VertexLocation(new HexLocation(-1,-2), VertexDirection.NorthWest);
            if(map.canBuildRoad(playerTwo, newRoad)) {
                map.buildRoad(playerTwo, newRoad);
            }
            if(map.canBuildSettlement(playerTwo, newSettlement)) {
                map.buildSettlement(playerTwo, newSettlement);
            }
            if(map.canBuildCity(playerTwo, newCity)) {
                map.buildCity(playerTwo, newCity);
            }
            java.util.Map<Integer, List<ResourceType>> resources = map.getResources(8);
            StringBuilder test = new StringBuilder();
            for (java.util.Map.Entry<Integer, List<ResourceType>> entry : resources.entrySet()) {
                test.append(entry.getKey()).append("/").append(entry.getValue());
            }
            String answer = "1/[WHEAT]" + "2/[BRICK, BRICK]";
            assertTrue(test.toString().equals(answer));
            map.moveRobber(new HexLocation(-1,-2));
            resources = map.getResources(8);
            test = new StringBuilder();
            for (final java.util.Map.Entry<Integer, List<ResourceType>> entry : resources.entrySet()) {
                test.append(entry.getKey()).append("/").append(entry.getValue());
            }
            answer = "1/[WHEAT]";
            assertTrue(test.toString().equals(answer));

        } catch(InvalidLocationException | StructureException e) {
            System.out.println(e.getMessage());
        } catch(InvalidDiceRollException e) {
            assertTrue(e.getMessage().equals("Need to move robber instead of giving resources"));
        }
        try {
            java.util.Map<Integer, List<ResourceType>> resources = map.getResources(13);
        } catch(InvalidDiceRollException e) {
            assertTrue(e.getMessage().equals("Dice roll was 13"));
        }
    }

    @Test
    public void testInitiateSettlement() {
        // TODO --
    }

    /**
     * vertexLocFour       vertexLocThree
     *              xxxxxxx
     *             x       x
     *           x           x
     *         x     (0,0)     x vertexLocOne
     *           x           x
     *             x       x
     *              xxxxxxx
     *
     *
     * Verify the locations where a player can and cannot play a settlement.
     *
     * @throws Exception
     */
    @Test
    public void testCanInitiateSettlement() throws Exception {
        // the player being used to test this
        final int playerOne = 1;

        // The vertex on the East corner of hex (0, 0)
        final HexLocation hexLocOne = new HexLocation(0, 0);
        final VertexLocation vertexLocOne = new VertexLocation(hexLocOne, VertexDirection.East);

        // the vertex on the NorthWest corner of hex (1, 1)
        final HexLocation hexLocTwo = new HexLocation(1, 1);
        final VertexLocation vertexLocTwo = new VertexLocation(hexLocTwo, VertexDirection.NorthWest);

        // the vertex on the NorthEast corner of hex (0, 0)
        final HexLocation hexLocThree = new HexLocation(0, 0);
        final VertexLocation vertexLocThree = new VertexLocation(hexLocThree, VertexDirection.NorthEast);

        // the vertex on the NorthWest corner of the hex (0, 0)
        final HexLocation hexLocFour = new HexLocation(0, 0);
        final VertexLocation vertexLocFour = new VertexLocation(hexLocFour, VertexDirection.NorthWest);

        // player one should be able to create a settlement on vertexOne
        assertTrue(map.canInitiateSettlement(playerOne, vertexLocOne));

        // actually create the settlement
        map.initiateSettlement(playerOne, vertexLocOne);

        // player one should no longer be able to create a map there
        assertFalse(map.canInitiateSettlement(playerOne, vertexLocOne));

        // vertex two/three should be untouchable
        assertFalse(map.canInitiateSettlement(playerOne, vertexLocTwo));
        assertFalse(map.canInitiateSettlement(playerOne, vertexLocThree));

        // vertex four should still be able to create a settlement
        assertTrue(map.canInitiateSettlement(playerOne, vertexLocFour));

        // same assertions as above but with the jsonMap
        assertTrue(jsonMap.canInitiateSettlement(playerOne, vertexLocOne));
        jsonMap.initiateSettlement(playerOne, vertexLocOne);
        assertFalse(jsonMap.canInitiateSettlement(playerOne, vertexLocOne));
        assertFalse(jsonMap.canInitiateSettlement(playerOne, vertexLocTwo));
        assertFalse(jsonMap.canInitiateSettlement(playerOne, vertexLocThree));
        assertTrue(jsonMap.canInitiateSettlement(playerOne, vertexLocFour));
        jsonMap.canInitiateSettlement(0, vertexLocFour);

        // verify you can't place a settlement on a vertex that doesn't exist
        try{
            map.canInitiateSettlement(1, new VertexLocation(new HexLocation(3, 0), VertexDirection.NorthEast));
            assertTrue(false); // should never reach here
        } catch(InvalidLocationException e) {
            assertTrue(e.getMessage().equals("Vertex location is not on the map"));
        }
    }

    @Test
    public void testInitiateRoad() {
        // TODO --
    }

    @Test
    public void testCanInitiateRoad() throws Exception {
        final int playerOne = 1;
        final int playerTwo = 2;
        final HexLocation hexLocOneFirst = new HexLocation(0, 0);
        final VertexLocation vertexLocOneFirst = new VertexLocation(hexLocOneFirst, VertexDirection.West);
        final EdgeLocation edgeLocOneFirst = new EdgeLocation(hexLocOneFirst, EdgeDirection.NorthWest);
        final HexLocation hexLocOneSecond = new HexLocation(0, 0);
        final VertexLocation vertexLocOneSecond = new VertexLocation(hexLocOneSecond, VertexDirection.East);
        final EdgeLocation edgeLocOneSecond = new EdgeLocation(hexLocOneSecond, EdgeDirection.SouthEast);
        final HexLocation hexLocTwoFirst = new HexLocation(-2, -2);
        final EdgeLocation edgeLocTwoFirst = new EdgeLocation(hexLocTwoFirst, EdgeDirection.NorthWest);
        try {
            map.initiateSettlement(playerOne, vertexLocOneFirst);
            assertFalse(map.canInitiateRoad(playerOne, edgeLocOneFirst, vertexLocOneSecond));
            assertFalse(map.canInitiateRoad(playerTwo, edgeLocOneFirst, vertexLocOneFirst));
            map.initiateRoad(playerOne, edgeLocOneFirst, vertexLocOneFirst);
            assertFalse(map.canInitiateRoad(playerOne, edgeLocOneSecond, vertexLocOneFirst));
            map.initiateSettlement(playerOne, vertexLocOneSecond);
            assertFalse(map.canInitiateRoad(playerOne, edgeLocTwoFirst, vertexLocOneSecond));
            assertFalse(map.canInitiateRoad(playerOne, edgeLocOneFirst, vertexLocOneFirst));
            assertTrue(map.canInitiateRoad(playerOne, edgeLocOneSecond, vertexLocOneSecond));

            jsonMap.initiateSettlement(playerOne, vertexLocOneFirst);
            assertFalse(jsonMap.canInitiateRoad(playerOne, edgeLocOneFirst, vertexLocOneSecond));
            assertFalse(jsonMap.canInitiateRoad(playerTwo, edgeLocOneFirst, vertexLocOneFirst));
            jsonMap.initiateRoad(playerOne, edgeLocOneFirst, vertexLocOneFirst);
            assertFalse(jsonMap.canInitiateRoad(playerOne, edgeLocOneSecond, vertexLocOneFirst));
            jsonMap.initiateSettlement(playerOne, vertexLocOneSecond);
            assertFalse(jsonMap.canInitiateRoad(playerOne, edgeLocTwoFirst, vertexLocOneSecond));
            assertFalse(jsonMap.canInitiateRoad(playerOne, edgeLocOneFirst, vertexLocOneFirst));
            assertTrue(jsonMap.canInitiateRoad(playerOne, edgeLocOneSecond, vertexLocOneSecond));
        } catch(InvalidLocationException | StructureException e) {
            assertTrue(false); // should never reach here
        }
    }

    @Test
    public void testCanBuildRoad() throws Exception {
        initializeMap();
        assertTrue(map.canBuildRoad(1, new EdgeLocation(new HexLocation(1,1), EdgeDirection.SouthEast)));
        map.buildRoad(1, new EdgeLocation(new HexLocation(1,1), EdgeDirection.SouthEast));
        assertFalse(map.canBuildRoad(1, new EdgeLocation(new HexLocation(2,1), EdgeDirection.SouthWest)));
        assertFalse(map.canBuildRoad(1, new EdgeLocation(new HexLocation(3,2), EdgeDirection.NorthWest)));
        assertFalse(map.canBuildRoad(1, new EdgeLocation(new HexLocation(-2,-2), EdgeDirection.NorthWest)));
        assertTrue(map.canBuildRoad(1, new EdgeLocation(new HexLocation(1,2), EdgeDirection.NorthWest)));
        assertTrue(map.canBuildRoad(1, new EdgeLocation(new HexLocation(0,1), EdgeDirection.NorthEast)));

        assertTrue(jsonMap.canBuildRoad(1, new EdgeLocation(new HexLocation(1,1), EdgeDirection.SouthEast)));
        jsonMap.buildRoad(1, new EdgeLocation(new HexLocation(1,1), EdgeDirection.SouthEast));
        assertFalse(jsonMap.canBuildRoad(1, new EdgeLocation(new HexLocation(2,1), EdgeDirection.SouthWest)));
        assertFalse(jsonMap.canBuildRoad(1, new EdgeLocation(new HexLocation(3,2), EdgeDirection.NorthWest)));
        assertFalse(jsonMap.canBuildRoad(1, new EdgeLocation(new HexLocation(-2,-2), EdgeDirection.NorthWest)));
        assertTrue(jsonMap.canBuildRoad(1, new EdgeLocation(new HexLocation(1,2), EdgeDirection.NorthWest)));
        assertTrue(jsonMap.canBuildRoad(1, new EdgeLocation(new HexLocation(0,1), EdgeDirection.NorthEast)));
    }

    @Test
    public void testBuildRoad() {
        // TODO --
    }

    @Test
    public void testCanBuildSettlement() throws Exception {
        initializeMap();
        assertFalse(map.canBuildSettlement(1, new VertexLocation(new HexLocation(1,2), VertexDirection.NorthWest)));
        assertFalse(map.canBuildSettlement(1, new VertexLocation(new HexLocation(0,1), VertexDirection.NorthEast)));
        map.buildRoad(1, new EdgeLocation(new HexLocation(0,1), EdgeDirection.NorthEast));
        assertFalse(map.canBuildSettlement(1, new VertexLocation(new HexLocation(0,1), VertexDirection.NorthEast)));
        map.buildRoad(1, new EdgeLocation(new HexLocation(0,0), EdgeDirection.SouthEast));
        assertTrue(map.canBuildSettlement(1, new VertexLocation(new HexLocation(0,0), VertexDirection.East)));
        map.buildSettlement(1, new VertexLocation(new HexLocation(0,0), VertexDirection.East));
        assertFalse(map.canBuildSettlement(1, new VertexLocation(new HexLocation(0,0), VertexDirection.East)));
        map.buildCity(1, new VertexLocation(new HexLocation(0,0), VertexDirection.East));
        assertFalse(map.canBuildSettlement(1, new VertexLocation(new HexLocation(1,1), VertexDirection.NorthWest)));

        assertFalse(jsonMap.canBuildSettlement(1, new VertexLocation(new HexLocation(1,2), VertexDirection.NorthWest)));
        assertFalse(jsonMap.canBuildSettlement(1, new VertexLocation(new HexLocation(0,1), VertexDirection.NorthEast)));
        jsonMap.buildRoad(1, new EdgeLocation(new HexLocation(0,1), EdgeDirection.NorthEast));
        assertFalse(jsonMap.canBuildSettlement(1, new VertexLocation(new HexLocation(0,1), VertexDirection.NorthEast)));
        jsonMap.buildRoad(1, new EdgeLocation(new HexLocation(0,0), EdgeDirection.SouthEast));
        assertTrue(jsonMap.canBuildSettlement(1, new VertexLocation(new HexLocation(0,0), VertexDirection.East)));
        jsonMap.buildSettlement(1, new VertexLocation(new HexLocation(0,0), VertexDirection.East));
        assertFalse(jsonMap.canBuildSettlement(1, new VertexLocation(new HexLocation(0,0), VertexDirection.East)));
        jsonMap.buildCity(1, new VertexLocation(new HexLocation(0,0), VertexDirection.East));
        assertFalse(jsonMap.canBuildSettlement(1, new VertexLocation(new HexLocation(1,1), VertexDirection.NorthWest)));
    }

    @Test
    public void testBuildSettlement() {
        // TODO --
    }

    @Test
    public void testCanBuildCity() throws Exception {
        initializeMap();
        assertFalse(map.canBuildCity(1, new VertexLocation(new HexLocation(1,2), VertexDirection.NorthEast)));
        assertFalse(map.canBuildCity(1, new VertexLocation(new HexLocation(1,2), VertexDirection.East)));
        assertFalse(map.canBuildCity(2, new VertexLocation(new HexLocation(1,2), VertexDirection.NorthWest)));
        assertTrue(map.canBuildCity(1, new VertexLocation(new HexLocation(1,2), VertexDirection.NorthWest)));
        map.buildCity(1, new VertexLocation(new HexLocation(1,2), VertexDirection.NorthWest));
        assertFalse(map.canBuildCity(1, new VertexLocation(new HexLocation(1,2), VertexDirection.NorthWest)));

        assertFalse(jsonMap.canBuildCity(1, new VertexLocation(new HexLocation(1,2), VertexDirection.NorthEast)));
        assertFalse(jsonMap.canBuildCity(1, new VertexLocation(new HexLocation(1,2), VertexDirection.East)));
        assertFalse(jsonMap.canBuildCity(2, new VertexLocation(new HexLocation(1,2), VertexDirection.NorthWest)));
        assertTrue(jsonMap.canBuildCity(1, new VertexLocation(new HexLocation(1,2), VertexDirection.NorthWest)));
        jsonMap.buildCity(1, new VertexLocation(new HexLocation(1,2), VertexDirection.NorthWest));
        assertFalse(jsonMap.canBuildCity(1, new VertexLocation(new HexLocation(1,2), VertexDirection.NorthWest)));
    }

    @Test
    public void testBuildCity() {
        // TODO --
    }

    @Test
    public void testGetLongestRoad() throws Exception {
        initializeMap();
        assertTrue(1 == map.getLongestRoadSize(1));
        assertTrue(2 == map.getLongestRoadSize(2));
        map.buildRoad(1, new EdgeLocation(new HexLocation(2,2), EdgeDirection.NorthWest));
        map.buildRoad(1, new EdgeLocation(new HexLocation(-1,1), EdgeDirection.NorthEast));
        map.buildRoad(1, new EdgeLocation(new HexLocation(0,1), EdgeDirection.NorthWest));
        map.buildRoad(1, new EdgeLocation(new HexLocation(0,1), EdgeDirection.North));
        map.buildRoad(1, new EdgeLocation(new HexLocation(1,1), EdgeDirection.NorthWest));
        map.buildRoad(1, new EdgeLocation(new HexLocation(1,1), EdgeDirection.North));
        map.buildRoad(1, new EdgeLocation(new HexLocation(1,1), EdgeDirection.NorthEast));
        assertTrue(7 == map.getLongestRoadSize(1));
        map.buildRoad(1, new EdgeLocation(new HexLocation(1,2), EdgeDirection.NorthWest));
        assertTrue(10 == map.getLongestRoadSize(1));
        map.buildRoad(1, new EdgeLocation(new HexLocation(0,0), EdgeDirection.SouthWest));
        map.buildRoad(1, new EdgeLocation(new HexLocation(0,0), EdgeDirection.NorthWest));
        map.buildRoad(1, new EdgeLocation(new HexLocation(0,0), EdgeDirection.North));
        map.buildRoad(1, new EdgeLocation(new HexLocation(0,0), EdgeDirection.NorthEast));
        map.getLongestRoadSize(1);
        assertTrue(12 == map.getLongestRoadSize(1));
    }

    private void initializeMap() {
        final int playerOne = 1;
        final int playerTwo = 2;
        final HexLocation hexLocOneFirst = new HexLocation(1,2);
        final VertexLocation vertexLocOneFirst = new VertexLocation(hexLocOneFirst, VertexDirection.NorthWest);
        final EdgeLocation edgeLocOneFirst = new EdgeLocation(hexLocOneFirst, EdgeDirection.North);
        final HexLocation hexLocOneSecond = new HexLocation(0,2);
        final VertexLocation vertexLocOneSecond = new VertexLocation(hexLocOneSecond, VertexDirection.NorthWest);
        final EdgeLocation edgeLocOneSecond = new EdgeLocation(hexLocOneSecond, EdgeDirection.North);
        final HexLocation hexLocTwoFirst = new HexLocation(2,2);
        final VertexLocation vertexLocTwoFirst = new VertexLocation(hexLocTwoFirst, VertexDirection.NorthWest);
        final EdgeLocation edgeLocTwoFirst = new EdgeLocation(hexLocTwoFirst, EdgeDirection.North);
        final HexLocation hexLocTwoSecond = new HexLocation(3,2);
        final VertexLocation vertexLocTwoSecond = new VertexLocation(hexLocTwoSecond, VertexDirection.NorthWest);
        final EdgeLocation edgeLocTwoSecond = new EdgeLocation(hexLocTwoSecond, EdgeDirection.NorthWest);
        try {
            if (map.canInitiateSettlement(playerOne, vertexLocOneFirst)) {
                map.initiateSettlement(playerOne, vertexLocOneFirst);
            }
            if (map.canInitiateRoad(playerOne, edgeLocOneFirst, vertexLocOneFirst)) {
                map.initiateRoad(playerOne, edgeLocOneFirst, vertexLocOneFirst);
            }
            if (map.canInitiateSettlement(playerTwo, vertexLocTwoFirst)) {
                map.initiateSettlement(playerTwo, vertexLocTwoFirst);
            }
            if (map.canInitiateRoad(playerTwo, edgeLocTwoFirst, vertexLocTwoFirst)) {
                map.initiateRoad(playerTwo, edgeLocTwoFirst, vertexLocTwoFirst);
            }
            if (map.canInitiateSettlement(playerTwo, vertexLocTwoSecond)) {
                map.initiateSettlement(playerTwo, vertexLocTwoSecond);
            }
            if (map.canInitiateRoad(playerTwo, edgeLocTwoSecond, vertexLocTwoSecond)) {
                map.initiateRoad(playerTwo, edgeLocTwoSecond, vertexLocTwoSecond);
            }
            if (map.canInitiateSettlement(playerOne, vertexLocOneSecond)) {
                map.initiateSettlement(playerOne, vertexLocOneSecond);
            }
            if (map.canInitiateRoad(playerOne, edgeLocOneSecond, vertexLocOneSecond)) {
                map.initiateRoad(playerOne, edgeLocOneSecond, vertexLocOneSecond);
            }

            if (jsonMap.canInitiateSettlement(playerOne, vertexLocOneFirst)) {
                jsonMap.initiateSettlement(playerOne, vertexLocOneFirst);
            }
            if (jsonMap.canInitiateRoad(playerOne, edgeLocOneFirst, vertexLocOneFirst)) {
                jsonMap.initiateRoad(playerOne, edgeLocOneFirst, vertexLocOneFirst);
            }
            if (jsonMap.canInitiateSettlement(playerTwo, vertexLocTwoFirst)) {
                jsonMap.initiateSettlement(playerTwo, vertexLocTwoFirst);
            }
            if (jsonMap.canInitiateRoad(playerTwo, edgeLocTwoFirst, vertexLocTwoFirst)) {
                jsonMap.initiateRoad(playerTwo, edgeLocTwoFirst, vertexLocTwoFirst);
            }
            if (jsonMap.canInitiateSettlement(playerTwo, vertexLocTwoSecond)) {
                jsonMap.initiateSettlement(playerTwo, vertexLocTwoSecond);
            }
            if (jsonMap.canInitiateRoad(playerTwo, edgeLocTwoSecond, vertexLocTwoSecond)) {
                jsonMap.initiateRoad(playerTwo, edgeLocTwoSecond, vertexLocTwoSecond);
            }
            if (jsonMap.canInitiateSettlement(playerOne, vertexLocOneSecond)) {
                jsonMap.initiateSettlement(playerOne, vertexLocOneSecond);
            }
            if (jsonMap.canInitiateRoad(playerOne, edgeLocOneSecond, vertexLocOneSecond)) {
                jsonMap.initiateRoad(playerOne, edgeLocOneSecond, vertexLocOneSecond);
            }
        } catch(Exception ignored) {

        }
    }
}
