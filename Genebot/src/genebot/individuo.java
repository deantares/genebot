/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genebot;

import java.util.*;

/**
 *
 * @author antares
 */
public class individuo {

    public String ToString(){
        String out ="Cromosomas:";
        for(double a : cromo){
            out+=a + ",";
        }
        out+=" - Turnos:"+this.turnos+" ¿Gana?:"+this.gana;
        return out;
    }

    public individuo(double [] a){
        cromo=a;
    }

    public individuo(){
        cromo = new double[] {0,0,0,0,0,0,0,0,0};
        turnos = 0;
        gana = false;
    }

    public int compare(individuo t1, individuo t2){
        if(t1.gana!=t2.gana){
            if(t1.gana==false) return 1;
            if(t2.gana=false) return -1;
        }else{
            if(t1.turnos<t2.turnos) return -1;
            else if(t1.turnos>t2.turnos) return 1;
            else return 0;
        }
        return 0;
    }

    public double [] cromo ;
    public int turnos;
    public boolean gana;
}

    class IndividuoComparator implements Comparator {

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
