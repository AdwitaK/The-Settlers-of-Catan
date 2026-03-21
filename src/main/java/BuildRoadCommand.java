public class BuildRoadCommand implements Command {
    private final GameManager game;
    private final Trader bank;
    private final Agent player;
    private final Edge edge;

    public BuildRoadCommand(GameManager game, Trader bank, Agent player, Edge edge){
        this.game = game;
        this.bank = bank;
        this.player = player;
        this.edge = edge;
    }

    // Charge resources for road
    private void chargeResources(){
        ResourceType[] cost = { ResourceType.BRICK, ResourceType.LUMBER };

        for (ResourceType type : cost) {
            Card card = player.removeCard(type);   // take from player
            bank.addCard(card);          // give to bank
        }
    }

    @Override
    public void execute() {
        chargeResources();
        player.buildRoad(edge);
        game.updateBoard();
    }

    @Override
    public void undo() {
        Infrastructure[] myInfrastructure = player.getInfrastructure();

        for(int i = 0; i < player.getInfraCount(); i++){
            if (myInfrastructure[i] instanceof Road && myInfrastructure[i].getLocation().equals(edge)) {
                // shift array left
                for (int j = i; j < player.getInfraCount() - 1; j++) {
                    myInfrastructure[j] = myInfrastructure[j + 1];
                }
                myInfrastructure[player.getInfraCount() - 1] = null;
                player.decrementInfraCount();
                break;
            }
        }

        // Restoring road
        player.restoreRoad();

        // Clearing occupancy
        edge.clearOccupied();

        // Refund resources
        player.addCard(new ResourceCard(ResourceType.BRICK));
        player.addCard(new ResourceCard(ResourceType.LUMBER));

        bank.removeCard(ResourceType.BRICK);
        bank.removeCard(ResourceType.LUMBER);

        game.updateBoard();
    }
}//End of BuildRoadCommand()