
/*
 * CS303: Computer Graphics - Continuous Assessment Course Project 1
 * Alphabet: Letter T
 * Task: Draw the letter T in 2D and rotate it 360 degrees clockwise.
 *
 * The letter T is drawn using two filled rectangles: 
 * a horizontal bar and a vertical stem. 
 * The shape is positioned at the center of the panel
 * and rotated clockwise in real time using a Swing Timer.
 * The animation currently stops after a 360-degree rotation.
 */


import java.awt.*;
import javax.swing.*;

class RotatingT extends JPanel{
    private double angle = 0;       // current rotation angle in radians

    public RotatingT() {
        new Timer(20, e -> {
            angle = (angle + 2) % 360; 
            repaint(); 
        }).start();
    }

   /*private double angle = 0;
   private Timer timer;

    public RotatingT() {
        timer = new Timer(20, e -> {
                angle += 2;
                    if (angle >= 180) {
                        angle = 0;
                        timer.stop();
                    }
                    repaint();
                });
                timer.start();
    }*/



    @Override 
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);   
        
        g2.translate(getWidth() / 2.0, getHeight() / 2.0);  //origin to the center   
        g2.rotate(Math.toRadians(angle));               //convert from degrees to radius clockwise

        g2.setColor(new Color(0x1F6FEB));
        g2.fillRect(-80, -100, 160, 40);   //horizontal bar of the T
        g2.fillRect(-20, -60, 40, 160);   //vertical bar of the T
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Rotating Letter T");
        frame.add(new RotatingT());
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}


