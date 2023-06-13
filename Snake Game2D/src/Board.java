import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
    int B_HEIGHT=400;
    int B_WIDTH=400;
    int MAX_DOTS=1600;
    int DOT_SIZE=10;
    int DOTS;//size of snake
    //coordinates of bodyparts of snake
    int[] x=new int[MAX_DOTS];
    int[] y=new int[MAX_DOTS];
    int apple_x;
    int apple_y;
    //Timer directs the snake to move
    //Everytime the timer ticks it generates an action event
    Timer timer;
    //Delay tells after how much millisecond timer should increment,helps map timer to actual time
    int DELAY=100;
    //Images
    Image body,head,apple;
    boolean leftDirection=true;
    boolean rightDirection=false;
    boolean upDirection=false;
    boolean downDirection=false;
    boolean inGame=true;
    Board()
    {
        TAdapter tAdapter=new TAdapter();
        addKeyListener(tAdapter);
        setFocusable(true);
        setPreferredSize(new Dimension(B_WIDTH,B_HEIGHT));
        setBackground(Color.BLACK);
        initGame();
        loadImages();
    }
    //Initialize game
    public void initGame()
    {
        DOTS=3;
        x[0]=150;
        y[0]=150;
        //Initialize snake position
        for(int i=1;i<DOTS;i++)
        {
            x[i]=x[0]+DOT_SIZE*i;
            y[i]=y[0];
        }
        //Initialize apple position
//        apple_x=150;
//        apple_y=150;
        locateApple();
        timer=new Timer(DELAY,this);
        timer.start();
    }
    //Load images from resource folder to image objects
    public void loadImages()
    {
        ImageIcon bodyImage=new ImageIcon(getClass().getResource("dot.png"));
        body=bodyImage.getImage();
        ImageIcon appleImage=new ImageIcon(getClass().getResource("apple.png"));
        apple=appleImage.getImage();
        ImageIcon headImage=new ImageIcon(getClass().getResource("head.png"));
        head=headImage.getImage();
    }
    //Draw images at snake and apple position
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if(inGame)
        {
            doDrawing(g);
        }
        else {
            GameOver(g);
            timer.stop();

        }
    }
    //Draw image
    public void doDrawing(Graphics g)
    {
        g.drawImage(apple,apple_x,apple_y,this);
        for(int i=0;i<DOTS;i++)
        {
            if(i==0)
            {
                g.drawImage(head,x[0],y[0],this);
            }
            else {
                g.drawImage(body,x[i],y[i],this);
            }
        }
    }
    //Randomise apple position
    public void locateApple()
    {
        apple_x=(int)(Math.random()*39)*DOT_SIZE;
        apple_y=(int)(Math.random()*39)*DOT_SIZE;
    }
    //Make snake move

    public void move()
    {
        for(int i=DOTS-1;i>0;i--)
        {
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if(leftDirection){
            x[0]-=DOT_SIZE;
        }
        if(rightDirection){
            x[0]+=DOT_SIZE;
        }
        if(upDirection){
            y[0]-=DOT_SIZE;
        }
        if(downDirection){
            y[0]+=DOT_SIZE;
        }
    }
    //Make snake eat food
    public void checkApple()
    {
        if(apple_x==x[0] && apple_y==y[0])
        {
            DOTS++;
            locateApple();
        }
    }
    //Implement controls
    private class TAdapter extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e) {
            int key=e.getKeyCode();
            if(key==KeyEvent.VK_LEFT && rightDirection==false)
            {
                leftDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if(key==KeyEvent.VK_RIGHT && leftDirection==false)
            {
                rightDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if(key==KeyEvent.VK_UP && downDirection==false)
            {
                upDirection=true;
                leftDirection=false;
                rightDirection=false;
            }
            if(key==KeyEvent.VK_DOWN && upDirection==false)
            {
                downDirection=true;
                leftDirection=false;
                rightDirection=false;
            }
        }
    }
    //Check Collisions with border and body
    public void checkCollision()
    {
        //check body collision
        for(int i=1;i<DOTS;i++)
        {
            if(i>4 && x[0]==x[i] && y[0]==y[i])
            {
                inGame=false;
            }
        }
        //Collision with border
        if(x[0]<0 || x[0]>=B_WIDTH || y[0]<0 || y[0]>=B_WIDTH)inGame=false;
    }
    //Game Over
    public void GameOver(Graphics g)
    {
        String msg="Game Over";
        int score=(DOTS-3)*100;
        String Score="Score:"+Integer.toString(score);
        Font small=new Font("Helvetica",Font.BOLD,14);
        FontMetrics fontMetrics=getFontMetrics(small);//Gets width and height of message
        g.setColor(Color.WHITE);
        g.setFont(small);
        g.drawString(msg,(B_WIDTH-fontMetrics.stringWidth(msg))/2,B_HEIGHT/4);
        g.drawString(Score,(B_WIDTH-fontMetrics.stringWidth(Score))/2,(3*B_HEIGHT)/4);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //check everytime if apple is consumed before snake moves
        if(inGame=true)
        {
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }
}
