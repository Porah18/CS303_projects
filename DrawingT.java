import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class DrawingT extends JPanel {

    // Corner points of the BIG T outline (relative to window centre), in drawing order
    private static final int[][] P = {
        {-150, -170}, {150, -170}, {150, -110}, {30, -110},
        {30, 170}, {-30, 170}, {-30, -110}, {-150, -110}, {-150, -170}
    };

    private double angle = 0;   // phase 1: current rotation in degrees
    private double dist = 0;    // phase 2: distance travelled along the path
    private double total = 0;   // total length of the path
    private Timer timer;

    public DrawingT() {
        setBackground(Color.WHITE);

        // add up the length of every segment of the outline
        for (int i = 0; i < P.length - 1; i++)
            total += Math.hypot(P[i + 1][0] - P[i][0], P[i + 1][1] - P[i][1]);

        timer = new Timer(20, e -> {
            if (angle < 360) {
                angle += 4;           // phase 1: spin on the spot
            } else if (dist < total) {
                dist += 4;            // phase 2: move along the big T
            } else {
                timer.stop();         // finished
            }
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        g2.translate(getWidth() / 2.0, getHeight() / 2.0);   // origin to centre

        // walk along the path to find the T's position and build the trail
        double d = Math.min(dist, total);
        double x = P[0][0], y = P[0][1];
        Path2D trail = new Path2D.Double();
        trail.moveTo(x, y);
        for (int i = 0; i < P.length - 1 && d > 0; i++) {
            double dx = P[i + 1][0] - P[i][0];
            double dy = P[i + 1][1] - P[i][1];
            double len = Math.hypot(dx, dy);
            double step = Math.min(d, len);
            x = P[i][0] + dx * step / len;
            y = P[i][1] + dy * step / len;
            trail.lineTo(x, y);
            d -= step;
        }

        // draw the trail (the big T being "drawn")
        g2.setColor(new Color(0xBFD4F7));
        g2.setStroke(new BasicStroke(6));
        g2.draw(trail);

        // draw the small rotating T at the current position
        g2.translate(x, y);
        g2.rotate(Math.toRadians(angle));       // clockwise
        g2.scale(0.25, 0.25);                   // make it small
        g2.setColor(new Color(0x1F6FEB));
        g2.fillRect(-80, -100, 160, 40);        // top bar
        g2.fillRect(-20, -60, 40, 160);         // stem
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Drawing a Big T");
        f.add(new DrawingT());
        f.setSize(600, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}