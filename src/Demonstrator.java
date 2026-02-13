//package src;
////public class Demonstrator {
////    static void main(String[] args){
////        Game gameOne = new Game(8192, 4);
////        gameOne.run();
////    }
////}
//
///**
// * Demonstrator class for the Game Simulator.
// * This class serves as the entry point to showcase the game logic,
// * including initial setup, resource production, and building mechanics.
// */
//public class Demonstrator {
//
//    public static void main(String[] args) {
//        // --- 1. Initialization Phase ---
//        // We create a simulation with a set number of rounds and players.
//        // The Game constructor handles the creation of the Board, Bank, and Agents.
//        int rounds = 10;
//        int players = 3;
//
//        System.out.println("DEBUG: Starting Simulation with " + players + " players.");
//        Game simulation = new Game(rounds, players);
//
//        /* * --- 2. Execution Phase ---
//         * The run() method triggers the core game loop:
//         * a) initialSetup(): Demonstrates player placement of settlements and roads.
//         * b) turnLoop(): Iterates through rounds, handling:
//         * - Dice rolling (MultiDice class)
//         * - Resource production (produceResource method)
//         * - Player actions (build method)
//         * - Victory condition checks (10 Victory Points)
//         */
//        try {
//            System.out.println("LOG: Entering Game Loop...");
//            simulation.run();
//        } catch (Exception e) {
//            // Error handling to ensure the simulator provides feedback if logic fails
//            System.out.println("CRITICAL: Simulation interrupted.");
//            e.printStackTrace();
//        }
//
//        // --- 3. Termination Phase ---
//        System.out.println("LOG: Simulation complete. Check console output for winner details.");
//    }
//}

package src;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * DEMONSTRATOR CLASS
 * * Purpose: This class serves as the entry point to observe the simulator's behavior.
 * It demonstrates:
 * 1. Automated Game Setup (R1.1 - R1.3)
 * 2. Resource Management & Invariants (R1.6)
 * 3. Linear Action Validation & Random Selection (R1.8)
 * * Engineering Decision: We use System.setIn to simulate real-time console input,
 * allowing the reviewer to see a "hands-off" execution of the logic.
 */
public class Demonstrator {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   CATAN SIMULATOR: ARCHITECTURAL DEMONSTRATION  ");
        System.out.println("==================================================");

        // --- SCENARIO 1: Standard 3-Player Match ---
        // This simulates the logic for setup and several rounds of play.
        // Input String simulates: Node IDs, Edge IDs, and 'yes/no' build choices.
        String automatedScript = buildScript();
        InputStream testInput = new ByteArrayInputStream(automatedScript.getBytes());
        System.setIn(testInput);

        System.out.println("[INFO] Initializing Game Engine...");

        /* * R1.1 Initialization: Game(rounds, players)
         * This triggers the creation of the Board, Bank, and Agents.
         */
        Game catanSim = new Game(20, 3);

        System.out.println("[INFO] Starting Automated Simulation Loop...");
        try {
            // This runs the full logic: Setup -> Rounds -> Victory Check
            catanSim.run();
        } catch (Exception e) {
            System.err.println("[ERROR] Simulation Invariant Violated: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("==================================================");
        System.out.println("            DEMONSTRATION CONCLUDED              ");
        System.out.println("==================================================");
    }

    /**
     * Helper method to generate a script of inputs for the Scanner.
     * This mimics a user providing valid IDs for nodes and edges.
     */
    private static String buildScript() {
        StringBuilder sb = new StringBuilder();

        // --- Setup Phase Inputs ---
        // Each player needs: Node ID, Start Node ID, End Node ID (x2 for snake order)
        // Adjust these numbers based on your MapSkeleton IDs
        for(int i = 0; i < 6; i++) {
            sb.append("10\n"); // Node ID
            sb.append("10\n11\n"); // Edge Start/End
        }

        // --- Turn Phase Inputs ---
        // For each round, we simulate a decision to build or not
        for(int i = 0; i < 50; i++) {
            sb.append("no\n"); // Choosing not to build to speed up demo
        }

        return sb.toString();
    }
}