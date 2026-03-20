public class BuildCityCommand implements Command {
    private final GameManager game;
    private final Trader bank;
    private final Agent player;
    private final Node node;

    public BuildCityCommand(GameManager game, Trader bank, Agent player, Node node) {
        this.game = game;
        this.bank = bank;
        this.player = player;
        this.node = node;
    }

    private void chargeResources() {
        ResourceType[] cost = {
                ResourceType.GRAIN, ResourceType.GRAIN,
                ResourceType.ORE, ResourceType.ORE, ResourceType.ORE
        };

        for (ResourceType type : cost) {
            Card card = player.removeCard(type);
            bank.addCard(card);
        }
    }

    @Override
    public void execute() {
        chargeResources();
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
            bank.removeCard(type);
        }

        game.updateBoard();
    }
}