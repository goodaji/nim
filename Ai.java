import java.util.Arrays;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class Ai {
    private int difficulty;
    private final static int maxDepth = 2; // must be even for HARD difficulty

    // AI difficulty levels
    private final static int EASY = 1;
    private final static int HARD = 2;
    private final static int EXPERT = 3;

    public record State(int[] board, int score) {}

    public Ai(int difficulty) {
        this.difficulty = difficulty;
    }

    public int[] getMove(Game nim) {
        switch(difficulty) {
            case EASY :
                return randomMove(nim);
            case HARD :
                return searchForMove(nim);
            case EXPERT :
                return searchForMove(nim);
        }
        return new int[] { 0, 0 };
    }
        
    public int[] randomMove(Game nim) {
        int[] board = nim.getBoard();
        int maxTake = nim.getMaxTake();

        Random random = new Random();
        List<Integer> nonEmptyPiles = new ArrayList<>();
    
        for (int i = 0; i < board.length; i++) {
            if (board[i] > 0) {
                nonEmptyPiles.add(i);
            }
        }

        int pile = nonEmptyPiles.get(random.nextInt(nonEmptyPiles.size())) + 1;
        int sticks = random.nextInt(Math.min(maxTake, board[pile-1])) + 1;

        return new int[] { pile, sticks }; 
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
                    value = minValue(nim, Integer.MIN_VALUE, Integer.MAX_VALUE, 1); // initial depth = 1
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
                    value = maxValue(nim, Integer.MIN_VALUE, Integer.MAX_VALUE, 1); // initial depth = 1
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

    private int maxValue(Game nim, int alpha, int beta, int depth) {
        int[] board = nim.getBoard();

        int value;

        // end of game test
        if (nim.gameOver()) {
            return 1;
        }
        // max depth test
        if (depth > maxDepth) {
            if (difficulty == EXPERT) {
                value = expertEval(board, nim.getMaxTake());
            } else {
                value = hardEval(board);
            }
            return value;
        }

        // initialize evaluation score
        value = Integer.MIN_VALUE;

        for (int i = 1; i <= board.length; i++) {
            for (int j = 1; j <= board[i-1]; j++) {
                nim.takeSticks(i, j);
                // check max of action evaluation against current best
                value = Math.max(value, minValue(nim, alpha, beta, depth+1));
                if (value >= beta) {
                    nim.replaceSticks(i, j);
                    return value;
                }
                alpha = Math.max(alpha, value);
                nim.replaceSticks(i, j);
            }
        }
        return value;
    }

    private int minValue(Game nim, int alpha, int beta, int depth) {
        int[] board = nim.getBoard();

        int value;

        // end of game test
        if (nim.gameOver()) {
            return -1;
        }
        // max depth test
        if (depth > maxDepth) {
            if (difficulty == EXPERT) {
                value = -expertEval(board, nim.getMaxTake());
            } else {
                value = -hardEval(board);
            }
            return value;
        }

        // initialize evaluation score
        value = Integer.MAX_VALUE;

        for (int i = 1; i <= board.length; i++) {
            for (int j = 1; j <= board[i-1]; j++) {
                nim.takeSticks(i, j);
                // check min of action evaluation against current best
                value = Math.min(value, maxValue(nim, alpha, beta, depth+1));
                if (value <= alpha) {
                    nim.replaceSticks(i, j);
                    return value;
                }
                beta = Math.min(beta, value);
                nim.replaceSticks(i, j);
            }
        }
        return value;
    }

    private int expertEval(int[] board, int maxTake) {
        int endgame = endgameEval(board);
        if (endgame != 0) {
            return endgame;
        }

        int xorBoard = xorArray(board);

        if (xorBoard % (maxTake+1) == 0) {
            return -1;
        } else {
            return 1;
        }
    }

    private int hardEval(int[] board) {
        // Note: max search depth must be even for this evaluation function to work
        int endgame = endgameEval(board);
        if (endgame != 0) {
            return endgame;
        }

        int count;

        for (int i : board) {
            if (i == 0) {
                continue;
            }
            count = 0;
            for (int j : board) {
                if (i == j) {
                    count++;
                }
            }
            if (count % 2 == 1) {
                return 1;
            }
        }
        return -1;
    }

    private int endgameEval(int[] board) {
        int sumBoard = sumArray(board);
        int maxBoard = maxArray(board);

        // All piles have at most 1 stick
        if (maxBoard == 1) {
            if ((sumBoard % 2) == 1) {
                return -1;
            } else {
                return 1;
            }
        }
        // Only 1 pile with any sticks
        for (int i : board) {
            if (i == sumBoard) {
                return 1;
            }
        }
        return 0;
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
