import java.util.Scanner;
public class HumanAgent extends Agent{

    public HumanAgent(int id){
        super(id);
    }


    @Override
    public void takeTurn(GameManager game, Scanner scanner) {
        boolean rolled = false;

        while (true) {
            String input = scanner.nextLine().trim();
            boolean success = false; //Capture the result of the command

            // ----- ROLL -----
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
            // ----- GO (END TURN) -----
            else if (input.matches("(?i)^\\s*go\\s*$"))  {
                if (!rolled) {
                    game.printMessage("Must roll first.");
                    continue;
                }

                if (this.mustSpendCards()){
                    //Checking if they have enough resources to build and if there are valid locations for them to even build
                    boolean canBuild =
                            this.canAfford(2) && !game.getLegalCityMoves(this).isEmpty() ||
                                    this.canAfford(1) && !game.getLegalSettlementMoves(this).isEmpty() ||
                                    this.canAfford(0) && !game.getLegalRoadMoves(this).isEmpty();

                    if(canBuild){
                        game.printMessage("You have more than 7 cards. You must build before ending your turn.");
                        continue;
                    }
                    // else the player can't build anything, and we allow them ending their turn
                }
                break; //Turn ends
            }
            // ----- UNDO -----
            else if (input.matches("(?i)^undo$")) {
                game.getCommandManager().undo();
                game.printMessage("Undo complete.");
                continue;
            }
            // ----- UNDO -----
            else if(input.matches("(?i)^redo$")) {
                game.getCommandManager().redo();
                game.printMessage("Redo complete.");
                continue;
            }
            // ----- OTHER COMMANDS -----
            else{
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