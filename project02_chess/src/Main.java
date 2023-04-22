import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;



public class Main extends Frame {
    //declare textures


    public Button[][] chessboardButtons;
    public Button saveButton;
    public Button loadButton;

    public Button thisButtonOnlyExistsBecauseAWTisBadAndSwingShouldBeUsedInstead;

    public String[][] chessboardPosition = new String[8][8];

    public Font chessFont = new Font( "ihateAWT", Font.PLAIN, 16); //AWT only supports logical fonts for labels, as such I cannot use unicode chess symbols



    ////////////////////////////
    public Main() throws IOException
    {

        setTitle("Chess!");
        setResizable(false);


        //Create the chessboardButtons grid
        chessboardButtons = new Button[8][8];
        //////////////////////
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
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
        //////////////////

        //save and load buttons

        saveButton = new Button("Save");
        loadButton = new Button("Load");

        saveButton.setBounds(8, 31+128*8, 128, 64);
        loadButton.setBounds(8+128, 31+128*8, 128, 64);

        saveButton.setBackground(new Color(4, 180, 4));
        loadButton.setBackground(new Color(44, 64, 220));

        add(saveButton);
        add(loadButton);

        ///////////

        //fix the layout because the last added button gets messed up

        thisButtonOnlyExistsBecauseAWTisBadAndSwingShouldBeUsedInstead = new Button();

        add(thisButtonOnlyExistsBecauseAWTisBadAndSwingShouldBeUsedInstead);

        thisButtonOnlyExistsBecauseAWTisBadAndSwingShouldBeUsedInstead.addActionListener(event -> {
            System.out.println("DON'T CLICK ME PLEASEEEEEE");
        });


        ////


        ////////// make the X button work
        this.addWindowListener(new WindowAdapter()
        {

            public void windowClosing(WindowEvent evt)
            {
                System.exit(0);
            }

        });
        //////////

    }
    ////////////////////////////

    ////////////////////////////
    public static void main(String[] args) throws IOException
    {
        Main frame = new Main();


        frame.setSize(1024 + 16, 1024 + 8 + 31 + 64 /*64 pixels for save and load buttons*/ ); //accounting for window margins
        frame.setVisible(true);

        frame.initializeBoard();
        frame.paint(frame.getGraphics());
    }
    ////////////////////////////

    ////////////////////////////

    @Override
    public void paint(Graphics g)
    {

    }
    ////////////////////////////

    ////////////////////////////


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
        chessboardButtons[7][7].setLabel(chessboardPosition[7][7]);
    }

    public void initializeBoard()
    {

        chessboardPosition[0] = new String[]{"Black Tower","Black Knight","Black Bishop","Black Queen","Black King","Black Bishop","Black Knight","Black Rook"};
        chessboardPosition[1] = new String[]{"Black Pawn","Black Pawn","Black Pawn","Black Pawn","Black Pawn","Black Pawn","Black Pawn","Black Pawn"};
        chessboardPosition[2] = new String[]{" "," "," "," "," "," "," "," "};
        chessboardPosition[3] = new String[]{" "," "," "," "," "," "," "," "};
        chessboardPosition[4] = new String[]{" "," "," "," "," "," "," "," "};
        chessboardPosition[5] = new String[]{" "," "," "," "," "," "," "," "};
        chessboardPosition[6] = new String[]{"White Pawn","White Pawn","White Pawn","White Pawn","White Pawn","White Pawn","White Pawn","White Pawn"};
        chessboardPosition[7] = new String[]{"White Tower","White Knight","White Bishop","White Queen","White King","White Bishop","White Knight","White Rook"};



        updateChessboard();

    }
    ////////////////////////////
}
