import javax.swing.*;

public class GameFrame extends JFrame {

    GameFrame()
    {
        //GamePanel panel=new GamePanel();
        this.add(new GamePanel());  //(panel());---using shortkey
        this.setTitle("KingCobra");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();// ---using other component as draw,gameover,...
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
