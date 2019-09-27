package Model.Pieces;

import Model.Board;
import Model.Piece;

/**
 *
 * Knight: can jump in "L" shape (i.e. rowDiff == 2 && colDiff == 1   ||   rowDiff == 1 && colDiff == 2).
 *
 */
public class Knight extends Piece {
    public Knight(int row, int col, int playerNumber){
        super(row, col, playerNumber);
        setPieceName("Knight");
    }

    @Override
    public boolean isValidMove(Board board, int targetRow, int targetCol) {
        if (!super.isValidMove(board, targetRow, targetCol)){
            return false;
        }
        int rowDiff = Math.abs(row - targetRow);
        int colDiff = Math.abs(col - targetCol);
        if ((rowDiff == 1 && colDiff == 2) || (rowDiff == 2 && colDiff == 1)){
            return true;
        }
        return false;
    }

    @Override
    public boolean hasValidMove(Board board){
        return hasValidMoveKnightJump(board);
    }
}
