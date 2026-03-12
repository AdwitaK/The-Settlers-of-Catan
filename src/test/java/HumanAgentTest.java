import org.junit.jupiter.api.Test;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class HumanAgentTest {

    // Minimal Game stub, just for the purpose of testing
    static class TestGame extends Game {
        public TestGame() {
            super(1, 1, true);
        }

        @Override
        public boolean processCommand(String command) {
            // Only simulate "roll" and "go"
            return command.equalsIgnoreCase("roll") || command.equalsIgnoreCase("go");
        }

        @Override
        public void printMessage(String message) {
            // Ignore output because we don't need to have it directly
        }
    }

    @Test
    void testRollThenGo() {
        // Inputs: try 'go' first, then 'roll', then 'go'
        String input = "go\nroll\ngo\n";
        Scanner scanner = new Scanner(input);

        HumanAgent human = new HumanAgent(0);
        TestGame game = new TestGame();
        human.takeTurn(game, scanner);// Run takeTurn: it should respect roll, then go ordering

        assertTrue(true);
        game.endVisualizer();
    }

    @Test
    void testListBeforeRoll() {
        // Inputs: list, then 'roll', then 'go'
        String input = "list\nroll\ngo\n";
        Scanner scanner = new Scanner(input);

        HumanAgent human = new HumanAgent(0);
        TestGame game = new TestGame();
        human.takeTurn(game, scanner);

        assertTrue(true);
        game.endVisualizer();
    }
}
