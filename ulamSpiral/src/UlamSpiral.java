import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class UlamSpiral extends Frame {
    private static final int CELL_SIZE = 1;
    private int[][] spiral;
    private int spiralSize;

    private int screenSize = 640;
    private Vector<Integer> primes = new Vector<>();

    public UlamSpiral() throws IOException {


        spiralSize = (screenSize / CELL_SIZE) * 10 + 1;
        spiral = new int[spiralSize][spiralSize];


        if(new File("primes.bin").isFile())
        {

            System.out.print("Primes binary found! Loading primes from binary..");
            primes = readPrimesFromFile("primes.bin");


        }
        else {

            System.out.println("No primes binary found!\nGenerating primes.. please wait!");


            for (int i = 2; i <= (spiralSize * spiralSize); i++) {
                if (isPrime(i)) {
                    primes.add(i);
                }
            }


            System.out.println("Saving primes to primes.bin...");
            savePrimesToFile(primes, "primes.bin");


        }
        System.out.println("\nLaunching application window..");

        setSize(screenSize, screenSize);
        setVisible(true);

        ////// make the X button work
        this.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent evt) {
                System.exit(0);
            }

        });

    }


    ////////////// Saving and loading from file //////////////

    public void savePrimesToFile(Vector<Integer> primes, String filename) throws IOException
    {

        FileOutputStream outputStream = new FileOutputStream(filename);

        for (int i = 1; i <= 8; i++) {
            int numPrimes = 0;
            for (int prime : primes) {
                if (prime >= (1 << ((i - 1) * 8)) && prime < (1 << (i * 8))) {
                    numPrimes++;
                }
            }
            outputStream.write(toByteArray(numPrimes, 8));
        }


        for (int i = 1; i <= 8; i++) {
            for (int prime : primes) {
                if (prime >= (1 << ((i - 1) * 8)) && prime < (1 << (i * 8))) {
                    outputStream.write(toByteArray(prime, i));
                }
            }
        }


        outputStream.close();

    }

    //////////////


    public Vector<Integer> readPrimesFromFile(String filename) throws IOException {
        Vector<Integer> primes = new Vector<Integer>();

        FileInputStream inputStream = new FileInputStream(filename);

        int[] numPrimes = new int[8];
        for (int i = 0; i < 8; i++) {
            byte[] buffer = new byte[8];
            inputStream.read(buffer);
            numPrimes[i] = fromByteArray(buffer);
        }


        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < numPrimes[i]; j++) {
                byte[] buffer = new byte[i+1];
                inputStream.read(buffer);
                int prime = fromByteArray(buffer);
                primes.add(prime);
            }
        }

        inputStream.close();

        return primes;
    }


    ////////////// byte conversion //////////////
    private int fromByteArray(byte[] bytes) {
        int primeFromArray = 0;
        for (int i = 0; i < bytes.length; i++) {
            primeFromArray = (primeFromArray << 8) | (bytes[i] & 255);
        }
        return primeFromArray;
    }

    private byte[] toByteArray(int num, int length) {
        byte[] bytes = new byte[length];
        for (int i = length - 1; i >= 0; i--) {
            bytes[i] = (byte) (num & 255);
            num >>= 8;
        }
        return bytes;
    }



    ////////////// painting the window //////////////

    @Override
    public void paint(Graphics g) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int x = centerX;
        int y = centerY;
        int direction = 0; // 0 = right, 1 = down, 2 = left, 3 = up
        int steps = 1;
        int stepCount = 0;
        int primeIndex = 0;

        for (int i = 1; i <= (spiralSize * spiralSize); i++) {

            if (primes.size()>primeIndex && i == primes.get(primeIndex)) {
                g.setColor(Color.WHITE);
                primeIndex++;
            } else {
                g.setColor(Color.BLACK);
            }

            if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
                g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                spiral[y / CELL_SIZE][x / CELL_SIZE] = i;
            }



            if (++stepCount == steps) {
                stepCount = 0;
                direction = (direction + 1) % 4;
                if (direction == 0 || direction == 2) {
                    steps++;
                }
            }



            switch (direction) {
                case 0: x += CELL_SIZE; break;
                case 1: y += CELL_SIZE; break;
                case 2: x -= CELL_SIZE; break;
                case 3: y -= CELL_SIZE; break;

            }
        }
    }

    ////////////// Check if number is prime //////////////
    private boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    ////////////// Main //////////////
    public static void main(String[] args) throws IOException {

        new UlamSpiral();

    }
}