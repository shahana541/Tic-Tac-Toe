import java.util.Scanner;

public class TicTacToe {

        // Data members
        private static char[][] board = new char[5][5];
        private static char currentPlayer = 'X';

        // Main method
        public static void main(String[] args) {
            initializeBoard(); // Fill the board with empty spaces
            Scanner scanner = new Scanner(System.in);
            System.out.println("Tic_Tac_Toe");

            int moves = 0; // Count the number of moves

            while (true) {
                displayBoard();
                System.out.println("Player " + currentPlayer + ", enter your move (row [0-4] and column [0-4]): ");

                int row = scanner.nextInt();
                int col = scanner.nextInt();

                if (makeMove(row, col)) {
                    moves++;

                    if (checkWinner()) {
                        displayBoard();
                        System.out.println("Player " + currentPlayer + " wins!");
                        break;
                    }

                    if (moves == 25) { // Check if the board is full
                        displayBoard();
                        System.out.println("It's a draw!");
                        break;
                    }

                    switchPlayer(); // Change the current player
                } else {
                    System.out.println("Invalid move. Try again.");
                }
            }
        }

        // Initialize the board with empty spaces
        private static void initializeBoard() {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    board[i][j] = ' ';
                }
            }
        }

        // Display the current state of the board
        private static void displayBoard() {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    System.out.print(board[i][j]);
                    if (j < 4) System.out.print(" | ");
                }
                System.out.println();
                if (i < 4) System.out.println("------------------");
            }
        }

        // Handle a player's move
        private static boolean makeMove(int row, int col) {
            if (row >= 0 && row < 5 && col >= 0 && col < 5 && board[row][col] == ' ') {
                board[row][col] = currentPlayer;
                return true;
            }
            return false; // Move is invalid
        }

        // Check if the current player has won
        private static boolean checkWinner() {
            // Check rows and columns
            for (int i = 0; i < 5; i++) {
                if ((board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer && board[i][3] == currentPlayer && board[i][4] == currentPlayer) || // Row
                        (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer && board[3][i] == currentPlayer && board[4][i] ==currentPlayer)) { // Column
                    return true;
                }
            }
            // Check diagonals
            if ((board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer && board[3][3] == currentPlayer && board[4][4] == currentPlayer) || // Top-left to bottom-right
                    (board[0][4] == currentPlayer && board[1][3] == currentPlayer && board[2][2] == currentPlayer && board[3][1] == currentPlayer && board[4][0] == currentPlayer)) { // Top-right to bottom-left
                return true;
            }
            return false; // No winner
        }

        // Switch to the other player
        private static void switchPlayer() {
            currentPlayer = (currentPlayer == 'X') ? 'O': 'X';
        }
    }




}
