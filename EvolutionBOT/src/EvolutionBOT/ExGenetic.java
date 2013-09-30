/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package EvolutionBOT;

import Utils.individuos.comparators.IndividuoComparator;
import Utils.fitness.t_fitness;
import java.util.*;
import java.io.*;
import Utils.*;

/**
 *
 * @author antares
 * @version 0.1
 * Clase basica del algoritmo genético, sin demasiadas complicaciones
 */
public class ExGenetic {

    /**
     * Actual generation
     */
    public int generation = 0;

    /**
     * Outfile standart
     */
    public FileWriter outfile = null;

    /**
     * Outline stream
     */
    public PrintWriter pw = null;

    /**
     * Number of genes
     */
    public int n_genes = 8;

    /**
     * Random seed
     */
    public Random random = new Random(System.currentTimeMillis());

    /**
     * Default counter
     */
    public int i = 0;

    /**
     * Number of individuals procesed for show indications in display
     */
    public int n_output = 5;

    /**
     * Mutation factor
     */
    public double factor_mutacion = 0.1;

    /**
     * Factor de crossover
     */
    public double factor_crossover = 0.8;

    /**
     * Poblation size
     */
    public int size = 200;

    /**
     * Number max of generations
     */
    public int total_generaciones = 20;

    /**
     * Number of individuals in contest
     */
    public int nts = 2;

    /**
     * Number of individual of elitism
     */
    public int n_eli = 4;

    /**
     * Number of threads in poblation evaluation
     */
    public int n_hebras = 1; //Cantidad de hebras creadas al evaluar la población

    /**
     * Array of poblation
     */
    public ArrayList<individuo> population = new ArrayList<individuo>();

    /**
     * Array of future poblation
     */
    public ArrayList<individuo> future_population = new ArrayList<individuo>();

    public sel_mapa mapas;

    public individuo genebot8 = new individuo(new double[] {0.09978267816749446,0.23032668116070584,0.06446668711729682,0.3145662812995889,0.6783974914572055,0.7306356321118216,0.6645303530434207,0.7107283003587886});

    public ArrayList<individuo> load_poblation(String fichero) {
        return null;
    }

    public void start_espectro(ArrayList<individuo> poblacion) {
        //¿Cómo generamos la población iniciar? Unidistribuimos las variables.
        //        individuo a = new individuo();

        double[] i = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        double inicial = 0.3;
        double incr = 0.3;

        for (i[0] = inicial; i[0] <= 1; i[0] = i[0] + incr) {
            for (i[1] = inicial; i[1] <= 1; i[1] = i[1] + incr) {
                for (i[2] = inicial; i[2] <= 1; i[2] = i[2] + incr) {
                    for (i[3] = inicial; i[3] <= 1; i[3] = i[3] + incr) {
                        for (i[4] = inicial; i[4] <= 1; i[4] = i[4] + incr) {
                            for (i[5] = inicial; i[5] <= 1; i[5] = i[5] + incr) {
                                for (i[6] = inicial; i[6] <= 1; i[6] = i[6] + incr) {
                                    for (i[7] = inicial; i[7] <= 1; i[7] = i[7] + incr) {
                                        poblacion.add(new individuo(new double[]{i[0], i[1], i[2], i[3], i[4], i[5], i[6], i[7]}));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }




    }

    public void start_random(ArrayList<individuo> poblacion, int tama) {
        //¿Cómo generamos la población iniciar? Unidistribuimos las variables.
        //        individuo a = new individuo();

        Random aleatorio = new Random(System.currentTimeMillis());

        for (int i = 0; i < tama; i++) {
            poblacion.add(new individuo(new double[]{aleatorio.nextDouble(), aleatorio.nextDouble(), aleatorio.nextDouble(), aleatorio.nextDouble(), aleatorio.nextDouble(), aleatorio.nextDouble(), aleatorio.nextDouble(), aleatorio.nextDouble()}));
        }
    }

    public void start_random_m(ArrayList<sel_mapa> poblacion, int tama) {
        //¿Cómo generamos la población iniciar? Unidistribuimos las variables.
        //        individuo a = new individuo();

        Random aleatorio = new Random(System.currentTimeMillis());

        for (int i = 0; i < tama; i++) {
            poblacion.add(new sel_mapa(new int[]{aleatorio.nextInt(101), aleatorio.nextInt(101), aleatorio.nextInt(101), aleatorio.nextInt(101), aleatorio.nextInt(101)}));
        }
    }

    public void mutation(double cromo, double factor) {
        Random ale = new Random(System.currentTimeMillis());
        if (ale.nextDouble() <= factor) {
            //Un gen es mutado
            cromo = ale.nextDouble();
        }

    }

    public void recombination_part(individuo t1, individuo t2, double[] out) {
        Random ale = new Random(System.currentTimeMillis());
        int cut = ale.nextInt(t1.cromo.length);
        for (int i = 0; i < t1.cromo.length; i++) {
            out[i] = i < cut ? t1.cromo[i] : t2.cromo[i];
        }

    }

    public void recombination_blx(individuo t1, individuo t2, double[] a1, double[] a2) {

        Random ale = new Random(System.currentTimeMillis());
        double alfa = 0.5;
        for (int i = 0; i < t1.cromo.length; i++) {
            double cmax = t1.cromo[i] > t2.cromo[i] ? t1.cromo[i] : t2.cromo[i];
            double cmin = t1.cromo[i] < t2.cromo[i] ? t1.cromo[i] : t2.cromo[i];
            double I = cmax - cmin;
            // System.err.print(ale.nextDouble() * ((cmin - I *alfa) - (cmax + I *alfa) )+ (cmin - I *alfa));

            double maxRange = 1, minRange = 0;

            double limSup = cmax + (alfa * I);
            double limInf = cmin - (alfa * I);

            if (limSup > maxRange) {
                limSup = maxRange;
            }
            if (limInf < minRange) {
                limInf = minRange;
            }

            a1[i] = ale.nextDouble() * (limSup - limInf) + (limInf);
            a2[i] = ale.nextDouble() * (limSup - limInf) + (limInf);
        }
    }

    public void evaluate() {
        //Evaluamos toda la población
        long tiempoInicio = System.currentTimeMillis();
        System.err.println("Evaluando población:" + population.size());

        //for(individuo a : population){
        for (int a = 0; a < population.size() - n_hebras;) {

            ArrayList<t_fitness> hebras = new ArrayList<t_fitness>(n_hebras);

            char text = '1';
            //Creamos las hebras
            //System.err.println( "hebra_"+ text+".txt");
            for (int jj = 0; jj < n_hebras && a + jj < population.size(); jj++) {
                //hebras.add(new t_fitness(population.get(a + jj), "hebra_" + text + ".txt"));

                hebras.add(new t_fitness(population.get(a + jj), genebot8,  "hebra_" + text + ".txt", mapas));

                text++;
            }

            //Las lanzamos
            for (t_fitness t : hebras) {
                t.start();
            }

            //Esperamos que termine
            try {
                for (t_fitness t : hebras) {
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

    public ExGenetic() {
    } // Del SimpleGenetic()

    public ExGenetic(String[] args) {
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

            } else if (args[j].equals("--map")) {
                //1,2,3,4,5
                if (j + 1 >= args.length) {
                    System.err.println("Parámetros incorrectos");
                    System.exit(0);
                    
                }
                String cad = new String(args[j+1]);
                System.out.println(cad);
                this.mapas = new sel_mapa(cad.split(","));

            }  else if (args[j].equals("--neli")) {
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
    }//Del SimpleGenetic(String [] args )

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
        System.out.println("Evaluando los siguientes mapas {" + mapas.ToString() + "}");

        //Inicializamos
        System.err.println("Inicializando población");
        //start_espectro(population);
        start_random(population, size);

        //Evaluamos toda la población

        evaluate();

        System.err.println("\n Ajustando población");

        //Ordenamos
        Collections.sort(population, new IndividuoComparator());

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
            Collections.sort(population, new IndividuoComparator());

            //Almacenamos
            store();
        }
    }
}
