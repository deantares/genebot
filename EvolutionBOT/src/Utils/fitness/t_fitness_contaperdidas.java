/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Utils.fitness;

import Utils.fitness_return;
import Utils.individuals.individuo_contaperdidas;
import Utils.sel_mapa;
import java.io.*;


/**
 *
 * @author antares
 */
public class t_fitness_contaperdidas extends Thread  {

                public individuo_contaperdidas tio;
                public individuo_contaperdidas tio2 = null;
                public String file;
                public int[] mapas={76,69,7,11,26};

               public t_fitness_contaperdidas(individuo_contaperdidas t, String f) {
           // store parameter for later user
                   tio=t;
                   file=f;
       }

               public t_fitness_contaperdidas(individuo_contaperdidas t1, individuo_contaperdidas t2, String f){
                   tio=t1;
                   tio2=t2;
                   file=f;
               }

               public t_fitness_contaperdidas(individuo_contaperdidas t, String f, sel_mapa m) {
           // store parameter for later user
                   tio=t;
                   file=f;
                   mapas = m.cromo;
       }

               public t_fitness_contaperdidas(individuo_contaperdidas t1, individuo_contaperdidas t2, String f, sel_mapa m){
                   tio=t1;
                   tio2=t2;
                   file=f;
                   mapas = m.cromo;
               }

            @Override
            public void run() {

                //System.err.print("*");

            boolean gana = true;
            int [] turnos = {0,0,0,0,0};
            //Mapas en los que más planetas hay, menos planetas, pierde inanición, más tarda en ganar, menos tarda.

            //Mapas en los que más planetas hay,
            for(int i = 0; i<mapas.length; i++){

            try {

                //System.err.println("|-"+ Integer.toString(mapas[i])+ Double.toString(tio.cromo[0]) +Double.toString(tio.cromo[1]) + Double.toString(tio.cromo[2])+ Double.toString(tio.cromo[3])+ Double.toString(tio.cromo[4])+ Double.toString(tio.cromo[5])+ Double.toString(tio.cromo[6])+ Double.toString(tio.cromo[7])+ file);
                ProcessBuilder pb;
                if(tio2==null){
                pb = new ProcessBuilder("./lanzar", Integer.toString(mapas[i]), Double.toString(tio.cromo[0]) ,Double.toString(tio.cromo[1]) , Double.toString(tio.cromo[2]), Double.toString(tio.cromo[3]), Double.toString(tio.cromo[4]), Double.toString(tio.cromo[5]), Double.toString(tio.cromo[6]), Double.toString(tio.cromo[7]), file.toString());
                }else{
                pb = new ProcessBuilder("./lanzar2", Integer.toString(mapas[i]), Double.toString(tio.cromo[0]) ,Double.toString(tio.cromo[1]) , Double.toString(tio.cromo[2]), Double.toString(tio.cromo[3]), Double.toString(tio.cromo[4]), Double.toString(tio.cromo[5]), Double.toString(tio.cromo[6]), Double.toString(tio.cromo[7]), file.toString(), Double.toString(tio2.cromo[0]) ,Double.toString(tio2.cromo[1]) , Double.toString(tio2.cromo[2]), Double.toString(tio2.cromo[3]), Double.toString(tio2.cromo[4]), Double.toString(tio2.cromo[5]), Double.toString(tio2.cromo[6]), Double.toString(tio2.cromo[7]));
                }
                pb.redirectErrorStream(true); // Mezclamos todas las salidas

                Process p = pb.start(); //Lanzamos la hebra
                InputStreamReader isr = new  InputStreamReader(p.getInputStream());
                BufferedReader br = new BufferedReader(isr);

                String lineRead;
                while ((lineRead = br.readLine()) != null) {
                       //System.out.println(lineRead);
                    }

                int rc = p.waitFor(); //Esperamos que termine la ejecución del juego y sepamos el resultado

               // System.err.println(":->" + rc);

                //System.err.println("--->" + file);
                File archivo = new File (file);
                FileReader fr = new FileReader (archivo);
                BufferedReader br2 = new BufferedReader(fr);
                String linea, winer="",turn="";

                     // Lectura del fichero
                while((linea=br2.readLine())!=null){
                   // System.out.print("-");
                    turn=winer;
                    if(winer!="Draw!") winer=linea;
                    else {
                        br2.readLine();
                        winer = linea;
                    }

                }

                try{

                //In line 3 we have the numbers of turn and in line2 we have the result
                if(winer.charAt(7)=='2') {gana=false;}


                }catch (Exception e){
                   System.err.print("#");
                   tio.turnos = Integer.MAX_VALUE;
                   tio.gana = 0;
                   return ;
                }

                //System.out.println(Integer.parseInt(turn.substring(5)));
                //tio.turnos=Integer.parseInt(turn.substring(5));
                turnos[i]=Integer.parseInt(turn.substring(5));
                fr.close();
                br2.close();
                p.destroy();


            }
            catch (IOException e) {
                e.printStackTrace(); // or log it, or otherwise handle it
            }
            catch (InterruptedException ie) {
                ie.printStackTrace(); // or log it, or otherwise handle it
            }finally{

            }
            }

           tio.turnos = turnos[0]+turnos[1]+turnos[2]+turnos[3]+turnos[4];
//           if(gana==true) tio.gana = gana;

    //System.err.print("#");

            }


            public fitness_return run2() {

                //System.err.print("*");

                fitness_return resultado = new fitness_return();

            boolean gana = true;
            int [] turnos = {0,0,0,0,0};
            //Mapas en los que más planetas hay, menos planetas, pierde inanición, más tarda en ganar, menos tarda.

            //Mapas en los que más planetas hay,
            for(int i = 0; i<mapas.length; i++){

            try {

                //System.err.println("|-"+ Integer.toString(mapas[i])+ Double.toString(tio.cromo[0]) +Double.toString(tio.cromo[1]) + Double.toString(tio.cromo[2])+ Double.toString(tio.cromo[3])+ Double.toString(tio.cromo[4])+ Double.toString(tio.cromo[5])+ Double.toString(tio.cromo[6])+ Double.toString(tio.cromo[7])+ file);
                ProcessBuilder pb;
                if(tio2==null){
                pb = new ProcessBuilder("./lanzar", Integer.toString(mapas[i]), Double.toString(tio.cromo[0]) ,Double.toString(tio.cromo[1]) , Double.toString(tio.cromo[2]), Double.toString(tio.cromo[3]), Double.toString(tio.cromo[4]), Double.toString(tio.cromo[5]), Double.toString(tio.cromo[6]), Double.toString(tio.cromo[7]), file.toString());
                }else{
                pb = new ProcessBuilder("./lanzar2", Integer.toString(mapas[i]), Double.toString(tio.cromo[0]) ,Double.toString(tio.cromo[1]) , Double.toString(tio.cromo[2]), Double.toString(tio.cromo[3]), Double.toString(tio.cromo[4]), Double.toString(tio.cromo[5]), Double.toString(tio.cromo[6]), Double.toString(tio.cromo[7]), file.toString(), Double.toString(tio2.cromo[0]) ,Double.toString(tio2.cromo[1]) , Double.toString(tio2.cromo[2]), Double.toString(tio2.cromo[3]), Double.toString(tio2.cromo[4]), Double.toString(tio2.cromo[5]), Double.toString(tio2.cromo[6]), Double.toString(tio2.cromo[7]));
                }
                pb.redirectErrorStream(true); // Mezclamos todas las salidas

                Process p = pb.start(); //Lanzamos la hebra
                InputStreamReader isr = new  InputStreamReader(p.getInputStream());
                BufferedReader br = new BufferedReader(isr);

                String lineRead;
                while ((lineRead = br.readLine()) != null) {
                       //System.out.println(lineRead);
                    }

                int rc = p.waitFor(); //Esperamos que termine la ejecución del juego y sepamos el resultado

               // System.err.println(":->" + rc);

                //System.err.println("--->" + file);
                File archivo = new File (file);
                FileReader fr = new FileReader (archivo);
                BufferedReader br2 = new BufferedReader(fr);
                String linea, winer="",turn="";

                     // Lectura del fichero
                while((linea=br2.readLine())!=null){
                   // System.out.print("-");
                    turn=winer;
                    if(winer!="Draw!") winer=linea;
                    else {
                        br2.readLine();
                        winer = linea;
                    }

                }

                try{

                //In line 3 we have the numbers of turn and in line2 we have the result
                if(winer.charAt(7)=='2') {gana=false;}


                }catch (Exception e){
                   System.err.print("#");
                   tio.turnos = Integer.MAX_VALUE;
//                   tio.gana = false;
                   return resultado ;
                }

                //System.out.println(Integer.parseInt(turn.substring(5)));
                //tio.turnos=Integer.parseInt(turn.substring(5));
                turnos[i]=Integer.parseInt(turn.substring(5));
                fr.close();
                br2.close();
                p.destroy();


            }
            catch (IOException e) {
                e.printStackTrace(); // or log it, or otherwise handle it
            }
            catch (InterruptedException ie) {
                ie.printStackTrace(); // or log it, or otherwise handle it
            }finally{

            }
            }

           resultado.turnos = turnos[0]+turnos[1]+turnos[2]+turnos[3]+turnos[4];
           resultado.gana = gana;

           return resultado;

    //System.err.print("#");

            }
}
