public class Pawn extends Piece{

    public Pawn (int row, int col, int playerNumber){
        super(row, col, playerNumber);
        setPieceName("Pawn");
    }

    @Override
    public boolean isValidMove(Board board, int targetRow, int targetCol) {
        if (!super.isValidMove(board, targetRow, targetCol)) {
            return false;
        }

        int rowDiff = Math.abs(row - targetRow);
        int colDiff = Math.abs(col - targetCol);
        Tile[][] tiles = board.getTiles();
        boolean valid = true;

        /* test if way is blocked (only possible at inital 2 steps move) */
        if (rowDiff == 2) {
            if (row == 1) {
                valid = valid && (!tiles[row + 1][col].isOccupied());
            } else if (row == 6) {
                valid = valid && (!tiles[row - 1][col].isOccupied());
            } else return false; /* 2 steps move is only possible at initial position, row = 1 or 6*/
        }
        else {
            valid = valid && (rowDiff == 1);
        }


        if (col != targetCol){
            valid = valid && (colDiff < 2);
        }

        return valid;
    }
}
