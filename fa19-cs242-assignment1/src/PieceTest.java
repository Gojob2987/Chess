import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNotEquals;


public class PieceTest {
    protected Board testBoard = new Board("normal");




    @Test
    public void testInvalidMoveStayAtCurrentPosition(){
        Piece testPawn = testBoard.getTiles()[1][0].getPiece();
        assertFalse(testPawn.isValidMove(testBoard, testPawn.getRow(), testPawn.getCol()));
    }

    @Test
    public void testInvalidMoveOutOfBound(){
        Piece testPawn = testBoard.getTiles()[1][0].getPiece();
        assertFalse(testPawn.isValidMove(testBoard, testBoard.getWidth(), testBoard.getHeight()));
        assertFalse(testPawn.isValidMove(testBoard, -1, testPawn.getCol() + 1));
        assertFalse(testPawn.isValidMove(testBoard, testPawn.getRow(), -1));
    }

    @Test
    public void testInvalidMoveLandOnFriend(){
        Piece testRook = testBoard.getTile(0, 0).getPiece();
        assertFalse(testRook.isValidMove(testBoard, testRook.getRow(), testRook.getCol() + 1));
        assertFalse(testRook.isValidMove(testBoard, testRook.getRow() + 1, testRook.getCol()));

    }

    @Test
    public void testMovePawn(){
        Piece testPawn0 = testBoard.getTile(1, 1).getPiece();
        Piece testPawn1 = testBoard.getTile(6, 1).getPiece();
        assertTrue(testPawn0.isValidMove(testBoard, testPawn0.getRow() + 1, testPawn0.getCol()));
        assertFalse(testPawn0.isValidMove(testBoard, testPawn0.getRow() + 3, testPawn0.getCol()));
        assertTrue(testPawn0.isValidMove(testBoard, testPawn0.getRow() + 2, testPawn0.getCol() ));
        assertFalse(testPawn0.isValidMove(testBoard, testPawn0.getRow(), testPawn0.getCol() + 1));

        testBoard.movePieceByPiece(testPawn0, testPawn0.getRow() + 1, testPawn0.getCol());
        testBoard.movePieceByPiece(testPawn1, testPawn1.getRow() - 2, testPawn1.getCol());

        assertFalse(testPawn0.isValidMove(testBoard, testPawn0.getRow() - 1, testPawn0.getCol()));
        testBoard.movePieceByPiece(testPawn0, testPawn0.getRow() + 1, testPawn0.getCol());
        assertFalse(testPawn1.isValidMove(testBoard, testPawn1.getRow() + 1, testPawn1.getCol()));
        assertFalse(testPawn1.isValidMove(testBoard, testPawn1.getRow() + 2, testPawn1.getCol()));

        testBoard.printBoard();
    }





    @Test
    public void testMoveKnight(){
        Piece testKnight0 = testBoard.getTile(0, 1).getPiece();
        assertTrue(testKnight0.isValidMove(testBoard, 2, 2));
        assertTrue(testKnight0.isValidMove(testBoard, 2, 0));
        assertFalse(testKnight0.isValidMove(testBoard, 3, 1));
        assertFalse(testKnight0.isValidMove(testBoard, 3, 2));
    }




    @Test
    public void testMoveRook(){
        Piece testRook0 = testBoard.getTile(0, 0).getPiece();
        Piece testRook1 = testBoard.getTile(7, 0).getPiece();
        setUpClearPathForRookBishop();

        /* illegal move because 5, 0 is occupied by friendly Pawn*/
        assertFalse(testRook1.isValidMove(testBoard, 7, 1));
        /* illegal move, diagonal*/
        assertFalse(testRook1.isValidMove(testBoard, 6, 1));
        /* illegal move, Rook is not a Knight*/
        assertFalse(testRook1.isValidMove(testBoard, 5, 1));
        /* illegal move, path is blocked by enemy Pawn at 5, 0*/
        assertFalse(testRook1.isValidMove(testBoard, 4, 0));

        testBoard.movePieceByPiece(testRook1, 5, 0);
        testBoard.movePieceByPiece(testRook0, 5, 0);
        testBoard.printBoard();


    }


    @Test
    public void testMoveBishop(){
        setUpClearPathForRookBishop();
        Piece testBishop1 = testBoard.getTile(7, 2).getPiece();
        Piece testBishop0 = testBoard.getTile(0, 2).getPiece();

        /* illegal move because 6, 3 is occupied by friendly Pawn*/
        assertFalse(testBishop1.isValidMove(testBoard, 6, 3));
        /* illegal move, path is blocked by enemy Pawn at 6, 3*/
        assertFalse(testBishop1.isValidMove(testBoard, 5, 4));

        testBoard.movePieceByPiece(testBishop1, 5, 0);
        testBoard.movePieceByPosition(1, 3, 2, 3);
        testBoard.movePieceByPiece(testBishop1, 2, 3);
        testBoard.movePieceByPiece(testBishop0, 1, 3);

        /* illegal move vertical*/
        assertFalse(testBishop1.isValidMove(testBoard, 3, 3));
        testBoard.printBoard();

    }

    @Test
    public void testMoveQueen() {
        setUpClearPathForQueen();
        Piece testQueen1 = testBoard.getTile(7, 3).getPiece();
        Piece testQueen0 = testBoard.getTile(0, 3).getPiece();
        /* illegal move because 6, 2 and 7, 4 are occupied by friendly King and Pawn*/
        assertFalse(testQueen1.isValidMove(testBoard, 6, 2));
        assertFalse(testQueen1.isValidMove(testBoard, 7, 4));
        /* illegal move, Queen is not a Knight*/
        assertFalse(testQueen1.isValidMove(testBoard, 5, 2));
        /* illegal move, path is blocked by Pawn at 6, 2*/
        assertFalse(testQueen1.isValidMove(testBoard, 5, 1));

        testBoard.movePieceByPiece(testQueen1, 5, 5);
        testBoard.movePieceByPiece(testQueen0, 7, 3);

        testBoard.printBoard();

    }


    /* set up (move blocking pawns away), do a printBoard() to see what it does*/
    public void setUpClearPathForRookBishop(){
        Piece testPawn0 = testBoard.getTile(1, 0).getPiece();
        Piece testPawn1 = testBoard.getTile(6, 1).getPiece();
        testBoard.movePieceByPiece(testPawn0, testPawn0.getRow() + 2, testPawn0.getCol());
        testBoard.movePieceByPiece(testPawn1, testPawn1.getRow() - 2, testPawn1.getCol());
        testBoard.movePieceByPiece(testPawn0, testPawn0.getRow() + 1, testPawn0.getCol() + 1);
        testPawn1 = testBoard.getTile(6, 0).getPiece();
        testBoard.movePieceByPiece(testPawn1, testPawn1.getRow() - 1, testPawn1.getCol());
        testBoard.movePieceByPiece(testPawn0, testPawn0.getRow() + 1, testPawn0.getCol() - 1);
        /* System.out.println("setUpClearPathForRookBishop() has been called, testBoard now looks like this:");
        testBoard.printBoard(); */
    }

    public void setUpClearPathForQueen(){
        BoardTest.setUpClearPathForCheck(testBoard);
        /* System.out.println("setUpClearPathForQueen() has been called, testBoard now looks like this:");
        testBoard.printBoard(); */
    }
}
