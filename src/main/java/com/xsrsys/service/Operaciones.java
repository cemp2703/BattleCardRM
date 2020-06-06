package com.xsrsys.service;

import java.util.Random;

import com.xsrsys.model.*;
import com.xsrsys.model.Carta;
import com.xsrsys.model.ZonaBatalla.PosBatalla;

public class Operaciones {

    public int ModoJuego;
    public static class MODOJUEGO {
        public static final int VSPLAYER = 0;//VSPLAYER: Jugador1 vs Jugador2
        public static final int VSBOT = 1;//  VSBOT: Jugador1 vs IA
    }

    public int Dificultad;
    public static class DIFICULTAD{
        public static final int FACIL = 0; // Facil(Aleatorio)
        public static final int NORMAL = 1; // Normal (Primero el mejor)
        public static final int AVANZADO = 2; // Avanzado (MaxMin)
    }

    public PosBatalla CartaPosSel;//posicion  de ataque, defenza, etc  almacenado temporalmente para la carta a colocar en la zona de batalla
    public int IdCartaZonaBSel;//id de posicion en Zona de batalla
    public int IdCartaZonaBSelEnemigo;//id de posicion en Zona de batalla Enemiga
    public int IdCartaManoSel;//id de segunda posicion almacenada temporalmente
    
    private boolean cartaselegidas[][];
    public ResultadoAtaque resATK;

    public Operaciones(){
        ModoJuego=MODOJUEGO.VSPLAYER;
        Dificultad=DIFICULTAD.FACIL;
        IdCartaZonaBSel=-1;
        IdCartaManoSel=-1;
        IdCartaZonaBSelEnemigo= -1;
        CartaPosSel= ZonaBatalla.PosBatalla.NOHAYCARTA;
        cartaselegidas=new boolean[Carta.getNumeroElementosCartas()][Carta.MAXVALORCARTA];
        resATK = null;
    }

    public void repartirCartas(Jugador jug){
        Random rm;
        int n,m,maximoendeck,cartasrepartidas;

        maximoendeck=(Deck.MAXDECK - Mano.MAXMANOCARDS - Barrera.MAXBARRERACARDS);

        for(int i=0;i<cartaselegidas.length;i++){
            for(int j=0;j<cartaselegidas[i].length;j++){
                cartaselegidas[i][j] = false;
            }
        }

        cartasrepartidas=0;

        int numeroElementosCartas = Carta.getNumeroElementosCartas();
        
        while(cartasrepartidas < Deck.MAXDECK ){
            rm=new Random();
            
            n=rm.nextInt(numeroElementosCartas);
            rm=new Random();
            m=rm.nextInt(Carta.MAXVALORCARTA);

            if(!cartaselegidas[n][m]){
                cartaselegidas[n][m]=true;

                cartasrepartidas++;

                if(jug.Barrera.obtenerNumerodeCartas()<Barrera.MAXBARRERACARDS){
                    jug.Barrera.agregarCartaEnEspacioVacio(new Carta(m+1,Carta.Elemento.values()[n]));
                }
                else if(jug.Mano.obtenerNumerodeCartas()<Mano.MAXMANOCARDS){
                    jug.Mano.agregarCartaEnEspacioVacio(new Carta(m+1,Carta.Elemento.values()[n]));
                }
                else if(jug.Deck.Deck.size()< maximoendeck ){
                    jug.Deck.agregarUnaCarta(new Carta(m+1,Carta.Elemento.values()[n]));
                }

            }
        }
    }

}


