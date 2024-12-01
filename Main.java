import java.util.Scanner;
import java.util.Arrays;

public class Main {
  public static void main(String[] args) {
    Game nim = new Game();
    
    Scanner input = new Scanner(System.in);

    System.out.println("Enter CPU difficulty level (1=Hard; 2=Expert)");
    int difficulty = input.nextInt();

    Ai cpu1 = new Ai(difficulty);
    Ai cpu2 = new Ai(difficulty);

    int[] action = new int[] { 0, 0 };
    int pile;
    int sticks;

    while (nim.gameOver() == false) {
      nim.drawBoard();
      nim.printPlayerTurn();
      
      if (nim.getPlayerTurn() == 0) {
        action = cpu1.searchForMove(nim);
        pile = action[0];
        sticks = action[1];
        System.out.println("CPU takes " + sticks + " sticks from pile " + pile);
      } else {
        System.out.println("Enter pile and number of sticks");
        pile = input.nextInt();
        sticks = input.nextInt();
        System.out.println("Player takes " + sticks + " sticks from pile " + pile);
      }
      nim.takeSticks(pile, sticks);
    }
    nim.printWinner();
  }
}
