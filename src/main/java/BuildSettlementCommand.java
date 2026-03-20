public class BuildSettlementCommand implements Command {

    private final GameManager game;
    private final Trader bank;
    private final Agent player;
    private final Node node;

    public BuildSettlementCommand(GameManager game, Trader bank, Agent player, Node node) {
        this.game = game;
        this.bank = bank;
        this.player = player;
        this.node = node;
    }

    private void chargeResources() {
        ResourceType[] cost = {
                ResourceType.BRICK,
                ResourceType.LUMBER,
                ResourceType.GRAIN,
                ResourceType.WOOL
        };

        for (ResourceType type : cost) {
            Card card = player.removeCard(type);
            bank.addCard(card);
        }
    }

    @Override
    public void execute() {
        chargeResources();
        player.buildSettlement(node);
        game.updateBoard();
    }

    @Override
    public void undo() {
        Infrastructure[] infra = player.getInfrastructure();

        for (int i = 0; i < player.getInfraCount(); i++) {
            if (infra[i] instanceof Settlement && infra[i].getLocation().equals(node)) {
                for (int j = i; j < player.getInfraCount() - 1; j++) {
                    infra[j] = infra[j + 1];
                }
                infra[player.getInfraCount() - 1] = null;
                player.decrementInfraCount();
                break;
            }
        }

        player.restoreSettlement();
        node.clearOccupied();

        // Refund resources
        ResourceType[] refund = {
                ResourceType.BRICK,
                ResourceType.LUMBER,
                ResourceType.GRAIN,
                ResourceType.WOOL
        };

        for (ResourceType type : refund) {
            player.addCard(new ResourceCard(type));
            bank.removeCard(type);
        }

        game.updateBoard();
    }
}