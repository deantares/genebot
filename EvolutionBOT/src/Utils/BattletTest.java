/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Utils;

import Utils.fitness.t_fitness;
import java.util.ArrayList;

/**
 *
 * @author antares
 */
public class BattletTest {

        /**
     * Function that fight an bot define by args with all the maps versus the bot define in lanzar script
     * @param args Define the bot
     */

    public void battletest(String[] args) {
    individuo tio = new individuo (new double [] {Double.parseDouble(args[1]),Double.parseDouble(args[2]),Double.parseDouble(args[3]),Double.parseDouble(args[4]),Double.parseDouble(args[5]),Double.parseDouble(args[6]),Double.parseDouble(args[7]),Double.parseDouble(args[8])});
    System.err.println(tio.ToString());
    int derrotas = 0;
    int turnicos = 0;
            for(int i=1;i<=100;i++){

                t_fitness t = new t_fitness(tio, "battletest/bt_"+ args[9] +"_" + i+".txt", new sel_mapa(new int[] {i}));
                t.start();
                try {
                      t.join();

                  }catch (Exception e){

                  }
                  System.err.println("Mapa: "+ i +"  Turnos: " + tio.turnos + "  ¿Ganador? " + tio.gana);

                  if(tio.gana==false) derrotas ++;
                  turnicos = turnicos + tio.turnos;

            }
    System.err.println("\n RESULTADO FINAL ("+ args[9] +"): Turnos medios:"+ turnicos/100 + "  Nº Derrotas:" + derrotas);

        }

                    /**
     * Function that fight and bot versus a selection of bots
     * @param args
     */
            public void battletestbots(String[] args) {
              ArrayList<individuo> poblacion = new ArrayList <individuo> ();

              poblacion.add(new individuo(new double [] {0.5998149691289131,0.0,0.3203014274830107,0.6348218748464499,0.6715334379640565,0.7908027437454676,0.2624520220002189,0.6332454944811828}));
              poblacion.add(new individuo(new double [] {0.48231655449480576,0.10649542170626028,0.5623083960457418,0.40529883928645455,0.3515051786780067,0.6441622892638008,0.5989578698935684,0.8251208746187438}));
              poblacion.add(new individuo(new double [] {0.3919305854764415,0.03027927275131309,0.6908147364455396,0.3559640928082235,0.426402059754704,0.814945973663688,0.5549861375996193,0.21015986425274444}));
              poblacion.add(new individuo(new double [] {0.034222396220094015,0.46961880715935866,0.6618127230706216,0.616489430991588,0.07952046257968269,0.7109535986694575,0.45054368895575203,0.47592289801101983}));
              poblacion.add(new individuo(new double [] {0.03184182889671823,0.0712566535498098,0.7848675281078807,0.6079625695776322,0.6383045770257679,0.7174020304004736,0.507002540947466,0.2612852710965636}));
              poblacion.add(new individuo(new double [] {0.539370799194244,0.11495072908590714,0.5386587788233015,0.6905063895039896,0.20462338086671492,0.8108662848749906,0.7627616158254455,0.8470291375259826}));
              poblacion.add(new individuo(new double [] {0.39969603712889223,0.012840744258300545,0.5554363176516031,0.15606410243810775,0.7425922056628191,0.7109472230363102,0.41218237494267485,0.5946394142929935}));
              poblacion.add(new individuo(new double [] {0.5339911187296903,0.003646706463124119,0.35658600072154845,0.48093248816123474,0.4863878165314131,0.7160563139637138,0.6540326750398607,0.5029913016747484}));



              for(individuo t1 : poblacion){

                  for(individuo t2: poblacion){
                      if (!t1.equals(t2)){
                         // System.err.print(t1.ToString() + "VS" + t2.ToString());
                          System.err.print(poblacion.indexOf(t1) + " VS " + poblacion.indexOf(t2));
                            int derrotas = 0;
                            int turnicos = 0;


            for(int i=1;i<=100;i++){

                t_fitness t = new t_fitness(t1, t2, "battletest/"+poblacion.indexOf(t1)+"vs"+poblacion.indexOf(t2) +"_m" + i+".txt", new sel_mapa(new int[] {i}));
                t.start();
                try {
                      t.join();

                  }catch (Exception e){

                  }
                 System.err.print(t1.turnos+"-");
                 // System.err.println("Mapa: "+ i +"  Turnos: " + tio.turnos + "  ¿Ganador? " + tio.gana);

                  if(t1.gana==false) derrotas ++;
                  turnicos = turnicos + t1.turnos;

            }
    System.err.println("\n RESULTADO FINAL: Turnos medios:"+ turnicos/100 + "  Nº Derrotas:" + derrotas);
    System.err.print("\n");


                      }
                  }
              }


        }
}
