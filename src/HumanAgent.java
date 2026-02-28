import java.util.Scanner;
public class HumanAgent extends Agent{

    public HumanAgent(int id){
        super(id);
    }

    @Override
    public void takeTurn(Game game, Scanner scanner){
        boolean rolled = false;

        while (true) {
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("roll")) {
                if (!rolled) {
                    game.processCommand(input);
                    rolled = true;
                } else {
                    game.printMessage("You already rolled.");
                }
            }
            else if (input.startsWith("build")) {
                if (!rolled) {
                    game.printMessage("You must roll first.");
                } else {
                    game.processCommand(input);
                    break; // turn ends after build attempt
                }
            }
            else if (input.equalsIgnoreCase("list")) {
                game.processCommand(input);
            }
            else {
                game.printMessage("Invalid command.");
            }
        }
    }


}
