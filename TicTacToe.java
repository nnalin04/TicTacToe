import java.util.*;

class TicTacToe {

    public static void main(String[] args) {
        // creating the board as an 3*3 array
        String[][] board = new String[3][3];

        // calling the function to play the game.
        playingTheGame(board);
    }

    // function to play the game.
    public static void playingTheGame(String[][] board) {

        // creating an object of PlayingTTT class.
        PlayingTTT play = new PlayingTTT();

        Scanner sc = new Scanner(System.in);

        List<Integer> PlayerPosition = new ArrayList<>();
        List<Integer> cpuPosition = new ArrayList<>();
        List<Integer> occupiedPosition = new ArrayList<>();

        // calling the function to set the re-set the board.
        play.settingBoard(board);

        // calling a function to decide who will play first amd there symbol choice.
        boolean toss = play.decidingToss();

        // setting winning condition for both opponents.
        boolean playerResult = false;
        boolean cpuResult = false;

        // calling a function to choose the player and cpu symbol.
        String[] SymbolArray = new String[2];
        SymbolArray = play.choosingSymbol(toss, sc);
        String playerSymbol = SymbolArray[0];
        String cpuSymbol = SymbolArray[1];

        // playing till either of the competitor win or tie.
        do {
            if (toss) {
                // calling a function to display the board.
                play.displayBoard(board);
                System.out.println("enter a the position you want to place your symbol, between 1-9");
                int index = sc.nextInt();
                index = checkingIfPresent(sc, index, occupiedPosition);
                occupiedPosition.add(index);
                PlayerPosition.add(index);
                board = play.settingSymbol(board, playerSymbol, index);
                playerResult = play.checkForWin(PlayerPosition);
                if (playerResult) {
                    System.out.println("Player Wins");
                }
                toss = false;
            } else {
                int index = cpuMove(occupiedPosition);
                cpuPosition.add(index);
                occupiedPosition.add(index);
                board = play.settingSymbol(board, cpuSymbol, index);
                cpuResult = play.checkForWin(cpuPosition);
                if (cpuResult) {
                    System.out.println("cpu Wins");
                }
                toss = true;
            }

            // checking for draw.
            if (play.checkForDraw(board)) {
                System.out.println("It's a draw");
                break;
            }
        } while (playerResult == false && cpuResult == false);

        play.displayBoard(board);

        sc.close();
    }

    private static int cpuMove(List<Integer> occupiedPosition) {
        Random r = new Random();
        int index = r.nextInt(9)+1;
        if(occupiedPosition.contains(index)){
            cpuMove(occupiedPosition);
        }
		return index;
	}

	private static int checkingIfPresent(Scanner sc, int index, List<Integer> occupiedPosition) {
        if (occupiedPosition.contains(index)) {
            System.out.println("enter a different position " + index + " is already present");
            index = sc.nextInt();
            checkingIfPresent(sc, index, occupiedPosition);
        }
        return index;
    }
}

class PlayingTTT {

    // function to re-set the board.
    public void settingBoard(String[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = " ";
            }
        }
    }

    public String[] choosingSymbol(boolean toss, Scanner sc) {
        String[] arr = new String[2];
        if (toss) {
            System.out.println("Enter a symbol");
            arr[0] = sc.nextLine();
            if (arr[0] == "X") {
                arr[1] = "O";
            } else {
                arr[1] = "X";
            }
        } else {
            if (decidingToss()) {
                arr[0] = "X";
                arr[1] = "O";
            } else {
                arr[0] = "O";
                arr[1] = "X";
            }
        }
        return arr;
    }

    // function to create a random toss
    public boolean decidingToss() {
        Random r = new Random();
        return r.nextBoolean();
    }

    // function to display the board.
    public void displayBoard(String[][] board) {
        String str = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (j != 2) {
                    str += board[i][j] + "|";
                } else {
                    str += board[i][j];
                }
            }
            if (i != 2) {
                str += "\n-+-+-\n";
            }

        }
        System.out.println("\n" + str);
    }

    public boolean checkForWin(List<Integer> board) {
        if (checkRowsForWin(board) || checkColumnsForWin(board) || checkDiagonalsForWin(board)) {
            return true;
        }
        return false;
    }

    private boolean checkRowsForWin(List<Integer> board) {
        List<Integer> row0 = new ArrayList<>();
        row0.add(1);
        row0.add(2);
        row0.add(3);
        List<Integer> row1 = new ArrayList<>();
        row1.add(4);
        row1.add(5);
        row1.add(6);
        List<Integer> row2 = new ArrayList<>();
        row2.add(7);
        row2.add(8);
        row2.add(9);
        if (board.containsAll(row0) || board.containsAll(row1) || board.containsAll(row2)) {
            return true;
        }
        return false;
    }

    private boolean checkColumnsForWin(List<Integer> board) {
        List<Integer> col0 = new ArrayList<>();
        col0.add(1);
        col0.add(4);
        col0.add(7);
        List<Integer> col1 = new ArrayList<>();
        col1.add(2);
        col1.add(5);
        col1.add(8);
        List<Integer> col2 = new ArrayList<>();
        col2.add(3);
        col2.add(6);
        col2.add(9);
        if (board.containsAll(col0) || board.containsAll(col1) || board.containsAll(col2)) {
            return true;
        }
        return false;
    }

    private boolean checkDiagonalsForWin(List<Integer> board) {
        List<Integer> dig0 = new ArrayList<>();
        dig0.add(1);
        dig0.add(5);
        dig0.add(9);
        List<Integer> dig1 = new ArrayList<>();
        dig1.add(4);
        dig1.add(5);
        dig1.add(6);
        if (board.containsAll(dig0) || board.containsAll(dig1)) {
            return true;
        }
        return false;
    }

    public boolean checkForDraw(String[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == " ") {
                    return false;
                }
            }
        }
        return true;
    }

    public String[][] settingSymbol(String[][] board, String symbol, int index) {
        index -= 1;
        int x = (int) Math.floor(index / 3);
        int y = index % 3;
        board[x][y] = symbol;
        return board;
    }
}
