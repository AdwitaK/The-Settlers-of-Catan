import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AgentTest {
    @Test
    void checkVPsAtStart(){
        //Might remove later
        Agent player = new HumanAgent(1);
        assertEquals(0, player.getVictoryPoints());
    }

    @Test
    void checkVPsAfterSettlementBuild(){
        Agent player = new HumanAgent(1);
        Location tileNode = new Node(1);
        player.buildSettlement(tileNode);
        assertEquals(1, player.getVictoryPoints());
    }

    @Test
    void checkVPsAfterCityBuild(){
        Agent player = new HumanAgent(1);
        Location tileNode = new Node(1);
        player.buildSettlement(tileNode);
        player.buildCity(tileNode);
        assertEquals(2, player.getVictoryPoints());
    }

    @Test
    void justEnoughBuilds(){
        Agent player = new HumanAgent(1);
        Board board = new Board();
        //Each player only gets 5 settlements
        for(int i = 0; i<4; i++){
            player.buildSettlement(board.getIDNode(i));
        }

        assertDoesNotThrow(() ->{
            player.buildSettlement(board.getIDNode(4));
        });

    }

    @Test
    void tooManyBuilds(){
        Agent player = new HumanAgent(1);
        Board board = new Board();
        //Each player only gets 5 settlements, so they cannot build a 6th one
        for(int i = 0; i<5; i++){
            player.buildSettlement(board.getIDNode(i));
        }

        assertThrowsExactly(IllegalStateException.class, () ->{
            player.buildSettlement(board.getIDNode(5));
        });

    }

    @Test
    void playerWithColour(){
        Agent player = new HumanAgent(1);
        assertEquals("RED", player.getColourName());
    }
}
