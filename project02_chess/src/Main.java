import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;

public class Main extends Frame {
    //declare textures
    public Image chessboardImage;

    public Button[][] chessboardButtons;

    public char[][] chessboardPosition = new char[8][8];

    public Font chessFont = new Font( "MS Gothic", Font.PLAIN, 24);



    ////////////////////////////
    public Main() throws IOException {

        setTitle("Chess!");
        setResizable(false);


        //Create the chessboardButtons grid
        chessboardButtons = new Button[9][9];
        //////////////////////
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                chessboardButtons[i][j] = new Button();

                chessboardButtons[i][j].setBounds(i * 128 + 8, j * 128 + 31, 128, 128);
                if(i%2!=0 && j%2==0)
                {
                    chessboardButtons[i][j].setBackground(new Color(245, 0, 4)); // make the button black
                }
                else if(i%2==0 && j%2!=0)
                {
                    chessboardButtons[i][j].setBackground(new Color(245, 4, 4)); // make the button black
                }
                add(chessboardButtons[i][j]);
                chessboardButtons[i][j].setFont(chessFont);
            }
        }

        ////////// make the X button work
        this.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent evt) {
                System.exit(0);
            }

        });
        //////////

    }
    ////////////////////////////

    ////////////////////////////
    public static void main(String[] args) throws IOException {
        Main frame = new Main();
        frame.loadImages(); //loading textures

        frame.setSize(1024 + 16, 1024 + 8 + 31); //accounting for window margins
        frame.setVisible(true);

        frame.initializeBoard();
        frame.paint(frame.getGraphics());
    }
    ////////////////////////////

    ////////////////////////////
    @Override
    public void paint(Graphics g) {
        g.drawImage(chessboardImage, 8, 31, null); //draw the chessboard
    }
    ////////////////////////////

    ////////////////////////////
    public void loadImages() throws IOException {
        chessboardImage = ImageIO.read(new File("chessboard.png")); //read background/chessboard image
    }

    public void updateChessboard()
    {
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
             chessboardButtons[j][i].setLabel(String.valueOf(chessboardPosition[i][j]));

             System.out.println(i+"\n"+j);
             chessboardButtons[i][j].setFont(chessFont);

            }
        }
        chessboardButtons[7][7].setLabel(String.valueOf(chessboardPosition[7][7]));
    }

    public void initializeBoard()
    {

        chessboardPosition[0] = new char[]{'♜','♞','♝','♛','♚','♝','♞','♜'};
        chessboardPosition[1] = new char[]{'♟','♟','♟','♟','♟','♟','♟', '♟'};
        chessboardPosition[2] = new char[]{' ',' ',' ',' ',' ',' ',' ',' '};
        chessboardPosition[3] = new char[]{' ','A','M','O','G','U','S',' '};
        chessboardPosition[4] = new char[]{' ',' ',' ',' ',' ',' ',' ',' '};
        chessboardPosition[5] = new char[]{' ',' ',' ',' ',' ',' ',' ',' '};
        chessboardPosition[6] = new char[]{'♙','♙','♙','♙','♙','♙','♙','♙'};
        chessboardPosition[7] = new char[]{'♖','♘','♗','♕','♔','♗','♘','♖', ' '};



        updateChessboard();

    }
    ////////////////////////////
}
