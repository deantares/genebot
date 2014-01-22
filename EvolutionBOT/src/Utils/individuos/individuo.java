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
public class individuo {

    public String ToString() {
        String out = "Cromosomas:";
        for (double a : cromo) {
            out += a + ",";
        }
        out += " - Turnos:" + this.turnos;
        if (puesto != -1) {
            out += " Puesto:" + this.puesto;;
        } else {
            out += " ¿Gana?:" + this.gana;;
        }

        return out;
    }

    public individuo(double[] a) {
        cromo = a;
    }

    public individuo() {
        cromo = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        turnos = 0;
        gana = false;
    }

    public void reset(){
        turnos = 0;
        gana = false;
        puesto = -1;
    }
    
    public int compare(individuo t1, individuo t2) {
        if(t1.turnos == 0){ return 1;}
        if(t2.turnos == 0){ return -1;}
        if (t1.puesto != -1 && t2.puesto != -1) {
            if (t1.gana != t2.gana) {
                if (t1.gana == false) {
                    return 1;
                }
                if (t2.gana == false) {
                    return -1;
                }
            } else {
                if (t1.turnos < t2.turnos) {
                    return -1;
                } else if (t1.turnos > t2.turnos) {
                    return 1;
                } else {
                    return 0;
                }
            }
            return 0;
        } else {
            if (t1.puesto < t2.puesto) {
                if (t1.gana == false) {
                    return -1;
                }
                if (t2.gana == false) {
                    return 1;
                }
            } else if (t1.puesto == 1) {
                if (t1.turnos < t2.turnos) {
                    return -1;
                } else if (t1.turnos > t2.turnos) {
                    return 1;
                } else {
                    return 0;
                }
            } else {
                if (t1.turnos > t2.turnos) {
                    return -1;
                } else if (t1.turnos > t2.turnos) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
        return 0;
    }
    public double[] cromo;
    public int turnos;
    public boolean gana;
    //Parámetros para el co-evolutivo
    public int puesto = -1;
}
