package Control;

import Model.*;
import View.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Really helpful link
 * https://www.leepoint.net/GUI/structure/40mvc.html
 */

public class ChessController {
    private static Board board;
    private static ChessView view;

    public ChessController(Board board, ChessView view){
        this.board = board;
        this.view = view;

        view.addGameStartListener(new GameStartListener());
        view.addShowScoreListener(new ShowScoreListener());

    }


    class GameStartListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            board.gameStart();
        }
    }

    class ShowScoreListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int player0Score = board.getPlayer0Score();
            int player1Score = board.getPlayer1Score();
            view.showScoreWindow(player0Score, player1Score);
        }
    }


}
