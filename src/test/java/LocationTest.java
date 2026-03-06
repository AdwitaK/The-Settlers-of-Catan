import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LocationTest {

    @Test
    void checkNodeOccupation(){
        Location node = new Node(5);
        node.setOccupied();
        boolean status = node.isOccupied();
        assertTrue(status);
    }

    @Test
    void checkEdgeOccupation(){
        Location edge = new Edge(2, 3);
        boolean status = edge.isOccupied();
        assertFalse(status);
    }
}
