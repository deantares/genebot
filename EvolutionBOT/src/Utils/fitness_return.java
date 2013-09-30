/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Utils;

import javax.swing.text.StyledEditorKit.BoldAction;

/**
 *
 * @author antares
 */
public class fitness_return {
    public int turnos;
    public boolean gana;

    public fitness_return(){
        this.turnos = Integer.MAX_VALUE;
        this.gana = Boolean.FALSE;
    }

    public fitness_return(int t, boolean g){
        this.turnos = t;
        this.gana = g;
    }

    public void fitness_combinate( fitness_return t){
        this.turnos = this.turnos + t.turnos;
        this.gana = this.gana && t.gana;
    }
            

}
