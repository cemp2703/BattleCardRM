package com.xsrsys.service;


import com.xsrsys.service.Jugador.ResultadoCojerUnaCarta;
import com.xsrsys.service.Jugador.VeredictoAtaque;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class Juego {

    public enum Pantalla {
        BIENVENIDAJUEGO,//Pantalla de Bienvenida
        JUEGO,//Pantalla de Juego
        FINDEJUEGO//Pantalla de Finde Juego
    }
    public enum DialogoJuego {
        OPCIONESENTURNO,

        SELECCIONAR_MANO,
        SELECCIONAR_ZONABATALLA,
        SELECCIONAR_POSICIONBATALLA,
        SELECCIONAR_POSICIONBATALLAE,

        CARTA_COLOCADA,
        ATAQUE_CARTA_REALIZADO,
        ATAQUE_BARRERA_REALIZADO,
        CAMBIODEPOSICION_REALIZADO,

        //FINALES
        JUGADORSINCARTASBARRERA,
        JUGADORSINCARTASMAZO,
    }
    public enum Momento{
    	BIENVENIDAJUEGO,
        OPCIONESENTURNO,//Opciones ofrecidas al jugador en su turno
        //ColocarCarta
        COLOCAR_SELECCIONARMANO,//Seleccionar Carta en Mano
        COLOCAR_SELECCIONARZONABATALLA,//Selecionar posicion en zona de batalla para colocar carta
        COLOCAR_SELECCIONARPOSICIONBATALLA,//Elegir posicion de batalla
        COLOCAR_CARTACOLOCADA,
        //AtacarCarta
        ATACARCARTA_SELECCIONARZONABATALLA,//Seleccionar Carta en zona de batalla
        ATACARCARTA_SELECCIONARZONABATALLAE,//Seleccionar Carta en zona de batalla enemiga
        ATACARCARTA_ATAQUEREALIZADO,//Ataque realizado
        //AtacarBarrera
        ATACARBARRERA_SELECCIONARZONABATALLA,
        ATACARBARRERA_ATAQUEREALIZADO,
        //CambioDePosicionDeBatalla
        CAMBIARPOSICIONBATALLA_SELECCIONARZONABATALLA,
        CAMBIARPOSICIONBATALLA_REALIZADO,


        //FINALES
        JUGADORSINCARTASBARRERA,
        JUGADORSINCARTASMAZO,

        FINDEJUEGO //Pantalla Final
    }
    public enum ModoJuego {
        VSPLAYER, //VSPLAYER: Jugador1 vs Jugador2
        VSBOT //  VSBOT: Jugador1 vs IA
    }
    
    public ModoJuego modoJuego = ModoJuego.VSPLAYER;
    public Pantalla pantalla = Pantalla.BIENVENIDAJUEGO;
    public DialogoJuego dialogoJuego;
    public Momento momento = Momento.BIENVENIDAJUEGO;
    public Tablero tablero;
    public Jugador jugadorVictorioso;
    int IdCartaZonaBSel;//id de posicion en Zona de batalla
    int IdCartaZonaBSelEnemigo;//id de posicion en Zona de batalla Enemiga
    int IdCartaManoSel; //id de segunda posicion almacenada temporalmente
    ResultadoAtaque resATK;
    protected static final Logger logger = LogManager.getLogger(Juego.class);
    
    public void pantallaBienvenida() {
    	pantalla = Pantalla.BIENVENIDAJUEGO;
    	momento = Momento.BIENVENIDAJUEGO;
    }
    
    public void iniciarJuegoNuevo(){
		tablero = new Tablero(new Jugador("Jugador1"),new Jugador("Jugador2"));
	    tablero.jugadorActual.repartirCartas();
	    tablero.jugadorAnterior.repartirCartas();
	 	tablero.jugadorActual.accionIniciarTurno();
	    pantalla = Pantalla.JUEGO;
	    momento = Momento.OPCIONESENTURNO;
	    dialogoJuego = DialogoJuego.OPCIONESENTURNO;
    }
    
    public void iniciarColocarCarta() {
        if (tablero.jugadorActual.puedeColocarCartaEnZB()){
        	momento = Momento.COLOCAR_SELECCIONARMANO;
        	dialogoJuego = DialogoJuego.SELECCIONAR_MANO;
        }
    }
    
    public void colocarSeleccionarMano( int IdCartaManoSel) {
    	if (tablero.jugadorActual.Mano.obtenerCartaxId(IdCartaManoSel) != null) {
            this.IdCartaManoSel = IdCartaManoSel;
            momento = Momento.COLOCAR_SELECCIONARZONABATALLA;
            dialogoJuego = DialogoJuego.SELECCIONAR_ZONABATALLA;
        }
    }
    
    public void colocarSeleccionarZonaBatalla(int IdCartaZonaBSel) {
        if (tablero.jugadorActual.ZBatalla.obtenerCartaxId(IdCartaZonaBSel) == null) {
            this.IdCartaZonaBSel = IdCartaZonaBSel;
            momento = Momento.COLOCAR_SELECCIONARPOSICIONBATALLA;
            dialogoJuego = DialogoJuego.SELECCIONAR_POSICIONBATALLA;
            
        }
    }
    
    public void colocarSeleccionarPosicionBatallaAtaque() {
        tablero.jugadorActual.accionColocarCarta(IdCartaZonaBSel, IdCartaManoSel, ZonaBatalla.PosBatalla.ATAQUE);
        momento = Momento.COLOCAR_CARTACOLOCADA;
        dialogoJuego = DialogoJuego.CARTA_COLOCADA;

    }
    
    public void colocarSeleccionarPosicionBatallaDefensa() {
        tablero.jugadorActual.accionColocarCarta(IdCartaZonaBSel, IdCartaManoSel, ZonaBatalla.PosBatalla.DEFCARAABAJO);
        momento = Momento.COLOCAR_CARTACOLOCADA;
        dialogoJuego = DialogoJuego.CARTA_COLOCADA;

    }
    
    public void iniciarAtacarCarta() {
        if (tablero.jugadorActual.puedeAtacarCartas(tablero.jugadorAnterior)){
            momento = Momento.ATACARCARTA_SELECCIONARZONABATALLA;
        	dialogoJuego = DialogoJuego.SELECCIONAR_ZONABATALLA;
        }
    }
    
    public void  atacarCartaSeleccionarZonaBatalla(int IdCartaZonaBSel) {
        if (tablero.jugadorActual.ZBatalla.obtenerCartaxId(IdCartaZonaBSel) != null) {
            this.IdCartaZonaBSel = IdCartaZonaBSel;
            momento = Momento.ATACARCARTA_SELECCIONARZONABATALLAE;
            dialogoJuego = DialogoJuego.SELECCIONAR_POSICIONBATALLAE;
        }
    }
    
    public void atacarCartaSeleccionarZonaBatallaEnemiga(int IdCartaZonaBSelEnemigo) {
        this.IdCartaZonaBSelEnemigo = IdCartaZonaBSelEnemigo;
        resATK = tablero.jugadorActual.accionAtacarCarta(tablero.jugadorAnterior, this.IdCartaZonaBSelEnemigo, IdCartaZonaBSel);
        if (resATK.veredicto == Jugador.VeredictoAtaque.ENEMIGOSINBARRERA) {
        	jugadorVictorioso = tablero.jugadorActual;
        	momento = Momento.JUGADORSINCARTASBARRERA;
        	dialogoJuego = DialogoJuego.JUGADORSINCARTASBARRERA;
            
        } else if (resATK.veredicto != Jugador.VeredictoAtaque.NOSECUMPLENCOND) {
            momento = Momento.ATACARCARTA_ATAQUEREALIZADO;
            dialogoJuego = DialogoJuego.ATAQUE_CARTA_REALIZADO;
        }
    }
    
    public void iniciarAtacarBarrera() {
        if (tablero.jugadorActual.puedeAtacarBarreras(tablero.jugadorAnterior)){
            momento = Momento.ATACARBARRERA_SELECCIONARZONABATALLA;
        	dialogoJuego = DialogoJuego.SELECCIONAR_ZONABATALLA;
        }
    }
    
    public void atacarBarreraSeleccionarZonaBatalla(int IdCartaZonaBSel) {
        this.IdCartaZonaBSel = IdCartaZonaBSel;
        VeredictoAtaque res = tablero.jugadorActual.accionAtacarBarrera(tablero.jugadorAnterior, IdCartaZonaBSel);
        if (res == Jugador.VeredictoAtaque.BARRERADESTRUIDA) {
            momento = Momento.ATACARBARRERA_ATAQUEREALIZADO;
        	dialogoJuego = DialogoJuego.ATAQUE_BARRERA_REALIZADO;

        } else if (res == Jugador.VeredictoAtaque.ENEMIGOSINBARRERA) {
        	jugadorVictorioso = tablero.jugadorActual;
        	momento = Momento.JUGADORSINCARTASBARRERA;
        	dialogoJuego = DialogoJuego.JUGADORSINCARTASBARRERA;
        }
    }
    
    public void iniciarCambiarPosicionBatalla() {
        if (tablero.jugadorActual.puedeCambiarPosicion()){
            momento = Momento.CAMBIARPOSICIONBATALLA_SELECCIONARZONABATALLA;
        	dialogoJuego = DialogoJuego.SELECCIONAR_ZONABATALLA;
        }
    }
    
    public void cambiarPosicionSeleccionarZonaBatalla(int IdCartaZonaBSel) {
        this.IdCartaZonaBSel = IdCartaZonaBSel;
        if (tablero.jugadorActual.accionCambiarPosicionBatalla(IdCartaZonaBSel)) {
            momento = Momento.CAMBIARPOSICIONBATALLA_REALIZADO;
        	dialogoJuego = DialogoJuego.CAMBIODEPOSICION_REALIZADO;
        }
    }
    
    public void finalizarJuego() {
        momento = Momento.FINDEJUEGO;
    	pantalla = Pantalla.FINDEJUEGO;
    }
    
    public void opcionesEnTurno() {
        momento = Momento.OPCIONESENTURNO;
        dialogoJuego = DialogoJuego.OPCIONESENTURNO;
    }
	
	public void cogerUnaCartaDelDeck() {
    	ResultadoCojerUnaCarta res = tablero.jugadorActual.accionCogerUnaCartaDelDeck();
        if (res == Jugador.ResultadoCojerUnaCarta.EXITO || res == Jugador.ResultadoCojerUnaCarta.MANOLLENA) {
            dialogoJuego = DialogoJuego.OPCIONESENTURNO;
            momento = Momento.OPCIONESENTURNO;
        }
        else {
            dialogoJuego = DialogoJuego.JUGADORSINCARTASBARRERA;
            momento = Momento.JUGADORSINCARTASBARRERA;
            jugadorVictorioso=tablero.jugadorAnterior;
        }
	}
	
    public void cambiarTurno() {
    	tablero.cambioDeJugadorActual();
    	tablero.jugadorActual.accionIniciarTurno();
    	cogerUnaCartaDelDeck();
    }
    
}

