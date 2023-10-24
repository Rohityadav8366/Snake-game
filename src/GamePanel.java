import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.JPanel;
public class GamePanel extends JPanel implements ActionListener
{
    static final int SCREEN_WiDTH=900;
    static final int SCREEN_HEIGHT=775;
    static final int UNIT_SIZE=25;
    static final int GAME_UNITS=(SCREEN_WiDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY=100;
    final int x[]=new int[GAME_UNITS];
    final int y[]=new int[GAME_UNITS];
    int bodyParts=3;
    int applesEaten;
    int appleX;
    int appleY;
    char direction='R';
    boolean running= false;
    Timer timer;
    Random random;
    GamePanel()     //constractor for gamepanel
    {
        random=new Random();
        this.setPreferredSize(new Dimension(SCREEN_WiDTH,SCREEN_HEIGHT));
        this.setBackground(Color.DARK_GRAY);
       this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame() {
        newaApple();
        running=true;
        timer=new Timer(DELAY,this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(running) {
//        for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++){
//            g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HEIGHT);
//            g.drawLine(0,i*UNIT_SIZE ,SCREEN_WiDTH,i*UNIT_SIZE);
//        }
            g.setColor(Color.GREEN);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.GREEN);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(75, 18, 4));
                    g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }//scoring here
            g.setColor(Color.green);
            g.setFont(new Font("Ink free",Font.BOLD,30));
            FontMetrics metrics=getFontMetrics(g.getFont());
            g.drawString("Score=)"+applesEaten,(SCREEN_WiDTH-metrics.stringWidth("Score=)"+applesEaten)),g.getFont().getSize());
        }
        else{
            gameOver(g);
        }
    }
    public void newaApple(){
        appleX=random.nextInt((int)(SCREEN_WiDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY=random.nextInt((int) (SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void move(){
        for(int i=bodyParts;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];

        }
        switch (direction){
            case 'U':
                y[0]=y[0]-UNIT_SIZE;
                break;
            case 'D':
                y[0]=y[0]+UNIT_SIZE;
                break;
            case 'L':
                x[0]=x[0]-UNIT_SIZE;
                break;
            case 'R':
                x[0]=x[0]+UNIT_SIZE;
                break;
        }
    }
    public void checkApple(){
        if((x[0]==appleX)&&(y[0]==appleY)){
            bodyParts++;
            applesEaten++;
            newaApple();
        }
    }
    public void checkCollisions(){
        // checks if head touch its bodyparts
        for(int i=bodyParts;i>0;i--){
            if((x[0]==x[i])&&(y[0]==y[i])){
                running=false;
            }
        }
        //check if head touches border
        if(x[0]<0){
            running=false;
        }
        if(x[0]>SCREEN_WiDTH){
            running=false;
        }
        if (y[0]<0){
            running=false;
        }
        if (y[0]>SCREEN_HEIGHT){
            running=false;
        }
        if(!running) {
            timer.stop();
        }
    }
    public void gameOver(Graphics g){
        //score other page
        g.setColor(Color.green);
        g.setFont(new Font("Ink free",Font.BOLD,75));
        FontMetrics metrics=getFontMetrics(g.getFont());
        g.drawString("Score=)"+applesEaten,(SCREEN_WiDTH-metrics.stringWidth("Score=)"+applesEaten))/2,g.getFont().getSize());
        //Game over text
        g.setColor(Color.BLUE);
        g.setFont(new Font("Ink free",Font.BOLD,75));
        metrics = getFontMetrics(g.getFont());
        g.drawString("Well Play  Game Over",(SCREEN_WiDTH-metrics.stringWidth("lnGame Over"))/7,SCREEN_HEIGHT/2);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }
    public  class MyKeyAdapter implements KeyListener {
     @Override
     public void keyPressed(KeyEvent e){

         switch (e.getKeyCode()){
             case KeyEvent.VK_LEFT:
                 if(direction!='R'){
                     direction='L';
                 }
                 break;
             case KeyEvent.VK_RIGHT:
                 if(direction!='L'){
                     direction='R';
                 }
                 break;
             case KeyEvent.VK_DOWN:
                 if(direction!='U'){
                     direction='D';
                 }
                 break;
             case KeyEvent.VK_UP:
                 if(direction!='D'){
                     direction='U';
                 }
                 break;
         }
     }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }
}
