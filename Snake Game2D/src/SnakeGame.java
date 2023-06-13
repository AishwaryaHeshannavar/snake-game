import javax.swing.*;

public class SnakeGame extends JFrame {
    Board board;
    SnakeGame()
    {
        board=new Board();
        add(board);
        //sizes the frame so that all its contents are at or above preferred size
        //resizes the frame to component size that is to the boards dimension
        pack();
        setResizable(false);
        setTitle("Snake Game 2D");
        setDefaultCloseOperation(SnakeGame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args) {
        SnakeGame snakeGame=new SnakeGame();
    }
}