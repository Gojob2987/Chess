import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;



public class PieceTest {
    protected Board testBoard = new Board("normal");

    @Test
    public void InvalidMoveStayAtCurrentPosition(){
        /* testBoard.printBoard(); */
        Piece testPawn = testBoard.getTiles()[1][0].getPiece();
        System.out.println(testPawn.pieceToString());
        assertFalse(testPawn.isValidMove(testBoard, testPawn.getRow(), testPawn.getCol()));
    }

    @Test
    public void InvalidMoveOutOfBound(){
        Piece testPawn = testBoard.getTiles()[1][0].getPiece();
        System.out.println(testPawn.pieceToString());
        assertFalse(testPawn.isValidMove(testBoard, testBoard.getWidth(), testBoard.getHeight()));
        assertFalse(testPawn.isValidMove(testBoard, -1, testPawn.getCol() + 1));
        assertFalse(testPawn.isValidMove(testBoard, testPawn.getRow(), -1));
    }

    @Test
    public void InvalidMoveLandOnFriend(){
        Piece testRook = testBoard.getTiles()[0][0].getPiece();
        System.out.println(testRook.pieceToString());
        assertFalse(testRook.isValidMove(testBoard, testRook.getRow(), testRook.getCol() + 1));
        assertFalse(testRook.isValidMove(testBoard, testRook.getRow() + 1, testRook.getCol()));


    }

}
