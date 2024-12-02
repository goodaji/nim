public class Game {
    private int[] board;
    private int playerTurn;
    private static int maxTake = 7;

    public Game() {
        this.board = new int[] { 1, 3, 4, 5, 6 };
        this.playerTurn = 0;
    }

    public void drawBoard() {
        for (int i = 0; i < board.length; i++) {
            System.out.print(i+1 + ": ");
            for (int j = 0; j < board[i]; j++) {
                System.out.print("|");
            }
            System.out.println();
        }
    }

    public int[] getBoard() {
        return board;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public int getMaxTake() {
        return maxTake;
    }
public int[] randomPick(nim.getBoard) {
        Random random = new Random();
        
        for (int i = 0; i < piles.length; i++) {
            if (piles[i] > 0) {
                int sticksToRemove;
                if (piles[i] > 3) {
                    sticksToRemove = random.nextInt(3) + 1;
                } else if (piles[i] > 1) {
                    sticksToRemove = random.nextInt(2) + 1;
                } else {
                    sticksToRemove = 1;
                }
                
                piles[i] -= sticksToRemove; 
                
                return new int[] { i, sticksToRemove };
            }
        }
        return new int[] { -1, 0 }; 
    }
    public void takeSticks(int pile, int sticks) {
        if (pile < 1 || pile > board.length) {
            System.out.println("Invalid pile");
            return;
        }
        if (sticks < 1 || sticks > board[pile - 1]) {
            System.out.println("Invalid number of sticks");
            return;
        }
        board[pile - 1] -= sticks;
        playerTurn = (playerTurn + 1) % 2;
    }

    public void replaceSticks(int pile, int sticks) {
        board[pile - 1] += sticks;
        playerTurn = (playerTurn + 1) % 2;
    }

    public boolean gameOver() {
        int sum = 0;
        for (int i : board) {
            sum += i;
        }
        if (sum == 0) 
            return true;
        else          
            return false;
    }

    public void printPlayerTurn() {
        System.out.println("It's player " + (playerTurn+1) + "'s turn");
    }

    public void printWinner() {
        System.out.print("Player " + ((playerTurn+1)%2+1) + " took the last stick. ");
        System.out.println("Player " + (playerTurn+1) + " wins the game!");
    }
}
