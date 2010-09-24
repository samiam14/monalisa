/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package monalisa;

import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author samiam14
 */
public class Individual implements Mutable {
    private final LinkedList dna;

    Individual(LinkedList polygons) {
        dna = polygons;
    }

    public void mutate() {
        Iterator itr = dna.listIterator();
        while(itr.hasNext()) {
            Mutable poly = (Mutable)itr.next();
            poly.mutate();
        }
    }

    static Individual breed(Individual a, Individual b) {
        return null;
    }

}
