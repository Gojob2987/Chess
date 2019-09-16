public class King extends Piece{
    public King (int row, int col, int playerNumber){
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
}
