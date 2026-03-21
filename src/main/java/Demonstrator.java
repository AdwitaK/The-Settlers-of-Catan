import java.io.File;
import java.util.Scanner;

/**
 * Demonstrator class for the Settlers of Catan simulator.
 *
 * This class serves as the entry point for running and demonstrating
 * the Catan simulation. It allows the user to either play interactively through the console.
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
 * - undoing/redoing builds within the same round
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
     *
     * Console mode enables:
     * - Interactive gameplay
     * - Manual testing of commands and game rules
     *
     * The method initializes the
     * game with the specified number of rounds and players, and
     * then runs the simulation by interacting with the user
     *
     * Undo and redo commands can be used to undo/redo
     * build actions within the same round.
     *
     * Computer agents now use smarter algorithms for build strategies
     * They can recognize opportunities to earn victory points and choose
     * their moves strategically.
     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean demoMode = false;
        try {
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

            GameFacade game = new GameFacade(maxRounds, numPlayers, demoMode);

            game.run(scanner);

            scanner.close();
            System.out.println("\n↳ Simulation Completed");

        } catch (Exception e) {
            System.err.println("Fatal Game Error: " + e.getMessage());
        }
        scanner.close();
    }
}//end of Demonstrator() class