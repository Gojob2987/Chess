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

    /* Check test for all pieces (aka will this move result in me being inCheck) */
    public boolean isValidMove(Board board, int targetRow, int targetCol){
        boolean printErrorMsg = board.getPrintErrorMsg();

        if (targetRow < 0 || targetRow >= board.getWidth() || targetCol < 0 || targetCol >= board.getHeight()){
            if (printErrorMsg){
                System.out.println("Illegal move: out of board bound");
            }
            return false;
        }
        if (row == targetRow && col == targetCol){
            if (printErrorMsg){
                System.out.println("Illegal move: staying at current position");
            }
            return false;
        }

        Tile targetTile = board.getTile(targetRow, targetCol);
        Piece targetPiece = targetTile.getPiece();
        if (targetPiece != null && (playerNumber == targetPiece.getPlayerNumber())){
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


    /* used in Piece.Bishop and Piece.Queen, and other long ranged unit moving diagonally*/
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


    /* used in Piece.Rook and Piece.Queen, and other long ranged unit moving horizontally / vertically */
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



    /*=======================Do I have Valid Move?===========================*/
    public boolean hasValidMoveToEscapeCheck(Board board){
        return true;
    }

    /* used in Piece.Bishop and Piece.Queen, and other long ranged unit moving diagonally*/
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

    /* used in Piece.Rook and Piece.Queen, and other long ranged unit moving horizontally / vertically */
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

    /* used in Piece.Knight*/
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

    public boolean hasValidMoveInGivenRowColArrays(Board board, int[] targetRows, int[] targetColsShort) {
        for (int targetRow : targetRows) {
            for (int targetCol : targetColsShort) {
                if (this.isValidMove(board, targetRow, targetCol)) {
                    return true;
                }
            }
        }
        return false;
    }


    /*==========================Children of Piece=======================*/
    public static class Pawn extends Piece{

        public Pawn (int row, int col, int playerNumber){
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
        public boolean hasValidMoveToEscapeCheck(Board board){
            return hasValidMoveDiagonal(board);
        }
    }

    public static class Bishop extends Piece{
        public Bishop(int row, int col, int playerNumber){
            super(row, col, playerNumber);
            setPieceName("Bishop");
        }

        @Override
        public boolean isValidMove(Board board, int targetRow, int targetCol) {
            if (!super.isValidMove(board, targetRow, targetCol)){
                return false;
            }
            return isValidMoveDiagonal(board, targetRow, targetCol);
        }

        @Override
        public boolean hasValidMoveToEscapeCheck(Board board){
            return hasValidMoveDiagonal(board);
        }
    }

    public static class Rook extends Piece{
        public Rook(int row, int col, int playerNumber){
            super(row, col, playerNumber);
            setPieceName("Rook");
        }

        @Override
        public boolean isValidMove(Board board, int targetRow, int targetCol) {
            if (!super.isValidMove(board, targetRow, targetCol)){
                return false;
            }
            return isValidMoveHorizontalVertical(board, targetRow, targetCol);
        }
        @Override
        public boolean hasValidMoveToEscapeCheck(Board board){
            return hasValidMoveHorizontalVertical(board);
        }
    }

    public static class King extends Piece{
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

        @Override
        public boolean hasValidMoveToEscapeCheck(Board board){
            Tile currTile = board.getTile(row, col);
            int[] targetRows = new int[]{row - 1, row, row + 1};
            int[] targetCols = new int[]{col - 1, col, col + 1};
            if (hasValidMoveInGivenRowColArrays(board, targetRows, targetCols)) {return true;}
            return false;
        }
    }


    public static class Queen extends Piece {
        public Queen(int row, int col, int playerNumber) {
            super(row, col, playerNumber);
            setPieceName("Queen");
        }

        @Override
        public boolean isValidMove(Board board, int targetRow, int targetCol) {
            if (!super.isValidMove(board, targetRow, targetCol)) {
                return false;
            }
            return isValidMoveDiagonal(board, targetRow, targetCol) || isValidMoveHorizontalVertical(board, targetRow, targetCol);
        }

        @Override
        public boolean hasValidMoveToEscapeCheck(Board board) {
            return hasValidMoveDiagonal(board) && hasValidMoveHorizontalVertical(board);
        }
    }

    public static class Knight extends Piece{
        public Knight (int row, int col, int playerNumber){
            super(row, col, playerNumber);
            setPieceName("Knight");
        }

        @Override
        public boolean isValidMove(Board board, int targetRow, int targetCol) {
            if (!super.isValidMove(board, targetRow, targetCol)){
                return false;
            }
            int rowDiff = Math.abs(row - targetRow);
            int colDiff = Math.abs(col - targetCol);
            if ((rowDiff == 1 && colDiff == 2) || (rowDiff == 2 && colDiff == 1)){
                return true;
            }
            return false;
        }

        @Override
        public boolean hasValidMoveToEscapeCheck(Board board){
            return hasValidMoveKnightJump(board);
        }
    }

}

