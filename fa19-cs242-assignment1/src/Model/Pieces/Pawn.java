package Model.Pieces;

import Model.Board;
import Model.Piece;
import Model.Tile;

/**
 *
 * Pawn: can move forward 2 tiles from initial location, or only 1 tile after initial location.
 *
 * Can capture enemy Piece at the 2 forward, immediate intercardinal locations.
 */
public class Pawn extends Piece {

    public Pawn(int row, int col, int playerNumber){
        super(row, col, playerNumber);
        setPieceName("Pawn");
    }

    @Override
    public boolean isValidMove(Board board, int targetRow, int targetCol) {
        if (!super.isValidMove(board, targetRow, targetCol)){
            return false;
        }
        int rowDiff = Math.abs(row - targetRow);
        int colDiff = Math.abs(col - targetCol);
        Tile[][] tiles = board.getTiles();
        boolean valid = true;

        /* test if way is blocked (only possible at inital 2 steps move) */
        if (rowDiff == 2) {
            if (playerNumber == 0 && row == 1) {
                valid = !tiles[row + 1][col].isOccupied() && colDiff == 0;
            } else if (playerNumber == 1 && row == 6) {
                valid = !tiles[row - 1][col].isOccupied() && colDiff == 0;
            } else return false; /* 2 steps move is only possible at initial position, row = 1 or 6*/
        }
        else {
            if (playerNumber == 0) {
                valid = (targetRow - row) == 1;
            }
            else{
                valid = (row - targetRow) == 1;
            }
        }

        if (col != targetCol){
            valid = valid && (colDiff == 1) && tiles[targetRow][targetCol].isOccupied(); /* can only move diagonally to capture */
        }


        return valid;
    }

    @Override
    public boolean hasValidMove(Board board){
        int[] targetRows = new int[]{row - 2, row - 1, row, row + 1, row + 2};
        int[] targetCols = new int[]{col - 1, col, col + 1};
        return hasValidMoveInGivenRowColArrays(board, targetRows, targetCols);
    }
}
