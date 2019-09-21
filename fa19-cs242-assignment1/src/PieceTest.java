import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;



public class PieceTest {
    protected Board testBoard = new Board("normal");


    @Test
    public void testInvalidMoveStayAtCurrentPosition(){
        /* testBoard.printBoard(); */
        Piece testPawn = testBoard.getTiles()[1][0].getPiece();
        /* System.out.println(testPawn.pieceToString()); */
        assertFalse(testPawn.isValidMove(testBoard, testPawn.getRow(), testPawn.getCol()));
    }

    @Test
    public void testInvalidMoveOutOfBound(){
        Piece testPawn = testBoard.getTiles()[1][0].getPiece();
        /* System.out.println(testPawn.pieceToString()); */
        assertFalse(testPawn.isValidMove(testBoard, testBoard.getWidth(), testBoard.getHeight()));
        assertFalse(testPawn.isValidMove(testBoard, -1, testPawn.getCol() + 1));
        assertFalse(testPawn.isValidMove(testBoard, testPawn.getRow(), -1));
    }

    @Test
    public void testInvalidMoveLandOnFriend(){
        Piece testRook = testBoard.getTile(0, 0).getPiece();
        /* System.out.println(testRook.pieceToString()); */
        assertFalse(testRook.isValidMove(testBoard, testRook.getRow(), testRook.getCol() + 1));
        assertFalse(testRook.isValidMove(testBoard, testRook.getRow() + 1, testRook.getCol()));

    }

    @Test
    public void testMovePawnAtInitialPosition(){
        /* testBoard.printBoard(); */
        Piece testPawn0 = testBoard.getTile(1, 1).getPiece();
        /* System.out.println(testPawn0.pieceToString()); */
        assertTrue(testPawn0.isValidMove(testBoard, testPawn0.getRow() + 1, testPawn0.getCol()));
        assertFalse(testPawn0.isValidMove(testBoard, testPawn0.getRow() + 3, testPawn0.getCol()));
        assertTrue(testPawn0.isValidMove(testBoard, testPawn0.getRow() + 2, testPawn0.getCol() ));
        assertFalse(testPawn0.isValidMove(testBoard, testPawn0.getRow(), testPawn0.getCol() + 1));
    }


    @Test
    public void testInvalidMovePawnAtNormalPosition(){
    }

    @Test
    public void testValidMovePawnAtNormalPosition(){
    }

    @Test
    public void testInvalidMoveKnight(){
    }

    @Test
    public void testValidMoveKnight(){
    }

}
