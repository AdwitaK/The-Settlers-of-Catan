import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HexTileTest {
    @Test
    void checkResourceType(){

        Location[] nodes = {
            new Node(1),
            new Node(2),
            new Node(2),
            new Node(2),
            new Node(2),
            new Node(2),
        };

        Location[] edges = {
            new Edge(1, 1),
            new Edge(2, 2),
            new Edge(2, 3),
            new Edge(2, 3),
            new Edge(2, 5),
            new Edge(2, 1),
        };
        var tileTest = new HexTile(ResourceType.ORE, 1, 5, nodes, edges);
        assertEquals(ResourceType.ORE, tileTest.getResourceType());
    }
}