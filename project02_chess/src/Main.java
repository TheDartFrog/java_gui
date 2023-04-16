import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;


//////////////////////////////////////////////////
public class Main extends Frame {

    //declare textures
    public Image chessboardImage;
    public Image pawnImage;
    public Image rookImage;
    public Image horseyImage;
    public Image bishopImage;
    public Image queenImage;
    public Image kingImage;
    public Image emptyImage;
    public Image targetImage;
    //////



    ////////////////////////////
    public Main() throws IOException {

        setTitle("Chess!");
        setResizable(false);




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
        frame.setSize(1024+16, 1024+8+31); //accounting for window margins
        frame.setVisible(true);
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

        pawnImage = ImageIO.read(new File("pawn.png")); //read pieces images
        rookImage = ImageIO.read(new File("rook.png"));
        horseyImage = ImageIO.read(new File("hors.png"));
        bishopImage = ImageIO.read(new File("bish.png"));
        queenImage = ImageIO.read(new File("quen.png"));
        kingImage = ImageIO.read(new File("king.png"));

        emptyImage = ImageIO.read(new File("empty.png")); //read empty slot image for easy layout handling

        targetImage = ImageIO.read(new File("target.png")); //read 'possible move' highlight image



    }
    ////////////////////////////


}
//////////////////////////////////////////////////