/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package EvolutionBOT;

import EvolutionBOT.CoEvolution.CoevolutionBOT_4;
import EvolutionBOT.CoEvolution.CoevolutionBOTFloatMax_4;
import EvolutionBOT.CoEvolution.CoevolutionBOT;
import EvolutionBOT.CoEvolution.CoevolutionBOTFloatMax;
import EvolutionBOT.CoEvolutionSinFitness.CoevolutionBOT_no_fitness_basico;
import EvolutionBOT.CoEvolutionSinFitness.CoevolutionBOT_no_fitness_basico_4vs;
import EvolutionBOT.CoEvolutionSinFitness.CoevolutionBOT_no_fitness_estacionario_1vs1;
import java.util.*;
import java.io.*;
import Utils.individuos.individuo;
import Utils.fitness.t_fitness;
import Utils.fitness.t_fitness_versus2;
import Utils.sel_mapa;
import java.util.ArrayList;

/**
 *
 * @author antares
 */
public class Main {

    public static void consistencia_fitness(ArrayList<individuo> C_1, ArrayList<individuo> C_2, String l1) {
        //Todos categoría A vs Todos categoría A
        FileWriter outfile = null;
        PrintWriter pw = null;
        System.out.print(l1 + "\n");
        System.out.print("\t0\t1\t1\t3\t4\t5\t6\t7\t8\t9\n");
        for (int ia = 0; ia < C_1.size(); ia++) {
            System.out.print(ia + "\t");
            //System.out.print("A" + ia);
            for (int iaa = 0; iaa < C_2.size(); iaa++) {
                //System.out.print(iaa + ":");
                C_1.get(ia).gana = false;
                t_fitness t = new t_fitness(C_1.get(ia), C_2.get(iaa), "hebraBB_+" + l1 + ":" + ia + "-" + iaa, new sel_mapa(new int[]{new Random().nextInt(99) + 1}));

                t.run();
                try {
                    t.wait();
                } catch (Exception e) {
                }

                try {

                    outfile = new FileWriter("./logs/genera_A" + Integer.toString(ia) + "_A" + Integer.toString(iaa) + ".txt");
                    pw = new PrintWriter(outfile);

                    //System.err.println(a.ToString());
                    pw.println(C_1.get(ia).ToString());
                    pw.println(C_2.get(iaa).ToString());
                    pw.println("\n");
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
                //System.out.print( C_A.get(ia).ToString());
                //System.out.print("\n");
                System.out.print(C_1.get(ia).gana + "\t");


            }
            System.out.print("\n");
        }
    }

    public static void main(String[] args) {

        if (args[0].equals("SG")) {
            SimpleGenetic SG = new SimpleGenetic(args);
            SG.main();
        } else if (args[0].equals("CO")) {
            CoevolutionBOT SG = new CoevolutionBOT(args);
            SG.main();
        } else if (args[0].equals("COF")) {
            CoevolutionBOTFloatMax COF = new CoevolutionBOTFloatMax(args);
            COF.main();
        } else if (args[0].equals("CO4")) {
            CoevolutionBOT_4 SG = new CoevolutionBOT_4(args);
            SG.main();
        } else if (args[0].equals("COF4")) {
            CoevolutionBOTFloatMax_4 COF = new CoevolutionBOTFloatMax_4(args);
            COF.main();
        } else if (args[0].equals("COSF")) {
            CoevolutionBOT_no_fitness_basico COF = new CoevolutionBOT_no_fitness_basico(args);
            COF.main();  
        }else if (args[0].equals("COSF-G4")) {
            CoevolutionBOT_no_fitness_basico_4vs COF = new CoevolutionBOT_no_fitness_basico_4vs(args);
            COF.main();
        }else if (args[0].equals("COSF-ESTA")) {
            CoevolutionBOT_no_fitness_estacionario_1vs1 COF = new CoevolutionBOT_no_fitness_estacionario_1vs1(args);
            COF.main();
        }else if (args[0].equals("EX")) {
            ExGenetic EX = new ExGenetic(args);
            EX.main();

        } else if (args[0].equals("BB")) {
            t_fitness t = new t_fitness(new individuo(new double[]{0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5,}), "texto");
            t.run();
            try {
                t.wait();
            } catch (Exception e) {
            }
        } else if (args[0].equals("compare")) {
            individuo mediabot = new individuo(new double[]{0.332812769, 0.070841113, 0.591981919, 1, 0.460496021, 0.759319642, 0.49165473, 0.50371368});
            individuo mejorbot = new individuo(new double[]{0.3919305854764415, 0.03027927275131309, 0.6908147364455396, 0.3559640928082235, 0.426402059754704, 0.814945973663688, 0.5549861375996193, 0.21015986425274444});
            individuo aresbot = new individuo(new double[]{0.1, 0.5, 1, 1, 1, 0.25, 0.5, 0.9});

            ArrayList<individuo> listado = new ArrayList<individuo>();
            listado.add(new individuo());
            listado.add(new individuo(new double[]{0.42866617349878516, 0.020996349960960912, 0.5951569661039416, 0.6135336512690417, 0.40440963230191695, 0.6093753586853742, 0.3742897568608773, 0.6818529923565275}));
            listado.add(new individuo(new double[]{0.023031400282765718, 0.288583879303044, 0.7813751517804515, 0.46473260901051044, 0.36669663203870606, 0.7900392616452466, 0.2300694619228707, 0.34710000376167904}));
            listado.add(new individuo(new double[]{0.007751828879710811, 0.4004256871225212, 0.3040806691061873, 0.5715829119547758, 0.48882347986611524, 0.7531843382468174, 0.17879672907929006, 0.4135356841476962}));
            listado.add(new individuo(new double[]{0.00865643416174294, 0.13013017516184602, 0.5942214326636988, 0.4606014377471249, 0.4291740228879596, 0.7313944209624611, 0.7437535147295122, 0.4801369984054713}));
            listado.add(new individuo(new double[]{0.15873361840070377, 0.19245540672612174, 0.5272372731688297, 0.7574950105654106, 0.35912020144138984, 0.2288179671216604, 0.923966384059881, 0.6269960155327602}));
            listado.add(new individuo(new double[]{0.15873361840070377, 0.19245540672612174, 0.5272372731688297, 0.7574950105654106, 0.35912020144138984, 0.2288179671216604, 0.923966384059881, 0.6269960155327602}));
            listado.add(new individuo(new double[]{0.4827950276485812, 0.05327803304277653, 0.05423813065856037, 0.1962467989575315, 0.18804641377935696, 0.7122540096707128, 0.08645176336491368, 0.5868439567098888}));
            listado.add(new individuo(new double[]{0.01798830002878981, 0.0589919310326945, 0.5095445636693665, 0.13954369278880815, 0.23273726269551936, 0.7332158071928045, 0.5894601955924742, 0.9740553181473641}));
            listado.add(new individuo(new double[]{0.49953971129076213, 0.2455070545305153, 0.5512142832806787, 0.6118190061522651, 0.2689422957379329, 0.7111120183561702, 0.7632524872112635, 0.8835856889355379}));
            listado.add(new individuo(new double[]{0.09978267816749446, 0.23032668116070584, 0.06446668711729682, 0.3145662812995889, 0.6783974914572055, 0.7306356321118216, 0.6645303530434207, 0.7107283003587886}));
            listado.add(new individuo(new double[]{0.08317899775001202, 0.269328559584938, 0.4519700602812491, 0.5126615605318623, 0.4497052379606489, 0.7193258268820903, 0.42479491894001076, 0.42518150038686914}));
            listado.add(new individuo(new double[]{0.40614755621904564, 0.008947907619556822, 0.459490372961674, 0.133516444428475, 0.09933458974225966, 0.6902334978297044, 0.6125091436443066, 0.4013289257589658}));
            listado.add(new individuo(new double[]{0.0027405859071194458, 0.399694097996913, 0.531066490957737, 0.5252463108459591, 0.44151805029313607, 0.7665458292633865, 0.31765908013562244, 0.7241157602163053}));
            listado.add(new individuo(new double[]{0.1952137327257209, 0.34166639022401857, 0.30853399836490764, 0.6524125820198226, 0.04900070130282645, 0.6846209417530463, 0.5674632927285976, 0.5595531263396434}));
            listado.add(new individuo(new double[]{0.034907932776653834, 0.11822246346561921, 0.8280008862519138, 0.6861023706620188, 0.6463652786783257, 0.7702612647882575, 0.4674416688335924, 0.5560812601595692}));



            //En primer lugar: Lucha contra GOOGLEBOT
            for (int b = 1; b < 16; b++) {
                System.out.print(b + "->m: ");
                for (int i = 1; i < 101; i++) {
                    System.out.print(i + "-");
                    mejorbot.gana = false;
                    t_fitness t = new t_fitness(listado.get(b), "ejebot" + b + "-" + i + ".txt", new sel_mapa(new int[]{i}));
                    t.run();
                    FileWriter outfile = null;
                    PrintWriter pw = null;
                    try {
                        t.wait();
                    } catch (Exception e) {
                    }

                    try {
                        outfile = new FileWriter("./logs/genera_google_vs_eje" + b + "_mapa" + i + ".txt");
                        pw = new PrintWriter(outfile);

                        //System.err.println(a.ToString());
                        pw.println(mejorbot.ToString());
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
                System.out.print("\n");
            }

        } else if (args[0].equals("fitness")) {
            System.out.println("Probando estabilidad del fitness");
            individuo tio = new individuo(new double[]{0.6147066420960032, 0.15712330060731747, 0.7412458555004816, 0.5286335311106324, 0.3192508512842522, 0.5819896634306796, 0.33138383861409937, 0.6651286683482183,});
            FileWriter outfile = null;
            PrintWriter pw = null;

            ArrayList<individuo> tios = new ArrayList<individuo>();

            //Mejor Individuo de cada ejecución
            tios.add(new individuo(new double[]{0.5998149691289131, 8.466698720355215E-5, 0.3203014274830107, 0.6348218748464499, 0.6715334379640565, 0.7908027437454676, 0.2624520220002189, 0.6332454944811828}));
            tios.add(new individuo(new double[]{0.48231655449480576, 0.10649542170626028, 0.5623083960457418, 0.40529883928645455, 0.3515051786780067, 0.6441622892638008, 0.5989578698935684, 0.8251208746187438}));
            tios.add(new individuo(new double[]{0.3919305854764415, 0.03027927275131309, 0.6908147364455396, 0.3559640928082235, 0.426402059754704, 0.814945973663688, 0.5549861375996193, 0.21015986425274444}));
            tios.add(new individuo(new double[]{0.034222396220094015, 0.46961880715935866, 0.6618127230706216, 0.616489430991588, 0.07952046257968269, 0.7109535986694575, 0.45054368895575203, 0.47592289801101983}));
            tios.add(new individuo(new double[]{0.03184182889671823, 0.0712566535498098, 0.7848675281078807, 0.6079625695776322, 0.6383045770257679, 0.7174020304004736, 0.507002540947466, 0.2612852710965636}));
            tios.add(new individuo(new double[]{0.539370799194244, 0.11495072908590714, 0.5386587788233015, 0.6905063895039896, 0.20462338086671492, 0.8108662848749906, 0.7627616158254455, 0.8470291375259826}));
            tios.add(new individuo(new double[]{0.39969603712889223, 0.012840744258300545, 0.5554363176516031, 0.15606410243810775, 0.7425922056628191, 0.7109472230363102, 0.41218237494267485, 0.5946394142929935}));
            tios.add(new individuo(new double[]{0.5339911187296903, 0.003646706463124119, 0.35658600072154845, 0.48093248816123474, 0.4863878165314131, 0.7160563139637138, 0.6540326750398607, 0.5029913016747484}));
            tios.add(new individuo(new double[]{0.3231075884164314, 0.002926643443022136, 0.19629877077294064, 0.35264435827428475, 0.15780604281427402, 0.7292802744939751, 0.5187265779885067, 0.42632418817490425}));
            tios.add(new individuo(new double[]{0.7572393056975263, 0.0014738042662734833, 0.32554520318916075, 0.65931227385236, 0.14410657209420116, 0.8203378170613118, 0.4181143093931408, 0.285268165683401}));

            //Individuo 89 (Media alta)
            tios.add(new individuo(new double[]{0.7324892228665172, 0.5426871873807844, 0.512732450854807, 0.6595219341981974, 0.18242646925721953, 0.4581101156270648, 0.5005328481116479, 0.836388139132023}));
            tios.add(new individuo(new double[]{0.47524080114902545, 0.09845189464396054, 0.541548976980672, 0.6565901273102095, 0.46481497579322484, 0.5582463603109192, 0.6972828128890618, 0.568501957754497}));
            tios.add(new individuo(new double[]{0.3531122819496763, 0.17569428761772377, 0.2829935152685049, 0.438992427929433, 0.6840845904192429, 0.3620573908775729, 0.18040468884524619, 0.3123511034146843}));
            tios.add(new individuo(new double[]{0.7490755665137564, 0.08359250135372265, 0.7336709637508884, 0.5545695418119171, 0.16016630557909506, 0.8413567597020921, 0.8525752860403087, 0.8653247222701729}));
            tios.add(new individuo(new double[]{0.31740669145824746, 0.276172377339684, 0.9160797823597739, 0.4812043630717402, 0.520672550048631, 0.32999657859635406, 0.4846333353602402, 0.2921973865234736}));
            tios.add(new individuo(new double[]{0.566567915909419, 0.15640096499931078, 0.6124800004555673, 0.3382911305853285, 0.38860813085564094, 0.7875590532811656, 0.5846726007237886, 0.6046276288806289}));
            tios.add(new individuo(new double[]{0.39444388725904217, 0.20539630681880522, 0.4953078079456061, 0.5788298921668698, 0.6341205632015274, 0.6378219193602874, 0.5199957917596909, 0.2750847336685912}));
            tios.add(new individuo(new double[]{0.3130198675590431, 0.20112831655845748, 0.6687326860641559, 0.5415042597401578, 0.23326921524333238, 0.7826911164600651, 0.3569387741554251, 0.6638845945905161}));

            //Individuo 143 (Media baja)
            tios.add(new individuo(new double[]{0.8684779077609888, 0.5013532153967083, 0.500008926685959, 0.6175720152409286, 0.14604109963287304, 0.31385823503005467, 0.5275737533954761, 0.8547601892760897}));
            tios.add(new individuo(new double[]{0.48412239374392924, 0.13721307628709176, 0.6827643553805209, 0.4590453405845547, 0.5418794811974735, 0.5750809928738979, 0.36938327378135805, 0.2917248666812771}));
            tios.add(new individuo(new double[]{0.7089022837026769, 0.31989877868814465, 0.6206594537968501, 0.6571595947149123, 0.08076091760150522, 0.4036441242755474, 0.7538642650878938, 0.36819215130441646}));
            tios.add(new individuo(new double[]{0.28564652676925295, 0.5165044476303243, 0.8758451648844086, 0.5306092955471652, 0.32619112335981076, 0.5732806096878724, 0.6267944205535243, 0.02933422079183155}));
            tios.add(new individuo(new double[]{0.5995271634524153, 0.1703012326660877, 0.6501894201348999, 0.7039660487070981, 0.2882593836367432, 0.7064460126349605, 0.4352312870941228, 0.7917979598003605}));
            tios.add(new individuo(new double[]{0.501681671651604, 0.30235540177496345, 0.37510240602736517, 0.4703236830301975, 0.7784746700351837, 0.6876808688564645, 0.3927120662648277, 0.4078686943792241}));
            tios.add(new individuo(new double[]{0.33400933291265356, 0.23261097958029836, 0.6592985044836778, 0.6232024062744385, 0.15149294719361722, 0.544501073485585, 0.6118795003468965, 0.5065205845983668}));

            //Individuo 200 (Malo malo malo)
            tios.add(new individuo(new double[]{0.7324892228665172, 0.8745810285391838, 0.30822234803884085, 0.5985452253397301, 0.20094231404574736, 0.7723847104068429, 0.3925238789875519, 0.6122344850447125}));
            tios.add(new individuo(new double[]{0.3467119120869925, 0.05918155842254984, 0.10838333289585891, 0.5776481642075753, 0.4458730777112157, 0.9469411082715166, 0.5441396863853952, 0.2721327369867848}));
            tios.add(new individuo(new double[]{0.5437514200203175, 0.0753744896179056, 0.7773800711596388, 0.7041901101351078, 0.13342970390314118, 0.49124861156552235, 0.3916878751724116, 0.7788769370078653}));
            tios.add(new individuo(new double[]{0.19599726349167035, 0.29167611513624725, 0.46955237961976726, 0.26251211126049157, 0.7126860626238284, 0.38807287472852914, 0.624735599732169, 0.4147246722624295}));
            tios.add(new individuo(new double[]{0.6331797746943328, 0.16092968695367355, 0.602886839766939, 0.6536992356403346, 0.19861756338632924, 0.7298373393398572, 0.35498390676726405, 0.8849772329962544}));
            tios.add(new individuo(new double[]{0.36708080845318075, 0.09728557200858856, 0.3996650808077026, 0.34364281993420215, 0.7576588265374099, 0.5187205490316713, 0.5807285662638288, 0.69994952340877}));



            for (int p = 0; p < tios.size(); p++) {
                System.out.print(p + "\n");
                for (int i = 0; i < 100; i++) {
                    System.out.print(".");
                    if (i % 5 == 0) {
                        System.out.print(i);
                    }
                    t_fitness t = new t_fitness(tio, "hebra");
                    t.run();
                    try {
                        t.wait();
                    } catch (Exception e) {
                    }

                    try {

                        outfile = new FileWriter("./logs/genera_p_" + Integer.toString(p) + "_i" + Integer.toString(i) + ".txt");
                        pw = new PrintWriter(outfile);

                        //System.err.println(a.ToString());
                        pw.println(tio.ToString());
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
            }
        } else if (args[0].equals("compareCo")) {
            //individuo coGene = new individuo(new double[]{0.332812769, 0.070841113, 0.591981919, 1, 0.460496021, 0.759319642, 0.49165473, 0.50371368});
            //individuo mejorbot = new individuo(new double[]{0.3919305854764415,0.03027927275131309,0.6908147364455396,0.3559640928082235,0.426402059754704,0.814945973663688,0.5549861375996193,0.21015986425274444});
            //individuo GeneBot = new individuo(new double[]{0.01798830002878981,0.0589919310326945,0.5095445636693665,0.13954369278880815,0.23273726269551936,0.7332158071928045,0.5894601955924742,0.9740553181473641});
            //individuo aresbot =  new individuo(new double[]{0.1,0.5,1,1,1,0.25,0.5,0.9});

            individuo GeneBot = new individuo(new double[]{0.01798830002878981, 0.0589919310326945, 0.5095445636693665, 0.13954369278880815, 0.23273726269551936, 0.7332158071928045, 0.5894601955924742, 0.9740553181473641});
            individuo CoGeneBot_Google = new individuo(new double[]{0.19776689279914292, 0.05397294083079567, 0.4457230194920662, 0.7075704820025892, 0.7228610524755783, 0.1179759455837378, 0.7063829090779397, 0.6342737279767714});
            individuo CoGeneBot_Eje8 = new individuo(new double[]{0.7539149176114447, 0.03721530937862044, 0.8346657983444475, 0.4367253994290777, 0.5528266914888688, 0.10239887664309469, 0.30732853868890686, 0.38378828421763256});


            //En primer lugar: Lucha contra mediabot vs mejorbot
            //




            for (int i = 1; i < 101; i++) {

                System.out.print(".");
                if (i % 10 == 0) {
                    System.out.print("\n");
                }

                FileWriter outfile = null;
                PrintWriter pw = null;


                for (int h = 0; h < 20; h++) {
                    CoGeneBot_Eje8.reset();
                    CoGeneBot_Google.reset();

                    try {

                        t_fitness_versus2 t = new t_fitness_versus2(CoGeneBot_Google,CoGeneBot_Eje8, "compare_genebots"+i+"-"+h+".txt", new sel_mapa(new int[]{i}));
                        t.run();

                        try {
                            t.wait();
                        } catch (Exception e) {
                        }
                        outfile = new FileWriter("./logs/genera_CoGenebotEje8_vs_CoGenebotGoogle_mapa" + i + "-" + h + " .txt");
                        pw = new PrintWriter(outfile);

                        //System.err.println(a.ToString());
                        //pw.println("Google: " + CoGeneBot_Google.turnos + " " + CoGeneBot_Google.puesto + "  \t Gene:" + CoGeneBot_Eje8.turnos + " " + CoGeneBot_Eje8.puesto);
                        pw.println("Google: " + CoGeneBot_Google.turnos + " " + CoGeneBot_Google.puesto);

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (null != outfile) {
                                outfile.close();
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            }
        } else if (args[0].equals("compareBB")) {
            individuo mediabot = new individuo(new double[]{0.332812769, 0.070841113, 0.591981919, 1, 0.460496021, 0.759319642, 0.49165473, 0.50371368});
            individuo mejorbot = new individuo(new double[]{0.3919305854764415, 0.03027927275131309, 0.6908147364455396, 0.3559640928082235, 0.426402059754704, 0.814945973663688, 0.5549861375996193, 0.21015986425274444});
            individuo aresbot = new individuo(new double[]{0.1, 0.5, 1, 1, 1, 0.25, 0.5, 0.9});

            //En primer lugar: Lucha contra mediabot vs mejorbot
            for (int i = 1; i < 101; i++) {
                mediabot.gana = false;
                t_fitness t = new t_fitness(mediabot, aresbot, "mediabot_vs_aresbot", new sel_mapa(new int[]{i}));
                t.run();
                FileWriter outfile = null;
                PrintWriter pw = null;
                try {
                    t.wait();
                } catch (Exception e) {
                }

                try {
                    outfile = new FileWriter("./logs/genera_mediabot_vs_aresbot_mapa" + i + ".txt");
                    pw = new PrintWriter(outfile);

                    //System.err.println(a.ToString());
                    pw.println(mediabot.ToString());
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
        } else if (args[0].equals("fitnessBB")) {
            System.out.println("Probando consistencia del fitness");
            individuo tio = new individuo(new double[]{0.6147066420960032, 0.15712330060731747, 0.7412458555004816, 0.5286335311106324, 0.3192508512842522, 0.5819896634306796, 0.33138383861409937, 0.6651286683482183,});
            FileWriter outfile = null;
            PrintWriter pw = null;

            ArrayList<individuo> C_A = new ArrayList<individuo>();
            ArrayList<individuo> C_B = new ArrayList<individuo>();
            ArrayList<individuo> C_C = new ArrayList<individuo>();
            ArrayList<individuo> C_D = new ArrayList<individuo>();

            //Mejor Individuo de cada ejecución
            C_A.add(new individuo(new double[]{0.5998149691289131, 8.466698720355215E-5, 0.3203014274830107, 0.6348218748464499, 0.6715334379640565, 0.7908027437454676, 0.2624520220002189, 0.6332454944811828}));
            C_A.add(new individuo(new double[]{0.48231655449480576, 0.10649542170626028, 0.5623083960457418, 0.40529883928645455, 0.3515051786780067, 0.6441622892638008, 0.5989578698935684, 0.8251208746187438}));
            C_A.add(new individuo(new double[]{0.3919305854764415, 0.03027927275131309, 0.6908147364455396, 0.3559640928082235, 0.426402059754704, 0.814945973663688, 0.5549861375996193, 0.21015986425274444}));
            C_A.add(new individuo(new double[]{0.034222396220094015, 0.46961880715935866, 0.6618127230706216, 0.616489430991588, 0.07952046257968269, 0.7109535986694575, 0.45054368895575203, 0.47592289801101983}));
            C_A.add(new individuo(new double[]{0.03184182889671823, 0.0712566535498098, 0.7848675281078807, 0.6079625695776322, 0.6383045770257679, 0.7174020304004736, 0.507002540947466, 0.2612852710965636}));
            C_A.add(new individuo(new double[]{0.539370799194244, 0.11495072908590714, 0.5386587788233015, 0.6905063895039896, 0.20462338086671492, 0.8108662848749906, 0.7627616158254455, 0.8470291375259826}));
            C_A.add(new individuo(new double[]{0.39969603712889223, 0.012840744258300545, 0.5554363176516031, 0.15606410243810775, 0.7425922056628191, 0.7109472230363102, 0.41218237494267485, 0.5946394142929935}));
            C_A.add(new individuo(new double[]{0.5339911187296903, 0.003646706463124119, 0.35658600072154845, 0.48093248816123474, 0.4863878165314131, 0.7160563139637138, 0.6540326750398607, 0.5029913016747484}));
            C_A.add(new individuo(new double[]{0.3231075884164314, 0.002926643443022136, 0.19629877077294064, 0.35264435827428475, 0.15780604281427402, 0.7292802744939751, 0.5187265779885067, 0.42632418817490425}));
            C_A.add(new individuo(new double[]{0.7572393056975263, 0.0014738042662734833, 0.32554520318916075, 0.65931227385236, 0.14410657209420116, 0.8203378170613118, 0.4181143093931408, 0.285268165683401}));

            //Individuo 89 (Media alta)
            C_B.add(new individuo(new double[]{0.7324892228665172, 0.5426871873807844, 0.512732450854807, 0.6595219341981974, 0.18242646925721953, 0.4581101156270648, 0.5005328481116479, 0.836388139132023}));
            C_B.add(new individuo(new double[]{0.47524080114902545, 0.09845189464396054, 0.541548976980672, 0.6565901273102095, 0.46481497579322484, 0.5582463603109192, 0.6972828128890618, 0.568501957754497}));
            C_B.add(new individuo(new double[]{0.3531122819496763, 0.17569428761772377, 0.2829935152685049, 0.438992427929433, 0.6840845904192429, 0.3620573908775729, 0.18040468884524619, 0.3123511034146843}));
            C_B.add(new individuo(new double[]{0.7490755665137564, 0.08359250135372265, 0.7336709637508884, 0.5545695418119171, 0.16016630557909506, 0.8413567597020921, 0.8525752860403087, 0.8653247222701729}));
            C_B.add(new individuo(new double[]{0.31740669145824746, 0.276172377339684, 0.9160797823597739, 0.4812043630717402, 0.520672550048631, 0.32999657859635406, 0.4846333353602402, 0.2921973865234736}));
            C_B.add(new individuo(new double[]{0.566567915909419, 0.15640096499931078, 0.6124800004555673, 0.3382911305853285, 0.38860813085564094, 0.7875590532811656, 0.5846726007237886, 0.6046276288806289}));
            C_B.add(new individuo(new double[]{0.39444388725904217, 0.20539630681880522, 0.4953078079456061, 0.5788298921668698, 0.6341205632015274, 0.6378219193602874, 0.5199957917596909, 0.2750847336685912}));
            C_B.add(new individuo(new double[]{0.3130198675590431, 0.20112831655845748, 0.6687326860641559, 0.5415042597401578, 0.23326921524333238, 0.7826911164600651, 0.3569387741554251, 0.6638845945905161}));
            C_B.add(new individuo(new double[]{0.7324892228665172, 0.31022621889802193, 0.6872136034787744, 0.6394621575957203, 0.20094231404574736, 0.3650896691204155, 0.6092067237743678, 0.8261299740071284}));
            C_B.add(new individuo(new double[]{0.39456796918373765, 0.07311319933606417, 0.44743726121733396, 0.8483587386775233, 0.5836180316599388, 0.6031468048157245, 0.31430509667183515, 0.37543379333695126}));

            //Individuo 143 (Media baja)
            C_C.add(new individuo(new double[]{0.8684779077609888, 0.5013532153967083, 0.500008926685959, 0.6175720152409286, 0.14604109963287304, 0.31385823503005467, 0.5275737533954761, 0.8547601892760897}));
            C_C.add(new individuo(new double[]{0.48412239374392924, 0.13721307628709176, 0.6827643553805209, 0.4590453405845547, 0.5418794811974735, 0.5750809928738979, 0.36938327378135805, 0.2917248666812771}));
            C_C.add(new individuo(new double[]{0.7089022837026769, 0.31989877868814465, 0.6206594537968501, 0.6571595947149123, 0.08076091760150522, 0.4036441242755474, 0.7538642650878938, 0.36819215130441646}));
            C_C.add(new individuo(new double[]{0.28564652676925295, 0.5165044476303243, 0.8758451648844086, 0.5306092955471652, 0.32619112335981076, 0.5732806096878724, 0.6267944205535243, 0.02933422079183155}));
            C_C.add(new individuo(new double[]{0.5995271634524153, 0.1703012326660877, 0.6501894201348999, 0.7039660487070981, 0.2882593836367432, 0.7064460126349605, 0.4352312870941228, 0.7917979598003605}));
            C_C.add(new individuo(new double[]{0.501681671651604, 0.30235540177496345, 0.37510240602736517, 0.4703236830301975, 0.7784746700351837, 0.6876808688564645, 0.3927120662648277, 0.4078686943792241}));
            C_C.add(new individuo(new double[]{0.33400933291265356, 0.23261097958029836, 0.6592985044836778, 0.6232024062744385, 0.15149294719361722, 0.544501073485585, 0.6118795003468965, 0.5065205845983668}));
            C_C.add(new individuo(new double[]{0.7280370014986205, 0.10078268409597733, 0.5718304510472951, 0.6634005632639571, 0.16131518740962386, 0.7786931168112903, 0.673951591535452, 0.2730657575603918}));
            C_C.add(new individuo(new double[]{0.4045823561194814, 0.2660186624222213, 0.508447845401625, 0.591842399396066, 0.4496808036881539, 0.15477538825581813, 0.6142130599248721, 0.29913154737325637}));
            C_C.add(new individuo(new double[]{0.7324892228665172, 0.840378557978019, 0.4221918712377738, 0.43479508284805823, 0.22153265267523303, 0.447382404752803, 0.4923918669948824, 0.7393156859172236}));


            //Individuo 200 (Malo malo malo)
            C_D.add(new individuo(new double[]{0.7324892228665172, 0.8745810285391838, 0.30822234803884085, 0.5985452253397301, 0.20094231404574736, 0.7723847104068429, 0.3925238789875519, 0.6122344850447125}));
            C_D.add(new individuo(new double[]{0.1994972883293742, 0.05430030210304214, 0.02204266203291965, 0.7811463579917417, 0.8607407562033462, 0.9469411082715166, 0.42980084300712085, 0.48854686707635786}));
            C_D.add(new individuo(new double[]{0.5437514200203175, 0.0753744896179056, 0.7773800711596388, 0.7041901101351078, 0.13342970390314118, 0.49124861156552235, 0.3916878751724116, 0.7788769370078653}));
            C_D.add(new individuo(new double[]{0.19599726349167035, 0.29167611513624725, 0.46955237961976726, 0.26251211126049157, 0.7126860626238284, 0.38807287472852914, 0.624735599732169, 0.4147246722624295}));
            C_D.add(new individuo(new double[]{0.6331797746943328, 0.16092968695367355, 0.602886839766939, 0.6536992356403346, 0.19861756338632924, 0.7298373393398572, 0.35498390676726405, 0.8849772329962544}));
            C_D.add(new individuo(new double[]{0.36708080845318075, 0.09728557200858856, 0.3996650808077026, 0.34364281993420215, 0.7576588265374099, 0.5187205490316713, 0.5807285662638288, 0.69994952340877}));
            C_D.add(new individuo(new double[]{0.22076916706254127, 0.23388545851361364, 0.5932494561878887, 0.5093061788095452, 0.2244590718468053, 0.632971715034712, 0.6748549908766053, 0.3490916753617031}));
            C_D.add(new individuo(new double[]{0.2826871608133976, 0.30819091508323804, 0.5502674290518285, 0.7782462590969071, 0.352229861565255, 0.3895703715930478, 0.6453037176207251, 0.24485814396164807}));
            C_D.add(new individuo(new double[]{0.06293993677915219, 0.37282744855226074, 0.8732089504708723, 0.4389017890951966, 0.4802755008719036, 0.39141701328880585, 0.6325105121526801, 0.031766911009969}));
            C_D.add(new individuo(new double[]{0.3918000867106085, 0.1332923799737444, 0.08228721505088461, 0.8226629461582664, 0.746320968709771, 0.9189011064591674, 0.4849034082172189, 0.49026529607860525}));


            //consistencia_fitness(C_A,C_A,"AvA");
            System.out.print("\n\n----------------------------------------\n\n");
            // consistencia_fitness(C_A, C_B, "AvB");
            System.out.print("\n\n----------------------------------------\n\n");
            // consistencia_fitness(C_A, C_C, "AvC");
            System.out.print("\n\n----------------------------------------\n\n");
            consistencia_fitness(C_A, C_D, "AvD");
            System.out.print("\n\n----------------------------------------\n\n");

            consistencia_fitness(C_B, C_A, "BvA");
            System.out.print("\n\n----------------------------------------\n\n");
            consistencia_fitness(C_B, C_B, "BvB");
            System.out.print("\n\n----------------------------------------\n\n");
            consistencia_fitness(C_B, C_C, "BvC");
            System.out.print("\n\n----------------------------------------\n\n");
            consistencia_fitness(C_B, C_D, "BvD");
            System.out.print("\n\n----------------------------------------\n\n");

            consistencia_fitness(C_C, C_A, "CvA");
            System.out.print("\n\n----------------------------------------\n\n");
            consistencia_fitness(C_C, C_B, "CvB");
            System.out.print("\n\n----------------------------------------\n\n");
            consistencia_fitness(C_C, C_C, "CvC");
            System.out.print("\n\n----------------------------------------\n\n");
            consistencia_fitness(C_C, C_D, "DvD");
            System.out.print("\n\n----------------------------------------\n\n");

            consistencia_fitness(C_D, C_A, "DvA");
            System.out.print("\n\n----------------------------------------\n\n");
            consistencia_fitness(C_D, C_B, "DvB");
            System.out.print("\n\n----------------------------------------\n\n");
            consistencia_fitness(C_D, C_C, "DvC");
            System.out.print("\n\n----------------------------------------\n\n");
            consistencia_fitness(C_D, C_D, "DvD");
            System.out.print("\n\n----------------------------------------\n\n");





        } else if (args[0].equals("15eje")) {
            ArrayList<individuo> listado = new ArrayList<individuo>();
            listado.add(new individuo(new double[]{0.42866617349878516, 0.020996349960960912, 0.5951569661039416, 0.6135336512690417, 0.40440963230191695, 0.6093753586853742, 0.3742897568608773, 0.6818529923565275}));
            listado.add(new individuo(new double[]{0.023031400282765718, 0.288583879303044, 0.7813751517804515, 0.46473260901051044, 0.36669663203870606, 0.7900392616452466, 0.2300694619228707, 0.34710000376167904}));
            listado.add(new individuo(new double[]{0.007751828879710811, 0.4004256871225212, 0.3040806691061873, 0.5715829119547758, 0.48882347986611524, 0.7531843382468174, 0.17879672907929006, 0.4135356841476962}));
            listado.add(new individuo(new double[]{0.00865643416174294, 0.13013017516184602, 0.5942214326636988, 0.4606014377471249, 0.4291740228879596, 0.7313944209624611, 0.7437535147295122, 0.4801369984054713}));
            listado.add(new individuo(new double[]{0.15873361840070377, 0.19245540672612174, 0.5272372731688297, 0.7574950105654106, 0.35912020144138984, 0.2288179671216604, 0.923966384059881, 0.6269960155327602}));
            listado.add(new individuo(new double[]{0.15873361840070377, 0.19245540672612174, 0.5272372731688297, 0.7574950105654106, 0.35912020144138984, 0.2288179671216604, 0.923966384059881, 0.6269960155327602}));
            listado.add(new individuo(new double[]{0.4827950276485812, 0.05327803304277653, 0.05423813065856037, 0.1962467989575315, 0.18804641377935696, 0.7122540096707128, 0.08645176336491368, 0.5868439567098888}));
            listado.add(new individuo(new double[]{0.01798830002878981, 0.0589919310326945, 0.5095445636693665, 0.13954369278880815, 0.23273726269551936, 0.7332158071928045, 0.5894601955924742, 0.9740553181473641}));
            listado.add(new individuo(new double[]{0.49953971129076213, 0.2455070545305153, 0.5512142832806787, 0.6118190061522651, 0.2689422957379329, 0.7111120183561702, 0.7632524872112635, 0.8835856889355379}));
            listado.add(new individuo(new double[]{0.09978267816749446, 0.23032668116070584, 0.06446668711729682, 0.3145662812995889, 0.6783974914572055, 0.7306356321118216, 0.6645303530434207, 0.7107283003587886}));
            listado.add(new individuo(new double[]{0.08317899775001202, 0.269328559584938, 0.4519700602812491, 0.5126615605318623, 0.4497052379606489, 0.7193258268820903, 0.42479491894001076, 0.42518150038686914}));
            listado.add(new individuo(new double[]{0.40614755621904564, 0.008947907619556822, 0.459490372961674, 0.133516444428475, 0.09933458974225966, 0.6902334978297044, 0.6125091436443066, 0.4013289257589658}));
            listado.add(new individuo(new double[]{0.0027405859071194458, 0.399694097996913, 0.531066490957737, 0.5252463108459591, 0.44151805029313607, 0.7665458292633865, 0.31765908013562244, 0.7241157602163053}));
            listado.add(new individuo(new double[]{0.1952137327257209, 0.34166639022401857, 0.30853399836490764, 0.6524125820198226, 0.04900070130282645, 0.6846209417530463, 0.5674632927285976, 0.5595531263396434}));
            listado.add(new individuo(new double[]{0.034907932776653834, 0.11822246346561921, 0.8280008862519138, 0.6861023706620188, 0.6463652786783257, 0.7702612647882575, 0.4674416688335924, 0.5560812601595692}));

            consistencia_fitness(listado, listado, "Listado");
            System.out.print("\n\n----------------------------------------\n\n");

        } else if (args[0].equals("VS")) {
            Genetic_BOTvsBOT VS = new Genetic_BOTvsBOT(args);
            VS.main();
        } else if (args[0].equals("ExVSGe")) {
            Genetic_BOTvsBOT VS = new Genetic_BOTvsBOT(args);
            VS.main();

        } else if (args[0].equals("ruidoso")) {
            System.out.println("Probando consistencia del fitness");
            individuo tio = new individuo(new double[]{0.6147066420960032, 0.15712330060731747, 0.7412458555004816, 0.5286335311106324, 0.3192508512842522, 0.5819896634306796, 0.33138383861409937, 0.6651286683482183,});
            FileWriter outfile = null;
            PrintWriter pw = null;

            ArrayList<individuo> C_A = new ArrayList<individuo>();
            ArrayList<individuo> C_B = new ArrayList<individuo>();
            ArrayList<individuo> C_C = new ArrayList<individuo>();
            ArrayList<individuo> C_D = new ArrayList<individuo>();

            //Mejor Individuo de cada ejecución JCST
            C_A.add(new individuo(new double[]{0.5998149691289131, 8.466698720355215E-5, 0.3203014274830107, 0.6348218748464499, 0.6715334379640565, 0.7908027437454676, 0.2624520220002189, 0.6332454944811828}));
            C_A.add(new individuo(new double[]{0.48231655449480576, 0.10649542170626028, 0.5623083960457418, 0.40529883928645455, 0.3515051786780067, 0.6441622892638008, 0.5989578698935684, 0.8251208746187438}));
            C_A.add(new individuo(new double[]{0.3919305854764415, 0.03027927275131309, 0.6908147364455396, 0.3559640928082235, 0.426402059754704, 0.814945973663688, 0.5549861375996193, 0.21015986425274444}));
            C_A.add(new individuo(new double[]{0.034222396220094015, 0.46961880715935866, 0.6618127230706216, 0.616489430991588, 0.07952046257968269, 0.7109535986694575, 0.45054368895575203, 0.47592289801101983}));
            C_A.add(new individuo(new double[]{0.03184182889671823, 0.0712566535498098, 0.7848675281078807, 0.6079625695776322, 0.6383045770257679, 0.7174020304004736, 0.507002540947466, 0.2612852710965636}));
            C_A.add(new individuo(new double[]{0.539370799194244, 0.11495072908590714, 0.5386587788233015, 0.6905063895039896, 0.20462338086671492, 0.8108662848749906, 0.7627616158254455, 0.8470291375259826}));
            C_A.add(new individuo(new double[]{0.39969603712889223, 0.012840744258300545, 0.5554363176516031, 0.15606410243810775, 0.7425922056628191, 0.7109472230363102, 0.41218237494267485, 0.5946394142929935}));
            C_A.add(new individuo(new double[]{0.5339911187296903, 0.003646706463124119, 0.35658600072154845, 0.48093248816123474, 0.4863878165314131, 0.7160563139637138, 0.6540326750398607, 0.5029913016747484}));
            C_A.add(new individuo(new double[]{0.3231075884164314, 0.002926643443022136, 0.19629877077294064, 0.35264435827428475, 0.15780604281427402, 0.7292802744939751, 0.5187265779885067, 0.42632418817490425}));
            C_A.add(new individuo(new double[]{0.7572393056975263, 0.0014738042662734833, 0.32554520318916075, 0.65931227385236, 0.14410657209420116, 0.8203378170613118, 0.4181143093931408, 0.285268165683401}));

            //Mejores individuos sin contemplación del fitness ruidoso
            C_B.add(new individuo(new double[]{0.7287237670101581, 0.00019764683908036714, 0.019635615113085012, 0.7791485042845068, 0.5797580400949608, 0.7988137328303923, 0.006428877664570334, 0.6378173740289792}));
            C_B.add(new individuo(new double[]{0.4392411248220655, 0.009700786439660719, 0.7173465584960896, 0.3766289581595217, 0.47510839945112254, 0.5093313198748592, 0.538802771314292, 0.5146092161239835}));
            C_B.add(new individuo(new double[]{0.590258109778764, 0.017623904653372702, 0.1407411303671977, 0.26118148028126564, 0.7001448298190506, 0.5100181419453033, 0.6306930992927638, 0.9641739558100707}));
            C_B.add(new individuo(new double[]{0.5896735067649828, 0.016278635136734824, 0.7843099264985853, 0.5714572804787234, 0.2931756849072513, 0.5489039773303381, 0.594452053612526, 0.3414520057774871}));
            C_B.add(new individuo(new double[]{0.6580234689157458, 0.029256312791566985, 0.3246612862763807, 0.3982090192664454, 0.2853206242795603, 0.43604714729957883, 0.5718072584656735, 0.6652366759068766}));
            C_B.add(new individuo(new double[]{0.06743848950790526, 0.22271758315801388, 0.2970919108614899, 0.7184600044754701, 0.14358837217024217, 0.075086478218511, 0.6244742862503351, 0.5312470595337491}));
            C_B.add(new individuo(new double[]{0.8782301195822971, 0.00005357791690796532, 0.968385171313883, 0.7059869097801371, 0.6526965818739529, 0.7712581719984003, 0.421538852623482, 0.5612113460627509}));
            C_B.add(new individuo(new double[]{0.3965370830360585, 0.11570191575266671, 0.6788116526801333, 0.3240448412843576, 0.39173166052295194, 0.44160229400404605, 0.536093565647586, 0.8633944852910742}));
            C_B.add(new individuo(new double[]{0.007743431900899348, 0.3991666644849571, 0.8446960237495768, 0.7116351566003961, 0.6575476772335784, 0.7830706322117771, 0.5974767816383124, 0.8332572731916884}));
            C_B.add(new individuo(new double[]{0.005797757592918895, 0.4649553466655785, 0.35787244349091973, 0.4943102848987584, 0.3291192345627275, 0.7839700282476851, 0.36453560241694316, 0.5677095749261043}));

            consistencia_fitness(C_A, C_B, "AvB");
            System.out.print("\n\n----------------------------------------\n\n");

            consistencia_fitness(C_B, C_B, "BvB");
            System.out.print("\n\n----------------------------------------\n\n");


//En primer lugar: Lucha contra GOOGLEBOT
            for (int b = 1; b < C_B.size(); b++) {
                System.out.print(b + "->m: ");
                for (int i = 0; i < 100; i++) {
                    System.out.print(".");
                    if (i % 5 == 0) {
                        System.out.print(i);
                    }
                    t_fitness t = new t_fitness(C_B.get(b), "hebra", new sel_mapa(new int[]{i}));
                    t.run();
                    try {
                        t.wait();
                    } catch (Exception e) {
                    }

                    try {

                        outfile = new FileWriter("./logs/genera_p_" + Integer.toString(b) + 1 + "_i" + Integer.toString(i) + ".txt");
                        pw = new PrintWriter(outfile);

                        //System.err.println(a.ToString());
                        pw.println(tio.ToString());
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

            }
        }
    }
}
