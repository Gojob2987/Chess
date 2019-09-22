import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotEquals;


public class BoardTest {
    protected Board testBoard = new Board("normal");


    @Test
    public void testValidBoardConstructor1(){
        String boardMode = "normal";
        Board testBoard = new Board(boardMode);
        testBoard.printBoard();
    }

    @Test
    public void testValidBoardConstructor2(){
        String boardMode = "beast mode!";
        Board testBoard = new Board(boardMode);
        testBoard.printBoard();
    }

    @Test
    public void testPlayerPiecesAfterBoardInitialization(){
        String boardMode = "normal";
        Board testBoard = new Board(boardMode);
        testBoard.printPlayerPieces(0);
        testBoard.printPlayerPieces(1);
    }





    @Test
    public void testMovePawnSingleTurn(){
        assertEquals(testBoard.getTotalTurn(), 0);
        assertEquals(testBoard.getPlayerTurn(), 0);
        /* testBoard.printBoard(); */
        Piece testPawn0 = testBoard.getTile(1, 1).getPiece();

        /* illegal move due to no piece at selected position */
        testBoard.movePieceByPosition(2, 0, 3, 1);

        /* illegal move because playerTurn = 0, but piece at target belongs to player1*/
        testBoard.movePieceByPosition(6, 0, 5, 0);
        assertEquals(testBoard.getTotalTurn(), 0);
        assertEquals(testBoard.getPlayerTurn(), 0);


        testBoard.movePieceByPiece(testPawn0, testPawn0.getRow() + 2, testPawn0.getCol());
        assertEquals(testBoard.getTotalTurn(), 1);
        assertEquals(testBoard.getPlayerTurn(), 1);

    }



    @Test
    public void testMovePawnMultipleTurns(){
        assertEquals(testBoard.getTotalTurn(), 0);
        assertEquals(testBoard.getPlayerTurn(), 0);

        Piece testPawn0 = testBoard.getTile(1, 1).getPiece();
        Piece testPawn1 = testBoard.getTile(6, 1).getPiece();

        testBoard.movePieceByPiece(testPawn0, testPawn0.getRow() + 2, testPawn0.getCol());
        testBoard.movePieceByPiece(testPawn1, testPawn1.getRow() - 2, testPawn1.getCol());
        testBoard.movePieceByPiece(testPawn0, 4, 1);

        int player1PawnCount = testBoard.getPieceCount(1, "Pawn");
        assertEquals(player1PawnCount, testBoard.getWidth() - 1);
    }


    @Test
    public void testInCheck(){
        setUpClearPathForCheck(testBoard);
        System.out.println("setUpClearPathForCheck() has been called, testBoard now looks like this:");
        testBoard.printBoard();

        Piece testQueen1 = testBoard.getTile(7, 3).getPiece();
        Piece testQueen0 = testBoard.getTile(0, 3).getPiece();
        testBoard.movePieceByPiece(testQueen1, 5, 5);
        testBoard.movePieceByPiece(testQueen0, 7, 3);

        assertTrue(testBoard.isInCheck());
        Piece testKing1 = testBoard.getTile(7, 4).getPiece();
        Piece testBishop1 = testBoard.getTile(7, 2).getPiece();
        /* the only legal move now is for testKing1 to eat testQueen0*/
        assertFalse(testKing1.isValidMove(testBoard, 6, 4));
        assertFalse(testQueen1.isValidMove(testBoard, 4, 5));
        assertFalse(testBishop1.isValidMove(testBoard, 6, 3));

        testBoard.movePieceByPiece(testKing1, 7, 3);
        assertFalse(testBoard.isInCheck());
        testBoard.printBoard();


        testBoard.movePieceByPosition(1, 0, 3, 0); /* Knight-0 */
        testBoard.movePieceByPosition(5, 5, 6, 4); /* Queen-1 */
        testBoard.movePieceByPosition(5, 4, 6, 4); /* Pawn-0 */
        testBoard.movePieceByPosition(7, 3, 6, 3); /* King-1 */
        testBoard.movePieceByPosition(0, 0, 2, 0); /* Rook-0 */
        testBoard.movePieceByPosition(6, 3, 5, 3); /* King-1 */
        testBoard.movePieceByPosition(2, 0, 2, 4); /* Rook-0 */
        testBoard.movePieceByPosition(5, 3, 4, 3); /* King-1 */
        testBoard.movePieceByPosition(1, 1, 2, 1); /* Pawn-0 */
        testBoard.movePieceByPosition(4, 3, 3, 3); /* King-1 */
        testBoard.movePieceByPosition(1, 2, 2, 2); /* Pawn-0 */
        assertTrue(testKing1.hasValidMove(testBoard));
        testBoard.printBoard();

    }


    public static void setUpClearPathForCheck(Board testBoard) {
        Piece testPawn0 = testBoard.getTile(1, 3).getPiece();
        Piece testPawn1 = testBoard.getTile(6, 3).getPiece();
        testBoard.movePieceByPiece(testPawn0, testPawn0.getRow() + 2, testPawn0.getCol());
        testBoard.movePieceByPiece(testPawn1, testPawn1.getRow() - 2, testPawn1.getCol());
        testBoard.movePieceByPiece(testPawn0, testPawn0.getRow() + 1, testPawn0.getCol());
        testPawn1 = testBoard.getTile(6, 4).getPiece();
        testBoard.movePieceByPiece(testPawn1, testPawn1.getRow() - 1, testPawn1.getCol());
        testBoard.movePieceByPiece(testPawn0, testPawn0.getRow() + 1, testPawn0.getCol() + 1);
    }
}
