/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils.individuos.comparators;

import Utils.individuos.individuo;
import java.util.*;

/**
 *
 * @author antares
 */
public class IndividuoComparatorVersus4 implements Comparator {

  public int compare(Object o1, Object o2) {
    individuo t1 = (individuo) o1;
    individuo t2 = (individuo) o2;

    if (t1.turnos == 0) {
      return 1;
    }
    if (t2.turnos == 0) {
      return -1;
    }

    if (t1.puesto < t2.puesto) {
      return -1;
    } else if (t1.puesto > t2.puesto) {
      return 1;
    } else if(t1.puesto <= 10) {
      if (t1.turnos < t2.turnos) {
        return -1;
      } else if (t1.turnos > t2.turnos) {
        return 1;
      } else {
        return 0;
      }
    }else{
      if (t1.turnos < t2.turnos) {
        return 1;
      } else if (t1.turnos > t2.turnos) {
        return -1;
      } else {
        return 0;
      }
    }

  }

  public boolean equals(Object o) {
    return this == o;
  }
}
