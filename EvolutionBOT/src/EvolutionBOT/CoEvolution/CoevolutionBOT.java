/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package EvolutionBOT.CoEvolution;

import EvolutionBOT.SimpleGenetic;
import Utils.individuos.individuo;
import java.util.ArrayList;
import java.util.*;
import java.io.*;
import Utils.fitness.t_fitness_versus2;
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

  public void co_evaluate() {
    //Evaluamos toda la población
    long tiempoInicio = System.currentTimeMillis();
    System.err.println("Evaluando población:" + population.size());

    ArrayList<t_fitness_versus2> hebras = new ArrayList<t_fitness_versus2>(n_hebras);

    future_population.clear();

    while (!population.isEmpty()) {

      char text = '1';
      for (int i_h = 0; i_h < n_hebras; i_h++) {
        if (!population.isEmpty()) {
          int tipo1 = random.nextInt(population.size());
          int tipo2 = random.nextInt(population.size());
          while (tipo1 == tipo2) {
            tipo2 = random.nextInt(population.size());
          }

          future_population.add(population.get(tipo2));
          future_population.add(population.get(tipo1));

//          System.err.println(
//                  tipo1 + "[" + population.get(tipo1).marca() + "]"
//                  + tipo2 + "[" + population.get(tipo2).marca() + "]");
//
//          System.err.println("Evaluamos: "
//                  + tipo1 + "[" + future_population.get(future_population.size() - 1).marca() + "]"
//                  + tipo2 + "[" + future_population.get(future_population.size() - 2).marca() + "]"
//                  + " [" + population.size() + "]");

          hebras.add(new t_fitness_versus2(
                  future_population.get(future_population.size() - 1),
                  future_population.get(future_population.size() - 2),
                  "hebra_" + text + ".txt"));

          if (tipo1 < tipo2) {
            population.remove(tipo1);
            population.remove(tipo2 - 1);
          } else {
            population.remove(tipo2);
            population.remove(tipo1 - 1);
          }

          text++;
        }
      }

      //Las lanzamos
      for (t_fitness_versus2 t : hebras) {
        t.start();
      }

      //Esperamos que termine
      try {
        for (t_fitness_versus2 t : hebras) {
          t.join();
          System.err.print("..");
        }
      } catch (Exception e) {
      }

      hebras.clear();

      if (future_population.size() % n_output == 0) {
        long totalTiempo = System.currentTimeMillis() - tiempoInicio;
        System.err.print("[" + future_population.size() + "](" + (double) totalTiempo / 1000 / 60 + " min)");
      }

    }

    population.addAll(future_population);
    future_population.clear();

    long totalTiempo = System.currentTimeMillis() - tiempoInicio;
    System.err.print("(" + (double) totalTiempo / 1000 / 60 + " min)");


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


    //Elitimos como el fitness se basa en el anterior, tenemos que resetear el individuo más adelante
    for (int j = 0; j < n_eli; j++) {
      future_population.add(population.get(j));
    }

    //Como tenemos elitismo, los n peores individuos "mueren"
    for (int h = 0; h < n_eli; h++) {
      population.remove(population.size() - 1);
    }


    //Selecionamos un individuo de los que quedan
    for (individuo t1 : population) {
      int selecion;
      int sel = random.nextInt(population.size());
      //Selecionamos su torneo. Hemos generado n números aleatorios que indican la posición de los indiviuos en el array. Como están ordenados según su fitness, nos quedamos con el menor
      for (int j = 0; j < nts; j++) {
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

    //Como tenemos re-evaluaciones, eliminamos la información anterior
    for (individuo a : population) {
      a.reset();
    }

  }

  @Override
  public void main() {

    System.out.println("Empezando algoritmo Co-Evolutivo con los siguientes parámetros");
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

    co_evaluate();

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
      co_evaluate();

      //Ordenamos de nuevo
      System.err.print("\n");
      Collections.sort(population, new IndividuoComparatorVersus4());

      //Almacenamos
      store();
    }
  }
}
