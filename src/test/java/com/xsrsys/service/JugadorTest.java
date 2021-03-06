package com.xsrsys.service;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.xsrsys.service.Carta;
import com.xsrsys.service.Jugador;
import com.xsrsys.service.ResultadoAtaque;
import com.xsrsys.service.ZonaBatalla;
import com.xsrsys.service.Jugador.EstadoCarta;
import com.xsrsys.service.Jugador.VeredictoAtaque;
import com.xsrsys.service.ZonaBatalla.PosBatalla;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class JugadorTest {

    @Test
    public void testAccionCogerUnaCartaDelDeck() throws Exception{
        //Inicializando juego
        Jugador j1=new Jugador("J1");
        Carta c1=new Carta(1,Carta.Elemento.CORAZON);
        Carta c2=new Carta(2,Carta.Elemento.CORAZON);
        Carta c3=new Carta(3,Carta.Elemento.CORAZON);
        Carta c4=new Carta(4,Carta.Elemento.CORAZON);
        Carta c5=new Carta(5,Carta.Elemento.CORAZON);
        Carta c6=new Carta(6,Carta.Elemento.CORAZON);
        j1.Mano.agregarCartaEnEspacioVacio(c1);
        j1.Mano.agregarCartaEnEspacioVacio(c2);
        j1.Mano.agregarCartaEnEspacioVacio(c3);
        j1.Mano.agregarCartaEnEspacioVacio(c4);
        j1.Mano.agregarCartaEnEspacioVacio(c5);
        //RESULTADOCOJERUNACARTA.MANOLLENA
        assertEquals(Jugador.ResultadoCojerUnaCarta.MANOLLENA,j1.accionCogerUnaCartaDelDeck());
        j1.Mano.quitarCartaenPos(4);
        assertEquals(Jugador.ResultadoCojerUnaCarta.DECKVACIO,j1.accionCogerUnaCartaDelDeck());
        j1.Deck.agregarUnaCarta(c6);
        assertEquals(Jugador.ResultadoCojerUnaCarta.EXITO,j1.accionCogerUnaCartaDelDeck());
    }

    @Test
    public void testPosibilidadAtacarCarta_Insatisfactorio() throws Exception{

        //Inicializando juego
        int IDCARTAZB = 0;
        int IDCARTAMANO = 0;
        PosBatalla POSCARTAATACANTE = ZonaBatalla.PosBatalla.DEFCARAABAJO;
        Jugador j1=new Jugador("J1");
        Jugador j2=new Jugador("J2");
        Carta carbar1=new Carta(6,Carta.Elemento.ESPADA);
        Carta carbar2=new Carta(2,Carta.Elemento.ESPADA);
        Carta c1=new Carta(1,Carta.Elemento.CORAZON);
        Carta c2=new Carta(2,Carta.Elemento.CORAZON);
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
        j1.ZBatalla.posBatalla[IDCARTAZB] = ZonaBatalla.PosBatalla.ATAQUE;
        j1.ZBatalla.dispAtaque[IDCARTAZB] = ZonaBatalla.DispAtaque.NODISPONIBLE;
        //CARTA ATACANTE NO ESTA DISPONIBLE PARA ATACAR
        assertEquals(false,j1.posibilidadAtacarCarta(j2,IDCARTAZB,IDCARTAZB));


    }

    @SuppressWarnings("unused")
	private Object[][] sampleData(){
        Object[][] data=new Object[6][8];
        //Cabeceras de filas
        //0: Valor carta Atacante
        //1: Valor carta Atacada
        //2: Posición de Batalla carta Atacada
        //3: Veredicto de Ataque
        //4: Estado de Carta Atacante
        //5: Estado de Carta Atacada
        //6: Estado de Barrera
        //8: Descripcion de caso de prueba

        //En este caso en especial, se pueba la condición que el atacado se queda sin cartas barrera al recibir ataque
        data[0][0] = 7;
        data[0][1] = 6;
        data[0][2] = ZonaBatalla.PosBatalla.ATAQUE;
        data[0][3] = Jugador.VeredictoAtaque.ENEMIGOSINBARRERA;
        data[0][4] = Jugador.EstadoCarta.ACTIVA;
        data[0][5] = Jugador.EstadoCarta.DESTRUIDA;
        data[0][6] = Jugador.EstadoCarta.DESTRUIDA;
        data[0][7] = "Victoria de Atacante destruye carta atacada y barrera (7 Ataque > 6 Ataque) ";

        data[1][0] = 7;
        data[1][1] = 6;
        data[1][2] = ZonaBatalla.PosBatalla.DEFCARAABAJO;
        data[1][3] = Jugador.VeredictoAtaque.GANAATACANTE;
        data[1][4] = Jugador.EstadoCarta.ACTIVA;
        data[1][5] = Jugador.EstadoCarta.DESTRUIDA;
        data[1][6] = Jugador.EstadoCarta.ACTIVA;
        data[1][7] = "Victoria de Atacante destruye carta atacada (7 Ataque > 6 Defensa)";

        data[2][0] = 7;
        data[2][1] = 7;
        data[2][2] = ZonaBatalla.PosBatalla.ATAQUE;
        data[2][3] = Jugador.VeredictoAtaque.EMPATE;
        data[2][4] = Jugador.EstadoCarta.DESTRUIDA;
        data[2][5] = Jugador.EstadoCarta.DESTRUIDA;
        data[2][6] = Jugador.EstadoCarta.ACTIVA;
        data[2][7] = "Empate destruye carta ambas cartas en zona de batalla (7 Ataque = 7 Ataque)";

        data[3][0] = 7;
        data[3][1] = 7;
        data[3][2] = ZonaBatalla.PosBatalla.DEFCARAARRIBA;
        data[3][3] = Jugador.VeredictoAtaque.EMPATE;
        data[3][4] = Jugador.EstadoCarta.ACTIVA;
        data[3][5] = Jugador.EstadoCarta.ACTIVA;
        data[3][6] = Jugador.EstadoCarta.ACTIVA;
        data[3][7] = "Empate ambas cartas permanecen en campo (7 Ataque = 7 Defensa)";

        data[4][0] = 6;
        data[4][1] = 7;
        data[4][2] = ZonaBatalla.PosBatalla.ATAQUE;
        data[4][3] = Jugador.VeredictoAtaque.PIERDEATACANTE;
        data[4][4] = Jugador.EstadoCarta.DESTRUIDA;
        data[4][5] = Jugador.EstadoCarta.ACTIVA;
        data[4][6] = Jugador.EstadoCarta.ACTIVA;
        data[4][7] = "Derrota de atacante destruye carta atacante (6 Ataque < 7 Ataque)";

        data[5][0] = 6;
        data[5][1] = 7;
        data[5][2] = ZonaBatalla.PosBatalla.DEFCARAABAJO;
        data[5][3] = Jugador.VeredictoAtaque.PIERDEATACANTE;
        data[5][4] = Jugador.EstadoCarta.DESTRUIDA;
        data[5][5] = Jugador.EstadoCarta.ACTIVA;
        data[5][6] = Jugador.EstadoCarta.ACTIVA;
        data[5][7] = "Derrota de atacante destruye carta atacante (6 Ataque < 7 Defensa)";

        return data;
    }

    @Test
    @Parameters(method = "sampleData")
    public void testAccionAtacarCarta(
            int valorCartaAtacante,int valorCartaAtacada,PosBatalla posicionCartaAtacada,
            VeredictoAtaque veredictoAtaque, EstadoCarta estadoCartaAtacante, EstadoCarta estadoCartaAtacada,
            EstadoCarta estadoBarrera, String descripcion
    ){
        //Inicializando juego
        int IDCARTAZB = 0;
        int IDCARTAMANO = 0;
        PosBatalla POSCARTAATACANTE = ZonaBatalla.PosBatalla.ATAQUE;
        Jugador j1=new Jugador("J1");
        Jugador j2=new Jugador("J2");
        Carta carbar1=new Carta(6,Carta.Elemento.ESPADA);
        Carta carbar2=new Carta(2,Carta.Elemento.ESPADA);
        Carta c1=new Carta(valorCartaAtacante,Carta.Elemento.CORAZON);
        Carta c2=new Carta(valorCartaAtacada,Carta.Elemento.CORAZON);
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
        assertEquals(false,j2.puedeAtacarCartas(j1));

        //Segundo Turno:J1
        j1.accionIniciarTurno();
        //POSIBILIDAD DE ATACAR CARTA SATISFACTORIO
        assertEquals("POSIBILIDAD DE ATACAR CARTA SATISFACTORIO",true,j1.posibilidadAtacarCarta(j2,IDCARTAZB,IDCARTAZB));
        //TESTS ATACAR CARTA
        int IDCARTAINEXISTENTE=1;
        assertEquals(false,j1.posibilidadAtacarCarta(j2,IDCARTAZB,IDCARTAINEXISTENTE));
        assertEquals(false,j1.posibilidadAtacarCarta(j2,IDCARTAINEXISTENTE,IDCARTAZB));
        ResultadoAtaque rs= j1.accionAtacarCarta(j2,IDCARTAZB,IDCARTAZB);
        assertEquals(descripcion,veredictoAtaque,rs.veredicto);
        assertEquals(estadoCartaAtacante,rs.estadoCartaAtacante);
        assertEquals(estadoCartaAtacada,rs.estadoCartaAtacada);
        assertEquals(estadoBarrera,rs.estadoBarrera);
    }

    @Test
    public void testPosibilidadAtacarBarrera_Insatisfactorio() throws Exception{

        //Inicializando juego
        int IDCARTAZB = 0;
        int IDCARTAMANO = 0;
        PosBatalla POSCARTAATACANTE = ZonaBatalla.PosBatalla.DEFCARAABAJO;
        Jugador j1=new Jugador("J1");
        Jugador j2=new Jugador("J2");
        Carta carbar1=new Carta(6,Carta.Elemento.ESPADA);
        Carta carbar2=new Carta(2,Carta.Elemento.ESPADA);
        Carta c1=new Carta(1,Carta.Elemento.CORAZON);
        Carta c2=new Carta(2,Carta.Elemento.CORAZON);
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
        j1.ZBatalla.posBatalla[IDCARTAZB] = ZonaBatalla.PosBatalla.ATAQUE;
        j1.ZBatalla.dispAtaque[IDCARTAZB] = ZonaBatalla.DispAtaque.NODISPONIBLE;
        //CARTA ATACANTE NO ESTA DISPONIBLE PARA ATACAR
        assertEquals(false,j1.posibilidadAtacarBarrera(j2,IDCARTAZB));
        j1.ZBatalla.dispAtaque[IDCARTAZB] = ZonaBatalla.DispAtaque.DISPONIBLE;


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
        PosBatalla POSCARTA = ZonaBatalla.PosBatalla.ATAQUE;
        Jugador j1=new Jugador("J1");
        Jugador j2=new Jugador("J2");
        Carta carbar1=new Carta(6,Carta.Elemento.CORAZON);
        Carta carbar2=new Carta(2,Carta.Elemento.CORAZON);
        Carta carbar3=new Carta(2,Carta.Elemento.CORAZON);
        Carta c2=new Carta(5,Carta.Elemento.ESPADA);
        Carta c=new Carta(4,Carta.Elemento.ESPADA);
        j1.Barrera.agregarCartaEnEspacioVacio(carbar1);
        j2.Barrera.agregarCartaEnEspacioVacio(carbar2);
        j2.Barrera.agregarCartaEnEspacioVacio(carbar3);
        j1.Deck.agregarUnaCarta(c2);
        j1.Deck.agregarUnaCarta(c);//agrega una carta al deck j1

        //Primer turno: J1
        j1.accionIniciarTurno(); //jala una carta del deck a la mano
        j1.accionCogerUnaCartaDelDeck();
        int IDINEXISTENTE = 2;
        assertEquals(false,j1.posibilidadColocarCartaEnPosicion(IDCARTAZB, IDINEXISTENTE));
        assertEquals(true,j1.posibilidadColocarCartaEnPosicion(IDCARTAZB, IDCARTAMANO));
        assertEquals(true,j1.accionColocarCarta(IDCARTAZB,IDCARTAMANO,POSCARTA));//coloca carta de la mano a la zona de batalla
        assertEquals(false,j1.puedeColocarCartaEnZB());
        assertEquals(false,j1.puedeAtacarBarreras(j2));
        assertEquals(false,j1.posibilidadColocarCartaEnPosicion(IDCARTAZB, IDCARTAMANO));
        assertEquals(false,j1.accionColocarCarta(IDCARTAZB,IDCARTAMANO,POSCARTA));//coloca carta de la mano a la zona de batalla
        assertEquals(Jugador.VeredictoAtaque.NOSECUMPLENCOND,j1.accionAtacarBarrera(j2,IDCARTAZB));
        //Primer turno: J2
        j2.accionIniciarTurno();
        j2.accionCogerUnaCartaDelDeck();

        //Segundo turno: J1
        j1.accionIniciarTurno();
        j1.accionCogerUnaCartaDelDeck();
        //POSIBILIDAD DE ATACAR BARRERA SATISFACTORIA
        int IDCARTAINEXISTENTE=1;
        assertEquals(false,j1.posibilidadColocarCartaEnPosicion(IDCARTAZB, IDCARTAMANO));
        assertEquals(false,j1.posibilidadAtacarBarrera(j2,IDCARTAINEXISTENTE));
        assertEquals(true,j1.posibilidadAtacarBarrera(j2,IDCARTAZB));
        //JUGADOR 1 DESTRUYÓ UNA BARRERA
        assertEquals(Jugador.VeredictoAtaque.BARRERADESTRUIDA,j1.accionAtacarBarrera(j2,IDCARTAZB));


        //Segundo turno: J2
        j2.accionIniciarTurno();
        

        //Tercer Turno:J1
        j1.accionIniciarTurno();
        //POSIBILIDAD DE ATACAR BARRERA SATISFACTORIA
        assertEquals(true,j1.posibilidadAtacarBarrera(j2,IDCARTAZB));
        //JUGADOR 1 DESTRUYÓ UNA BARRERA Y JUGADOR 2 SE QUEDÓ SIN BARRERAS
        assertEquals(Jugador.VeredictoAtaque.ENEMIGOSINBARRERA,j1.accionAtacarBarrera(j2,IDCARTAZB));
        
    }

    @Test
    public void testCambiarPosicion() {
    	//Inicializando juego
        int IDCARTAZB = 0;
        int IDCARTAMANO = 0;
        PosBatalla POSCARTA = ZonaBatalla.PosBatalla.ATAQUE;
        Jugador j1=new Jugador("J1");
        Jugador j2=new Jugador("J2");
        Carta c=new Carta(4,Carta.Elemento.ESPADA);
        j1.Deck.agregarUnaCarta(c);//agrega una carta al deck j1

        //Primer turno: J1
        j1.accionIniciarTurno();
        j1.accionCogerUnaCartaDelDeck();//jala una carta del deck a la mano
        j1.accionColocarCarta(IDCARTAZB,IDCARTAMANO,POSCARTA);//coloca carta de la mano a la zona de batalla


        //Primer turno: J2
        j2.accionIniciarTurno();


        //Segundo turno: J1
        j1.accionIniciarTurno();
        //Cambiar posicion de carta
        assertEquals(true,j1.puedeCambiarPosicion());
        assertEquals(true,j1.posibilidadCambiarPosicionBatallaEnCarta(IDCARTAZB));
        assertEquals(true,j1.accionCambiarPosicionBatalla(IDCARTAZB));
        
    }
    
    @Test
    public void testClone() throws Exception {
        String t="J2";
    	Jugador d=new Jugador("J1");
        Jugador dclone=(Jugador)d.clone();
        assertEquals(d,dclone);
        d.setNombre(t);
        assertNotEquals(d,dclone);
        assertEquals(t,d.getNombre());
    }

	@Test
	public void testRepartirCartas() throws Exception{
		String nomJug1="J1";
		Jugador jug1=new Jugador(nomJug1);
		int nCartasEnDeck = Deck.MAXDECK - Mano.MAXMANOCARDS - Barrera.MAXBARRERACARDS;
		assertEquals(0,jug1.Mano.obtenerNumerodeCartas());
		assertEquals(0,jug1.Deck.Deck.size());
		assertEquals(0,jug1.Barrera.obtenerNumerodeCartas());
		jug1.repartirCartas();
		assertEquals(Mano.MAXMANOCARDS,jug1.Mano.obtenerNumerodeCartas());
		assertEquals(nCartasEnDeck,jug1.Deck.Deck.size());
		assertEquals(Barrera.MAXBARRERACARDS,jug1.Barrera.obtenerNumerodeCartas());
	}
}
