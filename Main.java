import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Game nim = new Game();
    nim.drawBoard();
    
    Scanner input = new Scanner(System.in);

    Ai cpu = new Ai(0);

    cpu.createSearchTree(nim);

    while (nim.gameOver() == false) {
      nim.printPlayerTurn();
      System.out.println("Enter pile and number of sticks");
      int pile = input.nextInt();
      int sticks = input.nextInt();

      nim.takeSticks(pile, sticks);
      nim.drawBoard();
    }
    nim.printWinner();
  }
}
