import java.util.Scanner;

public class Game {
    private int[] board;
    private int playerTurn;
    private static int maxTake = 3;
    private int[] players = new int[] { 0, 0 };
    private boolean interactive;

    private Ai[] cpu = new Ai[2];

    public Game(int[] board, boolean interactive, int[] players) {
        this.board = board;
        this.players = players;
        this.interactive = interactive;
        this.playerTurn = 0;

        selectPlayers();
    }

    private void selectPlayers() {
        Scanner input = new Scanner(System.in);
        boolean validEntry;

        for (int i = 0; i < 2; i++) {
            validEntry = false;
            while (!validEntry) {
                if (interactive) {
                    System.out.println("Select player " + (i+1));
                    System.out.println("------------------------");
                    System.out.println("0: Human");
                    System.out.println("1: CPU difficulty easy");
                    System.out.println("2: CPU difficulty hard");
                    System.out.println("3: CPU difficulty expert");
                    players[i] = input.nextInt();
                }
                if (players[i] >= 0 || players[i] <= 3) {
                    if (interactive) confirmPlayerSelect(i+1, players[i]);
                    this.cpu[i] = new Ai(players[i]); 
                    validEntry = true;
                } else {
                    System.out.println("Invalid entry");
                }
            }
        }
    }

    private void confirmPlayerSelect(int player, int difficulty) {
        System.out.print("Player " + player + " is ");
        switch (difficulty) {
            case 0:
                System.out.println("Human");
                break;
            case 1:
                System.out.println("CPU easy difficulty");
                break;
            case 2:
                System.out.println("CPU hard difficulty");
                break;
            case 3:
                System.out.println("CPU expert difficulty");
                break;
            default:
                break;
        }
    }

    public int playGame() {
        int[] action = new int[] { 0, 0 };
        if (interactive) {
            System.out.println("Beginning game");
            System.out.println("--------------");
        }
        while (!gameOver()) {
            if (interactive) {
                drawBoard();
                printPlayerTurn();
            }
            switch (players[this.playerTurn]) {
                case 0:
                    action = getHumanMove();
                    break;
                case 1:
                    action = cpu[this.playerTurn].randomMove(this);
                    break;
                case 2:
                case 3:
                    action = cpu[this.playerTurn].searchForMove(this);
                    break;
            }
            if (interactive) printAction(action[0], action[1]);
            takeSticks(action[0], action[1]);
        }
        return (playerTurn + 1); // winner
    }

    private int[] getHumanMove() {
        Scanner input = new Scanner(System.in);
        int[] action = new int[] { 0, 0 };

        System.out.print("Enter pile and number of sticks: ");
        action[0] = input.nextInt();
        action[1] = input.nextInt();

        return action;        
    }

    private void printAction(int pile, int sticks) {
        System.out.print("Player " + (playerTurn+1) + getPlayerDesc(players[playerTurn]) + 
                            " took " + sticks + " stick");
        if (sticks > 1) {
            System.out.print("s");
        }
        System.out.println(" from pile " + pile );
    }

    private String getPlayerDesc(int player) {
        switch (player) {
            case 1:
                return " (CPU-easy)";
            case 2:
                return " (CPU-hard)";
            case 3:
                return " (CPU-expert)";
            default:
                return "";
        }
    }

    private void drawBoard() {
        for (int i = 0; i < board.length; i++) {
            System.out.print(i+1 + ": ");
            for (int j = 0; j < board[i]; j++) {
                System.out.print("|");
            }
            System.out.println("        (" + board[i] + ")");
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
    
    public void takeSticks(int pile, int sticks) {
        if (pile < 1 || pile > board.length) {
            System.out.println("Invalid pile. Try again.");
            return;
        }
        if (sticks < 1 || sticks > board[pile - 1]) {
            System.out.println("Invalid number of sticks. Try again.");
            return;
        }
        if (sticks > getMaxTake()) {
            System.out.println("Number of sticks exceeds maximum (" + getMaxTake() + "). Try again.");
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
        int losingPlayer = (playerTurn + 1) % 2;
        int winningPlayer = (playerTurn);
        if (interactive) {
            System.out.print("Player " + (losingPlayer+1) + getPlayerDesc(players[losingPlayer]) + " took the last stick. ");
            System.out.println("Player " + (winningPlayer+1) + getPlayerDesc(players[winningPlayer]) + " wins the game!");
        }
    }
}
