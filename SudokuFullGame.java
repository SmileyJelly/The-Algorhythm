import java.util.Random;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class SudokuFullGame {
    private int[][] board;
    private int[][] solution;

    public SudokuFullGame() {
        board = new int[9][9];
    }

    public int[][] getBoard() {
        return board;
    }

    public void printBoard() {
        for (int row = 0; row < 9; row++) {
            if (row % 3 == 0 && row != 0) {
                System.out.println("------+-------+------");
            }
            for (int col = 0; col < 9; col++) {
                if (col % 3 == 0 && col != 0) {
                    System.out.print("| ");
                }
                int val = board[row][col];
                System.out.print(val == 0 ? ". " : val + " ");
            }
            System.out.println();
        }
    }

    public boolean isValidMove(int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num || board[i][col] == num) {
                return false;
            }
        }

        int startRow = row - row % 3;
        int startCol = col - col % 3;

        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (board[i][j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isComplete() {
        for (int[] row : board) {
            for (int val : row) {
                if (val == 0) return false;
            }
        }
        return true;
    }

    public void generatePuzzle(int difficulty) {
        SudokuSolver solver = new SudokuSolver();
        int[][] fullBoard = new int[9][9];
        solver.solve(fullBoard);
        solution = copyBoard(fullBoard);  // full solution
        board = copyBoard(fullBoard);     // puzzle that gets modified

        int cellsToRemove = switch (difficulty) {
            case 1 -> 30; // Easy
            case 2 -> 40; // Medium
            case 3 -> 50; // Hard
            default -> 30;
        };

        Random rand = new Random();
        while (cellsToRemove > 0) {
            int row = rand.nextInt(9);
            int col = rand.nextInt(9);
            if (board[row][col] != 0) {
                board[row][col] = 0;
                cellsToRemove--;
            }
        }
    }

    private int[][] copyBoard(int[][] source) {
        int[][] copy = new int[9][9];
        for (int i = 0; i < 9; i++) {
            System.arraycopy(source[i], 0, copy[i], 0, 9);
        }
        return copy;
    }

    // Solver: Source used link 2 youtube video
    static class SudokuSolver {
        public boolean solve(int[][] board) {
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    if (board[row][col] == 0) {
                        List<Integer> numbers = getShuffledNumber(); // added shuffle method to make puzzle more random

                        for (int num : numbers) {
                            if (isSafe(board, row, col, num)) {
                                board[row][col] = num;
                                if (solve(board)) return true;
                                board[row][col] = 0; // backtrack
                            }
                        }

                        return false; // no number worked here, need to backtrack
                    }
                }
            }
            return true; // puzzle is solved
        }

        public boolean isSafe(int[][] board, int row, int col, int num) {
            for (int i = 0; i < 9; i++) {
                if (board[row][i] == num || board[i][col] == num) return false;
            }

            int startRow = row - row % 3;
            int startCol = col - col % 3;

            for (int i = startRow; i < startRow + 3; i++) {
                for (int j = startCol; j < startCol + 3; j++) {
                    if (board[i][j] == num) return false;
                }
            }

            return true;
        }

        // Source used youtube video 1.
        private List<Integer> getShuffledNumber() { 
            List<Integer> numbers = new ArrayList<>();
            for (int i = 1; i <= 9; i++) {
                numbers.add(i);
            }

            Random rand = new Random();
            for (int i = numbers.size() - 1; i > 0; i--) {
                int j = rand.nextInt(i + 1);
                int temp = numbers.get(i);
                numbers.set(i, numbers.get(j));
                numbers.set(j, temp);
            }

            return numbers;
        }
    }

    public static void main(String[] args) {
        SudokuFullGame board = new SudokuFullGame();
        Scanner input = new Scanner(System.in);

        board.generatePuzzle(3);  // Change difficulty here 1,2,3. It is on 1 by default
        System.out.println("Welcome to Console Sudoku!");
        System.out.println("Type 's' at any time to auto-solve the Sudoku puzzle.\n");

        while (!board.isComplete()) {
            board.printBoard();
            System.out.print("Enter row (1-9) or 's' to solve: ");
            String rowInput = input.next();

            if (rowInput.equalsIgnoreCase("s")) {
                SudokuSolver solver = new SudokuSolver();
                if (solver.solve(board.getBoard())) {
                    System.out.println("\nPuzzle solved:");
                    board.printBoard();
                } else {
                    System.out.println("No solution exists.");
                }
                break;
            }

            int row;
            if (rowInput.matches("[1-9]")) {
                row = Integer.parseInt(rowInput) - 1;
            } else {
                System.out.println("Invalid input. Please enter a number 1-9 or 's'.");
                continue;
            }
            System.out.print("Enter column (1-9): ");
            int col = input.nextInt() - 1;
            System.out.print("Enter number (1-9): ");
            int num = input.nextInt();
            if (row < 0 || row >= 9 || col < 0 || col >= 9 || num < 1 || num > 9) {
                System.out.println("Invalid input. Try again.");
                continue;
            }

            if (board.getBoard()[row][col] != 0) {
                System.out.println("Cell is already filled. Try another cell.");
            } else if (board.isValidMove(row, col, num)) {
                board.getBoard()[row][col] = num;
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }

        if (board.isComplete()) {
            System.out.println("Congratulations! You completed the puzzle:");
            board.printBoard();
        } // Ascii Art Generated
        System.out.println(
                "⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⣀⠀⢀⣶⣿⡛⠛⠋⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠙⠛⢛⣿⣶⡄⠀⣀⠀⠀\n" +
                        "⠀⠀⣿⣧⣼⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣧⣼⣿⠀⠀\n" +
                        "⠀⠀⣿⡏⠉⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠉⢹⣿⠀⠀\n" +
                        "⠀⠀⢻⣧⠀⢹⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡏⠀⣼⡏⠀⠀\n" +
                        "⠀⠀⠘⣿⡄⠀⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠀⢰⣿⠃⠀⠀\n" +
                        "⠀⠀⠀⠹⣷⡀⠈⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠁⢠⣿⡏⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⢻⣿⣄⢀⣻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡀⣠⣿⡟⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠙⣿⣾⡿⠋⠻⣿⣿⣿⣿⣿⣿⣿⣿⠟⠙⢿⣿⣿⠏⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠈⠻⠟⠀⠀⠀⢹⣿⣿⣿⣿⡏⠀⠀⠀⠻⠟⠁⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢻⣿⣿⣿⣿⡟⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⣿⣿⣇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣴⣿⣿⣿⣿⣿⣿⣦⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠀⣶⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣶⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⠉⠉⠉⠛⠛⠛⠛⠉⠉⠉⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀"
        );

        input.close();
    }
}
