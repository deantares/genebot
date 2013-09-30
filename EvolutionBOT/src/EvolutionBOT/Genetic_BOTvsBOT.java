/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package EvolutionBOT;


import Utils.fitness.t_fitness;
import java.util.*;
import java.io.*;
import Utils.*;
import Utils.fitness.t_fitness_BotVSBot;
import java.util.ArrayList;


/**
 *
 * @author antares
 */
public class Genetic_BOTvsBOT extends SimpleGenetic {

    public int number_fights = 5;

    public ArrayList<ArrayList<fitness_return> > matriz_resultados;

   // public Mat

        @Override
        public void evaluate() {
        //Evaluamos toda la población

        long tiempoInicio = System.currentTimeMillis();
        System.err.println("Evaluando población:" + population.size());

        //for(individuo a : population){
        for (int a = 0; a < population.size() - n_hebras;) {

            //Creamos conjunto de rivales de pruebas

            ArrayList<Integer> rivales = new ArrayList<Integer>(10);
            for(int aux=0; aux<number_fights;aux++){
                rivales.add(random.nextInt(population.size()));
            }
            
            population.get(a).gana = true;
            population.get(a).turnos = 0;

            ArrayList<t_fitness_BotVSBot> hebras = new ArrayList<t_fitness_BotVSBot>(n_hebras);

            char text = '1';
            //Creamos las hebras
            //System.err.println( "hebra_"+ text+".txt");
            for (int jj = 0; jj < n_hebras && a + jj < population.size(); jj++) {
                for(int ii=0; ii < number_fights; ii++){
                hebras.add(new t_fitness_BotVSBot(population.get(a + jj), population.get(rivales.get(ii)), "hebra_" + text + ".txt"));
                text++;
                }
            }

            //Las lanzamos
            for (t_fitness_BotVSBot t : hebras) {
                t.start();
            }

            //Esperamos que termine
           try {
                for (t_fitness_BotVSBot t : hebras) {
                    t.join();
                    population.get(a).turnos = population.get(a).turnos + t.resultado.turnos;
                    population.get(a).gana = population.get(a).gana && t.resultado.gana ;
                }
            } catch (Exception e) {
            }     try {
                for (t_fitness_BotVSBot t : hebras) {
                    t.join();
                }
            } catch (Exception e) {
            }

            for (int jj = 0; jj < n_hebras; jj++) {
                if (population.get(a).turnos <= 5) {
                    System.err.println("Algo está fallando :( " + population.get(a).ToString());
                }
                if (i % n_output == 0) {
                    long totalTiempo = System.currentTimeMillis() - tiempoInicio;
                    System.err.print("[" + i + "](" + (double) totalTiempo / 1000 / 60 + " min)");
                }
                System.err.print(".");
                i++;
                a++;
            }

        }
    }
    

    public Genetic_BOTvsBOT() {
    } // Del SimpleGenetic()

    public Genetic_BOTvsBOT(String[] args) {
        //Miramos los parámetros pasados del genético

        for (int j = 1; j < args.length; j++) {
            if (args[j].equals("--fm")) {
                if (j + 1 >= args.length) {
                    System.err.println("Parámetros incorrectos");
                    System.exit(0);
                }

                this.factor_mutacion = Double.parseDouble(args[j + 1]);

            } else if (args[j].equals("--fc")) {
                if (j + 1 >= args.length) {
                    System.err.println("Parámetros incorrectos");
                    System.exit(0);
                }

                this.factor_crossover = Double.parseDouble(args[j + 1]);

            } else if (args[j].equals("--s")) {
                if (j + 1 >= args.length) {
                    System.err.println("Parámetros incorrectos");
                    System.exit(0);
                }

                this.size = Integer.parseInt(args[j + 1]);

            } else if (args[j].equals("--g")) {
                if (j + 1 >= args.length) {
                    System.err.println("Parámetros incorrectos");
                    System.exit(0);
                }
                this.total_generaciones = Integer.parseInt(args[j + 1]);

            } else if (args[j].equals("--nts")) {
                if (j + 1 >= args.length) {
                    System.err.println("Parámetros incorrectos");
                    System.exit(0);
                }
                this.nts = Integer.parseInt(args[j + 1]);

            } else if (args[j].equals("--neli")) {
                if (j + 1 >= args.length) {
                    System.err.println("Parámetros incorrectos");
                    System.exit(0);
                }
                this.n_eli = Integer.parseInt(args[j + 1]);

            } else if (args[j].equals("--h")) {
                if (j + 1 >= args.length) {
                    System.err.println("Parámetros incorrectos");
                    System.exit(0);
                }
                this.n_hebras = Integer.parseInt(args[j + 1]);

            }

        }
    }//Del SimpleGenetic(String [] args

}
