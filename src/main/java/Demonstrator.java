import java.io.File;
import java.util.Scanner;

/**
 * Demonstrator class for the Settlers of Catan simulator.
 *
 * This class serves as the entry point for running and demonstrating
 * the Catan simulation. It allows the user to either:
 *
 * 1. Run a deterministic demonstration using a predefined input file for 5 rounds.
 * 2. Play interactively through the console.
 *
 * When file mode is selected, the program reads commands from
 * "demo_input.txt". This allows for reproducible demonstrations
 * of game mechanics and rule enforcement.
 *
 * When console mode is selected, the user can manually enter
 * commands and play through the simulation step by step.
 *
 *
 * The reviewer can run this class directly (either file or normal mode) to observe:
 * - Initial placement phase (forward and reverse order)
 * - Dice rolling and resource production
 * - Bank interaction
 * - Build validation (legal ande illegal builds)
 * - Resource payment and refund logic
 * - Turn progression between human and random agents
 * - Victory point tracking
 *
 * This class focuses purely on demonstration.
 * AlL game logic remains encapsulated inside the Game class,
 * following separation of concerns and Single Responsibility Principle.
 *
 * This structure allows both interactive gameplay and deterministic
 * scripted demonstrations for testing and evaluation.
 *
 * A demonstrator class to showcase the functionality of the catan simulation
 */

public class Demonstrator {

    /**
     * Entry point of the simulator.
     *
     * This method starts one full simulation of the Catan game.
     * The user is prompted to choose whether the game should read
     * commands from a predefined input file (demo_input.txt) or
     * accept commands interactively through the console.
     *
     * File mode enables:
     * - Deterministic behaviour
     * - Reproducible testing
     * - Clear demonstration of specific scenarios
     *
     * Console mode enables:
     * - Interactive gameplay
     * - Manual testing of commands and game rules
     *
     * After selecting the input mode, the method initializes the
     * game with the specified number of rounds and players, and
     * then runs the simulation using the chosen input source.
     */

    public static void main(String[] args) {
        Scanner scanner;
        Scanner consoleScanner = new Scanner(System.in);
        boolean demoMode = false;
        try {
            while (true){
                System.out.println("Read from a file (1) or Play through a console (2): ");
                String userOption = consoleScanner.nextLine();
                if (userOption.equals("1")){
                    scanner = new Scanner(new File("demo_input.txt"));
                    System.out.println("↳ Reading input from file: demo_input.txt");
                    demoMode = true;
                    break;
                }
                if (userOption.equals("2")){
                    scanner = consoleScanner;
                    System.out.println("↳ Using console input");
                    break;
                }
            }

            int maxRounds = -1;

            while (maxRounds <= 0 || maxRounds > MapSkeleton.maxRounds){
                System.out.print("Enter maximum rounds to play (positive integer): ");
                String input = scanner.nextLine().trim();

                try {
                    maxRounds = Integer.parseInt(input);

                    if (maxRounds <= 0)
                        System.out.println("Error: Rounds must be greater than 0.");

                    if (maxRounds > MapSkeleton.maxRounds)
                        System.out.println("Error: Rounds must be ≤ " + MapSkeleton.maxRounds);

                }
                catch (NumberFormatException e) {
                    System.out.println("Error: '" + input + "' is not a valid number.");
                }
            }

            int numPlayers = -1;

            while (numPlayers < 3 || numPlayers > 4) {
                System.out.print("Enter number of players (3 or 4): ");
                String input = scanner.nextLine().trim();

                try {
                    numPlayers = Integer.parseInt(input);

                    if (numPlayers < 3 || numPlayers > 4)
                        System.out.println("Error: This game only supports 3 or 4 players.");

                }
                catch (NumberFormatException e) {
                    System.out.println("Error: Please enter 3 or 4.");
                }
            }

            System.out.println("\n↳ Initializing Game with " + maxRounds + " rounds and " + numPlayers + " players...");

            Game game = new Game(maxRounds, numPlayers, demoMode);

            game.run(scanner);

            scanner.close();
            System.out.println("\n↳ Simulation Completed");

        } catch (Exception e) {
            System.err.println("Fatal Game Error: " + e.getMessage());
        }
        consoleScanner.close();
    }
}