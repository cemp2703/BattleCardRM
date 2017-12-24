package Clases;

public class Juego {

    public int CartaPosSel;//posicion  de ataque, defenza, etc  almacenado temporalmente para la carta a colocar en la zona de batalla
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
        public static final int COLOCANDOCARTA = 1; // Colocando una Carta
        public static final int ATACANDOCARTA = 2; // Atacando una carta
    }

    public Jugador JugadorActual;
    public Jugador JugadorAnterior;

    public Controlador Controlador;
    public Maquina Maquina;
    public Estado Estado;
    public Regla Regla;


    public Juego(){
        ModoJuego=MODOJUEGO.VSPLAYER;
        Dificultad=DIFICULTAD.FACIL;
        Accion=ACCION.NOACCION;
        JugadorActual=null;
        JugadorAnterior=null;
        Controlador=null;
        Maquina=null;
        Estado=null;
        Regla=null;
        CartaPosSel= Carta.CARTAPOS.NOSELECCION;
        IdCartaZonaBSel=-1;
        IdCartaManoSel=-1;
    }


}


