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
import Utils.fitness.CoEvolutionSinFitness.t_fitness_versus2;
import Utils.individuos.comparators.IndividuoComparatorVersus4;

/**
 *
 * @author antares
 */
public class CoevolutionBOT_no_fitness_estacionario_1vs1 extends SimpleGenetic {
  
  int current_evaluations = 0;
  int MAX_evaluations =40000;

  public ArrayList<individuo> vencedores_population = new ArrayList<individuo>();
  
  public CoevolutionBOT_no_fitness_estacionario_1vs1(String[] args) {
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

    ArrayList<t_fitness_versus2> hebras = new ArrayList<t_fitness_versus2>(n_hebras);

    ArrayList<individuo> evaluando = new ArrayList<individuo>();
    
    future_population.clear();

    while (!population.isEmpty()) {

      char text = '1';
      evaluando.clear();
      for (int i_h = 0; i_h < n_hebras; i_h++) {       
        
        if (!population.isEmpty()) {
          int tipo1 = random.nextInt(population.size());
          int tipo2 = random.nextInt(population.size());
          while (tipo1 == tipo2) {
            tipo2 = random.nextInt(population.size());
          }

          evaluando.add(population.get(tipo2));
          evaluando.add(population.get(tipo1));       

          hebras.add(new t_fitness_versus2(
                  evaluando.get(evaluando.size() - 1),
                  evaluando.get(evaluando.size() - 2),
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
      
      for (int ii = 0; ii < evaluando.size(); ii=ii+2){
            if(evaluando.get(ii).puesto < evaluando.get(ii+1).puesto){
              vencedores_population.add(evaluando.get(ii));
            }else{
              vencedores_population.add(evaluando.get(ii+1));
            }
          }
      
      hebras.clear();

      if (vencedores_population.size() % n_output == 0) {
        long totalTiempo = System.currentTimeMillis() - tiempoInicio;
        System.err.print("[" + vencedores_population.size() + "](" + (double) totalTiempo / 1000 / 60 + " min)");
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
    System.err.println("Población vencedores:" + vencedores_population.size());


    //Selecionamos un individuo de los que quedan
    while( !vencedores_population.isEmpty()) {
      int selecion;
      int sel = random.nextInt(vencedores_population.size());
      //Selecionamos su torneo. Hemos generado n números aleatorios que indican la posición de los indiviuos en el array. Como están ordenados según su fitness, nos quedamos con el menor
      
     individuo t1 = vencedores_population.get(0);
     
     //Elegimos un amor aleatorio. 
     do{
     sel = random.nextInt(vencedores_population.size());
     }while(sel==0);
     
      //en sel tenemos la posición del individuo que vamos a cruzar.
      double[] h1 = {0, 0, 0, 0, 0, 0, 0, 0}, h2 = {0, 0, 0, 0, 0, 0, 0, 0};

      //Hacemos que los padres tengan descendencia
      

      if (random.nextDouble() <= factor_crossover) {

        recombination_blx(t1, vencedores_population.get(sel), h1, h2);
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
      future_population.add(t1);
      future_population.add(vencedores_population.get(sel));
      future_population.add(new individuo(h1));
      future_population.add(new individuo(h2));
      
      //Los vencedores salen de la lista de vencedores
      
      vencedores_population.remove(sel);
      vencedores_population.remove(0);

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
    
          //Forzamos que no haya torneo
      nts = 1;
            //Forzamos que no haya "No cruces"
      factor_crossover= 1;
      

    System.out.println("Empezando algoritmo Co-Evolutivo sin fitness simple con los siguientes parámetros");
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
    Collections.sort(population, new IndividuoComparatorVersus4());

    System.err.println("Almacenando información sobre individuos");
    store();

    //Comienza el algoritmo genético en si
    while (generation < total_generaciones) {
      generation++;

      co_evaluate();
      next_generation();

      //Evaluamos de nuevo los nuevos individuos
      i = 0;
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
