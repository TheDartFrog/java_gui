import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;



public class Main extends Frame {



    public Button[][] chessboardButtons;
    public Button saveButton;
    public Button loadButton;

    public Button thisButtonOnlyExistsBecauseAWTisBadAndSwingShouldBeUsedInstead;

    public Piece[][] chessboardPosition = new Piece[8][8];

    public Font chessFont = new Font( "ihateAWT", Font.PLAIN, 16); //AWT only supports logical fonts for labels, as such I cannot use unicode chess symbols

    public Piece selectedPiece = null;
    public int selectedPositionX = 9;
    public int selectedPositionY = 9;



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

                final int x = i;
                final int y = j;
                chessboardButtons[i][j].addActionListener(e -> chessboardClicked(x, y));


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

    private void chessboardClicked(int x, int y) {

        if (selectedPiece == null)
        {
            selectedPiece = chessboardPosition[y][x];
            selectedPositionX = x;
            selectedPositionY = y;


        } else
        {
            chessboardPosition[y][x] = selectedPiece;
            chessboardPosition[selectedPositionY][selectedPositionX] = null;
            updateChessboard();
            selectedPiece = null;
        }
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
                if(chessboardPosition[i][j] != null)
                {
                    chessboardButtons[j][i].setLabel(chessboardPosition[i][j].PieceLabel());

                    //System.out.println(i + "\n" + j);
                    chessboardButtons[i][j].setFont(chessFont);
                } else {chessboardButtons[j][i].setLabel("");}
            }
        }

    }

    public void initializeBoard()
    {
        chessboardPosition[0] = new Piece[]{new Rook(Piece.PieceColor.BLACK), new Horsey(Piece.PieceColor.BLACK), new Bishop(Piece.PieceColor.BLACK), new Queen(Piece.PieceColor.BLACK), new King(Piece.PieceColor.BLACK), new Bishop(Piece.PieceColor.BLACK), new Horsey(Piece.PieceColor.BLACK), new Rook(Piece.PieceColor.BLACK)};
        chessboardPosition[1] = new Piece[]{new Pawn(Piece.PieceColor.BLACK), new Pawn(Piece.PieceColor.BLACK), new Pawn(Piece.PieceColor.BLACK), new Pawn(Piece.PieceColor.BLACK), new Pawn(Piece.PieceColor.BLACK), new Pawn(Piece.PieceColor.BLACK), new Pawn(Piece.PieceColor.BLACK), new Pawn(Piece.PieceColor.BLACK)};
        chessboardPosition[2] = new Piece[]{null, null, null, null, null, null, null, null};
        chessboardPosition[3] = new Piece[]{null, null, null, null, null, null, null, null};
        chessboardPosition[4] = new Piece[]{null, null, null, null, null, null, null, null};
        chessboardPosition[5] = new Piece[]{null, null, null, null, null, null, null, null};
        chessboardPosition[6] = new Piece[]{new Pawn(Piece.PieceColor.WHITE), new Pawn(Piece.PieceColor.WHITE), new Pawn(Piece.PieceColor.WHITE), new Pawn(Piece.PieceColor.WHITE), new Pawn(Piece.PieceColor.WHITE), new Pawn(Piece.PieceColor.WHITE), new Pawn(Piece.PieceColor.WHITE), new Pawn(Piece.PieceColor.WHITE)};
        chessboardPosition[7] = new Piece[]{new Rook(Piece.PieceColor.WHITE), new Horsey(Piece.PieceColor.WHITE), new Bishop(Piece.PieceColor.WHITE), new Queen(Piece.PieceColor.WHITE), new King(Piece.PieceColor.WHITE), new Bishop(Piece.PieceColor.WHITE), new Horsey(Piece.PieceColor.WHITE), new Rook(Piece.PieceColor.WHITE)};

        updateChessboard();
    }

    ////////////////////////////
}

abstract class Piece
{
    public abstract void MoveDirections();

    public enum PieceColor
    {
        BLACK("Black"), WHITE("White");
        private String pieceColorString;

        PieceColor(String color)
        {

            this.pieceColorString = color;

        }

        @Override
        public String toString()
        {

            return pieceColorString;

        }
    }

    public abstract String PieceLabel();

    public abstract boolean IsCheckable();


}


//////////////////////////////////////////////////// classes extending Piece for different types of pieces
class King extends Piece {

    private final PieceColor pieceColor;


    public King(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }



    @Override
    public void MoveDirections() {

    }

    @Override
    public String PieceLabel() {
        return this.pieceColor.toString() + " King";
    }

    @Override
    public boolean IsCheckable() {
        return true;
    }
}


class Queen extends Piece {

    private final PieceColor pieceColor;


    public Queen(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }



    @Override
    public void MoveDirections() {

    }

    @Override
    public String PieceLabel() {
        return this.pieceColor.toString() + " Queen";

    }

    @Override
    public boolean IsCheckable() {
        return false;
    }
}


class Bishop extends Piece {

    private final PieceColor pieceColor;


    public Bishop(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }



    @Override
    public void MoveDirections() {

    }

    @Override
    public String PieceLabel() {
        return this.pieceColor.toString() +" Bishop";

    }

    @Override
    public boolean IsCheckable() {
        return false;
    }
}

class Horsey extends Piece {

    private final PieceColor pieceColor;


    public Horsey(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }



    @Override
    public void MoveDirections() {

    }

    @Override
    public String PieceLabel() {
        return this.pieceColor.toString() +" Knight";

    }

    @Override
    public boolean IsCheckable() {
        return false;
    }
}

class Pawn extends Piece {

    private final PieceColor pieceColor;


    public Pawn(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }



    @Override
    public void MoveDirections() {

    }

    @Override
    public String PieceLabel() {
        return this.pieceColor.toString() +" Pawn";

    }

    @Override
    public boolean IsCheckable() {
        return false;
    }
}

class Rook extends Piece {

    private final PieceColor pieceColor;


    public Rook(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }



    @Override
    public void MoveDirections() {

    }

    @Override
    public String PieceLabel() {
        return this.pieceColor.toString() +" Rook";

    }

    @Override
    public boolean IsCheckable() {
        return false;
    }
}
