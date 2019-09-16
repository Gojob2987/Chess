import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;



public class PieceTest {
    protected Board testBoard = new Board("normal");

    @Test
    public void MoveTestStayAtCurrentPosition(){
        testBoard.printBoard();
        Piece testPawn = testBoard.getTiles()[1][0].getPiece();
        System.out.println(testPawn.pieceToString());
        assertFalse(testPawn.isValidMove(testBoard, testPawn.getRow(), testPawn.getCol()));
    }
}
