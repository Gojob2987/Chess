package Model;

import org.junit.Test;

import java.util.List;

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
        assertTrue(testPawn0.hasValidMove(testBoard));

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
        assertTrue(testKnight0.hasValidMove(testBoard));
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

        assertTrue(testRook0.hasValidMove(testBoard));

        testBoard.movePieceByPiece(testRook1, 5, 0);
        testBoard.movePieceByPiece(testRook0, 5, 0);
        testBoard.movePieceByPosition(7, 1, 5, 2);
        testBoard.movePieceByPiece(testRook0, 5, 2);
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

        assertTrue(testBishop1.hasValidMove(testBoard));

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

        assertTrue(testQueen1.hasValidMove(testBoard));

        testBoard.movePieceByPiece(testQueen1, 5, 5);
        testBoard.movePieceByPiece(testQueen0, 7, 3);

        testBoard.printBoard();

    }

    @Test
    public void testMoveCatapult(){
        Piece testCatapult0 = new Piece.Catapult(0, 0, 0);
        setUpInitPiece(testCatapult0);
        assertFalse(testCatapult0.isValidMove(testBoard, 0, 1));
        assertFalse(testCatapult0.isValidMove(testBoard, 2, 0));
        assertTrue(testCatapult0.isValidMove(testBoard, 6, 0));

        testBoard.movePieceByPiece(testCatapult0, 6, 0);
        testBoard.movePieceByPosition(6, 2, 5, 2);
        testBoard.printBoard();

        assertTrue(testCatapult0.hasValidMove(testBoard));
        assertFalse(testCatapult0.isValidMove(testBoard, 6, 2));
        assertFalse(testCatapult0.isValidMove(testBoard, 6, 4));
        assertFalse(testCatapult0.isValidMove(testBoard, 7, 0));
        assertFalse(testCatapult0.isValidMove(testBoard, 7, 1));
        assertFalse(testCatapult0.isValidMove(testBoard, 0, 0));
        assertTrue(testCatapult0.isValidMove(testBoard, 2, 0));
        assertTrue(testCatapult0.isValidMove(testBoard, 6, 3));
    }

    @Test
    public void testMoveBlinker(){
        Piece testBlinker0 = new Piece.Blinker(0, 0, 0);
        setUpInitPiece(testBlinker0);
        assertFalse(testBlinker0.isValidMove(testBoard, 0, 1));
        assertTrue(testBlinker0.isValidMove(testBoard, 2, 1));
        assertTrue(testBlinker0.isValidMove(testBoard, 2, 6));

        testBoard.movePieceByPiece(testBlinker0, 2, 1);
        testBoard.movePieceByPosition(6,1, 4, 1);
        testBoard.printBoard();

        assertTrue(testBlinker0.hasValidMove(testBoard));
        assertFalse(testBlinker0.isValidMove(testBoard, 3, 1));
        assertFalse(testBlinker0.isValidMove(testBoard, 5, 2));
        assertTrue(testBlinker0.isValidMove(testBoard, 0, 0));
        assertTrue(testBlinker0.isValidMove(testBoard, 2, 0));

    }

    /** Set up that adds targetPiece into the game (board and playerPieces),
     * remove the piece from tile at targetPiece's location*/
    public void setUpInitPiece(Piece targetPiece){
        if (targetPiece == null){
            System.out.println("targetPiece cannot be null");
            return;
        }

        int targetRow = targetPiece.getRow();
        int targetCol = targetPiece.getCol();
        int targetPlayerNumber = targetPiece.getPlayerNumber();
        List<Piece> playerPieces = testBoard.getPlayerPieces(targetPlayerNumber);
        playerPieces.removeIf(e -> e.getRow() == targetRow && e.getCol() == targetCol);
        playerPieces.add(targetPiece);
        Tile targetInitTile = testBoard.getTile(targetRow, targetCol);
        targetInitTile.setPiece(targetPiece);
    }



    /** Set up that moves blocking pawns away*/
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
