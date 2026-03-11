import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Game game;
    private HumanAgent human;

    @Test
    void testRollCommandParsing() {
        assertTrue(game.processCommand("roll")); // First roll
        assertTrue(game.processCommand(" RoLL  ")); //Testing case sensitivity
    }

    @Test
    void testGoCommandParsing() {
        assertFalse(game.processCommand("go")); // Can't 'go' before rolling
    }

    @Test
    void testListCommandParsing() {
        assertTrue(game.processCommand("list"));
        assertTrue(game.processCommand("  LisT  ")); //Testing case sensitivity
    }

    @Test
    void testInvalidCommands() {
        assertFalse(game.processCommand("fly"));
        assertFalse(game.processCommand(""));
        assertFalse(game.processCommand("1234"));
        assertFalse(game.processCommand(" ro ll "));
    }
}
