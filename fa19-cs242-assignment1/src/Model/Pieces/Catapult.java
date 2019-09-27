package Model.Pieces;

import Model.Board;
import Model.Piece;

/**
 *
 * Catapult: horizontal / vertical move (i.e. row == targetRow || col == targetCol) on unblocked path.
 *
 * when trying to capture, need to have one (and only one) middle Piece as a "stepping stone".
 *
 * */
public class Catapult extends Piece {
    public Catapult(int row, int col, int playerNumber){
        super(row, col, playerNumber);
        setPieceName("Catapult");
    }

    @Override
    public boolean isValidMove(Board board, int targetRow, int targetCol) {
        if (!super.isValidMove(board, targetRow, targetCol)) {
            return false;
        }
        return isValidMoveCatapult(board, targetRow, targetCol);
    }

    @Override
    public boolean hasValidMove(Board board){
        return hasValidMoveCatapult(board);
    }

}
