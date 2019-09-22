package View;

import Model.Piece;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

/** Sample code from: https://wiki.illinois.edu/wiki/display/cs242/Assignment+1.1
 *
 * Chess Pieces icon:
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
 * */

public class ChessGUI implements ActionListener{

    public ChessGUI(){
        JFrame gameWindow = new JFrame("Chessboard GUI");
        gameWindow.setSize(800, 800);
        JButton[][] boardButtons = initializeBoardButtons();
        JPanel mainPanel = initializeMainPanel();
        JPanel boardPanel = initializeBoardPanel();
        /* initializeButton(mainPanel); */
        for (int i = 0; i < 8; i ++){
            for (int j = 0; j < 8; j ++){
                boardPanel.add(boardButtons[i][j]);
            }
        }
        mainPanel.add(boardPanel);
        /* setUpMenu(gameWindow); */
        gameWindow.setContentPane(mainPanel);
        gameWindow.setVisible(true);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /*
    private void initializeButton(JPanel myPanel) {
        JButton startButton = new JButton("Game Start");
        startButton.addActionListener(this);
        myPanel.add(startButton, BorderLayout.EAST);
    }

     */

    private JButton[][] initializeBoardButtons(){
        JButton[][] boardButtons = new JButton[8][8];
        for (int i = 0; i < 8; i ++){
            for (int j = 0; j < 8; j ++){
                JButton tile = new JButton();
                tile.setPreferredSize(new Dimension(64, 64));
                if (i % 2 == j % 2){
                    tile.setBackground(Color.WHITE);
                }
                else{
                    tile.setBackground(Color.GRAY);
                }
                tile.setOpaque(true);
                tile.setBorderPainted(false);
                boardButtons[i][j] = tile;
            }
        }

        ImageIcon BishopDark = createImageIcon("../Asset/Chess_bdt60.png");
        ImageIcon PawnDark = createImageIcon("../Asset/Chess_pdt60.png");
        ImageIcon KingDark = createImageIcon("../Asset/Chess_kdt60.png");
        ImageIcon KnightDark = createImageIcon("../Asset/Chess_ndt60.png");
        ImageIcon QueenDark = createImageIcon("../Asset/Chess_qdt60.png");
        ImageIcon RookDark = createImageIcon("../Asset/Chess_rdt60.png");
        ImageIcon BishopLight = createImageIcon("../Asset/Chess_blt60.png");
        ImageIcon PawnLight = createImageIcon("../Asset/Chess_plt60.png");
        ImageIcon KingLight = createImageIcon("../Asset/Chess_klt60.png");
        ImageIcon KnightLight = createImageIcon("../Asset/Chess_nlt60.png");
        ImageIcon QueenLight = createImageIcon("../Asset/Chess_qlt60.png");
        ImageIcon RookLight = createImageIcon("../Asset/Chess_rlt60.png");

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

        return boardButtons;
    }

    /* Sample code from: https://docs.oracle.com/javase/tutorial/uiswing/components/icon.html*/
    protected  ImageIcon createImageIcon(String path){
        URL imgURL = getClass().getResource(path);
        if (imgURL != null){
            return new ImageIcon(imgURL);
        }
        else{
            System.out.println("File at " + path + " could not be found");
            return null;
        }
    }


    private JPanel initializeMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(720,720));
        mainPanel.setLayout(new BorderLayout());
        return mainPanel;
    }

    private JPanel initializeBoardPanel(){
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(0, 8));
        return boardPanel;
    }

/*
    private void setUpMenu(JFrame window) {
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
        new ChessGUI();
    }
}
