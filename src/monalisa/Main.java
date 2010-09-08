/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package monalisa;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author samiam14
 */
public class Main {

    private static MonaLisaPanel panel;
    private static Thread runner;

    public static void main(String[] args) {
        Dimension dm = Toolkit.getDefaultToolkit().getScreenSize();
        final int SCREEN_WIDTH = (int) dm.getWidth();
        final int SCREEN_HEIGHT = (int) dm.getHeight();

        final JFrame f = new JFrame("Mona Lisa Simulation");
        JMenuBar menu = new JMenuBar();

        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);

        JMenuItem new_run = new JMenuItem("New");
        new_run.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                final JFileChooser fc = new JFileChooser();
                fc.setFileFilter(new FileNameExtensionFilter("Images", ImageIO.getReaderFileSuffixes()));
                int ret = fc.showOpenDialog(f.getContentPane());
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    try {
                        BufferedImage img = ImageIO.read(file);
                        panel = new MonaLisaPanel(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT), img);
                        panel.setVisible(true);
                        f.add(panel);
                        f.validate();
                        run(SCREEN_WIDTH, SCREEN_HEIGHT);
                    } catch (IOException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        file.add(new_run);

        menu.add(file);

        f.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        f.setLocation(0, 0);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setJMenuBar(menu);
        f.setVisible(true);
        f.setResizable(false);
    }

    private static void run(final int SW, final int SH) {
        Individual i = new Individual(initPolygons(SW, SH));
        i.breed();
        runner = new Thread(new Runnable() {

            public void run() {
                do {
                    LinkedList polygons = initPolygons(SW, SH);
                    panel.paintPolygons(polygons);
                    panel.generate();
                } while (panel.match() < 1.0);
            }
        });
        runner.start();
    }

    private static LinkedList initPolygons(int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        LinkedList polygons = new LinkedList<ColoredPolygon>();
        for (int k = 0; k < 20; k++) {
            polygons.add(new ColoredPolygon(randomPolygon(SCREEN_WIDTH, SCREEN_HEIGHT, 6), randomColor()));
        }
        return polygons;
    }

    private static Polygon randomPolygon(int width, int height, int factor) {
        int maxWidth = (int) (width / factor);
        int maxHeight = (int) (height / factor);
        int count = 0;
        Polygon p = new Polygon();
        while (Math.random() < .5 || count < 3) {
            int x = (int) ((Math.random() * maxWidth) * (int) (Math.random() * factor + 1));
            int y = (int) ((Math.random() * maxHeight) * (int) (Math.random() * factor + 1));
            p.addPoint(x, y);
            count++;
        }
        return p;
    }

    private static Color randomColor() {
        return new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 192) + 64);
    }
}
