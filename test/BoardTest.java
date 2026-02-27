import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    @Test
    void checkHexTileResourceProduced(){
        Board board = new Board();
        HexTile[] hextiles = board.getResourceProdTile(10);
        HexTile firstTile = hextiles[0];
        HexTile secondTile = hextiles[1];
        assertEquals(ResourceType.LUMBER, firstTile.getResourceType());
        assertEquals(ResourceType.WOOL, secondTile.getResourceType());
    }
}
