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
        if (row < 0 || row > board.getWidth() || col < 0 || col > board.getHeight()){
            /* System.out.println("Illegal move: out of board bound"); */
            return false;
        }
        if (row == targetRow && col == targetCol){
            /* System.out.println("Illegal move: staying at current position"); */
            return false;
        }

        Tile currTile = board.getTile(targetRow, targetCol);
        Piece currPiece = currTile.getPiece();
        if (currPiece == null){
            /* System.out.println("Illegal move: no piece at currently selected location"); */
            return false;
        }

        Tile targetTile = board.getTile(targetRow, targetCol);
        Piece targetPiece = targetTile.getPiece();
        if (targetPiece != null && (currPiece.getPlayerNumber() == targetPiece.getPlayerNumber())){
            /* System.out.println("Illegal move: you cant eat your own piece, at least shouldn't"); */
            return false;
        }

        if (!isValidMoveAvoidingCheck(board, targetRow, targetCol)){
            /* System.out.println("Illegal move: moving the piece will result in your king being in Check"); */
            return false;
        }

        return true;
    }

    public boolean isValidMoveAvoidingCheck(Board board, int targetRow, int targetCol){
        int currRow = row;
        int currCol = col;
        Tile currTile = board.getTile(currRow, currCol);
        Tile targetTile = board.getTile(targetRow, targetCol);
        Piece targetPiece = targetTile.getPiece();

        targetTile.moveIn(board, this);
        currTile.moveOut();
        if (board.isInCheck()){
            targetTile.moveBack(board, targetPiece, currRow, currCol);
            return false;
        }
        return true;
    }


    /* used in Piece.Bishop and Piece.Queen, and other long ranged unit moving diagonally*/
    public boolean isValidMoveDiagonal(Board board, int targetRow, int targetCol){

        /* test is moving diagonally */
        int rowDiff = Math.abs(row - targetRow);
        int colDiff = Math.abs(col - targetCol);
        if (rowDiff != colDiff){
            System.out.println("Forbidden move: movement is not purely diagonal");
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
                System.out.println("Forbidden move: your diagonal movement is blocked by" + testTile.tileToString());
                return false;
            }
        }
        return true;
    }


    /* used in Piece.Rook and Piece.Queen, and other long ranged unit moving horizontally / vertically */
    public boolean isValidMoveHorizontalVertical(Board board, int targetRow, int targetCol){

        /* test if moving only along one axis */
        int rowDiff = Math.abs(row - targetRow);
        int colDiff = Math.abs(col - targetCol);
        if (rowDiff * colDiff != 0){
            System.out.println("Forbidden move: movement is not purely horizontal or vertical");
            return false;
        }
        Tile[][] tiles = board.getTiles();
        /* test for blocking pieces on the horizontal / vertical direction */
        if (rowDiff > 0){
            int minRow = Math.min(row, targetRow);
            for (int increment = 1; increment < rowDiff; increment ++){
                Tile testTile = tiles[minRow + increment][col];
                if (testTile.isOccupied()){
                    System.out.println("Forbidden move: horizontal movement is blocked by" + testTile.tileToString());
                    return false;
                }
            }
        }
        else{
            int minCol = Math.min(col, targetCol);
            for (int increment = 1; increment < colDiff; increment ++){
                Tile testTile = tiles[row][minCol + increment];
                if (testTile.isOccupied()){
                    System.out.println("Forbidden move: vertical movement is blocked by" + testTile.tileToString());
                    return false;
                }
            }
        }

        return true;
    }



    /*=======================Do I have Valid Move?===========================*/
    public boolean hasValidMoveForPiece(Board board){
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
            if (isValidMove(board, targetRow, targetColUp) && (isValidMove(board, targetRow, targetColDown))){
                return true;
            }
        }
        return false;
    }

    /* used in Piece.Rook and Piece.Queen, and other long ranged unit moving horizontally / vertically */
    public boolean hasValidMoveHorizontal(Board board){
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
        for (int targetRow : targetRowsLong){
            for (int targetCol : targetColsShort){
                if (isValidMove(board, targetRow, targetCol)){
                    return true;
                }
            }
        }
        for (int targetRow : targetRowsShort){
            for (int targetCol : targetColsLong){
                if (isValidMove(board, targetRow, targetCol)){
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
            setPieceName("Piece.Pawn");
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
                    valid = valid && (!tiles[row + 1][col].isOccupied());
                } else if (playerNumber == 1 && row == 6) {
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

        @Override
        public boolean hasValidMoveForPiece(Board board){
            return hasValidMoveDiagonal(board);
        }
    }

    public static class Bishop extends Piece{
        public Bishop(int row, int col, int playerNumber){
            super(row, col, playerNumber);
            setPieceName("Piece.Bishop");
        }

        @Override
        public boolean isValidMove(Board board, int targetRow, int targetCol) {
            if (!super.isValidMove(board, targetRow, targetCol)){
                return false;
            }
            return isValidMoveDiagonal(board, targetRow, targetCol);
        }

        @Override
        public boolean hasValidMoveForPiece(Board board){
            return hasValidMoveDiagonal(board);
        }
    }

    public static class Rook extends Piece{
        public Rook(int row, int col, int playerNumber){
            super(row, col, playerNumber);
            setPieceName("Piece.Rook");
        }

        @Override
        public boolean isValidMove(Board board, int targetRow, int targetCol) {
            if (!super.isValidMove(board, targetRow, targetCol)){
                return false;
            }
            return isValidMoveHorizontalVertical(board, targetRow, targetCol);
        }
        @Override
        public boolean hasValidMoveForPiece(Board board){
            return hasValidMoveHorizontal(board);
        }
    }

    public static class King extends Piece{
        public King (int row, int col, int playerNumber){
            super(row, col, playerNumber);
            setPieceName("Piece.King");
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
        public boolean hasValidMoveForPiece(Board board){
            Tile currTile = board.getTile(row, col);
            int[] targetRows = new int[]{row - 1, row, row + 1};
            int[] targetCols = new int[]{col - 1, col, col + 1};
            for (int targetRow : targetRows){
                for (int targetCol : targetCols){
                    if (isValidMove(board, targetRow, targetCol)){
                        return true;
                    }
                }
            }
            return false;
        }
    }


    public static class Queen extends Piece {
        public Queen(int row, int col, int playerNumber) {
            super(row, col, playerNumber);
            setPieceName("Piece.Queen");
        }

        @Override
        public boolean isValidMove(Board board, int targetRow, int targetCol) {
            if (!super.isValidMove(board, targetRow, targetCol)) {
                return false;
            }
            return isValidMoveDiagonal(board, targetRow, targetCol) && isValidMoveHorizontalVertical(board, targetRow, targetCol);
        }

        @Override
        public boolean hasValidMoveForPiece(Board board) {
            return hasValidMoveDiagonal(board) && hasValidMoveHorizontal(board);
        }
    }

    public static class Knight extends Piece{
        public Knight (int row, int col, int playerNumber){
            super(row, col, playerNumber);
            setPieceName("Piece.Knight");
        }

        @Override
        public boolean isValidMove(Board board, int targetRow, int targetCol) {
            if (!super.isValidMove(board, targetRow, targetCol)){
                return false;
            }
            int rowDiff = Math.abs(row - targetRow);
            int colDiff = Math.abs(col - targetCol);
            if (!(rowDiff == 1 && colDiff == 2) || !(rowDiff == 2 && colDiff == 1)){
                return false;
            }
            return true;
        }

        @Override
        public boolean hasValidMoveForPiece(Board board){
            return hasValidMoveKnightJump(board);
        }
    }

}

