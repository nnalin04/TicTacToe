class TicTacToe {
    public static void main(String[] args) {

        //creating an object of PlayingTTT class.
        PlayingTTT play = new PlayingTTT();

        //creating the board as an 3*3 array
        String[][] board = new String[3][3];
        
        //calling the function to set the re-set the board.
        play.settingBoard(board);
    }
}

class PlayingTTT {

    //function to re-set the board.
    public void settingBoard(String[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = ". ";
            }
        }
    }
}
