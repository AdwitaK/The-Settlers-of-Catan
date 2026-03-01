import java.util.Random;
import java.util.Scanner;

public class RandomAgent extends Agent{

    private Random rand;

    public RandomAgent(int id, Random random){
        super(id);
        rand = random;
    }

    @Override
    public boolean yesNoMove(Scanner scanner) { //Scanner is passed in order to respect the signature of the abstract methods.
        return rand.nextBoolean();
    }

    @Override
    public int chooseOption(Scanner scanner) {
        return rand.nextInt(4) + 1; //chooses from 1 to 4
    }
}
