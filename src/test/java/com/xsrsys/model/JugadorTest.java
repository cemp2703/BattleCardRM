package com.xsrsys.model;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
//import org.testng.annotations.DataProvider;

import com.xsrsys.model.Carta;
import com.xsrsys.model.Jugador;
import com.xsrsys.model.ResultadoAtaque;
import com.xsrsys.model.ZonaBatalla;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class JugadorTest {

    @Test
    public void testAccionCogerUnaCartaDelDeck() throws Exception{
        //Inicializando juego
        int IDCARTAZB = 0;
        int IDCARTAMANO = 0;
        int POSCARTAATACANTE = ZonaBatalla.POSBATALLA.DEFCARAABAJO;
        Jugador j1=new Jugador("J1");
        Jugador j2=new Jugador("J2");
        Carta c1=new Carta(Carta.ELEMENTO.CORAZON,1);
        Carta c2=new Carta(Carta.ELEMENTO.CORAZON,2);
        Carta c3=new Carta(Carta.ELEMENTO.CORAZON,3);
        Carta c4=new Carta(Carta.ELEMENTO.CORAZON,4);
        Carta c5=new Carta(Carta.ELEMENTO.CORAZON,5);
        Carta c6=new Carta(Carta.ELEMENTO.CORAZON,6);
        j1.Mano.agregarCartaEnEspacioVacio(c1);
        j1.Mano.agregarCartaEnEspacioVacio(c2);
        j1.Mano.agregarCartaEnEspacioVacio(c3);
        j1.Mano.agregarCartaEnEspacioVacio(c4);
        j1.Mano.agregarCartaEnEspacioVacio(c5);
        //RESULTADOCOJERUNACARTA.MANOLLENA
        assertEquals(Jugador.RESULTADOCOJERUNACARTA.MANOLLENA,j1.accionCogerUnaCartaDelDeck());
        j1.Mano.quitarCartaenPos(4);
        assertEquals(Jugador.RESULTADOCOJERUNACARTA.DECKVACIO,j1.accionCogerUnaCartaDelDeck());
        j1.Deck.agregarUnaCarta(c6);
        assertEquals(Jugador.RESULTADOCOJERUNACARTA.EXITO,j1.accionCogerUnaCartaDelDeck());
    }

    @Test
    public void testPosibilidadAtacarCarta_Insatisfactorio() throws Exception{

        //Inicializando juego
        int IDCARTAZB = 0;
        int IDCARTAMANO = 0;
        int POSCARTAATACANTE = ZonaBatalla.POSBATALLA.DEFCARAABAJO;
        Jugador j1=new Jugador("J1");
        Jugador j2=new Jugador("J2");
        Carta carbar1=new Carta(Carta.ELEMENTO.ESPADA,6);
        Carta carbar2=new Carta(Carta.ELEMENTO.ESPADA,2);
        Carta c1=new Carta(Carta.ELEMENTO.CORAZON,1);
        Carta c2=new Carta(Carta.ELEMENTO.CORAZON,2);
        j1.Barrera.agregarCartaEnEspacioVacio(carbar1);
        j1.Deck.agregarUnaCarta(c1);
        j2.Deck.agregarUnaCarta(c2);


        j1.accionIniciarTurno();
        j1.accionCogerUnaCartaDelDeck();
        //NO SE PUEDE ATACAR EN PRIMER TURNO
        assertEquals(false,j1.posibilidadAtacarCarta(j2,IDCARTAZB,IDCARTAZB));


        j2.accionIniciarTurno();


        j1.accionIniciarTurno();
        //JUGADOR ATACANTE NO TIENE CARTAS EN ESA UBICACION EN ZONA DE BATALLA
        assertEquals(false,j1.posibilidadAtacarCarta(j2,IDCARTAZB,IDCARTAZB));
        j1.accionColocarCarta(IDCARTAZB,IDCARTAMANO,POSCARTAATACANTE);//coloca carta de la mano a la zona de batalla
        //JUGADOR ATACADO NO TIENE CARTA EN ESA UBICACION EN ZONA DE BATALLA
        assertEquals(false,j1.posibilidadAtacarCarta(j2,IDCARTAZB,IDCARTAZB));


        j2.accionIniciarTurno();
        j2.accionCogerUnaCartaDelDeck();
        j2.accionColocarCarta(IDCARTAZB,IDCARTAMANO,POSCARTAATACANTE);
        //JUGADOR ATACADO NO TIENE CARTAS DE BARRERA
        assertEquals(false,j1.posibilidadAtacarCarta(j2,IDCARTAZB,IDCARTAZB));
        j2.Barrera.agregarCartaEnEspacioVacio(carbar2);


        j1.accionIniciarTurno();
        //CARTA ATACANTE NO ESTA EN POSICION DE ATAQUE
        assertEquals(false,j1.posibilidadAtacarCarta(j2,IDCARTAZB,IDCARTAZB));
        j1.ZBatalla.posbatalla[IDCARTAZB] = ZonaBatalla.POSBATALLA.ATAQUE;
        j1.ZBatalla.dispataque[IDCARTAZB] = ZonaBatalla.DISPATAQUE.NODISPONIBLE;
        //CARTA ATACANTE NO ESTA DISPONIBLE PARA ATACAR
        assertEquals(false,j1.posibilidadAtacarCarta(j2,IDCARTAZB,IDCARTAZB));


    }

    private Object[][] sampleData(){
        Object[][] data=new Object[6][9];
        //Cabeceras de filas
        //0: Valor carta Atacante
        //1: Valor carta Atacada
        //2: Posición carta Atacada
        //3: Resultado de Ataque
        //4: Resultado de Carta Atacante
        //5: Resultado de Carta Atacada
        //6: Resultado de Barrera
        //7: Resultado id de Barrera destruida
        //8: Descripcion de caso de prueba

        //En este caso en especial, se pueba la condición que el atacado se queda sin cartas barrera al recibir ataque
        data[0][0] = 7;
        data[0][1] = 6;
        data[0][2] = ZonaBatalla.POSBATALLA.ATAQUE;
        data[0][3] = Jugador.RESULTADOATACARCARTA.ENEMIGOSINBARRERA;
        data[0][4] = Jugador.RESULTADOCARTA.UP;
        data[0][5] = Jugador.RESULTADOCARTA.DOWN;
        data[0][6] = Jugador.RESULTADOCARTA.DOWN;
        data[0][7] = 0;
        data[0][8] = "Victoria de Atacante destruye carta atacada y barrera (7 Ataque > 6 Ataque) ";

        data[1][0] = 7;
        data[1][1] = 6;
        data[1][2] = ZonaBatalla.POSBATALLA.DEFCARAABAJO;
        data[1][3] = Jugador.RESULTADOATACARCARTA.GANAATACANTE;
        data[1][4] = Jugador.RESULTADOCARTA.UP;
        data[1][5] = Jugador.RESULTADOCARTA.DOWN;
        data[1][6] = Jugador.RESULTADOCARTA.UP;
        data[1][7] = -1;
        data[1][8] = "Victoria de Atacante destruye carta atacada (7 Ataque > 6 Defensa)";

        data[2][0] = 7;
        data[2][1] = 7;
        data[2][2] = ZonaBatalla.POSBATALLA.ATAQUE;
        data[2][3] = Jugador.RESULTADOATACARCARTA.EMPATE;
        data[2][4] = Jugador.RESULTADOCARTA.DOWN;
        data[2][5] = Jugador.RESULTADOCARTA.DOWN;
        data[2][6] = Jugador.RESULTADOCARTA.UP;
        data[2][7] = -1;
        data[2][8] = "Empate destruye carta ambas cartas en zona de batalla (7 Ataque = 7 Ataque)";

        data[3][0] = 7;
        data[3][1] = 7;
        data[3][2] = ZonaBatalla.POSBATALLA.DEFCARAARRIBA;
        data[3][3] = Jugador.RESULTADOATACARCARTA.EMPATE;
        data[3][4] = Jugador.RESULTADOCARTA.UP;
        data[3][5] = Jugador.RESULTADOCARTA.UP;
        data[3][6] = Jugador.RESULTADOCARTA.UP;
        data[3][7] = -1;
        data[3][8] = "Empate ambas cartas permanecen en campo (7 Ataque = 7 Defensa)";

        data[4][0] = 6;
        data[4][1] = 7;
        data[4][2] = ZonaBatalla.POSBATALLA.ATAQUE;
        data[4][3] = Jugador.RESULTADOATACARCARTA.PIERDEATACANTE;
        data[4][4] = Jugador.RESULTADOCARTA.DOWN;
        data[4][5] = Jugador.RESULTADOCARTA.UP;
        data[4][6] = Jugador.RESULTADOCARTA.UP;
        data[4][7] = -1;
        data[4][8] = "Derrota de atacante destruye carta atacante (6 Ataque < 7 Ataque)";

        data[5][0] = 6;
        data[5][1] = 7;
        data[5][2] = ZonaBatalla.POSBATALLA.DEFCARAABAJO;
        data[5][3] = Jugador.RESULTADOATACARCARTA.PIERDEATACANTE;
        data[5][4] = Jugador.RESULTADOCARTA.DOWN;
        data[5][5] = Jugador.RESULTADOCARTA.UP;
        data[5][6] = Jugador.RESULTADOCARTA.UP;
        data[5][7] = -1;
        data[5][8] = "Derrota de atacante destruye carta atacante (6 Ataque < 7 Defensa)";

        return data;
    }

    @Test
    @Parameters(method = "sampleData")
    public void testAccionAtacarCarta(
            int valorCartaAtacante,int valorCartaAtacada,int posicionCartaAtacada,
            int resultadoAtaque, int resultadoCartaAtacante, int resultadoCartaAtacada,
            int resultadoBarrera, int resultadoIdBarrera, String descripcion
    ){
        //Inicializando juego
        int IDCARTAZB = 0;
        int IDCARTAMANO = 0;
        int POSCARTAATACANTE = ZonaBatalla.POSBATALLA.ATAQUE;
        Jugador j1=new Jugador("J1");
        Jugador j2=new Jugador("J2");
        Carta carbar1=new Carta(Carta.ELEMENTO.ESPADA,6);
        Carta carbar2=new Carta(Carta.ELEMENTO.ESPADA,2);
        Carta c1=new Carta(Carta.ELEMENTO.CORAZON,valorCartaAtacante);
        Carta c2=new Carta(Carta.ELEMENTO.CORAZON,valorCartaAtacada);
        j1.Barrera.agregarCartaEnEspacioVacio(carbar1);
        j1.Deck.agregarUnaCarta(c1);
        j2.Barrera.agregarCartaEnEspacioVacio(carbar2);
        j2.Deck.agregarUnaCarta(c2);

        //Primer turno: J1
        j1.accionIniciarTurno();
        j1.accionCogerUnaCartaDelDeck();//jala una carta del deck a la mano
        j1.accionColocarCarta(IDCARTAZB,IDCARTAMANO,POSCARTAATACANTE);//coloca carta de la mano a la zona de batalla


        //Primer turno: J2
        j2.accionIniciarTurno();
        j2.accionCogerUnaCartaDelDeck();//jala una carta del deck a la mano
        j2.accionColocarCarta(IDCARTAZB,IDCARTAMANO,posicionCartaAtacada);//coloca carta de la mano a la zona de batalla


        //Segundo Turno:J1
        j1.accionIniciarTurno();
        //POSIBILIDAD DE ATACAR CARTA SATISFACTORIO
        assertEquals("POSIBILIDAD DE ATACAR CARTA SATISFACTORIO",true,j1.posibilidadAtacarCarta(j2,IDCARTAZB,IDCARTAZB));
        //TESTS ATACAR CARTA
        ResultadoAtaque rs= j1.accionAtacarCarta(j2,IDCARTAZB,IDCARTAZB);
        assertEquals(descripcion,resultadoAtaque,rs.resultado);
        assertEquals(resultadoCartaAtacante,rs.cartaAtacante);
        assertEquals(resultadoCartaAtacada,rs.cartaAtacada);
        assertEquals(resultadoBarrera,rs.barrera);
        assertEquals(resultadoIdBarrera,rs.idbarrera);

    }

    
    @Test
    public void testPosibilidadAtacarBarrera_Insatisfactorio() throws Exception{

        //Inicializando juego
        int IDCARTAZB = 0;
        int IDCARTAMANO = 0;
        int POSCARTAATACANTE = ZonaBatalla.POSBATALLA.DEFCARAABAJO;
        Jugador j1=new Jugador("J1");
        Jugador j2=new Jugador("J2");
        Carta carbar1=new Carta(Carta.ELEMENTO.ESPADA,6);
        Carta carbar2=new Carta(Carta.ELEMENTO.ESPADA,2);
        Carta c1=new Carta(Carta.ELEMENTO.CORAZON,1);
        Carta c2=new Carta(Carta.ELEMENTO.CORAZON,2);
        j1.Barrera.agregarCartaEnEspacioVacio(carbar1);
        j1.Deck.agregarUnaCarta(c1);
        j2.Deck.agregarUnaCarta(c2);


        j1.accionIniciarTurno();
        j1.accionCogerUnaCartaDelDeck();//jala una carta del deck a la mano
        //NO SE PUEDE ATACAR EN PRIMER TURNO
        assertEquals(false,j1.posibilidadAtacarBarrera(j2,IDCARTAZB));


        j2.accionIniciarTurno();


        j1.accionIniciarTurno();
        //JUGADOR ATACANTE NO TIENE CARTAS EN ESA UBICACION EN ZONA DE BATALLA
        assertEquals(false,j1.posibilidadAtacarBarrera(j2,IDCARTAZB));
        j1.accionColocarCarta(IDCARTAZB,IDCARTAMANO,POSCARTAATACANTE);//coloca carta de la mano a la zona de batalla


        j2.accionIniciarTurno();
        //JUGADOR ATACADO NO TIENE CARTAS DE BARRERA
        assertEquals(false,j1.posibilidadAtacarBarrera(j2,IDCARTAZB));
        j2.Barrera.agregarCartaEnEspacioVacio(carbar2);


        j1.accionIniciarTurno();
        //CARTA ATACANTE NO ESTA EN POSICION DE ATAQUE
        assertEquals(false,j1.posibilidadAtacarBarrera(j2,IDCARTAZB));
        j1.ZBatalla.posbatalla[IDCARTAZB] = ZonaBatalla.POSBATALLA.ATAQUE;
        j1.ZBatalla.dispataque[IDCARTAZB] = ZonaBatalla.DISPATAQUE.NODISPONIBLE;
        //CARTA ATACANTE NO ESTA DISPONIBLE PARA ATACAR
        assertEquals(false,j1.posibilidadAtacarBarrera(j2,IDCARTAZB));
        j1.ZBatalla.dispataque[IDCARTAZB] = ZonaBatalla.DISPATAQUE.DISPONIBLE;


        j2.accionIniciarTurno();
        j2.accionCogerUnaCartaDelDeck();
        j2.accionColocarCarta(IDCARTAZB,IDCARTAMANO,POSCARTAATACANTE);
        //JUGADOR ATACADO NO TIENE CARTAS EN ZONA DE BATALLA
        assertEquals(false,j1.posibilidadAtacarBarrera(j2,IDCARTAZB));

    }

    @Test
    public void testAccionAtacarBarrera(){
        //Inicializando juego
        int IDCARTAZB = 0;
        int IDCARTAMANO = 0;
        int POSCARTA = ZonaBatalla.POSBATALLA.ATAQUE;
        Jugador j1=new Jugador("J1");
        Jugador j2=new Jugador("J2");
        Carta carbar1=new Carta(Carta.ELEMENTO.CORAZON,6);
        Carta carbar2=new Carta(Carta.ELEMENTO.CORAZON,2);
        Carta carbar3=new Carta(Carta.ELEMENTO.CORAZON,2);
        Carta c=new Carta(Carta.ELEMENTO.ESPADA,4);
        j1.Barrera.agregarCartaEnEspacioVacio(carbar1);
        j2.Barrera.agregarCartaEnEspacioVacio(carbar2);
        j2.Barrera.agregarCartaEnEspacioVacio(carbar3);
        j1.Deck.agregarUnaCarta(c);//agrega una carta al deck j1

        //Primer turno: J1
        j1.accionIniciarTurno();
        j1.accionCogerUnaCartaDelDeck();//jala una carta del deck a la mano
        j1.accionColocarCarta(IDCARTAZB,IDCARTAMANO,POSCARTA);//coloca carta de la mano a la zona de batalla


        //Primer turno: J2
        j2.accionIniciarTurno();


        //Segundo turno: J1
        j1.accionIniciarTurno();
        //POSIBILIDAD DE ATACAR BARRERA SATISFACTORIA
        assertEquals(true,j1.posibilidadAtacarBarrera(j2,IDCARTAZB));
        //JUGADOR 1 DESTRUYÓ UNA BARRERA
        assertEquals(Jugador.RESULTADOATACARBARRERA.EXITO,j1.accionAtacarBarrera(j2,IDCARTAZB));


        //Segundo turno: J2
        j2.accionIniciarTurno();


        //Tercer Turno:J1
        j1.accionIniciarTurno();
        //POSIBILIDAD DE ATACAR BARRERA SATISFACTORIA
        assertEquals(true,j1.posibilidadAtacarBarrera(j2,IDCARTAZB));
        //JUGADOR 1 DESTRUYÓ UNA BARRERA Y JUGADOR 2 SE QUEDÓ SIN BARRERAS
        assertEquals(Jugador.RESULTADOATACARBARRERA.SINBARRERAS,j1.accionAtacarBarrera(j2,IDCARTAZB));
    }

    @Test
    public void testClone() throws Exception {
        Jugador d=new Jugador("J1");
        Jugador dclone=(Jugador)d.clone();
        assertEquals(d,dclone);
        d.setNombre("J2");
        assertNotEquals(d,dclone);
    }




}
