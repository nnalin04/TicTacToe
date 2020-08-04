import java.util.*;

class TicTacToe {

    public static void main(String[] args) {
        // creating the board as an 3*3 array
        String[][] board = new String[3][3];

        // creating an object of PlayingTTT class.
        PlayingTTT play = new PlayingTTT();

        Scanner sc = new Scanner(System.in);

        // calling the function to play the game.
        playingTheGame(board, play, sc);

        sc.close();
    }

    //
    /**
     * function to play the game.
     * 
     * @param board - board with the index used as the game board.
     * @param play  - object of the PlayingTTT class to use its method.
     * @param sc    - object of scanner class to take input from the user.
     */
    public static void playingTheGame(String[][] board, PlayingTTT play, Scanner sc) {

        List<Integer> PlayerPosition = new ArrayList<>();
        List<Integer> position = new ArrayList<>();
        List<Integer> occupiedPosition = new ArrayList<>();

        // calling the function to set the re-set the board.
        play.settingBoard(board);

        // calling a function to decide who will play first amd there symbol choice.
        boolean toss = play.decidingToss();

        // calling a function to choose the player and cpu symbol.
        String[] SymbolArray = new String[2];
        SymbolArray = play.choosingSymbol(toss, sc, SymbolArray);
        String playerSymbol = SymbolArray[0];
        String cpuSymbol = SymbolArray[1];

        // setting winning condition for both opponents.
        boolean playerResult = false;
        boolean cpuResult = false;

        // playing till either of the competitor win or tie.
        do {
            // if toss is true it will be player turn.
            if (toss) {

                // calling a function to display the board.
                play.displayBoard(board);

                // calling a function to get cell index from the user.
                int index = checkingIfPresent(sc, occupiedPosition);

                occupiedPosition.add(index);
                PlayerPosition.add(index);

                // calling a function for setting the symbol at the given index.
                board = play.settingSymbol(board, playerSymbol, index);

                // calling a function to check for the winning condition.
                playerResult = play.checkForWin(PlayerPosition);
                if (playerResult) {
                    System.out.println("Player Wins");
                    break;
                }
                toss = false;

            } else {
                // calling a function to get cell index from the cpu.
                int index = cpuMove(occupiedPosition, PlayerPosition, position);

                position.add(index);
                occupiedPosition.add(index);

                // calling a function for setting the symbol at the given index.
                board = play.settingSymbol(board, cpuSymbol, index);

                // calling a function to check for the winning condition.
                cpuResult = play.checkForWin(position);
                if (cpuResult) {
                    System.out.println("cpu Wins");
                    System.out.println(position);
                    break;
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
    }

    /**
     * function to get the cell index from the user.
     * 
     * @param sc               - object of scanner class, takes input from the user.
     * @param occupiedPosition - index's of the occupiedPosition in the game board.
     */
    private static int checkingIfPresent(Scanner sc, List<Integer> occupiedPosition) {
        System.out.println("enter a the position you want to place your symbol, between 1-9");
        int index = sc.nextInt();
        while (occupiedPosition.contains(index)) {
            System.out.println("enter a different position " + index + " is already present");
            index = sc.nextInt();
        }
        return index;
    }

    /**
     * function to get the cell index from the computer.
     * 
     * @param occupiedPosition - index's of the occupiedPosition in the game board.
     * @param playerPosition   - index's of the position used by the user.
     * @param position         - index's of the position used by the CPU.
     */
    private static int cpuMove(List<Integer> occupiedPosition, List<Integer> PlayerPosition, List<Integer> position) {

        Random r = new Random();
        int index = r.nextInt(9) + 1;
        while (occupiedPosition.contains(index)) {
            index = r.nextInt(9) + 1;
        }

        int firstIndex = index;

        index = possibleBestPosition(position, occupiedPosition, index);
        if (firstIndex != index) {
            return index;
        }

        return index;
    }

    /**
     * function to get the best possible index where the symbol can be placed on the
     * board to win or stop the opponent from winning.
     * 
     * @param position         - list of index from which we have to compare.
     * @param occupiedPosition - index's of the occupiedPosition in the game board.
     * @param index            - taking index for comparison.
     */
    private static int possibleBestPosition(List<Integer> position, List<Integer> occupiedPosition, int index) {
        int[][] winning = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 1, 4, 7 }, { 2, 5, 8 }, { 3, 6, 9 }, { 1, 5, 9 },
                { 3, 5, 7 } };

        // variables to calculate index and count the number of matching element.
        int k = 0;
        List<Integer> unMatched = new ArrayList<>();

        while (k < 8) {
            int matchCount = 0;
            unMatched.clear();

            for (int i = 0; i < winning[k].length; i++) {
                if (occupiedPosition.contains(winning[k][i])) {
                    if (position.contains(winning[k][i])) {
                        matchCount++;
                    }
                } else {
                    unMatched.add(winning[k][i]);
                }
            }

            if (matchCount == 2 && unMatched.size() == 1) {
                index = unMatched.get(0);
                break;
            }
            k++;
        }
        return index;
    }

}

class PlayingTTT {

    /**
     * function to get the cell index from the user.
     * 
     * @param board - board with the index used as the game board.
     */
    public void settingBoard(String[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = " ";
            }
        }
    }

    /**
     * function to chose a symbol for the computer or the user.
     * 
     * @param toss        - decide who will chose the first symbol.
     * @param sc          - object of scanner class to take input from the user.
     * @param symbolArray - array to take symbol as an input.
     */
    public String[] choosingSymbol(boolean toss, Scanner sc, String[] symbolArray) {
        if (toss) {
            System.out.println("Enter a symbol");
            String symbol = sc.nextLine();
            if (symbol == "X") {
                symbolArray[0] = "O";
                symbolArray[1] = "X";

            } else {
                symbolArray[0] = "X";
                symbolArray[1] = "O";
            }
        } else {
            if (decidingToss()) {
                symbolArray[0] = "X";
                symbolArray[1] = "O";
            } else {
                symbolArray[0] = "O";
                symbolArray[1] = "X";
            }
        }
        return symbolArray;
    }

    /**
     * function to create a random toss
     */
    public boolean decidingToss() {
        Random r = new Random();
        return r.nextBoolean();
    }

    /**
     * function to display the board.
     * 
     * @param board - board with the cells block used as the game board.
     */
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

    /**
     * function to check for the winning condition.
     * 
     * @param board - board with the index used as the game board.
     */
    public boolean checkForWin(List<Integer> board) {
        if (checkRowsForWin(board) || checkColumnsForWin(board) || checkDiagonalsForWin(board)) {
            return true;
        }
        return false;
    }

    /**
     * function to match winning condition for row.
     * 
     * @param board - board with the indexes used as the game board.
     */
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

    /**
     * function to match winning condition for col.
     * 
     * @param board - board with the indexes used as the game board.
     */
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

    /**
     * function to match winning condition for diagonal.
     * 
     * @param board - board with the indexes used as the game board.
     */
    private boolean checkDiagonalsForWin(List<Integer> board) {
        List<Integer> dig0 = new ArrayList<>();
        dig0.add(1);
        dig0.add(5);
        dig0.add(9);
        List<Integer> dig1 = new ArrayList<>();
        dig1.add(3);
        dig1.add(5);
        dig1.add(7);
        if (board.containsAll(dig0) || board.containsAll(dig1)) {
            return true;
        }
        return false;
    }

    /**
     * function to set the board to ' ' as every element.
     * 
     * @param board - board with the cells block used as the game board.
     */
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

    /**
     * function to to set symbol as per players requirement.
     * 
     * @param board - board with the cells block used as the game board.
     * @param symbol - a letter which is to be set on board.
     * @param index - place where the symbol will be set.
     */
    public String[][] settingSymbol(String[][] board, String symbol, int index) {
        index -= 1;
        int x = (int) Math.floor(index / 3);
        int y = index % 3;
        board[x][y] = symbol;
        return board;
    }
}