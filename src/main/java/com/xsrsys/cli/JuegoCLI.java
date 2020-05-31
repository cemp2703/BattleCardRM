package com.xsrsys.cli;

import java.util.Scanner;

import com.xsrsys.service.Juego;
import com.xsrsys.service.Juego.Dialogo;
import com.xsrsys.service.Juego.MomentoJuego;

public class JuegoCLI {
	Juego j;
	
	public void hiloJuego() {
		Scanner sc = new Scanner(System.in);
	     j=new Juego();
	     String resp="";
	     do{
	         System.out.print(j.obtenerStringPantalla());
	         System.out.print(j.obtenerStringDialogo());
	         resp=sc.next();
	         clear();
	         if(!resp.equalsIgnoreCase("cerrar")){
	             recibirRespuestaDialogo(resp);
	         }
	     }while(!resp.equalsIgnoreCase("cerrar"));
	     sc.close();
	}

	
    static void clear(){
        for (int i = 0; i < 50; ++i) System.out.println();
    }
    
	public void recibirRespuestaDialogo(String pResp){
        int id;
        switch(j.momento) {
            case BIENVENIDAJUEGO:
                if (pResp.equalsIgnoreCase("i")) {
                	 j.iniciarJuegoNuevo();
                }
                break;
            case OPCIONESENTURNO:
                if (pResp.equalsIgnoreCase("C")) { //Colocar una carta
                    j.iniciarColocarCarta();
                } else if (pResp.equalsIgnoreCase("A")) {//Atacar a una carta en Zona de batalla
                	j.iniciarAtacarCarta();
                } else if (pResp.equalsIgnoreCase("B")) {//Atacar a una Barrera
                	j.iniciarAtacarBarrera();
                } else if (pResp.equalsIgnoreCase("K")) {//Cambiar posición de batalla
                	j.iniciarCambiarPosicionBatalla();
                } else if (pResp.equalsIgnoreCase("T")) {//Finalizar turno y cambiar turno de jugador
                	j.cambiarTurno();
                }
                break;
            case COLOCAR_SELECCIONARMANO:
                if (pResp.matches("[0-4]")) {
                    id = Integer.parseInt(pResp);
                    j.colocarSeleccionarMano(id);
                } 
                else if (pResp.equalsIgnoreCase("c")) {
                	j.opcionesEnTurno();
                }
                break;
            case COLOCAR_SELECCIONARZONABATALLA:
                if (pResp.matches("[0-2]")) {
                    id = Integer.parseInt(pResp);
                    j.colocarSeleccionarZonaBatalla(id);
                } else if (pResp.equalsIgnoreCase("c")) {
                    j.dialogo = Dialogo.SELECCIONAR_MANO;
                    j.momento = MomentoJuego.COLOCAR_SELECCIONARMANO;
                }
                break;
            case COLOCAR_SELECCIONARPOSICIONBATALLA:
                if (pResp.equalsIgnoreCase("a")) {//ATAQUE
                	j.colocarSeleccionarPosicionBatallaAtaque();
                } else if (pResp.equalsIgnoreCase("d")) {//DEF
                	j.colocarSeleccionarPosicionBatallaDefensa();
                } else if (pResp.equalsIgnoreCase("c")) {
                    j.dialogo = Dialogo.SELECCIONAR_ZONABATALLA;
                    j.momento = MomentoJuego.COLOCAR_SELECCIONARZONABATALLA;
                }
                break;
            case ATACARCARTA_SELECCIONARZONABATALLA:
                if (pResp.matches("[0-2]")) {
                    id = Integer.parseInt(pResp);
                    j.atacarCartaSeleccionarZonaBatalla(id);
                }
                else if (pResp.equalsIgnoreCase("c")) {
                	j.opcionesEnTurno();
                }
                break;
            case ATACARCARTA_SELECCIONARZONABATALLAE:
                if (pResp.matches("[0-2]")) {
                    id = Integer.parseInt(pResp);
                    j.atacarCartaSeleccionarZonaBatallaEnemiga(id);
                }
                else if (pResp.equalsIgnoreCase("c")) {
                    j.dialogo = Dialogo.SELECCIONAR_ZONABATALLA;
                    j.momento = MomentoJuego.ATACARCARTA_SELECCIONARZONABATALLA;
                }
                break;
            case COLOCAR_CARTACOLOCADA:
            case ATACARCARTA_ATAQUEREALIZADO:
            case ATACARBARRERA_ATAQUEREALIZADO:
            case CAMBIARPOSICIONBATALLA_REALIZADO:
                if (pResp.equalsIgnoreCase("c")) {
                	j.opcionesEnTurno();
                }
                break;
            case ATACARBARRERA_SELECCIONARZONABATALLA:
                if (pResp.matches("[0-2]")) {
                    id = Integer.parseInt(pResp);
                    j.atacarBarreraSeleccionarZonaBatalla(id);
                }
                else if (pResp.equalsIgnoreCase("c")) {
                	j.opcionesEnTurno();
                }
                break;
            case CAMBIARPOSICIONBATALLA_SELECCIONARZONABATALLA:
                if (pResp.matches("[0-2]")) {
                    id = Integer.parseInt(pResp);
                    j.cambiarPosicionSeleccionarZonaBatalla(id);
                }
                else if (pResp.equalsIgnoreCase("c")) {
                	j.opcionesEnTurno();
                }
                break;
            case JUGADORSINCARTASMAZO:
            case JUGADORSINCARTASBARRERA:
                if (pResp.equalsIgnoreCase("c")) {
                	j.pantallaFinDelJuego();
                }
                break;
            case FINDEJUEGO:
                if (pResp.equalsIgnoreCase("c")) {
                	j.pantallaBienvenida();
                }
                break;
        }
    }
}
