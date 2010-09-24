/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package monalisa;

import java.awt.Color;
import java.awt.Polygon;
import java.util.Random;

/**
 *
 * @author samiam14
 */
class ColoredPolygon implements Mutable {

    private Polygon p;
    private Color c;
    private static Random rand = new Random();
    private static int MAX_WIDTH, MAX_HEIGHT, mutations = 0, generations = 0;

    public ColoredPolygon(int MW, int MH) {
        MAX_WIDTH = MW;
        MAX_HEIGHT = MH;
        c = randomColor();
        p = randomPolygon(6);
    }

    public Color getColor() {
        return c;
    }

    public Polygon getPolygon() {
        return p;
    }

    @Override
    public String toString() {
        return "<ColoredPolygon>Polygon: " + p.toString() + " Color: " + c + "</ColoredPolygon>";
    }

    public void mutate() {
        double random = rand.nextDouble();
        generations++;
        if(random < .99)
            return;

        random = Math.random();
        if(random < .25) {
            mutateColor();
        } else {
            mutatePolygon();
        }
        mutations++;
        System.out.println(mutations + " mutations in " + generations + " attempts. ~" + Math.round((double)(mutations) / (double)(generations) * 100) + "%");
    }

    private void mutateColor() {
        //Use normal distribution. Scale the standard deviation to 32
        int stdev = 64;
        int blue = c.getBlue() + (int)(rand.nextGaussian() * stdev);
        int red = c.getRed() + (int)(rand.nextGaussian() * stdev);
        int green = c.getGreen() + (int)(rand.nextGaussian() * stdev);
        int alpha = c.getAlpha() + (int)(rand.nextGaussian() * stdev);

        //If the color is out of bounds, then set it to the appropriate bound, otherwise use the current value
        blue = (blue < 0 || blue > 255) ? (int)(Math.ceil(blue/512.0) * 255) : blue;
        green = (green < 0 || green > 255) ? (int)(Math.ceil(green/512.0) * 255) : green;
        red = (red < 0 || red > 255) ? (int)(Math.ceil(red/512.0) * 255) : red;
        alpha = (alpha < 0 || alpha > 255) ? (int)(Math.ceil(alpha/512.0) * 255) : alpha;

        c = new Color(red, green, blue, alpha);
    }

    private void mutatePolygon() {
        double random = rand.nextDouble();
        if(random < .75) {
            int stdev = (MAX_WIDTH > MAX_HEIGHT ? MAX_HEIGHT : MAX_WIDTH) / 10;
            int dx = (int)(rand.nextGaussian() * stdev);
            int dy = (int)(rand.nextGaussian() * stdev);
            p.translate(dx, dy);
        } else {
            p = randomPolygon(6);
        }
    }

    private static Polygon randomPolygon(int factor) {
        int maxWidth = MAX_WIDTH / factor;
        int maxHeight = MAX_HEIGHT / factor;
        int count = 0;
        Polygon p = new Polygon();
        while (Math.random() < .5 || count < 3) {
            int x = (int) (rand.nextDouble() * maxWidth * (rand.nextInt(factor) + 1));
            int y = (int) (rand.nextDouble() * maxHeight * (rand.nextInt(factor) + 1));
            p.addPoint(x, y);
            count++;
        }
        return p;
    }

    private static Color randomColor() {
        return new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 192) + 64);
    }
}
