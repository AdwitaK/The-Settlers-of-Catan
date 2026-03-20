import java.util.ArrayList;
import java.util.List;

public class Robber {
    private HexTile currentTile;

    public Robber(HexTile startTile){
        this.currentTile = startTile;
    }

    public boolean moveRobber(HexTile newTile){
        //check if the robber is already on this tile
        if (currentTile.equals(newTile)){
            return false; //can't place the robber in the same tile again
        }

        //removes robber from old tile
        if (currentTile != null){
            currentTile.setRobber(false);
        }

        //moves robber to new tile
        this.currentTile = newTile;
        newTile.setRobber(true);

        return true; //robber changed successful
    }
    public HexTile getCurrentTile(){
        return currentTile;
    }


    /**
     * Orchestrates the full robber event triggered by rolling a 7:
     * discard phase, move phase, steal phase.
     * Moved from GameManager to keep robber logic self-contained.
     */
    public void activate(Trader[] agents, Trader currentPlayer, Trader bank,
                         java.util.List<HexTile> tiles, java.util.Random random,
                         int currentRound, JsonWriter writer) {

        // Discard cards
        for (Trader agent : agents) {
            Agent player = (Agent) agent;
            if (player.getTotalCardCount() > 7) {
                java.util.List<Card> discardCards = player.discardHalfOfHand(random);
                for (Card discardCard : discardCards)
                    bank.addCard(discardCard);
                if (!discardCards.isEmpty()) {
                    System.out.println(currentRound + " / " + player.getId() + ": Discard half their deck");
                }
            }
        }

        // Move robber
        HexTile targetTile;
        do {
            targetTile = tiles.get(random.nextInt(tiles.size()));
        } while (!this.moveRobber(targetTile));

        writer.moveRobber(targetTile.getlandTileID());
        System.out.println(currentRound + " / " + ((Agent) currentPlayer).getId()
                + ": Moved the robber to tile " + targetTile.getlandTileID());

        // Steal card
        Location[] tileNodes = targetTile.getNodes();
        java.util.Set<Agent> qualifyingAgents = new java.util.HashSet<>();

        for (Trader agent : agents) {
            if (agent != currentPlayer && agent instanceof Agent enemyAgent) {
                boolean found = false;
                for (int i = 0; i < enemyAgent.getInfraCount() && !found; i++) {
                    Infrastructure infra = enemyAgent.getInfrastructure()[i];
                    for (Location node : tileNodes) {
                        if (infra.getLocation() == node) {
                            qualifyingAgents.add(enemyAgent);
                            found = true;
                            break;
                        }
                    }
                }
            }
        }

        if (!qualifyingAgents.isEmpty()) {
            ArrayList<Agent> victimList = new ArrayList<>(qualifyingAgents);
            Agent victim = victimList.get(random.nextInt(victimList.size()));
            Card stolenCard = victim.removeRandomCard(random);
            int currentPlayerId = ((Agent) currentPlayer).getId();
            if (stolenCard != null) {
                currentPlayer.addCard(stolenCard);
                System.out.println(currentRound + " / " + currentPlayerId + ": Steals a card from " + victim.getId());
            } else {
                System.out.println(currentRound + " / " + currentPlayerId + ": " + victim.getId() + " Has no cards to steal");
            }
        }
    }
}//end of Robber() class