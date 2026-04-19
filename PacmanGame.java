import java.util.Scanner;
import java.util.Random;

// Main class where the program starts
public class PacmanGame {
    public static void main(String[] args) {

        // creating objects of all classes
        GameBoard board = new GameBoard(10, 10);
        Pacman pac = new Pacman(0, 0);
        Ghost ghost = new Ghost(5, 5);
        Food food = new Food(3, 3);

        Scanner input = new Scanner(System.in);
        boolean running = true;

        System.out.println("Use W A S D to move Pacman");

        // main game loop
        while (running) {

            // display board with all elements
            board.display(pac, ghost, food);

            // taking user input
            System.out.print("Move: ");
            char move = input.next().toUpperCase().charAt(0);

            // move pacman based on input
            pac.move(move, board);

            // ghost moves randomly
            ghost.moveRandom(board);

            // check if pacman reaches food
            if (pac.getX() == food.getX() && pac.getY() == food.getY()) {
                System.out.println("Food eaten!");
                pac.increaseScore();   // increase score
                food.respawn(board);  // place food again
            }

            // check if ghost catches pacman
            if (pac.getX() == ghost.getX() && pac.getY() == ghost.getY()) {
                System.out.println("Game Over! Ghost caught you.");
                running = false; // stop game
            }
        }

        // final score after game ends
        System.out.println("Final Score: " + pac.getScore());
    }
}


// class for game board
class GameBoard {

    private int rows;
    private int cols;

    // constructor to set board size
    public GameBoard(int r, int c) {
        rows = r;
        cols = c;
    }

    // function to display board
    public void display(Pacman p, Ghost g, Food f) {

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                // print P for pacman
                if (i == p.getX() && j == p.getY()) {
                    System.out.print("P ");
                }
                // print G for ghost
                else if (i == g.getX() && j == g.getY()) {
                    System.out.print("G ");
                }
                // print . for food
                else if (i == f.getX() && j == f.getY()) {
                    System.out.print(". ");
                }
                // empty space
                else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
    }

    // check if position is inside board
    public boolean isValid(int x, int y) {
        return (x >= 0 && x < rows && y >= 0 && y < cols);
    }
}


// class for pacman (player)
class Pacman {

    private int x, y;
    private int score;

    // constructor
    public Pacman(int x, int y) {
        this.x = x;
        this.y = y;
        score = 0;
    }

    // function to move pacman
    public void move(char dir, GameBoard board) {

        int newX = x;
        int newY = y;

        // movement logic
        if (dir == 'W') newX--;     // up
        else if (dir == 'S') newX++; // down
        else if (dir == 'A') newY--; // left
        else if (dir == 'D') newY++; // right

        // update position if valid
        if (board.isValid(newX, newY)) {
            x = newX;
            y = newY;
        }
    }

    // increase score when food is eaten
    public void increaseScore() {
        score++;
    }

    // getter methods
    public int getScore() {
        return score;
    }

    public int getX() { return x; }
    public int getY() { return y; }
}


// class for ghost (enemy)
class Ghost {

    private int x, y;
    private Random rand = new Random();

    // constructor
    public Ghost(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // random movement of ghost
    public void moveRandom(GameBoard board) {

        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        int choice = rand.nextInt(4);

        int newX = x + dx[choice];
        int newY = y + dy[choice];

        // move only if inside board
        if (board.isValid(newX, newY)) {
            x = newX;
            y = newY;
        }
    }

    // getter methods
    public int getX() { return x; }
    public int getY() { return y; }
}


// class for food
class Food {

    private int x, y;
    private Random rand = new Random();

    // constructor
    public Food(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // respawn food at random position
    public void respawn(GameBoard board) {
        x = rand.nextInt(10);
        y = rand.nextInt(10);
    }

    // getter methods
    public int getX() { return x; }
    public int getY() { return y; }

}
