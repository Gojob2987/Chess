import Control.ChessController;
import Model.Board;
import View.ChessView;

import javax.swing.*;

public class ChessMVC {
    public static void main(String[] args){
        Board board = new Board("normal");
        ChessView view = new ChessView();
        ChessController controller = new ChessController(board, view);

    }


}
