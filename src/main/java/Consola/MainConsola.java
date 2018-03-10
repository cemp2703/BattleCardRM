package Consola;
//TODO: Implementar ataque a una barrera
//TODO; Implementar cambiar posicion de batalla
//TODO: Implementar fin del juego
//TODO: implementar reinicio de juego
//TODO: sacar toda imprsion de consola de clase estado
//TODO: Crear un ID de jugador dentro de jugador en lugar de la variable turno actual.

import Clases.*;
import Clases.POJO.ResultadoAtaque;
import Juego.Operaciones;

import java.io.IOException;
import java.util.Scanner;

public class MainConsola {

    static Estado E;
    static Operaciones Ops;

    static int idpantalla;
    static int iddialogo;

    public static void main(String[] args) throws IOException, InterruptedException{
        Inicializando();
        Scanner sc = new Scanner(System.in);
        String resp="";
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
        idpantalla = 0;
        iddialogo = 0;
    }

    static void cambiandoPantalla(int pantalla){
        idpantalla=pantalla;
        iddialogo=0;
    }

    static void clear(){
        for (int i = 0; i < 50; ++i) System.out.println();
    }

    static void  imprimirPantalla() {
        switch(idpantalla){
            case 0:
                System.out.println("******************");
                System.out.println("   Battle Cards   ");
                System.out.println("******************");
                break;
            case 1:
                System.out.print("TURNO: "+E.JugadorActual.getNombre()+"\n");
                imprimeEstado(E);
                break;

        }
    }

    static void imprimirDialogo(){

        if(idpantalla == 0){
            switch (iddialogo){
                case 0:
                    System.out.println("Ingrese:");
                    System.out.println("(I) Iniciar Juego");
                    System.out.println("(Cerrar) Cerrar el Juego (En cualquier momento)");
                    break;
            }
        }
        else if(idpantalla == 1){
            switch (iddialogo){
                case 0://dialogo acciones
                    System.out.println("Seleccion accion a realizar:");
                    if(E.JugadorActual.puedeColocarCarta())
                        System.out.println("(C) Colocar una carta");
                    if(E.JugadorActual.puedeAtacarAUnaCarta(E.JugadorAnterior))
                        System.out.println("(A) Atacar a una carta en Zona de batalla");
                    if(E.JugadorActual.puedeAtacarAUnaBarrera(E.JugadorAnterior))
                        System.out.println("(B) Atacar a una Barrera");
                    System.out.println("(T) Terminar Turno");
                    System.out.println("Accion: ");
                    break;
                case 1://Colocar carta - Seleccionando carta en mano
                    System.out.println("Seleccionando carta en Mano");
                    System.out.println("Ingrese la posicion de (0 - 4) de la carta en su mano: ");
                    System.out.println("O Ingrese (C) Cancelar para volver");
                    break;
                case 2://Colocar carta - Seleccionando carta en zona de batalla
                    System.out.println("Colocando Carta en Zona de batalla");
                    System.out.println("Ingrese la posicion de (0 - 2) de la zona de batalla: ");
                    System.out.println("O Ingrese (C) Cancelar para volver");
                    break;
                case 3://Colocar carta - Elegir posición de batalla
                    System.out.println("Elegir posición de batalla");
                    System.out.println("Ingrese (A) para Ataque: ");
                    System.out.println("Ingrese (D) para Defensa: ");
                    System.out.println("O Ingrese (C) Cancelar para volver");
                    break;
                case 4://Colocar carta - Carta Colocada
                    System.out.println("Carta Colocada!!");
                    System.out.println("Ingrese (C) para continuar");
                    break;
                case 5://Atacar carta - Seleccionando carta en zona de batalla
                    System.out.println("Seleccionando carta en Zona de Batalla");
                    System.out.println("Ingrese la posicion de (0 - 2) de la zona de batalla: ");
                    System.out.println("O Ingrese (C) Cancelar para volver");
                    break;
                case 6://Atacar carta - Seleccionando carta en zona de batalla enemiga
                    System.out.println("Seleccionando carta en Zona de Batalla Enemiga");
                    System.out.println("Ingrese la posicion de (0 - 2) de la zona de batalla: ");
                    System.out.println("O Ingrese (C) Cancelar para volver");
                    break;
                case 7://Colocar carta - Ataque Realizado
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
                case 8://Atacar barrera - Seleccionando carta en zona de batalla
                    System.out.println("Seleccionando carta en Zona de Batalla");
                    System.out.println("Ingrese la posicion de (0 - 2) de la zona de batalla: ");
                    System.out.println("O Ingrese (C) Cancelar para volver");
                    break;
                case 9://Atacar barrera - Ataque Realizado
                    System.out.println("Ataque Realizado!!");
                    System.out.println("Barrera Destruida");
                    System.out.println("Ingrese (C) para continuar");
                    break;
            }
        }

    }

    static void recibeRespuesta(String pResp){
        int id;
        if(idpantalla == 0 && iddialogo == 0){
            if(pResp.equalsIgnoreCase("i")){
                Ops.repartirCartas(E.Jugador1);
                Ops.repartirCartas(E.Jugador2);
                E.JugadorActual.accionIniciarTurno();
                cambiandoPantalla(idpantalla+1);
            }
        }
        else if(idpantalla == 1 && iddialogo == 0) {
            if (pResp.equalsIgnoreCase("C")){
                if (E.JugadorActual.puedeColocarCarta())
                    iddialogo = 1;
            }
            else if (pResp.equalsIgnoreCase("A")){
                if(E.JugadorActual.puedeAtacarAUnaCarta(E.JugadorAnterior))
                    iddialogo = 5;
            }
            else if (pResp.equalsIgnoreCase("B")){
                if(E.JugadorActual.puedeAtacarAUnaBarrera(E.JugadorAnterior))
                    iddialogo = 8;
            }
            else {
                if (pResp.equalsIgnoreCase("T")){
                    E.cambioDeTurno();
                    iddialogo = 0;
                }
            }
        }
        else if(idpantalla == 1 && iddialogo == 1) {
            if(pResp.matches("[0-4]")){
                id = Integer.parseInt(pResp);
                if(E.JugadorActual.Mano.obtenerCartaxId(id) !=null) {
                    Ops.IdCartaManoSel = id;
                    iddialogo = 2;
                }
                //else no hay carta en esa posicion en la mano
            }
            else if(pResp.equalsIgnoreCase("c")){
                iddialogo = 0;
            }
        }
        else if(idpantalla == 1 && iddialogo == 2) {
            if(pResp.matches("[0-2]")){
                id = Integer.parseInt(pResp);
                if(E.JugadorActual.ZBatalla.obtenerCartaxId(id) ==null) {
                    Ops.IdCartaZonaBSel = id;
                    iddialogo = 3;
                }
            }
            else if(pResp.equalsIgnoreCase("c")){
                iddialogo = 1;
            }
        }
        else if(idpantalla == 1 && iddialogo == 3) {
            if(pResp.equalsIgnoreCase("a")){
                E.JugadorActual.accionColocarCarta(Ops.IdCartaZonaBSel,Ops.IdCartaManoSel, ZonaBatalla.POSCARTA.ATAQUE);
                iddialogo = 4;
            }
            else if(pResp.equalsIgnoreCase("d")){
                E.JugadorActual.accionColocarCarta(Ops.IdCartaZonaBSel,Ops.IdCartaManoSel, ZonaBatalla.POSCARTA.DEFCARAABAJO);
                iddialogo = 4;
            }
            else if(pResp.equalsIgnoreCase("c")){
                iddialogo = 1;
            }
        }
        else if(idpantalla == 1 && iddialogo == 4) {
            if(pResp.equalsIgnoreCase("c")){
                iddialogo = 0;
            }
        }
        else if(idpantalla == 1 && iddialogo == 5) {
            if(pResp.matches("[0-2]")){
                id = Integer.parseInt(pResp);
                if(E.JugadorActual.ZBatalla.obtenerCartaxId(id) != null) {
                    Ops.IdCartaZonaBSel = id;
                    iddialogo = 6;
                }
            }
            else if(pResp.equalsIgnoreCase("c")){
                iddialogo = 1;
            }
        }
        else if(idpantalla == 1 && iddialogo == 6) {
            if(pResp.matches("[0-2]")){
                id = Integer.parseInt(pResp);
                if(E.JugadorAnterior.ZBatalla.obtenerCartaxId(id) != null) {
                    Ops.IdCartaZonaBSelEnemigo = id;
                    Ops.resATK= E.JugadorActual.accionAtacarCarta(E.JugadorAnterior,Ops.IdCartaZonaBSelEnemigo,Ops.IdCartaZonaBSel);
                    iddialogo = 7;
                }
            }
            else if(pResp.equalsIgnoreCase("c")){
                iddialogo = 5;
            }
        }
        else if(idpantalla == 1 && iddialogo == 7) {
            if(pResp.equalsIgnoreCase("c")){
                iddialogo = 0;
            }
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
        switch(n){
            case 0: return "\u2665";
            case 1: return "\u2666";
            case 2: return "\u2663";
            case 3: return "\u2660";
            default: return "";
        }
    }

}
