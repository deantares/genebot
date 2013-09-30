/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils.fitness;

import Utils.fitness_return;
import Utils.individuo;
import Utils.sel_mapa;
import java.io.*;

/**
 *
 * @author antares
 */
public class t_fitness_versus4 extends Thread {

    public individuo tio;
    public individuo tio2 = null;
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
        int[] tio1_turnos = {0, 0, 0, 0, 0};
        int[] tio1_puesto = {0, 0, 0, 0, 0};

        int[] tio2_turnos = {0, 0, 0, 0, 0};
        int[] tio2_puesto = {0, 0, 0, 0, 0};

        int[] tio3_turnos = {0, 0, 0, 0, 0};
        int[] tio3_puesto = {0, 0, 0, 0, 0};

        int[] tio4_turnos = {0, 0, 0, 0, 0};
        int[] tio4_puesto = {0, 0, 0, 0, 0};

        //Mapas en los que más planetas hay, menos planetas, pierde inanición, más tarda en ganar, menos tarda.

        //Mapas en los que más planetas hay, 
        for (int i = 0; i < mapas.length; i++) {

            try {

                //System.err.println("|-"+ Integer.toString(mapas[i])+ Double.toString(tio.cromo[0]) +Double.toString(tio.cromo[1]) + Double.toString(tio.cromo[2])+ Double.toString(tio.cromo[3])+ Double.toString(tio.cromo[4])+ Double.toString(tio.cromo[5])+ Double.toString(tio.cromo[6])+ Double.toString(tio.cromo[7])+ file);
                ProcessBuilder pb;
                pb = new ProcessBuilder("./lanzar_versus4", Integer.toString(mapas[i]), Double.toString(tio.cromo[0]), Double.toString(tio.cromo[1]), Double.toString(tio.cromo[2]), Double.toString(tio.cromo[3]), Double.toString(tio.cromo[4]), Double.toString(tio.cromo[5]), Double.toString(tio.cromo[6]), Double.toString(tio.cromo[7]), file.toString(), Double.toString(tio2.cromo[0]), Double.toString(tio2.cromo[1]), Double.toString(tio2.cromo[2]), Double.toString(tio2.cromo[3]), Double.toString(tio2.cromo[4]), Double.toString(tio2.cromo[5]), Double.toString(tio2.cromo[6]), Double.toString(tio2.cromo[7]));

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

                // System.err.println(":->" + rc);

                //System.err.println("--->" + file);
                File archivo = new File(file + "_res");
                FileReader fr = new FileReader(archivo);
                BufferedReader br2 = new BufferedReader(fr);
                String linea;

                try {
                    linea = br2.readLine();
                    tio1_puesto[i] = Integer.parseInt(linea);
                    linea = br2.readLine();
                    tio1_turnos[i] = Integer.parseInt(linea);

                    linea = br2.readLine();
                    tio2_puesto[i] = Integer.parseInt(linea);
                    linea = br2.readLine();
                    tio2_turnos[i] = Integer.parseInt(linea);


                } catch (Exception e) {
                    System.err.print("#");
                    tio.turnos = Integer.MAX_VALUE;
                    tio.gana = false;
                    tio.puesto = 4;

                    tio2.turnos = Integer.MAX_VALUE;
                    tio2.gana = false;
                    tio2.puesto = 4;
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

        tio.turnos = tio1_turnos[0] + tio1_turnos[1] + tio1_turnos[2] + tio1_turnos[3];
        tio.puesto = tio1_puesto[0] + tio1_puesto[1] + tio1_puesto[2] + tio1_puesto[3];

        tio2.turnos = tio2_turnos[0] + tio2_turnos[1] + tio2_turnos[2] + tio2_turnos[3];
        tio2.puesto = tio2_puesto[0] + tio2_puesto[1] + tio2_puesto[2] + tio2_puesto[3];

    }
}
