import java.util.Scanner;
public class HumanAgent extends Agent{

    public HumanAgent(int id){
        super(id);
    }


    @Override
    public void takeTurn(Game game, Scanner scanner) {
        boolean rolled = false;

        while (true) {
            //Read human input
            String input = scanner.nextLine().trim();

            //Step forward and End turn functionality
            if (input.equalsIgnoreCase("go")) {
                if (!rolled) {
                    game.printMessage("Must roll first.");
                    continue;
                }
                game.printMessage("Decides to proceed, with no more actions.");
                break; //Exit the loop to end the turn
            }

            //Logic Rule: Can't do anything (except list) before rolling
            if (!rolled && !input.equalsIgnoreCase("roll") && !input.equalsIgnoreCase("list")) {
                game.printMessage("Must roll first.");
                continue;
            }

            //Delegate to the Regex Parser
            boolean success = game.processCommand(input);

            //Update the local turn state based on the parser's success
            if (success) {
                if (input.equalsIgnoreCase("roll")) {
                    rolled = true;
                }
            }
        }
    }



}
