import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;






public class BoardTest {

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
    public void testValidMovePiece(){

    }
}
