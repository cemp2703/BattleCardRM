package com.xsrsys.service;

import java.util.Random;

import com.xsrsys.model.Barrera;
import com.xsrsys.model.Carta;
import com.xsrsys.model.Deck;
import com.xsrsys.model.Carta.Elemento;
import com.xsrsys.model.Estado;
import com.xsrsys.model.Jugador;
import com.xsrsys.model.Jugador.ResultadoCojerUnaCarta;
import com.xsrsys.model.Jugador.VeredictoAtaque;
import com.xsrsys.model.Mano;
import com.xsrsys.model.ZonaBatalla;
import com.xsrsys.model.ZonaBatalla.PosBatalla;
import com.xsrsys.model.Jugador.EstadoCarta;


public class Juego {

    public enum Pantalla {
        BIENVENIDAJUEGO,//Pantalla de Bienvenida
        JUEGO,//Pantalla de Juego
        FINDEJUEGO//Pantalla de Finde Juego
    }
    public enum Dialogo {
        BIENVENIDAJUEGO,//Pantalla de Bienvenida

        OPCIONESENTURNO,

        SELECCIONAR_MANO,
        SELECCIONAR_ZONABATALLA,
        SELECCIONAR_POSICIONBATALLA,

        CARTA_COLOCADA,
        ATAQUE_CARTA_REALIZADO,
        ATAQUE_BARRERA_REALIZADO,
        CAMBIODEPOSICION_REALIZADO,

        //FINALES
        JUGADORSINCARTASBARRERA,
        JUGADORSINCARTASMAZO,

        FINDEJUEGO //Pantalla Final
    }
    public enum MomentoJuego{
        BIENVENIDAJUEGO,//Pantalla de Bienvenida

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

    public Estado E;
    public Operaciones Ops;

    public Pantalla pantalla = Pantalla.BIENVENIDAJUEGO;
    public Dialogo dialogo = Dialogo.BIENVENIDAJUEGO;
    public MomentoJuego momento = MomentoJuego.BIENVENIDAJUEGO;
    public Jugador jugadorVictorioso;

    public String obtenerStringPantalla(){
    	String resp="";
    	switch(pantalla){
        case BIENVENIDAJUEGO:
        	resp+="******************\n";
        	resp+="   Battle Cards   \n";
        	resp+="******************\n";
            break;
        case JUEGO:
        	resp+="TURNO: "+E.jugadorActual.getNombre()+"\n";
            resp+=obtenerStringEstado(E);
            break;
        case FINDEJUEGO:
        	resp+="++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";
        	resp+="++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";
        	resp+="                                                                             \n";
        	resp+="                            Victoria                                        \n";
        	resp+="                         "+jugadorVictorioso.getNombre()+"\n";
        	resp+="                                                                            \n";
        	resp+="++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";
        	resp+="++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";
    	}
    	return resp;
    }
    
    public String obtenerStringDialogo(){
        String resp="";
    	switch (dialogo){
            case BIENVENIDAJUEGO:
                resp+="Ingrese:\n";
                resp+="(I) Iniciar Juego\n";
                resp+="(Cerrar) Cerrar el Juego (En cualquier momento)\n";
                break;
            case OPCIONESENTURNO://Opciones en turno
                resp+="Seleccion accion a realizar:\n";
                if(E.jugadorActual.puedeColocarCartaEnZB())
                    resp+="(C) Colocar una carta\n";
                if(E.jugadorActual.puedeAtacarCartas(E.jugadorAnterior))
                	resp+="(A) Atacar a una carta en Zona de batalla\n";
                if(E.jugadorActual.puedeAtacarBarreras(E.jugadorAnterior))
                    resp+="(B) Atacar a una Barrera\n";
                if(E.jugadorActual.puedeCambiarPosicion())
                    resp+="(K) Cambiar posición de batalla\n";
                resp+="(T) Terminar Turno\n";
                resp+="Accion: \n";
                break;
            case SELECCIONAR_MANO://Seleccionando carta en mano
            	resp+="Seleccionando carta en Mano\n";
            	resp+="Ingrese la posicion de (0 - 4) de la carta en su mano: \n";
            	resp+="O Ingrese (C) Cancelar para volver\n";
                break;
            case SELECCIONAR_ZONABATALLA://Seleccionando carta en zona de batalla
                resp+="Seleccionando carta en Zona de batalla\n";
                resp+="Ingrese la posicion de (0 - 2) de la zona de batalla: \n";
                resp+="O Ingrese (C) Cancelar para volver\n";
                break;
            case SELECCIONAR_POSICIONBATALLA://Elegir posición de batalla
            	resp+="Elegir posición de batalla\n";
            	resp+="Ingrese (A) para Ataque: \n";
            	resp+="Ingrese (D) para Defensa: \n";
            	resp+="O Ingrese (C) Cancelar para volver\n";
                break;
            case CARTA_COLOCADA://Colocar carta -
            	resp+="Carta Colocada!!\n";
            	resp+="Ingrese (C) para continuar\n";
                break;
            case ATAQUE_CARTA_REALIZADO://Ataque Carta Realizado
            	resp+="Ataque Realizado!!\n";
                if(Ops.resATK.veredicto == Jugador.VeredictoAtaque.GANAATACANTE)
                	resp+="Victoria!!\n";
                else if(Ops.resATK.veredicto == Jugador.VeredictoAtaque.PIERDEATACANTE)
                	resp+="Derrota!!\n";
                else if(Ops.resATK.veredicto == Jugador.VeredictoAtaque.EMPATE)
                	resp+="Empate!!\n";
                resp+="     Tu Carta     |        Enemigo         \n";
                resp+= Ops.resATK.cartaAtacante.getValor()+" ("+obtenerStringElementoUnicode(Ops.resATK.cartaAtacante.getElemento())+") " +
                        "(Al Ataque)"+
                        "  |   " +
                        Ops.resATK.cartaAtacada.getValor()+" ("+obtenerStringElementoUnicode(Ops.resATK.cartaAtacada.getElemento())+") "+
                        (Ops.resATK.posicionCartaAtacada == ZonaBatalla.PosBatalla.ATAQUE? "(Al Ataque)" : "(A la Defensa) ") +"\n";
                if(Ops.resATK.estadoBarrera == Jugador.EstadoCarta.DESTRUIDA)
                    resp+="Barrera enemiga destruida\n";
                if(Ops.resATK.estadoCartaAtacante == Jugador.EstadoCarta.DESTRUIDA)
                    resp+="Tu Carta en Zona de Batalla ha sido destruida\n";
                if(Ops.resATK.estadoCartaAtacada == Jugador.EstadoCarta.DESTRUIDA)
                    resp+="Carta enemiga en Zona de Batalla destruida\n";
                resp+="Ingrese (C) para continuar\n";
                break;
            case ATAQUE_BARRERA_REALIZADO://Ataque barrera realizado
            	resp+="Ataque Realizado!!\n";
            	resp+="Barrera Destruida\n";
            	resp+="Ingrese (C) para continuar\n";
                break;
            case CAMBIODEPOSICION_REALIZADO://Cambiar Posición - Cambio de posición realizado
            	resp+="Cambio de Posición Realizado!!\n";
            	resp+="Ingrese (C) para continuar\n";
                break;
            case JUGADORSINCARTASMAZO://Fin de Juego - Jugador Actual se quedó sin cartas en mazo
            	resp+="Fin del Juego!!!\n";
            	resp+=E.jugadorActual.getNombre()+" se quedó sin cartas en el mazo!!\n";
            	resp+="Ingrese (C) para continuar\n";
                break;
            case JUGADORSINCARTASBARRERA://Fin de Juego - Jugador Contrario se quedó sin barreras
            	resp+="Fin del Juego!!!\n";
            	resp+=E.jugadorAnterior.getNombre()+" se quedó sin barreras!!\n";
            	resp+="Ingrese (C) para continuar\n";
                break;
            case FINDEJUEGO:
                resp+="Fin del Juego!!!\n";
                resp+="\n";
                resp+="Ingrese (C) para Volver al Inicio\n";
                break;
        }
    	return resp;
    }
    
    String obtenerStringEstado(Estado E) {
    	String resp = "";
    	resp+="*****************************************************************************\n";
        resp+="Jugador 1\n";
        resp+="Mano\n";
        for(int i = 0; i < Mano.MAXMANOCARDS; i++){
            if(E.jugador1.Mano.obtenerCartaxId(i) != null)
                resp+=E.jugador1.Mano.obtenerCartaxId(i).getValor() + " " +
                        obtenerStringElementoUnicode(E.jugador1.Mano.obtenerCartaxId(i).getElemento()) + " | ";
            else
                resp+="VACIO | ";
        }
        resp+="\n";
        resp+="Barrera\n";
        for(int i=0;i < Barrera.MAXBARRERACARDS;i++){
            if(E.jugador1.Barrera.obtenerCartaxId(i) != null)
                resp+="BARRERA| ";
            else
                resp+="VACIO  | ";
        }
        resp+="\n";
        resp+="ZonaBatalla\n";
        for(int i=0;i < ZonaBatalla.MAXZONABATALLACARDS;i++){
            if(E.jugador1.ZBatalla.obtenerCartaxId(i) != null)
                resp+=E.jugador1.ZBatalla.obtenerCartaxId(i).getValor()+ " " +
                        obtenerStringElementoUnicode(E.jugador1.ZBatalla.obtenerCartaxId(i).getElemento()) + " " +
                        obtenerStringPosCarta(E.jugador1.ZBatalla.posBatalla[i]) + " | ";
            else
                resp+="VACIO | ";
        }
        resp+="\n";

        resp+="Jugador 2\n";
        resp+="Mano\n";
        for(int i=0;i < Mano.MAXMANOCARDS;i++){
            if(E.jugador2.Mano.obtenerCartaxId(i) != null)
                resp+=E.jugador2.Mano.obtenerCartaxId(i).getValor() + " " +
                        obtenerStringElementoUnicode(E.jugador2.Mano.obtenerCartaxId(i).getElemento()) + " | ";
            else
                resp+="VACIO | ";
        }
        resp+="\n";
        resp+="Barrera\n";
        for(int i=0;i < Barrera.MAXBARRERACARDS;i++){
            if(E.jugador2.Barrera.obtenerCartaxId(i) != null)
                resp+="BARRERA| ";
            else
                resp+="VACIO | ";
        }
        resp+="\n";
        resp+="ZonaBatalla\n";
        for(int i=0;i < ZonaBatalla.MAXZONABATALLACARDS;i++){
            if(E.jugador2.ZBatalla.obtenerCartaxId(i) != null)
                resp+=E.jugador2.ZBatalla.obtenerCartaxId(i).getValor() + " " +
                        obtenerStringElementoUnicode(E.jugador2.ZBatalla.obtenerCartaxId(i).getElemento()) + " " +
                        obtenerStringPosCarta(E.jugador2.ZBatalla.posBatalla[i]) + " | ";
            else
                resp+="VACIO | ";
        }

        resp+="\n";
        resp+="*****************************************************************************\n";
        resp+="\n";
        return resp;
    }
    
    String obtenerStringElementoUnicode(Elemento e){
        String elemento = "";
        switch(e){
            case CORAZON: elemento =  "\u2665"; break;
            case COCO: elemento = "\u2666"; break;
            case TREBOL: elemento = "\u2663"; break;
            case ESPADA: elemento = "\u2660"; break;
        }
        return elemento;
    }
    
    String obtenerStringPosCarta(PosBatalla posBatalla){
        switch(posBatalla){
            case NOHAYCARTA: return "VACIO";
            case ATAQUE: return "ATQ";
            case DEFCARAABAJO: return "DBA"; //defensa boca abajo
            case DEFCARAARRIBA: return "DBV"; //defensa boca arriba
            default: return "";
        }
    }
    
    public void pantallaBienvenida() {
    	pantalla = Pantalla.BIENVENIDAJUEGO;
        dialogo = Dialogo.BIENVENIDAJUEGO;
        momento = MomentoJuego.BIENVENIDAJUEGO;
    }
    
    public void iniciarJuegoNuevo(){
    	if(momento == MomentoJuego.BIENVENIDAJUEGO) {
        	E = new Estado(new Jugador("Jugador1"),new Jugador("Jugador2"));
            Ops = new Operaciones();
            repartirCartas(E.jugadorActual);
            repartirCartas(E.jugadorAnterior);
       	 	E.jugadorActual.accionIniciarTurno();
       	 	E.jugadorActual.accionCogerUnaCartaDelDeck();
            pantalla = Pantalla.JUEGO;
            dialogo = Dialogo.OPCIONESENTURNO;
            momento = MomentoJuego.OPCIONESENTURNO;
    	}
    }
    
    public void iniciarColocarCarta() {
        if (E.jugadorActual.puedeColocarCartaEnZB()){
            dialogo = Dialogo.SELECCIONAR_MANO;
            momento = MomentoJuego.COLOCAR_SELECCIONARMANO;
        }
    }
    
    public void colocarSeleccionarMano( int IdCartaManoSel) {
    	if (E.jugadorActual.Mano.obtenerCartaxId(IdCartaManoSel) != null) {
            Ops.IdCartaManoSel = IdCartaManoSel;
            dialogo = Dialogo.SELECCIONAR_ZONABATALLA;
            momento = MomentoJuego.COLOCAR_SELECCIONARZONABATALLA;
        }
    }
    
    public void colocarSeleccionarZonaBatalla(int IdCartaZonaBSel) {
        if (E.jugadorActual.ZBatalla.obtenerCartaxId(IdCartaZonaBSel) == null) {
            Ops.IdCartaZonaBSel = IdCartaZonaBSel;
            dialogo = Dialogo.SELECCIONAR_POSICIONBATALLA;
            momento = MomentoJuego.COLOCAR_SELECCIONARPOSICIONBATALLA;
        }
    }
    
    public void colocarSeleccionarPosicionBatallaAtaque() {
        E.jugadorActual.accionColocarCarta(Ops.IdCartaZonaBSel, Ops.IdCartaManoSel, ZonaBatalla.PosBatalla.ATAQUE);
        dialogo = Dialogo.CARTA_COLOCADA;
        momento = MomentoJuego.COLOCAR_CARTACOLOCADA;
    }
    
    public void colocarSeleccionarPosicionBatallaDefensa() {
        E.jugadorActual.accionColocarCarta(Ops.IdCartaZonaBSel, Ops.IdCartaManoSel, ZonaBatalla.PosBatalla.DEFCARAABAJO);
        dialogo = Dialogo.CARTA_COLOCADA;
        momento = MomentoJuego.COLOCAR_CARTACOLOCADA;
    }
    
    public void iniciarAtacarCarta() {
        if (E.jugadorActual.puedeAtacarCartas(E.jugadorAnterior)){
            dialogo = Dialogo.SELECCIONAR_ZONABATALLA;
            momento = MomentoJuego.ATACARCARTA_SELECCIONARZONABATALLA;
        }
    }
    
    public void  atacarCartaSeleccionarZonaBatalla(int IdCartaZonaBSel) {
        if (E.jugadorActual.ZBatalla.obtenerCartaxId(IdCartaZonaBSel) != null) {
            Ops.IdCartaZonaBSel = IdCartaZonaBSel;
            dialogo = Dialogo.SELECCIONAR_ZONABATALLA;
            momento = MomentoJuego.ATACARCARTA_SELECCIONARZONABATALLAE;
        }
    }
    
    public void atacarCartaSeleccionarZonaBatallaEnemiga(int IdCartaZonaBSelEnemigo) {
        Ops.IdCartaZonaBSelEnemigo = IdCartaZonaBSelEnemigo;
        Ops.resATK = E.jugadorActual.accionAtacarCarta(E.jugadorAnterior, Ops.IdCartaZonaBSelEnemigo, Ops.IdCartaZonaBSel);
        if (Ops.resATK.veredicto == Jugador.VeredictoAtaque.ENEMIGOSINBARRERA) {
            terminoSinBarreras();
            dialogo = Dialogo.JUGADORSINCARTASBARRERA;
            momento = MomentoJuego.JUGADORSINCARTASBARRERA;
        } else if (Ops.resATK.veredicto != Jugador.VeredictoAtaque.NOSECUMPLENCOND) {
            dialogo = Dialogo.ATAQUE_CARTA_REALIZADO;
            momento = MomentoJuego.ATACARCARTA_ATAQUEREALIZADO;
        }
    }
    
    public void iniciarAtacarBarrera() {
        if (E.jugadorActual.puedeAtacarBarreras(E.jugadorAnterior)){
            dialogo = Dialogo.SELECCIONAR_ZONABATALLA;
            momento = MomentoJuego.ATACARBARRERA_SELECCIONARZONABATALLA;
        }
    }
    
    public void atacarBarreraSeleccionarZonaBatalla(int IdCartaZonaBSel) {
        Ops.IdCartaZonaBSel = IdCartaZonaBSel;
        VeredictoAtaque res = E.jugadorActual.accionAtacarBarrera(E.jugadorAnterior, Ops.IdCartaZonaBSel);
        if (res == Jugador.VeredictoAtaque.BARRERADESTRUIDA) {
            dialogo = Dialogo.ATAQUE_BARRERA_REALIZADO;
            momento = MomentoJuego.ATACARBARRERA_ATAQUEREALIZADO;
        } else if (res == Jugador.VeredictoAtaque.ENEMIGOSINBARRERA) {
            terminoSinBarreras();
            dialogo = Dialogo.JUGADORSINCARTASBARRERA;
            momento = MomentoJuego.JUGADORSINCARTASBARRERA;
        }
    }
    
    public void iniciarCambiarPosicionBatalla() {
        if (E.jugadorActual.puedeCambiarPosicion()){
            dialogo = Dialogo.SELECCIONAR_ZONABATALLA;
            momento = MomentoJuego.CAMBIARPOSICIONBATALLA_SELECCIONARZONABATALLA;
        }
    }
    
    public void cambiarPosicionSeleccionarZonaBatalla(int IdCartaZonaBSel) {
        Ops.IdCartaZonaBSel = IdCartaZonaBSel;
        if (E.jugadorActual.accionCambiarPosicionBatalla(Ops.IdCartaZonaBSel)) {
            dialogo = Dialogo.CAMBIODEPOSICION_REALIZADO;
            momento = MomentoJuego.CAMBIARPOSICIONBATALLA_REALIZADO;
        }
    }
    
    public void cambiarTurno() {
    	cambioDeJugadorActual();
    	ResultadoCojerUnaCarta res = E.jugadorActual.accionCogerUnaCartaDelDeck();
        if (res == Jugador.ResultadoCojerUnaCarta.EXITO || res == Jugador.ResultadoCojerUnaCarta.MANOLLENA) {
            dialogo = Dialogo.OPCIONESENTURNO;
            momento = MomentoJuego.OPCIONESENTURNO;
        }
        else if (res == Jugador.ResultadoCojerUnaCarta.DECKVACIO) {
            dialogo = Dialogo.JUGADORSINCARTASBARRERA;
            momento = MomentoJuego.JUGADORSINCARTASBARRERA;
        }
    }
    
    public void pantallaFinDelJuego() {
        pantalla = Pantalla.FINDEJUEGO;
        dialogo = Dialogo.FINDEJUEGO;
    }
    
    public void opcionesEnTurno() {
        dialogo = Dialogo.OPCIONESENTURNO;
        momento = MomentoJuego.OPCIONESENTURNO;
    }
    
	public void terminoSinBarreras(){
		jugadorVictorioso = E.jugadorActual;
	}
	public void terminoSinCartas(){
		jugadorVictorioso=E.jugadorAnterior;
	}
	
	public ResultadoCojerUnaCarta cogerUnaCartaDelDeck() {
		ResultadoCojerUnaCarta res = E.jugadorActual.accionCogerUnaCartaDelDeck();
		if( res == Jugador.ResultadoCojerUnaCarta.DECKVACIO) {
			terminoSinCartas();
		}
		return res;
	}
	
    public void repartirCartas(Jugador jug){
    	boolean cartaselegidas[][];
    	cartaselegidas=new boolean[Carta.getNumeroElementosCartas()][Carta.MAXVALORCARTA];
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
    
	public void cambioDeJugadorActual(){
		Jugador jugadorTmp = E.jugadorActual;
		E.jugadorActual = E.jugadorAnterior;
		E.jugadorAnterior = jugadorTmp;
	}
}

