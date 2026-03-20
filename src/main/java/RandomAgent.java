import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RandomAgent extends Agent{

    private Random rand;

    public RandomAgent(int id, Random random){
        super(id);
        rand = random;
    }

    @Override
    public void takeTurn(Game game, Scanner scanner) {
        game.processCommand("roll");
        game.processCommand("list");

        while(this.canAfford(2) || this.canAfford(1) || this.canAfford(0)) {

            boolean builtSomething = false;
            if (this.canAfford(2)) { // City
                List<Node> citySpots = game.getLegalCityMoves(this);
                if (!citySpots.isEmpty()) {
                    Node pick = citySpots.get(rand.nextInt(citySpots.size()));
                    game.processCommand("build city " + pick.getId());
                    builtSomething = true;
                }
            } else if (this.canAfford(1)) { // Settlement
                List<Node> settlementSpots = game.getLegalSettlementMoves(this);
                if (!settlementSpots.isEmpty()) {
                    Node pick = settlementSpots.get(rand.nextInt(settlementSpots.size()));
                    game.processCommand("build settlement " + pick.getId());
                    builtSomething = true;
                }
            } else if (this.canAfford(0)) { // Road
                List<Edge> roadSpots = game.getLegalRoadMoves(this);
                if (!roadSpots.isEmpty()) {
                    Edge pick = roadSpots.get(rand.nextInt(roadSpots.size()));
                    //Add the mandatory comma for the parser
                    game.processCommand("build road " + pick.getStart() + ", " + pick.getEnd());
                    builtSomething = true;
                }
            }
            //Stop if cannot build anything even with more than 7 cards
            if (this.mustSpendCards() && !builtSomething){
                game.printMessage("Could not spend all cards; no legal moves available.");//print a message that it could not build anything ...
                break;
            }

            //Stop if could not afford anything to build at all
            if (!builtSomething) {
                game.printMessage("Could not build anything even with enough resources; no legal moves available.");
                break;
            }
        }
    }
}//end of RandomAgent() class
