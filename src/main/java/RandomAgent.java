import java.util.*;

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
            if (this.canAfford(0) && (this.getRoadsLeft() > 0)){//Strategic Road
                //Always try defensive first once
                //defensiveRoadBuild(game); //db

                //Then repeatedly try bridge gaps for a max of 3 tries
                int attempts = 0;
                int maxAttempts = 3;

                while (attempts < maxAttempts && this.canAfford(0)){
                    attempts++;
                    if (!bridgeTheGap(game)) break; //there was no more gaps to fill
                    builtSomething = true;
                }
            }//end of strategic road building

            if (this.canAfford(2) && (this.getCitiesLeft() > 0)) { // City
                List<Node> citySpots = game.getLegalCityMoves(this);
                if (!citySpots.isEmpty()) {
                    Node pick = citySpots.get(rand.nextInt(citySpots.size()));
                    game.processCommand("build city " + pick.getId());
                    builtSomething = true;
                }
            }//end of city building

            else if (this.canAfford(1) && (this.getSettlementsLeft() > 0)) { // Settlement
                List<Node> settlementSpots = game.getLegalSettlementMoves(this);
                if (!settlementSpots.isEmpty()) {
                    Node pick = settlementSpots.get(rand.nextInt(settlementSpots.size()));
                    game.processCommand("build settlement " + pick.getId());
                    builtSomething = true;
                }
            }//end of settlement building
            else if (this.canAfford(0) && (this.getRoadsLeft() > 0)) { // Road
                List<Edge> roadSpots = game.getLegalRoadMoves(this);
                if (!roadSpots.isEmpty()) {
                    Edge pick = roadSpots.get(rand.nextInt(roadSpots.size()));
                    //Add the mandatory comma for the parser
                    game.processCommand("build road " + pick.getStart() + ", " + pick.getEnd());
                    builtSomething = true;
                }
            }//end of road building
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
    }//end of takeTurn


    //If you have two separate road networks with a small gap (1 or 2 empty edges) between them, random agent should prioritize "linking" them.
    public boolean bridgeTheGap(Game game){//UML
        Edge e = findLinkingRoad(game.getBoard());

        if (e != null){
            game.processCommand("build road " + e.getStart() + ", " + e.getEnd());
            return true;
        }
        return false;
    }//end of bridgeTheGap()

    private boolean isNodeInRoadNetwork(int nodeId){//UML
        return roadGraph.containsKey(nodeId);
    }

    //Get other node of an edge
    private int getOtherNode(Edge e, int nodeId){//UML
        if (e.getStart() == nodeId)
            return e.getEnd();
        else
            return e.getStart();
    }//end of getOtherNode()

    public Edge findLinkingRoad(Board board){ //UML
        for (int nodeId : roadGraph.keySet()){ //every node you touch through roads (not settlements)
            List<Edge> edges1 = board.getEdgesFromNode(nodeId); //Gets all the edges from that node (2-3)

            for (Edge e1 : edges1){
                if (e1.isOccupied()) continue; //Ignore edges that already have roads

                int nextNode = getOtherNode(e1, nodeId);
                //GAP = 1
                if (isNodeInRoadNetwork(nextNode)){
                    return e1;
                }
                //GAP = 2
                List<Edge> edges2 = board.getEdgesFromNode(nextNode);

                for (Edge e2 : edges2) {
                    if (e2 == e1) continue;
                    if (e2.isOccupied()) continue;

                    int nextNode2 = getOtherNode(e2, nextNode);

                    if (isNodeInRoadNetwork(nextNode2)){
                        return e1;
                    }
                }//end of for loop (check all edges connected to the neighbour node)
            }//end of for loop (check all edges connected to one node)
        }//end of for loop (check all nodes in every road you have)
        return null; //no road to link was found
    }//end of findLinkingRoad()


    //Tries to maintain 2-road safety buffer to prevent opponent from taking the title of longest road owner.
    public void defensiveRoadBuild(Game game){//UML
        return;
    }//end of defensiveRoadBuild()
}//end of RandomAgent() class
