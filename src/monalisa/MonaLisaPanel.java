/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package monalisa;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JPanel;

/**
 *
 * @author samiam14
 */
public class MonaLisaPanel extends JPanel {

    private BufferedImage background, image;
    private Graphics bg, graphics;
    private int WIDTH, HEIGHT;

    public MonaLisaPanel(Dimension d, BufferedImage img) {
        super(new BorderLayout());
        WIDTH = (int) d.getWidth();
        HEIGHT = (int) d.getHeight();
        image = img;
        background = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        bg = background.getGraphics();
        bg.setColor(Color.BLACK);
        bg.fillRect(0, 0, WIDTH, HEIGHT);
        repaint();
    }

    void paintPolygons(LinkedList polygons) {
        bg.setColor(Color.BLACK);
        bg.fillRect(0, 0, WIDTH, HEIGHT);
        Iterator it = polygons.listIterator();
        while (it.hasNext()) {
            ColoredPolygon poly = (ColoredPolygon) it.next();
            bg.setColor(poly.getColor());
            bg.fillPolygon(poly.getPolygon());
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(background, 0, 0, WIDTH, HEIGHT, null);
        g.drawImage(image, 0, 0, null);
    }

    double match() {
        return 0.0;
    }

    private void stats() {
        double diff = 0.0;
        int h = 0;
        int width = background.getWidth();
        int height = background.getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = background.getRGB(x, y);
                int objective = image.getRGB(x, y);

                int dblue = (objective & 255) - (rgb & 255);
                int dgreen = ((objective >> 8) & 255) - ((rgb >> 8) & 255);
                int dred = ((objective >> 16) & 255) - ((rgb >> 16) & 255);

                h += fitness(dblue, dgreen, dred);
                diff += (dblue / 255.0) + (dgreen / 255.0) + (dred / 255.0);
            }
        }
        diff = 100.0 * diff / (double) (width * height * 3);
    }

    void generate() {
    }

    private int fitness(int dblue, int dgreen, int dred) {
        return dblue * dblue + dgreen * dgreen + dred * dred;
    }
}
