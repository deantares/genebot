/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genebot;


import java.util.*;
import java.io.*;

/**
 *
 * @author antares
 */
public class Main {
    
    
    public static void battletest(String[] args) {
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

      public static void battletestbots(String[] args) {
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


    public static class t_fitness extends Thread {
            public individuo tio;
            public individuo tio2 = null;
            public String file;
            public int[] mapas={76,69,7,11,26};

           public t_fitness(individuo t, String f) {
       // store parameter for later user
               tio=t;
               file=f;
   }
           public t_fitness(individuo t, String f, sel_mapa m) {
       // store parameter for later user
               tio=t;
               file=f;
               mapas = m.cromo;
   }

           public t_fitness(individuo t1, individuo t2, String f, sel_mapa m){
               tio=t1;
               tio2=t2;
               file=f;
               mapas = m.cromo;
           }
        
        public void run() {

            //System.err.print("*");

        boolean gana = true;
        int [] turnos = {0,0,0,0,0};
        //Mapas en los que más planetas hay, menos planetas, pierde inanición, más tarda en ganar, menos tarda.

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
               tio.gana = false;
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
       tio.gana = gana;

//System.err.print("#");

        }
    }

    public static class t_fitness_m extends Thread {
            public individuo tio;
            public sel_mapa mis_mapas;
            public String file;

           public t_fitness_m(individuo t, String f, sel_mapa m) {
       // store parameter for later user
               tio=t;
               file=f;
               mis_mapas = m;
   }

        public void run() {

            //System.err.print("*");

        mis_mapas.derrotas = 0;
        int [] turnos = {0,0,0,0,0};
        //Mapas en los que más planetas hay, menos planetas, pierde inanición, más tarda en ganar, menos tarda.
        int [] mapas = mis_mapas.cromo;

        for(int i = 0; i<mapas.length; i++){

        try {

            //System.err.println("|-"+ Integer.toString(mapas[i])+ Double.toString(tio.cromo[0]) +Double.toString(tio.cromo[1]) + Double.toString(tio.cromo[2])+ Double.toString(tio.cromo[3])+ Double.toString(tio.cromo[4])+ Double.toString(tio.cromo[5])+ Double.toString(tio.cromo[6])+ Double.toString(tio.cromo[7])+ file);

            ProcessBuilder pb = new ProcessBuilder("./lanzar", Integer.toString(mapas[i]), Double.toString(tio.cromo[0]) ,Double.toString(tio.cromo[1]) , Double.toString(tio.cromo[2]), Double.toString(tio.cromo[3]), Double.toString(tio.cromo[4]), Double.toString(tio.cromo[5]), Double.toString(tio.cromo[6]), Double.toString(tio.cromo[7]), file.toString());
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
            if(winer.charAt(7)=='2') {mis_mapas.derrotas++;}


            }catch (Exception e){
               System.err.print("#");
               tio.turnos = Integer.MAX_VALUE;
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

       mis_mapas.turnos = turnos[0]+turnos[1]+turnos[2]+turnos[3]+turnos[4];

//System.err.print("#");

        }
    }    

    public static ArrayList<individuo> load_poblation(String fichero){
        return null;
    }

    public static void fitness(individuo tio, String file){
        boolean gana = true;
        int [] turnos = {0,0,0,0,0};
        //Mapas en los que más planetas hay, menos planetas, pierde inanición, más tarda en ganar, menos tarda.
        int [] mapas = {76,69,7,11,26};

        for(int i = 0; i<mapas.length; i++){

        try {
            ProcessBuilder pb = new ProcessBuilder("./lanzar", Integer.toString(mapas[i]), Double.toString(tio.cromo[0]) ,Double.toString(tio.cromo[1]) , Double.toString(tio.cromo[2]), Double.toString(tio.cromo[3]), Double.toString(tio.cromo[4]), Double.toString(tio.cromo[5]), Double.toString(tio.cromo[6]), Double.toString(tio.cromo[7]),file.toString());
            pb.redirectErrorStream(true); // Mezclamos todas las salidas

            Process p = pb.start(); //Lanzamos la hebra
            InputStreamReader isr = new  InputStreamReader(p.getInputStream());
            BufferedReader br = new BufferedReader(isr);

            String lineRead;
            while ((lineRead = br.readLine()) != null) {
                   //System.out.println(lineRead);
                }

            int rc = p.waitFor(); //Esperamos que termine la ejecución del juego y sepamos el resultado

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

            //In line 3 we have the numbers of turn and in line2 we have the result
            if(winer.charAt(7)=='2') {gana=false;}
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
       tio.gana = gana;
    }

    public static void start_espectro(ArrayList< individuo > poblacion){
        //¿Cómo generamos la población iniciar? Unidistribuimos las variables.
//        individuo a = new individuo();

        double[] i={0,0,0,0,0,0,0,0,0};
        double inicial = 0.3;
        double incr = 0.3;

        for (i[0]=inicial; i[0]<=1;i[0]=i[0]+incr)
            for (i[1]=inicial; i[1]<=1;i[1]=i[1]+incr)
                for (i[2]=inicial; i[2]<=1;i[2]=i[2]+incr)
                    for (i[3]=inicial; i[3]<=1;i[3]=i[3]+incr)
                        for (i[4]=inicial; i[4]<=1;i[4]=i[4]+incr)
                            for (i[5]=inicial; i[5]<=1;i[5]=i[5]+incr)
                                for (i[6]=inicial; i[6]<=1;i[6]=i[6]+incr)
                                    for (i[7]=inicial; i[7]<=1;i[7]=i[7]+incr)
                                            poblacion.add(new individuo(new double []{i[0],i[1],i[2],i[3],i[4],i[5],i[6],i[7]}) );
                    
                
            
                
    }

    public static void start_random(ArrayList< individuo > poblacion, int tama){
        //¿Cómo generamos la población iniciar? Unidistribuimos las variables.
//        individuo a = new individuo();

        Random aleatorio = new Random(System.currentTimeMillis());

        for (int i=0; i<tama;i++){
                 poblacion.add(new individuo(new double []{aleatorio.nextDouble(),aleatorio.nextDouble(),aleatorio.nextDouble(),aleatorio.nextDouble(),aleatorio.nextDouble(),aleatorio.nextDouble(),aleatorio.nextDouble(),aleatorio.nextDouble()}) );
        }
    }

    public static void start_random_m(ArrayList< sel_mapa> poblacion, int tama){
        //¿Cómo generamos la población iniciar? Unidistribuimos las variables.
//        individuo a = new individuo();

        Random aleatorio = new Random(System.currentTimeMillis());

        for (int i=0; i<tama;i++){
                 poblacion.add(new sel_mapa(new int []{aleatorio.nextInt(101),aleatorio.nextInt(101),aleatorio.nextInt(101),aleatorio.nextInt(101),aleatorio.nextInt(101)}) );
        }
    }

    public static void mutation (double cromo, double factor){
        Random ale = new Random(System.currentTimeMillis());
        if (ale.nextDouble() <= factor){
            //Un gen es mutado
            cromo=ale.nextDouble();
        }

    }

    public static void recombination_part (individuo t1, individuo t2, double [] out){
        Random ale = new Random(System.currentTimeMillis());
        int cut = ale.nextInt(t1.cromo.length);
        for (int i=0; i<t1.cromo.length;i++){
            out[i]= i<cut ? t1.cromo[i] : t2.cromo[i];
        }

    }

    public static void recombination_blx (individuo t1, individuo t2, double [] a1, double [] a2){

        Random ale = new Random(System.currentTimeMillis());
        double alfa = 0.5;
        for (int i=0; i<t1.cromo.length;i++){
            double cmax= t1.cromo[i]>t2.cromo[i] ? t1.cromo[i] : t2.cromo[i];
            double cmin= t1.cromo[i]<t2.cromo[i] ? t1.cromo[i] : t2.cromo[i];
            double I= cmax - cmin;
           // System.err.print(ale.nextDouble() * ((cmin - I *alfa) - (cmax + I *alfa) )+ (cmin - I *alfa));

            double maxRange=1,minRange=0;
            
            double limSup = cmax + (alfa*I);
            double limInf = cmin - (alfa*I);
            
            if(limSup > maxRange) limSup = maxRange;
            if(limInf < minRange) limInf = minRange;

            a1[i]=  ale.nextDouble() * (limSup - limInf ) + (limInf);
            a2[i]=  ale.nextDouble() * (limSup - limInf ) + (limInf);
        }
    }

    public static void genetico(String[] args) {
        //Variables generales del algoritmo
        int generaciones=0; //Generación actual
        FileWriter salida = null; //Fichero de salida
        PrintWriter pw = null; //Flujo de salida
        int n_genes = 8; //Número de genes
        Random aleatorio = new Random(System.currentTimeMillis()); //Semilla aleatoria

        //Parámetros de salida de pantalla
        int i=0; //Contador por defecto
        int n_sal_pantalla = 5; // Número de individuos procesados para mostrar indicación en pantalla

        //Parámetros de entrada del algoritmo por defecto
        double factor_mutacion = 0.1; //Factor de mutación
        double factor_crossover = 0.8; //Factor de crossover
        int size = 200; //Tamaño de la población
        int total_generaciones = 20; //Número máximo de generaciones
        int nts = 2; //Número de individuos en torneo
        int n_eli = 4; //Cantidad de individuos que pasan por elitismo
        int n_hebras = 1; //Cantidad de hebras creadas al evaluar la población

        //Miramos los parámetros pasados del genético

        for(int j = 1; j < args.length; j++)
        {
            if(args[j].equals("--fm"))
            {
                if(j+1 >= args.length)
                {
                    System.err.println("Parámetros incorrectos");
                    System.exit(0);
                }

                    factor_mutacion = Double.parseDouble(args[j+1]);

            }else if(args[j].equals("--fc"))
            {
                if(j+1 >= args.length)
                {
                    System.err.println("Parámetros incorrectos");
                    System.exit(0);
                }

                    factor_crossover = Double.parseDouble(args[j+1]);

            }else if(args[j].equals("--s"))
            {
                if(j+1 >= args.length)
                {
                    System.err.println("Parámetros incorrectos");
                    System.exit(0);
                }

                    size = Integer.parseInt(args[j+1]);

            }else if(args[j].equals("--g"))
            {
                if(j+1 >= args.length)
                {
                    System.err.println("Parámetros incorrectos");
                    System.exit(0);
                }
                    total_generaciones= Integer.parseInt(args[j+1]);

            }else if(args[j].equals("--nts"))
            {
                if(j+1 >= args.length)
                {
                    System.err.println("Parámetros incorrectos");
                    System.exit(0);
                }
                    nts= Integer.parseInt(args[j+1]);

            }else if(args[j].equals("--neli"))
            {
                if(j+1 >= args.length)
                {
                    System.err.println("Parámetros incorrectos");
                    System.exit(0);
                }
                    n_eli = Integer.parseInt(args[j+1]);

            }else if(args[j].equals("--h"))
            {
                if(j+1 >= args.length)
                {
                    System.err.println("Parámetros incorrectos");
                    System.exit(0);
                }
                    n_hebras = Integer.parseInt(args[j+1]);

            }

        }

        System.out.println("Empezando algoritmo genético con los siguientes parámetros");
        System.out.println("==============================================================");
        System.out.println("Factor mutación: " + factor_mutacion);
        System.out.println("Factor crossover: " + factor_crossover);
        System.out.println("Total generaciones: " + total_generaciones);
        System.out.println("Tamaño población: " + size);
        System.out.println("Número individuos torneo: " + nts);
        System.out.println("Cantidad de individuos elitimo: " + n_eli);
        System.out.println("Utilizando " + n_hebras + " hebras simultaneas para evaluaciones.");

        // Mierdaaaaaaaaaaaaaaaaaaaaaaaa

        //System.exit(0);

        //Creamos la población
        ArrayList< individuo > poblacion = new ArrayList< individuo >();
        ArrayList< individuo > poblacion_futura = new ArrayList< individuo >();


        //Inicializamos
        System.err.println("Inicializando población");
        //start_espectro(poblacion);
        start_random(poblacion, size);

        //Evaluamos toda la población
        long tiempoInicio = System.currentTimeMillis();
        System.err.println("Evaluando población:"+poblacion.size());

        //for(individuo a : poblacion){
        for(int a=0; a<poblacion.size()-n_hebras;){

              ArrayList < t_fitness > hebras = new ArrayList<t_fitness> (n_hebras);

              char text = '1';
              //Creamos las hebras
              //System.err.println( "hebra_"+ text+".txt");
              for (int jj=0;jj<n_hebras && a+jj<poblacion.size() ; jj++){
                 hebras.add(new t_fitness(poblacion.get(a+jj), "hebra_"+ text+".txt"));
                 text++;
              }

              //Las lanzamos
              for(t_fitness t : hebras){
                  t.start();
              }

              //Esperamos que termine
              try {
                  for (t_fitness t : hebras){
                      t.join();
                  }
              }catch (Exception e){

              }

              for(int jj=0; jj< n_hebras; jj++){
                              if(poblacion.get(a).turnos<=5) System.err.println("Algo está fallando :( " + poblacion.get(a).ToString());
                              if(i%n_sal_pantalla==0){
                                long totalTiempo = System.currentTimeMillis() - tiempoInicio;
                                System.err.print("[" + i+"]("+(double) totalTiempo/1000/60+" min)");
                              }
                              System.err.print(".");
                              i++;
                              a++;
              }



        }

        System.err.println("\n Ajustando población");

        //Ordenamos
        Collections.sort(poblacion, new IndividuoComparator());

        System.err.println("Almacenando información sobre individuos");
        //Almacenamos
        try{
        salida = new FileWriter("./logs/genera"+Integer.toString(generaciones)+".txt");
        pw = new PrintWriter (salida);

        for(individuo a: poblacion){
          //System.err.println(a.ToString());
            pw.println(a.ToString());

        }
        }catch (Exception e) {
             e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para
           // asegurarnos que se cierra el fichero.
           if (null != salida)
              salida.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }


        //Comienza el algoritmo genético en si

        while(generaciones < total_generaciones){
            generaciones++;

            System.out.println("La población se encuentra en la " + generaciones + " generación");
            System.err.println("Población:"+poblacion.size());
            //Elitimos
            for(int j=0; j<n_eli;j++) poblacion_futura.add(poblacion.get(j));

            //Selecionamos un individuo
            for(individuo t1: poblacion){
                int selecion;
                int sel=aleatorio.nextInt(poblacion.size());
                //Selecionamos su torneo. Hemos generado n números aleatorios que indican la posición de los indiviuos en el array. Como están ordenados según su fitness, nos quedamos con el menor
                for(int j =0 ; j< nts; j++){
                    //selecion=aleatorio.nextInt(poblacion.size());
                    selecion=aleatorio.nextInt(poblacion.size());
                    if(selecion<sel && !t1.equals(poblacion.get(selecion))){
                        sel = selecion;
                    }
                }

                //en sel tenemos la posición del individuo que vamos a cruzar.
                double [] h1={0,0,0,0,0,0,0,0},h2={0,0,0,0,0,0,0,0};

                //Hacemos que los padres tengan descendencia
                if(aleatorio.nextDouble()<=factor_crossover){

                recombination_blx(t1, poblacion.get(sel), h1, h2);
                //recombination_part(t1, poblacion.get(sel), h1);

                }else{
                    h1[0]=t1.cromo[0];
                    h1[1]=t1.cromo[1];
                    h1[2]=t1.cromo[2];
                    h1[3]=t1.cromo[3];
                    h1[4]=t1.cromo[4];
                    h1[5]=t1.cromo[5];
                    h1[6]=t1.cromo[6];
                    h1[7]=t1.cromo[7];
                }

                //Mutación
                for(i= 0 ;i<h1.length;i++){
                    mutation(h1[i],factor_mutacion);
                    mutation(h2[i],factor_mutacion);
                }


                //Añadimos uno de los dos individuos a la población.
                poblacion_futura.add(new individuo(h1));
               // poblacion_futura.add(new individuo(h2));
                //poblacion.remove(t1);
               // borrar = t1;
                //poblacion.remove(sel);
            }

            poblacion.clear();
            poblacion.addAll(poblacion_futura);
            poblacion_futura.clear();

            //Como tenemos elitismo, los n peores individuos "mueren"
            for(int h = 0; h< n_eli ; h++)
                poblacion.remove(poblacion.size()-1);


            //Evaluamos de nuevo los nuevos individuos
            i=0;
             for(int a=0; a<poblacion.size()-n_hebras;){

                  ArrayList < t_fitness > hebras = new ArrayList<t_fitness> (n_hebras);

                  char text = '1';
                  //Creamos las hebras
              for (int jj=0;jj<n_hebras && a+jj<poblacion.size() ; jj++){
                 hebras.add(new t_fitness(poblacion.get(a+jj), "hebra_"+ text+".txt"));
                 text++;
              }

                  //Las lanzamos
                  for(t_fitness t : hebras){
                      t.start();
                  }

                  //Esperamos que termine
                  try {
                      for (t_fitness t : hebras){
                          t.join();
                      }
                  }catch (Exception e){

                  }

                  for(int jj=0; jj< n_hebras; jj++){
                                  if(poblacion.get(a).turnos<=5){
                                      System.err.println("Algo está fallando :( " + poblacion.get(a).ToString());
                                      System.err.println("Volvemos a ejecutar");
                                      fitness(poblacion.get(a), "fallando.txt"+System.currentTimeMillis());
                                      System.err.println("NUEVO RESULTADO" + poblacion.get(a).ToString());
                                  }
                                  if(i%n_sal_pantalla==0){
                                    long totalTiempo = System.currentTimeMillis() - tiempoInicio;
                                    System.err.print("[" + i+"]("+(double) totalTiempo/1000/60+" min)");
                                  }
                                  System.err.print(".");
                                  i++;
                                  a++;
                  }

             }//Del FOR de la evaluación
            System.err.print("\n");
            Collections.sort(poblacion, new IndividuoComparator());

            //Almacenamos
        try{
        salida = new FileWriter("./logs/genera"+Integer.toString(generaciones)+".txt");
        pw = new PrintWriter (salida);

        for(individuo a: poblacion){
          //System.err.println(a.ToString());
            pw.println(a.ToString());

        }
        }catch (Exception e) {
             e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para
           // asegurarnos que se cierra el fichero.
           if (null != salida)
              salida.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }

        }

    }

    public static void coevolution(String[] args){ /*
        //Variables generales del algoritmo
        int generaciones=0; //Generación actual
        FileWriter salida = null; //Fichero de salida
        PrintWriter pw = null; //Flujo de salida
        int n_genes = 8; //Número de genes
        Random aleatorio = new Random(System.currentTimeMillis()); //Semilla aleatoria

        //Parámetros de salida de pantalla
        int i=0; //Contador por defecto
        int n_sal_pantalla = 5; // Número de individuos procesados para mostrar indicación en pantalla

        //Parámetros de entrada del algoritmo por defecto
        double factor_mutacion = 0.1; //Factor de mutación
        double factor_crossover = 0.8; //Factor de crossover
        int size = 200; //Tamaño de la población
        int total_generaciones = 20; //Número máximo de generaciones
        int nts = 2; //Número de individuos en torneo
        int n_eli = 4; //Cantidad de individuos que pasan por elitismo
        int n_hebras = 1; //Cantidad de hebras creadas al evaluar la población

        //Miramos los parámetros pasados del genético

        for(int j = 1; j < args.length; j++)
        {
            if(args[j].equals("--fm"))
            {
                if(j+1 >= args.length)
                {
                    System.err.println("Parámetros incorrectos");
                    System.exit(0);
                }

                    factor_mutacion = Double.parseDouble(args[j+1]);

            }else if(args[j].equals("--fc"))
            {
                if(j+1 >= args.length)
                {
                    System.err.println("Parámetros incorrectos");
                    System.exit(0);
                }

                    factor_crossover = Double.parseDouble(args[j+1]);

            }else if(args[j].equals("--s"))
            {
                if(j+1 >= args.length)
                {
                    System.err.println("Parámetros incorrectos");
                    System.exit(0);
                }

                    size = Integer.parseInt(args[j+1]);

            }else if(args[j].equals("--g"))
            {
                if(j+1 >= args.length)
                {
                    System.err.println("Parámetros incorrectos");
                    System.exit(0);
                }
                    total_generaciones= Integer.parseInt(args[j+1]);

            }else if(args[j].equals("--nts"))
            {
                if(j+1 >= args.length)
                {
                    System.err.println("Parámetros incorrectos");
                    System.exit(0);
                }
                    nts= Integer.parseInt(args[j+1]);

            }else if(args[j].equals("--neli"))
            {
                if(j+1 >= args.length)
                {
                    System.err.println("Parámetros incorrectos");
                    System.exit(0);
                }
                    n_eli = Integer.parseInt(args[j+1]);

            }else if(args[j].equals("--h"))
            {
                if(j+1 >= args.length)
                {
                    System.err.println("Parámetros incorrectos");
                    System.exit(0);
                }
                    n_hebras = Integer.parseInt(args[j+1]);

            }

        }

        System.out.println("Empezando algoritmo genético con los siguientes parámetros");
        System.out.println("==============================================================");
        System.out.println("Factor mutación: " + factor_mutacion);
        System.out.println("Factor crossover: " + factor_crossover);
        System.out.println("Total generaciones: " + total_generaciones);
        System.out.println("Tamaño población: " + size);
        System.out.println("Número individuos torneo: " + nts);
        System.out.println("Cantidad de individuos elitimo: " + n_eli);
        System.out.println("Utilizando " + n_hebras + " hebras simultaneas para evaluaciones.");

        // Mierdaaaaaaaaaaaaaaaaaaaaaaaa

        //System.exit(0);

        //Creamos la población
        ArrayList< individuo > b_poblacion = new ArrayList< individuo >();
        ArrayList< individuo > b_poblacion_futura = new ArrayList< individuo >();

        ArrayList < sel_mapa > m_poblacion = new ArrayList<sel_mapa>();
        ArrayList < sel_mapa > m_poblacion_futura = new ArrayList<sel_mapa>();

        //Inicializamos
        System.err.println("Inicializando población de bots");
        //start_espectro(poblacion);
        start_random(b_poblacion, size);

        //Inicializamos de mapas
        System.err.println("Inicializando población de mapas");
        //start_espectro(poblacion);
        start_random_m(m_poblacion, size/5);


        //Evaluamos toda la población de bots con el conjunto por defecto de mapas
        long tiempoInicio = System.currentTimeMillis();
        System.err.println("Evaluando población:"+b_poblacion.size());

        for(int a=0; a<b_poblacion.size()-n_hebras;){

              ArrayList < t_fitness > hebras = new ArrayList<t_fitness> (n_hebras);

              char text = '1';
              //Creamos las hebras
              //System.err.println( "hebra_"+ text+".txt");
              for (int jj=0;jj<n_hebras && a+jj<b_poblacion.size() ; jj++){
                 hebras.add(new t_fitness(b_poblacion.get(a+jj), "hebra_"+ text+".txt"));
                 text++;
              }

              //Las lanzamos
              for(t_fitness t : hebras){
                  t.start();
              }

              //Esperamos que termine
              try {
                  for (t_fitness t : hebras){
                      t.join();
                  }
              }catch (Exception e){

              }

              for(int jj=0; jj< n_hebras; jj++){
                              if(b_poblacion.get(a).turnos<=5) System.err.println("Algo está fallando :( " + b_poblacion.get(a).ToString());
                              if(i%n_sal_pantalla==0){
                                long totalTiempo = System.currentTimeMillis() - tiempoInicio;
                                System.err.print("[" + i+"]("+(double) totalTiempo/1000/60+" min)");
                              }
                              System.err.print(".");
                              i++;
                              a++;
              }



        }

        System.err.println("\n Ajustando población");

        //Ordenamos
        Collections.sort(b_poblacion, new IndividuoComparator());

        System.err.println("Almacenando información sobre individuos");
        //Almacenamos
        try{
        salida = new FileWriter("./logs/bots_genera"+Integer.toString(generaciones)+".txt");
        pw = new PrintWriter (salida);

        for(individuo a: b_poblacion){
          //System.err.println(a.ToString());
            pw.println(a.ToString());

        }
        }catch (Exception e) {
             e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para
           // asegurarnos que se cierra el fichero.
           if (null != salida)
              salida.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }


        System.err.println("Evaluando población de Mapas:"+m_poblacion.size());

        i = 0;
        for(int a=0; a<m_poblacion.size()-n_hebras;){

              ArrayList < t_fitness_m > hebras = new ArrayList<t_fitness_m> (n_hebras);

              char text = '1';
              //Creamos las hebras
              //System.err.println( "hebra_"+ text+".txt");
              for (int jj=0;jj<n_hebras && a+jj<b_poblacion.size() ; jj++){
                 hebras.add(new t_fitness_m(b_poblacion.get(0),"hebra_"+ text+".txt", m_poblacion.get(a+jj)));
                 text++;
              }

              //Las lanzamos
              for(t_fitness_m t : hebras){
                  t.start();
              }

              //Esperamos que termine
              try {
                  for (t_fitness_m t : hebras){
                      t.join();
                  }
              }catch (Exception e){

              }

              for(int jj=0; jj< n_hebras; jj++){                              
                              if(i%n_sal_pantalla==0){
                                long totalTiempo = System.currentTimeMillis() - tiempoInicio;
                                System.err.print("[" + i+"]("+(double) totalTiempo/1000/60+" min)");
                              }
                              System.err.print(".");
                              i++;
                              a++;
              }



        }

        System.err.println("\n Ajustando población");

        //Ordenamos
        Collections.sort(m_poblacion, new sel_mapaComparator());


        System.err.println("Almacenando información sobre mapas");
        //Almacenamos
        try{
        salida = new FileWriter("./logs/mapas_genera"+Integer.toString(generaciones)+".txt");
        pw = new PrintWriter (salida);

        for(individuo a: b_poblacion){
          //System.err.println(a.ToString());
            pw.println(a.ToString());

        }
        }catch (Exception e) {
             e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para
           // asegurarnos que se cierra el fichero.
           if (null != salida)
              salida.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }

        //Comienza el algoritmo genético en si

        while(generaciones < total_generaciones){
            generaciones++;

            System.out.println("La población se encuentra en la " + generaciones + " generación");
            System.err.println("Población:"+b_poblacion.size());
            //Elitimos
            for(int j=0; j<n_eli;j++) b_poblacion_futura.add(b_poblacion.get(j));

            //Selecionamos un individuo
            for(individuo t1: b_poblacion){
                int selecion;
                int sel=aleatorio.nextInt(b_poblacion.size());
                //Selecionamos su torneo. Hemos generado n números aleatorios que indican la posición de los indiviuos en el array. Como están ordenados según su fitness, nos quedamos con el menor
                for(int j =0 ; j< nts; j++){
                    //selecion=aleatorio.nextInt(poblacion.size());
                    selecion=aleatorio.nextInt(b_poblacion.size());
                    if(selecion<sel && !t1.equals(b_poblacion.get(selecion))){
                        sel = selecion;
                    }
                }

                //en sel tenemos la posición del individuo que vamos a cruzar.
                double [] h1={0,0,0,0,0,0,0,0},h2={0,0,0,0,0,0,0,0};

                //Hacemos que los padres tengan descendencia
                if(aleatorio.nextDouble()<=factor_crossover){

                recombination_blx(t1, b_poblacion.get(sel), h1, h2);
                //recombination_part(t1, poblacion.get(sel), h1);

                }else{
                    h1[0]=t1.cromo[0];
                    h1[1]=t1.cromo[1];
                    h1[2]=t1.cromo[2];
                    h1[3]=t1.cromo[3];
                    h1[4]=t1.cromo[4];
                    h1[5]=t1.cromo[5];
                    h1[6]=t1.cromo[6];
                    h1[7]=t1.cromo[7];
                }

                //Mutación
                for(i= 0 ;i<h1.length;i++){
                    mutation(h1[i],factor_mutacion);
                    mutation(h2[i],factor_mutacion);
                }


                //Añadimos uno de los dos individuos a la población.
                b_poblacion_futura.add(new individuo(h1));
               // poblacion_futura.add(new individuo(h2));
                //poblacion.remove(t1);
               // borrar = t1;
                //poblacion.remove(sel);
            }

            b_poblacion.clear();
            b_poblacion.addAll(b_poblacion_futura);
            b_poblacion_futura.clear();

            //Como tenemos elitismo, los n peores individuos "mueren"
            for(int h = 0; h< n_eli ; h++)
                b_poblacion.remove(b_poblacion.size()-1);


            //Evaluamos de nuevo los nuevos individuos
            i=0;
             for(int a=0; a<b_poblacion.size()-n_hebras;){

                  ArrayList < t_fitness > hebras = new ArrayList<t_fitness> (n_hebras);

                  char text = '1';
                  //Creamos las hebras
              for (int jj=0;jj<n_hebras && a+jj<b_poblacion.size() ; jj++){
                 hebras.add(new t_fitness(b_poblacion.get(a+jj), "hebra_"+ text+".txt",m_poblacion.get(0)));
                 text++;
              }

                  //Las lanzamos
                  for(t_fitness t : hebras){
                      t.start();
                  }

                  //Esperamos que termine
                  try {
                      for (t_fitness t : hebras){
                          t.join();
                      }
                  }catch (Exception e){

                  }

                  for(int jj=0; jj< n_hebras; jj++){
                                  if(b_poblacion.get(a).turnos<=5){
                                      System.err.println("Algo está fallando :( " + b_poblacion.get(a).ToString());
                                      System.err.println("Volvemos a ejecutar");
                                      fitness(b_poblacion.get(a), "fallando.txt"+System.currentTimeMillis());
                                      System.err.println("NUEVO RESULTADO" + b_poblacion.get(a).ToString());
                                  }
                                  if(i%n_sal_pantalla==0){
                                    long totalTiempo = System.currentTimeMillis() - tiempoInicio;
                                    System.err.print("[" + i+"]("+(double) totalTiempo/1000/60+" min)");
                                  }
                                  System.err.print(".");
                                  i++;
                                  a++;
                  }

             }//Del FOR de la evaluación
            System.err.print("\n");
            Collections.sort(b_poblacion, new IndividuoComparator());

            //Almacenamos
        try{
        salida = new FileWriter("./logs/genera"+Integer.toString(generaciones)+".txt");
        pw = new PrintWriter (salida);

        for(individuo a: b_poblacion){
          //System.err.println(a.ToString());
            pw.println(a.ToString());

        }
        }catch (Exception e) {
             e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para
           // asegurarnos que se cierra el fichero.
           if (null != salida)
              salida.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }

        //Empezando genético de los mapas:

         System.out.println("La población de mapas se encuentra en la " + generaciones + " generación");
            System.err.println("Población:"+m_poblacion.size());
            //Elitimos
            for(int j=0; j<n_eli;j++) m_poblacion_futura.add(m_poblacion.get(j));

            //Selecionamos un individuo
            for(sel_mapa t1: m_poblacion){
                int selecion;
                int sel=aleatorio.nextInt(b_poblacion.size());
                //Selecionamos su torneo. Hemos generado n números aleatorios que indican la posición de los indiviuos en el array. Como están ordenados según su fitness, nos quedamos con el menor
                for(int j =0 ; j< nts; j++){
                    selecion=aleatorio.nextInt(m_poblacion.size());
                    if(selecion<sel && !t1.equals(m_poblacion.get(selecion))){
                        sel = selecion;
                    }
                }

                //en sel tenemos la posición del individuo que vamos a cruzar.
                double [] h1={0,0,0,0,0,0,0,0},h2={0,0,0,0,0,0,0,0};

                //Hacemos que los padres tengan descendencia
                if(aleatorio.nextDouble()<=factor_crossover){

                
                //recombination_part(t1, poblacion.get(sel), h1);

                }else{
                    h1[0]=t1.cromo[0];
                    h1[1]=t1.cromo[1];
                    h1[2]=t1.cromo[2];
                    h1[3]=t1.cromo[3];
                    h1[4]=t1.cromo[4];
                    h1[5]=t1.cromo[5];
                    h1[6]=t1.cromo[6];
                    h1[7]=t1.cromo[7];
                }

                //Mutación
                for(i= 0 ;i<h1.length;i++){
                    mutation(h1[i],factor_mutacion);
                    mutation(h2[i],factor_mutacion);
                }


                //Añadimos uno de los dos individuos a la población.
                b_poblacion_futura.add(new individuo(h1));
               // poblacion_futura.add(new individuo(h2));
                //poblacion.remove(t1);
               // borrar = t1;
                //poblacion.remove(sel);
            }

            b_poblacion.clear();
            b_poblacion.addAll(b_poblacion_futura);
            b_poblacion_futura.clear();

            //Como tenemos elitismo, los n peores individuos "mueren"
            for(int h = 0; h< n_eli ; h++)
                b_poblacion.remove(b_poblacion.size()-1);


            //Evaluamos de nuevo los nuevos individuos
            i=0;
             for(int a=0; a<b_poblacion.size()-n_hebras;){

                  ArrayList < t_fitness > hebras = new ArrayList<t_fitness> (n_hebras);

                  char text = '1';
                  //Creamos las hebras
              for (int jj=0;jj<n_hebras && a+jj<b_poblacion.size() ; jj++){
                 hebras.add(new t_fitness(b_poblacion.get(a+jj), "hebra_"+ text+".txt",m_poblacion.get(0)));
                 text++;
              }

                  //Las lanzamos
                  for(t_fitness t : hebras){
                      t.start();
                  }

                  //Esperamos que termine
                  try {
                      for (t_fitness t : hebras){
                          t.join();
                      }
                  }catch (Exception e){

                  }

                  for(int jj=0; jj< n_hebras; jj++){
                                  if(b_poblacion.get(a).turnos<=5){
                                      System.err.println("Algo está fallando :( " + b_poblacion.get(a).ToString());
                                      System.err.println("Volvemos a ejecutar");
                                      fitness(b_poblacion.get(a), "fallando.txt"+System.currentTimeMillis());
                                      System.err.println("NUEVO RESULTADO" + b_poblacion.get(a).ToString());
                                  }
                                  if(i%n_sal_pantalla==0){
                                    long totalTiempo = System.currentTimeMillis() - tiempoInicio;
                                    System.err.print("[" + i+"]("+(double) totalTiempo/1000/60+" min)");
                                  }
                                  System.err.print(".");
                                  i++;
                                  a++;
                  }

             }//Del FOR de la evaluación
            System.err.print("\n");
            Collections.sort(b_poblacion, new IndividuoComparator());

            //Almacenamos
        try{
        salida = new FileWriter("./logs/genera"+Integer.toString(generaciones)+".txt");
        pw = new PrintWriter (salida);

        for(individuo a: b_poblacion){
          //System.err.println(a.ToString());
            pw.println(a.ToString());

        }
        }catch (Exception e) {
             e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para
           // asegurarnos que se cierra el fichero.
           if (null != salida)
              salida.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }


        } */
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(args[0].equals("genetico")){
            genetico(args);
        }else if (args[0].equals("coevolutcion")){
            coevolution(args);
        }else if (args[0].equals("battletest")){
            battletest(args);
        }else if (args[0].equals("battletestbots")){
            battletestbots(args);
        }
       
    }
}
