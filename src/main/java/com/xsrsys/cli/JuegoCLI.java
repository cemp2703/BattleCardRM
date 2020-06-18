package com.xsrsys.cli;

import java.util.Scanner;

import com.xsrsys.service.Juego;
import com.xsrsys.service.Juego.DialogoJuego;
import com.xsrsys.service.Juego.Momento;

public class JuegoCLI {
	Juego j;
	
	public void hiloJuego() {
		Scanner sc = new Scanner(System.in);
	     j=new Juego();
	     String opcion="";
	     do{
	         System.out.print(obtenerStringPantalla());
	         opcion=sc.next();
	         limpiarPantalla();
	         if(!opcion.equalsIgnoreCase("cerrar")){
	             procesarOpcion(opcion);
	         }
	     }while(!opcion.equalsIgnoreCase("cerrar"));
	     sc.close();
	}

	
    void limpiarPantalla(){
        for (int i = 0; i < 50; ++i) System.out.println();
    }
    
	void procesarOpcion(String opcion){
        int id;
        switch(j.momento) {
            case BIENVENIDAJUEGO:
                if (opcion.equalsIgnoreCase("i")) {
                  	 j.iniciarJuegoNuevo();
                  }
                break;
            case OPCIONESENTURNO:
                if (opcion.equalsIgnoreCase("C")) { //Colocar una carta
                    j.iniciarColocarCarta();
                } else if (opcion.equalsIgnoreCase("A")) {//Atacar a una carta en Zona de batalla
                	j.iniciarAtacarCarta();
                } else if (opcion.equalsIgnoreCase("B")) {//Atacar a una Barrera
                	j.iniciarAtacarBarrera();
                } else if (opcion.equalsIgnoreCase("K")) {//Cambiar posición de batalla
                	j.iniciarCambiarPosicionBatalla();
                } else if (opcion.equalsIgnoreCase("T")) {//Finalizar turno y cambiar turno de jugador
                	j.cambiarTurno();
                }
                break;
            case COLOCAR_SELECCIONARMANO:
                if (opcion.matches("[0-4]")) {
                    id = Integer.parseInt(opcion);
                    j.colocarSeleccionarMano(id);
                } 
                else if (opcion.equalsIgnoreCase("c")) {
                	j.opcionesEnTurno();
                }
                break;
            case COLOCAR_SELECCIONARZONABATALLA:
                if (opcion.matches("[0-2]")) {
                    id = Integer.parseInt(opcion);
                    j.colocarSeleccionarZonaBatalla(id);
                } else if (opcion.equalsIgnoreCase("c")) {
                    j.dialogoJuego = DialogoJuego.SELECCIONAR_MANO;
                    j.momento = Momento.COLOCAR_SELECCIONARMANO;
                }
                break;
            case COLOCAR_SELECCIONARPOSICIONBATALLA:
                if (opcion.equalsIgnoreCase("a")) {//ATAQUE
                	j.colocarSeleccionarPosicionBatallaAtaque();
                } else if (opcion.equalsIgnoreCase("d")) {//DEF
                	j.colocarSeleccionarPosicionBatallaDefensa();
                } else if (opcion.equalsIgnoreCase("c")) {
                    j.dialogoJuego = DialogoJuego.SELECCIONAR_ZONABATALLA;
                    j.momento = Momento.COLOCAR_SELECCIONARZONABATALLA;
                }
                break;
            case ATACARCARTA_SELECCIONARZONABATALLA:
                if (opcion.matches("[0-2]")) {
                    id = Integer.parseInt(opcion);
                    j.atacarCartaSeleccionarZonaBatalla(id);
                }
                else if (opcion.equalsIgnoreCase("c")) {
                	j.opcionesEnTurno();
                }
                break;
            case ATACARCARTA_SELECCIONARZONABATALLAE:
                if (opcion.matches("[0-2]")) {
                    id = Integer.parseInt(opcion);
                    j.atacarCartaSeleccionarZonaBatallaEnemiga(id);
                }
                else if (opcion.equalsIgnoreCase("c")) {
                    j.dialogoJuego = DialogoJuego.SELECCIONAR_ZONABATALLA;
                    j.momento = Momento.ATACARCARTA_SELECCIONARZONABATALLA;
                }
                break;
            case COLOCAR_CARTACOLOCADA:
            case ATACARCARTA_ATAQUEREALIZADO:
            case ATACARBARRERA_ATAQUEREALIZADO:
            case CAMBIARPOSICIONBATALLA_REALIZADO:
                if (opcion.equalsIgnoreCase("c")) {
                	j.opcionesEnTurno();
                }
                break;
            case ATACARBARRERA_SELECCIONARZONABATALLA:
                if (opcion.matches("[0-2]")) {
                    id = Integer.parseInt(opcion);
                    j.atacarBarreraSeleccionarZonaBatalla(id);
                }
                else if (opcion.equalsIgnoreCase("c")) {
                	j.opcionesEnTurno();
                }
                break;
            case CAMBIARPOSICIONBATALLA_SELECCIONARZONABATALLA:
                if (opcion.matches("[0-2]")) {
                    id = Integer.parseInt(opcion);
                    j.cambiarPosicionSeleccionarZonaBatalla(id);
                }
                else if (opcion.equalsIgnoreCase("c")) {
                	j.opcionesEnTurno();
                }
                break;
            case JUGADORSINCARTASMAZO:
            case JUGADORSINCARTASBARRERA:
                if (opcion.equalsIgnoreCase("c")) {
                	j.finalizarJuego();
                }
                break;
            case FINDEJUEGO:
                if (opcion.equalsIgnoreCase("c")) {
                	j.pantallaBienvenida();
                }
                break;
        }
    }
	
    String obtenerStringDialogoJuego(){
        String resp="";
    	switch (j.dialogoJuego){
            case OPCIONESENTURNO://Opciones en turno
                resp+="Seleccion accion a realizar:\n";
                if(j.tablero.jugadorActual.puedeColocarCartaEnZB())
                    resp+="(C) Colocar una carta\n";
                if(j.tablero.jugadorActual.puedeAtacarCartas(j.tablero.jugadorAnterior))
                	resp+="(A) Atacar a una carta en Zona de batalla\n";
                if(j.tablero.jugadorActual.puedeAtacarBarreras(j.tablero.jugadorAnterior))
                    resp+="(B) Atacar a una Barrera\n";
                if(j.tablero.jugadorActual.puedeCambiarPosicion())
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
            case SELECCIONAR_POSICIONBATALLAE://Seleccionando carta en zona de batalla enemiga
                resp+="Seleccionando carta en Zona de batalla Enemiga\n";
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
            case ATAQUE_CARTA_REALIZADO://Ataque Carta Realizado
            case ATAQUE_BARRERA_REALIZADO://Ataque barrera realizado
            case CAMBIODEPOSICION_REALIZADO://Cambiar Posición - Cambio de posición realizado
            case JUGADORSINCARTASMAZO://Fin de Juego - Jugador Actual se quedó sin cartas en mazo
            case JUGADORSINCARTASBARRERA://Fin de Juego - Jugador Contrario se quedó sin barreras
            	resp+="Ingrese (C) para continuar\n";
                break;
        }
    	return resp;
    }

    String obtenerStringPantalla(){
    	String resp="";
    	switch(j.pantalla){
        case BIENVENIDAJUEGO:
        	resp+="******************\n";
        	resp+="   Battle Cards   \n";
        	resp+="******************\n";
        	resp+="Ingrese:\n";
        	resp+="(I) Iniciar Juego\n";
        	resp+="(Cerrar) Cerrar el Juego (En cualquier momento)\n";
            break;
        case JUEGO:
            resp+=j.tablero.obtenerStringEstado();
            resp+=obtenerStringDialogoJuego();
            break;
        case FINDEJUEGO:
        	resp+="+++++++++++++++++++++++++++++++++++++++++++++\n";
        	resp+="+++++++++++++++++++++++++++++++++++++++++++++\n";
        	resp+="                                             \n";
        	resp+=" Victoria: "+j.jugadorVictorioso.getNombre()+"\n";
        	resp+="                                            \n";
        	resp+="+++++++++++++++++++++++++++++++++++++++++++++\n";
        	resp+="+++++++++++++++++++++++++++++++++++++++++++++\n";
        	resp+="Ingrese (C) para Volver al Inicio\n";
    	}
    	return resp;
    }
}
