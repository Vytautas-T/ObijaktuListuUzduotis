import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner skaitytuvas = new Scanner(System.in);
        ArrayList<Preke> prekes = nuskaitoPrekesIsFailo("prekes.csv");

        isvestiListaAtskiraiEilutemis(prekes);
        isvedaBruksniukus();

        ArrayList<Preke> esanciosPrekes = rastiPrekesSandelyje(prekes);
        isvestiListaAtskiraiEilutemis(esanciosPrekes);

        isvedaBruksniukus();
        System.out.println("prekiuVidurkis(prekes) = " + prekiuVidurkis(prekes));

        isvedaBruksniukus();
        System.out.println("prekiu kiekis sandelyja " + prekiuKiekis(prekes));

        isvedaBruksniukus();
        System.out.println("didziause prekes kaina " + brangiausiosPrekesKaina(prekes));

        isvedaBruksniukus();
        nuolaiduPritaikymasKainomsDidesniomsUz600(prekes);

        isvedaBruksniukus();
    }

    public static void isvedaBruksniukus() {
        System.out.println("--------------------------------------------");
    }


    public static ArrayList<Preke> nuskaitoPrekesIsFailo(String failoPavadinimas) {
        ArrayList<Preke> prekes = new ArrayList<>();
        try {
            File failoObjektas = new File(failoPavadinimas);
            Scanner skaitytuvas = new Scanner(failoObjektas);
            skaitytuvas.nextLine();         // pirmą eilutę praleidžiu, nes ten ne duomenys, o tiesiog paaiškinimai stulpelių
            while (skaitytuvas.hasNextLine()) {
                String eilute = skaitytuvas.nextLine();
                String[] stulpeliai = eilute.split(",");
                Preke laikinaPreke = new Preke(Integer.parseInt(stulpeliai[0]), stulpeliai[1], stulpeliai[2], Double.parseDouble(stulpeliai[3]), Integer.parseInt(stulpeliai[4]), stulpeliai[5], stulpeliai[6]);
                prekes.add(laikinaPreke);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Nėra tokio failo.");
        }
        return prekes;
    }

    public static void isvestiListaAtskiraiEilutemis(ArrayList<Preke> prekes) {
        for (Preke preke : prekes) {
            System.out.println("preke = " + preke);
        }
    }


    public static ArrayList<Preke> rastiPrekesSandelyje(ArrayList<Preke> visosPrekes) {
        ArrayList<Preke> esanciosPrekes = new ArrayList<>();
        for (Preke preke : visosPrekes) {
            if (preke.arYraSandelyje()) {
                esanciosPrekes.add(preke);
            }
        }
        return esanciosPrekes;
    }


    public static double prekiuSuma(List<Preke> prekes) {
        double suma = 0;
        for (Preke preke : prekes) {
            suma += preke.getKaina();
        }
        return suma;
    }

    public static double prekiuVidurkis(List<Preke> prekes) {
        return prekiuSuma(prekes) / prekes.size();
    }

    public static double prekiuKiekis(List<Preke> prekes) {
        double kiekis = 0;
        for (Preke preke : prekes) {
            kiekis += preke.kiekYraSandelyje();
        }
        return kiekis;
    }

    public static Preke brangiausiosPrekesKaina(List<Preke> prekes) {
        Preke preke2 = null;
        for (Preke preke : prekes) {
            if (preke2 == null)
                preke2 = preke;
            if (preke2.getKaina() < preke.getKaina()) {
                preke2 = preke;
            }

        }
        return preke2;
        }

    public static double pritaikytiNuolaida(double kaina, int procentas){
       double nuolaida = (kaina / 100) * procentas;
       kaina = kaina - nuolaida;
        return kaina;
    }

    public static void nuolaiduPritaikymasKainomsDidesniomsUz600(ArrayList<Preke> prekes){
        double kaina = 0;
        int nuolaida = 10;
        ArrayList atrinkTosPrekes = new ArrayList<>();
        for (Preke preke : prekes){
            if (preke.getKaina() > 600){
                kaina = preke.getKaina();
                kaina = pritaikytiNuolaida(kaina,nuolaida);
                preke.setKaina(kaina);
            }
        }
        isvestiListaAtskiraiEilutemis(prekes);
    }
}



