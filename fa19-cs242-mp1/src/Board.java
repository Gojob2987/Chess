import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Board {
    private int width = 0;
    private int height = 0;
    private static Tile[][] tiles;
    private static int playerTurn; /* this is used interchangeably with player name in various printing*/
    private static boolean gameover;
    private static List<Piece> piecesPlayer1;
    private static List<Piece> piecesPlayer2;

    public Board(String boardMode){
        if ("normal".equals(boardMode)) {
            initBoardNormal();
        }
        else {
            System.out.println("Requested board shape: " + boardMode + " unimplemented");
            return;
        }

    }
    public int getWidth(){
        return width;
    }
    public void setWidth(int width){
        this.width = width;
    }
    public int getHeight(){
        return height;
    }
    public void setHeight(int height){
        this.height = height;
    }
    public Tile[][] getTiles(){
        return tiles;
    }
    public int getPlayerTurn(){
        return playerTurn;
    }
    public void setPlayerTurn(int newPlayerTurn){
        playerTurn = newPlayerTurn;
    }
    public void setGameover(){
        gameover = true;
        System.out.println("player " + playerTurn + " has defeated their foe, congratulation!");
    }

    public List<Piece> getPlayerPieces(int playerNumber){
        if (playerNumber == 1){
            return piecesPlayer1;
        }
        else{
            return piecesPlayer2;
        }
    }

    public void printPlayerPieces(List<Piece> playerPieces){
        for (Piece piece : playerPieces){
            System.out.print(piece.printPiece() + " ");
        }
        System.out.println();
    }
    /*
    public List getPieces(){
        return pieces;
    }
     */

    public void initBoardNormal() {
        setWidth(8);
        setHeight(8);
        initPlayerPiecesNormal();
        initTilesNormal();
        initPiecesNormal();
        playerTurn = 1;
        gameover = false;
        /*printBoard();*/
    }

    public void initPlayerPiecesNormal(){
        piecesPlayer1 = new ArrayList<>(16);
        piecesPlayer2 = new ArrayList<>(16);
    }


    private void initTilesNormal(){
        this.tiles = new Tile[8][8];
        for (int row = 0; row < 8; row ++){
            for (int col = 0; col < 8; col ++){
                tiles[row][col] = new Tile(row, col, null);
            }
        }
    }

    private void initPiecesNormal(){
        initSpecialPiecesNormalForPlayerAtRow(1, 0, piecesPlayer1);
        initPawnPiecesNormalForPlayerAtRow(1, 1, piecesPlayer1);
        initSpecialPiecesNormalForPlayerAtRow(2, 7, piecesPlayer2);
        initPawnPiecesNormalForPlayerAtRow(2, 6, piecesPlayer2);
    }

    private void initSpecialPiecesNormalForPlayerAtRow(int playerNumber, int row, List<Piece> playerPieces) {
        Rook rook1 = new Rook(row, 0, playerNumber);
        Knight knight1 = new Knight(row, 1, playerNumber);
        Bishop bishop1 = new Bishop(row, 2, playerNumber);
        Queen queen = new Queen(row, 3, playerNumber);
        King king = new King(row, 4, playerNumber);
        Bishop bishop2 = new Bishop(row, 5, playerNumber);
        Knight knight2 = new Knight(row, 6, playerNumber);
        Rook rook2 = new Rook(row, 7, playerNumber);

        tiles[row][0].setPiece(rook1);
        tiles[row][1].setPiece(knight1);
        tiles[row][2].setPiece(bishop1);
        tiles[row][3].setPiece(queen);
        tiles[row][4].setPiece(king);
        tiles[row][5].setPiece(bishop2);
        tiles[row][6].setPiece(knight2);
        tiles[row][7].setPiece(rook2);

        playerPieces.addAll(Arrays.asList(rook1, knight1, bishop1, queen, king, bishop2, knight2, rook2));

    }
    private void initPawnPiecesNormalForPlayerAtRow(int playerNumber, int row, List<Piece> playerPieces) {
        for (int col = 0; col < width; col++) {
            Pawn pawn = new Pawn(row, col, playerNumber);
            tiles[row][col].setPiece(pawn);
            playerPieces.add(pawn);
        }
    }

    public boolean isCheckmate(){
        if (playerTurn == 1){
            return isCheckmateToMyFoe(piecesPlayer1, piecesPlayer2);
        }
        else{
            return isCheckmateToMyFoe(piecesPlayer2, piecesPlayer1);
        }
    }

    private boolean isCheckmateToMyFoe(List<Piece> ownPlayerPieces, List<Piece> enemyPlayerPieces){
        Piece enemyKing = enemyPlayerPieces.stream()
                .filter(piece -> "King"
                        .equals(piece.getPieceName())).findAny().orElse(null);
        for (Piece ownPiece : ownPlayerPieces){
            int targetRow = enemyKing.getRow();
            int targetCol = enemyKing.getCol();
            if (ownPiece.isValidMove(this, targetRow, targetCol)) {
                return true;
            }
        }
        return false;
    }

    public void movePiece(int currRow, int currCol, int targetRow, int targetCol){
        if (currRow < 0 || currRow > width || currCol < 0 || currCol > height){
            System.out.println("You cannot move the piece at" + currRow + ", " + currCol + ". it is out of board bound");
            return;
        }
        Tile currTile = tiles[currRow][currCol];
        Piece currPiece = currTile.getPiece();
        if (currPiece == null){
            System.out.println("No piece at selected location" + currRow + ", " + currCol);
            return;
        }
        if (!currTile.getPiece().isValidMove(this, targetRow, targetCol)){
            System.out.println("Invalid move, see why it is forbid in Piece :: isValidMove()");
            return;
        }

        /* the actual moveIn (to targetRow, targetCol) and moveOut(from currRow, currCol) */
        tiles[targetRow][targetCol].moveIn(this, currPiece);
        currTile.moveOut();

        /* check checkmate condition before switching player turn */
        if (isCheckmate()){
            System.out.println(playerTurn + "has Checkmate over the other player, do something!");
        }


        /* change player turn */
        if (getPlayerTurn() == 1){
            setPlayerTurn(2);
        }
        else{
            setPlayerTurn(1);
        }

    }


    public void printBoard(){
        if (tiles == null) {
            System.out.println("board is not initialized, call one of the initBoard() functions first");
            return;
        }
        for (int row = 0; row < width; row ++){
            for (int col = 0; col < height; col ++){
                System.out.print(tiles[row][col].printTile() + " ");
            }
            System.out.println();
        }
    }

}
