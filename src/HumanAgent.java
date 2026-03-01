import java.util.Scanner;
public class HumanAgent extends Agent{

    public HumanAgent(int id){
        super(id);
    }


    @Override
    public void takeTurn(Game game, Scanner scanner) {
        boolean rolled = false;

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("roll")) {
                if (rolled) {
                    game.printMessage("Already rolled. You cannot roll again this turn.");
                    continue; // Skip the rest and ask for input again
                }
                game.processCommand(input);
                rolled = true;
            }
            else if (input.equalsIgnoreCase("go")) {
                if (!rolled) {
                    game.printMessage("Must roll first.");
                    continue;
                }
                break; //Turn ends
            }
            else {
                //For list, build, etc.
                if (!rolled && !input.equalsIgnoreCase("list")) {
                    game.printMessage("Must roll first.");
                } else {
                    game.processCommand(input);
                }
            }
        }
    }




}
