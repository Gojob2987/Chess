import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tile {
    protected int row, col;
    protected Piece piece;
    private static final int maxPrintTileLength = 15;

    public Tile(int row, int col, Piece piece){
        this.row = row;
        this.col = col;
        this.piece = piece;
    }

    /*==========================UTILITY FUNCTIONS=======================*/
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

    public String tileToString(){
        String result = row + "," + col;
        if (piece == null){
            return tileToStringPadded(result);
        }
        result += " " + piece.getPieceName() + "-" + piece.getPlayerNumber();
        return tileToStringPadded(result);
    }

    public String tileToStringPadded(String result){
        String paddedString = result;
        int currentLength = result.length();
        for (int i = 0; i < maxPrintTileLength - currentLength; i ++){
            paddedString += " ";
        }
        return paddedString;
    }

    public void printTile(){
        System.out.println(tileToString());
    }

    public boolean isOccupied(){
        return piece != null;
    }

    /*========================CHANGE OF PIECE (MOVE IN / OUT)=======================*/
    public void moveIn(Board board, Piece visitor){
        if (this.piece != null) {
            if (this.piece.getPieceName() == "Piece.King") {
                board.setGameover();
                return;
            }

            int playerNumber = this.piece.getPlayerNumber();
            List<Piece> playerPieces = board.getPlayerPieces(playerNumber);
            playerPieces.remove(this.piece);
        }

        this.piece = visitor;
        visitor.setRow(row);
        visitor.setCol(col);
    }


    public void moveOut(){
        this.piece = null;
    }

    public void moveBack(Board board, Piece removedPiece, int visitorRow, int visitorCol){
        if (removedPiece != null) {
            int playerNumber = removedPiece.getPlayerNumber();
            List<Piece> playerPieces = board.getPlayerPieces(playerNumber);
            playerPieces.add(removedPiece);
        }

        Piece visitor = this.piece;
        visitor.setRow(visitorRow);
        visitor.setCol(visitorCol);
        Tile visitorTile = board.getTile(visitorRow, visitorCol);
        visitorTile.setPiece(visitor);

        this.piece = removedPiece;

    }
}
