import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CSI2300HW15 extends Application {

    private static final int SIZE = 5;
    private Button[][] board = new Button[SIZE][SIZE];
    private boolean xPlayerTurn = true;
    private boolean gameEnded = false;

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Button button = new Button("");
                button.setPrefSize(80, 80);

                int chosenRow = row;
                int chosenCol = col;

                button.setOnAction(e -> showMove(chosenRow, chosenCol));
                board[row][col] = button;
                grid.add(button, col, row);
            }
        }

        Scene scene = new Scene(grid, 450, 450);
        primaryStage.setTitle("TicTacToe 5x5");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showMove(int row, int col) {
        if (gameEnded) return;

        Button button = board[row][col];
        if (!button.getText().isEmpty()) return;

        button.setText(xPlayerTurn ? "X" : "O");

        if (checkWin(row, col)) {
            gameEnded = true;
            showWinner(xPlayerTurn ? "X" : "O");
        } else {
            xPlayerTurn = !xPlayerTurn;
        }
    }

    private boolean checkWin(int row, int col) {
        String symbol = board[row][col].getText();
        return checkDirection(row, col, 1, 0, symbol)
            || checkDirection(row, col, 0, 1, symbol)
            || checkDirection(row, col, 1, 1, symbol)
            || checkDirection(row, col, 1, -1, symbol);
    }

    private boolean checkDirection(int row, int col, int dx, int dy, String symbol) {
        int count = 1;

        for (int i = 1; i < 5; i++) {
            int chosenRow = row + i * dx;
            int chosenCol = col + i * dy;

            if (chosenRow >= 0 && chosenRow < SIZE && chosenCol >= 0 && chosenCol < SIZE && symbol.equals(board[chosenRow][chosenCol].getText())) {
                count++;
            } else break;
        }

        for (int i = 1; i < 5; i++) {
            int chosenRow = row - i * dx;
            int chosenCol = col - i * dy;

            if (chosenRow >= 0 && chosenRow < SIZE && chosenCol >= 0 && chosenCol < SIZE && symbol.equals(board[chosenRow][chosenCol].getText())) {
                count++;
            } else break;
        }

        return count >= 5;
    }

    private void showWinner(String winner) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText("Winner: " + winner);
        alert.setContentText("Click OK.");
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}