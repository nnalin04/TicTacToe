import java.util.*;

class TicTacToe {

    public static void main(final String[] args) {

        // creating an object of PlayingTTT class.
        final PlayingTTT play = new PlayingTTT();
        Scanner sc = new Scanner(System.in);

        // creating the board as an 3*3 array
        final String[][] board = new String[3][3];

        // calling the function to set the re-set the board.
        play.settingBoard(board);

        String player;
        String cpu;

        // calling a function to display the board.
        play.displayBoard(board);

        // calling a function to decide who will play first amd there symbol choice.
        if (play.decidingToss()) {
            System.out.println("Enter a character between X or O");
            player = sc.next();
            if (player == "X") {
                cpu = "O";
            } else {
                cpu = "X";
            }
        } else {
            if (play.decidingToss()) {
                cpu = "O";
                player = "X";
            } else {
                cpu = "X";
                player = "O";
            }
        }
        sc.close();
    }
}

class PlayingTTT {

    // function to re-set the board.
    public void settingBoard(final String[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = " .";
            }
        }
    }

    // function to create a random toss
    public boolean decidingToss() {
        final Random r = new Random();
        return r.nextBoolean();
    }

    // function to display the board.
    public void displayBoard(String[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }
}
