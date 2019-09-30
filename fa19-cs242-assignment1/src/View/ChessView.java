package View;

import Control.ChessController.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.net.URL;


/** Sample code from: https://wiki.illinois.edu/wiki/display/cs242/Assignment+1.1
 *
 * Standard Chess Pieces icon:
 * By en:User:Cburnett - File:Chess bdt45.svg, CC BY-SA 3.0, https://commons.wikimedia.org/w/index.php?curid=20363776
 * By en:User:Cburnett - File:Chess blt45.svg, CC BY-SA 3.0, https://commons.wikimedia.org/w/index.php?curid=20363777
 * By en:User:Cburnett - File:Chess kdt45.svg, CC BY-SA 3.0, https://commons.wikimedia.org/w/index.php?curid=20363778
 * By en:User:Cburnett - File:Chess klt45.svg, CC BY-SA 3.0, https://commons.wikimedia.org/w/index.php?curid=20363779
 * By en:User:Cburnett - File:Chess ndt45.svg, CC BY-SA 3.0, https://commons.wikimedia.org/w/index.php?curid=20363780
 * By en:User:Cburnett - File:Chess nlt45.svg, CC BY-SA 3.0, https://commons.wikimedia.org/w/index.php?curid=20363781
 * By en:User:Cburnett - File:Chess pdt45.svg, CC BY-SA 3.0, https://commons.wikimedia.org/w/index.php?curid=20363782
 * By en:User:Cburnett - File:Chess plt45.svg, CC BY-SA 3.0, https://commons.wikimedia.org/w/index.php?curid=20363783
 * By en:User:Cburnett - File:Chess qdt45.svg, CC BY-SA 3.0, https://commons.wikimedia.org/w/index.php?curid=20363784
 * By en:User:Cburnett - File:Chess qlt45.svg, CC BY-SA 3.0, https://commons.wikimedia.org/w/index.php?curid=20363785
 * By en:User:Cburnett - File:Chess rdt45.svg, CC BY-SA 3.0, https://commons.wikimedia.org/w/index.php?curid=20363786
 * By en:User:Cburnett - File:Chess rlt45.svg, CC BY-SA 3.0, https://commons.wikimedia.org/w/index.php?curid=20363775
 *
 * Catapult-black Icon:
 * <div>Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/"
 *   title="Flaticon">www.flaticon.com</a></div>
 *
 * Catapult-white Icon:
 * <div>Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/"
 *  title="Flaticon">www.flaticon.com</a></div>
 *
 * Blinker-black Icon:
 * https://dev.bukkit.org/projects/advanced-randomtp/files/2719636
 *
 * Blinker-white Icon:
 * https://game-icons.net/1x1/lorc/transportation-rings.html
 *
 *
 * */

public class ChessView implements ActionListener{

    private static ImageIcon BishopDark = createImageIcon("../Asset/Chess_bdt60.png");
    private static ImageIcon PawnDark = createImageIcon("../Asset/Chess_pdt60.png");
    private static ImageIcon KingDark = createImageIcon("../Asset/Chess_kdt60.png");
    private static ImageIcon KnightDark = createImageIcon("../Asset/Chess_ndt60.png");
    private static ImageIcon QueenDark = createImageIcon("../Asset/Chess_qdt60.png");
    private static ImageIcon RookDark = createImageIcon("../Asset/Chess_rdt60.png");
    private static ImageIcon CatapultDark = createImageIcon("../Asset/catapult_b.png");
    private static ImageIcon BlinkerDark = createImageIcon("../Asset/blinker_b.png");
    private static ImageIcon BishopLight = createImageIcon("../Asset/Chess_blt60.png");
    private static ImageIcon PawnLight = createImageIcon("../Asset/Chess_plt60.png");
    private static ImageIcon KingLight = createImageIcon("../Asset/Chess_klt60.png");
    private static ImageIcon KnightLight = createImageIcon("../Asset/Chess_nlt60.png");
    private static ImageIcon QueenLight = createImageIcon("../Asset/Chess_qlt60.png");
    private static ImageIcon RookLight = createImageIcon("../Asset/Chess_rlt60.png");
    private static ImageIcon CatapultLight = createImageIcon("../Asset/catapult_w.png");
    private static ImageIcon BlinkerLight = createImageIcon("../Asset/blinker_w.png");


    private static JFrame gameWindow;
    private static JPanel mainPanel;
    private static JPanel utilityPanel;
    private static JPanel boardPanel;
    private static JButton[][] boardButtons;
    private static JButton gameStartButton;
    private static JButton gameForfeitButton;
    private static JButton gameStartWithSpecialUnitButton;
    private static JButton showScoreButton;
    private static JButton showPlayerTurnButton;
    private static JButton showTotalTurnButton;
    private static JButton undoMoveButton;


    public ChessView(){
        initView("normal");
    }

    public static void initView(String gameMode){
        gameWindow = new JFrame("Chess GUI");
        gameWindow.setSize(1024, 1024);

        initUtilityComponents();
        initMainPanel();
        initUtilityPanel();
        initBoardPanel();

        switch (gameMode){
            case "normal":
                initBoardButtonsNormal();
                break;
            case "special":
                initBoardButtonsWithSpecialUnit();
                break;
            default:
                System.out.println("Unknown game mode " + gameMode);
                return;
        }

        mainPanel.add(utilityPanel, BorderLayout.EAST);
        mainPanel.add(boardPanel);
        /* setUpMenu(gameWindow); */
        gameWindow.setContentPane(mainPanel);
        gameWindow.setVisible(true);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void resetView(String gameMode){
        if (gameWindow.getContentPane() != null) {
            gameWindow.getContentPane().removeAll();
        }
        initView(gameMode);
        gameWindow.revalidate();
    }

    public static JButton getBoardButton(int row, int col){
        return boardButtons[row][col];
    }


    public static void spawnShowScoreWindow(int player0Score, int player1Score){
        JFrame scoreWindow = new JFrame();
        scoreWindow.setSize(150, 150);
        JPanel scorePanel = new JPanel();
        JLabel scoreLabel0 = new JLabel("Player0 score: " + player0Score);
        JLabel scoreLabel1 = new JLabel("Player1 score: " + player1Score);
        scorePanel.add(scoreLabel0);
        scorePanel.add(scoreLabel1);
        scoreWindow.setContentPane(scorePanel);
        scoreWindow.setVisible(true);
        scoreWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void spawnGameOverWindow(String gameOverTypeString, int playerTurn){
        JFrame gameOverWindow = new JFrame();
        gameOverWindow.setSize(300, 300);
        JPanel gameOverPanel = new JPanel();
        JLabel gameOverStatementLabel = new JLabel("Game Over! " + gameOverTypeString);
        JLabel gameOverResultLabel = new JLabel();
        switch (gameOverTypeString){
            case "Checkmate":
                gameOverResultLabel.setText("Winner: Player" + (1 - playerTurn));
                break;
            case "Stalemate":
                gameOverResultLabel.setText("Draw: Stalemate");
                break;
            case "Forfeit":
                gameOverResultLabel.setText("Winner: Player" + (1 - playerTurn) + " due to opponent forfeit.");
                break;
            default:
                gameOverResultLabel.setText("Unknown end game condition (neither Checkmate nor Stalemate");
        }
        gameOverPanel.add(gameOverStatementLabel);
        gameOverPanel.add(gameOverResultLabel);
        gameOverWindow.setContentPane(gameOverPanel);
        gameOverWindow.setVisible(true);
        gameOverWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }


    public static void setShowTurnButtons(int playerTurn, int totalTurn){
        String playerTurnColorString = (playerTurn == 0) ? "B" : "W";
        showPlayerTurnButton.setText("Current Turn: " + playerTurn + " (" + playerTurnColorString + ")");
        showTotalTurnButton.setText("Total Turn: " + totalTurn);
    }

    private static void initBoardButtonsPaintBackground(){
        boardButtons = new JButton[8][8];
        for (int i = 0; i < 8; i ++){
            for (int j = 0; j < 8; j ++){
                JButton boardButton = new JButton();
                if (i % 2 == j % 2){
                    boardButton.setBackground(Color.WHITE);
                }
                else{
                    boardButton.setBackground(Color.GRAY);
                }
                boardButton.setOpaque(true);
                boardButton.setBorderPainted(false);
                boardButtons[i][j] = boardButton;
                boardButton.setActionCommand("" + i + "," + j);
                boardButton.setText("");
                boardButton.addActionListener(new MovePieceListener());
                boardPanel.add(boardButton);
            }
        }
    }

    private static void initBoardButtonsNormal(){
        initBoardButtonsPaintBackground();
        for (int col = 0; col < 8; col ++) {
            boardButtons[1][col].setIcon(PawnDark);
            boardButtons[6][col].setIcon(PawnLight);
        }
        boardButtons[0][0].setIcon(RookDark);
        boardButtons[0][1].setIcon(KnightDark);
        boardButtons[0][2].setIcon(BishopDark);
        boardButtons[0][3].setIcon(KingDark);
        boardButtons[0][4].setIcon(QueenDark);
        boardButtons[0][5].setIcon(BishopDark);
        boardButtons[0][6].setIcon(KnightDark);
        boardButtons[0][7].setIcon(RookDark);
        boardButtons[7][0].setIcon(RookLight);
        boardButtons[7][1].setIcon(KnightLight);
        boardButtons[7][2].setIcon(BishopLight);
        boardButtons[7][3].setIcon(KingLight);
        boardButtons[7][4].setIcon(QueenLight);
        boardButtons[7][5].setIcon(BishopLight);
        boardButtons[7][6].setIcon(KnightLight);
        boardButtons[7][7].setIcon(RookLight);
    }

    private static void initBoardButtonsWithSpecialUnit(){
        initBoardButtonsNormal();
        boardButtons[0][0].setIcon(CatapultDark);
        boardButtons[0][2].setIcon(BlinkerDark);
        boardButtons[0][5].setIcon(BlinkerDark);
        boardButtons[0][7].setIcon(CatapultDark);
        boardButtons[7][0].setIcon(CatapultLight);
        boardButtons[7][2].setIcon(BlinkerLight);
        boardButtons[7][5].setIcon(BlinkerLight);
        boardButtons[7][7].setIcon(CatapultLight);

    }

    /** Sample code from:
     * https://docs.oracle.com/javase/tutorial/uiswing/components/icon.html
     *
     * and for resizing:
     *  https://stackoverflow.com/questions/2856480/resizing-a-imageicon-in-a-jbutton
     * */
    protected static ImageIcon createImageIcon(String path){
        URL imgURL = ChessView.class.getResource(path);
        if (imgURL != null){
            ImageIcon icon = new ImageIcon(imgURL);
            Image img = icon.getImage();
            Image newImg = img.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            return new ImageIcon(newImg);
        }
        else{
            System.out.println("File at " + path + " could not be found");
            return null;
        }
    }


    private static JPanel initMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(1024,1024));
        mainPanel.setLayout(new BorderLayout());
        return mainPanel;
    }

    private static void initUtilityComponents(){
        gameStartButton = new JButton("Start/Restart (Normal)");
        gameStartWithSpecialUnitButton = new JButton("Start/Restart (Special)");
        gameForfeitButton = new JButton("Forfeit");
        showScoreButton = new JButton("Display Player Scores");
        undoMoveButton = new JButton("Undo");
        showPlayerTurnButton = new JButton("Current Player Turn:");
        showTotalTurnButton = new JButton("Total Turn:");

        gameStartButton.addActionListener(new GameRestartListener());
        gameForfeitButton.addActionListener(new GameForfeitListener());
        gameStartWithSpecialUnitButton.addActionListener(new GameStartWithSpecialUnitListener());
        showScoreButton.addActionListener(new ShowScoreListener());
        undoMoveButton.addActionListener(new UndoMoveListener());
    }


    private static JPanel initUtilityPanel(){
        utilityPanel = new JPanel();
        utilityPanel.setLayout(new BoxLayout(utilityPanel, BoxLayout.Y_AXIS));
        utilityPanel.setPreferredSize(new Dimension(300,1024));
        utilityPanel.add(gameStartButton);
        utilityPanel.add(gameStartWithSpecialUnitButton);
        utilityPanel.add(gameForfeitButton);
        utilityPanel.add(showScoreButton);
        utilityPanel.add(undoMoveButton);
        utilityPanel.add(showPlayerTurnButton);
        utilityPanel.add(showTotalTurnButton);
        return utilityPanel;
    }

    private static JPanel initBoardPanel(){
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(0, 8));
        return boardPanel;
    }


/*
    private static void setUpMenu(JFrame window) {
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem open = new JMenuItem("Open");
        open.addActionListener(this);
        file.add(open);
        menubar.add(file);
        window.setJMenuBar(menubar);
    }

 */



    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null,
                "I was clicked by "+e.getActionCommand(),
                "Title here", JOptionPane.INFORMATION_MESSAGE);
    }



    public static void main(String[] args) {
        new ChessView();
    }
}
