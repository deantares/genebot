/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author antares
 */
    public class categoriaMapa{
        public double distancia;
        public double dispersion;
        private int categoria_distancia;
        private int categoria_dispersion;

        public categoriaMapa() {
        }
        
        public String toString(){
            String cad = dispersion<-1?"0":dispersion>1?"2":"1";
            String cad2 = distancia<16?"0":distancia>24?"2":"1";
            return cad2 +"-"+cad;
        }

    public void setDispersion(double dispersion) {
        this.dispersion = dispersion;
        
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }
        
    }
