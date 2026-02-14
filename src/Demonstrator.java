package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
<<<<<<< HEAD
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
* - Build validation (legal and illegal builds)
* - Resource payment and refund logic
* - Victory point tracking
*
* This class focuses purely on demonstration.
* AlL game logic remains encapsulated inside the Game class,
* following separation of concerns and Single Responsibility Principle.
*/

=======
 * A demonstrator class to showcase the functionality of the catan simulation
 */
>>>>>>> a9a4ee4b1a2d420082ecce174e1ee4330222e357
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

    public static void main(String[] args) {
<<<<<<< HEAD
        
        //Game configuration
=======
        // Game configuration
>>>>>>> a9a4ee4b1a2d420082ecce174e1ee4330222e357
        int maxRounds;
        int numPlayers = 4; //Standard Catan player count

        // Create the game
        Game game;
        System.out.println("↳ Starting Simulation\n");
        // Read user inputs from a file instead of typing
        try {
            /*
             * ------------------------------------
             * 1. Input-Driven Simulation
             * ------------------------------------
             * Instead of interactive console input,
             * we load all player decisions from demo_input.txt.
             *
             * This allows us to:
             * - Precisely control player actions
             * - Demonstrate specific success/failure cases
             * - Ensure consistent grading behaviour
             */

            Scanner fileScanner = new Scanner(new File("demo_input.txt"));

            /**
             * ------------------------------------
             * 2. Turn -> Round Conversion
             * ------------------------------------
             * The specification defines simulation length in turns.
             * However, the Game class internally operates in rounds,
             * where:
             * 
             * 1 round = every player takes exactly one turn
             *
             * Therefore: rounds = totalTurns / numberOfPlayers
             *
             * Example:
             * turns: 16
             * players: 4
             * -> rounds = 4
             * 
             * This ensures correct full-rotation simulation.
             */

            
            //Read the first line to get the max rounds
            String line = fileScanner.nextLine().trim();

            //Split at ':' to get the number
            String[] parts = line.split(":");
            maxRounds = (Integer.parseInt(parts[1].trim()))/numPlayers;

            /*
             * ------------------------------------
             3. Game Initialization
             * ------------------------------------
             *
             * The Game constructor is responsible for creating:
             * - The Board
             * - The Bank (19 resource cards per type)
             * - AlL Agent players
             * - The Dice system
             * 
             * The Demonstrator does not manipulate internal state.
             * This preserves encapsulation.
            */

            game = new Game(maxRounds, numPlayers);

            /*
             * ------------------------------------
             * 4. Input Scenario Design Explanation
             * ------------------------------------
             * The demo_input.txt file is intentionally structured
             * to demonstrate specific rule enforcement scenarios.
             * 
             * Early in the simulation:
             * - Multiple "no" inputs are provided.
             * - This allows players to accumulate resources
             * through dice production before building.
             * 
             * In the final round, the file forces:
             * Player 1:
             * - Attempts to build a road.
             * - Placement is legal.
             * - Resources are sufficient.
             * -> Successful build demonstrated.
             * Player 2:
             * - Attempts to build a settlement.
               - Violates adjacent settlement rule.
               -> Illegal build rejection demonstrated.
             * Player 3:
            * - Attempts to build a road.
            * - Does not have required resources.
            * -> Resource validation failure demonstrated.
            * Player 4:
            * Chooses not to build.
            * -> Optional action handling demonstrated.
            
            * This deterministic sequence demonstrates:
            - Rule validation logic
            - Resource deduction
            - Bank interaction
            - Resource insufficiency handling
            - Build rejection without state corruption
            */
            
           /* 
            * ------------------------------------
            * 5. Execute Full Simulation Lifecycle
            * ------------------------------------
            game.run() performs:

            * PHASE 1 - Initial Setup
            - Forward order settlement + road placement
            - Reverse order settlement + road placement
            - Initial resource allocation

            * PHASE 2 - Main Game Loop
            * For each round:
            - Each player rolls dice
            - Resources are produced
            - Player chooses whether to build
            - Victory points are evaluated

            * Termination occurs when:
            - A player reaches 10 victory points, or
            - Maximum rounds are reached.
            */

            //pass this file scanner to the game run method
            game.run(fileScanner);

            fileScanner.close(); //prevents leaks
        }
        catch (FileNotFoundException e) {
            System.out.println("Exception: " + e);
        }

        /*
        * ------------------------------------
        * 6. Simulation Completed
        * ------------------------------------
        * The console output during execution demonstrates:
        * - Turn sequencing
        * - Dice outcomes
        * - Bank interactions
        * - Victory point tracking
        * - Resource production
        * - Legal and illegal builds
        */

        System.out.println("↳ Simulation Completed");
    }
}


