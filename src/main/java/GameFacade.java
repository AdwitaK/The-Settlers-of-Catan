import java.util.Scanner;

public class GameFacade {

    private GameManager manager;

    public GameFacade(int rounds, int numPlayers, boolean demoMode) {
        this.manager = new GameManager(rounds, numPlayers, demoMode);
    }

    public void run(Scanner scanner) {
        manager.run(scanner);
    }

}//end of GameFacade class