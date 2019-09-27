package Model.Pieces;

import Model.Board;
import Model.Piece;
import Model.Tile;

/**
 *
 * King: can move to 8 neighbor locations (4 cardinal, 4 intercardinal).
 *
 */
public class King extends Piece {
    public King(int row, int col, int playerNumber){
        super(row, col, playerNumber);
        setPieceName("King");
    }

    @Override
    public boolean isValidMove(Board board, int targetRow, int targetCol) {
        if (!super.isValidMove(board, targetRow, targetCol)){
            return false;
        }
        int rowDiff = Math.abs(row - targetRow);
        int colDiff = Math.abs(col - targetCol);
        if (rowDiff > 1 || colDiff > 1){
            return false;
        }

        return true;
    }

    @Override
    public boolean hasValidMove(Board board){
        Tile currTile = board.getTile(row, col);
        int[] targetRows = new int[]{row - 1, row, row + 1};
        int[] targetCols = new int[]{col - 1, col, col + 1};
        return hasValidMoveInGivenRowColArrays(board, targetRows, targetCols);
    }
}
