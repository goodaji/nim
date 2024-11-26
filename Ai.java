import java.util.Arrays;

public class Ai {
    private int level;
    private final int maxDepth = 1;

    public record State(int[] board, int score) {}


    public Ai(int level) {
        this.level = level;
    }

    public void createSearchTree(Game nim) {
        int depth = 0;
        int[] board = nim.getBoard();
        State rootState = new State(board, 0);
        Node<State> root = new Node<State>(rootState);
        populateSearchTree(nim, root, depth);
    }

    public void populateSearchTree(Game nim, Node<State> node, int depth) {
        if (depth > maxDepth)
            return;

        int playerTurn;
        int[] board = nim.getBoard();  
        for (int pile = 1; pile <= board.length; pile++) {
            for (int sticks = 1; sticks <= board[pile-1]; sticks++) {
                playerTurn = nim.getPlayerTurn();

                nim.takeSticks(pile, sticks);
                State currentState = new State(board, 0);
                Node<State> childNode = node.addChild(currentState);
                
                System.out.println("Depth " + depth + ": Player " + (playerTurn+1) + " took " + sticks + 
                                    " sticks from pile " + pile + ": " + Arrays.toString(board));
                
                populateSearchTree(nim, childNode, depth+1);
                nim.replaceSticks(pile, sticks);
            }
        }
    }

    private int eval(int[] board) {
        return 1;
    }
}