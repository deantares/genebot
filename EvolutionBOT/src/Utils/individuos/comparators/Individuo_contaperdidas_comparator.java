/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Utils.individuos.comparators;

import java.util.*;
import Utils.individuals.individuo_contaperdidas;

/**
 *
 * @author antares
 */
public class Individuo_contaperdidas_comparator implements Comparator {

  public int compare(Object o1, Object o2) {
    individuo_contaperdidas t1 = (individuo_contaperdidas) o1;
    individuo_contaperdidas t2 = (individuo_contaperdidas) o2;
    if(t1.gana < t2.gana){
        return 1;
    }else if (t1.gana > t2.gana){
        return -1;
    }else{
        if(t1.turnos<t2.turnos) return -1;
            else if(t1.turnos>t2.turnos) return 1;
            else return 0;
    }
  }
}
