package Model.Pieces;

import Model.Board;
import Model.Piece;

/**
 *
 * Blinker: can teleport to the neighbor tile of a friendly Piece other than self.
 *
 * Cannot capture!
 *
 * */
public class Blinker extends Piece {
    public Blinker(int row, int col, int playerNumber){
        super(row, col, playerNumber);
        setPieceName("Blinker");
    }

    @Override
    public boolean isValidMove(Board board, int targetRow, int targetCol) {
        if (!super.isValidMove(board, targetRow, targetCol)) {
            return false;
        }
        return isValidMoveBlinker(board, targetRow, targetCol);
    }

    @Override
    public boolean hasValidMove(Board board){
        return hasValidMoveBlinker(board);
    }


}
