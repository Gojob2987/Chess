

public class Tile {
    protected int row, col;
    protected Piece piece;

    public Tile(int row, int col, Piece piece){
        this.row = row;
        this.col = col;
        this.piece = piece;
    }

    public int getRow() {
        return row;
    }
    public void setRow(int row){
        this.row = row;
    }
    public int getCol(){
        return col;
    }
    public void setCol(int col){
        this.col = col;
    }
    public Piece getPiece(){
        return piece;
    }
    public void setPiece(Piece piece){
        this.piece = piece;
    }

    public String printTile(){
        String result = row + ", " + col;
        if (piece == null){
            return result;
        }
        return result + " " + piece.getPieceName();
    }

    public boolean isOccupied(){
        return piece != null;
    }

    public void moveIn(Board board, Piece visitor){
        if (this.piece.getPieceName() == "King"){
            board.setGameover();
            return;
        }
        this.piece = visitor;
        visitor.setRow(row);
        visitor.setCol(col);

    }

    public void moveOut(){
        this.piece = null;
    }

}
