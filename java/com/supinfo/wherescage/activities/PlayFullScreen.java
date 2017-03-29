package com.supinfo.wherescage.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.supinfo.wherescage.R;
import com.supinfo.wherescage.utils.Compteur;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PlayFullScreen extends AppCompatActivity {

    // Déclaration

    int MODE_JEU; // Le mode de jeu :
                  //    -   1 : Normal (Une seule tete de nico, T chronometré mec !)
                  //    -   2 : CountDown (Combien de tete trouveras tu avec un temps imparti sur un laps de temps alors que celui-çi est degressif)
                  //    -   3 : CountDown (Combien de tete trouveras tu dans un laps de temps ?)

    int nbNicoFound; // Say pour compter le nombre de tete qu'on trouve

    // Ben j'ai galleré a mettre ça sous forme de Res xml... Alors le voila ici :')
    static String[][] tabResImg = { // x,y,tailleTete,id Imgae
                                    {"620","640","30",""+R.drawable.nico1},
                                    {"1160","310","40",""+R.drawable.nico2},
                                    {"805","640","40",""+R.drawable.nico3},
                                    {"455","160","110",""+R.drawable.nico4},
                                    {"860","455","80",""+R.drawable.nico5},
                                    {"850","280","40",""+R.drawable.nico6},
                                    {"1830","450","70",""+R.drawable.nico7}
                                  };

    List<Integer> imageDejaUse; // Une list pour mémoriser lesimage déja réalisé au cour de la partie

    float xInit;        // Position initiale du Touch sur X (Le doight sur l'écran)
    float yInit;        //Position initiale du Touch sur Y  (Le doight sur l'écran)

    // Ben se sont des déclaration de MotionEvent.PointerCoords... C pour jouer avec les coordonnées lorsqu'on pinch ou "schrusht" les doights sur le screen
    MotionEvent.PointerCoords pCo1Init = new MotionEvent.PointerCoords();
    MotionEvent.PointerCoords pCo2Init = new MotionEvent.PointerCoords();
    MotionEvent.PointerCoords pCo1 = new MotionEvent.PointerCoords();
    MotionEvent.PointerCoords pCo2 = new MotionEvent.PointerCoords();

    float delta; // longueur entre les 2 doights actuel
    float deltaInit; // Longueur entre les 2 doights au premier contact
    boolean isFirstTime = true; // Hey it's your first time ?  Savoir pour les 2 doigts, premier contact
    boolean oldIsPositif = true; // Permet de detecter le changement de mouvement pinch à "écarté"

    Long timeInitial; // Recupere le temps au moment du Touch, pour savoir si le gars reste appuyé longt
    Long timeFinal;  // Recupere le temps a la fin du Touch, pour savoir si le gars est resté appuyé longt

    FrameLayout fL; // Le frameLayout le plus haut de cette aKeTiVitI
    FrameLayout cacheLayout; // Masque Troll
    Button butPause; // Pour les lopettes
    FrameLayout but; // Tete de Nico
    ImageView iVNico; // La vue de tout Nico

    Compteur compt; // Le "Boss"
    TextView chrono;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_full_screen);

        // Initialisation
        nbNicoFound = 0;
        intent = new Intent(PlayFullScreen.this, GameOver.class);

        // Pour le mode de jeu choisit-
        Intent intentRecut = getIntent();
        MODE_JEU = intentRecut.getIntExtra("MODE_JEU",0);
        Log.d("mode","Mode de jeu = "+MODE_JEU); // A désactiver pour le googleplaystore


        imageDejaUse = new ArrayList<Integer>();

        // Recuperation des views
        iVNico = (ImageView) findViewById(R.id.iv_image);
        fL = (FrameLayout) findViewById(R.id.fl);
        but = (FrameLayout) findViewById(R.id.clikeable1);
        cacheLayout = (FrameLayout) findViewById(R.id.cache);
        butPause = (Button) findViewById(R.id.boutonPause);

        // Chrono
        chrono = (TextView) findViewById(R.id.chrono);
        compt = new Compteur(); // Le boss instanciation

        // Ecouteur sur Bouton Pause au clique
        butPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause(); // Pause...
            }
        });

        // Pioche une image
        chargeImageAvecControl();

        // Met maintenant un ecouteur sur la surface
        fL.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Si pluisieur doight
                if (event.getPointerCount() > 1) {
                    Log.d("zoom", "Multitouch event"); // A dez.
                    boolean isZoom; // Flag si true alors zoom

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_POINTER_DOWN:
                            // Semble ne jamais entrer là dedans dans cette case
                            event.getPointerCoords(0, pCo1Init);
                            event.getPointerCoords(1, pCo2Init);
                            break;
                        case MotionEvent.ACTION_MOVE:

                            if (isFirstTime) { // Dectection de la premiere invocation onTouch
                                // Initialisation
                                Log.d("zoom", "FIRSTTIME !!!!!!");
                                event.getPointerCoords(0, pCo1Init);
                                event.getPointerCoords(1, pCo2Init);
                                deltaInit = (float) Math.sqrt(Math.pow((pCo2Init.x - pCo1Init.x), 2) + Math.pow((pCo2Init.y - pCo1Init.y), 2));
                                isFirstTime = false;
                            }

                            // Récuperation coordonnées
                            event.getPointerCoords(0, pCo1);
                            event.getPointerCoords(1, pCo2);

                            // Calcul Longueur actuel
                            delta = (float) Math.sqrt(Math.pow((pCo2.x - pCo1.x), 2) + Math.pow((pCo2.y - pCo1.y), 2));

                            // Détermination zoom dézoom
                            if ((deltaInit - delta) > 0) {
                                isZoom = true; //zoom
                            } else {
                                isZoom = false; //dézoom
                            }
                            // preset
                            if (isFirstTime) {
                                oldIsPositif = isZoom;
                            }

                            if (oldIsPositif != isZoom) { // Detecte le changement zoom dézoom
                                Log.d("zoom", "CANGELENT !!!!!!");
                                isFirstTime = true;
                                oldIsPositif = isZoom;
                            }

                            // Traitement
                            float x = (Math.abs(deltaInit - delta)) / deltaInit; // rapport du delta du premier placement des doights et de mtn par rapport au premier ecart
                            float scale; // une petite declaration pour noter l'echelle


                            // Méthode de travail
                            if (isZoom) {
                                scale = fL.getScaleX() - x*3;
                            } else {
                                scale = fL.getScaleX() + x*3;
                            }

                            // Control débordement
                            if (scale > 10) {
                                scale = 10;
                            }
                            if (scale <= 1) {
                                scale = 1;
                            }

                            // Application
                            fL.setScaleX(scale);
                            fL.setScaleY(scale);

                            break; // KitKat
                        case MotionEvent.ACTION_POINTER_UP:
                            isFirstTime = true;
                            break;
                        default:
                            break;
                    }
                } else {
                    // Single touch event
                    Log.d("zoom", "Single touch event"); // A Dez
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:

                            // Initialisation
                            Log.d("zoom", "DOWN"); // A Dez

                            // Position du doight
                            xInit = (int) event.getX();
                            yInit = (int) event.getY();

                            // Quand ?
                            timeInitial = System.currentTimeMillis();
                            break;
                        case MotionEvent.ACTION_MOVE:

                            // Recuperation position "curseur"
                            int x_cord = (int) event.getX();
                            int y_cord = (int) event.getY();

                            // Translation
                            fL.setTranslationX(fL.getTranslationX() - (xInit - x_cord));
                            fL.setTranslationY(fL.getTranslationY() - (yInit - y_cord));
                            break;
                        case MotionEvent.ACTION_UP:
                            timeFinal = System.currentTimeMillis();
                            // Determination simple clique Si moins de 200 ms alors faute
                            if (timeFinal - timeInitial < 200) {
                                Log.d("zoom", "Considerer comme mauvais clique !");
                                //MALUS !!!
                                try {
                                    Log.d("z","ATTEND !!!!!!!");
                                    punition();
                                    Log.d("z","C bon !!!!");
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
                return true;
            }
        });
        compt.startCompteur();
        startChrono();
    }

    public void startChrono(){
        Timer timer = new Timer();
        final boolean[] enArret = {false};
        switch (MODE_JEU){
            case 1:
                timer.schedule(new TimerTask(){

                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                chrono.setText(compt.getTempsPasseEnMnSsMs());

                            }
                        });
                    }
                },10,10);
                break;
            case 2:

                    timer.schedule(new TimerTask() {

                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    chrono.setText(compt.getTempsRestantParNiveauEnMnSsMs(nbNicoFound));
                                    if(compt.getTempsRestantParNiveauEnMs(nbNicoFound)<=0){

                                        if(!enArret[0]){
                                            Intent intent = new Intent(PlayFullScreen.this, GameOver.class);
                                            intent.putExtra("MODE_JEU", MODE_JEU);
                                            intent.putExtra("nbNicoFind", nbNicoFound);
                                            intent.putExtra("timeLong", compt.getTempsPasseEnMs());
                                            intent.putExtra("timeString", compt.getTempsPasseEnMnSsMs());
                                            Log.d("res","nbNico au GAMEOVER :"+nbNicoFound);
                                            startActivity(intent);
                                            enArret[0] =true;
                                        }
                                    }
                                }
                            });
                        }
                    },10, 10 );
                break;
            case 3:
                timer.schedule(new TimerTask(){
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                chrono.setText(compt.getTempsPasse300MoinsEnMnSsMs());
                                if(compt.getTempsPasse300MoinsEnMs()<=0){
                                    if(!enArret[0]){
                                        Intent intent = new Intent(PlayFullScreen.this, GameOver.class);
                                        intent.putExtra("MODE_JEU", MODE_JEU);
                                        intent.putExtra("nbNicoFind", nbNicoFound);
                                        intent.putExtra("timeLong", compt.getTempsPasseEnMs());
                                        intent.putExtra("timeString", compt.getTempsPasseEnMnSsMs());
                                        startActivity(intent);
                                        Log.d("res","nbNico au GAMEOVER :"+nbNicoFound);
                                        enArret[0] =true;
                                    }
                                }
                            }
                        });
                    }
                },10,10);
                break;
            default:
                break;
        }
    }

    // Ho oui la punition !
    public void punition() throws InterruptedException {
        Timer timer = new Timer();
        cacheLayout.setVisibility(View.VISIBLE); // On cache l'image
        timer.schedule(new TimerTask(){
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cacheLayout.setVisibility(View.INVISIBLE); // On décache l'image
                    }
                });
            };
        },2000); // On attend 2 sc
    }


    public void clickOnNico(View view) {
        Log.d("zoom", "Clique sur le BON button !!!");
        Intent intent = new Intent(PlayFullScreen.this, GameOver.class);
        switch (MODE_JEU){
            case 1:// Normal
                intent.putExtra("MODE_JEU", MODE_JEU);
                intent.putExtra("timeLong", compt.getTempsPasseEnMs());
                intent.putExtra("timeString", compt.getTempsPasseEnMnSsMs());
                startActivity(intent);
                break;
            case 2://Chrono
                    nbNicoFound++;
                    Log.d("res","nbNico au clique :"+nbNicoFound);
                    compt.startCompteur();
                    chargeImageAvecControl();
                break;
            case 3://ChronoMod2
                    nbNicoFound++;
                    chargeImageAvecControl();
                break;
        }
    }


    public void chargeImageAvecControl(){
        boolean dejaPresent = false;
        // Selection au hasar
        Random r = new Random();
        int imgDeJeu = r.nextInt(tabResImg.length);
        Log.d("jeu","imageDeja "+imageDejaUse.size()+" tabResImg "+ tabResImg.length);

        // Control d'image
        // Si toutes les images sont passées
        if(imageDejaUse.size() >= tabResImg.length){
            // Fin du jeu transmision donnée a l'activité game over
            Intent intent = new Intent(PlayFullScreen.this, GameOver.class);
            intent.putExtra("MODE_JEU", MODE_JEU);
            intent.putExtra("nbNicoFind", nbNicoFound);
            intent.putExtra("timeLong", compt.getTempsPasseEnMs());
            intent.putExtra("timeString", compt.getTempsPasseEnMnSsMs());
            startActivity(intent);
            Log.d("jeu","TT est fait !!!");
        }else{ // Sinon on enregistrement de l'image en mémoire si non présente en controllant l'utilisation
            if(imageDejaUse.contains(imgDeJeu)){ // Controle si deja utilisé
                dejaPresent = true;
                while(dejaPresent){
                    imgDeJeu = r.nextInt(tabResImg.length);
                    if(!imageDejaUse.contains(imgDeJeu)){
                        dejaPresent = false;
                    }
                }
            }
            imageDejaUse.add(imgDeJeu);
        }

        // Application
        iVNico.setImageResource(Integer.parseInt(tabResImg[imgDeJeu][3]));
        // Placement bouton
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        // Recolte information image
        Drawable img = iVNico.getDrawable();
        int imgH =  img.getMinimumHeight();
        int imgW =  img.getMinimumWidth();
        float echelle =(float)metrics.widthPixels / (float)imgW ;

        // Configuration
        but.setMinimumHeight((int) (Float.parseFloat(tabResImg[imgDeJeu][2])*echelle));
        but.setMinimumWidth((int) (Float.parseFloat(tabResImg[imgDeJeu][2])*echelle));

        // Application
        but.setTranslationX((Integer.parseInt(tabResImg[imgDeJeu][0])*echelle));
        but.setTranslationY(((fL.getLayoutParams().height/2)-((imgH*echelle/2)))+( (Float.parseFloat(tabResImg[imgDeJeu][1])*echelle)) );
    }

    public void pause() {
        butPause.setText("REPRENDRE");
        cacheLayout.setVisibility(View.VISIBLE);
        // Stop chrono, La ca troll juste
        butPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reprendre();
            }
        });
    }

    public void reprendre() {
        butPause.setText("PAUSE");
        cacheLayout.setVisibility(View.INVISIBLE);
        //reprendre chrono
        butPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause();
            }
        });
    }
}
