/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Utils;

import java.util.*;

/**
 *
 * @author antares
 */
public class sel_mapa {
 public String ToString(){
        String out ="Mapas:";
        for(double a : cromo){
            out+=a + ",";
        }
        return out;
    }

    public sel_mapa(int [] a){
        cromo=a;
    }

    public sel_mapa(String [] a){
        cromo = new int[]{0,0,0,0};
        System.out.println(a[0]);
        for(int i = 0;i <a.length; i++){
            cromo[i]=Integer.valueOf(a[i]);
            //cromo=a;
        }
    }

    public sel_mapa(){
        cromo = new int[] {0,0,0,0,0};
        turnos = 0;
        derrotas = 0;
    }

    public int compare(sel_mapa t1, sel_mapa t2){
        int fit_t1 = t1.turnos * (t1.derrotas + 1),fit_t2 = t2.turnos * (t2.derrotas + 1);
            if(fit_t1<fit_t2) return -1;
            else if(fit_t1>fit_t2) return 1;
            else return 0;
    }

    public int [] cromo ;
    public int turnos;
    public int derrotas;
}

    class sel_mapaComparator implements Comparator {

  public int compare(Object o1, Object o2) {
    sel_mapa t1 = (sel_mapa) o1;
    sel_mapa t2 = (sel_mapa) o2;
    int fit_t1 = t1.turnos * (t1.derrotas + 1),fit_t2 = t2.turnos * (t2.derrotas + 1);

    if(fit_t1<fit_t2) return -1;
    else if(fit_t1>fit_t2) return 1;
    else return 0;
  }

  public boolean equals(Object o) {
    return this == o;
  }
}
