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
    private Board board;
    private ChessView view;

    public ChessController(Board board, ChessView view){
        this.board = board;
        this.view = view;

        view.addShowScoreListener(new ShowScoreListener());
    }

    class ShowScoreListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int player0Score = board.getPlayer0Score();
            int player1Score = board.getPlayer1Score();
            String scoreString = "player0 score: " + player0Score + "; player1 score: " + player1Score;
            view.setScore(scoreString);
        }
    }

}
