public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to Nim!");

        int[] startingBoard = new int[] { 1, 3, 5, 7, 9 };
        int[] players = new int[] { 0, 0 };
        boolean interactive = true;
        int maxTake = 5;

        Game nim = new Game(startingBoard, interactive, players, maxTake);
        nim.playGame();
        nim.printWinner();
    }

}
