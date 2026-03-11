import java.util.Scanner;
public class HumanAgent extends Agent{

    public HumanAgent(int id){
        super(id);
    }


    @Override
    public void takeTurn(Game game, Scanner scanner) {
        boolean rolled = false;

        while (true) {
            String input = scanner.nextLine();
            boolean success = false; //Capture the result of the command

            if (input.matches("(?i)^\\s*roll\\s*$"))  {
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
            else if (input.matches("(?i)^\\s*go\\s*$"))  {
                if (!rolled) {
                    game.printMessage("Must roll first.");
                    continue;
                }
                break; //Turn ends
            }
            else {
                if (!rolled && !input.matches("(?i)^\\s*list\\s*$"))  {
                    game.printMessage("Must roll first.");
                } else {
                    success = game.processCommand(input);

                    //Give feedback if a build was successful
                    if (success && input.matches("(?i)^\\s*build.*"))  {
                        game.printMessage("Construction complete! Anything else? (or type 'go')");
                    }
                }
            }
        }
    }





}
