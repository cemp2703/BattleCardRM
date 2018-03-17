package Consola;

import Clases.*;
import Juego.Operaciones;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.text.Format;
import java.util.FormatFlagsConversionMismatchException;
import java.util.Scanner;

public class MainConsola {

    public enum Pantalla {
        INICIAL,//Pantalla de Bienvenida
        JUEGO,//Pantalla de Juego
        FINDEJUEGO//Pantalla de Finde Juego
    }
    public enum Dialogo {
        INICIAL,//Pantalla de Bienvenida

        ACCIONES,//Acciones de Juego
            //ColocarCarta
            SELECCIONARMANO,//Seleccionar Carta en Mano
            SELECCIONARZONABATALLA,//Seleecionar posicion en zona de batalla para colocar carta
            ELEGIRPOSICIONBATALLA,//Elegir posicion de batalla
            CARTACOLOCADA,
            //AtacarCarta
            SELECCIONARZONABATALLAATK,//Seleccionar Carta en zona de batalla
            SELECCIONARZONABATALLAE,//Seleccionar Carta en zona de batalla enemiga
            ATAQUEREALIZADO,//Ataque realizado
            //AtacarBarrera
            SELECCIONARZONABATALLAB,
            ATAQUEBARRERA,
            //CambioDePosicionDeBatalla
            SELECCIONARZONABATALLAC,
            CAMBIODEPOSICIONREALIZADO,

        //FINALES
        JUGADORSINCARTASBARRERA,
        JUGADORSINCARTASMAZO,

        FINDEJUEGO //Pantalla Final
    }

    static Estado E;
    static Operaciones Ops;

    static Pantalla pantalla;
    static Dialogo dialogo;

    public static void main(String[] args) throws IOException, InterruptedException{
        Scanner sc = new Scanner(System.in);
        String resp="";
        pantalla = Pantalla.INICIAL;
        dialogo = Dialogo.INICIAL;
        do{
            imprimirPantalla();
            imprimirDialogo();
            resp=sc.next();
            clear();
            if(!resp.equalsIgnoreCase("cerrar")){
                recibeRespuesta(resp);
            }
        }while(!resp.equalsIgnoreCase("cerrar"));
    }

    static void  Inicializando(){
        E = new Estado(new Jugador("Jugador1"),new Jugador("Jugador2"));
        Ops = new Operaciones();
        Ops.repartirCartas(E.JugadorActual);
        Ops.repartirCartas(E.JugadorAnterior);
        E.JugadorActual.accionIniciarTurno();
    }

    static void clear(){
        for (int i = 0; i < 50; ++i) System.out.println();
    }

    static void  imprimirPantalla() {
        switch(pantalla){
            case INICIAL:
                System.out.println("******************");
                System.out.println("   Battle Cards   ");
                System.out.println("******************");
                break;
            case JUEGO:
                System.out.print("TURNO: "+E.JugadorActual.getNombre()+"\n");
                imprimeEstado(E);
                break;
            case FINDEJUEGO:
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println("                                                                             ");
                System.out.println("                            Victoria                                        ");
                System.out.println("                         "+E.JugadorVictorioso.getNombre()                   );
                System.out.println("                                                                            ");
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }
    }

    static void imprimirDialogo(){
        switch (dialogo){
            case INICIAL:
                System.out.println("Ingrese:");
                System.out.println("(I) Iniciar Juego");
                System.out.println("(Cerrar) Cerrar el Juego (En cualquier momento)");
                break;
            case ACCIONES://dialogo acciones
                System.out.println("Seleccion accion a realizar:");
                if(E.JugadorActual.puedeColocarCarta())
                    System.out.println("(C) Colocar una carta");
                if(E.JugadorActual.puedeAtacarAUnaCarta(E.JugadorAnterior))
                    System.out.println("(A) Atacar a una carta en Zona de batalla");
                if(E.JugadorActual.puedeAtacarAUnaBarrera(E.JugadorAnterior))
                    System.out.println("(B) Atacar a una Barrera");
                if(E.JugadorActual.puedecambiarposicion())
                    System.out.println("(K) Cambiar posición de batalla");
                System.out.println("(T) Terminar Turno");
                System.out.println("Accion: ");
                break;
            case SELECCIONARMANO://Colocar carta - Seleccionando carta en mano
                System.out.println("Seleccionando carta en Mano");
                System.out.println("Ingrese la posicion de (0 - 4) de la carta en su mano: ");
                System.out.println("O Ingrese (C) Cancelar para volver");
                break;
            case SELECCIONARZONABATALLA://Colocar carta - Seleccionando carta en zona de batalla
                System.out.println("Colocando Carta en Zona de batalla");
                System.out.println("Ingrese la posicion de (0 - 2) de la zona de batalla: ");
                System.out.println("O Ingrese (C) Cancelar para volver");
                break;
            case ELEGIRPOSICIONBATALLA://Colocar carta - Elegir posición de batalla
                System.out.println("Elegir posición de batalla");
                System.out.println("Ingrese (A) para Ataque: ");
                System.out.println("Ingrese (D) para Defensa: ");
                System.out.println("O Ingrese (C) Cancelar para volver");
                break;
            case CARTACOLOCADA://Colocar carta - Carta Colocada
                System.out.println("Carta Colocada!!");
                System.out.println("Ingrese (C) para continuar");
                break;
            case SELECCIONARZONABATALLAATK://Atacar carta - Seleccionando carta en zona de batalla
                System.out.println("Seleccionando carta en Zona de Batalla");
                System.out.println("Ingrese la posicion de (0 - 2) de la zona de batalla: ");
                System.out.println("O Ingrese (C) Cancelar para volver");
                break;
            case SELECCIONARZONABATALLAE://Atacar carta - Seleccionando carta en zona de batalla enemiga
                System.out.println("Seleccionando carta en Zona de Batalla Enemiga");
                System.out.println("Ingrese la posicion de (0 - 2) de la zona de batalla enemiga: ");
                System.out.println("O Ingrese (C) Cancelar para volver");
                break;
            case ATAQUEREALIZADO://Colocar carta - Ataque Realizado
                System.out.println("Ataque Realizado!!");
                if(Ops.resATK.resultado == Jugador.RESULTADOATACARCARTA.GANAATACANTE)
                    System.out.println("Victoria!!");
                else if(Ops.resATK.resultado == Jugador.RESULTADOATACARCARTA.PIERDEATACANTE)
                    System.out.println("Derrota!!");
                else if(Ops.resATK.resultado == Jugador.RESULTADOATACARCARTA.EMPATE)
                    System.out.println("Empate!!");
                System.out.println("     Tu Carta     |        Enemigo         ");
                System.out.println(
                        Ops.resATK.valorCartaAtacante+" ("+imprimirElementoUnicode(Ops.resATK.elementoCartaAtacante)+") " +
                        "(Al Ataque)"+
                        "  |   " +
                        Ops.resATK.valorCartaAtacada+" ("+imprimirElementoUnicode(Ops.resATK.elementoCartaAtacada)+") "+
                        (Ops.resATK.posicionCartaAtacada == ZonaBatalla.POSCARTA.ATAQUE? "(Al Ataque)" : "(A la Defensa) ")
                );
                if(Ops.resATK.barrera == Jugador.RESULTADOCARTA.DOWN)
                    System.out.println("Barrera enemiga destruida");
                if(Ops.resATK.cartaAtacante == Jugador.RESULTADOCARTA.DOWN)
                    System.out.println("Tu Carta en Zona de Batalla ha sido destruida");
                if(Ops.resATK.cartaAtacada == Jugador.RESULTADOCARTA.DOWN)
                    System.out.println("Carta enemiga en Zona de Batalla destruida");
                System.out.println("Ingrese (C) para continuar");
                break;
            case SELECCIONARZONABATALLAB://Atacar barrera - Seleccionando carta en zona de batalla
                System.out.println("Seleccionando carta en Zona de Batalla");
                System.out.println("Ingrese la posicion de (0 - 2) de la zona de batalla: ");
                System.out.println("O Ingrese (C) Cancelar para volver");
                break;
            case ATAQUEBARRERA://Atacar barrera - Ataque Realizado
                System.out.println("Ataque Realizado!!");
                System.out.println("Barrera Destruida");
                System.out.println("Ingrese (C) para continuar");
                break;
            case SELECCIONARZONABATALLAC://Cambiar Posición - Seleccionando carta en zona de batalla
                System.out.println("Seleccionando carta en Zona de Batalla");
                System.out.println("Ingrese la posicion de (0 - 2) de la zona de batalla: ");
                System.out.println("O Ingrese (C) Cancelar para volver");
                break;
            case CAMBIODEPOSICIONREALIZADO://Cambiar Posición - Cambio de posición realizado
                System.out.println("Cambio de Posición Realizado!!");
                System.out.println("Ingrese (C) para continuar");
                break;
            case JUGADORSINCARTASMAZO://Fin de Juego - Jugador Actual se quedó sin cartas en mazo
                System.out.println("Fin del Juego!!!");
                System.out.println(E.JugadorActual.getNombre()+" se quedó sin cartas en el mazo!!");
                System.out.println("Ingrese (C) para continuar");
                break;
            case JUGADORSINCARTASBARRERA://Fin de Juego - Jugador Contrario se quedó sin barreras
                System.out.println("Fin del Juego!!!");
                System.out.println(E.JugadorAnterior.getNombre()+" se quedó sin barreras!!");
                System.out.println("Ingrese (C) para continuar");
                break;
            case FINDEJUEGO:
                System.out.println("Fin del Juego!!!");
                System.out.println("");
                System.out.println("Ingrese (C) para Volver al Inicio");
                break;
        }
    }

    static void recibeRespuesta(String pResp){
        int id;
        switch(dialogo) {
            case INICIAL:
                if (pResp.equalsIgnoreCase("i")) {
                    Inicializando();
                    pantalla = Pantalla.JUEGO;
                }
                break;
            case ACCIONES:
                if (pResp.equalsIgnoreCase("C")) {
                    if (E.JugadorActual.puedeColocarCarta())
                        dialogo = Dialogo.SELECCIONARMANO;
                } else if (pResp.equalsIgnoreCase("A")) {
                    if (E.JugadorActual.puedeAtacarAUnaCarta(E.JugadorAnterior))
                        dialogo = Dialogo.SELECCIONARZONABATALLAATK;
                } else if (pResp.equalsIgnoreCase("B")) {
                    if (E.JugadorActual.puedeAtacarAUnaBarrera(E.JugadorAnterior))
                        dialogo = Dialogo.SELECCIONARZONABATALLAB;
                } else if (pResp.equalsIgnoreCase("K")) {
                    if (E.JugadorActual.puedecambiarposicion())
                        dialogo = Dialogo.SELECCIONARZONABATALLAC;
                } else if (pResp.equalsIgnoreCase("T")) {
                    int res = E.cambioDeTurno();
                    if (res == Jugador.RESULTADOCOJERUNACARTA.EXITO || res == Jugador.RESULTADOCOJERUNACARTA.MANOLLENA)
                        dialogo = Dialogo.ACCIONES;
                    else if (res == Jugador.RESULTADOCOJERUNACARTA.DECKVACIO) {
                        dialogo = Dialogo.JUGADORSINCARTASBARRERA;
                    }
                }
                break;
            case SELECCIONARMANO:
                if (pResp.matches("[0-4]")) {
                    id = Integer.parseInt(pResp);
                    if (E.JugadorActual.Mano.obtenerCartaxId(id) != null) {
                        Ops.IdCartaManoSel = id;
                        dialogo = Dialogo.SELECCIONARZONABATALLA;
                    }
                } else if (pResp.equalsIgnoreCase("c")) {
                    dialogo = Dialogo.ACCIONES;
                }
                break;
            case SELECCIONARZONABATALLA:
                if (pResp.matches("[0-2]")) {
                    id = Integer.parseInt(pResp);
                    if (E.JugadorActual.ZBatalla.obtenerCartaxId(id) == null) {
                        Ops.IdCartaZonaBSel = id;
                        dialogo = Dialogo.CARTACOLOCADA;
                    }
                } else if (pResp.equalsIgnoreCase("c")) {
                    dialogo = Dialogo.SELECCIONARMANO;
                }
                break;
            case ELEGIRPOSICIONBATALLA:
                if (pResp.equalsIgnoreCase("a")) {
                    E.JugadorActual.accionColocarCarta(Ops.IdCartaZonaBSel, Ops.IdCartaManoSel, ZonaBatalla.POSCARTA.ATAQUE);
                    dialogo = Dialogo.CARTACOLOCADA;
                } else if (pResp.equalsIgnoreCase("d")) {
                    E.JugadorActual.accionColocarCarta(Ops.IdCartaZonaBSel, Ops.IdCartaManoSel, ZonaBatalla.POSCARTA.DEFCARAABAJO);
                    dialogo = Dialogo.CARTACOLOCADA;
                } else if (pResp.equalsIgnoreCase("c")) {
                    dialogo = Dialogo.SELECCIONARZONABATALLA;
                }
                break;
            case CARTACOLOCADA:
                if (pResp.equalsIgnoreCase("c")) {
                    dialogo = Dialogo.ACCIONES;
                }
                break;
            case SELECCIONARZONABATALLAATK:
                if (pResp.matches("[0-2]")) {
                    id = Integer.parseInt(pResp);
                    if (E.JugadorActual.ZBatalla.obtenerCartaxId(id) != null) {
                        Ops.IdCartaZonaBSel = id;
                        dialogo = Dialogo.SELECCIONARZONABATALLAE;
                    }
                } else if (pResp.equalsIgnoreCase("c")) {
                    dialogo = Dialogo.ACCIONES;
                }
                break;
            case SELECCIONARZONABATALLAE:
                if (pResp.matches("[0-2]")) {
                    id = Integer.parseInt(pResp);
                    Ops.IdCartaZonaBSelEnemigo = id;
                    Ops.resATK = E.JugadorActual.accionAtacarCarta(E.JugadorAnterior, Ops.IdCartaZonaBSelEnemigo, Ops.IdCartaZonaBSel);
                    if (Ops.resATK.resultado == Jugador.RESULTADOATACARCARTA.ENEMIGOSINBARRERA) {
                        E.terminoSinBarreras();
                        dialogo = Dialogo.JUGADORSINCARTASBARRERA;
                    } else if (Ops.resATK.resultado != Jugador.RESULTADOATACARCARTA.NOSECUMPLENCOND) {
                        dialogo = Dialogo.ATAQUEREALIZADO;
                    }
                } else if (pResp.equalsIgnoreCase("c")) {
                    dialogo = Dialogo.SELECCIONARZONABATALLAATK;
                }
                break;
            case ATAQUEREALIZADO:
                if (pResp.equalsIgnoreCase("c")) {
                    dialogo = Dialogo.ACCIONES;
                }
                break;
            case SELECCIONARZONABATALLAB:
                if (pResp.matches("[0-2]")) {
                    id = Integer.parseInt(pResp);
                    Ops.IdCartaZonaBSel = id;
                    int res = E.JugadorActual.accionAtacarBarrera(E.JugadorAnterior, Ops.IdCartaZonaBSel);
                    if (res == Jugador.RESULTADOATACARBARRERA.EXITO) {
                        dialogo = Dialogo.ATAQUEBARRERA;
                    } else if (res == Jugador.RESULTADOATACARBARRERA.SINBARRERAS) {
                        E.terminoSinBarreras();
                        dialogo = Dialogo.JUGADORSINCARTASBARRERA;
                    }
                } else if (pResp.equalsIgnoreCase("c")) {
                    dialogo = Dialogo.ACCIONES;
                }
                break;
            case ATAQUEBARRERA:
                if (pResp.equalsIgnoreCase("c")) {
                    dialogo = Dialogo.ACCIONES;
                }
                break;
            case SELECCIONARZONABATALLAC:
                if (pResp.matches("[0-2]")) {
                    id = Integer.parseInt(pResp);
                    Ops.IdCartaZonaBSel = id;
                    if (E.JugadorActual.accionCambiarPosicionBatalla(Ops.IdCartaZonaBSel)) {
                        dialogo = Dialogo.CAMBIODEPOSICIONREALIZADO;
                    }
                } else if (pResp.equalsIgnoreCase("c")) {
                    dialogo = Dialogo.ACCIONES;
                }
                break;
            case CAMBIODEPOSICIONREALIZADO:
                if (pResp.equalsIgnoreCase("c")) {
                    dialogo = Dialogo.ACCIONES;
                }
                break;
            case JUGADORSINCARTASBARRERA:
                if (pResp.equalsIgnoreCase("c")) {
                    pantalla = Pantalla.FINDEJUEGO;
                    dialogo = Dialogo.ACCIONES;
                }
                break;
            case JUGADORSINCARTASMAZO:
                if (pResp.equalsIgnoreCase("c")) {
                    pantalla = Pantalla.FINDEJUEGO;
                    dialogo = Dialogo.ACCIONES;
                }
                break;
            case FINDEJUEGO:
                if (pResp.equalsIgnoreCase("c")) {
                    pantalla = Pantalla.INICIAL;
                    dialogo = Dialogo.INICIAL;
                }
                break;
        }
    }

    static void imprimeEstado(Estado E){
        System.out.println("*****************************************************************************");
        System.out.println("Jugador 1");
        System.out.println("Mano");
        for(int i = 0; i < Mano.MAXMANOCARDS; i++){
            if(E.Jugador1.Mano.obtenerCartaxId(i) != null)
                System.out.print(E.Jugador1.Mano.obtenerCartaxId(i).getValor() + " " +
                        imprimirElementoUnicode(E.Jugador1.Mano.obtenerCartaxId(i).getElemento()) + " | ");
            else
                System.out.print("VACIO | ");
        }
        System.out.println();
        System.out.println("Barrera");
        for(int i=0;i < Barrera.MAXBARRERACARDS;i++){
            if(E.Jugador1.Barrera.obtenerCartaxId(i) != null)
                System.out.print("BARRERA| ");
            else
                System.out.print("VACIO  | ");
        }
        System.out.println();
        System.out.println("ZonaBatalla");
        for(int i=0;i < ZonaBatalla.MAXZONABATALLACARDS;i++){
            if(E.Jugador1.ZBatalla.obtenerCartaxId(i) != null)
                System.out.print(E.Jugador1.ZBatalla.obtenerCartaxId(i).getValor()+ " " +
                        imprimirElementoUnicode(E.Jugador1.ZBatalla.obtenerCartaxId(i).getElemento()) + " " +
                        ZonaBatalla.devuelveposcarta(E.Jugador1.ZBatalla.poscarta[i]) + " | ");
            else
                System.out.print("VACIO | ");
        }
        System.out.println();

        System.out.println("Jugador 2");
        System.out.println("Mano");
        for(int i=0;i < Mano.MAXMANOCARDS;i++){
            if(E.Jugador2.Mano.obtenerCartaxId(i) != null)
                System.out.print(E.Jugador2.Mano.obtenerCartaxId(i).getValor() + " " +
                        imprimirElementoUnicode(E.Jugador2.Mano.obtenerCartaxId(i).getElemento()) + " | ");
            else
                System.out.print("VACIO | ");
        }
        System.out.println();
        System.out.println("Barrera");
        for(int i=0;i < Barrera.MAXBARRERACARDS;i++){
            if(E.Jugador2.Barrera.obtenerCartaxId(i) != null)
                System.out.print("BARRERA| ");
            else
                System.out.print("VACIO | ");
        }
        System.out.println();
        System.out.println("ZonaBatalla");
        for(int i=0;i < ZonaBatalla.MAXZONABATALLACARDS;i++){
            if(E.Jugador2.ZBatalla.obtenerCartaxId(i) != null)
                System.out.print(E.Jugador2.ZBatalla.obtenerCartaxId(i).getValor() + " " +
                        imprimirElementoUnicode(E.Jugador2.ZBatalla.obtenerCartaxId(i).getElemento()) + " " +
                        ZonaBatalla.devuelveposcarta(E.Jugador2.ZBatalla.poscarta[i]) + " | ");
            else
                System.out.print("VACIO | ");
        }

        System.out.println();
        System.out.println("*****************************************************************************");
        System.out.println();
        System.out.println();
    }

    static String imprimirElementoUnicode(int n){
        String elemento = "";
        switch(n){
            case 0: elemento =  "\u2665"; break;
            case 1: elemento = "\u2666"; break;
            case 2: elemento = "\u2663"; break;
            case 3: elemento = "\u2660"; break;
        }
        return elemento;
    }

}
