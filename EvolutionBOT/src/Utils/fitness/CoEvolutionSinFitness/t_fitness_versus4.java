/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils.fitness.CoEvolutionSinFitness;

import Utils.individuos.individuo;
import Utils.sel_mapa;
import java.io.*;

/**
 *
 * @author antares
 */
public class t_fitness_versus4 extends Thread {

    public individuo tio;
    public individuo tio2 = null;
    public individuo tio3 = null;
    public individuo tio4 = null;
    public String file;
    public int[] mapas = {76, 69, 7, 11, 26};
    //public int[] mapas = {76};

    public t_fitness_versus4(individuo t, String f) {
        // store parameter for later user
        tio = t;
        file = f;
    }

    public t_fitness_versus4(individuo t1, individuo t2, String f) {
        tio = t1;
        tio2 = t2;
        file = f;
    }
    
    public t_fitness_versus4(individuo t1, individuo t2, individuo t3, individuo t4, String f){
      tio = t1;
      tio2 = t2;
      tio3 = t3;
      tio4 = t4;
      file = f;
    }

    public t_fitness_versus4(individuo t, String f, sel_mapa m) {
        // store parameter for later user
        tio = t;
        file = f;
        mapas = m.cromo;
    }

    public t_fitness_versus4(individuo t1, individuo t2, String f, sel_mapa m) {
        tio = t1;
        tio2 = t2;
        file = f;
        mapas = m.cromo;
    }//

    @Override
    public void run() {

        //System.err.print("*");

        boolean gana = true;
        float[] tio1_turnos = {0, 0, 0, 0, 0};
        float[] tio1_puesto = {0, 0, 0, 0, 0};

        float[] tio2_turnos = {0, 0, 0, 0, 0};
        float[] tio2_puesto = {0, 0, 0, 0, 0};
        
        float[] tio3_turnos = {0, 0, 0, 0, 0};
        float[] tio3_puesto = {0, 0, 0, 0, 0};
        
        float[] tio4_turnos = {0, 0, 0, 0, 0};
        float[] tio4_puesto = {0, 0, 0, 0, 0};

        //Mapas en los que más planetas hay, menos planetas, pierde inanición, más tarda en ganar, menos tarda.

        //Mapas en los que más planetas hay, 
        for (int i = 0; i < mapas.length; i++) {

            try {

                //System.err.println("|-"+ Integer.toString(mapas[i])+ Double.toString(tio.cromo[0]) +Double.toString(tio.cromo[1]) + Double.toString(tio.cromo[2])+ Double.toString(tio.cromo[3])+ Double.toString(tio.cromo[4])+ Double.toString(tio.cromo[5])+ Double.toString(tio.cromo[6])+ Double.toString(tio.cromo[7])+ file);
                ProcessBuilder pb;
                pb = new ProcessBuilder("./lanzar_versus4.sh", 
                        Integer.toString(mapas[i]), 
                        Double.toString(tio.cromo[0]),
                        Double.toString(tio.cromo[1]),
                        Double.toString(tio.cromo[2]),
                        Double.toString(tio.cromo[3]),
                        Double.toString(tio.cromo[4]),
                        Double.toString(tio.cromo[5]),
                        Double.toString(tio.cromo[6]), 
                        Double.toString(tio.cromo[7]),
                        file.toString(),
                        Double.toString(tio2.cromo[0]),
                        Double.toString(tio2.cromo[1]),
                        Double.toString(tio2.cromo[2]),
                        Double.toString(tio2.cromo[3]),
                        Double.toString(tio2.cromo[4]),
                        Double.toString(tio2.cromo[5]),
                        Double.toString(tio2.cromo[6]),
                        Double.toString(tio2.cromo[7]),
                        
                        Double.toString(tio3.cromo[0]),
                        Double.toString(tio3.cromo[1]),
                        Double.toString(tio3.cromo[2]),
                        Double.toString(tio3.cromo[3]),
                        Double.toString(tio3.cromo[4]),
                        Double.toString(tio3.cromo[5]),
                        Double.toString(tio3.cromo[6]),
                        Double.toString(tio3.cromo[7]),
                        
                        Double.toString(tio4.cromo[0]),
                        Double.toString(tio4.cromo[1]),
                        Double.toString(tio4.cromo[2]),
                        Double.toString(tio4.cromo[3]),
                        Double.toString(tio4.cromo[4]),
                        Double.toString(tio4.cromo[5]),
                        Double.toString(tio4.cromo[6]),
                        Double.toString(tio4.cromo[7])
                        );

                //System.out.print(pb.toString());}

                pb.redirectErrorStream(true); // Mezclamos todas las salidas

                Process p = pb.start(); //Lanzamos la hebra

                InputStreamReader isr = new InputStreamReader(p.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                
                String lineRead;
                    while ((lineRead = br.readLine()) != null) {
                        //System.out.println(lineRead);
                    }

                int rc = p.waitFor(); //Esperamos que termine la ejecución del juego y sepamos el resultado

                File archivo = new File(file + "_res");
                FileReader fr = new FileReader(archivo);
                BufferedReader br2 = new BufferedReader(fr);
                String linea;

                try {
                  //Formato de salida - P1 \n Turnos \n Puesto
                  
                    linea = br2.readLine();
                    //Linea P1
                    linea = br2.readLine();
                    tio1_turnos[i] = Float.parseFloat(linea);
                    linea = br2.readLine();
                    tio1_puesto[i] = Float.parseFloat(linea);

                    linea = br2.readLine();
                    //Linea P2
                    linea = br2.readLine();
                    tio2_turnos[i] = Float.parseFloat(linea);
                    linea = br2.readLine();
                    tio2_puesto[i] = Float.parseFloat(linea);
                    
                    linea = br2.readLine();
                    //Linea P3
                    linea = br2.readLine();
                    tio3_turnos[i] = Float.parseFloat(linea);
                    linea = br2.readLine();
                    tio3_puesto[i] = Float.parseFloat(linea);
                    
                    
                    linea = br2.readLine();
                    //Linea P4
                    linea = br2.readLine();
                    tio4_turnos[i] = Float.parseFloat(linea);
                    linea = br2.readLine();
                    tio4_puesto[i] = Float.parseFloat(linea);
                  


                } catch (Exception e) {
                    System.err.print("#");
                    tio.turnos = Integer.MAX_VALUE;
                    tio.gana = false;
                    tio.puesto = -1;

                    tio2.turnos = Integer.MAX_VALUE;
                    tio2.gana = false;
                    tio2.puesto = -1;
                    return;
                }

                fr.close();
                br2.close();
                p.destroy();



            } catch (IOException e) {
                System.out.println(e.toString());
                e.printStackTrace(); // or log it, or otherwise handle it
            } catch (InterruptedException ie) {
                ie.printStackTrace(); // or log it, or otherwise handle it
            } finally {
            }
        }

        tio.setFitness(
                tio1_puesto[0] + tio1_puesto[1] + tio1_puesto[2] + tio1_puesto[3] + tio1_puesto[4],
                tio1_turnos[0] + tio1_turnos[1] + tio1_turnos[2] + tio1_turnos[3] + tio1_turnos[4]);
        
        tio2.setFitness(
                tio2_puesto[0] + tio2_puesto[1] + tio2_puesto[2] + tio2_puesto[3] + tio2_puesto[4],
                tio2_turnos[0] + tio2_turnos[1] + tio2_turnos[2] + tio2_turnos[3] + tio2_turnos[4]);
        
        tio3.setFitness(
                tio3_puesto[0] + tio3_puesto[1] + tio3_puesto[2] + tio3_puesto[3] + tio3_puesto[4],
                tio3_turnos[0] + tio3_turnos[1] + tio3_turnos[2] + tio3_turnos[3] + tio3_turnos[4]);
        
        tio4.setFitness(
                tio4_puesto[0] + tio4_puesto[1] + tio4_puesto[2] + tio4_puesto[3] + tio4_puesto[4],
                tio4_turnos[0] + tio4_turnos[1] + tio4_turnos[2] + tio4_turnos[3] + tio4_turnos[4]);
        
    }
}
