import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Game game;
    private HumanAgent human;

    @BeforeEach
    void setup() {
        game = new Game(1, 4);
        human = (HumanAgent) game.getAgents()[0];
        game.setCurrentPlayer(human); //game now has access to the human player from here


        // Add dummy cards so build commands can be processed without failing due to resources
        for (int i = 0; i < 5; i++) {
            human.addCard(new ResourceCard(ResourceType.BRICK));
            human.addCard(new ResourceCard(ResourceType.LUMBER));
            human.addCard(new ResourceCard(ResourceType.GRAIN));
            human.addCard(new ResourceCard(ResourceType.WOOL));
            human.addCard(new ResourceCard(ResourceType.ORE));
        }
    }

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
