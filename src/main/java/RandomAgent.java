import java.util.*;

public class RandomAgent extends Agent{

    private Random rand;

    public RandomAgent(int id, Random random){
        super(id);
        rand = random;
    }

    @Override
    public void takeTurn(GameManager game, Scanner scanner) {
        game.processCommand("roll");
        game.processCommand("list");

        while(this.canAfford(2) || this.canAfford(1) || this.canAfford(0)) {

            boolean builtSomething = false;
            if (this.canAfford(0) && (this.getRoadsLeft() > 0)){//Strategic Road
                //Always try defensive first once
                defensiveRoadBuild(game);

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
    public boolean bridgeTheGap(GameManager game){
        Edge e = findLinkingRoad(game.getBoard());

        if (e != null){
            game.processCommand("build road " + e.getStart() + ", " + e.getEnd());
            return true;
        }
        return false;
    }//end of bridgeTheGap()

    private boolean isNodeInRoadNetwork(int nodeId){
        return roadGraph.containsKey(nodeId);
    }

    //Get other node of an edge
    private int getOtherNode(Edge e, int nodeId){
        if (e.getStart() == nodeId)
            return e.getEnd();
        else
            return e.getStart();
    }//end of getOtherNode()

    public Edge findLinkingRoad(Board board){
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
    public void defensiveRoadBuild(GameManager game){
        if (!hasLongestRoad()) return; // Only defensive if we already own the longest road
        for (Trader t : game.getAgents()){
            Agent other = (Agent) t;
            if (other == this) continue; //skips looking at themselves

            int myRoadLength = this.getLongestRoadLength(game);
            int otherRoadLength = other.getLongestRoadLength(game);

            if (otherRoadLength == myRoadLength - 1){
                Edge e = findBestExtension(game);
                if (e != null){
                    game.processCommand("build road " + e.getStart() + ", " + e.getEnd());
                    return;
                }
            }
        }//end of for loop (check all agents)
    }//end of defensiveRoadBuild()

    public Edge findBestExtension(GameManager game){
        /* Start of finding the best path to extend from to increase road length */
        List<Edge> bestPath = new ArrayList<>();

        //Find the longest road path
        for (int i = 0; i < getInfraCount(); i++) {
            Infrastructure infra = getInfrastructure()[i];

            if (infra instanceof Road){
                Edge start = (Edge) infra.getLocation();

                List<Edge> currentPath = new ArrayList<>();
                Set<Edge> usedEdges = new HashSet<>();

                List<Edge> result = dfsEdgePath(start, usedEdges, game, currentPath); //Uses depth first search to find longest path

                if (result.size() > bestPath.size()) {
                    bestPath = new ArrayList<>(result);
                }
            }
        }//end of for loop

        if (bestPath.isEmpty()) return null;
        /* End of finding the best path to extend from to increase road length */

        //Get endpoints of best path
        Set<Integer> endpoints = getEndpointsFromPath(bestPath);

        Edge bestEdge = null;
        int bestScore = -1; //Calculated based the degree of the next node (how many unbuilt paths lead out of it)

        //Check which end of the path is best to build from
        for (int node : endpoints){
            for (Edge e : game.getBoard().getEdgesFromNode(node)){//Checks all edges from the endpoint node
                if (e.isOccupied()) continue; //road is already there
                if (bestPath.contains(e)) continue; //dont look back into the path
                if (game.isBlocked(node, this)) continue; //Checks if the node contains a building from other agent -> blocked

                int nextNode; //Identify the "other" end of the edge to continue traversal
                if (e.getStart() == node)
                    nextNode = e.getEnd();
                else
                    nextNode = e.getStart();

                int futureOptions = 0;

                for (Edge next : game.getBoard().getEdgesFromNode(nextNode)){
                    if (!next.isOccupied()) {
                        futureOptions++;
                    }
                }

                if (futureOptions > bestScore){
                    bestScore = futureOptions;
                    bestEdge = e;
                }
            }//end of for (check edges from a node)
        }//end of for (checking all endpoints nodes)
        return bestEdge;
    }//end of findBestExtension()

    //Find the path (list of edges) of the longest road using depth-first search
    private List<Edge> dfsEdgePath(Edge current, Set<Edge> usedEdges, GameManager game, List<Edge> path){
        usedEdges.add(current);
        path.add(current);

        List<Edge> best = new ArrayList<>(path);

        int[] nodes = {current.getStart(), current.getEnd()};

        for (int node : nodes){
            if (game.isBlocked(node, this)) continue;
            for (Edge next : game.getBoard().getEdgesFromNode(node)){
                if (usedEdges.contains(next)) continue;
                if (!isMyRoad(next)) continue;

                List<Edge> candidate = dfsEdgePath(next, usedEdges, game, path);

                if (candidate.size() > best.size()) {
                    best = new ArrayList<>(candidate);
                }
            }
        }//end of for loop

        usedEdges.remove(current); //backtrack
        path.remove(path.size() - 1);

        return best;
    }//end of dfsEdgePath()


    //Only works if path is a simple path w/ no branches (should be since its called using bestPath)
    private Set<Integer> getEndpointsFromPath(List<Edge> path){
        Set<Integer> endpoints = new HashSet<>(); //path of nodeIds
        //Count how many times each node appears in the path
        Map<Integer, Integer> degree = new HashMap<>();

        for (Edge e : path){
            //getOrDefault: If this node exists -> return its count. If not -> the count is 0
            degree.put(e.getStart(), degree.getOrDefault(e.getStart(), 0) + 1);
            degree.put(e.getEnd(), degree.getOrDefault(e.getEnd(), 0) + 1);
        }

        //Endpoint nodes = appears only once in the path
        for (int node : degree.keySet()){
            if (degree.get(node) == 1){
                endpoints.add(node);
            }
        }
        return endpoints;
    }//end of getEndpointsFromPath()
}//end of RandomAgent() class
