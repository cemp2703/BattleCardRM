package Clases;

import Clases.POJO.ResultadoAtaque;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class JugadorTest {
    @BeforeMethod
    public void setUp() throws Exception {

    }

    @AfterMethod
    public void tearDown() throws Exception {
    }

    @Test
    public void testOps(){
        int IDCARTAZB = 0;
        int IDCARTAMANO = 0;
        int POSCARTA = ZonaBatalla.POSCARTA.ATAQUE;
        Jugador j1=new Jugador("J1");
        Jugador j2=new Jugador("J2");
        Carta carbar1=new Carta(Carta.ELEMENTO.ESPADA,6);
        Carta carbar2=new Carta(Carta.ELEMENTO.ESPADA,2);
        Carta c=new Carta(Carta.ELEMENTO.ESPADA,1);

        j1.Barrera.agregarCartaEnEspacioVacio(carbar1);
        j2.Barrera.agregarCartaEnEspacioVacio(carbar2);

        j1.accionIniciarTurno();
        Assert.assertEquals(j1.accionCojerUnaCartaDelDeck(),Jugador.RESULTADOCOJERUNACARTA.DECKVACIO);
        j1.Deck.agregarUnaCarta(c);//agrega una carta al deck
        Assert.assertEquals(j1.accionCojerUnaCartaDelDeck(),Jugador.RESULTADOCOJERUNACARTA.EXITO);//jala una carta del deck a la mano
        Assert.assertEquals(j1.accionColocarCarta(IDCARTAZB,IDCARTAMANO,POSCARTA),true);//coloca carta de la mano a la zona de batalla
        j1.accionTerminarTurno();

        j2.accionIniciarTurno();
        j2.accionTerminarTurno();

        j1.accionIniciarTurno();
        Assert.assertEquals(j1.posibilidadAtacarBarrera(j2,IDCARTAZB),true);
        Assert.assertEquals(j1.accionAtacarBarrera(j2,IDCARTAZB),Jugador.RESULTADOATACARBARRERA.EXITO);
        j1.accionTerminarTurno();
    }

    @DataProvider(name="matrixataques")
    public Object[][] passData(){
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
        data[0][2] = ZonaBatalla.POSCARTA.ATAQUE;
        data[0][3] = Jugador.RESULTADOATACARCARTA.ENEMIGOSINBARRERA;
        data[0][4] = Jugador.RESULTADOCARTA.UP;
        data[0][5] = Jugador.RESULTADOCARTA.DOWN;
        data[0][6] = Jugador.RESULTADOCARTA.DOWN;
        data[0][7] = 0;
        data[0][8] = "Victoria de Atacante destruye carta atacada y barrera (7 Ataque > 6 Ataque) ";

        data[1][0] = 7;
        data[1][1] = 6;
        data[1][2] = ZonaBatalla.POSCARTA.DEFCARAABAJO;
        data[1][3] = Jugador.RESULTADOATACARCARTA.GANAATACANTE;
        data[1][4] = Jugador.RESULTADOCARTA.UP;
        data[1][5] = Jugador.RESULTADOCARTA.DOWN;
        data[1][6] = Jugador.RESULTADOCARTA.UP;
        data[1][7] = -1;
        data[1][8] = "Victoria de Atacante destruye carta atacada (7 Ataque > 6 Defensa)";

        data[2][0] = 7;
        data[2][1] = 7;
        data[2][2] = ZonaBatalla.POSCARTA.ATAQUE;
        data[2][3] = Jugador.RESULTADOATACARCARTA.EMPATE;
        data[2][4] = Jugador.RESULTADOCARTA.DOWN;
        data[2][5] = Jugador.RESULTADOCARTA.DOWN;
        data[2][6] = Jugador.RESULTADOCARTA.UP;
        data[2][7] = -1;
        data[2][8] = "Empate destruye carta ambas cartas en zona de batalla (7 Ataque = 7 Ataque)";

        data[3][0] = 7;
        data[3][1] = 7;
        data[3][2] = ZonaBatalla.POSCARTA.DEFCARAARRIBA;
        data[3][3] = Jugador.RESULTADOATACARCARTA.EMPATE;
        data[3][4] = Jugador.RESULTADOCARTA.UP;
        data[3][5] = Jugador.RESULTADOCARTA.UP;
        data[3][6] = Jugador.RESULTADOCARTA.UP;
        data[3][7] = -1;
        data[3][8] = "Empate ambas cartas permanecen en campo (7 Ataque = 7 Defensa)";

        data[4][0] = 6;
        data[4][1] = 7;
        data[4][2] = ZonaBatalla.POSCARTA.ATAQUE;
        data[4][3] = Jugador.RESULTADOATACARCARTA.PIERDEATACANTE;
        data[4][4] = Jugador.RESULTADOCARTA.DOWN;
        data[4][5] = Jugador.RESULTADOCARTA.UP;
        data[4][6] = Jugador.RESULTADOCARTA.UP;
        data[4][7] = -1;
        data[4][8] = "Derrota de atacante destruye carta atacante (6 Ataque < 7 Ataque)";

        data[5][0] = 6;
        data[5][1] = 7;
        data[5][2] = ZonaBatalla.POSCARTA.DEFCARAABAJO;
        data[5][3] = Jugador.RESULTADOATACARCARTA.PIERDEATACANTE;
        data[5][4] = Jugador.RESULTADOCARTA.DOWN;
        data[5][5] = Jugador.RESULTADOCARTA.UP;
        data[5][6] = Jugador.RESULTADOCARTA.UP;
        data[5][7] = -1;
        data[5][8] = "Derrota de atacante destruye carta atacante (6 Ataque < 7 Defensa)";

        return data;
    }

    @Test(dataProvider = "matrixataques")
    public void testAccionAtacarCarta(
            int valorCartaAtacante,int valorCartaAtacada,int posicionCartaAtacada,
            int resultadoAtaque, int resultadoCartaAtacante, int resultadoCartaAtacada,
            int resultadoBarrera, int resultadoIdBarrera, String descripcion
    ){
        int IDCARTAZB = 0;
        int IDCARTAMANO = 0;
        int IDBARRERA = 1;
        int POSCARTAATACANTE = ZonaBatalla.POSCARTA.ATAQUE;
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

        //---  Inicia el juego

        j1.accionIniciarTurno();
        j1.accionCojerUnaCartaDelDeck();//jala una carta del deck a la mano
        j1.accionColocarCarta(IDCARTAZB,IDCARTAMANO,POSCARTAATACANTE);//coloca carta de la mano a la zona de batalla
        j1.accionTerminarTurno();

        j2.accionIniciarTurno();
        j2.accionCojerUnaCartaDelDeck();//jala una carta del deck a la mano
        j2.accionColocarCarta(IDCARTAZB,IDCARTAMANO,posicionCartaAtacada);//coloca carta de la mano a la zona de batalla
        j2.accionTerminarTurno();

        j1.accionIniciarTurno();
        Assert.assertEquals(j1.posibilidadAtacarCarta(j2,IDCARTAZB,IDCARTAZB),true);
        ResultadoAtaque rs= j1.accionAtacarCarta(j2,IDCARTAZB,IDCARTAZB);
        Assert.assertEquals(rs.resultado,resultadoAtaque);
        Assert.assertEquals(rs.cartaAtacante, resultadoCartaAtacante);
        Assert.assertEquals(rs.cartaAtacada, resultadoCartaAtacada);
        Assert.assertEquals(rs.barrera, resultadoBarrera);
        Assert.assertEquals(rs.idbarrera, resultadoIdBarrera);
        j1.accionTerminarTurno();

        //---  Fin del juego
    }

    @Test
    public void testClone() throws Exception {
        Jugador d=new Jugador("J1");
        Jugador dclone=(Jugador)d.clone();
        Assert.assertEquals(d,dclone);
        d.setNombre("J2");
        Assert.assertNotEquals(d,dclone);
    }




}
