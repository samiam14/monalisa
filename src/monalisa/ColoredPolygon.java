/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package monalisa;

import java.awt.Color;
import java.awt.Polygon;

/**
 *
 * @author samiam14
 */
class ColoredPolygon {

    private Polygon p;
    private Color c;

    public ColoredPolygon(Polygon poly, Color col) {
        p = poly;
        c = col;
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
}
