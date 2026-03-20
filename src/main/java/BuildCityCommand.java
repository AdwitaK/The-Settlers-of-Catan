public class BuildCityCommand implements Command {
    private final Game game;
    private final Agent player;
    private final Node node;

    public BuildCityCommand(Game game, Agent player, Node node) {
        this.game = game;
        this.player = player;
        this.node = node;
    }

    @Override
    public void execute() {
        player.buildCity(node);
        game.updateBoard();
    }

    @Override
    public void undo() {
        Infrastructure[] infra = player.getInfrastructure();

        // Replace city with settlement
        for (int i = 0; i < player.getInfraCount(); i++) {
            if (infra[i] instanceof City && infra[i].getLocation().equals(node)) {
                infra[i] = new Settlement(player);
                infra[i].setLocation(node);
                break;
            }
        }

        player.restoreCity();
        player.restoreSettlement();

        // Refund resources
        ResourceType[] refund = {
                ResourceType.GRAIN, ResourceType.GRAIN,
                ResourceType.ORE, ResourceType.ORE, ResourceType.ORE
        };

        for (ResourceType type : refund) {
            player.addCard(new ResourceCard(type));
            game.getBank().removeCard(type);
        }

        game.updateBoard();
    }
}