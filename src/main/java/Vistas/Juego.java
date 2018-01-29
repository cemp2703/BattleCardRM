package Vistas;

import Clases.*;

import javax.swing.border.MatteBorder;
import java.awt.*;

public class Juego {

    public int IdCartaZonaBSel;//id de primera posicion almacenada temporalmente
    public int IdCartaManoSel;//id de segunda posicion almacenada temporalmente

    public int ModoJuego;
    public static class MODOJUEGO {
        public static final int VSPLAYER = 0;//VSPLAYER: Jugador1 vs Jugador2
        public static final int VSBOT = 1;//  VSBOT: Jugador1 vs Maquina
    }

    public int Dificultad;
    public static class DIFICULTAD{
        public static final int FACIL = 0; // Facil(Aleatorio)
        public static final int NORMAL = 1; // Normal (Primero el mejor)
        public static final int AVANZADO = 2; // Avanzado (MaxMin)
    }

    public int Accion;
    public static class ACCION{
        public static final int NOACCION = 0; // NO accion
        public static final int COLOCARATK = 1; // Colocando una Carta
        public static final int COLOCARDEFBA = 2; // Colocando una Carta boca abajo
        public static final int ATACARCARTA = 3; // Atacando una carta
    }

    public Jugador JugadorActual;
    public Jugador JugadorAnterior;

    public Clases.Controlador Controlador;
    public Clases.Maquina Maquina;
    public Estado E;
    public Clases.Regla Regla;


    public Juego(){
        ModoJuego=MODOJUEGO.VSPLAYER;
        Dificultad=DIFICULTAD.FACIL;
        Accion=ACCION.NOACCION;
        JugadorActual=null;
        JugadorAnterior=null;
        Controlador=null;
        Maquina=null;
        Regla=null;
        IdCartaZonaBSel=-1;
        IdCartaManoSel=-1;
        inicializar();
    }

    public Color CMano;
    public Color CBarrera;
    public Color CZonaBatalla;

    void inicializar() {

        this.E = new Estado(new Jugador("Jugador 1"), new Jugador("Jugador 2"), 0, Estado.TURNO.JUGADOR1, Estado.TERMINO.NOTERMINO);

        CMano = new Color(0, 0, 0);
        CBarrera = new Color(139, 69, 19);
        CZonaBatalla = new Color(85, 107, 47);
        //CSeleccionado=new Color(255,215,0);

        for (int i = 0; i < Mano.MAXMANOCARDS; i++) {
            E.Jugador1.Mano.obtenerJCartaxId(i).setJugador(Estado.TURNO.JUGADOR1);
            E.Jugador2.Mano.obtenerJCartaxId(i).setJugador(Estado.TURNO.JUGADOR2BOT);
            E.Jugador1.Mano.obtenerJCartaxId(i).setBorder(new MatteBorder(2, 2, 2, 2, (Color) CMano));
            E.Jugador2.Mano.obtenerJCartaxId(i).setBorder(new MatteBorder(2, 2, 2, 2, (Color) CMano));
        }

        for (int i = 0; i < Barrera.MAXBARRERACARDS; i++) {
            E.Jugador1.Barrera.obtenerJCartaxId(i).setJugador(Estado.TURNO.JUGADOR1);
            E.Jugador2.Barrera.obtenerJCartaxId(i).setJugador(Estado.TURNO.JUGADOR2BOT);
            E.Jugador1.Barrera.obtenerJCartaxId(i).setBorder(new MatteBorder(2, 2, 2, 2, (Color) CBarrera));
            E.Jugador2.Barrera.obtenerJCartaxId(i).setBorder(new MatteBorder(2, 2, 2, 2, (Color) CBarrera));
        }

        for (int i = 0; i < ZonaBatalla.MAXZONABATALLACARDS; i++) {
            E.Jugador1.ZonaBatalla.obtenerJCartaxId(i).setJugador(Estado.TURNO.JUGADOR1);
            E.Jugador2.ZonaBatalla.obtenerJCartaxId(i).setJugador(Estado.TURNO.JUGADOR2BOT);
            E.Jugador1.ZonaBatalla.obtenerJCartaxId(i).setBorder(new MatteBorder(2, 2, 2, 2, (Color) CZonaBatalla));
            E.Jugador2.ZonaBatalla.obtenerJCartaxId(i).setBorder(new MatteBorder(2, 2, 2, 2, (Color) CZonaBatalla));
        }
    }
}


