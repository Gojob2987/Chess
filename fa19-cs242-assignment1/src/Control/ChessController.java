package Control;

import Model.*;
import View.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * Really helpful link for a basic ActionListener implemented in MVC pattern
 * https://www.leepoint.net/GUI/structure/40mvc.html
 *
 * "Game Logic" design example
 * https://www3.ntu.edu.sg/home/ehchua/programming/java/J8d_Game_Framework.html
 */


public class ChessController {
    private static Board board;
    private static ChessView view;
    private static Color sourceColor;
    private static Icon sourceIcon;
    private static int targetRow, targetCol, sourceRow, sourceCol;
    private static int player0Score = 0;
    private static int player1Score = 0;

    public int getPlayer0Score(){return player0Score;}
    public void setPlayer0Score(int score){player0Score = score;}
    public int getPlayer1Score(){return player1Score;}
    public void setPlayer1Score(int score){player1Score = score;}

    public enum GameState {
        INITIALIZED, PLAYING, PAUSED, SELECT_SOURCE, SELECT_TARGET, GAMEOVER;
    }

    public static GameState state;


    public ChessController() {
        board = new Board("normal");
        view = new ChessView();
        state = GameState.INITIALIZED;
    }

    /**
     * Can try to start an individual game thread here in furture
     */
    public static void gameStart() {
        state = GameState.SELECT_SOURCE;
        player0Score = player1Score = 0;
        view.setShowTurnButton(board.getPlayerTurn());
    }

    public static void updateValidMoveOnView() {
        JButton targetButton = view.getBoardTileButton(targetRow, targetCol);
        targetButton.setIcon(sourceIcon);

        JButton sourceButton = view.getBoardTileButton(sourceRow, sourceCol);
        sourceButton.setBackground(sourceColor);
        sourceButton.setIcon(null);

        targetRow = targetCol = sourceRow = sourceCol = 0;
        view.setShowTurnButton(board.getPlayerTurn());
    }


    public static void updateInvalidMoveOnView() {
        JButton sourceButton = view.getBoardTileButton(sourceRow, sourceCol);
        sourceButton.setBackground(sourceColor);
        sourceButton.setIcon(sourceIcon);
        targetRow = targetCol = sourceRow = sourceCol = 0;
    }


    public static class GameStartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            gameStart();
        }
    }

    public static class ShowScoreListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.spawnShowScoreWindow(player0Score, player1Score);
        }
    }

    public static class MovePieceListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton pieceButton = (JButton) e.getSource();
            String buttonCoordinateString = pieceButton.getActionCommand();
            String[] buttonCoordinate = buttonCoordinateString.split(",");
            int row = Integer.parseInt(buttonCoordinate[0]);
            int col = Integer.parseInt(buttonCoordinate[1]);

            /*
            String buttonText = pieceButton.getText();
            switch (buttonText) {
                case "":
                    pieceButton.setText(buttonCoordinateString);
                    break;
                default:
                    pieceButton.setText("");
            }

             */

            switch (state) {
                case SELECT_SOURCE:
                    sourceRow = row;
                    sourceCol = col;
                    JButton sourceButton = view.getBoardTileButton(sourceRow, sourceCol);
                    sourceColor = sourceButton.getBackground();
                    sourceIcon = sourceButton.getIcon();
                    sourceButton.setBackground(Color.CYAN);
                    state = GameState.SELECT_TARGET;
                    break;

                case SELECT_TARGET:
                    targetRow = row;
                    targetCol = col;

                    int oldPlayerTurn = board.getPlayerTurn();
                    board.movePieceByPosition(sourceRow, sourceCol, targetRow, targetCol);
                    int newPlayerTurn = board.getPlayerTurn();

                    if (oldPlayerTurn != newPlayerTurn) {
                        updateValidMoveOnView();
                    } else {
                        updateInvalidMoveOnView();
                    }

                    state = GameState.SELECT_SOURCE;
                    break;

                default:
                    /*
                    System.out.println("current GameState is " + state);
                     */
            }

            if (board.isCheckmate()) {
                gameOver("Checkmate");
                switch (board.getPlayerTurn()){ /* current player is Checked by last player*/
                    case 0:
                        player1Score += 1;
                        break;
                    case 1:
                        player0Score += 1;
                        break;
                    default:
                        System.out.println("Unknown player number has won: " + board.getPlayerTurn());
                }
            } else if (board.isStalemate()) {
                gameOver("Stalemate");
            }

        }
    }

    public static void gameOver(String gameOverString) {
        state = GameState.GAMEOVER;
        int playerTurn = board.getPlayerTurn();
        view.spawnGameOverWindow(gameOverString, playerTurn);
    }


}
