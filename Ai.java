import java.util.Arrays;

public class Ai {
    private int level;
    private final int maxDepth = 3;

    public record State(int[] board, int score) {}


    public Ai(int level) {
        this.level = level;
    }

    public int[] searchForMove(Game nim) {
        int[] board  = nim.getBoard();

        int pileStar = 0;
        int sticksStar = 0;
        int valueStar;
        int value;

        if (nim.getPlayerTurn() == 1) {
            // maximize value for player 1 (2)
            valueStar = Integer.MIN_VALUE;
            // iterate through all actions
            for (int pile = 1; pile <= board.length; pile++) {
                for (int sticks = 1; sticks <= board[pile-1]; sticks++) {
                    value = Integer.MIN_VALUE;
                    nim.takeSticks(pile, sticks);
                    value = minValue(nim, pile, sticks, 1); // initial depth = 1
                    if (value > valueStar) {
                        pileStar = pile;
                        sticksStar = sticks;
                        valueStar = value;
                    }
                    nim.replaceSticks(pile, sticks);
                }
            }
        } else {
            // minimize value for player 0 (1)
            valueStar = Integer.MAX_VALUE;
            // iterate through all actions
            for (int pile = 1; pile <= board.length; pile++) {
                for (int sticks = 1; sticks <= board[pile-1]; sticks++) {
                    value = Integer.MAX_VALUE;
                    nim.takeSticks(pile, sticks);
                    value = maxValue(nim, pile, sticks, 1); // initial depth = 1
                    if (value < valueStar) {
                        pileStar = pile;
                        sticksStar = sticks;
                        valueStar = value;
                    }
                    nim.replaceSticks(pile, sticks);
                }
            }
        }

        return new int[] {pileStar, sticksStar};
    }

    private int maxValue(Game nim, int pile, int sticks, int depth) {
        int[] board = nim.getBoard();

        int value;

        // end of game test
        if (nim.gameOver()) {
            return 1;
        }
        // max depth test
        if (depth > maxDepth) {
            value = eval(board, nim.getMaxTake());
            return value;
        }

        // initialize evaluation score
        value = Integer.MIN_VALUE;

        for (int i = 1; i <= board.length; i++) {
            for (int j = 1; j <= board[i-1]; j++) {
                nim.takeSticks(i, j);
                // check max of action evaluation against current best
                value = Math.max(value, minValue(nim, i, j, depth+1));
                nim.replaceSticks(i, j);
            }
        }
        return value;
    }

    private int minValue(Game nim, int pile, int sticks, int depth) {
        int[] board = nim.getBoard();

        int value;

        // end of game test
        if (nim.gameOver()) {
            return -1;
        }
        // max depth test
        if (depth > maxDepth) {
            value = -eval(board, nim.getMaxTake());
            return value;
        }

        // initialize evaluation score
        value = Integer.MAX_VALUE;

        for (int i = 1; i <= board.length; i++) {
            for (int j = 1; j <= board[i-1]; j++) {
                nim.takeSticks(i, j);
                // check min of action evaluation against current best
                value = Math.min(value, maxValue(nim, i, j, depth+1));
                nim.replaceSticks(i, j);
            }
        }
        return value;
    }

    private int eval(int[] board, int maxTake) {
        int sumBoard = sumArray(board);
        int maxBoard = maxArray(board);
        int xorBoard = xorArray(board);

        if (maxBoard == 1) {
            if ((sumBoard % 2) == 1) {
                return -1;
            } else {
                return 1;
            }
        } else if (xorBoard % (maxTake+1) == 0) {
            return -1;
        } else {
            return 1;
        }
    }

    private static int sumArray(int arr[]) {
        int sum = 0;
        for (int i : arr)
            sum += i;
        return sum;
    }

    private static int maxArray(int arr[]) {
        int max = 0;
        for (int i : arr)
            max = Math.max(max, i);
        return max;
    }

    private static int xorArray(int arr[]) {
        int xorArr = 0;
        for (int i = 0; i < arr.length; i++) {
            xorArr = xorArr ^ arr[i];
        }
        return xorArr;
    }
}