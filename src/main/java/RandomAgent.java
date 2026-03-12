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

        if (this.canAfford(2)) { // City
            List<Node> citySpots = game.getLegalCityMoves(this);
            if (!citySpots.isEmpty()) {
                Node pick = citySpots.get(rand.nextInt(citySpots.size()));
                game.processCommand("build city " + pick.getId());
            }
        }
        else if (this.canAfford(1)) { // Settlement
            List<Node> settlementSpots = game.getLegalSettlementMoves(this);
            if (!settlementSpots.isEmpty()) {
                Node pick = settlementSpots.get(rand.nextInt(settlementSpots.size()));
                game.processCommand("build settlement " + pick.getId());
            }
        }
        else if (this.canAfford(0)) { // Road
            List<Edge> roadSpots = game.getLegalRoadMoves(this);
            if (!roadSpots.isEmpty()) {
                Edge pick = roadSpots.get(rand.nextInt(roadSpots.size()));
                //Add the mandatory comma for the parser
                game.processCommand("build road " + pick.getStart() + ", " + pick.getEnd());
            }
        }
    }
}//end of RandomAgent() class
