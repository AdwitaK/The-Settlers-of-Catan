import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AgentTest {
    @Test
    void checkVPsAtStart(){
        //Might remove later
        Agent player = new Agent(1);
        assertEquals(0, player.getVictoryPoints());
    }

    @Test
    void checkVPsAfterSettlementBuild(){
        Agent player = new Agent(1);
        Location tileNode = new Node(1);
        player.buildSettlement(tileNode);
        assertEquals(1, player.getVictoryPoints());
    }

    @Test
    void checkVPsAfterCityBuild(){
        Agent player = new Agent(1);
        Location tileNode = new Node(1);
        player.buildCity(tileNode);
        assertEquals(2, player.getVictoryPoints());
    }
}
