import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


////////////////////////////////////////////////////
public class S28538Project02 extends Frame {



    public Button[][] chessboardButtons;
    public Button saveButton;
    public Button loadButton;

    public Button thisButtonOnlyExistsBecauseAWT;

    public Piece[][] chessboardPosition = new Piece[8][8];

    public Font chessFont = new Font( "bruh", Font.PLAIN, 16); //this only controls the font size, AWT only supports logical fonts for labels, as such I cannot use unicode chess symbols

    public Piece selectedPiece = null;
    public int selectedPositionX = 9;
    public int selectedPositionY = 9;

    public String chessGameID;

    public String gameToLoadFilename;


    public String currentTurn = "White";
    ////////////////////////////

    ////////////////////////////
    public S28538Project02()
    {

        setTitle("Chess! Start the game by moving a White piece!");
        setResizable(false);

        ChessboardIO chessInputOutput = new ChessboardIO();

        chessGameID = UUID.randomUUID().toString().substring(0,7); //generate an 8 character ID for the chess game


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
                    chessboardButtons[i][j].setBackground(new Color(245, 0, 4)); // make the button red
                }
                else if(i%2==0 && j%2!=0)
                {
                    chessboardButtons[i][j].setBackground(new Color(245, 4, 4)); // make the button red
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

        saveButton.setBounds(8, 31+128*8, 256, 64);
        loadButton.setBounds(8+256, 31+128*8, 256, 64);

        saveButton.setBackground(new Color(4, 180, 4));
        loadButton.setBackground(new Color(44, 64, 220));

        add(saveButton);
        add(loadButton);


        saveButton.addActionListener(event ->
        {
            try {
                chessInputOutput.savePositionToFile(chessboardPosition, String.valueOf("Chessgame_"+ chessGameID + "_" + System.currentTimeMillis())+".bin"); //save as "Chessgame_[GAME ID]_[TIME SINCE EPOCH]"

                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                saveButton.setLabel("Game Saved! " + formatter.format(new Date()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


        loadButton.addActionListener(event ->
        {

            Frame loadNamePopup = new Frame("Enter your file name:");
            TextField textField = new TextField();
            loadNamePopup.add(textField);
            loadNamePopup.setSize(300, 100);
            loadNamePopup.setVisible(true);
            loadNamePopup.setResizable(false);

            textField.addActionListener(e -> {
                gameToLoadFilename = textField.getText();
                if (!gameToLoadFilename.endsWith(".bin")){gameToLoadFilename = gameToLoadFilename + ".bin";}
                System.out.println("Loading game with filename: " + gameToLoadFilename);
                loadNamePopup.dispose();


                    chessboardPosition = chessInputOutput.loadPositionFromFile(gameToLoadFilename);
                    updateChessboard();


            });




        });

        ///////////

        //fix the layout because the last added button gets messed up

        thisButtonOnlyExistsBecauseAWT = new Button();

        add(thisButtonOnlyExistsBecauseAWT);

        thisButtonOnlyExistsBecauseAWT.addActionListener(event -> {
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
    private void chessboardClicked(int x, int y)
    {

        if (selectedPiece == null) //check if a piece is selected to be moved
        {
            if(chessboardPosition[y][x] != null && chessboardPosition[y][x].GetPieceColor().toString().equals(currentTurn))
            {
                selectedPiece = chessboardPosition[y][x];
                selectedPositionX = x;
                selectedPositionY = y;
            }
            else if (chessboardPosition[y][x] != null)
            {
                System.out.println("It is currently " + currentTurn + "'s turn! Please select a " + currentTurn + " piece!");
            }

        }
        else if (chessboardPosition[y][x] != null && selectedPiece.GetPieceColor() == chessboardPosition[y][x].GetPieceColor())
        {
            System.out.println(selectedPiece.GetPieceColor() + ": selected a different piece!");

            selectedPiece = chessboardPosition[y][x];
            selectedPositionX = x;
            selectedPositionY = y;

        }
        else
        {
            chessboardPosition[y][x] = selectedPiece;
            chessboardPosition[selectedPositionY][selectedPositionX] = null;
            updateChessboard();
            selectedPiece = null;
            switchTurn(currentTurn);
        }
    }
    ////////////////////////////

    ////////////////////////////
    public void switchTurn(String currentlyPlaying)
    {
        if (currentlyPlaying.equals("White"))
        {
            currentTurn = "Black";
        }
        else
        {
            currentTurn = "White";
        }
        setTitle("Chess! Current turn: " + currentTurn);
    }
    ////////////////////////////

    ////////////////////////////
    public static void main(String[] args)
    {
        S28538Project02 frame = new S28538Project02();




        frame.setSize(1024 + 16, 1024 + 8 + 31 + 64 /*64 pixels for save and load buttons*/ ); //accounting for window margins
        frame.setVisible(true);

        frame.initializeBoard();
        //frame.paint(frame.getGraphics());
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
                    //promote last rank pawns to queens
                    if(chessboardPosition[i][j].PieceLabel().equals("White Pawn") && i == 0 || chessboardPosition[i][j].PieceLabel().equals("Black Pawn") && i == 7) //promote last rank pawns to queens
                    {
                        chessboardPosition[i][j] = new Queen(chessboardPosition[i][j].GetPieceColor());
                    }
                    /////////

                    chessboardButtons[j][i].setLabel(chessboardPosition[i][j].PieceLabel());

                    //System.out.println(i + "\n" + j);
                } else {chessboardButtons[j][i].setLabel("");}
            }
        }

    }
    ////////////////////////////

    ////////////////////////////
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
////////////////////////////////////////////////////

//////////////////////////////////////////////////// abstract class for pieces
abstract class Piece
{
    public abstract void MoveDirections();

    public abstract PieceColor GetPieceColor();

    public enum PieceColor
    {
        BLACK("Black"), WHITE("White");
        private final String pieceColorString;

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
////////////////////////////////////////////////////

//////////////////////////////////////////////////// classes extending Piece for different types of pieces

////////////////////////////
class King extends Piece {

    private final PieceColor pieceColor;


    public King(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }

                                                                                        //KING

    @Override
    public void MoveDirections()
    {

    }

    @Override
    public String PieceLabel() {
        return this.pieceColor.toString() + " King";
    }

    @Override
    public boolean IsCheckable() {
        return true;
    }

    @Override
    public PieceColor GetPieceColor() {
        return pieceColor;
    }
}
////////////////////////////

////////////////////////////
class Queen extends Piece {

    private final PieceColor pieceColor;


    public Queen(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }

                                                                                        //QUEEN

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

    @Override
    public PieceColor GetPieceColor() {
        return pieceColor;
    }
}
////////////////////////////

////////////////////////////
class Bishop extends Piece {

    private final PieceColor pieceColor;


    public Bishop(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }

                                                                                    //BISHOP

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

    @Override
    public PieceColor GetPieceColor() {
        return this.pieceColor;
    }
}
////////////////////////////

////////////////////////////
class Horsey extends Piece {

    private final PieceColor pieceColor;


    public Horsey(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }

                                                                                //HORSEY

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

    @Override
    public PieceColor GetPieceColor() {
        return pieceColor;
    }
}
////////////////////////////

////////////////////////////
class Pawn extends Piece {

    private final PieceColor pieceColor;


    public Pawn(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }

                                                                            //PAWN

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

    @Override
    public PieceColor GetPieceColor() {
        return pieceColor;
    }
}
////////////////////////////

////////////////////////////
class Rook extends Piece {

    private final PieceColor pieceColor;


    public Rook(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }

                                                                        //ROOK

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

    @Override
    public PieceColor GetPieceColor() {
        return pieceColor;
    }
}
////////////////////////////

////////////////////////////////////////////////////

//////////////////////////////////////////////////// saving and loading


 class ChessboardIO {
    public byte outputByte1 = 0;
    public byte outputByte2 = 0;

    public byte inputByte1 = 0;
    public byte inputByte2 = 0;

     public void savePositionToFile(Piece[][] chessboardPosition, String filename) throws IOException {

         try{
             FileOutputStream theStreamOfTheOutputs = new FileOutputStream(filename);
             for (int i = 0; i < 8; i++)
             {
                 for (int j = 0; j < 8; j++)
                 {
                     if(chessboardPosition[i][j] != null)
                     {
                         outputByte1 = (byte) (((PieceToInt(chessboardPosition[i][j]) & 0b111) << 5) | ((j+1 & 0b1111) << 1) | ((i+1 & 0b1111) >> 3));
                         // 00000XXX << XXX00000       shifting the bits representing the piece
                         // XXX0JJJJ << XXXJJJJ0       shifting the bits representing horizontal pos
                         // XXXJJJJI (III cut off)     shifting the vertical pos

                         outputByte2 = (byte) (((i+1 & 0b1111) << 4) | (PieceColorToInt(chessboardPosition[i][j]) & 0b1));

                         // 0000IIII << III00000 (I cut off at the start)
                         // III0000W << IIIW0000       shifting the color bit

                         theStreamOfTheOutputs.write(outputByte1);
                         theStreamOfTheOutputs.write(outputByte2);


                     }
                 }
             }
             theStreamOfTheOutputs.close();
         }
         catch (IOException e)
         {
             System.out.println("something bad happened while saving the file..");
         }


     }

     public Piece[][] loadPositionFromFile(String filename) {
         int readPiece;
         int readPosHor;
         int readPosVer;
         int readColor;

         Piece[][] readChessboardPosition = new Piece[8][8];



         try {
             FileInputStream theStreamOfTheInputs = new FileInputStream(filename);


             byte[] readBytes = new byte[2];


             int bytesRead = theStreamOfTheInputs.read(readBytes); //read first 2 bytes


             while (bytesRead == 2) {

                 readPiece = ((readBytes[0] >> 5) & 0b111);
                 readPosHor = ((readBytes[0] >> 1) & 0b1111);
                 readPosVer = (((readBytes[0] & 0b1) << 3) | ((readBytes[1] >> 5) & 0b111));
                 readColor = (readBytes[1] & 0b1);

                 if (readPosVer-1 >= 0 && readPosHor-1 >= 0 && readPosVer-1 <= 7 && readPosHor-1 <= 7) {
                     readChessboardPosition[readPosVer - 1][readPosHor - 1] = intToPieceWColor(readPiece, readColor);
                 }


                 bytesRead = theStreamOfTheInputs.read(readBytes); //read next two bytes, we're looping until we read no bytes
             }
             theStreamOfTheInputs.close();
         } catch (IOException e) {
             Frame errorLoading = new Frame("ERROR LOADING THE FILE!");
             Label errorLabel = new Label("Error loading savefile! Are you sure you entered the correct name?");
             Button okButton = new Button("Exit");
             errorLoading.setSize(516, 359);
             errorLoading.setVisible(true);
             errorLoading.setResizable(false);

             okButton.setBounds(8, 31+150, 500, 150);
             errorLabel.setBounds(8, 31, 500, 150);
             Button thisButtonOnlyExistsBecauseAWT = new Button();
             thisButtonOnlyExistsBecauseAWT.setBounds(0, 0, 0, 0);

             errorLoading.add(errorLabel);
             errorLoading.add(okButton);
             errorLoading.add(thisButtonOnlyExistsBecauseAWT);




             okButton.addActionListener(okButtonEve -> {
                 System.exit(-1);
             });
         }

         return readChessboardPosition;

     }
     public int PieceColorToInt(Piece piece)
     {
         if (piece.GetPieceColor().toString().equals("White")) {return 0;} else {return 1;}
     }

     public int PieceToInt(Piece piece)
     {
         int convertedPiece = 0;
         switch(piece.toString())
         {
             case "Pawn":

                 convertedPiece = 0;

                 break;

             case "Knight":

                 convertedPiece = 5;

                 break;

             case "Bishop":

                 convertedPiece = 4;

                 break;

             case "Rook":

                 convertedPiece = 3;

                 break;

             case "Queen":

                 convertedPiece = 2;

                 break;

             case "King":

                 convertedPiece = 1;

                 break;

         }
         return convertedPiece;
     }

     public Piece intToPieceWColor(int pieceNum, int colorNum) {

         Piece readPiece = new Pawn(Piece.PieceColor.WHITE);
         Piece.PieceColor readColor;
         if (colorNum == 0) {
             readColor = Piece.PieceColor.WHITE;
         }
         else
         {readColor = Piece.PieceColor.BLACK;}

         switch (pieceNum) {
             case 0: readPiece = new Pawn(readColor); break;
             case 1: readPiece = new King(readColor); break;
             case 2: readPiece = new Queen(readColor); break;
             case 3: readPiece = new Rook(readColor); break;
             case 4: readPiece = new Bishop(readColor); break;
             case 5: readPiece = new Horsey(readColor); break;
         }
         return readPiece;
     }



}

