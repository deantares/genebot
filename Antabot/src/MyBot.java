
import java.util.*;
import java.util.ArrayList;

/**
 *
 * @author Antonio Fernandez Ares
 * @e-mail antares.es@gmail.com Universidad de Granada - Spain
 */
public class MyBot {

    private static boolean debug = false;

    public static void DoTurn(PlanetWars pw) {

        //Variables
        Random aleatorio = new Random(); //Variable aleatoria
        Planet base = null; //Es la base principal de nuestro ejercito

        //Peso[0] Porcentaje de tropas en Diezmo
        // Peso[1] Posibilidad diezmo
        //Peso[2] Peso numero de naves
        //Peso[3] Peso de factor de crecimiento
        //Peso[4] Peso de la distancia
        //Peso[5] Flotas_extras

        /* double diezmo = 0.10;
         int flotas_extras = 5;
         double p_NS = 1;
         double p_GR = 1;
         double p_DIS = 1; */

//0.48231655449480576
//0.10649542170626028
//0.5623083960457418
//0.40529883928645455
//0.3515051786780067
//0.6441622892638008
//0.5989578698935684
//0.8251208746187438
//Turnos 716

//0.16259146919042347
//0.020777112417761187
//0.540427710359566
//0.7568133697690975
//0.42192190816780395
//0.7733812572486694
//0.37517566326974067
//0.45170600185766996
//Turnos:596

        double diezmo = 0.09978267816749446;
        double p_diezmo = 0.23032668116070584;
        double p_NS = 0.06446668711729682;
        double p_GR = 0.3145662812995889;
        double p_DIS = 0.6783974914572055;
        int flotas_extras = (int) (0.7306356321118216 * 20);
        double envio_secundo = 0.6645303530434207;
        double p_envio_secundo = 0.7107283003587886;

        //System.err.println("---------------> 1");
        //Peso[0] Diezmo
        //Peso[1] Flotas_extras
        //Peso[2] Peso numero de naves
        //Peso[3] Peso de factor de crecimiento
        //Peso[4] Peso de la distancia
        // Peso[5]

        //System.err.println("---------------> 2");

        try {

            //Localizamos el planeta principal
            double flotabase = Double.MIN_VALUE;
            for (Planet p : pw.MyPlanets()) {
                double flota = (double) p.NumShips();
                if (flota > flotabase) {
                    flotabase = flota;
                    base = p;
                }
            }




            //System.err.println("---------------> 3");

            //Pasamos revista para ver a que planetas estamos enviando naves. Mandar dos tripulaciones es un gasto estupido.
            int[] v = new int[pw.NumPlanets()];
            for (Fleet naves : pw.MyFleets()) {
                v[naves.DestinationPlanet()] = 1;
            }

            double dist_total = 0, dist = 0;
            // Localizamos el siguiente planeta que vamos a atacar. Atacamos como mucho un planeta por turno
            Planet objetivo = null;
            double flotasobjetivo = Double.MAX_VALUE;
            for (Planet p : pw.NotMyPlanets()) {
                //double score = p.NumShips();
                dist = pw.Distance(p.PlanetID(), base.PlanetID());
                //double score = (p.NumShips() * p_NS ) / ((1+p.GrowthRate()*2)*p_GR)*(p_DIS * Math.sqrt(dist));

                double score = ((p.NumShips() * p_NS) * (p_DIS * dist)) / ((1 + p.GrowthRate()) * p_GR * 2.5);

                //double score = p.NumShips()/(1+p.GrowthRate()*2)*(pw.Distance(p.PlanetID(), base.PlanetID()))^(1/2);
                //double score = p.NumShips()/(p.GrowthRate()^2 +1)*(pw.Distance(p.PlanetID(), base.PlanetID()));
                //double score = (p_NS * p.NumShips() ) - (p_GR * p.GrowthRate()) + (p_DIS * pw.Distance(p.PlanetID(), base.PlanetID()) );
                //System.err.println("Planeta:" + p.PlanetID() + " Grow:" + p.GrowthRate() + " Distacia base:" + pw.Distance(p.PlanetID(), base.PlanetID()) );
                if ((score < flotasobjetivo) && v[p.PlanetID()] == 0) {
                    flotasobjetivo = score;
                    objetivo = p;
                    dist_total = dist;
                }
            }


            //Realizamos el diezmo o envio, esto es, la paga de tropas a la base central o el envio de tropas auxiliares.
            int mandando = 0;
            //Toca diezmo
            for (Planet colonia : pw.MyPlanets()) {
                if (colonia != base) {
                    if (aleatorio.nextDouble() < p_diezmo) {
                        int numShips = (int) (colonia.NumShips() * diezmo);
                        if (numShips > 0) {
                            pw.IssueOrder(colonia, base, numShips);
                        }
                    } else if (dist < dist && (aleatorio.nextDouble() < p_envio_secundo) && mandando < objetivo.NumShips()) {
                        int numShips = (int) (colonia.NumShips() * envio_secundo);
                        if (numShips > 0) {
                            pw.IssueOrder(colonia, objetivo, numShips);
                            mandando += numShips;
                        }
                    }
                }
            }



            //Mandamos las tropas de la base al objetivo
            if (base != null && objetivo != null) {
                if (objetivo.Owner() == 0) {
                    //Estamos expandiendo, por tanto no tenemos generacion de tropas.
                    if (base.NumShips() > (objetivo.NumShips() + flotas_extras - mandando)) {
                        int armada = (int) (flotas_extras + objetivo.NumShips() - mandando);
                        pw.IssueOrder(base, objetivo, armada);
                    }
                } else {
                    //Estamos conquistando, por tanto tenemos generacion de tropas. Lo consideramos
                    int a = (pw.Distance(base.PlanetID(), objetivo.PlanetID()) * objetivo.GrowthRate()) + objetivo.NumShips() + flotas_extras - mandando;

                    if (base.NumShips() > a) {
                        int armada = a;
                        pw.IssueOrder(base, objetivo, armada);
                    }
                }
            }

            //System.err.println("---------------> n");

        } catch (Exception e) {
            //System.err.println(e.getMessage());
        }

    }

    public static void DoTurn(PlanetWars pw, double[] pesos) {

        //Variables
        Random aleatorio = new Random(); //Variable aleatoria
        Planet base = null; //Es la base principal de nuestro ejercito

        //Peso[0] Porcentaje de tropas en Diezmo
        // Peso[1] Posibilidad diezmo
        //Peso[2] Peso numero de naves
        //Peso[3] Peso de factor de crecimiento
        //Peso[4] Peso de la distancia
        //Peso[5] Flotas_extras

        /* double diezmo = 0.10;
         int flotas_extras = 5;
         double p_NS = 1;
         double p_GR = 1;
         double p_DIS = 1; */

        double diezmo = pesos[0];
        double p_diezmo = pesos[1];
        double p_NS = pesos[2];
        double p_GR = pesos[3];
        double p_DIS = pesos[4];
        int flotas_extras = (int) (pesos[5] * 20);
        double envio_secundo = pesos[6];
        double p_envio_secundo = pesos[7];

        //System.err.println("---------------> 1");
        //Peso[0] Diezmo
        //Peso[1] Flotas_extras
        //Peso[2] Peso numero de naves
        //Peso[3] Peso de factor de crecimiento
        //Peso[4] Peso de la distancia
        // Peso[5]

        //System.err.println("---------------> 2");

        try {

            //Localizamos el planeta principal
            double flotabase = Double.MIN_VALUE;
            for (Planet p : pw.MyPlanets()) {
                double flota = (double) p.NumShips();
                if (flota > flotabase) {
                    flotabase = flota;
                    base = p;
                }
            }




            //System.err.println("---------------> 3");

            //Pasamos revista para ver a que planetas estamos enviando naves. Mandar dos tripulaciones es un gasto estupido.
            int[] v = new int[pw.NumPlanets()];
            for (Fleet naves : pw.MyFleets()) {
                v[naves.DestinationPlanet()] = 1;
            }

            double dist_total = 0, dist = 0;
            // Localizamos el siguiente planeta que vamos a atacar. Atacamos como mucho un planeta por turno
            Planet objetivo = null;
            double flotasobjetivo = Double.MAX_VALUE;
            for (Planet p : pw.NotMyPlanets()) {
                //double score = p.NumShips();
                dist = pw.Distance(p.PlanetID(), base.PlanetID());
                //double score = (p.NumShips() * p_NS ) / ((1+p.GrowthRate()*2)*p_GR)*(p_DIS * Math.sqrt(dist));

                double score = ((p.NumShips() * p_NS) * (p_DIS * Math.sqrt(dist)) / ((1 + p.GrowthRate() * 2) * p_GR));

                //double score = p.NumShips()/(1+p.GrowthRate()*2)*(pw.Distance(p.PlanetID(), base.PlanetID()))^(1/2);
                //double score = p.NumShips()/(p.GrowthRate()^2 +1)*(pw.Distance(p.PlanetID(), base.PlanetID()));
                //double score = (p_NS * p.NumShips() ) - (p_GR * p.GrowthRate()) + (p_DIS * pw.Distance(p.PlanetID(), base.PlanetID()) );
                //System.err.println("Planeta:" + p.PlanetID() + " Grow:" + p.GrowthRate() + " Distacia base:" + pw.Distance(p.PlanetID(), base.PlanetID()) );
                if ((score < flotasobjetivo) && v[p.PlanetID()] == 0) {
                    flotasobjetivo = score;
                    objetivo = p;
                    dist_total = dist;
                }
            }


            //Realizamos el diezmo o envio, esto es, la paga de tropas a la base central o el envio de tropas auxiliares.
            int mandando = 0;
            //Toca diezmo
            for (Planet colonia : pw.MyPlanets()) {
                if (colonia != base) {
                    if (aleatorio.nextDouble() < p_diezmo) {
                        int numShips = (int) (colonia.NumShips() * diezmo);
                        if (numShips > 0) {
                            pw.IssueOrder(colonia, base, numShips);
                        }
                    } else if (dist < dist && (aleatorio.nextDouble() < p_envio_secundo) && mandando < objetivo.NumShips()) {
                        int numShips = (int) (colonia.NumShips() * envio_secundo);
                        if (numShips > 0) {
                            pw.IssueOrder(colonia, objetivo, numShips);
                            mandando += numShips;
                        }
                    }
                }
            }



            //Mandamos las tropas de la base al objetivo
            if (base != null && objetivo != null) {
                if (objetivo.Owner() == 0) {
                    //Estamos expandiendo, por tanto no tenemos generacion de tropas.
                    if (base.NumShips() > (objetivo.NumShips() + flotas_extras - mandando)) {
                        int armada = (int) (flotas_extras + objetivo.NumShips() - mandando);
                        pw.IssueOrder(base, objetivo, armada);
                    }
                } else {
                    //Estamos conquistando, por tanto tenemos generacion de tropas. Lo consideramos
                    int a = (pw.Distance(base.PlanetID(), objetivo.PlanetID()) * objetivo.GrowthRate()) + objetivo.NumShips() + flotas_extras - mandando;

                    if (base.NumShips() > a) {
                        int armada = a;
                        pw.IssueOrder(base, objetivo, armada);
                    }
                }
            }

            //System.err.println("---------------> n");

        } catch (Exception e) {
            //System.err.println(e.getMessage());
        }

    }

    public static void DoTurnEX(PlanetWars pw) {
        if (debug) {
            System.err.println("Comenzando!");
        }

        //Variables
        Random aleatorio = new Random(); //Variable aleatoria
        Planet base = null; //Es la base principal de nuestro ejercito
        Planet baseenemiga = null; //Es la base enemiga

        categoriaMapa mapa = new categoriaMapa();
        if (debug) {
            System.err.println("Categoria Mapa creada");
        }
        int[][] m = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}; //Matriz de dispersión
        matriz_experta me = new matriz_experta();

        if (debug) {
            System.err.println("Matriz experta creada");
        }

        try {

            //Localizamos el planeta principal
            if (debug) {
                System.err.print("Localizando planeta BASE: ");
            }
            double flotabase = Double.MIN_VALUE;
            for (Planet p : pw.MyPlanets()) {
                int a = (int) Math.floor(p.X() / 8);
                m[(int) Math.floor(p.X() / 8)][(int) Math.floor(p.Y() / 8)]++;
                double flota = (double) p.NumShips();
                if (flota > flotabase) {
                    flotabase = flota;
                    base = p;
                }
            }
            if (debug) {
                System.err.println("OK");
            }
            if (debug) {
                System.err.print("Localizando planeta BASE enemigo: ");
            }
            double flotabaseenemiga = Double.MIN_VALUE;
            for (Planet p : pw.EnemyPlanets()) {
                double flota = (double) p.NumShips();
                m[(int) Math.floor(p.X() / 8)][(int) Math.floor(p.Y() / 8)]++;
                if (flota > flotabaseenemiga) {
                    flotabaseenemiga = flota;
                    baseenemiga = p;
                }
            }
            if (debug) {
                System.err.println("OK");
            }
            if (debug) {
                System.err.print("Analizando planetas neutrales: ");
            }
            for (Planet p : pw.NeutralPlanets()) {
                m[(int) Math.floor(p.X() / 8)][(int) Math.floor(p.Y() / 8)]++;
            }
            if (debug) {
                System.err.println("OK");
            }



            //Calculamos la distancia:
            if (debug) {
                System.err.print("Calculando distancia: ");
            }
            mapa.setDistancia(pw.Distance_double(base.PlanetID(), baseenemiga.PlanetID()));
            if (debug) {
                System.err.println("OK");
            }
            //Calculamos la dispersión
            if (debug) {
                System.err.print("Calculando dispersion:");
            }
            mapa.setDispersion((m[1][1] * 8.0 - m[0][0] - m[0][1] - m[0][2] - m[1][0] - m[1][2] - m[2][0] - m[2][1] - m[2][2]) / 8.0);
            if (debug) {
                System.err.println("OK");
            }

            //System.err.println("MEDIDAS:" + mapa.toString() +" [" + mapa.dispersion + "," + mapa.distancia + "]");
            System.err.println(mapa.toString());
            if (debug) {
                System.err.println(m[0][0] + " " + m[0][1] + " " + m[0][2] + "\n" + m[1][0] + " " + m[1][1] + " " + m[1][2] + "\n" + m[2][0] + " " + m[2][1] + " " + m[2][2] + "\n");
            }

            if (debug) {
                System.err.print("Consiguiendo mapa experto (" + mapa.toString() + "): ");
            }
            double[] pesos = me.get_experto(mapa.toString());
            if (debug) {
                System.err.println("OK");
            }

            if (debug) {
                System.err.print("Asignando pesos (" + pesos.length + "): ");
            }
            double diezmo = pesos[0];
            double p_diezmo = pesos[1];
            double p_NS = pesos[2];
            double p_GR = pesos[3];
            double p_DIS = pesos[4];
            int flotas_extras = (int) (pesos[5] * 20);
            double envio_secundo = pesos[6];
            double p_envio_secundo = pesos[7];
            if (debug) {
                System.err.println("OK");
            }



            //System.err.println("---------------> 3");

            //Pasamos revista para ver a que planetas estamos enviando naves. Mandar dos tripulaciones es un gasto estupido.
            if (debug) {
                System.err.print("Pasando revista: ");
            }
            int[] v = new int[pw.NumPlanets()];
            for (Fleet naves : pw.MyFleets()) {
                v[naves.DestinationPlanet()] = 1;
            }
            if (debug) {
                System.err.println("OK");
            }
            double dist_total = 0, dist = 0;
            if (debug) {
                System.err.print("Localizando planeta TARGET: ");
            }
            // Localizamos el siguiente planeta que vamos a atacar. Atacamos como mucho un planeta por turno
            Planet objetivo = null;
            double flotasobjetivo = Double.MAX_VALUE;
            for (Planet p : pw.NotMyPlanets()) {
                //double score = p.NumShips();
                dist = pw.Distance(p.PlanetID(), base.PlanetID());
                //double score = (p.NumShips() * p_NS ) / ((1+p.GrowthRate()*2)*p_GR)*(p_DIS * Math.sqrt(dist));

                double score = ((p.NumShips() * p_NS) * (p_DIS * Math.sqrt(dist)) / ((1 + p.GrowthRate() * 2) * p_GR));

                //double score = p.NumShips()/(1+p.GrowthRate()*2)*(pw.Distance(p.PlanetID(), base.PlanetID()))^(1/2);
                //double score = p.NumShips()/(p.GrowthRate()^2 +1)*(pw.Distance(p.PlanetID(), base.PlanetID()));
                //double score = (p_NS * p.NumShips() ) - (p_GR * p.GrowthRate()) + (p_DIS * pw.Distance(p.PlanetID(), base.PlanetID()) );
                //System.err.println("Planeta:" + p.PlanetID() + " Grow:" + p.GrowthRate() + " Distacia base:" + pw.Distance(p.PlanetID(), base.PlanetID()) );
                if ((score < flotasobjetivo) && v[p.PlanetID()] == 0) {
                    flotasobjetivo = score;
                    objetivo = p;
                    dist_total = dist;
                }
            }
            if (debug) {
                System.err.println("OK");
            }

            if (debug) {
                System.err.print("Realizando DIEZMO: ");
            }
            //Realizamos el diezmo o envio, esto es, la paga de tropas a la base central o el envio de tropas auxiliares.
            int mandando = 0;
            //Toca diezmo
            for (Planet colonia : pw.MyPlanets()) {
                if (colonia != base) {
                    if (aleatorio.nextDouble() < p_diezmo) {
                        int numShips = (int) (colonia.NumShips() * diezmo);
                        if (numShips > 0) {
                            pw.IssueOrder(colonia, base, numShips);
                        }
                    } else if (dist < dist && (aleatorio.nextDouble() < p_envio_secundo) && mandando < objetivo.NumShips()) {
                        int numShips = (int) (colonia.NumShips() * envio_secundo);
                        if (numShips > 0) {
                            pw.IssueOrder(colonia, objetivo, numShips);
                            mandando += numShips;
                        }
                    }
                }
            }
            if (debug) {
                System.err.println("OK");
            }


            if (debug) {
                System.err.print("Enviando tropas: ");
            }
            //Mandamos las tropas de la base al objetivo
            if (base != null && objetivo != null) {
                if (objetivo.Owner() == 0) {
                    //Estamos expandiendo, por tanto no tenemos generacion de tropas.
                    if (base.NumShips() > (objetivo.NumShips() + flotas_extras - mandando)) {
                        int armada = (int) (flotas_extras + objetivo.NumShips() - mandando);
                        pw.IssueOrder(base, objetivo, armada);
                    }
                } else {
                    //Estamos conquistando, por tanto tenemos generacion de tropas. Lo consideramos
                    int a = (pw.Distance(base.PlanetID(), objetivo.PlanetID()) * objetivo.GrowthRate()) + objetivo.NumShips() + flotas_extras - mandando;

                    if (base.NumShips() > a) {
                        int armada = a;
                        pw.IssueOrder(base, objetivo, armada);
                    }
                }
            }
            if (debug) {
                System.err.println("OK");
            }

            //System.err.println("---------------> n");

        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println(e.getLocalizedMessage());
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        String line = "Soy";
        String message = "";
        int c;
        try {
            while ((c = System.in.read()) >= 0) {
                switch (c) {
                    case '\n':
                        if (line.equals("go")) {
                            PlanetWars pw = new PlanetWars(message);
                            //long tiempoInicio = System.currentTimeMillis();
                            /*System.err.println("Empezamoooos:" + Float.valueOf(args[0]));
                             System.err.println("Empezamoooos:" + Float.valueOf(args[1]));
                             System.err.println("Empezamoooos:" + Float.valueOf(args[2]));
                             System.err.println("Empezamoooos:" + Float.valueOf(args[3]));
                             System.err.println("Empezamoooos:" + Float.valueOf(args[4]));
                             System.err.println("Empezamoooos:" + Float.valueOf(args[5]));
                             System.err.println("Empezamoooos:" + Float.valueOf(args[6]));
                             System.err.println("Empezamoooos:" + Float.valueOf(args[7]));*/
//                            if (args[0].equals("EX")) {
                            //DoTurnEX(pw);
//                            } else
//                            if (args.length < 7) {
//                                DoTurn(pw);
//                            } else {
                            DoTurn(pw, new double[]{Float.valueOf(args[0]), Float.valueOf(args[1]), Float.valueOf(args[2]), Float.valueOf(args[3]), Float.valueOf(args[4]), Float.valueOf(args[5]), Float.valueOf(args[6]), Float.valueOf(args[7])});
                            //DoTurn(pw);
//                            }
                            //long totalTiempo = System.currentTimeMillis() - tiempoInicio;
                            // System.err.println("TIEMPO EJECUCION:" + totalTiempo + "ms");
                            //System.err.println("P0 - " +  pw.IsAlive(0) );
                            System.err.println("P1 - " + pw.IsAlive(1));
                            System.err.println("P2 - " + pw.IsAlive(2));
                            System.err.println("P3 - " + pw.IsAlive(3));
                            System.err.println("P4 - " + pw.IsAlive(4));
                            pw.FinishTurn();
                            message = "";
                        } else {
                            message += line + "\n";
                        }
                        line = "";
                        break;
                    default:
                        line += (char) c;
                        break;
                }
            }
        } catch (Exception e) {
        }
    }
}
