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
    public void testValidConstructor1(){
        Board testBoard = new Board("normal");
        testBoard.printBoard();
    }

    @Test
    public void testValidConstructor2(){
        Board testBoard = new Board("beast mode!");
        testBoard.printBoard();
    }

    @Test
    public void testPlayerPiecesAfterInitialization(){
        Board testBoard = new Board("normal");
        List<Piece> piecesPlayer1 = testBoard.getPlayerPieces(1);
        testBoard.printPlayerPieces(piecesPlayer1);
        List<Piece> piecesPlayer2 = testBoard.getPlayerPieces(2);
        testBoard.printPlayerPieces(piecesPlayer2);
    }

    @Test
    public void testInitTileNormal(){

    }
}
