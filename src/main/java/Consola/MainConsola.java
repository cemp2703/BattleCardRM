package Consola;

import Clases.*;
import Juego.Operaciones;

import java.io.IOException;
import java.util.Scanner;

public class MainConsola {

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

    static Estado E;
    static Operaciones Ops;

    static Pantalla pantalla;
    static Dialogo dialogo;
    static MomentoJuego momento;

    public static void main(String[] args) throws IOException, InterruptedException{
        Scanner sc = new Scanner(System.in);
        String resp="";
        pantalla = Pantalla.BIENVENIDAJUEGO;
        dialogo = Dialogo.BIENVENIDAJUEGO;
        momento = MomentoJuego.BIENVENIDAJUEGO;
        do{
            imprimirPantalla();
            imprimirDialogo();
            resp=sc.next();
            clear();
            if(!resp.equalsIgnoreCase("cerrar")){
                recibeRespuestaDialogo(resp);
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
            case BIENVENIDAJUEGO:
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
            case BIENVENIDAJUEGO:
                System.out.println("Ingrese:");
                System.out.println("(I) Iniciar Juego");
                System.out.println("(Cerrar) Cerrar el Juego (En cualquier momento)");

                break;
            case OPCIONESENTURNO://Opciones en turno
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
            case SELECCIONAR_MANO://Seleccionando carta en mano
                System.out.println("Seleccionando carta en Mano");
                System.out.println("Ingrese la posicion de (0 - 4) de la carta en su mano: ");
                System.out.println("O Ingrese (C) Cancelar para volver");
                break;
            case SELECCIONAR_ZONABATALLA://Seleccionando carta en zona de batalla
                System.out.println("Seleccionando carta en Zona de batalla");
                System.out.println("Ingrese la posicion de (0 - 2) de la zona de batalla: ");
                System.out.println("O Ingrese (C) Cancelar para volver");
                break;
            case SELECCIONAR_POSICIONBATALLA://Elegir posición de batalla
                System.out.println("Elegir posición de batalla");
                System.out.println("Ingrese (A) para Ataque: ");
                System.out.println("Ingrese (D) para Defensa: ");
                System.out.println("O Ingrese (C) Cancelar para volver");
                break;
            case CARTA_COLOCADA://Colocar carta -
                System.out.println("Carta Colocada!!");
                System.out.println("Ingrese (C) para continuar");
                break;
            case ATAQUE_CARTA_REALIZADO://Ataque Carta Realizado
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
                        (Ops.resATK.posicionCartaAtacada == ZonaBatalla.POSBATALLA.ATAQUE? "(Al Ataque)" : "(A la Defensa) ")
                );
                if(Ops.resATK.barrera == Jugador.RESULTADOCARTA.DOWN)
                    System.out.println("Barrera enemiga destruida");
                if(Ops.resATK.cartaAtacante == Jugador.RESULTADOCARTA.DOWN)
                    System.out.println("Tu Carta en Zona de Batalla ha sido destruida");
                if(Ops.resATK.cartaAtacada == Jugador.RESULTADOCARTA.DOWN)
                    System.out.println("Carta enemiga en Zona de Batalla destruida");
                System.out.println("Ingrese (C) para continuar");
                break;
            case ATAQUE_BARRERA_REALIZADO://Ataque barrera realizado
                System.out.println("Ataque Realizado!!");
                System.out.println("Barrera Destruida");
                System.out.println("Ingrese (C) para continuar");
                break;
            case CAMBIODEPOSICION_REALIZADO://Cambiar Posición - Cambio de posición realizado
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

    static void recibeRespuestaDialogo(String pResp){
        int id;
        switch(momento) {
            case BIENVENIDAJUEGO:
                if (pResp.equalsIgnoreCase("i")) {
                    Inicializando();
                    pantalla = Pantalla.JUEGO;
                    dialogo = Dialogo.OPCIONESENTURNO;
                    momento = MomentoJuego.OPCIONESENTURNO;
                }
                break;
            case OPCIONESENTURNO:
                if (pResp.equalsIgnoreCase("C")) { //Colocar una carta
                    if (E.JugadorActual.puedeColocarCarta()){
                        dialogo = Dialogo.SELECCIONAR_MANO;
                        momento = MomentoJuego.COLOCAR_SELECCIONARMANO;
                    }
                } else if (pResp.equalsIgnoreCase("A")) {//Atacar a una carta en Zona de batalla
                    if (E.JugadorActual.puedeAtacarAUnaCarta(E.JugadorAnterior)){
                        dialogo = Dialogo.SELECCIONAR_ZONABATALLA;
                        momento = MomentoJuego.ATACARCARTA_SELECCIONARZONABATALLA;
                    }

                } else if (pResp.equalsIgnoreCase("B")) {//Atacar a una Barrera
                    if (E.JugadorActual.puedeAtacarAUnaBarrera(E.JugadorAnterior)){
                        dialogo = Dialogo.SELECCIONAR_ZONABATALLA;
                        momento = MomentoJuego.ATACARBARRERA_SELECCIONARZONABATALLA;
                    }
                } else if (pResp.equalsIgnoreCase("K")) {//Cambiar posición de batalla
                    if (E.JugadorActual.puedecambiarposicion()){
                        dialogo = Dialogo.SELECCIONAR_ZONABATALLA;
                        momento = MomentoJuego.CAMBIARPOSICIONBATALLA_SELECCIONARZONABATALLA;
                    }
                } else if (pResp.equalsIgnoreCase("T")) {
                    int res = E.cambioDeTurno();
                    if (res == Jugador.RESULTADOCOJERUNACARTA.EXITO || res == Jugador.RESULTADOCOJERUNACARTA.MANOLLENA) {
                        dialogo = Dialogo.OPCIONESENTURNO;
                        momento = MomentoJuego.OPCIONESENTURNO;
                    }
                    else if (res == Jugador.RESULTADOCOJERUNACARTA.DECKVACIO) {
                        dialogo = Dialogo.JUGADORSINCARTASBARRERA;
                        momento = MomentoJuego.JUGADORSINCARTASBARRERA;
                    }
                }
                break;
            case COLOCAR_SELECCIONARMANO:
                if (pResp.matches("[0-4]")) {
                    id = Integer.parseInt(pResp);
                    if (E.JugadorActual.Mano.obtenerCartaxId(id) != null) {
                        Ops.IdCartaManoSel = id;
                        dialogo = Dialogo.SELECCIONAR_ZONABATALLA;
                        momento = MomentoJuego.COLOCAR_SELECCIONARZONABATALLA;
                    }
                } else if (pResp.equalsIgnoreCase("c")) {
                    dialogo = Dialogo.OPCIONESENTURNO;
                    momento = MomentoJuego.OPCIONESENTURNO;
                }
                break;
            case COLOCAR_SELECCIONARZONABATALLA:
                if (pResp.matches("[0-2]")) {
                    id = Integer.parseInt(pResp);
                    if (E.JugadorActual.ZBatalla.obtenerCartaxId(id) == null) {
                        Ops.IdCartaZonaBSel = id;
                        dialogo = Dialogo.SELECCIONAR_POSICIONBATALLA;
                        momento = MomentoJuego.COLOCAR_SELECCIONARPOSICIONBATALLA;
                    }
                } else if (pResp.equalsIgnoreCase("c")) {
                    dialogo = Dialogo.SELECCIONAR_MANO;
                    momento = MomentoJuego.COLOCAR_SELECCIONARMANO;
                }
                break;
            case COLOCAR_SELECCIONARPOSICIONBATALLA:
                if (pResp.equalsIgnoreCase("a")) {//ATAQUE
                    E.JugadorActual.accionColocarCarta(Ops.IdCartaZonaBSel, Ops.IdCartaManoSel, ZonaBatalla.POSBATALLA.ATAQUE);
                    dialogo = Dialogo.CARTA_COLOCADA;
                    momento = MomentoJuego.COLOCAR_CARTACOLOCADA;
                } else if (pResp.equalsIgnoreCase("d")) {//DEF
                    E.JugadorActual.accionColocarCarta(Ops.IdCartaZonaBSel, Ops.IdCartaManoSel, ZonaBatalla.POSBATALLA.DEFCARAABAJO);
                    dialogo = Dialogo.CARTA_COLOCADA;
                    momento = MomentoJuego.COLOCAR_CARTACOLOCADA;
                } else if (pResp.equalsIgnoreCase("c")) {
                    dialogo = Dialogo.SELECCIONAR_ZONABATALLA;
                    momento = MomentoJuego.COLOCAR_SELECCIONARZONABATALLA;
                }
                break;
            case ATACARCARTA_SELECCIONARZONABATALLA:
                if (pResp.matches("[0-2]")) {
                    id = Integer.parseInt(pResp);
                    if (E.JugadorActual.ZBatalla.obtenerCartaxId(id) != null) {
                        Ops.IdCartaZonaBSel = id;
                        dialogo = Dialogo.SELECCIONAR_ZONABATALLA;
                        momento = MomentoJuego.ATACARCARTA_SELECCIONARZONABATALLAE;
                    }
                }
                else if (pResp.equalsIgnoreCase("c")) {
                    dialogo = Dialogo.OPCIONESENTURNO;
                    momento = MomentoJuego.OPCIONESENTURNO;
                }
                break;
            case ATACARCARTA_SELECCIONARZONABATALLAE:
                if (pResp.matches("[0-2]")) {
                    id = Integer.parseInt(pResp);
                    Ops.IdCartaZonaBSelEnemigo = id;
                    Ops.resATK = E.JugadorActual.accionAtacarCarta(E.JugadorAnterior, Ops.IdCartaZonaBSelEnemigo, Ops.IdCartaZonaBSel);
                    if (Ops.resATK.resultado == Jugador.RESULTADOATACARCARTA.ENEMIGOSINBARRERA) {
                        E.terminoSinBarreras();
                        dialogo = Dialogo.JUGADORSINCARTASBARRERA;
                        momento = MomentoJuego.JUGADORSINCARTASBARRERA;
                    } else if (Ops.resATK.resultado != Jugador.RESULTADOATACARCARTA.NOSECUMPLENCOND) {
                        dialogo = Dialogo.ATAQUE_CARTA_REALIZADO;
                        momento = MomentoJuego.ATACARCARTA_ATAQUEREALIZADO;
                    }
                }
                else if (pResp.equalsIgnoreCase("c")) {
                    dialogo = Dialogo.SELECCIONAR_ZONABATALLA;
                    momento = MomentoJuego.ATACARCARTA_SELECCIONARZONABATALLA;
                }
                break;
            case COLOCAR_CARTACOLOCADA:
            case ATACARCARTA_ATAQUEREALIZADO:
            case ATACARBARRERA_ATAQUEREALIZADO:
            case CAMBIARPOSICIONBATALLA_REALIZADO:
                if (pResp.equalsIgnoreCase("c")) {
                    dialogo = Dialogo.OPCIONESENTURNO;
                    momento = MomentoJuego.OPCIONESENTURNO;
                }
                break;
            case ATACARBARRERA_SELECCIONARZONABATALLA:
                if (pResp.matches("[0-2]")) {
                    id = Integer.parseInt(pResp);
                    Ops.IdCartaZonaBSel = id;
                    int res = E.JugadorActual.accionAtacarBarrera(E.JugadorAnterior, Ops.IdCartaZonaBSel);
                    if (res == Jugador.RESULTADOATACARBARRERA.EXITO) {
                        dialogo = Dialogo.ATAQUE_BARRERA_REALIZADO;
                        momento = MomentoJuego.ATACARBARRERA_ATAQUEREALIZADO;
                    } else if (res == Jugador.RESULTADOATACARBARRERA.SINBARRERAS) {
                        E.terminoSinBarreras();
                        dialogo = Dialogo.JUGADORSINCARTASBARRERA;
                        momento = MomentoJuego.JUGADORSINCARTASBARRERA;
                    }
                }
                else if (pResp.equalsIgnoreCase("c")) {
                    dialogo = Dialogo.OPCIONESENTURNO;
                    momento = MomentoJuego.OPCIONESENTURNO;
                }
                break;
            case CAMBIARPOSICIONBATALLA_SELECCIONARZONABATALLA:
                if (pResp.matches("[0-2]")) {
                    id = Integer.parseInt(pResp);
                    Ops.IdCartaZonaBSel = id;
                    if (E.JugadorActual.accionCambiarPosicionBatalla(Ops.IdCartaZonaBSel)) {
                        dialogo = Dialogo.CAMBIODEPOSICION_REALIZADO;
                        momento = MomentoJuego.CAMBIARPOSICIONBATALLA_REALIZADO;
                    }
                }
                else if (pResp.equalsIgnoreCase("c")) {
                    dialogo = Dialogo.OPCIONESENTURNO;
                    momento = MomentoJuego.OPCIONESENTURNO;
                }
                break;
            case JUGADORSINCARTASMAZO:
            case JUGADORSINCARTASBARRERA:
                if (pResp.equalsIgnoreCase("c")) {
                    pantalla = Pantalla.FINDEJUEGO;
                    dialogo = Dialogo.FINDEJUEGO;
                }
                break;
            case FINDEJUEGO:
                if (pResp.equalsIgnoreCase("c")) {
                    pantalla = Pantalla.BIENVENIDAJUEGO;
                    dialogo = Dialogo.BIENVENIDAJUEGO;
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
                        devuelveposcarta(E.Jugador1.ZBatalla.posbatalla[i]) + " | ");
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
                        devuelveposcarta(E.Jugador2.ZBatalla.posbatalla[i]) + " | ");
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

    public static String devuelveposcarta(int poscarta){
        switch(poscarta){
            case 0: return "VACIO";
            case 1: return "ATQ";
            case 2: return "DBA"; //defensa boca abajo
            case 3: return "DBV"; //defensa boca arriba
            default: return "";
        }
    }

}
