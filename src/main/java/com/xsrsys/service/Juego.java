package com.xsrsys.service;

import com.xsrsys.model.Barrera;
import com.xsrsys.model.Carta.Elemento;
import com.xsrsys.model.Estado;
import com.xsrsys.model.Jugador;
import com.xsrsys.model.Jugador.ResultadoAtacarCarta;
import com.xsrsys.model.Jugador.ResultadoCojerUnaCarta;
import com.xsrsys.model.Mano;
import com.xsrsys.model.ZonaBatalla;
import com.xsrsys.model.ZonaBatalla.PosBatalla;

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
    
    public String obtenerStringPantalla(){
    	String resp="";
    	switch(pantalla){
        case BIENVENIDAJUEGO:
        	resp+="******************\n";
        	resp+="   Battle Cards   \n";
        	resp+="******************\n";
            break;
        case JUEGO:
        	resp+="TURNO: "+E.JugadorActual.getNombre()+"\n";
            resp+=obtenerStringEstado(E);
            break;
        case FINDEJUEGO:
        	resp+="++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";
        	resp+="++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";
        	resp+="                                                                             \n";
        	resp+="                            Victoria                                        \n";
        	resp+="                         "+E.JugadorVictorioso.getNombre()+"\n";
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
                if(E.JugadorActual.puedeColocarCartaEnZB())
                    resp+="(C) Colocar una carta\n";
                if(E.JugadorActual.puedeAtacarCartas(E.JugadorAnterior))
                	resp+="(A) Atacar a una carta en Zona de batalla\n";
                if(E.JugadorActual.puedeAtacarBarreras(E.JugadorAnterior))
                    resp+="(B) Atacar a una Barrera\n";
                if(E.JugadorActual.puedeCambiarPosicion())
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
                if(Ops.resATK.resultado == Jugador.ResultadoAtacarCarta.GANAATACANTE)
                	resp+="Victoria!!\n";
                else if(Ops.resATK.resultado == Jugador.ResultadoAtacarCarta.PIERDEATACANTE)
                	resp+="Derrota!!\n";
                else if(Ops.resATK.resultado == Jugador.ResultadoAtacarCarta.EMPATE)
                	resp+="Empate!!\n";
                resp+="     Tu Carta     |        Enemigo         \n";
                resp+= Ops.resATK.valorCartaAtacante+" ("+obtenerStringElementoUnicode(Ops.resATK.elementoCartaAtacante)+") " +
                        "(Al Ataque)"+
                        "  |   " +
                        Ops.resATK.valorCartaAtacada+" ("+obtenerStringElementoUnicode(Ops.resATK.elementoCartaAtacada)+") "+
                        (Ops.resATK.posicionCartaAtacada == ZonaBatalla.PosBatalla.ATAQUE? "(Al Ataque)" : "(A la Defensa) ") +"\n";
                if(Ops.resATK.barrera == Jugador.ResultadoCarta.DOWN)
                    resp+="Barrera enemiga destruida\n";
                if(Ops.resATK.cartaAtacante == Jugador.ResultadoCarta.DOWN)
                    resp+="Tu Carta en Zona de Batalla ha sido destruida\n";
                if(Ops.resATK.cartaAtacada == Jugador.ResultadoCarta.DOWN)
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
            	resp+=E.JugadorActual.getNombre()+" se quedó sin cartas en el mazo!!\n";
            	resp+="Ingrese (C) para continuar\n";
                break;
            case JUGADORSINCARTASBARRERA://Fin de Juego - Jugador Contrario se quedó sin barreras
            	resp+="Fin del Juego!!!\n";
            	resp+=E.JugadorAnterior.getNombre()+" se quedó sin barreras!!\n";
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
            if(E.Jugador1.Mano.obtenerCartaxId(i) != null)
                resp+=E.Jugador1.Mano.obtenerCartaxId(i).getValor() + " " +
                        obtenerStringElementoUnicode(E.Jugador1.Mano.obtenerCartaxId(i).getElemento()) + " | ";
            else
                resp+="VACIO | ";
        }
        resp+="\n";
        resp+="Barrera\n";
        for(int i=0;i < Barrera.MAXBARRERACARDS;i++){
            if(E.Jugador1.Barrera.obtenerCartaxId(i) != null)
                resp+="BARRERA| ";
            else
                resp+="VACIO  | ";
        }
        resp+="\n";
        resp+="ZonaBatalla\n";
        for(int i=0;i < ZonaBatalla.MAXZONABATALLACARDS;i++){
            if(E.Jugador1.ZBatalla.obtenerCartaxId(i) != null)
                resp+=E.Jugador1.ZBatalla.obtenerCartaxId(i).getValor()+ " " +
                        obtenerStringElementoUnicode(E.Jugador1.ZBatalla.obtenerCartaxId(i).getElemento()) + " " +
                        obtenerStringPosCarta(E.Jugador1.ZBatalla.posBatalla[i]) + " | ";
            else
                resp+="VACIO | ";
        }
        resp+="\n";

        resp+="Jugador 2\n";
        resp+="Mano\n";
        for(int i=0;i < Mano.MAXMANOCARDS;i++){
            if(E.Jugador2.Mano.obtenerCartaxId(i) != null)
                resp+=E.Jugador2.Mano.obtenerCartaxId(i).getValor() + " " +
                        obtenerStringElementoUnicode(E.Jugador2.Mano.obtenerCartaxId(i).getElemento()) + " | ";
            else
                resp+="VACIO | ";
        }
        resp+="\n";
        resp+="Barrera\n";
        for(int i=0;i < Barrera.MAXBARRERACARDS;i++){
            if(E.Jugador2.Barrera.obtenerCartaxId(i) != null)
                resp+="BARRERA| ";
            else
                resp+="VACIO | ";
        }
        resp+="\n";
        resp+="ZonaBatalla\n";
        for(int i=0;i < ZonaBatalla.MAXZONABATALLACARDS;i++){
            if(E.Jugador2.ZBatalla.obtenerCartaxId(i) != null)
                resp+=E.Jugador2.ZBatalla.obtenerCartaxId(i).getValor() + " " +
                        obtenerStringElementoUnicode(E.Jugador2.ZBatalla.obtenerCartaxId(i).getElemento()) + " " +
                        obtenerStringPosCarta(E.Jugador2.ZBatalla.posBatalla[i]) + " | ";
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
            Ops.repartirCartas(E.JugadorActual);
            Ops.repartirCartas(E.JugadorAnterior);
            E.JugadorActual.accionIniciarTurno();
            pantalla = Pantalla.JUEGO;
            dialogo = Dialogo.OPCIONESENTURNO;
            momento = MomentoJuego.OPCIONESENTURNO;
    	}
    }
    
    public void iniciarColocarCarta() {
        if (E.JugadorActual.puedeColocarCartaEnZB()){
            dialogo = Dialogo.SELECCIONAR_MANO;
            momento = MomentoJuego.COLOCAR_SELECCIONARMANO;
        }
    }
    
    public void colocarSeleccionarMano( int IdCartaManoSel) {
    	if (E.JugadorActual.Mano.obtenerCartaxId(IdCartaManoSel) != null) {
            Ops.IdCartaManoSel = IdCartaManoSel;
            dialogo = Dialogo.SELECCIONAR_ZONABATALLA;
            momento = MomentoJuego.COLOCAR_SELECCIONARZONABATALLA;
        }
    }
    
    public void colocarSeleccionarZonaBatalla(int IdCartaZonaBSel) {
        if (E.JugadorActual.ZBatalla.obtenerCartaxId(IdCartaZonaBSel) == null) {
            Ops.IdCartaZonaBSel = IdCartaZonaBSel;
            dialogo = Dialogo.SELECCIONAR_POSICIONBATALLA;
            momento = MomentoJuego.COLOCAR_SELECCIONARPOSICIONBATALLA;
        }
    }
    
    public void colocarSeleccionarPosicionBatallaAtaque() {
        E.JugadorActual.accionColocarCarta(Ops.IdCartaZonaBSel, Ops.IdCartaManoSel, ZonaBatalla.PosBatalla.ATAQUE);
        dialogo = Dialogo.CARTA_COLOCADA;
        momento = MomentoJuego.COLOCAR_CARTACOLOCADA;
    }
    
    public void colocarSeleccionarPosicionBatallaDefensa() {
        E.JugadorActual.accionColocarCarta(Ops.IdCartaZonaBSel, Ops.IdCartaManoSel, ZonaBatalla.PosBatalla.DEFCARAABAJO);
        dialogo = Dialogo.CARTA_COLOCADA;
        momento = MomentoJuego.COLOCAR_CARTACOLOCADA;
    }
    
    public void iniciarAtacarCarta() {
        if (E.JugadorActual.puedeAtacarCartas(E.JugadorAnterior)){
            dialogo = Dialogo.SELECCIONAR_ZONABATALLA;
            momento = MomentoJuego.ATACARCARTA_SELECCIONARZONABATALLA;
        }
    }
    
    public void  atacarCartaSeleccionarZonaBatalla(int IdCartaZonaBSel) {
        if (E.JugadorActual.ZBatalla.obtenerCartaxId(IdCartaZonaBSel) != null) {
            Ops.IdCartaZonaBSel = IdCartaZonaBSel;
            dialogo = Dialogo.SELECCIONAR_ZONABATALLA;
            momento = MomentoJuego.ATACARCARTA_SELECCIONARZONABATALLAE;
        }
    }
    
    public void atacarCartaSeleccionarZonaBatallaEnemiga(int IdCartaZonaBSelEnemigo) {
        Ops.IdCartaZonaBSelEnemigo = IdCartaZonaBSelEnemigo;
        Ops.resATK = E.JugadorActual.accionAtacarCarta(E.JugadorAnterior, Ops.IdCartaZonaBSelEnemigo, Ops.IdCartaZonaBSel);
        if (Ops.resATK.resultado == Jugador.ResultadoAtacarCarta.ENEMIGOSINBARRERA) {
            E.terminoSinBarreras();
            dialogo = Dialogo.JUGADORSINCARTASBARRERA;
            momento = MomentoJuego.JUGADORSINCARTASBARRERA;
        } else if (Ops.resATK.resultado != Jugador.ResultadoAtacarCarta.NOSECUMPLENCOND) {
            dialogo = Dialogo.ATAQUE_CARTA_REALIZADO;
            momento = MomentoJuego.ATACARCARTA_ATAQUEREALIZADO;
        }
    }
    
    public void iniciarAtacarBarrera() {
        if (E.JugadorActual.puedeAtacarBarreras(E.JugadorAnterior)){
            dialogo = Dialogo.SELECCIONAR_ZONABATALLA;
            momento = MomentoJuego.ATACARBARRERA_SELECCIONARZONABATALLA;
        }
    }
    
    public void atacarBarreraSeleccionarZonaBatalla(int IdCartaZonaBSel) {
        Ops.IdCartaZonaBSel = IdCartaZonaBSel;
        ResultadoAtacarCarta res = E.JugadorActual.accionAtacarBarrera(E.JugadorAnterior, Ops.IdCartaZonaBSel);
        if (res == Jugador.ResultadoAtacarCarta.BARRERADESTRUIDA) {
            dialogo = Dialogo.ATAQUE_BARRERA_REALIZADO;
            momento = MomentoJuego.ATACARBARRERA_ATAQUEREALIZADO;
        } else if (res == Jugador.ResultadoAtacarCarta.ENEMIGOSINBARRERA) {
            E.terminoSinBarreras();
            dialogo = Dialogo.JUGADORSINCARTASBARRERA;
            momento = MomentoJuego.JUGADORSINCARTASBARRERA;
        }
    }
    
    public void iniciarCambiarPosicionBatalla() {
        if (E.JugadorActual.puedeCambiarPosicion()){
            dialogo = Dialogo.SELECCIONAR_ZONABATALLA;
            momento = MomentoJuego.CAMBIARPOSICIONBATALLA_SELECCIONARZONABATALLA;
        }
    }
    
    public void cambiarPosicionSeleccionarZonaBatalla(int IdCartaZonaBSel) {
        Ops.IdCartaZonaBSel = IdCartaZonaBSel;
        if (E.JugadorActual.accionCambiarPosicionBatalla(Ops.IdCartaZonaBSel)) {
            dialogo = Dialogo.CAMBIODEPOSICION_REALIZADO;
            momento = MomentoJuego.CAMBIARPOSICIONBATALLA_REALIZADO;
        }
    }
    
    public void cambiarTurno() {
    	ResultadoCojerUnaCarta res = E.cambioDeTurno();
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
}

