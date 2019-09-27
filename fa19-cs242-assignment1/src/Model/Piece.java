package Model;

public class Piece {
    protected int row, col, playerNumber;
    protected String name;

    public Piece(int row, int col, int playerNumber){
        this.row = row;
        this.col = col;
        this.playerNumber = playerNumber;
        this.name = "General";
    }

    /*==========================UTILITY FUNCTIONS=======================*/
    public int getRow(){
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
    public String getPieceName(){
        return name;
    }
    public void setPieceName(String name){
        this.name = name;
    }
    public int getPlayerNumber(){
        return playerNumber;
    }
    public String pieceToString(){
        return "" + row + "," + col + " " + name + "-" + playerNumber;
    }
    public void printPiece(){
        System.out.println(pieceToString());
    }
    /*
    public void setPlayerNumber(int playerNumber){
        this.playerNumber = playerNumber;
    }
     */




    /*=======================Is this Move Valid?===========================*/

    /**
     * Used in all Pieces.
     *
     * Various fundamental move tests are performed:
     *
     * {@link #isLocationInBound(Board, int, int)}
     *
     * {@link #isTargetLocationSameAsCurrentLocation(int, int)}
     *
     * {@link #isTileOccupiedByFriendlyPiece(Board, int, int)}
     *
     * {@link #isValidMoveAvoidingCheck(Board, int, int)}
     *
     *
     * @param board
     * @param targetRow
     * @param targetCol
     * @return true if the move is valid in regard to several fundamental move tests.
     */
    public boolean isValidMove(Board board, int targetRow, int targetCol){
        boolean printErrorMsg = board.getPrintErrorMsg();


        if (!isLocationInBound(board, targetRow, targetCol)){
            if (printErrorMsg){
                System.out.println("Illegal move: out of board bound");
            }
            return false;
        }
        if (isTargetLocationSameAsCurrentLocation(targetRow, targetCol)){
            if (printErrorMsg){
                System.out.println("Illegal move: staying at current position");
            }
            return false;
        }

        if (isTileOccupiedByFriendlyPiece(board, targetRow, targetCol)){
            if (printErrorMsg) {
                System.out.println("Illegal move: you cant eat your own piece, at least shouldn't");
            }
            return false;
        }

        if (!isValidMoveAvoidingCheck(board, targetRow, targetCol)){
            if (printErrorMsg){
                System.out.println("Illegal move: moving the piece will result in your king being in Check");
            }
            return false;
        }

        return true;
    }



    /**
     * Used in all Pieces.
     *
     * Check if the move will put your King in Check.
     *
     * The function will move current Piece into target coordinate and see if your King is in Check,
     * then call moveBack() to undo the move.
     *
     * @param board
     * @param targetRow
     * @param targetCol
     * @return true if the move will NOT put your King in Check (aka move is legal).
     */
    public boolean isValidMoveAvoidingCheck(Board board, int targetRow, int targetCol){
        if (playerNumber != board.getPlayerTurn()){
            return true; /* for this is not my turn, so wherever i move i wont get myself in check; i simply cant move */
        }

        int currRow = row;
        int currCol = col;
        boolean result = true;
        Tile currTile = board.getTile(currRow, currCol);
        Tile targetTile = board.getTile(targetRow, targetCol);
        Piece targetPiece = targetTile.getPiece();

        targetTile.moveIn(board, this);
        currTile.moveOut();

        if (board.isInCheck()){
            result = false;
        }
        targetTile.moveBack(board, targetPiece, currRow, currCol);
        return result;
    }


    /**
     * Used in Model.Pieces.Bishop, Model.Pieces.Queen.
     *
     * For Piece that can move diagonally, with no blocking Piece in path.
     *
     * @param board
     * @param targetRow
     * @param targetCol
     * @return true if the Piece can move to target coordinate according to its basic movement rules (diagonal).
     */
    public boolean isValidMoveDiagonal(Board board, int targetRow, int targetCol){
        boolean printErrorMsg = board.getPrintErrorMsg();

        /* test is moving diagonally */
        int rowDiff = Math.abs(row - targetRow);
        int colDiff = Math.abs(col - targetCol);
        if (rowDiff != colDiff){
            if (printErrorMsg){
                System.out.println("Forbidden move: movement is not purely diagonal");
            }
            return false;
        }

        /* test if way is blocked by checking each tile on the diagonal line between row, col and targetRow, targetCol
         */
        int rowMin = Math.min(row, targetRow);
        int colMin = Math.min(col, targetCol);
        Tile[][] tiles = board.getTiles();
        for (int increment = 1; increment < rowDiff; increment ++){
            Tile testTile = tiles[rowMin + increment][colMin + increment];
            if (testTile.isOccupied()){
                if (printErrorMsg){
                    System.out.println("Forbidden move: your diagonal movement is blocked by " + testTile.tileToString());
                }
                return false;
            }
        }
        return true;
    }


    /**
     * Used in Model.Pieces.Rook, Model.Pieces.Queen, Model.Pieces.Catapult.
     *
     * For Piece that can move horizontally / vertically, with no blocking Piece in path.
     *
     * @param board
     * @param targetRow
     * @param targetCol
     * @return true if the Piece can move to target coordinate according to its basic movement rules (horizontal /vertical)
     *
     */
    public boolean isValidMoveHorizontalVertical(Board board, int targetRow, int targetCol){
        boolean printErrorMsg = board.getPrintErrorMsg();

        /* test if moving only along one axis */
        int rowDiff = Math.abs(row - targetRow);
        int colDiff = Math.abs(col - targetCol);
        if (rowDiff * colDiff != 0){
            if (printErrorMsg) {
                System.out.println("Forbidden move: movement is not purely horizontal or vertical");
            }
            return false;
        }
        Tile[][] tiles = board.getTiles();
        /* test for blocking pieces on the horizontal / vertical direction */
        if (rowDiff > 0){
            int minRow = Math.min(row, targetRow);
            for (int increment = 1; increment < rowDiff; increment ++){
                Tile testTile = tiles[minRow + increment][col];
                if (testTile.isOccupied()){
                    if (printErrorMsg) {
                        System.out.println("Forbidden move: horizontal movement is blocked by " + testTile.tileToString());
                    }
                    return false;
                }
            }
        }
        else{
            int minCol = Math.min(col, targetCol);
            for (int increment = 1; increment < colDiff; increment ++){
                Tile testTile = tiles[row][minCol + increment];
                if (testTile.isOccupied()){
                    if (printErrorMsg) {
                        System.out.println("Forbidden move: vertical movement is blocked by" + testTile.tileToString());
                    }
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Catapult can move horizontally / vertically like Rook, when it is not capturing.
     * When trying to capture, one (and only one) Piece needs to be in between Catapult and the target.
     *
     * @param board
     * @param targetRow
     * @param targetCol
     * @return true if Catapult can move to target coordinate according to its basic movement rules.
     *
     *
     */
    public boolean isValidMoveCatapult(Board board, int targetRow, int targetCol){
        int rowDiff = row - targetRow;
        int colDiff = col - targetCol;
        Piece targetPiece = board.getTile(targetRow, targetCol).getPiece();
        if (targetPiece != null && targetPiece.getPlayerNumber() != playerNumber) {
            int midPieceCount = 0;
            if (row == targetRow) {
                int minCol = Math.min(col, targetCol);
                int maxCol = Math.max(col, targetCol);
                for (int midCol = minCol + 1; midCol < maxCol; midCol++) {
                    Piece midPiece = board.getTile(row, midCol).getPiece();
                    if (midPiece != null) {
                        midPieceCount ++;
                    }
                }
            } else if (col == targetCol) {
                int minRow = Math.min(row, targetRow);
                int maxRow = Math.max(row, targetRow);
                for (int midRow = minRow + 1; midRow < maxRow; midRow++) {
                    Piece midPiece = board.getTile(midRow, col).getPiece();
                    if (midPiece != null) {
                        midPieceCount ++;
                    }
                }
            }
            return midPieceCount == 1;
        }
        else{
            return isValidMoveHorizontalVertical(board, targetRow, targetCol);
        }
    }

    /**
     * Blinker can move to the neighbor square (4 Cardinal, 4 Intercardinal) of a friendly Piece.
     *
     * @param board
     * @param targetRow
     * @param targetCol
     * @return true if Blinker can move to target coordinate according to its basic movement rules.
     *
     */
    public boolean isValidMoveBlinker(Board board, int targetRow, int targetCol){
        Piece targetPiece = board.getTile(targetRow, targetCol).getPiece();
        if (targetPiece != null){ /* cannot move to tile occupied by either a friendly piece or an enemy piece*/
            return false;
        }

        return neighborTilesScan(board, targetRow, targetCol, 1);
    }

    /**
     *
     * @param board
     * @param targetRow
     * @param targetCol
     * @return true if target position in inside board boundary.
     */
    private boolean isLocationInBound(Board board, int targetRow, int targetCol){
        return targetRow >= 0 && targetRow < board.getWidth() && targetCol >= 0 && targetCol < board.getHeight();
    }

    /**
     *
     * @param targetRow
     * @param targetCol
     * @return true if target position is the same as current position.
     */
    private boolean isTargetLocationSameAsCurrentLocation(int targetRow, int targetCol){
        return row == targetRow && col == targetCol;
    }

    /**
     *
     *
     * @param targetRow
     * @param targetCol
     * @return true if target coordinate is different from current coordinate (aka. the Piece does not stay in current position
     * for the move).
     */
    private boolean isTileOccupiedByFriendlyPiece(Board board, int targetRow, int targetCol){
        Piece targetPiece = board.getTile(targetRow, targetCol).getPiece();
        if (targetPiece != null && targetPiece.getPlayerNumber() == playerNumber){
            return true;
        }
        return false;
    }

    /**
     *
     * @param board
     * @param targetRow
     * @param targetCol
     * @return true if target coordinate is occupied by a Piece
     */
    private boolean isTileOccupiedByPiece(Board board, int targetRow, int targetCol){
        Piece targetPiece = board.getTile(targetRow, targetCol).getPiece();
        if (targetPiece != null) {
            return true;
        }
        return false;
    }

    /**
     * mode = 0: true if at least one neighbor tile of the target coordinate is empty.
     *
     * mode = 1: true if at least one neighbor tile of the target coordinate
     * is occupied by a friendly Piece (other than the Blinker itself).
     *
     * @param board
     * @param targetRow
     * @param targetCol
     * @param mode 0 or 1
     * @return boolean depending on mode
     */
    private boolean neighborTilesScan(Board board, int targetRow, int targetCol, int mode){
        int[] neighborRows = new int[]{targetRow - 1, targetRow, targetRow + 1, targetRow - 1, targetRow + 1, targetRow - 1, targetRow, targetRow + 1};
        int[] neighborCols = new int[]{targetCol - 1, targetCol - 1, targetCol - 1, targetCol, targetCol, targetCol + 1, targetCol + 1, targetCol + 1};
        for (int i = 0; i < 8; i ++) {
            int neighborRow = neighborRows[i];
            int neighborCol = neighborCols[i];
            if (!isLocationInBound(board, neighborRow, neighborCol)){
                continue;
            }
            if (mode == 0 && !isTileOccupiedByPiece(board, neighborRow, neighborCol)){
                return true;
            }
            if (mode == 1 && isTileOccupiedByFriendlyPiece(board, neighborRow, neighborCol) && !(neighborRow == row && neighborCol == col)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Used in all Pieces.
     *
     * Generic function overridden by each individual Piece.
     *
     * @param board
     * @return true if Piece has any valid move.
     */
    public boolean hasValidMove(Board board){
        return true;
    }

    /**
     * Used in Model.Pieces.Bishop, Model.Pieces.Queen.
     *
     * @param board
     * @return true if the Piece has valid diagonal move.
     */
    public boolean hasValidMoveDiagonal(Board board){
        Tile currTile = board.getTile(row, col);
        int boardLength = Math.min(board.getWidth(), board.getHeight());
        for (int i = (-1) * boardLength; i < boardLength; i ++){
            int targetRow = row + i;
            int targetColUp = col + i;
            int targetColDown = col - i;
            if (isValidMove(board, targetRow, targetColUp) || (isValidMove(board, targetRow, targetColDown))){
                return true;
            }
        }
        return false;
    }

    /**
     * Used in Model.Pieces.Rook, Model.Pieces.Queen, Model.Pieces.Catapult
     *
     * @param board
     * @return true if the Piece has valid horizontal / diagonal move.
     */
    public boolean hasValidMoveHorizontalVertical(Board board){
        Tile currTile = board.getTile(row, col);
        int boardWidth = board.getWidth();
        int boardHeight = board.getHeight();
        for (int i = 0; i < boardWidth; i ++){
            if (isValidMove(board, i, col)){
                return true;
            }
        }
        for (int i = 0; i < boardHeight; i ++){
            if (isValidMove(board, row, i)){
                return true;
            }
        }
        return false;
    }

    /**
     * Used in Model.Pieces.Knight
     *
     * @param board
     * @return true if the Piece has valid knight jump move.
     */
    public boolean hasValidMoveKnightJump(Board board){
        Tile currTile = board.getTile(row, col);
        int[] targetRowsLong = new int[]{row - 2, row + 2};
        int[] targetRowsShort = new int[]{row - 1, row + 1};
        int[] targetColsLong = new int[]{col - 2, col + 2};
        int[] targetColsShort = new int[]{col - 1, col + 1};
        if (hasValidMoveInGivenRowColArrays(board, targetRowsLong, targetColsShort)) return true;
        if (hasValidMoveInGivenRowColArrays(board, targetRowsShort, targetColsLong)) return true;
        return false;
    }

    /**
     * Used in Model.Pieces.Catapult
     *
     * @param board
     * @return true if the Piece has valid Catapult move.
     */
    public boolean hasValidMoveCatapult(Board board){
        int boardWidth = board.getWidth();
        int boardHeight = board.getHeight();
        for (int i = 0; i < boardWidth; i ++){
            if (this.isValidMove(board, i, col)){
                return true;
            }
        }
        for (int i = 0; i < boardHeight; i ++){
            if (this.isValidMove(board, row, i)){
                return true;
            }
        }
        return false;
    }


    /**
     * Used in Model.Pieces.Blinker
     *
     * @param board
     * @return true if the Piece has valid Blinker move.
     */
    public boolean hasValidMoveBlinker(Board board){
        int boardWidth = board.getWidth();
        int boardHeight = board.getHeight();
        for (int iterRow = 0; iterRow < boardWidth; iterRow ++){
            for (int iterCol = 0; iterCol < boardHeight; iterCol ++){
                if (isValidMoveBlinker(board, iterRow, iterCol)){
                    return true;
                }
            }
        }
        return false;

    }


    /**
     *
     * @param board
     * @return true if the Piece has valid move to target locations specified in targetRows and targetCols
     */
    public boolean hasValidMoveInGivenRowColArrays(Board board, int[] targetRows, int[] targetCols) {
        for (int targetRow : targetRows) {
            for (int targetCol : targetCols) {
                if (this.isValidMove(board, targetRow, targetCol)) {
                    return true;
                }
            }
        }
        return false;
    }


}

