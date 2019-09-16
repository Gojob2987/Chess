public class Rook extends Piece{
    public Rook(int row, int col, int playerNumber){
        super(row, col, playerNumber);
        setPieceName("Rook");
    }
    public boolean isValidMove(Board board, int targetRow, int targetCol) {
        if (!super.isValidMove(board, targetRow, targetCol)) {
            return false;
        }
        return isValidMoveHorizontalVertical(board, targetRow, targetCol);
    }
}
