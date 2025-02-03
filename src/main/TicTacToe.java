import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.sound.sampled.*;

        public class TicTacToe {
            private JFrame frame;
            private JButton[][] buttons;
            private char[][] board;
            private String playerX, playerO, currentPlayerName;
            private char currentPlayer = 'X';
            private Clip backgroundMusic;

            public TicTacToe() {
                // Get Player Names
                playerX = JOptionPane.showInputDialog("Enter Player 1 Name (X):");
                playerO = JOptionPane.showInputDialog("Enter Player 2 Name (O):");
                currentPlayerName = playerX;

                // Create Game Frame
                frame = new JFrame("Tic-Tac-Toe 5x5");
                frame.setSize(500, 500);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());

                // Game Heading
                JLabel title = new JLabel("🎮 Tic-Tac-Toe 🎮", JLabel.CENTER);
                title.setFont(new Font("Arial", Font.BOLD, 24));
                title.setForeground(Color.GREEN);
                frame.add(title, BorderLayout.NORTH);

                // Set up board and buttons
                buttons = new JButton[5][5];
                board = new char[5][5];
                initializeBoard();
                JPanel panel = new JPanel(new GridLayout(5, 5));
                createButtons(panel);
                frame.add(panel, BorderLayout.CENTER);

                // Play Background Music
                playBackgroundMusic("background.wav"); // Background music file

                // Show frame
                frame.setVisible(true);
            }

            private void initializeBoard() {
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        board[i][j] = ' ';
                    }
                }
            }

            private void createButtons(JPanel panel) {
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        buttons[i][j] = new JButton();
                        buttons[i][j].setFont(new Font("Arial", Font.BOLD, 36));
                        buttons[i][j].setFocusPainted(false);
                        buttons[i][j].setBackground(Color.LIGHT_GRAY);

                        final int row = i;
                        final int col = j;

                        buttons[i][j].addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (board[row][col] == ' ') {
                                    board[row][col] = currentPlayer;
                                    buttons[row][col].setText(String.valueOf(currentPlayer));
                                    buttons[row][col].setForeground(currentPlayer == 'X' ? Color.RED : Color.BLUE);

                                    playSound("click.wav"); // Play click sound

                                    if (checkWinner()) {
                                        showWinner();
                                        return;
                                    } else if (isBoardFull()) { // Check for draw condition
                                        showDraw();
                                        return;
                                    }
                                    switchPlayer();
                                }
                            }
                        });

                        panel.add(buttons[i][j]);
                    }
                }
            }

            private boolean checkWinner() {
                for (int i = 0; i < 5; i++) {
                    if ((board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer &&
                            board[i][3] == currentPlayer && board[i][4] == currentPlayer) ||
                            (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer &&
                                    board[3][i] == currentPlayer && board[4][i] == currentPlayer)) {
                        return true;
                    }
                }

                if ((board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer &&
                        board[3][3] == currentPlayer && board[4][4] == currentPlayer) ||
                        (board[0][4] == currentPlayer && board[1][3] == currentPlayer && board[2][2] == currentPlayer &&
                                board[3][1] == currentPlayer && board[4][0] == currentPlayer)) {
                    return true;
                }

                return false;
            }

            private boolean isBoardFull() {
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        if (board[i][j] == ' ') {
                            return false; // Board still has empty spaces
                        }
                    }
                }
                return true; // Board is full, and no winner
            }

            private void showWinner() {
                playSound("win.wav"); // Play winning sound
                int option = JOptionPane.showOptionDialog(frame, "🎉 " + currentPlayerName + " wins! 🎉\n\nDo you want to restart or exit?",
                        "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                        new Object[] {"Restart", "Exit"}, "Restart");

                if (option == JOptionPane.YES_OPTION) {
                    restartGame();
                } else {
                    stopBackgroundMusic(); // Stop the music before exiting
                    System.exit(0); // Exit the process
                }
            }

            private void showDraw() {
                playSound("win.wav"); // Same win sound for draw
                int option = JOptionPane.showOptionDialog(frame, "😲 The game is a draw! 😲\n\nDo you want to restart or exit?",
                        "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                        new Object[] {"Restart", "Exit"}, "Restart");

                if (option == JOptionPane.YES_OPTION) {
                    restartGame();
                } else {
                    stopBackgroundMusic(); // Stop the music before exiting
                    System.exit(0); // Exit the process
                }
            }

            private void restartGame() {
                // Reset the board and buttons
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        board[i][j] = ' ';
                        buttons[i][j].setText("");
                        buttons[i][j].setEnabled(true);
                    }
                }

                // Reset the current player
                currentPlayer = 'X';
                currentPlayerName = playerX;
            }

            private void switchPlayer() {
                if (currentPlayer == 'X') {
                    currentPlayer = 'O';
                    currentPlayerName = playerO;
                } else {
                    currentPlayer = 'X';
                    currentPlayerName = playerX;
                }
            }

            private void playSound(String soundFile) {
                try {
                    File file = new File("sounds/" + soundFile); // Reference the sounds folder
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    clip.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void playBackgroundMusic(String musicFile) {
                try {
                    File file = new File("sounds/" + musicFile); // Reference the sounds folder
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                    backgroundMusic = AudioSystem.getClip();
                    backgroundMusic.open(audioStream);
                    backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY); // Loop music forever
                    backgroundMusic.start(); // Start the music immediately after loading
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void stopBackgroundMusic() {
                if (backgroundMusic != null && backgroundMusic.isRunning()) {
                    backgroundMusic.stop();
                }
            }

            public static void main(String[] args) {
                SwingUtilities.invokeLater(() -> new TicTacToe());
            }
        }
