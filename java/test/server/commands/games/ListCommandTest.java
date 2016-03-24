package server.commands.games;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import server.commands.CommandExecutionResult;
import server.facade.MockFacade;
import server.main.Config;

/**
 * Unit Testing for the "List Games" command.
 *
 * @author Derek Argueta
 */
public class ListCommandTest {

    @Before
    public void setUp() {
        Config.facade = new MockFacade();
    }

    @After
    public void tearDown() {

    }

    /**
     * Validates that the command works - this is a simple list command
     */
    @Test
    public void testExecute() {
        CommandExecutionResult result = new ListCommand().execute();
        assertTrue(result != null);
        assertFalse(result.errorOccurred());
    }
}
