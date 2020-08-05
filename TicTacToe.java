import java.util.*;

class TicTacToe {
    public static void main(String[] args) {
        // creating the board as an 3*3 array
        String[][] board = { { " ", " ", " " }, { " ", " ", " " }, { " ", " ", " " } };

        // creating an object of PlayingTTT class.
        PlayingTTT play = new PlayingTTT();

        // calling the function to play the game.
        play.initializeTheGame(board);
    }
}

class PlayingTTT {

    // list of index for player position cpu position and position occupied by both
    // of them.
    List<Integer> PlayerPosition = new ArrayList<>();
    List<Integer> cpuPosition = new ArrayList<>();
    List<Integer> occupiedPosition = new ArrayList<>();

    // list of condition to help decide the the next best move to win.
    List<Integer> corner = new ArrayList<>(Arrays.asList(1, 3, 7, 9));
    List<Integer> side = new ArrayList<>(Arrays.asList(2, 4, 6, 8));
    List<List<Integer>> winningConditionList = new ArrayList<>();

    String[] SymbolArray = new String[2];
    List<Integer> random = new ArrayList<>();

    /**
     * function to play the game.
     * 
     * @param board - board with the index used as the game board.
     */
    public void initializeTheGame(String[][] board) {

        Scanner sc = new Scanner(System.in);

        // calling a function to decide who will play first amd there symbol choice.
        boolean toss = decidingToss();

        // calling a function to choose the player and cpu symbol.
        SymbolArray = choosingSymbol(toss, sc, SymbolArray);
        String playerSymbol = SymbolArray[0];
        String cpuSymbol = SymbolArray[1];

        // setting winning condition for both opponents.
        boolean playerResult = false;
        boolean cpuResult = false;

        // playing till either of the competitor win or tie.
        do {
            // if toss is 'true' it will be player turn and if it if 'false' it will be
            // cpu's.
            if (toss) {

                // calling a function to display the board.
                displayBoard(board);

                // calling a function to get cell index from the user.
                int index = playerNextMove(sc);

                occupiedPosition.add(index);
                PlayerPosition.add(index);

                // calling a function for setting the symbol at the given index on the board.
                board = settingSymbol(board, playerSymbol, index);

                // calling a function to check for the winning condition.
                playerResult = checkForWin(PlayerPosition);
                if (playerResult) {
                    System.out.println("\nPlayer Wins");
                    break;
                }
            } else {

                // calling a function to get cell index from the cpu.
                int index = cpuNextMove(occupiedPosition, PlayerPosition, cpuPosition);

                cpuPosition.add(index);
                occupiedPosition.add(index);

                // calling a function for setting the symbol at the given index on the board.
                board = settingSymbol(board, cpuSymbol, index);

                // calling a function to check for the winning condition.
                cpuResult = checkForWin(cpuPosition);
                if (cpuResult) {
                    System.out.println("\ncpu Wins");
                    break;
                }
            }

            // checking for draw.
            if (occupiedPosition.size() == 9) {
                System.out.println("\nIt's a draw");
                break;
            }
            toss = !toss;
        } while (playerResult == false && cpuResult == false);
        displayBoard(board);
        sc.close();
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
     * function to get the cell index from the user.
     * 
     * @param sc - object of scanner class, takes input from the user.
     */
    public int playerNextMove(Scanner sc) {
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
    public int cpuNextMove(List<Integer> occupiedPosition, List<Integer> playerPosition, List<Integer> cpuPosition) {

        int index = 0;

        int firstIndex = index;

        // calling a function to get a winning chance.
        index = possibleBestPosition(cpuPosition, occupiedPosition, index);
        if (firstIndex != index) {
            return index;
        }

        // calling a function to stop my opponent from winning.
        index = possibleBestPosition(playerPosition, occupiedPosition, index);
        if (firstIndex != index) {
            return index;
        }

        // calling a function to get a corner index if not filled.
        while (occupiedPosition.containsAll(corner) == false) {
            index = possiblePosition(occupiedPosition, random, index, corner);
            if (firstIndex != index) {
                return index;
            }
        }

        if (occupiedPosition.contains(5) == false) {
            return 5;
        }

        // calling a function to get a corner index if not filled.
        while (occupiedPosition.containsAll(side) == false) {
            index = possiblePosition(occupiedPosition, random, index, side);
            if (firstIndex != index) {
                return index;
            }
        }
        return index;
    }

    /**
     * function to return random number.
     * 
     * @param maxRandom - greatest random number to be generated.
     */
    private int generateRandom(int maxRandom) {
        Random r = new Random();
        return r.nextInt(maxRandom);
    }

    /**
     * function to decide in index for corner or side.
     * 
     * @param occupiedPosition - index's of the occupiedPosition in the game board.
     * @param random           - list of random index for corner and side.
     * @param index            - given index.
     * @param cornerOrIndex    - decide which to check position for.
     */
    private int possiblePosition(List<Integer> occupiedPosition, List<Integer> random, int index,
            List<Integer> toCompare) {

        random.clear();
        while (random.size() <= 4) {
            int r = generateRandom(4);
            while (random.contains(r)) {
                r = generateRandom(4);
            }
            if (occupiedPosition.contains(toCompare.get(r))) {
                random.add(r);
                break;
            } else {
                return toCompare.get(r);
            }
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
    private int possibleBestPosition(List<Integer> position, List<Integer> occupiedPosition, int index) {
        List<List<Integer>> winning = winningCondition();
        // variables to calculate index and count the number of matching element.
        int k = 0;

        while (k < 8) {
            int matchCount = 0;
            List<Integer> unMatched = new ArrayList<>();
            for (int i = 0; i < winning.get(k).size(); i++) {
                if (occupiedPosition.contains(winning.get(k).get(i))) {
                    if (position.contains(winning.get(k).get(i))) {
                        matchCount++;
                    }
                } else {
                    unMatched.add(winning.get(k).get(i));
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

    /**
     * function to check for the winning condition.
     */
    public boolean checkForWin(List<Integer> board) {

        List<List<Integer>> win = winningCondition();
        for (int i = 0; i < win.size(); i++) {
            if (board.containsAll(win.get(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * function to generate a List of winning condition.
     */
    public List<List<Integer>> winningCondition() {
        winningConditionList.add(Arrays.asList(1, 2, 3));
        winningConditionList.add(Arrays.asList(4, 5, 6));
        winningConditionList.add(Arrays.asList(7, 8, 9));
        winningConditionList.add(Arrays.asList(1, 4, 7));
        winningConditionList.add(Arrays.asList(2, 5, 8));
        winningConditionList.add(Arrays.asList(3, 6, 9));
        winningConditionList.add(Arrays.asList(1, 5, 9));
        winningConditionList.add(Arrays.asList(3, 5, 7));

        return winningConditionList;
    }

    /**
     * function to to set symbol as per players requirement.
     * 
     * @param board  - board with the cells block used as the game board.
     * @param symbol - a letter which is to be set on board.
     * @param index  - place where the symbol will be set.
     */
    public String[][] settingSymbol(String[][] board, String symbol, int index) {
        index -= 1;
        int x = (int) Math.floor(index / 3);
        int y = index % 3;
        board[x][y] = symbol;
        return board;
    }
}