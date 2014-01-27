package Utils.individuos;

import java.util.Random;

/**
 *
 * @author antares
 */
public class individuo {

    public String ToString() {
        String out = this.marca();
        out += ",Secundario:" + this.turnos;
        if (puesto != -1) {
            out += ",Principal:" + this.puesto;
        } else {
            out += ",¿Gana?:" + this.gana;
        }
        out += ",{" + this.antiguedad + "}";
        out += ",Cromosomas:";
        for (double a : cromo) {
            out += a + ",";
        }
        return out;
    }

    public individuo(double[] a) {
        cromo = a;
        turnos = 0;
        gana = false;
        puesto = 0;
    }

    public individuo() {
        cromo = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        turnos = 0;
        gana = false;
        puesto = 0;
    }

    public void reset(){
        turnos = 0;
        gana = false;
        puesto = 0;
    }
    
    public individuo random(){
      Random aleatorio = new Random(System.currentTimeMillis());
      return new individuo(new double[]{aleatorio.nextDouble(), aleatorio.nextDouble(), aleatorio.nextDouble(), aleatorio.nextDouble(), aleatorio.nextDouble(), aleatorio.nextDouble(), aleatorio.nextDouble(), aleatorio.nextDouble()});
    }
    
    public void setPrincipal(float a){
      this.puesto = a;
    }
    
    public void setSecundario(float a){
      this.turnos = a;
    }
    
    public float getPrincipal(){
      return this.puesto;
    }
    
    public float getSecundario(){
      return this.turnos;
    }
    
    public String marca(){
      String r = "";
      for (double a : cromo){
        char b;int c;
        if(a<0.5){
          b = 'a';
          c = Math.round((float) a * 25);
        }else{
          b = 'A';
          c = Math.round((float) (a-0.5) * 25);
        }
        
        char d = (char) (b+c);
        r += String.valueOf(d);
      }
      return "["+r+"]";
    }
    
    public void setFitness(float pp, float ps){
      antiguedad++;
      puesto = pp;
      turnos = ps;
    }
  

    public double[] cromo;
    public float turnos;
    public boolean gana;
    //Parámetros para el co-evolutivo por turnos
    public float puesto = 0;
    public int antiguedad = 0;
    //Parámetros para el co-evolutivo por flotantes
}
