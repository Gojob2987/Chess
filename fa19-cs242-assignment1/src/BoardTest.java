import java.awt.Color;
import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.BeforeClass;
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
        List<Piece> piecesPlayer1 = testBoard.getPlayerPieces(0);
        testBoard.printPlayerPieces(piecesPlayer1);
        List<Piece> piecesPlayer2 = testBoard.getPlayerPieces(1);
        testBoard.printPlayerPieces(piecesPlayer2);
    }






    @Test
    public void testMovePiece(){
        testBoard.printBoard();
        Piece testPawn0 = testBoard.getTile(1, 1).getPiece();
        Piece testPawn1 = testBoard.getTile(6, 1).getPiece();

        testBoard.movePiece(2, 1, 3, 1); /* this should print error message and return **/


        testBoard.movePiece(testPawn0.getRow(), testPawn0.getCol(), testPawn0.getRow() + 2, testPawn0.getCol());
        testBoard.printBoard();
        assertNotEquals(testBoard.getPlayerTurn(), testPawn0.getPlayerNumber());


        testBoard.movePiece(6, 1, 4, 1);
        testBoard.printBoard();
        assertNotEquals(testBoard.getPlayerTurn(), testPawn1.getPlayerNumber());


        assertTrue(testPawn0.isValidMove(testBoard, 4, 1));
        testBoard.movePiece(3, 1, 4, 1);
        testBoard.printBoard();
    }



    public void testMovePieceCapture(){
        testBoard.printBoard();

    }
}
