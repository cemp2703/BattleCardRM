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
    public void testGetPosCartaxId() throws Exception {
        /*
        int POS = 0;
        ZonaBatalla zb=new ZonaBatalla();
        zb.jcarta[POS].carta=new Carta(1,Carta.ELEMENTO.ESPADA);
        zb.renovarPosibilidades();
        Assert.assertEquals(zb.dispataque[POS],ZonaBatalla.DISPATAQUE.DISPONIBLE);
        Assert.assertEquals(zb.dispcambio[POS],ZonaBatalla.DISPCAMBIO.DISPONIBLE);
        */
    }

    @Test
    public void testRenovarDisponibilidades() throws Exception {
        int POSCARTA0 = ZonaBatalla.POSCARTA.ATAQUE;
        int POSCARTA1 = ZonaBatalla.POSCARTA.DEFCARAABAJO;
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
        int POSCARTA = ZonaBatalla.POSCARTA.ATAQUE;
        boolean CARTACOLOCADA = true;
        ZonaBatalla zb=new ZonaBatalla();
        Carta c=new Carta(1,Carta.ELEMENTO.ESPADA);
        for(int i=0;i< ZonaBatalla.MAXZONABATALLACARDS;i++) {
            zb.agregarCartaEnPos(c, i, POSCARTA);
            Assert.assertEquals(zb.poscarta[i],POSCARTA);
            Assert.assertEquals(zb.dispcambio[i],ZonaBatalla.DISPCAMBIO.NODISPONIBLE);
            Assert.assertEquals(zb.dispataque[i],ZonaBatalla.DISPATAQUE.DISPONIBLE);
        }
        Assert.assertEquals(zb.isCartacolocada(),CARTACOLOCADA);
    }

    @Test
    public void testAgregarCartaEnEspacioVacio() throws Exception {
        int POSCARTA = ZonaBatalla.POSCARTA.ATAQUE;
        ZonaBatalla zb=new ZonaBatalla();
        Carta c=new Carta(1,Carta.ELEMENTO.ESPADA);
        int idZonaB= zb.agregarCartaEnEspacioVacio(c,POSCARTA);
        Assert.assertEquals(zb.obtenerCartaxId(idZonaB),c);
    }


    @Test
    public void testCambiarPosicionBatallaAtaque() throws Exception {
        ZonaBatalla zb=new ZonaBatalla();
        Carta c=new Carta(1,Carta.ELEMENTO.ESPADA);
        for(int i=0;i< ZonaBatalla.MAXZONABATALLACARDS;i++) {
            zb.agregarCartaEnPos(c, i, ZonaBatalla.POSCARTA.ATAQUE);
            zb.dispcambio[i]=ZonaBatalla.DISPCAMBIO.DISPONIBLE;
            zb.cambiarPosicionBatalla(i);
            Assert.assertEquals(zb.poscarta[i],ZonaBatalla.POSCARTA.DEFCARAARRIBA);
            Assert.assertEquals(zb.dispcambio[i],ZonaBatalla.DISPCAMBIO.NODISPONIBLE);
            Assert.assertEquals(zb.dispataque[i],ZonaBatalla.DISPATAQUE.NODISPONIBLE);
        }
    }

    @Test
    public void testCambiarPosicionBatallaDefensa() throws Exception {
        ZonaBatalla zb=new ZonaBatalla();
        Carta c=new Carta(1,Carta.ELEMENTO.ESPADA);
        for(int i=0;i< ZonaBatalla.MAXZONABATALLACARDS;i++) {
            zb.agregarCartaEnPos(c, i, ZonaBatalla.POSCARTA.DEFCARAABAJO);
            zb.dispcambio[i]=ZonaBatalla.DISPCAMBIO.DISPONIBLE;
            zb.cambiarPosicionBatalla(i);
            Assert.assertEquals(zb.poscarta[i],ZonaBatalla.POSCARTA.ATAQUE);
            Assert.assertEquals(zb.dispcambio[i],ZonaBatalla.DISPCAMBIO.NODISPONIBLE);
            Assert.assertEquals(zb.dispataque[i],ZonaBatalla.DISPATAQUE.DISPONIBLE);
        }
    }

    @Test
    public void testQuitarCartaenPos() throws Exception {
        int POSCARTA = ZonaBatalla.POSCARTA.ATAQUE;
        ZonaBatalla zb=new ZonaBatalla();
        Carta c=new Carta(1,Carta.ELEMENTO.ESPADA);
        for(int i=0;i< ZonaBatalla.MAXZONABATALLACARDS;i++) {
            zb.agregarCartaEnPos(c, i, POSCARTA);
            zb.quitarCartaenPos(i);
            Assert.assertEquals(zb.poscarta[i],ZonaBatalla.POSCARTA.NOHAYCARTA);
            Assert.assertEquals(zb.dispcambio[i],ZonaBatalla.DISPCAMBIO.NODISPONIBLE);
            Assert.assertEquals(zb.dispataque[i],ZonaBatalla.DISPATAQUE.NODISPONIBLE);
        }
    }

    @Test
    public void testClone() throws Exception {
        ZonaBatalla vc=new ZonaBatalla();
        int idZB0= 0;
        int idZB1= 1;
        int POSCARTA0 = ZonaBatalla.POSCARTA.ATAQUE;
        vc.agregarCartaEnPos(new Carta(Carta.ELEMENTO.ESPADA,1),idZB0,POSCARTA0);
        ZonaBatalla vclone=(ZonaBatalla)vc.clone();
        Assert.assertEquals(vc,vclone);
        vc.agregarCartaEnPos(new Carta(Carta.ELEMENTO.ESPADA,2),idZB1,POSCARTA0);
        Assert.assertNotEquals(vc,vclone);
    }


}
