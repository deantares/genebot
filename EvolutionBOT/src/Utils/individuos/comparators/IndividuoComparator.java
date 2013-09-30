/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Utils.individuos.comparators;
import Utils.individuo;
import java.util.*;

/**
 *
 * @author antares
 */
public class IndividuoComparator  implements Comparator{

  public int compare(Object o1, Object o2) {
    individuo t1 = (individuo) o1;
    individuo t2 = (individuo) o2;
    if(t1.gana!=t2.gana){
            if(t1.gana==false) return 1;
            if(t2.gana==false) return -1;
        }else{
            if(t1.turnos<t2.turnos) return -1;
            else if(t1.turnos>t2.turnos) return 1;
            else return 0;
        }
    return 0;
  }


  public boolean equals(Object o) {
  return this == o;
  }

}
