package Vistas;

import Clases.*;
import Juego.*;

import java.awt.*;

public class Juego {

    public int Accion;
    public static class ACCION{
        public static final int NOACCION = 0; // NO accion
        public static final int COLOCARATK = 1; // Colocando una Carta
        public static final int COLOCARDEFBA = 2; // Colocando una Carta boca abajo
        public static final int ATACARCARTA = 3; // Atacando una carta
    }

    public Estado E;


    public Juego(){
        Accion=ACCION.NOACCION;
        inicializar();
    }

    public Color CMano;
    public Color CBarrera;
    public Color CZonaBatalla;

    void inicializar() {

        this.E = new Estado(new Jugador("Jugador 1"), new Jugador("Jugador 2"));

        CMano = new Color(0, 0, 0);
        CBarrera = new Color(139, 69, 19);
        CZonaBatalla = new Color(85, 107, 47);
        //CSeleccionado=new Color(255,215,0);

        for (int i = 0; i < Mano.MAXMANOCARDS; i++) {
            /*
            E.Jugador1.Mano.obtenerCartaxId(i).setJugador(Estado.TURNO.JUGADOR1);
            E.Jugador2.Mano.obtenerCartaxId(i).setJugador(Estado.TURNO.JUGADOR2BOT);
            E.Jugador1.Mano.obtenerCartaxId(i).setBorder(new MatteBorder(2, 2, 2, 2, (Color) CMano));
            E.Jugador2.Mano.obtenerCartaxId(i).setBorder(new MatteBorder(2, 2, 2, 2, (Color) CMano));
            */
        }

        for (int i = 0; i < Barrera.MAXBARRERACARDS; i++) {
            /*
            E.Jugador1.Barrera.obtenerJCartaxId(i).setJugador(Estado.TURNO.JUGADOR1);
            E.Jugador2.Barrera.obtenerJCartaxId(i).setJugador(Estado.TURNO.JUGADOR2BOT);
            E.Jugador1.Barrera.obtenerJCartaxId(i).setBorder(new MatteBorder(2, 2, 2, 2, (Color) CBarrera));
            E.Jugador2.Barrera.obtenerJCartaxId(i).setBorder(new MatteBorder(2, 2, 2, 2, (Color) CBarrera));
            */
        }

        for (int i = 0; i < ZonaBatalla.MAXZONABATALLACARDS; i++) {
            /*
            E.Jugador1.ZonaBatalla.obtenerJCartaxId(i).setJugador(Estado.TURNO.JUGADOR1);
            E.Jugador2.ZonaBatalla.obtenerJCartaxId(i).setJugador(Estado.TURNO.JUGADOR2BOT);
            E.Jugador1.ZonaBatalla.obtenerJCartaxId(i).setBorder(new MatteBorder(2, 2, 2, 2, (Color) CZonaBatalla));
            E.Jugador2.ZonaBatalla.obtenerJCartaxId(i).setBorder(new MatteBorder(2, 2, 2, 2, (Color) CZonaBatalla));
            */
        }

    }
}


