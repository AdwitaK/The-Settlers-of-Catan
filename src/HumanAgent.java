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
                    game.printMessage("Already rolled.");
                }
            }
            else if (input.startsWith("build")) {
                if (!rolled) {
                    game.printMessage("Must roll first.");
                } else {
                    game.processCommand(input);
                    break; // turn ends after build attempt
                }
            }
            else if (input.equalsIgnoreCase("list")) {
                game.processCommand(input);
            }
            else if (input.equalsIgnoreCase("go")) {
                if(!rolled){
                    game.printMessage("Must roll first.");
                } else{
                    game.printMessage("Decides to proceed, with no more actions.");
                    break;
                }
            }
            else {
                game.printMessage("Invalid command.");
            }
        }
    }


}
