import java.util.Random;

class TicTacToe {

    public  static void main(final String[] args) {

        //creating an object of PlayingTTT class.
        final PlayingTTT play = new PlayingTTT();

        //creating the board as an 3*3 array
        final String[][] board = new String[3][3];
        
        //calling the function to set the re-set the board.
        play.settingBoard(board);

        String firstPlay = "";

        //calling a function to decide who will play first.
        if(play.decidingToss()){
            firstPlay = firstPlay+"player";
        }else{
            firstPlay = firstPlay+"computer";
        }
    }
}

class PlayingTTT {

    //function to re-set the board.
    public void settingBoard(final String[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = ". ";
            }
        }
    }

    //function to create a random toss
    public boolean decidingToss(){
        final Random r = new Random();
        return r.nextBoolean();
    }
}
