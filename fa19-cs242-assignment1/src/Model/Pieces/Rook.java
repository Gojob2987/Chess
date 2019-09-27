package Model.Pieces;

import Model.Board;
import Model.Piece;

/**
 *
 * Rook: horizontal / vertical move (i.e. row == targetRow || col == targetCol) on unblocked path.
 */
public class Rook extends Piece {
    public Rook(int row, int col, int playerNumber){
        super(row, col, playerNumber);
        setPieceName("Rook");
    }

    @Override
    public boolean isValidMove(Board board, int targetRow, int targetCol) {
        if (!super.isValidMove(board, targetRow, targetCol)){
            return false;
        }
        return isValidMoveHorizontalVertical(board, targetRow, targetCol);
    }
    @Override
    public boolean hasValidMove(Board board){
        return hasValidMoveHorizontalVertical(board);
    }
}
