import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private int width = 0;
    private int height = 0;
    private static Tile[][] tiles;
    private static int playerTurn; /* this is used interchangeably with player name in various printing*/
    private static int totalTurn;
    private static boolean printErrorMsg = false;
    private static boolean gameover;
    private static List<Piece> player0Pieces;
    private static List<Piece> player1Pieces;

    public Board(String boardMode){
        if ("normal".equals(boardMode)) {
            initBoardNormal();
        }
        else {
            System.out.println("Requested board shape: " + boardMode + " unimplemented");
            return;
        }

    /*==========================UTILITY FUNCTIONS=======================*/
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
    public int getTotalTurn() {return totalTurn;}
    public void setTotalTurn(int totalTurn) {this.totalTurn = totalTurn;}
    public boolean getPrintErrorMsg() {return printErrorMsg;}
    public void setPrintErrorMsg(boolean printErrorMsg) {this.printErrorMsg = printErrorMsg;}
    public Tile[][] getTiles(){
        return tiles;
    }
    public Tile getTile(int targetRow, int targetCol){
        return tiles[targetRow][targetCol];
    }
    public int getPlayerTurn(){
        return playerTurn;
    }
    public void changePlayerTurn(){
        playerTurn = (playerTurn + 1) % 2;
    }
    public void setGameover(){
        gameover = true;
        System.out.println("player " + playerTurn + " has defeated their foe, congratulation!");
        /* do something to break the game loop */
    }

    public List<Piece> getPlayerPieces(int playerNumber){
        if (playerNumber == 0){
            return player0Pieces;
        }
        else{
            return player1Pieces;
        }
    }

    public void printPlayerPieces(int playerNumber){
        List<Piece> playerPieces = this.getPlayerPieces(playerNumber);
        for (Piece piece : playerPieces){
            System.out.print(piece.pieceToString() + " ");
        }
        System.out.println();
    }

    public int getPieceCount(int playerNumber, String pieceName){
        List<Piece> playerPieces = this.getPlayerPieces(playerNumber);
        int pieceCount = 0;
        if (playerPieces != null) { /* if it is null game is already over tbh */
            for (Piece playerPiece : playerPieces){
                if (playerPiece.getPieceName().equals(pieceName)){
                    pieceCount ++;
                }
            }
        }
        return pieceCount;
    }

    public void printBoard(){
        if (tiles == null) {
            System.out.println("board is not initialized, call one of the initBoard() functions first");
            return;
        }
        System.out.println("Total Turns (from 0) " + totalTurn + "; Current Player Turn: " + playerTurn);
        for (int row = 0; row < width; row ++){
            for (int col = 0; col < height; col ++){
                System.out.print(tiles[row][col].tileToString() + " ");
            }
            System.out.println();
        }
    }

    /*==========================NORMAL MODE INITIALIZATION=======================*/

    public void initBoardNormal() {
        setWidth(8);
        setHeight(8);
        setTotalTurn(0);
        initPlayerPiecesNormal();
        initTilesNormal();
        initPiecesNormal();
        playerTurn = 0;
        gameover = false;
        /*printBoard();*/
    }

    private void initPlayerPiecesNormal(){
        player0Pieces = new ArrayList<>(16);
        player1Pieces = new ArrayList<>(16);
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
        initSpecialPiecesForPlayerAtRowNormal(0, 0, player0Pieces);
        initPawnPiecesForPlayerAtRowNormal(0, 1, player0Pieces);
        initSpecialPiecesForPlayerAtRowNormal(1, 7, player1Pieces);
        initPawnPiecesForPlayerAtRowNormal(1, 6, player1Pieces);
    }

    private void initSpecialPiecesForPlayerAtRowNormal(int playerNumber, int row, List<Piece> playerPieces) {
        Piece.Rook rook1 = new Piece.Rook(row, 0, playerNumber);
        Piece.Knight knight1 = new Piece.Knight(row, 1, playerNumber);
        Piece.Bishop bishop1 = new Piece.Bishop(row, 2, playerNumber);
        Piece.Queen queen = new Piece.Queen(row, 3, playerNumber);
        Piece.King king = new Piece.King(row, 4, playerNumber);
        Piece.Bishop bishop2 = new Piece.Bishop(row, 5, playerNumber);
        Piece.Knight knight2 = new Piece.Knight(row, 6, playerNumber);
        Piece.Rook rook2 = new Piece.Rook(row, 7, playerNumber);

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
    private void initPawnPiecesForPlayerAtRowNormal(int playerNumber, int row, List<Piece> playerPieces) {
        for (int col = 0; col < width; col++) {
            Piece.Pawn pawn = new Piece.Pawn(row, col, playerNumber);
            tiles[row][col].setPiece(pawn);
            playerPieces.add(pawn);
        }
    }


    /*==========================ENDGAME CHECK=======================*/

    /*
    Checkmate condition: isInCheck() + no legal move
     */
    public boolean isInCheck(){
        if (playerTurn == 0){
            return amIBeingChecked(player0Pieces, player1Pieces);
        }
        else{
            return amIBeingChecked(player1Pieces, player0Pieces);
        }
    }

    private boolean amIBeingChecked(List<Piece> myPieces, List<Piece> enemyPieces){
        Piece myKing = myPieces.stream()
                .filter(piece -> "King"
                        .equals(piece.getPieceName())).findAny().orElse(null);

        int targetRow = myKing.getRow();
        int targetCol = myKing.getCol();
        for (Piece enemyPiece : enemyPieces){
            if (enemyPiece.isValidMove(this, targetRow, targetCol)){
                return true;
            }
        }
        return false;
    }

    /* King is in Check and there is no legal move*/
    public boolean isCheckmate(){
        return isInCheck() && !hasValidMoveForPieces();
    }


    public boolean isStalemate(){
        return (!isInCheck()) && !hasValidMoveForPieces();
    }

    public boolean hasValidMoveForPieces(){
        List<Piece> myPieces = (playerTurn == 0) ? player0Pieces : player1Pieces;
        for (Piece myPiece : myPieces){
            if (myPiece.hasValidMoveToEscapeCheck(this)){
                return true;
            }
        }
        return false;

    }


    /*==========================HOW A SINGLE TURN IS DONE=======================*/

    public void movePieceByPosition(int currRow, int currCol, int targetRow, int targetCol){
        if (isCheckmate()){
            System.out.println("Player " + playerTurn + "lost due to Checkmate");
            this.setGameover();
            return;
        }
        if (isStalemate()){
            System.out.println("A draw occurs due to Stalemate");
            this.setGameover();
            return;
        }


        Tile currTile = tiles[currRow][currCol];
        Tile targetTile = tiles[targetRow][targetCol];
        Piece currPiece = currTile.getPiece();

        if (currPiece == null){
            System.out.println("No piece at selected tile!");
            return;
        }
        if (currPiece.getPlayerNumber() != playerTurn){
            System.out.println("Current playerTurn is " + playerTurn + ", not " + currPiece.getPlayerNumber());
            return;
        }

        if (!currPiece.isValidMove(this, targetRow, targetCol)){
            return;
        }

        /* the actual moveIn (to targetTile) and moveOut (from currTile)
        *  piece removal is done in Tile.moveIn()
        */
        targetTile.moveIn(this, currPiece);
        currTile.moveOut();

        /* change player turn */
        changePlayerTurn();
        setTotalTurn(totalTurn + 1);

    }

    public void movePieceByPiece(Piece myPiece, int targetRow, int targetCol){
        if (myPiece == null) {
            System.out.println("Please select the Piece you want to move");
            return;
        }
        movePieceByPosition(myPiece.getRow(), myPiece.getCol(), targetRow, targetCol);
    }


}
