import java.io.File;
import java.util.Random;
import java.util.Scanner;

/**
* Demonstrator class for the Settlers of Catan simulator.
* 
* This class is intentionally designed to:
* 1. Load a predefined sequence of inputs from a file.
* 2. Convert total turns into game rounds.
* 3. Execute a complete deterministic simulation.
* 4. Demonstrate rule enforcement and game mechanics.
*
* The reviewer can run this class directly to observe:
* - Initial placement phase (forward and reverse order)
* - Dice rolling and resource production
*
* - Bank interaction
* - Build validation (legal ande illegal builds)
* - Resource payment and refund logic
* - Victory point tracking
*
* This class focuses purely on demonstration.
* AlL game logic remains encapsulated inside the Game class,
* following separation of concerns and Single Responsibility Principle.


 * A demonstrator class to showcase the functionality of the catan simulation
 */

public class Demonstrator {
    

    /**
    * Entry point of the simulator.
    * 
    * This method runs one full simulation using a predefined 
    * input file (demo_input.txt).
    * 
    * The file-driven approach ensures:
    * - Deterministic behaviour
    * - Reproducible testing
    * - Clear demonstration of specific scenarios
    */

//    public static void main(String[] args) {
//        Scanner consoleScanner = new Scanner(System.in);
//        int maxRounds = -1;
//        //Loop until a valid positive integer is entered
//        while (maxRounds <= 0 || maxRounds > MapSkeleton.maxRounds) {
//            System.out.print("Enter maximum rounds to play (positive integer): ");
//            String input = consoleScanner.nextLine().trim();
//            try {
//                maxRounds = Integer.parseInt(input);
//                if (maxRounds <= 0) {
//                    System.out.println("Error: Rounds must be greater than 0.");
//                }
//                if (maxRounds > MapSkeleton.maxRounds) {
//                    System.out.println("Error: Rounds must be less or equal to " + MapSkeleton.maxRounds);
//                }
//            } catch (NumberFormatException e) {
//                System.out.println("Error: '" + input + "' is not a valid number. Please try again.");
//            }
//        }
//
//        int numPlayers = -1;
//        // Loop until 3 or 4 is entered
//        while (numPlayers < 3 || numPlayers > 4) {
//            System.out.print("Enter number of players (3 or 4): ");
//            String input = consoleScanner.nextLine().trim();
//            try {
//                numPlayers = Integer.parseInt(input);
//                if (numPlayers < 3 || numPlayers > 4) {
//                    System.out.println("Error: This game only supports 3 or 4 players.");
//                }
//            } catch (NumberFormatException e) {
//                System.out.println("Error: Please enter the digit 3 or 4.");
//            }
//        }
//
//        System.out.println("\n↳ Initializing Game with " + maxRounds + " rounds and " + numPlayers + " players...");
//
//        //Game game = new Game(maxRounds, numPlayers);
//        //Random random = new Random(12);
//        Scanner s;
//        System.out.println("Do you want to play on your own (false) or read from a file (true): "); //awkward
//        String userOption = consoleScanner.nextLine();
//        boolean b = Boolean.parseBoolean(userOption);
//        Game game = new Game(maxRounds, numPlayers, b);
//
//        try {
//            game.run(consoleScanner);
//        } catch (Exception e) {
//            System.err.println("Fatal Game Error: " + e.getMessage());
//        } finally {
//            consoleScanner.close();
//            System.out.println("\n↳ Simulation Completed");
//        }
//    }

    public static void main(String[] args) {
        Scanner scanner;
        boolean demoMode = false;
        try {
//            scanner = new Scanner(new File("demo_input.txt"));
//            System.out.println("↳ Reading input from file: demo_input.txt");
//            demoMode = true;

            if (args.length > 0){
                scanner = new Scanner(new File(args[0]));
                System.out.println("↳ Reading input from file: " + args[0]);
                demoMode = true;
            }
            else{
                scanner = new Scanner(System.in);
                System.out.println("↳ Using console input");
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
    }
}


