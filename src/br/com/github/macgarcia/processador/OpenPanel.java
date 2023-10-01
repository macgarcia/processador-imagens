package br.com.github.macgarcia.processador;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import org.opencv.core.Mat;

public class OpenPanel extends JPanel {

    private BufferedImage buf;

    public OpenPanel() {
        super();
        this.setDoubleBuffered(true);
    }

    public void setImage(Mat mat) {
        this.buf = Util.toBufferedImage(mat);
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.clearRect(0, 0, getWidth(), getHeight());
        if (buf != null) {
            g.drawImage(buf, 0, 0, null);
        }
    }

}
