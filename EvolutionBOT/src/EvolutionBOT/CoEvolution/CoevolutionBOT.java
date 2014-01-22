/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package EvolutionBOT;

import java.util.ArrayList;
import Utils.sel_mapa;
import java.util.*;
import java.io.*;
import Utils.*;
import Utils.fitness.t_fitness;
import Utils.fitness.t_fitness_versus4;
import Utils.individuos.comparators.IndividuoComparator;
import Utils.individuos.comparators.IndividuoComparatorVersus4;

/**
 *
 * @author antares
 */
public class CoevolutionBOT extends SimpleGenetic {

    public CoevolutionBOT(String[] args) {
        super(args);
        System.out.println(this.toString());
    }
    
    
    @Override
      public void evaluate() {
        //Evaluamos toda la población
        long tiempoInicio = System.currentTimeMillis();
        System.err.println("Evaluando población:" + population.size());

        //for(individuo a : population){
        for (int a = 0; a < population.size() - n_hebras -1;) {

            ArrayList<t_fitness_versus4> hebras = new ArrayList<t_fitness_versus4>(n_hebras);

            char text = '1';
            for (int jj = 0; jj < n_hebras && a + jj +1 < population.size(); jj++) {
                hebras.add(new t_fitness_versus4(population.get(a + jj*2), population.get(a + jj*2+1), "hebra_" + text + ".txt"));
                text++;
               // System.err.println("Evaluando: " + (a+jj*2) + " y " + (a+jj*2+1) );
            }

            //Las lanzamos
            for (t_fitness_versus4 t : hebras) {
                t.start();
            }

            //Esperamos que termine
            try {
                for (t_fitness_versus4 t : hebras) {
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
                System.err.print("..");
                i++;
                i++;
                a++;
                a++;
            }
            long totalTiempo = System.currentTimeMillis() - tiempoInicio;
            System.err.print("[" + i + "](" + (double) totalTiempo / 1000 / 60 + " min)");

        }
    }

    public void store() {
        //Almacenamos
        try {
            outfile = new FileWriter("./logs/genera" + Integer.toString(generation) + ".txt");
            pw = new PrintWriter(outfile);

            for (individuo a : population) {
                //System.err.println(a.ToString());
                pw.println(a.ToString());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Nuevamente aprovechamos el finally para
                // asegurarnos que se cierra el fichero.
                if (null != outfile) {
                    outfile.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }


    @Override
    public void next_generation() {
        System.out.println("La población se encuentra en la " + generation + " generación");
        System.err.println("Población:" + population.size());
        //Elitimos
        for (int j = 0; j < n_eli; j++) {
            future_population.add(population.get(j));
        }

        //Selecionamos un individuo
        for (individuo t1 : population) {
            int selecion;
            int sel = random.nextInt(population.size());
            //Selecionamos su torneo. Hemos generado n números aleatorios que indican la posición de los indiviuos en el array. Como están ordenados según su fitness, nos quedamos con el menor
            for (int j = 0; j < nts; j++) {
                //selecion=random.nextInt(population.size());
                selecion = random.nextInt(population.size());
                if (selecion < sel && !t1.equals(population.get(selecion))) {
                    sel = selecion;
                }
            }

            //en sel tenemos la posición del individuo que vamos a cruzar.
            double[] h1 = {0, 0, 0, 0, 0, 0, 0, 0}, h2 = {0, 0, 0, 0, 0, 0, 0, 0};

            //Hacemos que los padres tengan descendencia
            if (random.nextDouble() <= factor_crossover) {

                recombination_blx(t1, population.get(sel), h1, h2);
                //recombination_part(t1, population.get(sel), h1);

            } else {
                h1[0] = t1.cromo[0];
                h1[1] = t1.cromo[1];
                h1[2] = t1.cromo[2];
                h1[3] = t1.cromo[3];
                h1[4] = t1.cromo[4];
                h1[5] = t1.cromo[5];
                h1[6] = t1.cromo[6];
                h1[7] = t1.cromo[7];
            }

            //Mutación
            for (i = 0; i < h1.length; i++) {
                mutation(h1[i], factor_mutacion);
                mutation(h2[i], factor_mutacion);
            }


            //Añadimos uno de los dos individuos a la población.
            future_population.add(new individuo(h1));
            // future_population.add(new individuo(h2));
            //population.remove(t1);
            // borrar = t1;
            //population.remove(sel);
        }

        population.clear();
        population.addAll(future_population);
        future_population.clear();

        //Como tenemos elitismo, los n peores individuos "mueren"
        for (int h = 0; h < n_eli; h++) {
            population.remove(population.size() - 1);
        }

    }
    
    @Override
    public void main() {

        System.out.println("Empezando algoritmo genético con los siguientes parámetros");
        System.out.println("==============================================================");
        System.out.println("Factor mutación: " + factor_mutacion);
        System.out.println("Factor crossover: " + factor_crossover);
        System.out.println("Total generaciones: " + total_generaciones);
        System.out.println("Tamaño población: " + size);
        System.out.println("Número individuos torneo: " + nts);
        System.out.println("Cantidad de individuos elitimo: " + n_eli);
        System.out.println("Utilizando " + n_hebras + " hebras simultaneas para evaluaciones.");

        //Inicializamos
        System.err.println("Inicializando población");
        //start_espectro(population);
        start_random(population, size);

        //Evaluamos toda la población

        evaluate();

        System.err.println("\n Ajustando población");

        //Ordenamos
        Collections.sort(population, new IndividuoComparatorVersus4());

        System.err.println("Almacenando información sobre individuos");
        store();

        //Comienza el algoritmo genético en si
        while (generation < total_generaciones) {
            generation++;

            next_generation();

            //Evaluamos de nuevo los nuevos individuos
            i = 0;
            evaluate();

            //Ordenamos de nuevo
            System.err.print("\n");
            Collections.sort(population, new IndividuoComparatorVersus4());

            //Almacenamos
            store();
        }
    }
}
