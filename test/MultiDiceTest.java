import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MultiDiceTest {

    @Test
    void checkDiceRollRange(){
        MultiDice dice = new MultiDice(2);
        int num = dice.roll();

        int min = 2;
        int max = 12;
        assertTrue(num >= min && num <= max);
    }
}
