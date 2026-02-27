import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest {
    @Test
    void checkInitialValues(){
        Trader bank = new Bank();
        int [] cardsPerType = {19, 19, 19, 19, 19};

        assertArrayEquals(cardsPerType, bank.getResourceCount());
    }

    @Test
    void checkCardAddition(){
        Trader bank = new Bank();
        Card card = new ResourceCard(ResourceType.BRICK);
        int [] cardsPerType = {20, 19, 19, 19, 19};
        bank.addCard(card);
        assertArrayEquals(cardsPerType, bank.getResourceCount());
    }

    @Test
    void checkCardRemoval(){
        Trader bank = new Bank();
        int [] cardsPerType = {19, 18, 19, 19, 19};
        bank.removeCard(ResourceType.ORE);
        assertArrayEquals(cardsPerType, bank.getResourceCount());
    }
}
