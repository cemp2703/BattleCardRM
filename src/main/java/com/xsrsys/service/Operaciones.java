package com.xsrsys.service;

import com.xsrsys.model.*;

public class Operaciones {

    public ModoJuego modoJuego;
    public enum ModoJuego {
        VSPLAYER, //VSPLAYER: Jugador1 vs Jugador2
        VSBOT //  VSBOT: Jugador1 vs IA
    }

    public Dificultad dificultad;
    
    public enum Dificultad{
    	FACIL, // Facil(Aleatorio)
    	NORMAL, // Normal (Primero el mejor)
    	AVANZADO // Avanzado (MaxMin)
    }
    public int IdCartaZonaBSel;//id de posicion en Zona de batalla
    public int IdCartaZonaBSelEnemigo;//id de posicion en Zona de batalla Enemiga
    public int IdCartaManoSel;//id de segunda posicion almacenada temporalmente
    public ResultadoAtaque resATK;

    public Operaciones(){
        modoJuego=ModoJuego.VSPLAYER;
        dificultad=Dificultad.FACIL;
        IdCartaZonaBSel=-1;
        IdCartaManoSel=-1;
        IdCartaZonaBSelEnemigo= -1;
        resATK = null;
    }

}


