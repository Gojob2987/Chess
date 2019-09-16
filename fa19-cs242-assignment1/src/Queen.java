public class Queen extends Piece{
    public Queen (int row, int col, int playerNumber){
        super(row, col, playerNumber);
        setPieceName("Queen");
    }
    public boolean isValidMove(Board board, int targetRow, int targetCol) {
        if (!super.isValidMove(board, targetRow, targetCol)) {
            return false;
        }
        return isValidMoveDiagonal(board, targetRow, targetCol) && isValidMoveHorizontalVertical(board, targetRow, targetCol);
    }
}
