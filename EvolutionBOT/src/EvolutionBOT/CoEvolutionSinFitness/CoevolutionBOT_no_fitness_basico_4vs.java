/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package EvolutionBOT.CoEvolutionSinFitness;

import EvolutionBOT.SimpleGenetic;
import Utils.fitness.CoEvolutionSinFitness.t_fitness_evaluacion;
import Utils.individuos.individuo;
import java.util.ArrayList;
import java.util.*;
import java.io.*;
import Utils.fitness.CoEvolutionSinFitness.t_fitness_versus4;
import Utils.individuos.comparators.IndividuoComparatorVersus4;

/**
 *
 * @author antares
 */
public class CoevolutionBOT_no_fitness_basico_4vs extends SimpleGenetic {

  public ArrayList<individuo> ganadores_procrean = new ArrayList<individuo>();
  public ArrayList<individuo> mantienen = new ArrayList<individuo>();

  public CoevolutionBOT_no_fitness_basico_4vs(String[] args) {
    super(args);
    System.out.println(this.toString());
  }

  @Override
  public void evaluate() {
    //Evaluamos toda la población
    long tiempoInicio = System.currentTimeMillis();
    System.err.println("Evaluacín inicial población:" + population.size());

    //for(individuo a : population){
    for (int a = 0; a < population.size();) {
      System.err.println(a);
      ArrayList<t_fitness_evaluacion> hebras = new ArrayList<t_fitness_evaluacion>(n_hebras);

      char text = '1';
      //Creamos las hebras
      //System.err.println( "hebra_"+ text+".txt");
      for (int jj = 0; jj < n_hebras && a + jj < population.size(); jj++) {
        hebras.add(new t_fitness_evaluacion(population.get(a + jj), "hebra_" + text + ".txt"));
        text++;
      }

      //Las lanzamos
      for (t_fitness_evaluacion t : hebras) {
        t.start();
      }

      //Esperamos que termine
      try {
        for (t_fitness_evaluacion t : hebras) {
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

  public void co_evaluate() {
    //Evaluamos toda la población
    long tiempoInicio = System.currentTimeMillis();

    ArrayList<t_fitness_versus4> hebras = new ArrayList<t_fitness_versus4>(n_hebras);

    ArrayList<individuo> evaluando = new ArrayList<individuo>();

    future_population.clear();

    while (!population.isEmpty()) {
      System.out.println("Evaluando : " + population.size());

      char text = '1';
      evaluando.clear();
      for (int i_h = 0; i_h < n_hebras; i_h++) {

        if (!population.isEmpty()) {
          int tipo1 = random.nextInt(population.size());
          int tipo2 = random.nextInt(population.size());
          while (tipo1 == tipo2) {
            tipo2 = random.nextInt(population.size());
          }

          int tipo3 = random.nextInt(population.size());
          while (tipo3 == tipo1 || tipo3 == tipo2) {
            tipo3 = random.nextInt(population.size());
          }

          int tipo4 = random.nextInt(population.size());
          while (tipo4 == tipo1 || tipo4 == tipo2 || tipo4 == tipo3) {
            tipo4 = random.nextInt(population.size());
          }

          evaluando.add(population.get(tipo4));
          evaluando.add(population.get(tipo3));
          evaluando.add(population.get(tipo2));
          evaluando.add(population.get(tipo1));

          hebras.add(new t_fitness_versus4(
                  evaluando.get(evaluando.size() - 1),
                  evaluando.get(evaluando.size() - 2),
                  evaluando.get(evaluando.size() - 3),
                  evaluando.get(evaluando.size() - 4),
                  "hebra_" + text + ".txt"));

          population.remove(evaluando.get(evaluando.size() - 1));
          population.remove(evaluando.get(evaluando.size() - 2));
          population.remove(evaluando.get(evaluando.size() - 3));
          population.remove(evaluando.get(evaluando.size() - 4));

          text++;
        }
      }

      //Las lanzamos
      for (t_fitness_versus4 t : hebras) {
        t.start();
      }

      //Esperamos que termine
      try {
        for (t_fitness_versus4 t : hebras) {
          t.join();

          System.err.print("..");
        }
      } catch (Exception e) {
      }

      for (int ii = 0; ii < evaluando.size(); ii = ii + 4) {
        System.out.print("::");
        individuo pasa = Collections.min(evaluando.subList(ii, ii + 4), new IndividuoComparatorVersus4());

        individuo muere = Collections.max(evaluando.subList(ii, ii + 4), new IndividuoComparatorVersus4());

        ganadores_procrean.add(pasa);

        if (evaluando.get(ii) != pasa && evaluando.get(ii) != muere) {
          mantienen.add(evaluando.get(ii));
        }
        if (evaluando.get(ii + 1) != pasa && evaluando.get(ii + 1) != muere) {
          mantienen.add(evaluando.get(ii + 1));
        }
        if (evaluando.get(ii + 2) != pasa && evaluando.get(ii + 2) != muere) {
          mantienen.add(evaluando.get(ii + 2));
        }
        if (evaluando.get(ii + 3) != pasa && evaluando.get(ii + 3) != muere) {
          mantienen.add(evaluando.get(ii + 3));
        }

      }

      hebras.clear();

      if (ganadores_procrean.size() % n_output == 0) {
        long totalTiempo = System.currentTimeMillis() - tiempoInicio;
        System.err.print("[" + ganadores_procrean.size() + "](" + (double) totalTiempo / 1000 / 60 + " min)");
      }

    }

    //population.addAll(future_population);
    population.clear();

    long totalTiempo = System.currentTimeMillis() - tiempoInicio;
    System.err.print("(" + (double) totalTiempo / 1000 / 60 + " min)");


  }

  public void store_test() {
    //Almacenamos
    try {
      outfile = new FileWriter("./logs/final.txt");
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
    System.err.println("Población vencedores:" + ganadores_procrean.size());
    System.err.println("Población mantenidos:" + mantienen.size());

    //Como tenemos re-evaluaciones, eliminamos la información anterior
    for (individuo a : population) {
      a.reset();
    }

    //Selecionamos un individuo de los que quedan

    //Qué pasa si sólo queda 1 :( Si sólo queda uno, se elige aleatoriamente de entre

    while (ganadores_procrean.size() >= 2) {
      int sel = random.nextInt(ganadores_procrean.size());
      //Selecionamos su torneo. Hemos generado n números aleatorios que indican la posición de los indiviuos en el array. Como están ordenados según su fitness, nos quedamos con el menor

      individuo t1 = ganadores_procrean.get(0);

      System.out.print(t1.marca());

      //Elegimos un amor aleatorio. 
      do {
        sel = random.nextInt(ganadores_procrean.size());
      } while (sel == 0);

      System.out.print("♥");
      System.out.print(ganadores_procrean.get(sel).marca());

      //en sel tenemos la posición del individuo que vamos a cruzar.
      double[] h1 = {0, 0, 0, 0, 0, 0, 0, 0}, h2 = {0, 0, 0, 0, 0, 0, 0, 0};

      //Hacemos que los padres tengan descendencia
      if (random.nextDouble() <= factor_crossover) {

        recombination_blx(t1, ganadores_procrean.get(sel), h1, h2);
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

      System.out.println(" ✓");

      //Añadimos uno de los dos individuos a la población.
      future_population.add(t1);
      future_population.add(ganadores_procrean.get(sel));
      future_population.add(new individuo(h1));
      future_population.add(new individuo(h2));

      //Los vencedores salen de la lista de vencedores
      ganadores_procrean.remove(sel);
      ganadores_procrean.remove(0);

    }

    if (!ganadores_procrean.isEmpty()) {
      //Nos ha quedado un individuo sin pareja :(
      //Pobre ForeverAlone
      individuo t1 = ganadores_procrean.get(0);

      System.out.print(t1.marca());
      int sel = random.nextInt(mantienen.size());


      System.out.print("♥");
      System.out.print(mantienen.get(sel).marca());

      //en sel tenemos la posición del individuo que vamos a cruzar.
      double[] h1 = {0, 0, 0, 0, 0, 0, 0, 0}, h2 = {0, 0, 0, 0, 0, 0, 0, 0};

      //Hacemos que los padres tengan descendencia
      if (random.nextDouble() <= factor_crossover) {

        recombination_blx(t1, mantienen.get(sel), h1, h2);
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
        //mutation(h2[i], factor_mutacion);
      }

      System.out.println(" ✓");

      //Añadimos uno de los dos individuos a la población.
      future_population.add(t1);
      //future_population.add(ganadores_procrean.get(sel));
      future_population.add(new individuo(h1));
      //future_population.add(new individuo(h2));

      //Los vencedores salen de la lista de vencedores
      //ganadores_procrean.remove(sel);
      ganadores_procrean.remove(0);

    }

    population.clear();
    population.addAll(future_population);
    population.addAll(mantienen);
    mantienen.clear();
    future_population.clear();
    ganadores_procrean.clear();




  }

  @Override
  public void main() {

    //Forzamos que no haya torneo
    nts = 1;
    //Forzamos que no haya "No cruces"
    factor_crossover = 1;


    System.out.println("Empezando algoritmo Co-Evolutivo sin fitness simple 4vs con 25% ganadores, 25% hijos y 50% mantenidos no ganadores Eliminación del peor con los siguientes parámetros");
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

    //Evaluamos toda la población usando Genebot8 como métrica
    evaluate();

    System.err.println("\n Ajustando población");

    //Ordenamos
    //Collections.sort(population, new IndividuoComparatorVersus4());

    System.err.println("Almacenando información sobre individuos");
    store();

    //Comienza el algoritmo genético en si
    while (generation < total_generaciones) {

      System.out.println("Incrementamos generación");

      generation++;

      System.out.println("Coevaluamos");

      co_evaluate();

      System.out.println("Próxima generación");
      next_generation();

      //Evaluamos de nuevo los nuevos individuos
      //i = 0;
      //Ordenamos de nuevo
      //System.err.print("\n");
      //Collections.sort(population, new IndividuoComparatorVersus4());

      //Almacenamos
      store();
    }

    //Por último, volvemos a evaluar en función de Genebot8 y lo imprimos

    generation++;
    System.err.println("Realizando test de pruebas sobre la población final y esas cosas");
    evaluate();
    store_test();
  }
}
