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
    private static Icon sourceIcon, validTargetIcon, validSourceIcon;
    private static int targetRow, targetCol, sourceRow, sourceCol, validTargetRow, validTargetCol, validSourceRow, validSourceCol;
    private static boolean canUndo;
    private static Piece validTargetPiece;

    private static int player0Score = 0;
    private static int player1Score = 0;


    public enum GameState {
        INITIALIZED, PLAYING, PAUSED, SELECT_SOURCE, SELECT_TARGET, GAMEOVER;
    }

    public static GameState state;


    public ChessController() {
        gameInit();
    }

    /**
     * Can try to start an individual game thread here in furture
     */
    public static void gameInit() {
        board = new Board("normal");
        view = new ChessView();
        state = GameState.INITIALIZED;
    }


    public static void gameStart() {
        state = GameState.SELECT_SOURCE;
        view.setShowTurnButtons(board.getPlayerTurn(), board.getTotalTurn());
    }

    public static void gameRestart() {
        board = new Board("normal");
        view.resetView("normal");
        gameStart();
    }

    public static void gameRestartWithSpecialUnit() {
        board = new Board("special");
        view.resetView("special");
        gameStart();
    }

    public static void gameOver(String gameOverString) {
        state = GameState.GAMEOVER;
        int playerTurn = board.getPlayerTurn();
        view.spawnGameOverWindow(gameOverString, playerTurn);
        if (gameOverString.equals("Checkmate") || gameOverString.equals("Forfeit")) {
            switch (playerTurn) { /* current player is Checked by last player, or current player Forfeit*/
                case 0:
                    player1Score += 1;
                    break;
                case 1:
                    player0Score += 1;
                    break;
                default:
                    System.out.println("Unknown player number has won: " + (1 - playerTurn));
            }
        }
    }

    public static void gameForfeit() {
        gameOver("Forfeit");
    }

    public static void updateValidMoveOnView() {
        validSourceRow = sourceRow;
        validSourceCol = sourceCol;
        validTargetRow = targetRow;
        validTargetCol = targetCol;
        validSourceIcon = sourceIcon;
        canUndo = true;

        JButton validTargetButton = view.getBoardButton(targetRow, targetCol);
        validTargetIcon = validTargetButton.getIcon();
        validTargetButton.setIcon(sourceIcon);

        JButton sourceButton = view.getBoardButton(sourceRow, sourceCol);
        sourceButton.setBackground(sourceColor);
        sourceButton.setIcon(null);

        view.setShowTurnButtons(board.getPlayerTurn(), board.getTotalTurn());
    }

    /**
     * Undo is only possible before your opponent makes a valid move
     */
    public static void undoValidMoveOnView() {
        JButton validTargetButton = view.getBoardButton(validTargetRow, validTargetCol);
        validTargetButton.setIcon(validTargetIcon);
        JButton validSourceButton = view.getBoardButton(validSourceRow, validSourceCol);
        validSourceButton.setIcon(validSourceIcon);
        view.setShowTurnButtons(board.getPlayerTurn(), board.getTotalTurn());
    }

    public static void undoValidMoveOnBoard() {
        board.getTile(validTargetRow, validTargetCol).moveBack(board, validTargetPiece, validSourceRow, validSourceCol);
        board.setTotalTurn(board.getTotalTurn() - 1);
        board.setPlayerTurn(1 - board.getPlayerTurn());
    }

    public static void updateInvalidMoveOnView() {
        validTargetPiece = null;
        JButton sourceButton = view.getBoardButton(sourceRow, sourceCol);
        sourceButton.setBackground(sourceColor);
        sourceButton.setIcon(sourceIcon);
    }


    public static class GameRestartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            gameRestart();
        }
    }

    public static class GameForfeitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            gameForfeit();
        }
    }

    public static class GameStartWithSpecialUnitListener implements  ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            gameRestartWithSpecialUnit();
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
                    JButton sourceButton = view.getBoardButton(sourceRow, sourceCol);
                    sourceColor = sourceButton.getBackground();
                    sourceIcon = sourceButton.getIcon();
                    sourceButton.setBackground(Color.CYAN);
                    state = GameState.SELECT_TARGET;
                    break;

                case SELECT_TARGET:
                    targetRow = row;
                    targetCol = col;

                    int oldPlayerTurn = board.getPlayerTurn();
                    validTargetPiece = board.getTile(targetRow, targetCol).getPiece(); /* not valid till we test in move*/
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
                    System.out.println("current GameState is " + state + ", you cannot move piece.");
            }

            if (board.isCheckmate()) {
                gameOver("Checkmate");
            } else if (board.isStalemate()) {
                gameOver("Stalemate");
            }

        }
    }

    public static class UndoMoveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (canUndo) {
                undoValidMoveOnBoard();
                undoValidMoveOnView();
            }
            canUndo = false;
        }
    }

}
