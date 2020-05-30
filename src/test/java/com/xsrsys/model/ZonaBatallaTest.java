package Clases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ZonaBatallaTest {
    @BeforeMethod
    public void setUp() throws Exception {

    }

    @AfterMethod
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetPosBatallaxId() throws Exception {
        int IDCARTAZB = 0;
        ZonaBatalla zb=new ZonaBatalla();
        int POSBATALLA = ZonaBatalla.POSBATALLA.ATAQUE;
        zb.agregarCartaEnPos(new Carta(1,Carta.ELEMENTO.ESPADA),IDCARTAZB, POSBATALLA);
        zb.getPosBatallaxId(IDCARTAZB);
        Assert.assertEquals(zb.getPosBatallaxId(IDCARTAZB),POSBATALLA);
        //IDZONABATALLA mayor del tamaño maximo: 5
        int IDCARTAZBERRONEO = 8;
        Assert.assertNotEquals(zb.getPosBatallaxId(IDCARTAZBERRONEO),POSBATALLA);
    }


    @Test
    public void testRenovarDisponibilidades() throws Exception {
        int POSCARTA0 = ZonaBatalla.POSBATALLA.ATAQUE;
        int POSCARTA1 = ZonaBatalla.POSBATALLA.DEFCARAABAJO;
        ZonaBatalla zb=new ZonaBatalla();
        Carta c=new Carta(1,Carta.ELEMENTO.ESPADA);
        int idZB0 = 0;
        int idZB1 = 1;
        zb.agregarCartaEnPos(c, idZB0 , POSCARTA0);
        zb.agregarCartaEnPos(c, idZB1 , POSCARTA1);
        zb.renovarPosibilidades();
        Assert.assertEquals(zb.dispataque[idZB0],ZonaBatalla.DISPATAQUE.DISPONIBLE);
        Assert.assertEquals(zb.dispcambio[idZB0],ZonaBatalla.DISPCAMBIO.DISPONIBLE);
        Assert.assertEquals(zb.dispataque[idZB1],ZonaBatalla.DISPATAQUE.NODISPONIBLE);
        Assert.assertEquals(zb.dispcambio[idZB1],ZonaBatalla.DISPCAMBIO.DISPONIBLE);
    }

    @Test
    public void testAgregarCartaEnPos() throws Exception {
        int POSCARTA = ZonaBatalla.POSBATALLA.ATAQUE;
        boolean CARTACOLOCADA = true;
        ZonaBatalla zb=new ZonaBatalla();
        Carta c=new Carta(1,Carta.ELEMENTO.ESPADA);
        for(int i=0;i< ZonaBatalla.MAXZONABATALLACARDS;i++) {
            zb.agregarCartaEnPos(c, i, POSCARTA);
            Assert.assertEquals(zb.posbatalla[i],POSCARTA);
            Assert.assertEquals(zb.dispcambio[i],ZonaBatalla.DISPCAMBIO.NODISPONIBLE);
            Assert.assertEquals(zb.dispataque[i],ZonaBatalla.DISPATAQUE.DISPONIBLE);
        }
        Assert.assertEquals(zb.isCartacolocada(),CARTACOLOCADA);
    }

    @Test
    public void testAgregarCartaEnEspacioVacio() throws Exception {
        int POSCARTA = ZonaBatalla.POSBATALLA.ATAQUE;
        ZonaBatalla zb=new ZonaBatalla();
        Carta c=new Carta(1,Carta.ELEMENTO.ESPADA);
        int idZonaB0= 0;
        int idZonaB1= 1;
        int idZonaB2= 2;
        Assert.assertEquals(zb.agregarCartaEnEspacioVacio(c,POSCARTA),idZonaB0);
        Assert.assertEquals(zb.agregarCartaEnEspacioVacio(c,POSCARTA),idZonaB1);
        Assert.assertEquals(zb.agregarCartaEnEspacioVacio(c,POSCARTA),idZonaB2);
        Assert.assertEquals(zb.agregarCartaEnEspacioVacio(c,POSCARTA),VectorCartas.NOSEPUEDEAGREGARCARTAS);
    }


    @Test
    public void testCambiarPosicionBatallaAtaque() throws Exception {
        ZonaBatalla zb=new ZonaBatalla();
        Carta c=new Carta(1,Carta.ELEMENTO.ESPADA);
        for(int i=0;i< ZonaBatalla.MAXZONABATALLACARDS;i++) {
            zb.agregarCartaEnPos(c, i, ZonaBatalla.POSBATALLA.ATAQUE);
            zb.dispcambio[i]=ZonaBatalla.DISPCAMBIO.DISPONIBLE;
            zb.cambiarPosicionBatalla(i);
            Assert.assertEquals(zb.posbatalla[i], ZonaBatalla.POSBATALLA.DEFCARAARRIBA);
            Assert.assertEquals(zb.dispcambio[i],ZonaBatalla.DISPCAMBIO.NODISPONIBLE);
            Assert.assertEquals(zb.dispataque[i],ZonaBatalla.DISPATAQUE.NODISPONIBLE);
        }
    }

    @Test
    public void testCambiarPosicionBatallaDefensa() throws Exception {
        ZonaBatalla zb=new ZonaBatalla();
        Carta c=new Carta(1,Carta.ELEMENTO.ESPADA);
        for(int i=0;i< ZonaBatalla.MAXZONABATALLACARDS;i++) {
            zb.agregarCartaEnPos(c, i, ZonaBatalla.POSBATALLA.DEFCARAABAJO);
            zb.dispcambio[i]=ZonaBatalla.DISPCAMBIO.DISPONIBLE;
            zb.cambiarPosicionBatalla(i);
            Assert.assertEquals(zb.posbatalla[i], ZonaBatalla.POSBATALLA.ATAQUE);
            Assert.assertEquals(zb.dispcambio[i],ZonaBatalla.DISPCAMBIO.NODISPONIBLE);
            Assert.assertEquals(zb.dispataque[i],ZonaBatalla.DISPATAQUE.DISPONIBLE);
        }
    }

    @Test
    public void testQuitarCartaenPos() throws Exception {
        int POSCARTA = ZonaBatalla.POSBATALLA.ATAQUE;
        ZonaBatalla zb=new ZonaBatalla();
        Carta c=new Carta(1,Carta.ELEMENTO.ESPADA);
        for(int i=0;i< ZonaBatalla.MAXZONABATALLACARDS;i++) {
            zb.agregarCartaEnPos(c, i, POSCARTA);
            zb.quitarCartaenPos(i);
            Assert.assertEquals(zb.posbatalla[i], ZonaBatalla.POSBATALLA.NOHAYCARTA);
            Assert.assertEquals(zb.dispcambio[i],ZonaBatalla.DISPCAMBIO.NODISPONIBLE);
            Assert.assertEquals(zb.dispataque[i],ZonaBatalla.DISPATAQUE.NODISPONIBLE);
        }
    }

    @Test
    public void testPosibilidadCambiarPosicionBatalla() throws Exception{
        ZonaBatalla zb=new ZonaBatalla();
        Carta c=new Carta(1,Carta.ELEMENTO.ESPADA);
        int POSCARTA = ZonaBatalla.POSBATALLA.ATAQUE;
        int IDCARTAZB = 0;
        boolean NOCARTAS = false, DISP_CAMBIO = false;
        Assert.assertEquals(zb.posibilidadCambiarPosicionBatalla(IDCARTAZB), NOCARTAS);
        zb.agregarCartaEnPos(c,IDCARTAZB,POSCARTA);
        Assert.assertEquals(zb.posibilidadCambiarPosicionBatalla(IDCARTAZB), DISP_CAMBIO);
        zb.dispcambio[IDCARTAZB]=ZonaBatalla.DISPCAMBIO.DISPONIBLE;
        DISP_CAMBIO = true;
        Assert.assertEquals(zb.posibilidadCambiarPosicionBatalla(IDCARTAZB), DISP_CAMBIO);

    }

    @Test
    public void testClone() throws Exception {
        ZonaBatalla vc=new ZonaBatalla();
        int idZB0= 0;
        int idZB1= 1;
        int POSCARTA0 = ZonaBatalla.POSBATALLA.ATAQUE;
        vc.agregarCartaEnPos(new Carta(Carta.ELEMENTO.ESPADA,1),idZB0,POSCARTA0);
        ZonaBatalla vclone=(ZonaBatalla)vc.clone();
        Assert.assertEquals(vc,vclone);
        vc.agregarCartaEnPos(new Carta(Carta.ELEMENTO.ESPADA,2),idZB1,POSCARTA0);
        Assert.assertNotEquals(vc,vclone);
    }



}
