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
        game.processCommand("roll"); //Has to roll first by default

        //Display the card of each random agent player by default, so that we know what they have
        game.processCommand("list");

        //Check for City Upgrades first (High Priority because it gives the highest amount of VP)
        if (this.canAfford(2)) { // 2 = City
            List<Node> citySpots = game.getLegalCityMoves(this);
            if (!citySpots.isEmpty()) {
                //Pick a random valid spot for building a city from all the possible valid spots
                Node pick = citySpots.get(rand.nextInt(citySpots.size()));
                game.processCommand("build city " + pick.getId());
            }
        }
        //Otherwise, try for a Settlement (because it gives the second-highest amount of VP)
        else if (this.canAfford(1)) {
            List<Node> settlementSpots = game.getLegalSettlementMoves(this);
            if (!settlementSpots.isEmpty()) {
                //Pick a random valid spot for building a settlement from all the possible valid spots
                Node pick = settlementSpots.get(rand.nextInt(settlementSpots.size()));
                game.processCommand("build settlement " + pick.getId());
            }
        }
        //Otherwise, try for a Road, as last remaining build option
        else if (this.canAfford(0)) {
            List<Edge> roadSpots = game.getLegalRoadMoves(this);
            if (!roadSpots.isEmpty()) {
                //Pick a random valid spot for building a road from all the possible valid spots
                Edge pick = roadSpots.get(rand.nextInt(roadSpots.size()));
                game.processCommand("build road " + pick.getStart() + " " + pick.getEnd());
            }
        }

        game.processCommand("go");
    }





}
