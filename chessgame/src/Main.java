import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;


import java.awt.Font;

import java.awt.Point;

interface Board {
    int SIZE = 8;
    Dimension getPreferredSize();
    void paint(Graphics g);
    Color getSquareColor(int row, int col);
    void setPiece(int row, int col, Piece piece);
    Piece getPiece(int row, int col);
    boolean movePiece(int fromRow, int fromCol, int toRow, int toCol);
    boolean isCheck(Color color);
    boolean isCheckmate(Color color);
    boolean isStalemate(Color color);
    void newGame();
}


interface Piece {
    Color getColor();
    boolean isValidMove(Board board, int fromRow, int fromCol, int toRow, int toCol);
    String toString();
}

class Pawn implements Piece {
    private final Color color;
    private boolean hasMoved;

    public Pawn(Color color) {
        this.color = color;
        this.hasMoved = false;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public boolean isValidMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        int dy = toRow - fromRow;
        int dx = Math.abs(toCol - fromCol);

        // check if move is forward
        if (dy < 0) {
            return false;
        }

        // check if move is one or two squares
        if (dy > 2 || dy == 2 && hasMoved || dx > 1) {
            return false;
        }

        // check if move is diagonal
        if (dx == 1 && dy != 1) {
            return false;
        }

        // check if destination square is occupied
        if (dx == 0 && board.getPiece(toRow, toCol) != null) {
            return false;
        }

        // check if move is blocked by other piece
        if (dy == 1 && dx == 0 && board.getPiece(toRow, toCol) == null) {
            Piece enPassant = board.getPiece(fromRow, toCol);
            if (enPassant == null || enPassant.getColor() == color || !(enPassant instanceof Pawn) || !((Pawn) enPassant).hasMoved) {
                return false;
            }
        }

        // valid move
        return true;
    }

    @Override
    public String toString() {
        return "P";
    }
}

 class Rook implements Piece {
    private final Color color;

    public Rook(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public boolean isValidMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        int dy = toRow - fromRow;
        int dx = toCol - fromCol;

        //check if move is vertical or horizontal
        if (dx != 0 && dy != 0) {
            return false;
        }

        // check if path is clear
        int d = dx != 0 ? dx / Math.abs(dx) : dy / Math.abs(dy);
        int r = fromRow + d;
        int c = fromCol + d;
        while (r != toRow || c != toCol) {
            if (board.getPiece(r, c) != null) {
                return false;
            }
            r += d * (dx == 0 ? 0 : 1);
            c += d * (dy == 0 ? 0 : 1);
        }

        // valid move
        return true;
    }

    @Override
    public String toString() {
        return "R";
    }}






public class ChessBoard implements Board {
    private final Piece[][] pieces;
    private final Color[] squareColors;
    private final Color lightSquareColor = new Color(240, 217, 181);
    private final Color darkSquareColor = new Color(181, 136, 99);
    private final int squareSize = 64;
    private final Font font = new Font("Arial", Font.PLAIN, 48);
    private Point selectedSquare;

    public ChessBoard() {
        this.pieces = new Piece[SIZE][SIZE];
        this.squareColors = new Color[SIZE * SIZE];
        for (int i = 0; i < squareColors.length; i++) {
            squareColors[i] = (i / SIZE + i % SIZE) % 2 == 0 ? lightSquareColor : darkSquareColor;
        }
        this.selectedSquare = null;
        newGame();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(squareSize * SIZE, squareSize * SIZE);
    }

    @Override
    public void paint(Graphics g) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                g.setColor(getSquareColor(row, col));
                g.fillRect(col * squareSize, row * squareSize, squareSize, squareSize);
                Piece piece = getPiece(row, col);
                if (piece != null) {
                    g.setFont(font);
                    g.setColor(piece.getColor());
                    g.drawString(piece.toString(), col * squareSize + squareSize / 2 - 16, row * squareSize + squareSize / 2 + 16);
                }
            }
        }
    }

    @Override
    public Color getSquareColor(int row, int col) {
        return squareColors[row * SIZE + col];
    }

    @Override
    public void setPiece(int row, int col, Piece piece) {
        pieces[row][col] = piece;
    }

    @Override
    public Piece getPiece(int row, int col) {
        return pieces[row][col];
    }

    @Override
    public boolean movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        Piece piece = getPiece(fromRow, fromCol);
        if (piece == null) {
            return false;
        }
        if (!piece.isValidMove(this, fromRow, fromCol, toRow, toCol)) {


            if (check) {
                setPiece(toRow, toCol, piece);
                setPiece(fromRow, fromCol, null);
                return true;
            }
            return false;
        }
    }


    public void selectSquare(int row, int col) {
        if (selectedSquare != null) {
            int fromRow = selectedSquare.y;
            int fromCol = selectedSquare.x;
            if (movePiece(fromRow, fromCol, row, col)) {
                // valid move
                selectedSquare = null;
                return;
            }
        }
        Piece piece = getPiece(row, col);
        if (piece != null) {
            // select piece
            selectedSquare = new Point(col, row);
        } else {
            // unselect piece
            selectedSquare = null;
        }
    }


    public boolean isSquareSelected(int row, int col) {
        return selectedSquare != null && selectedSquare.x == col && selectedSquare.y == row;
    }

    @Override
    public boolean isGameOver() {
        // TODO: implement checkmate detection
        return false;
    }

    @Override
    public void newGame() {
        // set up initial position
        setPiece(0, 0, new Rook(Color.BLACK));
        setPiece(0, 1, new Knight(Color.BLACK));
        setPiece(0, 2, new Bishop(Color.BLACK));
        setPiece(0, 3, new Queen(Color.BLACK));
        setPiece(0, 4, new King(Color.BLACK));
        setPiece(0, 5, new Bishop(Color.BLACK));
        setPiece(0, 6, new Knight(Color.BLACK));
        setPiece(0, 7, new Rook(Color.BLACK));
        for (int col = 0; col < SIZE; col++) {
            setPiece(1, col, new Pawn(Color.BLACK));
        }
        setPiece(7, 0, new Rook(Color.WHITE));
        setPiece(7, 1, new Knight(Color.WHITE));
        setPiece(7, 2, new Bishop(Color.WHITE));
        setPiece(7, 3, new Queen(Color.WHITE));
        setPiece(7, 4, new King(Color.WHITE));
        setPiece(7, 5, new Bishop(Color.WHITE));
        setPiece(7, 6, new Knight(Color.WHITE));
        setPiece(7, 7, new Rook(Color.WHITE));
        for (int col = 0; col < SIZE; col++) {
            setPiece(6, col, new Pawn(Color.WHITE));
        }
    }
}


