import java.util.Scanner;
import java.util.Arrays;

public class Main {
    private final static int HUMAN = 0;
    private final static int CPU = 1;

    public static void main(String[] args) {
        Game nim = new Game();
        int player1 = HUMAN; // Set both players to HUMAN initially
        int player2 = HUMAN;
        int difficulty1 = 0;
        int difficulty2 = 0;
    
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to Nim!");
        System.out.print("First player (enter 0 for human or any other number for CPU): ");
        if (input.nextInt() != 0) {
            player1 = CPU;
            while (difficulty1 < 1 || difficulty1 > 3) {
                System.out.print("Enter CPU difficulty level (1=Easy, 2=Hard, 3=Expert): ");
                difficulty1 = input.nextInt();
                if (difficulty1 < 1 || difficulty1 > 3) {
                    System.out.println("Invalid entry.");
                }
            }
        }

        System.out.print("Second player (enter 0 for human or any other number for CPU): ");
        if (input.nextInt() != 0) {
            player2 = CPU;
            while (difficulty2 < 1 || difficulty2 > 3) {
                System.out.print("Enter CPU difficulty level (1=Easy, 2=Hard, 3=Expert): ");
                difficulty2 = input.nextInt();
                if (difficulty2 < 1 || difficulty2 > 3) {
                    System.out.println("Invalid entry.");
                }
            }
        }

        // Initialize AI instances
        Ai cpu1 = new Ai(difficulty1);
        Ai cpu2 = new Ai(difficulty2);

        int[] action = new int[] { 0, 0 };
        int pile;
        int sticks;

        while (nim.gameOver() == false) {
            nim.drawBoard();
            nim.printPlayerTurn();
      
            if (nim.getPlayerTurn() == 0) {
                if (player1 == CPU) {
                    action = cpu1.getMove(nim);
                    pile = action[0];
                    sticks = action[1];
                    System.out.println("Player 1 (CPU) takes " + sticks + " sticks from pile " + pile);
                } else {
                    System.out.println("Enter pile and number of sticks");
                    pile = input.nextInt();
                    sticks = input.nextInt();
                    System.out.println("Player 1 (human) takes " + sticks + " sticks from pile " + pile);
                }
            } else {
                if (player2 == CPU) {
                    action = cpu2.getMove(nim);
                    pile = action[0];
                    sticks = action[1];
                    System.out.println("Player 2 (CPU) takes " + sticks + " sticks from pile " + pile);
                } else {
                    System.out.println("Enter pile and number of sticks");
                    pile = input.nextInt();
                    sticks = input.nextInt();
                    System.out.println("Player 2 (human) takes " + sticks + "sticks from pile " + pile);
                }
            }
            nim.takeSticks(pile, sticks);
        }
        nim.printWinner();
    }

}
