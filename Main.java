public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to Nim!");

        Game nim = new Game();
        nim.playGame();
        nim.printWinner();
    }

}
