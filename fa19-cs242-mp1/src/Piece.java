
public class Piece {
    protected int row, col, playerNumber;
    protected String name;

    public Piece(int row, int col, int playerNumber){
        this.row = row;
        this.col = col;
        this.playerNumber = playerNumber;
        this.name = "General";
    }

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
    public String printPiece(){
        return "" + row + "," + col + " " + name + "-" + playerNumber;
    }
    /*
    public void setPlayerNumber(int playerNumber){
        this.playerNumber = playerNumber;
    }
     */

    /* generic tests for all pieces */
    public boolean isValidMove(Board board, int targetRow, int targetCol){
        /*
        if (playerNumber != board.getPlayerTurn()){
            System.out.println("Current turn is for player" + board.getPlayerTurn());
            return false;
        }
         */
        if (row == targetRow && col == targetCol){
            System.out.println("Forbidden move: staying at current position");
            return false;
        }
        if (targetRow < 0 || targetRow >= board.getWidth() || targetCol < 0 || targetCol >= board.getHeight()) {
            System.out.println("Forbidden move: out of board bound");
            return false;
        }
        Tile[][] tiles = board.getTiles();
        if (tiles[targetRow][targetCol].getPiece().getPlayerNumber() == playerNumber){
            System.out.println("Forbidden move: you cant eat your own piece, at least shouldn't");
            return false;
        }
        return true;
    }

    /* used in Bishop and Queen, and other long ranged diagonal moving units */
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
                System.out.println("Forbidden move: your diagonal movement is blocked by" + testTile.printTile());
                return false;
            }
        }
        return true;
    }


    /* used in Rook and Queen, and other long ranged unit moving on only one axis at a time */
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
                    System.out.println("Forbidden move: horizontal movement is blocked by" + testTile.printTile());
                    return false;
                }
            }
        }
        else{
            int minCol = Math.min(col, targetCol);
            for (int increment = 1; increment < colDiff; increment ++){
                Tile testTile = tiles[row][minCol + increment];
                if (testTile.isOccupied()){
                    System.out.println("Forbidden move: vertical movement is blocked by" + testTile.printTile());
                    return false;
                }
            }
        }

        return true;
    }



}

