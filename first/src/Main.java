import javax.lang.model.type.NullType;
import java.awt.*;
import java.util.Random;
import java.util.random.RandomGenerator;

public class Main
extends Frame {

    boolean hasInitialized = false;

    public static void main(String[] args) {
        new Main();
    }

    public Main()
    {
        super();

        this.setSize(640, 480);
        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g)
    {

    super.paint(g);

    //System.out.println("mogus");

    int windowHeight = g.getClipBounds().height-60;
    int windowWidth = g.getClipBounds().width-20;
    int circleSize;
    int tableSize = 10;
    double seed = 0;
    //boolean hasInitialized;
    if(!hasInitialized)
    {
        seed = Math.random()*1000;
        hasInitialized = true;
    }


    Random randomizer = new Random((long)seed);

    if (windowHeight>windowWidth)
    {
        circleSize = windowWidth/10;
    } else {
        circleSize = windowHeight/10;
    }

    for(int row = 0; row < tableSize; row++) {

        for(int column = 0; column<tableSize; column++){
            Color RandomColor = new Color(randomizer.nextInt());
                 g.setColor(RandomColor);
                 g.fillOval(windowWidth/tableSize*column + 10, windowHeight/tableSize*row + 30, circleSize, circleSize);
            }
        }
        System.out.println(windowHeight + " " + windowWidth +" "+ circleSize);
    }

    //MAKE PERMANENT RANDOM COLORS

}