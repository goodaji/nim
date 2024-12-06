import java.util.Arrays;

public class Test {

    public static void main(String[] args) {
        int[] players = new int[] { 2, 3 };
        int[] startingBoard = new int[] { 1, 1, 1, 1, 1 };
        int maxTake = 5;
        int winner;
        int games = 0;
        int optimalWinner;
        int p1games = 0;
        int p2games = 0;
        int p1winp1 = 0;
        int p2winp1 = 0;
        int p1winp2 = 0;
        int p2winp2 = 0;

        Game nim = new Game(startingBoard, false, players, maxTake);
        
        for (int p1 = 1; p1 < 6; p1++) {
            for (int p2 = p1+1; p2 < 7; p2++) {
                for (int p3 = p2+1; p3 < 8; p3++) {
                    for (int p4 = p3+1; p4 < 9; p4++) {
                        for (int p5 = p4+1; p5 < 10; p5++) {
                            startingBoard[0] = p1;
                            startingBoard[1] = p2;
                            startingBoard[2] = p3;
                            startingBoard[3] = p4;
                            startingBoard[4] = p5;
                            nim.updateBoard(startingBoard);
                            optimalWinner = nim.optimalPlayWinner();
                            if (optimalWinner == 1) {
                                p1games++;
                            } else {
                                p2games++;
                            }
                            games++;
                            System.out.print("Playing game " + games + ": ");
                            System.out.print(" board= " + Arrays.toString(nim.getBoard()));
                            winner = nim.playGame();
                            if (winner == 1) {
                                if (optimalWinner == 1) {
                                    p1winp1++;
                                } else {
                                    p1winp2++;
                                }
                            } else {
                                if (optimalWinner == 1) {
                                    p2winp1++;
                                } else {
                                    p2winp2++;
                                }
                            }
                            System.out.print("  optimal winner: " + optimalWinner);
                            System.out.println("  actual winner: " + winner);
                        }
                    }
                }
            }
        }
        System.out.println("p1games=" + p1games +
                            " p1wins=" + p1winp1 + 
                            " p2wins=" + p2winp1);
        System.out.println("p2games=" + p2games +
                            " p1wins=" + p1winp2 +
                            " p2wins=" + p2winp2);
    }
}