package Model.Pieces;

import Model.Board;
import Model.Piece;

/**
 *
 * Bishop: diagonal move (i.e. rowDiff == colDiff) on unblocked path.
 *
 */

public class Bishop extends Piece {
    public Bishop(int row, int col, int playerNumber){
        super(row, col, playerNumber);
        setPieceName("Bishop");
    }

    @Override
    public boolean isValidMove(Board board, int targetRow, int targetCol) {
        if (!super.isValidMove(board, targetRow, targetCol)){
            return false;
        }
        return isValidMoveDiagonal(board, targetRow, targetCol);
    }

    @Override
    public boolean hasValidMove(Board board){
        return hasValidMoveDiagonal(board);
    }
}
