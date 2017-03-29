package com.supinfo.wherescage.utils;

/**
 * Created by Djero on 25.03.2017.
 */

/* modeDuJeu
    1 = Timer, Temps debut de 0, compte le temps passé
    2 = ChronoMode 2, Compte de 300 secondes à 0
    3 = ChronoMode 1, Compte de 120 secondes a 0, ensuite de 100...
 */

public class Compteur{
//    int mode;

    /*public Compteur (int modeDuJeu)
    {
        mode = modeDuJeu;
    }*/


    long tempsDebut;

    //boolean countdownEnded = false;
    boolean gameHasEnded = false;

    public void startCompteur ()
    {
        tempsDebut = System.currentTimeMillis(); // Temps actuel en millisecondes
        long tempsInitial = 120000;

    }

    public long getTempsPasseEnMs () //pour le mode 1, le Temps qui est passe depuis l'appel de la fonction startCompteur
    {
        long timePassed = System.currentTimeMillis() - tempsDebut;
        return timePassed;
    }

    public String getTempsPasseEnMnSsMs () //pour le mode 1, le Temps qui a passé depuis l'appel de la fonction startCompteur en Format MN:SS:MS
    {
        long timePassed = System.currentTimeMillis() - tempsDebut;
        return convertMsToStringMnSsMs(timePassed);
    }

    public long getTempsPasse300MoinsEnMs()//pour le mode 2, le Temps qui reste de 300 secondes en total
    {
        long tempsRestant;
        long DELAIS = 300000; // Temps en totale pour trouver Cage 300 secondes

        tempsRestant = DELAIS - (System.currentTimeMillis()-tempsDebut);
                if (tempsRestant <= 0)
                {
                    gameHasEnded = true;
                }
        return tempsRestant;
    }

    public String getTempsPasse300MoinsEnMnSsMs()//pour le mode 2, le Temps qui reste de 300 secondes en Format MN:SS:MS
    {
        long tempsRestant;
        long DELAIS = 300000; // Temps en totale pour trouver Cage 300 secondes

        tempsRestant = DELAIS - (System.currentTimeMillis()-tempsDebut);
        if (tempsRestant <= 0)
        {
            gameHasEnded = true;
        }
        return convertMsToStringMnSsMs(tempsRestant);
    }

    public long getTempsRestantParNiveauEnMs (int Niveau)
    {
        long tempsInitial = 120000 - Niveau*20000;
        long tempsRestant = tempsInitial - (System.currentTimeMillis()-tempsDebut);
        return tempsRestant;
    }

    public String getTempsRestantParNiveauEnMnSsMs (int Niveau)
    {
        long tempsInitial = 120000 - Niveau*20000;
        long tempsRestant = tempsInitial - (System.currentTimeMillis()-tempsDebut);
        return convertMsToStringMnSsMs(tempsRestant);
    }

    public static String convertMsToStringMnSsMs (long milliseconds)
    {
        long ms = milliseconds % 1000;
        long ss = (milliseconds / 1000) % 60;
        long mn = (milliseconds / (1000 * 60)) % 60;
        return String.format("%d:%02d:%04d", mn,ss,ms);
    }

    public boolean isGameHasEnded() {
        return gameHasEnded;
    }


}
