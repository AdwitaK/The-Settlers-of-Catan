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

            //Capture the result of the command
            boolean success = false;

            if (input.equalsIgnoreCase("roll")) {
                if (rolled) {
                    game.printMessage("Already rolled. You cannot roll again this turn.");
                    continue;
                }
                success = game.processCommand(input);
                if (success) {
                    rolled = true;
                    game.printMessage("Roll accepted. You may now build or type 'go' to end turn.");
                }
            }
            else if (input.equalsIgnoreCase("go")) {
                if (!rolled) {
                    game.printMessage("Must roll first.");
                    continue;
                }
                break; //Turn ends
            }
            else {
                if (!rolled && !input.equalsIgnoreCase("list")) {
                    game.printMessage("Must roll first.");
                } else {
                    success = game.processCommand(input);

                    //Give feedback if a build was successful
                    if (success && input.startsWith("build")) {
                        game.printMessage("Construction complete! Anything else? (or type 'go')");
                    }
                }
            }
        }
    }





}
